package com.ricex.rpi.client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

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

public class PlayerModule {

	/** The singleton instance of this class */
	private static PlayerModule _instance;
	
	/** The player that this class uses */
	private Player player;

	/** Gets the singleton instance of this class */
	public static PlayerModule getInstance() {
		if (_instance == null) {
			_instance = new PlayerModule();
		}
		return _instance;
	}

	private PlayerModule() {

	}

	/**
	 * Plays the given video, using the path from the /mnt/video directory,
	 * Plays the video in a new thread, so the server can continue executing
	 * 
	 * @param videoPath
	 */

	public void playVideo(String videoPath) {
		//String command = "/home/mitchell/play.sh /mnt/videos/" + videoPath.trim();
		String command = "omxplayer -o hdmi /mnt/videos/" + videoPath.trim();
		
		//create and run the thread
		//stop the currently running video before we decide to start a new one
		stop();
		player = new Player(command);
		
	}
	
	/** Stops the currently playing video 
	 * 
	 */
	
	public void stop() {
		if (player != null) {
			player.writeToProcess("q");
			player = null;
		}
	}
	
	/** Pause / resumes the currently playing video
	 * 
	 */
	
	public void pause() {
		if (player != null && player.isPlaying()) {
			player.writeToProcess("p");
		}
	}
}
