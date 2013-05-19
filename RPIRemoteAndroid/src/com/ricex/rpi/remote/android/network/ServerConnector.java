package com.ricex.rpi.remote.android.network;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import android.util.Log;

import com.ricex.rpi.remote.android.cache.RemoteProperties;

/** Connents to the server */

public class ServerConnector {

	
	/** The instance of the server connector */
	private static ServerConnector _instance;
	
	public static ServerConnector getInstance() {
		if (_instance == null) {
			_instance = new ServerConnector();
		}
		return _instance;
	}
	
	/** The socket used to connect to the server */
	private Socket socket;
	
	/** The server handler to use to conenct to the server */
	private ServerHandler serverHandler;
	
	/** The thread in which the server handler runs in */
	private Thread serverHandlerThread;
	
	/** Whether or not we are conencted to the server */
	private boolean connected;
	
	/** Creates a new instance of server connector */
	private ServerConnector() {	
		connected = false;
	}
	
	/** Connects to the server
	 * 
	 * @return True if connected, false if it failed
	 */
	public boolean connect() {
		if (connected) {
			return true; // we are already connected
		}
		
		try {
			//create the connection to the server
			socket = new Socket(RemoteProperties.getInstance().getServerAddress(), RemoteProperties.getInstance().getServerPort());
			
			//create the handler for the server
			serverHandler = new ServerHandler(socket);
			
			//create the thread the server handler will run in
			serverHandlerThread = new Thread(serverHandler);
			serverHandlerThread.start();
			
		}
		catch (UnknownHostException e) {
			Log.e("RPIServerConnector", "Error connecting to Server", e);
			return false;
		}
		catch (IOException e) {
			Log.e("RPIServerConnector", "Error connecting to Server", e);
			return false;
		}
		connected = true;
		return false;
	}
	
	/** Disconnects from the server
	 * 
	 * @return True if sucessful or already disconnectd, false if cannot disconnect
	 */ 
	public boolean disconnect() {
		
		serverHandler.close();
		try {
			socket.close();
		}
		catch (IOException e) {
			Log.e("RPIServerConnector", "Error disconecting from server", e);
		}		
		
		return true;
	}
	
	/** Returns whether or not we are currently connected to the server */
	public boolean isConnected() {
		return connected;
	}
}
