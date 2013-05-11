package com.ricex.rpi.common.message;

/** A message used by the server to give a client a specified name
 * 
 * @author Mitchell
 *
 */

public class NameMessage implements IMessage {

	/** The name of the client */
	private String name;
	
	/** Creates a new NameMessage with the given name */
	public NameMessage(String name) {
		this.name = name;
	}
		
	/** Retreives the name in this message */
	public String getName() {
		return name;
	}
}
