package com.ricex.rpi.remote;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.ricex.rpi.remote.player.RPIPlayer;

/** Manages the connection to the server
 * 
 * @author Mitchell Caisse
 *
 */

public class RPIClient {

	
	/** The address of the server */
	protected String serverAddress;
	
	/** The port the server is running on */
	protected int serverPort;
	
	/** The socket used to connect to the server */
	protected Socket socket;
	
	/** Whether or not the client is connected to the server */
	protected boolean connected;
	
	/** The server handler */
	protected ServerHandler handler;
	
	/** The thread the server handler is being executed in */
	private Thread serverHandlerThread;
	
	/** Creates a new RPIClient to connect to a server
	 * 
	 */
	
	public RPIClient() {
		connected = false;
	}

	
	/** Connects to the server, and returns the RPIPlayer representing the server
	 * 
	 * @return The RPI player representing the server, null if unable to connect
	 */
	
	public RPIPlayer connectToServer() throws IOException, UnknownHostException {
		if (!connected) {
			//only connect to the server if we are not currently connected
			socket = new Socket(serverAddress, serverPort);
			ServerHandler handler = new ServerHandler(socket);
			return handler.getPlayer();			
		}
		return null;
	}
	
	/** Creates a starts a server handler on the given socket
	 * 
	 * @param socket The socket to create the server handler for
	 */
	
	private void startServerHandler(Socket socket) throws IOException {
		 handler = new ServerHandler(socket);
		 serverHandlerThread = new Thread(handler);
		 serverHandlerThread.start();
	}
	
	/** Disconnects from the server
	 * 
	 */
	
	public void disconnectFromServer() {
		
	}
	
	
	/** Sets the server address this client will connect to
	 * 
	 * @param serverAddress The new server address
	 */
	
	public void setServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
	}
	
	/** Returns the current server address used to connect to the server
	 * 
	 * @return the server address
	 */
	
	public String getServerAddress() {
		return serverAddress;
	}
	
	/** Sets the server port this client will use to connect to the server
	 * 
	 * @param serverPort The new server port
	 */
	
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}
	
	/** Returns the current server port used to connect to the server
	 * 
	 * @return the server port
	 */
	
	public int getServerPort() {
		return serverPort;
	}
	
}
