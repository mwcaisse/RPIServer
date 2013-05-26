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
	
	/** Whether or not we are conencted to the server */
	private boolean connected;
	
	/** The server thread that will be used to conenct to the server */
	private ServerThread serverThread;

	/** Creates a new instance of server connector */
	private ServerConnector() {
		connected = false;
		serverThread = new ServerThread();
	}

	/**
	 * Connects to the server
	 */
	public void connect() {
		if (connected) {
			return; // if we are already connected, no need to re-connect
		}		
		
		// connect to the server ina different thread
		Thread connectThread = new Thread(serverThread);
		connectThread.start();
	}

	/**
	 * Disconnects from the server
	 */
	public void disconnect() {
		serverThread.disconnect();
	}

	/** Returns whether or not we are currently connected to the server */
	public boolean isConnected() {
		return connected;
	}

	/** Class representing the thread that the server will run in */
	private class ServerThread implements Runnable {
		
		/** The socket used to connect to the server */
		private Socket socket;

		/** The server handler to use to conenct to the server */
		private ServerHandler serverHandler;

		/** The thread in which the server handler runs in */
		private Thread serverHandlerThread;
		
		/** Creates a new server thread */
		private ServerThread() {
			
		}

		public void run() {
			try {				
				RemoteProperties properties = RemoteProperties.getInstance();
				// create the connection to the server
				socket = new Socket(properties.getServerAddress(), properties.getServerPort());

				// create the handler for the server
				serverHandler = new ServerHandler(socket);

				// create the thread the server handler will run in
				serverHandlerThread = new Thread(serverHandler);
				serverHandlerThread.start();				
				connected = true;
				Log.i("RPIServerConnector", "We are now connected to the server");
			}
			catch (UnknownHostException e) {
				Log.e("RPIServerConnector", "Error connecting to Server", e);
				connected = false;
			}
			catch (IOException e) {
				Log.e("RPIServerConnector", "Error connecting to Server", e);
				connected = false;
			}
		}
		
		/** Disconnects from the server */
		public void disconnect() {
			serverHandler.close();
			try {
				socket.close();
			}
			catch (IOException e) {
				Log.e("RPIServerConnector", "Error disconecting from server", e);
			}	
			connected = false;
		}

	}
}
