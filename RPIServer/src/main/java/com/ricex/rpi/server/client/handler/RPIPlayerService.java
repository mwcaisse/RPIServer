package com.ricex.rpi.server.client.handler;

import com.ricex.rpi.common.RPIStatus;
import com.ricex.rpi.common.message.IMessage;
import com.ricex.rpi.common.message.update.DirectoryMessage;
import com.ricex.rpi.common.message.update.StatusMessage;
import com.ricex.rpi.common.video.Video;
import com.ricex.rpi.server.Server;

/** Service that will interface between the Server side and player side
 * 
 * @author Mitchell Caisse
 *
 */

public class RPIPlayerService {

	/** The server that this interfaces between */
	protected Server<?> server;
	
	/** The status of the RPI player */
	protected RPIStatus playerStatus;
	
	/** The directory listing of the RPI player */
	protected Video directoryListing;
	
	/** Creates a new instance of the RPIPlayerService with the specified server
	 * 
	 * @param server The server to interface with
	 */
	
	public RPIPlayerService(Server<?> server) {
		this.server = server;
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
	
	/** Updates the RPI Status
	 * 
	 *  Updates the local copy as well as sends update messages to all connected remotes
	 * 
	 * @param status The new status
	 */
	
	public void updateStatus(RPIStatus status) {
		playerStatus = status;
		updateClients(new StatusMessage(status));
	}
	
	/** Updates the Directory Listing
	 * 
	 *  Updates the local copy as well as sends update messages to all connected remotes
	 *  
	 * @param directoryListing the new directory listing
	 */
	
	public void updateDirectoryListing(Video directoryListing) {
		this.directoryListing = directoryListing;
		updateClients(new DirectoryMessage(directoryListing));
	}
	
	/** Sends the given message to the clients to update them of any changes
	 * 
	 * @param msg The message to send
	 */
	
	protected void updateClients(IMessage msg) {
		server.sendToAllClients(msg);
	}
	
	
}
