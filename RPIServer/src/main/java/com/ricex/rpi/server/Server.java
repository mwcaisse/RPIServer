package com.ricex.rpi.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ricex.rpi.common.message.IMessage;
import com.ricex.rpi.server.client.Client;
import com.ricex.rpi.server.client.ClientConnectionListener;

/**
 * A server module
 * 
 * @author Mitchell Caisse
 * 
 */

/*TODO: Change the framework used to send and receive data
*/


public abstract class Server<T extends Client> implements Runnable {

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
	
	/** List of connection listeners for this server */
	private List<ClientConnectionListener<T>> connectionListeners;

	public Server(int port, int maxConnections, String name) {
		this.port = port;
		this.maxConnections = maxConnections;
		this.name = name;
		
		connectedClients = new HashMap<Long, T>();
		prevId = 0;
		connectionListeners = new ArrayList<ClientConnectionListener<T>>();
	}

	public void run() {
		try {
			socket = new ServerSocket(port);
			
		}
		catch (IOException e) {
			System.out.println("Error starting the server");
			e.printStackTrace();
			return;
		}
		running = true;
		
		System.out.println(name + " Server Started");
		
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
				System.out.println("User connected to " + name + " server");
	
				// check if server is not full
				if (connectedClients.size() < maxConnections) {
					// create the client, and add to the connected clients
					T rPIClient = createClient(clientSocket);
					connectedClients.put(rPIClient.getId(), rPIClient);
					notifyConnectionListeners(true, rPIClient);
				}
				else {
					System.out.println(name + " Server: No more connections allowed");
					// we are at max connections.
					PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
					out.println("No more connections allowed, sorry. Come back later.");
					out.close();
					clientSocket.close();
				}
			}
			catch (IOException e) {
				System.out.println(name + " Server error");
				e.printStackTrace();
			}		
		}
		/*
		 *  disconnectClients();
		 *	System.out.println(name + " Server Stopped");
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
				notifyConnectionListeners(false, client);
			}
		}

	}

	private synchronized void disconnectClients() {
		for (T client : connectedClients.values()) {
			client.close();
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

	/** Adds the given client connection listener */

	public void addConnectionListener(ClientConnectionListener<T> listener) {
		connectionListeners.add(listener);
	}

	/** Removes the given client connection listener */

	public void removeConnectionListener(ClientConnectionListener<T> listener) {
		connectionListeners.remove(listener);
	}

	/**
	 * Notifies the connection listeners that the given client either connected
	 * or disconnected
	 * 
	 * @param connected
	 *            True of the client connected, false if they disconnected
	 * @param client
	 *            The client that disconencted or connected
	 */

	private void notifyConnectionListeners(boolean connected, T client) {
		for (ClientConnectionListener<T> listener : connectionListeners) {
			if (connected) {
				listener.clientConnected(client);
			}
			else {
				listener.clientDisconnected(client);
			}
		}
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
		System.out.println(name + " shutting down");
		running = false;
	}
}
