package com.ricex.rpi.server;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * RPI client that connects to the server
 * 
 * @author Mitchell Caisse
 * 
 */

public class RPIClient {

	/** The socket this will use to conenct to the server */
	private Socket socket;
	
	/** The server handle for this client */
	private ServerHandler serverHandler;
	
	/** The thread that the server handler will run in */
	private Thread serverHandlerThread;
	
	/** The ip address of the server */
	private String serverIp;
	
	/** Port of the server to connect to */
	private int serverPort;	


	public RPIClient() {
		serverIp = RPIClientProperties.getInstance().getServerIp();
		serverPort = RPIClientProperties.getInstance().getRPIPort();

	}
	
	/** Connects to the server
	 * 
	 * @throws UnknownHostException If the host cannot be found
	 * @throws IOException
	 */
	
	public void connectToServer() throws UnknownHostException, IOException {
		socket = new Socket(serverIp, serverPort);
		serverHandler = new ServerHandler(this,socket);	
		
		//create and start the server handler thread
		serverHandlerThread = new Thread(serverHandler);
		serverHandlerThread.start();
	}
	
	/** Disconnects from the server, waits for the server thread to finish, and closes the socket connections
	 */
	
	public void disconnectFromServer() {
		if (serverHandler != null) {		
			System.out.println("Disconnecting from server");
			serverHandler.disconnect();
			System.out.println("Interipting server handler");
			serverHandlerThread.interrupt();
			try {
				System.out.println("Waiting to join on server handler");
				serverHandlerThread.join();
			}
			catch (InterruptedException e) {
				System.out.println("Error waiting on server handler thread");
				e.printStackTrace();
			}
			serverHandlerThread = null;
			
			try {
				socket.close();
			}
			catch (IOException e) {
				System.out.println("Error closing the server socket");
				e.printStackTrace();
			}
		}
	}
	
	/** 
	 * @return Whether the client is connected to the server or not
	 */
	
	public boolean isConnected() {
		return serverHandler != null && serverHandler.isConnected();
	}
	
	/** Returns the IP address and port that the server is connected to
	 * 
	 * @return serverIp:serverPort
	 */
	
	public String getServerInfo() {
		return serverIp + ":" + serverPort;
	}
	
	public static void main(String[] args) {
		InputHandler handler = new InputHandler();
		handler.run();
	}	
	
}
