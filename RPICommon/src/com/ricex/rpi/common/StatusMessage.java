package com.ricex.rpi.common;


public class StatusMessage implements IMessage {

	/** The status of the given client */
	private RPIStatus status;	

	
	public StatusMessage(RPIStatus status) {
		this.status = status;
	}
	
	/** Returns the status embodied in this message */
	
	public RPIStatus getStatus() {
		return status;
	}
}
