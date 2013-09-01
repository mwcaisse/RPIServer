package com.ricex.rpi.server.player;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import com.ricex.rpi.server.RPIServerProperties;

/** The wrapper around the thread that will run the omx process
 * 
 * @author Mitchell Caisse
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
	
	/** The base directory for the movies */
	private final String baseDir;
	
	/** The base of the command for running the movies */
	private final String baseCommand;
	
	/** The movie file that is being played */
	private String movieFile;
	
	/** Create the player module. */	
	public  Player() {
		playing = false;
		movieProcess = null;
		listeners = new ArrayList<PlayerCompleteListener>();
		
		baseDir = RPIServerProperties.getInstance().getBaseDir();
		baseCommand = RPIServerProperties.getInstance().getBaseCommand();
		
	}
	
	/** Sets the command of the player */
	
	public void setMovieFile(String movieFile) {
		this.movieFile = movieFile;
		this.command = baseCommand + " " + baseDir + movieFile.trim();
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
	
	/** If the player is currently playing a video, stops it, otherwise it does nothing
	 *  Will wait for the process to stop playing before returning.
	 * 
	 */
	
	public boolean stop() {
		if (playing) {	
			System.out.println("We are about to write to the process");
			writeToProcess("q");	
			return true;
		}
		return false;
	}

	public void run() {
		try {
			playing = true;
			
			//create a process and play the video, we shall wait for the process to be over.
			movieProcess = Runtime.getRuntime().exec(command);
			
			//create the output stream
			out = new BufferedWriter(new OutputStreamWriter(movieProcess.getOutputStream()));
			
			//wait for the process to finish executing
			System.out.println("We are waiting for the movie process");
			movieProcess.waitFor();
			System.out.println("Movie process is done");
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
		System.out.println("Player thread is done executing");
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
			System.out.println("We are writing to the process");
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
		System.out.println("We are notifying the listeners");
		for (PlayerCompleteListener listener : listeners) {
			listener.notifyComplete();
		}
	}
}
