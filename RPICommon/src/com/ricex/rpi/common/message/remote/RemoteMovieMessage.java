package com.ricex.rpi.common.message.remote;

import com.ricex.rpi.common.Playlist;
import com.ricex.rpi.common.message.IMessage;
import com.ricex.rpi.common.message.MovieMessage.Command;

/** A movie message send by the remote, that includes the ID of the client to send the message
 * to
 * @author Mitchell Caisse
 *
 */

public class RemoteMovieMessage implements IMessage {

	/** The movie command to execute */
	private Command command;
	
	/** The id of the client to send the message to */
	private long clientId;
	
	/** The playlist to play */
	private Playlist playlist;
	
	/** Creates a new RemoteMovieMessage with the given command and client id */
	
	public RemoteMovieMessage(Command command, long clientId) {
		this.command = command;
		this.clientId = clientId;
	}
	
	/** Creates a new RemoteMovieMessage with the given moviePath, command and client id */
	public RemoteMovieMessage(Playlist playlist, Command command, long clientId) {
		this(command,clientId);
		this.playlist = playlist;
	}
	
	/** Returns the id of the client to send the message to */
	
	public long getClientId() {
		return clientId;
	}
	
	/**
	 * @return The movie command
	 */
	
	public Command getCommand() {
		return command;
	}
	
	/**
	 * 
	 * @return The playlist to play
	 */
	
	public Playlist getPlaylist() {
		return playlist;
	}

}
