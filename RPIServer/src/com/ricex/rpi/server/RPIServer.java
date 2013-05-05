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

import com.ricex.rpi.common.RPIProperties;

/**
 * For now this server will only accept one client, the Raspberry Pi
 * TODO: implement the different servers for the RPI clients and the remote clients
 * @author Mitchell
 * 
 */

public class RPIServer implements Runnable {
	
	//TODO: might want to add this to the rpi.conf
	private static final int MAX_CLIENTS = 1;

	private ServerSocket socket;

	/** The port that the server for the RPI's will run on */
	private int rpiPort;
	
	/** The port that remote controls will run on */
	private int remotePort;

	/** Map of currently connected clients */
	private Map<Long, Client> connectedClients;
	
	/** The previously used client id */
	private long prevId;
	
	public RPIServer() {
		prevId = 0;
		//get the ports from the server config 
		rpiPort = RPIProperties.getInstance().getRPIPort();
		remotePort = RPIProperties.getInstance().getRemotePort();
		
		connectedClients = new HashMap<Long, Client>();
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
				if (connectedClients.size() < MAX_CLIENTS) {
					//create the client, and add to the connected clients
					Client client = new Client(getNextId(), clientSocket);
					connectedClients.put(client.getId(), client);
				}
				else {
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
	}
	
	/** Returns the next avaiable id */
	
	private long getNextId() {
		return prevId++; // return the id, and then increment counter
	}

	/** Removes clients that have disconnected from the list of connected clients
	 * 
	 */
	
	private void updateConnectedClients() {
		Collection<Client> oldClients = connectedClients.values();
		for (Client client : oldClients ) {
			if (!client.isConnected()) {
				//client is not conencted, remove from the list
				connectedClients.remove(client.getId());				
			}
		}

	}
	
	/** Returns the list of connected clients
	 * 
	 */
	
	public synchronized List<Client> getConnectedClients() {
		return new ArrayList<Client>(connectedClients.values());
	}

}
