package com.ricex.rpi.client;

import java.io.IOException;

/**
 * The Player Module for the RPI Client
 * 
 * Runs in its own thread, and is responsible for playing the videos
 * 
 * @author Mitchell
 * 
 */

public class PlayerModule {

	/** The singleton instance of this class */
	private static PlayerModule _instance;
	
	/** The thread that we play the video in */
	private Thread playerThread;
	
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

	public synchronized void playVideo(String videoPath) {
		String command = "/home/mitchell/play.sh /mnt/videos/" + videoPath.trim();
		
		//create and run the thread
		//stop the currently running video before we decide to start a new one
		stopVideo();
		playerThread = new Thread(new Player(command));
		playerThread.start();
	}
	
	/** Stops the currently playing video 
	 * 
	 */
	
	public synchronized void stopVideo() {
		if (playerThread != null) {
			//there is a thread running
			playerThread.interrupt();
			playerThread = null;
		}
	}

	
	private class Player implements Runnable {
	
		/** The command to execute */
		private String command;
		
		private  Player(String command) {
			this.command = command;
		}

		public void run() {
			Process movieProcess = null;
			try {
				//create a process and play the video, we shall wait for the process to be over.
				movieProcess = Runtime.getRuntime().exec(command);
				movieProcess.waitFor();
			}
			catch (IOException e) {
				System.out.println("Error playing video: " + command);
				e.printStackTrace();
			}
			catch (InterruptedException e) {
				System.out.println("We were interupted, stopping the process");
				if (movieProcess != null) {
					movieProcess.destroy();
				}
			}
		}
	}


}
