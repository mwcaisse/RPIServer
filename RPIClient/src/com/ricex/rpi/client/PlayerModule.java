package com.ricex.rpi.client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

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
		stopVideo();
		player = new Player(command);
		
	}
	
	/** Stops the currently playing video 
	 * 
	 */
	
	public void stopVideo() {
		if (player != null) {
			player.writeToProcess("q");
		}
	}

	//want to be able to send commands to the process
	//wait for the process to finish executing, so we know when it is running...
	//
	
	private class Player implements Runnable {
	
		/** The command to execute */
		private String command;
		
		/** The process that the movie is running in */
		private Process movieProcess;
		
		/** The thread that this playing is running in */
		private Thread playerThread;	
		
		/** whether the thread is running or not, aka a video is being played */
		private boolean playing;
		
		/** The stream into the process */
		private BufferedWriter out;
		
		private  Player(String command) {
			playing = false;
			this.command = command;
			movieProcess = null;
			playerThread = new Thread(this);
			playerThread.start();
		}

		public void run() {
			try {
				playing = true;
				
				//create a process and play the video, we shall wait for the process to be over.
				movieProcess = Runtime.getRuntime().exec(command);
				
				//create the output stream
				out = new BufferedWriter(new OutputStreamWriter(movieProcess.getOutputStream()));
				
				//wait for the process to finish executing
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
			playing = false;
		}	
		
		public boolean isPlaying() {
			return playing;
		}
		
		/** Writes the given string to the process then flushes the stream
		 * 
		 * @param str The string to write
		 * @return true of false depending on the sucess of the write.
		 */
		
		public synchronized boolean writeToProcess(String str) {
			if (!isPlaying()) {
				return false; // there is nothing playing, we cant send commands
			}
			//the video is playing we can send the commands 
			try {
				out.write(str);
				out.flush();
			}
			catch (IOException e) {
				System.out.println("Error writing to process");
				return false;
			}			
			return true;
		}
	
	}


}
