package com.ricex.rpi.remote.android.network;

/** Interface used to notify classes when the server connection status changes
 * 
 * TODO: Possibly implement a status class rather than just a boolean.
 */

public interface ServerConnectionListener {

	/** Called when there is a change in the connection state of the server
	 * 
	 * @param connected True if the server is now connected, false if disconnected
	 */

	public void serverConnectionChanged(boolean connected);

}
