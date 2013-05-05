package com.ricex.rpi.server;

import java.net.Socket;
import java.util.List;

import com.ricex.rpi.common.IMessage;
import com.ricex.rpi.common.RPIStatus;

/** A client that is connected to the server
 * 
 * @author Mitchell
 *
 */

//TODO: Add a name to the client
//TODO: Possibly turn this into an interface when we have multiple versions of the client

public class Client {
	
	/** The id of this client */
	private long id;
	
	/** This Clients socket */
	private Socket socket;
	
	/** The client handler for this client */
	private ClientHandler handler;
	
	/** The thread that this client is executing in */
	private Thread clientThread;
	
	/** The status of this client */
	private RPIStatus status;
	
	/** Indicates wether this client is still connected or not */
	private boolean connected = false;
	
	/** The list of change listeners registered for this client */
	private List<ClientChangeListener> changeListeners;
	
	public Client (long id, Socket socket) {
		this.socket = socket;
		this.handler = new ClientHandler(this);		
		
		connected = true;
		
		clientThread = new Thread(handler);
		clientThread.start();
		
		status = new RPIStatus(RPIStatus.IDLE);
		
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
	
	/** Returns if the client is currently connected or not */
	
	public boolean isConnected() {
		return connected;
	}
	
	/** Returns the status of this client */
	
	public RPIStatus getStatus() {
		return status;
	}
	
	/** Sets the status of this client to the given status */
	
	public void setStatus(RPIStatus status) {
		this.status = status;
		notiftyChangeListeners(); //notify listeners that the status has been changed
	}
	
	/** Sets the connected value of this client */
	
	protected void setConnected(boolean connected) {
		this.connected = connected;
	}
	
	/** Sends a message to this client
	 * 
	 * @param message The message to send
	 * @return Whether the send was sucessful or not
	 */
	
	public boolean sendMessage(IMessage message) {
		System.out.println("Sending message to client: " + message);
		return handler.sendMessage(message);
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
	
	private void notiftyChangeListeners() {
		for (ClientChangeListener listener : changeListeners) {
			listener.clientChanged(this);
		}
	}
}
