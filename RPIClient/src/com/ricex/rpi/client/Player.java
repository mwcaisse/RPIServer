package com.ricex.rpi.client;

import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/** The wrapper around the thread that will run the omx process
 * 
 * @author Mitchell
 *
 */

public class Player implements Runnable {
	
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
	
	/** Listeners tha will be notified when this is done playing */
	private List<PlayerCompleteListener> listeners;
	
	/** Create the player module. */	
	public  Player(String command) {
		playing = false;
		this.command = command;
		movieProcess = null;
		listeners = new ArrayList<PlayerCompleteListener>();
	}
	
	/** Starts run the movie this is setup to play,
	 * If the movie is already playing, this will do nothing

		@return False if the movie is already playing, true otherwise
	*/
	
	public boolean start() {
		if (isPlaying()) {
			//movie was already playing, nothing to do
			return false;
		}
		//the thread isnt currently running, so we will restart the thread
		playerThread = new Thread(this);
		playerThread.start();
		return true;
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
		finally {
			playing = false;
			notifyListeners(); // notify the listeners that we have finished playing
			
		}
		
		
	
		
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
	
	
	/** Writes the given int to the process then flushes the stream
	 * 
	 * @param val The int to write
	 * @return true of false depending on the sucess of the write.
	 */
	
	public synchronized boolean writeToProcess(int val) {
		if (!isPlaying()) {
			return false; // there is nothing playing, we cant send commands
		}
		//the video is playing we can send the commands 
		try {
			out.write(val);
			out.flush();			

		}
		catch (IOException e) {
			System.out.println("Error writing to process");
			return false;
		}			
		return true;
	}
	
	public void writeToProcessTest() {
		if (!isPlaying()) {
			return;
		}
		
		try {	
			System.out.println("WE OUTPUTTING SHIT MOTHERFUCKER");
			movieProcess.getOutputStream().write(KeyEvent.VK_UP);
			movieProcess.getOutputStream().write('\u5b41');
			movieProcess.getOutputStream().write(0x5b41);			
			//maybe i hsould have flushed. shit
			movieProcess.getOutputStream().flush();
		
		}
		catch (IOException e) {
	
		}
	}
	
	
	/** Adds the given listener */
	
	public void addListener(PlayerCompleteListener listener) {
		listeners.add(listener);
	}
	
	/** Removes the given listener */
	
	public void removeListener(PlayerCompleteListener listener) {
		listeners.remove(listener);
	}
	
	/** Notifies all of the listeners that this has finished playing
	 * 
	 */
	
	private void notifyListeners() {
		for (PlayerCompleteListener listener : listeners) {
			listener.notifyComplete();
		}
	}
}
