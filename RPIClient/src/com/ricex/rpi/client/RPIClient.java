package com.ricex.rpi.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * RPI client that connects to the server
 * 
 * @author Mitchell
 * 
 */

public class RPIClient {

	/** Constants for the Server IP Address and the Port */
	public static final int SERVER_PORT = 1337;
	public static final String SERVER_IP = "192.168.1.103";

	/** The socket this will use to conenct to the server */
	private Socket socket;
	
	/** The server handle for this client */
	private ServerHandler serverHandler;

	public RPIClient() throws UnknownHostException, IOException {
		socket = new Socket(SERVER_IP, SERVER_PORT);
		serverHandler = new ServerHandler(socket);
		
		//block on the server handler
		serverHandler.run();
	}

	public static void main(String[] args) {

		RPIClient client;
		
		while (true) {			
			try {
				client = new RPIClient();
			}
			catch (UnknownHostException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				System.out.println("Connection refused, try again in 15 mins");
				try {
					Thread.sleep( 1000 * 60 * 15); //sleep for 15 mins
					
				}
				catch (InterruptedException ex) {
					System.out.println("Waiting interupted, trying to connect again");
				}
			}
		}
	

	}
}
