package com.ricex.rpi.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.ricex.rpi.common.RPIProperties;

/**
 * RPI client that connects to the server
 * 
 * @author Mitchell
 * 
 */

public class RPIClient {

	/** The socket this will use to conenct to the server */
	private Socket socket;
	
	/** The server handle for this client */
	private ServerHandler serverHandler;
	
	/** The ip address of the server */
	private String serverIp;
	
	/** Port of the server to connect to */
	private int serverPort;

	public RPIClient() throws UnknownHostException, IOException {
		serverIp = RPIProperties.getInstance().getServerIp();
		serverPort = RPIProperties.getInstance().getRPIPort();
		
		socket = new Socket(serverIp, serverPort);
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
