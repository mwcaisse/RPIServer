package com.ricex.rpi.server.player;

import com.ricex.rpi.server.client.RPIClient;

/** Listener to notify when the currently active client changes 
 * 
 * @author Mitchell
 *
 */

public interface ActiveClientListener {

	/** Called when the active client has been changed
	 * 
	 * @param activeClient The new active client
	 */
	
	public void activeClientChanged(RPIClient activeClient);
	
	/** Called when the active client has been changed, and there is no new active client
	 * 
	 */
	
	public void activeClientRemoved();
}
