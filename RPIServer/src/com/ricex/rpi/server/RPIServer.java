package com.ricex.rpi.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.ricex.rpi.common.RPIProperties;
import com.ricex.rpi.common.RPIStatus;

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

	/** List of currently connected clients */
	private List<Client> connectedClients;	
	
	/** List of status listeners for the clients */
	private List<StatusListener> statusListeners;
	
	public RPIServer() {
		
		//get the ports from the server config */
		rpiPort = RPIProperties.getInstance().getRPIPort();
		remotePort = RPIProperties.getInstance().getRemotePort();
		
		connectedClients = new ArrayList<Client>();
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
				updateClientList();

				Socket clientSocket = socket.accept();
				
				System.out.println("User connected to server!!!");

				if (connectedClients.size() < MAX_CLIENTS) {
					// we only allow one connection at a time,
					Client client = new Client(clientSocket,this);
					connectedClients.add(client);
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

	private void updateClientList() {
		for (int i = 0; i < connectedClients.size(); i++) {
			if (!connectedClients.get(i).isConnected()) {
				// client isnt connected anymore, remove them from the list
				connectedClients.remove(i);
				i--; // decrement the loop counter by one
				System.out.println("A client has disconnected");
			}
		}

	}
	
	/** Returns the list of connected clients
	 * 
	 */
	
	public synchronized List<Client> getConnectedClients() {
		return connectedClients;
	}
	
	/** Registers the given status listener */
	
	public void registerStatusListener(StatusListener listener) {
		statusListeners.add(listener);
	}
	
	public void notifityStatusListeners(RPIStatus status, String filePlaying) {
		for (StatusListener listener : statusListeners) {
			listener.statusChanged(status,filePlaying);
		}
	}

}
