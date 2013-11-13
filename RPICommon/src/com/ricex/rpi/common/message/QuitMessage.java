package com.ricex.rpi.common.message;

/** Message send from client to server to notify of disconnect,
 *  or send from server to client to notify the server is stopping
 * 
 * @author Mitchell Caisse
 *
 */

public class QuitMessage implements IMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public QuitMessage() {
		
	}
}
