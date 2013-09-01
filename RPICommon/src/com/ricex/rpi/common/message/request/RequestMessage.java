package com.ricex.rpi.common.message.request;

import com.ricex.rpi.common.message.IMessage;

/** The basis of a request message
 * 
 * @author Mitchell Caisse
 *
 */

public abstract class RequestMessage implements IMessage {

	private static final long serialVersionUID = -1051008839071759529L;	
	
	/** The id of the request, should be the same as the responseId */
	protected long requestId;
	
}
