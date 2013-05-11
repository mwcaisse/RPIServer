package com.ricex.rpi.common.message.remote;

import java.io.Serializable;


/** Holder class to send the clients over the network */

public class RemoteClient implements Serializable {

	/** The id of the client */
	private long id;
	
	/** the Name of the client */
	private String name;
	
	/** Creates a new client with the given id and name */
	public RemoteClient(long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	/** Returns the id of this client */
	public long getId() {
		return id;
	}
	
	/** Returns the name of this client */
	public String getName() {
		return name;
	}
}