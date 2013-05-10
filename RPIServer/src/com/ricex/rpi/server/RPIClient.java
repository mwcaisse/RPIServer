package com.ricex.rpi.server;

import java.io.IOException;
import java.net.Socket;

import com.ricex.rpi.common.IMessage;
import com.ricex.rpi.common.RPIStatus;

/** A client that is connected to the server
 * 
 * @author Mitchell
 *
 */

//TODO: Add a name to the client
//TODO: Possibly turn this into an interface when we have multiple versions of the client
//TODO: Possibly add a persistant storage of clients, for use in GUI ClientTableView

public class RPIClient extends Client {
	
	/** The name of this client */
	private String name;
	
	/** The client handler for this client */
	private RPIClientHandler handler;
	
	/** The thread that this client is executing in */
	private Thread clientThread;
	
	/** The status of this client */
	private RPIStatus status;
	
	public RPIClient (long id, Socket socket) {
		super(id, socket);
		name = "Unnamed Client " + id;
		handler = new RPIClientHandler(this);
		
		clientThread = new Thread(handler);
		clientThread.start();
		
		status = new RPIStatus(RPIStatus.IDLE);		
		
	}
	
	/** Closes the clients connection, and cleans up resources */
	
	public void close() {
		try {
			super.close();
			handler.close();
		}
		catch (IOException e) {		
		}
	}
	
	/** Returns the status of this client */
	
	public RPIStatus getStatus() {
		return status;
	}
	
	/** Sets the status of this client to the given status */
	
	public void setStatus(RPIStatus status) {
		this.status = status;
		notifyChangeListeners(); //notify listeners that the status has been changed
	}
	
	/** Returns the name of this client */
	
	public String getName() {
		return name;
	}
	
	/** Sets the name of the client to the given string */
	
	public void setName(String name) {
		this.name = name;
		notifyChangeListeners();
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
}
