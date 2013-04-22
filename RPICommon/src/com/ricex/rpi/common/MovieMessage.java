package com.ricex.rpi.common;


/** Message used to start playing a given movie
 * 
 * @author Mitchell
 *
 */
public class MovieMessage implements IMessage {
	
	public enum MovieCommand {
		PLAY, STOP;
	}
	
	/** The path to the video to be played */
	private String moviePath;
	
	/** Weather this movie will be played or stopped */
	private MovieCommand command;
	
	public MovieMessage(String moviePath, MovieCommand command) {
		this.moviePath = moviePath;
		this.command = command;
	}
	
	public String getMoviePath() {
		return moviePath;
	}
	
	public MovieCommand getCommand() {
		return command;
	}
}
