package com.ricex.rpi.server.client.handler;

import com.ricex.rpi.common.message.DirectoryListingMessage;
import com.ricex.rpi.common.message.IMessage;
import com.ricex.rpi.common.message.NameMessage;
import com.ricex.rpi.common.message.QuitMessage;
import com.ricex.rpi.common.message.StatusMessage;
import com.ricex.rpi.server.client.RPIClient;

public class RPIClientHandler extends ClientHandler<RPIClient> {
	
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
			System.out.println("Received status message from client: " + smsg.getStatus());
		}
		else if (msg instanceof NameMessage) {
			NameMessage nmsg = (NameMessage) msg;
			client.setName(nmsg.getName());
			System.out.println("Received name message from client: " + nmsg.getName());
		}
		else if (msg instanceof DirectoryListingMessage) {
			DirectoryListingMessage dmsg = (DirectoryListingMessage) msg;
			client.setRootDirectory(dmsg.getRootDirectory());
		}
		else if (msg instanceof QuitMessage) {
			client.setConnected(false); // client is disconnecting
		}
		else {
			System.out.println("Message received from client: " + msg);
		}
	}
}
