package com.ricex.rpi.remote;


/** Listens for changes from the RPI server
 * 
 * @author Mitchell Caisse
 */


public interface RPIPlayerChangeListener {
	
	/** Notifies the listener that a change has taken place on the player
	 * 
	 * @param changeEvent The event representing the change
	 */
	
	public void playerChanged(RPIPlayerChangeEvent changeEvent);
}
