package com.ricex.rpi.server.client;



public interface ClientChangeListener<T extends Client> {
	
	/** Notifies the listener that a chage has been made in the client,
	 * @param client The client the change occured on
	 */
	public void clientChanged(T client);
}
