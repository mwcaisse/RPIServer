package com.ricex.rpi.server;


public interface ClientChangeListener {
	
	/** Notifies the listener that a chage has been made in the client,
	 * ussualy a status change
	 * @param rPIClient The client the change occured on
	 */
	public void clientChanged(RPIClient rPIClient);
}
