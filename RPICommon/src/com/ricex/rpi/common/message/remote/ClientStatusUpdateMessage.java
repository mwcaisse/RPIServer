package com.ricex.rpi.common.message.remote;

import com.ricex.rpi.common.RPIStatus;
import com.ricex.rpi.common.message.StatusMessage;


public class ClientStatusUpdateMessage extends StatusMessage {

	/** The id of the client whose status changed */
	private long clientId;
	
	public ClientStatusUpdateMessage(long clientId, RPIStatus status) {
		super(status);
		this.clientId = clientId;
	}
	
	/** Returns the id of the client */
	public long getId() {
		return clientId;
	}
	                   

}
