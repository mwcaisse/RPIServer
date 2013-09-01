package com.ricex.rpi.common.message.update;

import com.ricex.rpi.common.RPIStatus;

/** Update message containing the current status
 * 
 * @author Mitchell Caisse
 *
 */

public class StatusMessage extends UpdateMessage {

	/** The status of the RPI player */
	private RPIStatus status;	

	/** Creates a new StatusMessae with the given status
	 * 
	 * @param status
	 */
	
	public StatusMessage(RPIStatus status) {
		this.status = status;
	}
	
	/** Returns the status embodied in this message */
	
	public RPIStatus getStatus() {
		return status;
	}
}
