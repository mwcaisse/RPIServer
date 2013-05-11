package com.ricex.rpi.server.client;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.ricex.rpi.common.IMessage;
import com.ricex.rpi.common.RPIStatus;
import com.ricex.rpi.server.Server;
import com.ricex.rpi.server.client.handler.RPIClientHandler;

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
	
	/** The list of change listeners registered for this client */
	private List<ClientChangeListener<RPIClient>> changeListeners;
	
	public RPIClient (Server<RPIClient> server, long id, Socket socket) {
		super(server, id, socket);
		name = "Unnamed Client " + id;
		handler = new RPIClientHandler(this);
		
		clientThread = new Thread(handler);
		clientThread.start();
		
		status = new RPIStatus(RPIStatus.IDLE);
		
		changeListeners = new ArrayList<ClientChangeListener<RPIClient>>();
		
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
	
	public void setConnected(boolean connected) {
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
	
	/** Adds the given change listener */
	public void addChangeListener(ClientChangeListener<RPIClient> listener) {
		changeListeners.add(listener);
	}
	
	/** Removes the given change listener */	
	public void removeChangeListener(ClientChangeListener<RPIClient> listener) {
		changeListeners.remove(listener);
	}
	
	/** 
	 *  {@inheritDoc}
	 */
	@Override
	protected void notifyChangeListeners() {
		for (ClientChangeListener<RPIClient> listener : changeListeners) {
			listener.clientChanged(this);
		}
	}
}
