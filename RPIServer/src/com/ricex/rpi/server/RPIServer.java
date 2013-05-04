package com.ricex.rpi.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.ricex.rpi.common.PlayerModule;

/**
 * For now this server will only accept one client, the Raspberry Pi
 * 
 * @author Mitchell
 * 
 */

public class RPIServer implements Runnable {

	public static final int PORT = 6510;
	
	private static final int MAX_CLIENTS = 1;

	private ServerSocket socket;

	/** The port that the server will run on */
	private int port;

	/** List of currently connected clients */
	private List<Client> connectedClients;	
	
	public RPIServer(int port) {
		this.port = port;
		connectedClients = new ArrayList<Client>();
	}

	public void run() {
		try {
			socket = new ServerSocket(port);

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
					Client client = new Client(clientSocket);
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

}
