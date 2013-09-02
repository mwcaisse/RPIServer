package com.ricex.rpi.server;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ricex.rpi.common.message.IMessage;
import com.ricex.rpi.server.client.Client;

/**
 * A server module
 * 
 * @author Mitchell Caisse
 * 
 */

/*TODO: Change the framework used to send and receive data
*/


public abstract class Server<T extends Client> implements Runnable {

	private static final Logger log = LoggerFactory.getLogger(Server.class);
	
	/** The name of the server to use when printing out details */
	private final String name;
	
	/** The Socket this server will use */
	private ServerSocket socket;
	
	/** Whether or not the server is running */
	private boolean running;

	/** The port that this server will listen for conenctions on */
	private final int port;
	
	/** The maximum number of clients allowed */
	private final int maxConnections;

	/** Map of currently connected clients */
	private Map<Long, T> connectedClients;
	
	/** The previously used client id */
	private long prevId;
	
	/** The thread that the server will run in  */
	private Thread serverThread;

	public Server(int port, int maxConnections, String name) {
		this.port = port;
		this.maxConnections = maxConnections;
		this.name = name;
		
		connectedClients = new HashMap<Long, T>();
		prevId = 0;
	}
	
	/** Starts the server thread
	 * 
	 * @return True if it started the server, false if the server was already running
	 */
	
	public boolean startServer() {
		if (!running) {
			serverThread = new Thread(this);
			serverThread.start();
			return true;
		}
		return false;
	}
	
	/** Stops the server thread, and waits for it to stop executing
	 * 
	 */
	
	public void stopServer() {
		if (running) {
			running = false;
			serverThread.interrupt();
			try {
				serverThread.join();
			}
			catch (InterruptedException e) {
				log.warn("Interrupted while waiting for server thread to exit");
			}
			serverThread = null;
		}
	}
	
	/** Returns true if the server is running false otherwise
	 * 
	 * @return True if running, false otherwise
	 */
	
	public boolean isRunning() {
		return running;
	}

	public void run() {
		try {
			socket = new ServerSocket(port);
			
		}
		catch (IOException e) {
			log.error("Unable to start server with name {}", name, e);
			return;
		}
		running = true;
		
		log.info("{} Server started", name);
		
		// listen for conenctions to the server
		while (running) {			
			try {
				/*
				 * before we check for new clients, let update the current list
				 * of clients
				 */
				updateConnectedClients();
	
				// wait for connections
				Socket clientSocket = socket.accept();
				log.info("User connected to {} server", name);
	
				// check if server is not full
				if (connectedClients.size() < maxConnections) {
					// create the client, and add to the connected clients
					T client = createClient(clientSocket);
					connectedClients.put(client.getId(), client);
				}
				else {
					log.info("No more connections allow to server {}", name);
					// we are at max connections.
					clientSocket.close();
				}
			}
			catch (InterruptedIOException e) {
				log.info("Server thread was interrupted");
			}
			catch (IOException e) {
				log.error("A server error has occured on server {}", name, e);
			}
			
		}
		/*
		 *  disconnectClients();
		 *	log.info("Server {} has stopped", name);
		 */
	}

	/** Returns the next avaiable id */

	protected long getNextId() {
		return prevId++; // return the id, and then increment counter
	}

	/**
	 * Removes clients that have disconnected from the list of connected clients
	 * 
	 */

	private void updateConnectedClients() {
		List<T> oldClients = new ArrayList<T>(connectedClients.values());
		for (T client : oldClients) {
			if (!client.isConnected()) {
				// client is not conencted, remove from the list
				connectedClients.remove(client.getId());
			}
		}

	}


	/**
	 * Returns the list of connected clients
	 * 
	 */

	public synchronized List<T> getConnectedClients() {
		return new ArrayList<T>(connectedClients.values());
	}
	
	/** Gets a client by thier id
	 * 
	 * @param id The id of the client
	 * @return The client with the given id, or null if it doesnt exist
	 */
	
	public synchronized T getClient(long id) {
		return connectedClients.get(id);		
	}
	
	/** Creates a client that connected with the given socket
	 * 
	 * @param socket The clients socket
	 * @return The new client
	 */
	
	protected abstract T createClient(Socket socket);
	
	
	/** Disconencts the given client from the server
	 * 
	 * @param client The client to disconnect
	 */
	public synchronized void disconnectClient(Client client) {
		client.setConnected(false);
		updateConnectedClients();
	}
	
	
	/** Ssends the given message to all connected clients */
	
	public synchronized void sendToAllClients(IMessage message) {
		for (Client client : connectedClients.values()) {
			client.sendMessage(message);
		}
	}
	
	/** Shuts down the server
	 *  TODO: implement shutdown better
	 */
	
	public synchronized void shutdown() {
		log.info("Server {} is shutting down", name);
		running = false;
	}
}
