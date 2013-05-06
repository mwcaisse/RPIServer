package com.ricex.rpi.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * For now this server will only accept one client, the Raspberry Pi
 * TODO: implement the different servers for the RPI clients and the remote clients
 * @author Mitchell
 * 
 */

public class RPIServer implements Runnable {

	/** The socket for the server*/
	private ServerSocket socket;

	/** The port that the server for the RPI's will run on */
	private final int rpiPort;
	
	/** The port that remote controls will run on */
	private final int remotePort;
	
	/** The maximum number of clients allowed */
	private final int maxConnections;

	/** Map of currently connected clients */
	private Map<Long, Client> connectedClients;
	
	/** The previously used client id */
	private long prevId;
	
	/** List of connection listeners for this server */
	private List<ClientConnectionListener> connectionListeners;
	
	public RPIServer() {
		prevId = 0;
		//get the ports from the server config 
		rpiPort = RPIServerProperties.getInstance().getRPIPort();
		remotePort = RPIServerProperties.getInstance().getRemotePort();
		maxConnections = RPIServerProperties.getInstance().getMaxConnectins();		
		connectedClients = new HashMap<Long, Client>();
		connectionListeners = new ArrayList<ClientConnectionListener>();
	}

	public void run() {
		try {
			socket = new ServerSocket(rpiPort);

			System.out.println("Server Started");
			// listen for conenctions to the server
			while (true) {
				/*
				 * before we check for new clients, let update the current list
				 * of clients
				 */
				updateConnectedClients();

				//wait for connections
				Socket clientSocket = socket.accept();				
				System.out.println("User connected to server");

				//check if server is not full
				if (connectedClients.size() < maxConnections) {
					//create the client, and add to the connected clients
					Client client = new Client(getNextId(), clientSocket);
					connectedClients.put(client.getId(), client);
					notifyConnectionListeners(true, client);
				}
				else {
					System.out.println("Server: No more connections allowed");
					//we are at max connections.
					PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
					out.println("No more connections allowed, sorry. Come back later.");
					out.close();
					clientSocket.close();
				}		
			}

		}
		catch (IOException e) {
			System.out.println("Server error");
			e.printStackTrace();
		}
		finally {
			//server is closing, lets close all connections
		}	
	}
	
	/** Returns the next avaiable id */
	
	private long getNextId() {
		return prevId++; // return the id, and then increment counter
	}

	/** Removes clients that have disconnected from the list of connected clients
	 * 
	 */
	
	private void updateConnectedClients() {
		List<Client> oldClients = new ArrayList<Client>(connectedClients.values());
		for (Client client : oldClients ) {
			if (!client.isConnected()) {
				//client is not conencted, remove from the list
				connectedClients.remove(client.getId());	
				notifyConnectionListeners(false, client);
			}
		}

	}
	
	private void disconnectClients() {
		for (Client client : connectedClients.values()) {
			client.close();
		}	
	}
	
	/** Returns the list of connected clients
	 * 
	 */
	
	public synchronized List<Client> getConnectedClients() {
		return new ArrayList<Client>(connectedClients.values());
	}
	
	/** Adds the given client connection listener */
	
	public void addConnectionListener(ClientConnectionListener listener) {
		connectionListeners.add(listener);
	}
	
	/** Removes the given client connection listener */
	
	public void removeConnectionListener(ClientConnectionListener listener) {
		connectionListeners.remove(listener);
	}	
	
	/** Notifies the connection listeners that the given client either connected or disconnected 
	 * 
	 * @param connected True of the client connected, false if they disconnected
	 * @param client The client that disconencted or connected
	 */
	
	private void notifyConnectionListeners(boolean connected, Client client) {
		for (ClientConnectionListener listener : connectionListeners) {
			if (connected) {
				listener.clientConnected(client);
			}
			else {
				listener.clientDisconnected(client);
			}
		}
	}

}
