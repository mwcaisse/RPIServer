package com.ricex.rpi.remote.android.network;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.ricex.rpi.common.message.IMessage;
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

	/** Listeners that are listening for the status of the server */
	private List<ServerConnectionListener> listeners;

	/** Creates a new instance of server connector */
	private ServerConnector() {
		connected = false;
		serverThread = new ServerThread();
		listeners = new ArrayList<ServerConnectionListener>();
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

	/** Sends the given message to the server
	 * 
	 * @param message The message to send to the server
	 * @return Whether there were any errors or not
	 */

	public boolean sendMessage(IMessage message) {
		if (connected) {
			return serverThread.sendMessage(message);
		}
		return false;
	}

	/** Adds the given listener to be notified when the server changes connection state
	 * 
	 * @param listener The listener to add
	 */

	public void addConnectionListener(ServerConnectionListener listener) {
		listeners.add(listener);
	}

	/** Removes the given connection listener
	 * 
	 * @param listener The connection listener to remove
	 */

	public void removeConnectionListener(ServerConnectionListener listener) {
		listeners.remove(listener);
	}

	/** Notifies all of the listeners of the change in connection status
	 * 
	 * @param connected Whether we are connected to the server or not
	 */

	private void notifyListeners(boolean connected) {
		for (ServerConnectionListener listener : listeners) {
			listener.serverConnectionChanged(connected);
		}
	}
	
	/** Notifies all of the listeners of the error that occured when connecting
	 * 
	 * @param connected Whether we are connected to the server or not
	 */

	private void notifyListenersError(Exception error) {
		for (ServerConnectionListener listener : listeners) {
			listener.errorConnecting(error);
		}
	}

	/** Sets the connection status of the server */

	private void setConnected(boolean connected) {
		this.connected = connected;
		notifyListeners(connected);
	}

	/** Class representing the thread that the server will run in */
	private class ServerThread implements Runnable, ServerConnectionListener {

		/** The socket used to connect to the server */
		private Socket socket;

		/** The server handler to use to conenct to the server */
		private ServerHandler serverHandler;

		/** The thread in which the server handler runs in */
		private Thread serverHandlerThread;

		/** The address of the server to connect to */
		private String serverAddress;

		/** The port of the server to connect to */
		private int serverPort;

		/** Creates a new server thread */
		private ServerThread() {			
		}

		@Override
		public void run() {
			try {

				serverAddress = RemoteProperties.getInstance().getServerAddress();
				serverPort = RemoteProperties.getInstance().getServerPort();
				
				Log.i("RPIServerConnector", "Connecting to server at " + serverAddress + ":" + serverPort);
				
				// create the connection to the server
				socket = new Socket(serverAddress, serverPort);

				// create the handler for the server
				serverHandler = new ServerHandler(socket, this);

				// create the thread the server handler will run in
				serverHandlerThread = new Thread(serverHandler);
				serverHandlerThread.start();
				setConnected(true);
				Log.i("RPIServerConnector", "We are now connected to the server");
			}
			catch (UnknownHostException e) {
				Log.e("RPIServerConnector", "Error connecting to Server", e);
				notifyListenersError(e);
			}
			catch (IOException e) {
				Log.e("RPIServerConnector", "Error connecting to Server", e);
				notifyListenersError(e);
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
			setConnected(false);
		}

		/** Sends a message to the server
		 * 
		 * @param message The message to send
		 * @return Whther the send was sucessful or not
		 */

		public boolean sendMessage(IMessage message) {
			return serverHandler.sendMessage(message);
		}

		/** Used by serverHandler to notify us that we have been disconnected from the server
		 * 
		 * This is called by serverHandler
		 * 
		 */
		@Override
		public void serverConnectionChanged(boolean connected) {
			setConnected(false);
		}

		/**
		 * {@inheritDoc}
		 */
		
		@Override
		public void errorConnecting(Exception e) {
			
		}
	}
}
