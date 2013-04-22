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
	public static final String SERVER_IP = "192.168.1.110";

	/** The socket this will use to conenct to the server */
	private Socket socket;
	
	/** The server handle for this client */
	private ServerHandler serverHandler;

	public RPIClient() throws UnknownHostException, IOException {
		socket = new Socket(SERVER_IP, SERVER_PORT);
		serverHandler = new ServerHandler(socket);
	}

	public static void main(String[] args) {

		RPIClient client;
		try {
			client = new RPIClient();
		}
		catch (UnknownHostException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	

	}
}
