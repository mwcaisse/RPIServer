package com.ricex.rpi.common.message.remote;

import com.ricex.rpi.common.RPIStatus;
import com.ricex.rpi.common.message.IMessage;


public class ClientUpdateMessage implements IMessage {

	/** Id of the client */
	private long id;
	
	/** Name of the client */
	private String name;

	/** Whether or not the client connected or disconnected */
	private boolean connected;
	
	/** The current status of the client */
	private RPIStatus status;
	
	/** Creates a new ClientUpdateMessage 
	 * 
	 * @param id The id of hte client
	 * @param name The name of the client
	 * @param connected Whether or not the client connected
	 */
	public ClientUpdateMessage(long id, String name, boolean connected) {
		this.id = id;
		this.name = name;
		this.connected = connected; 
	}
	
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	
	/**
	 * @return is connected
	 */
	public boolean isConnected() {
		return connected;
	}
	
}
