package com.ricex.rpi.common.message.remote;

import com.ricex.rpi.common.message.DirectoryListingMessage;
import com.ricex.rpi.common.video.Video;


public class ClientDirectoryListingMessage extends DirectoryListingMessage {

	/** The id of the client for which the listing is for */
	private long clientId;
	
	public ClientDirectoryListingMessage(long clientId, Video rootDirectory) {
		super(rootDirectory);
		this.clientId = clientId;
	}
	
	/** @retrun The id of the client fo the directory listing */
	
	public long getId() {
		return clientId;
	}
	
}
