package com.ricex.rpi.common.message.remote;

import com.ricex.rpi.common.RPIStatus;
import com.ricex.rpi.common.message.IMessage;
import com.ricex.rpi.common.video.Video;


public class ClientUpdateMessage implements IMessage {

	
	/** The name of the client changed */
	public static final int EVENT_NAME_CHANGE = 159;
	
	/** The connection state of the client has changed */
	public static final int EVENT_CONNECTION_CHANGE = EVENT_NAME_CHANGE + 1;
	
	/** The status of the client changed */
	public static final int EVENT_STATUS_CHANGE = EVENT_NAME_CHANGE + 2;
	
	/** The root directory of the client changed */
	public static final int EVENT_ROOT_DIRECTORY_CHANGE = EVENT_NAME_CHANGE + 3;
	
	/** Id of the client */
	private long id;
	
	/** Name of the client */
	private String name;

	/** Whether or not the client connected or disconnected */
	private boolean connected;
	
	/** The current status of the client */
	private RPIStatus status;
	
	/** The current directory listing of the client */
	private Video directoryListing;
	
	/** Creates a new ClientUpdateMessage 
	 * 
	 * @param id The id of the client
	 * @param name The name of the client
	 * @param connected Whether or not the client connected
	 */
	public ClientUpdateMessage(long id, String name, boolean connected, Video directoryListing) {
		this.id = id;
		this.name = name;
		this.connected = connected; 
		this.directoryListing = directoryListing;
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
	
	/**
	 * @return the directory listing
	 */
	
	public Video getDirectoryListing() {
		return directoryListing;
	}
	
}
