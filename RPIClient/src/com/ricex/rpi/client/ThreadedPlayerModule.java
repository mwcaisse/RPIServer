package com.ricex.rpi.client;

import com.ricex.rpi.common.PlayerModule;
import com.ricex.rpi.common.RPIStatus;
import com.ricex.rpi.common.StatusMessage;

/**
 *  The player module for RPIClient
 *  This will run on the Raspberry Pi itself, and is the controller around the omx player
 *  It interacts with the omx process, and can issue commands to the omxplayer process by using
 *  the output stream on the process.
 *  
 *  Only one video is allowed to be played at a time.
 * 
 * @author Mitchell
 * 
 */

public class ThreadedPlayerModule implements PlayerModule, PlayerCompleteListener {

	/** The singleton instance of this class */
	private static ThreadedPlayerModule _instance;
	
	/** The player that this class uses */
	private Player player;

	/** The base directory for the movies */
	private final String baseDir;
	
	/** The base of the command for running the movies */
	private final String baseCommand;
	
	/** The status of this player module */
	private RPIStatus status;
	
	/** The string representing the currently playing file */
	private String filePlaying;	
	
	/** The handler for communicating with the server */
	private ServerHandler handler;

	/** Creates a new ThreadedPlayerModule with the given ServerHandler */
	
	public ThreadedPlayerModule(ServerHandler handler) {
		this.handler = handler;
		baseDir = RPIClientProperties.getInstance().getBaseDir();
		baseCommand = RPIClientProperties.getInstance().getBaseCommand();
		filePlaying = "";
	}
	
	protected synchronized void updateStatus(RPIStatus newStatus) {
		status = newStatus;
		//send the updatd status to the server
		handler.sendMessage(new StatusMessage(status));
	}	

	/**
	 * Plays the given video, using the path from the /mnt/video directory,
	 * Plays the video in a new thread, so the server can continue executing
	 * 
	 * @param videoPath
	 */

	public void play(String videoPath) {
		filePlaying = videoPath;
		System.out.println("We are starting the video: " + videoPath);
		String command = baseCommand + " " + baseDir + videoPath.trim();
	
		//create and run the thread
		//stop the currently running video before we decide to start a new one
		stop();
		player = new Player(command);
		player.start();
		player.addListener(this);
		
		//update the status
		updateStatus(new RPIStatus(RPIStatus.PLAYING, filePlaying));
		
	}
	
	/** Stops the currently playing video 
	 * 
	 */
	
	public void stop() {
		if (player != null) {
			player.writeToProcess("q");
			player = null;		
			updateStatus(new RPIStatus(RPIStatus.IDLE));
		}
	}
	
	/** Pause / resumes the currently playing video
	 * 
	 */
	
	public void pause() {
		if (player != null && player.isPlaying()) {
			player.writeToProcess("p");
			
			//update the status
			if (status.getStatus() == RPIStatus.PAUSED) {
				updateStatus(new RPIStatus(RPIStatus.PLAYING, filePlaying));
			}			
			else {
				updateStatus(new RPIStatus(RPIStatus.PAUSED, filePlaying));
			}	
		}
	}
	
	/** Seeks the video to the next chapter */
	
	public void nextChapter() {
		if (player != null && player.isPlaying()) {
			player.writeToProcess("o");
		}
	}
	
	/** Seeks the video to the previous chapter */
	
	public void previousChapter() {
		if (player != null && player.isPlaying()) {
			player.writeToProcess("i");
		}
	}
	
	/** Turns the volume up */
	
	public void volumeUp() {
		if (player != null && player.isPlaying()) {
			player.writeToProcess("+");
		}
	}
	
	/** Turns the volume down */
	
	public void volumeDown() {
		if (player != null && player.isPlaying()) {
			player.writeToProcess("-");
		}
	}
	
	/** Constants for the key codes for seeking */
	
	private final int KEY_LEFT = 0x5b44;
	private final int KEY_RIGHT = 0x5b43;
	private final int KEY_UP = 0x5b41;
	private final int KEY_DOWN = 0x5b42;
	
	/** Seek forward 30 seconds */
	
	public void seekForwardSlow() {
		//RIGHT ARROW
		if (player != null && player.isPlaying()) {
			//player.writeToProcess(KEY_RIGHT);
			player.writeToProcess("7");
		}
	}
	
	/** Seek forward 600 seconds */
	
	public void seekForwardFast() {
		//UP ARROW
		if (player != null && player.isPlaying()) {
			//player.writeToProcess(KEY_UP);
			player.writeToProcess("8");
		}
	}
	
	/** Seek backwards 30 seconds */
	
	public void seekBackwardSlow() { 
		//LEFT ARROW
		if (player != null && player.isPlaying()) {
			//player.writeToProcess(KEY_LEFT);
			player.writeToProcess("6");
		}		
	}
	
	/** Seek backwards 600 seconds */
	
	public void seekBackwardFast() {
		//DOWN ARROW
		if (player != null && player.isPlaying()) {
			//player.writeToProcess(KEY_DOWN);
			player.writeToProcess("9");
		}
	}

	/** Returns the status of this player module */
	
	public RPIStatus getStatus() {	
		return status;
	}
	
	public String getFilePlaying() {
		return filePlaying;
	}

	/**
	 * {@inheritDoc}
	 */
	
	public synchronized void notifyComplete() {
		//the player reported that it is done playing
		player = null;		
		updateStatus(new RPIStatus(RPIStatus.IDLE));
	}


}
