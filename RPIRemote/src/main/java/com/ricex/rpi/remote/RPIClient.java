package com.ricex.rpi.remote;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ricex.rpi.remote.player.RPIPlayer;
import com.ricex.rpi.remote.player.RPIRemote;

/** Manages the connection to the server
 * 
 * @author Mitchell Caisse
 *
 */

public class RPIClient {

	/** The logger */
	private static final Logger log = LoggerFactory.getLogger(RPIRemote.class);
	
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
		serverAddress = RPIRemoteProperties.getInstance().getServerAddress();
		serverPort = RPIRemoteProperties.getInstance().getServerPort();
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
			startServerHandler();
			return handler.getPlayer();			
		}
		return null;
	}
	
	/** Starts the server handler for this client, needs to be called after connected to server, and only once.
	 */
	
	private void startServerHandler() {
		if (serverHandlerThread == null && handler != null) {
			serverHandlerThread = new Thread(handler);
			serverHandlerThread.start();
		}
		else {
			log.error("Unable to start the server handler, it is either already running or we are not connected to the server");
		}
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
