package com.ricex.rpi.common.message;

/** A movie message send by the remote, that includes the ID of the client to send the message
 * to
 * @author Mitchell
 *
 */

public class RemoteMovieMessage extends MovieMessage {

	/** The id of the client to send the message to */
	private long clientId;
	
	/** Creates a new RemoteMovieMessage with the given command and client id */
	
	public RemoteMovieMessage(Command command, long clientId) {
		this("", command, clientId);
	}
	
	/** Creates a new RemoteMovieMessage with the given moviePath, command and client id */
	public RemoteMovieMessage(String moviePath, Command command, long clientId) {
		super(moviePath, command);
		this.clientId = clientId;
	}

}
