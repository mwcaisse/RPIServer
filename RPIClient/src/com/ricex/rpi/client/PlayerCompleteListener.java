package com.ricex.rpi.client;

/** Listeners to the player thread, to be notified when the player has finished player
 * 
 * @author Mitchell
 *
 */

public interface PlayerCompleteListener {

	/** Called when the player has finished playing
	 * 
	 */
	
	public void notifyComplete();	                               
	                            
}
