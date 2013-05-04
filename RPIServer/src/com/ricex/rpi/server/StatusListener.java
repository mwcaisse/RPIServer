package com.ricex.rpi.server;

import com.ricex.rpi.common.RPIStatus;

/** Listens for changes in the status of the RPI CLients */

public interface StatusListener {

	
	/** The status changed, to the given status */
	public void statusChanged(RPIStatus status, String filePlaying);
}
