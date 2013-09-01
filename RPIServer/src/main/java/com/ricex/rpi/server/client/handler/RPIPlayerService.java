package com.ricex.rpi.server.client.handler;

import com.ricex.rpi.common.RPIStatus;
import com.ricex.rpi.common.video.Video;

/** Service that will control updating and retrieving data about the RPI player
 * 
 * @author Mitchell Caisse
 *
 */

public class RPIPlayerService {

	
	/** The status of the RPI player */
	protected RPIStatus playerStatus;
	
	/** The directory listing of the RPI player */
	protected Video directoryListing;
	
	/** Creates a new instance of the RPIPlayerService
	 * 
	 */
	
	public RPIPlayerService() {
		
	}
	
	
	/** Returns the status of the RPI Player
	 * 
	 * @return The status
	 */
	
	public RPIStatus getStatus() {
		return playerStatus;
	}
	
	/** Returns the directory listing of the RPI Player
	 * 
	 * @return The directory listing
	 */
	
	public Video getDirectoryListing() {
		return directoryListing;
	}
	
	
}
