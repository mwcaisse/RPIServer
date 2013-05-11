package com.ricex.rpi.common.message;


public class StringMessage implements IMessage {

	/** The message in this string message */
	private String message;
	
	public StringMessage(String message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
		return message;
	}
}
