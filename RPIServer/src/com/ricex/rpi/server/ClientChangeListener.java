package com.ricex.rpi.server;


public interface ClientChangeListener {
	
	/** Notifies the listener that a chage has been made in the client,
	 * ussualy a status change
	 * @param client The client the change occured on
	 */
	public void clientChanged(Client client);
}
