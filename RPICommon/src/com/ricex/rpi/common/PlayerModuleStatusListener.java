package com.ricex.rpi.common;

import com.ricex.rpi.common.PlayerModule;
import com.ricex.rpi.common.RPIStatus;

/** Listens for updates to the status of a PlayerModule
 * 
 * @author Mitchell Caisse
 *
 */

public interface PlayerModuleStatusListener {

	/** Called when the status of the specified player module has been changed
	 * 
	 * @param playerModule The player module that status changed
	 * @param status The new status 
	 */
	
	public void statusUpdated(PlayerModule playerModule, RPIStatus status);
}
