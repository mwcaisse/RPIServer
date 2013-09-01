package com.ricex.rpi.remote.client.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ricex.rpi.common.message.IMessage;
import com.ricex.rpi.common.message.NameMessage;
import com.ricex.rpi.common.message.QuitMessage;
import com.ricex.rpi.common.message.update.StatusMessage;
import com.ricex.rpi.remote.client.RPIClient;

public class RPIClientHandler extends ClientHandler<RPIClient> {
	
	private static final Logger log = LoggerFactory.getLogger(RPIClientHandler.class);

	
	/** Creates a new RPIClientHandler with the given client
	 * 
	 * @param rpiClient
	 */
	public RPIClientHandler(RPIClient client) {
		super(client);

	}
	
	/** {@inheritDoc}
	 */
	
	protected void processMessage(IMessage msg) {
		if (msg instanceof StatusMessage) {
			StatusMessage smsg = (StatusMessage) msg;
			client.setStatus(smsg.getStatus());
			log.debug("Receive status message from client {}", smsg.getStatus());
		}
		else if (msg instanceof NameMessage) {
			NameMessage nmsg = (NameMessage) msg;
			client.setName(nmsg.getName());
			log.debug("Receive name message from client {}", nmsg.getName());
		}
		else if (msg instanceof QuitMessage) {
			client.setConnected(false); // client is disconnecting
		}
		else {
			log.debug("Other message received from client {}", msg);
		}
	}
}
