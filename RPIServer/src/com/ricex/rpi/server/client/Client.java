package com.ricex.rpi.server.client;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


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

	/**
	 * Creates a new client with the given id and socket
	 * 
	 * @param id
	 *            The ID of the client
	 * @param socket
	 *            The socket through which the client connected
	 */

	public Client(long id, Socket socket) {
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

	/** Notifies all of the listeners that a change has been made */
	protected abstract void notifyChangeListeners();


}
