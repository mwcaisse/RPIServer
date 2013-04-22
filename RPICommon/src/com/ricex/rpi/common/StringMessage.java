package com.ricex.rpi.common;


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
