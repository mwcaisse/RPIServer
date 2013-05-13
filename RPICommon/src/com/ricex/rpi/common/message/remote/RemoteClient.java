package com.ricex.rpi.common.message.remote;

import java.io.Serializable;

import com.ricex.rpi.common.RPIStatus;


/** Holder class to send the clients over the network */

public class RemoteClient implements Serializable {

	/** The id of the client */
	private final long id;
	
	/** the Name of the client */
	private final String name;
	
	/** The status of the given client */
	private final RPIStatus status;
	
	/** Whether or not this client is enabled */
	private boolean enabled;
	
	/** Creates a new client with the given id and name */
	public RemoteClient(long id, String name, RPIStatus status) {
		this.id = id;
		this.name = name;
		this.status = status;
		enabled = false;
	}
	
	/** Returns the id of this client */
	public long getId() {
		return id;
	}
	
	/** Returns the name of this client */
	public String getName() {
		return name;
	}
	
	/** Returns the status of this client */
	public RPIStatus getStatus() {
		return status;
	}
	
	/** Returns whether or not this client is enabled */
	public boolean isEnabled() {
		return enabled;
	}
	
	/** Enables or disables this client */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	/**
	 * {@inheritDoc}
	 */
	/*
	@Override
	public String toString() {
		return getName();
	}
	*/
	
	
}