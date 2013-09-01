package com.ricex.rpi.server.client.handler;

import com.ricex.rpi.common.message.IMessage;
import com.ricex.rpi.common.message.MovieMessage;
import com.ricex.rpi.common.message.QuitMessage;
import com.ricex.rpi.common.message.request.DirectoryRequestMessage;
import com.ricex.rpi.common.message.request.StatusRequestMessage;
import com.ricex.rpi.server.client.RemoteClient;


public class RemoteClientHandler extends ClientHandler<RemoteClient> {
	
	/** Creates a new Remote Client handler */
	
	public RemoteClientHandler(RemoteClient client) {
		super(client);
	}
	
	/** 
	 * {@inheritDoc}
	 */

	protected void processMessage(IMessage message) {
		
		//listen to messages receive from the Remotes..
		
		if (message instanceof MovieMessage) {
			MovieMessage movieMessage = (MovieMessage) message;
			//handle the movie message
		}
		else if (message instanceof StatusRequestMessage) {
			
		}
		else if (message instanceof DirectoryRequestMessage) {
			
		}	
		else if (message instanceof QuitMessage) {
			client.setConnected(false); // client is disconnecting
		}
	}

}
