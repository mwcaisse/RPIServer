package com.ricex.rpi.server.client;

import java.io.IOException;
import java.net.Socket;

import com.ricex.rpi.server.Server;


/**
 * A client that connects to the given server interface
 * 
 * @author Mitchell
 * 
 */

public abstract class Client {

	/** The id of this client */
	final protected long id;

	/** The socket that the client connected on */
	final protected Socket socket;

	/** Indicates wether this client is still connected or not */
	protected boolean connected = false;
	
	/** The server this client is conencted to */
	private Server<?> server;

	/**
	 * Creates a new client with the given id and socket
	 * 
	 * @param id
	 *            The ID of the client
	 * @param socket
	 *            The socket through which the client connected
	 */

	public Client(Server<?> server, long id, Socket socket) {
		this.server = server;
		this.id = id;
		this.socket = socket;	
		connected = true;
	}

	/** Closes the clients connection, and cleans up resources */

	public void close() {
		try {
			socket.close();
		}
		catch (IOException e) {
		}
	}

	/** Returns the unique id of this client */

	public long getId() {
		return id;
	}

	/**
	 * Returns the socket that this client is connected on
	 */

	public Socket getSocket() {
		return socket;
	}

	/** Returns if the client is currently connected or not */

	public boolean isConnected() {
		return connected;
	}
	
	/** Sets whether this client is connected or not */
	public void setConnected(boolean connected) {
		this.connected = connected;
		if (!connected) {
			//notify the server that we disconnected;
			server.clientDisconnected(this);
		}	
	}

	/** Notifies all of the listeners that a change has been made */
	protected abstract void notifyChangeListeners();

}
