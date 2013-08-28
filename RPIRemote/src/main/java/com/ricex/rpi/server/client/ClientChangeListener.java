package com.ricex.rpi.server.client;



public interface ClientChangeListener<T extends Client> {
	
	/** Notifies the listener that a change has taken place on the client
	 * 
	 * @param changeEvent The event representing the change
	 */
	
	public void clientChanged(ClientChangeEvent<T> changeEvent);
}
