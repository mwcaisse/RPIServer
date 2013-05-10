package com.ricex.rpi.server;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/** A client that connects to the given server interface 
 * 
 * @author Mitchell
 *
 */

public abstract class Client {

	/** The id of this client */
	final protected long id;
	
	/** The socket that the client connected on */
	final protected Socket socket;
	
	/** The list of change listeners registered for this client */
	private List<ClientChangeListener> changeListeners;
	
	/** Creates a new client with the given id and socket
	 * 
	 * @param id The ID of the client
	 * @param socket The socket through which the client connected
	 */
	
	public Client(long id, Socket socket) {
		this.id = id;
		this.socket = socket;
		changeListeners = new ArrayList<ClientChangeListener>();
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
	
	/** Returns the socket that this client is connected on
	 */
	
	public Socket getSocket() {
		return socket;
	}
	
/** Adds the given client change listener */
	
	public void addChangeListener(ClientChangeListener listener) {
		changeListeners.add(listener);
	}
	
	/** Removes the given client change listener */
	
	public void removeChangeListener(ClientChangeListener listener) {
		changeListeners.remove(listener);
	}
	
	/** Notifies all of the listeners that a change has been made 
	 * 
	 */
	
	protected void notifyChangeListeners() {
		for (ClientChangeListener listener : changeListeners) {
			listener.clientChanged(this);
		}
	}
	
	
}
