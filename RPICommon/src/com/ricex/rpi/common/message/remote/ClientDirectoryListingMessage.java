package com.ricex.rpi.common.message.remote;

import com.ricex.rpi.common.message.IMessage;
import com.ricex.rpi.common.video.Video;


public class ClientDirectoryListingMessage implements IMessage {
	
	/** The id of the client for which the listing is for */
	private long clientId;
	
	/** The root directory of the specified client */
	private Video rootDirectory;
	
	public ClientDirectoryListingMessage(long clientId, Video rootDirectory) {
		this.clientId = clientId;
		this.rootDirectory = rootDirectory;
	}
	
	/** @retrun The id of the client fo the directory listing */
	
	public long getId() {
		return clientId;
	}
	
	/**
	 * @return the root directory 
	 */
	
	public Video getRootDirectory() {
		return rootDirectory;
	}
	
}
