package com.ricex.rpi.common;



/** Message used to start playing a given movie
 * 
 * @author Mitchell
 *
 */
public class MovieMessage implements IMessage {
	
	public enum Command {
		PLAY,  //START PLAYING
		PAUSE, //PAUSE/RESUME PLAYING
		SEEK_FORWARD_SLOW, // SEEK FORWARD 30 SECONDS
		SEEK_FORWARD_FAST, // SEEK FORWARD 600 SECONDS
		SEEK_BACKWARD_SLOW, // SEEK BACKWARD 30 SECONDS
		SEEK_BACKWARD_FAST, // SEEK BACKWARD 600 SECONDS
		NEXT_CHAPER, // MOVE TO THE NEXT CHAPTER
		PREVIOUS_CHAPTER, // MOVE TO THE PREVIOUS CHAPTER
		VOLUME_UP, // TURN VOLUME UP
		VOLUME_DOWN, // TURN VOLUME DOWN
		STOP;  // STOP PLAYING
	}
	
	/** The path to the video to be played */
	private String moviePath;
	
	/** Weather this movie will be played or stopped */
	private Command command;
	
	public MovieMessage(Command command) {
		this.command = command;
		moviePath = "";
	}
	
	public MovieMessage(String moviePath, Command command) {
		this.moviePath = moviePath;
		this.command = command;
	}
	
	/** Executes this message using the given player module
	 * @param playerModule The player module to use to play the movie
	 */
	
	public void execute(PlayerModule playerModule) {
		switch (command) {
		case PAUSE:
			playerModule.pause();
			break;
		case PLAY:
			playerModule.play(moviePath);
			break;
		case STOP:
			playerModule.stop();
			break;
		case NEXT_CHAPER:
			playerModule.nextChapter();
			break;
		case PREVIOUS_CHAPTER:
			playerModule.previousChapter();
			break;
		case SEEK_BACKWARD_FAST:
			playerModule.seekBackwardFast();
			break;
		case SEEK_BACKWARD_SLOW:
			playerModule.seekBackwardSlow();
			break;
		case SEEK_FORWARD_FAST:
			playerModule.seekForwardFast();
			break;
		case SEEK_FORWARD_SLOW:
			playerModule.seekForwardSlow();
			break;
		case VOLUME_DOWN:
			playerModule.volumeDown();
			break;
		case VOLUME_UP:
			playerModule.volumeUp();
			break;
		default:
			System.out.println("UNSUPPORTED OPERATION");
			break;		
		}	
	}
	
	public String getMoviePath() {
		return moviePath;
	}
	
	public Command getCommand() {
		return command;
	}
}
