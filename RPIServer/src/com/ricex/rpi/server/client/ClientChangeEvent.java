package com.ricex.rpi.server.client;


public class ClientChangeEvent<T extends Client> {

	/** The name of the client changed */
	public static final int EVENT_NAME_CHANGE = 159;

	/** The status of the client changed */
	public static final int EVENT_STATUS_CHANGE = EVENT_NAME_CHANGE + 1;

	/** The client that triggered the change event */
	private final T source;

	/** The type of event that occured */
	private final int eventType;

	public ClientChangeEvent(T source, int eventType) {
		this.source = source;
		this.eventType = eventType;
	}

	/** Returns the source that triggered the change event */

	public T getSource() {
		return source;
	}

	/** Returns the type of change event */

	public int getEventType() {
		return eventType;
	}
}
