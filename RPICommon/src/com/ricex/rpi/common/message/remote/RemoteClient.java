package com.ricex.rpi.common.message.remote;

import java.io.Serializable;

import com.ricex.rpi.common.RPIStatus;
import com.ricex.rpi.common.video.Video;


/** Holder class to send the clients over the network */

public class RemoteClient implements Serializable {

	/** The id of the client */
	private final long id;
	
	/** the Name of the client */
	private final String name;
	
	/** The status of the given client */
	private RPIStatus status;
	
	/** The directory listing for this client */
	private Video directoryListing;
	
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
	
	/** Sets the status of this client to the given status */
	public void setStatus(RPIStatus status) {
		this.status = status;
	}
	
	/** Returns whether or not this client is enabled */
	public boolean isEnabled() {
		return enabled;
	}
	
	/** Enables or disables this client */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	/** @return the root directory of the directory listing for this client
	 */
	
	public Video getDirectoryListing() {
		return directoryListing;
	}
	
	/** Sets the directory listing of this client
	 * 
	 * @param directoryListing The new directory listing
	 */
	
	public void setDirectoryListing(Video directoryListing) {
		this.directoryListing = directoryListing;
	}
	
	
	
}