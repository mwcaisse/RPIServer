package com.ricex.rpi.common;

/** Interface for a PlayerModule, that will controll playing and pausing movies / videos
 * 
 * @author Mitchell Caisse
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
	
	/** Pauses / resumes the currently playing movie */
		
	public void pause();
	
	/** Move to the next chapter */
	
	public void nextChapter();
	
	/** Move the previous chapter */
	
	public void previousChapter();
	
	/** Turn the volume up */
	
	public void volumeUp();
	
	/** Turn the volume down */
	
	public void volumeDown();
	
	/** Seek to the right 30 seconds */
	
	public void seekForwardSlow();
	
	/** Seek to the right 600 seconds */
	
	public void seekForwardFast();
	
	/** Seek to the left 30 seconds */
	
	public void seekBackwardSlow();
	
	/** Seek to the right 600 seconds */
	
	public void seekBackwardFast();
	
	/** Returns the status of this player module */
	
	public RPIStatus getStatus();
	
	/** Returns a string representing the file that is being played */
	
	public String getFilePlaying();
	
	/** Adds the given PlayerModuleStatusListener
	 * 
	 * @param listener The listener to add
	 */
	
	public void addPlayerModuleStatusListener(PlayerModuleStatusListener listener);
	
	/** Removes the given PlayerModuleStatusListener
	 * 
	 * @param listener The listener to remove
	 */
	
	public void removePlayerModuleStatusListener(PlayerModuleStatusListener listener);
}
