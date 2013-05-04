package com.ricex.rpi.common;


public class StatusMessage implements IMessage {

	/** The status of the given client */
	private RPIStatus status;
	
	/** The file that is being played, if status is PLAYING or PAUSED */
	private String filePlaying;
	
	public StatusMessage(RPIStatus status, String filePlaying) {
		this.status = status;
		this.filePlaying = filePlaying;
	}
	
	/** Returns the status embodied in this message */
	
	public RPIStatus getStatus() {
		return status;
	}
	
	/** Returns the currently playing file */
	
	public String getFilePlaying() {
		return filePlaying;
	}
}
