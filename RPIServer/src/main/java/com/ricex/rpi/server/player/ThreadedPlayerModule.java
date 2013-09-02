package com.ricex.rpi.server.player;

import java.util.ArrayList;
import java.util.List;

import com.ricex.rpi.common.PlayerModule;
import com.ricex.rpi.common.PlayerModuleStatusListener;
import com.ricex.rpi.common.RPIStatus;

/**
 *  The player module for RPIClient
 *  This will run on the Raspberry Pi itself, and is the controller around the omx player
 *  It interacts with the omx process, and can issue commands to the omxplayer process by using
 *  the output stream on the process.
 *  
 *  Only one video is allowed to be played at a time.
 * 
 * @author Mitchell Caisse
 * 
 */

public class ThreadedPlayerModule implements PlayerModule, PlayerCompleteListener {
	
	/** The player that this class uses */
	private Player player;
	
	/** The status of this player module */
	private RPIStatus status;
	
	/** The string representing the currently playing file */
	private String filePlaying;	

	/** Monitor used to stop this thread while it is waiting for movie to complete */
	private Object stopMonitor;
	
	/** Monitor used to lock on status changes */
	private Object statusLock;
	
	/** List of status listeners */
	private List<PlayerModuleStatusListener> listeners;
	
	
	/** Creates a new ThreadedPlayerModule with the given ServerHandler */
	
	public ThreadedPlayerModule() {
		filePlaying = "";
		
		player = new Player();
		player.addListener(this);
		
		stopMonitor = new Object();
		statusLock = new Object();
		
		listeners = new ArrayList<PlayerModuleStatusListener>();
	}
	
	protected void updateStatus(RPIStatus newStatus) {
		System.out.println("UpdateStatus called: " + newStatus);
		synchronized(statusLock) {
			status = newStatus;
		}
		notifyListenersOfStatus(newStatus);
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
	
		//create and run the thread
		//stop the currently running video before we decide to start a new one
		if (player.isPlaying()) {
			stop();
		}
		player.setMovieFile(videoPath);
		player.start();		
		
		//update the status
		updateStatus(new RPIStatus(RPIStatus.PLAYING, filePlaying));
		
	}
	
	/** Stops the currently playing video 
	 * 
	 */
	
	public void stop() {
		if (player.stop()) {	
			System.out.println("Waiting on the stop monitor");
			synchronized(stopMonitor) {
				try {
					System.out.println("Wait...");
					stopMonitor.wait();
				}
				catch (InterruptedException e) {
					//we were interupted, not much to do here really.
					e.printStackTrace();
				}
			}
		}
	}
	
	/** Pause / resumes the currently playing video
	 * 
	 */
	
	public void pause() {
		if (player.isPlaying()) {
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
		if (player.isPlaying()) {
			player.writeToProcess("o");
		}
	}
	
	/** Seeks the video to the previous chapter */
	
	public void previousChapter() {
		if (player.isPlaying()) {
			player.writeToProcess("i");
		}
	}
	
	/** Turns the volume up */
	
	public void volumeUp() {
		if (player.isPlaying()) {
			player.writeToProcess("+");
		}
	}
	
	/** Turns the volume down */
	
	public void volumeDown() {
		if (player.isPlaying()) {
			player.writeToProcess("-");
		}
	}
	
	/** Seek forward 30 seconds */
	
	public void seekForwardSlow() {
		//RIGHT ARROW
		if (player.isPlaying()) {
			//player.writeToProcess(KEY_RIGHT);
			player.writeToProcess("7");
		}
	}
	
	/** Seek forward 600 seconds */
	
	public void seekForwardFast() {
		//UP ARROW
		if (player.isPlaying()) {
			//player.writeToProcess(KEY_UP);
			player.writeToProcess("8");
		}
	}
	
	/** Seek backwards 30 seconds */
	
	public void seekBackwardSlow() { 
		//LEFT ARROW
		if (player.isPlaying()) {
			//player.writeToProcess(KEY_LEFT);
			player.writeToProcess("6");
		}		
	}
	
	/** Seek backwards 600 seconds */
	
	public void seekBackwardFast() {
		//DOWN ARROW
		if (player.isPlaying()) {
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
	
	public void notifyComplete() {	
		System.out.println("Threaded PlayerModule has been notified");
		updateStatus(new RPIStatus(RPIStatus.IDLE));
		
		synchronized(stopMonitor) {
			stopMonitor.notify(); //notify the thread that it is safe to resume
		}
	}
	
	/** Notifies the listeners that the status of the Player has changed
	 * 
	 * @param status The new status
	 */
	
	public void notifyListenersOfStatus(RPIStatus status) {
		for (PlayerModuleStatusListener listener : listeners) {
			listener.statusUpdated(this,  status);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	
	public void addPlayerModuleStatusListener(PlayerModuleStatusListener listener) {
		listeners.add(listener);
	}

	/**
	 * {@inheritDoc}
	 */
	
	public void removePlayerModuleStatusListener(PlayerModuleStatusListener listener) {
		listeners.remove(listener);
	}
}