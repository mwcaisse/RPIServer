package com.ricex.rpi.server.client.handler;

import com.ricex.rpi.common.message.IMessage;
import com.ricex.rpi.common.message.MovieMessage;
import com.ricex.rpi.common.message.QuitMessage;
import com.ricex.rpi.common.message.request.DirectoryRequestMessage;
import com.ricex.rpi.common.message.request.StatusRequestMessage;
import com.ricex.rpi.common.message.update.DirectoryMessage;
import com.ricex.rpi.common.message.update.StatusMessage;
import com.ricex.rpi.server.client.RemoteClient;


public class RemoteClientHandler extends ClientHandler<RemoteClient> {
	
	/** Creates a new Remote Client handler */
	
	/** The player service used to send information to the remote */
	protected RPIPlayerService playerService;
	
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
			//TODO: implement the handling of the MovieMessage
		}
		else if (message instanceof StatusRequestMessage) {
			//send a StatusMessage with the current status
			client.sendMessage(new StatusMessage(playerService.getStatus()));
		}
		else if (message instanceof DirectoryRequestMessage) {
			//send a DirectoryMessage with the currently directory listing
			client.sendMessage(new DirectoryMessage(playerService.getDirectoryListing()));
		}	
		else if (message instanceof QuitMessage) {
			client.setConnected(false); // client is disconnecting
		}
	}

}
