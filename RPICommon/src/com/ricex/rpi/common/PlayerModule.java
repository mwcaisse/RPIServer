package com.ricex.rpi.common;

/** Interface for a PlayerModule, that will controll playing and pausing movies / videos
 * 
 * @author Mitchell
 *
 */

public interface PlayerModule {

	/** Starts playing the movie with the given path
	 * 
	 * @param videoPath Path to the movie to play
	 */
	
	public void play(String videoPath);
	
	/** Stops the currently playing movie */
	
	public void stop();
	
	/** Pauses / resumes the currently playing movie
	 * 
	 */
	
	public void pause();
	
	public void nextChapter();
	
	public void previousChapter();
	
	public void volumeUp();
	
	public void volumeDown();
	
	public void seekForwardSlow();
	
	public void seekForwardFast();
	
	public void seekBackwardSlow();
	
	public void seekBackwardFast();
}
