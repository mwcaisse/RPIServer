package com.ricex.rpi.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.ricex.rpi.common.IMessage;
import com.ricex.rpi.common.NameMessage;
import com.ricex.rpi.common.StatusMessage;

public class RPIClientHandler extends ClientHandler {

	/** The client that this handler operations on */
	private RPIClient rpiClient;
	
	/** Creates a new RPIClientHandler with the given client
	 * 
	 * @param rpiClient
	 */
	public RPIClientHandler(RPIClient rpiClient) {
		super(rpiClient);
		this.rpiClient = rpiClient;
	}
	
	/** {@inheritDoc}
	 */
	
	protected void processMessage(IMessage msg) {
		if (msg instanceof StatusMessage) {
			StatusMessage smsg = (StatusMessage) msg;
			rpiClient.setStatus(smsg.getStatus());
			System.out.println("Received status message from client: " + smsg.getStatus());
		}
		else if (msg instanceof NameMessage) {
			NameMessage nmsg = (NameMessage) msg;
			rpiClient.setName(nmsg.getName());
			System.out.println("Received name message from client: " + nmsg.getName());
		}
		else {
			System.out.println("Message received from client: " + msg);
		}
	}
}
