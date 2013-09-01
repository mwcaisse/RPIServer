package com.ricex.rpi.server.client.handler;

import com.ricex.rpi.common.message.IMessage;
import com.ricex.rpi.common.message.MovieMessage;
import com.ricex.rpi.common.message.QuitMessage;
import com.ricex.rpi.common.message.request.DirectoryRequestMessage;
import com.ricex.rpi.common.message.request.StatusRequestMessage;
import com.ricex.rpi.common.message.update.DirectoryMessage;
import com.ricex.rpi.common.message.update.StatusMessage;
import com.ricex.rpi.server.RPIPlayer;
import com.ricex.rpi.server.client.RemoteClient;


public class RemoteClientHandler extends ClientHandler<RemoteClient> {
	
	/** The RPI Player */
	protected RPIPlayer player;
	
	/** Creates a new Remote Client handler */
	
	public RemoteClientHandler(RemoteClient client) {
		super(client);
		player = client.getPlayer();
	}
	
	/** 
	 * {@inheritDoc}
	 */

	protected void processMessage(IMessage message) {		
		if (message instanceof MovieMessage) {
			//execute the movie message
			MovieMessage movieMessage = (MovieMessage) message;
			movieMessage.execute(player.getPlayerModule());
		}
		else if (message instanceof StatusRequestMessage) {
			//send a StatusMessage with the current status
			client.sendMessage(new StatusMessage(player.getPlayerStatus()));
		}
		else if (message instanceof DirectoryRequestMessage) {
			//send a DirectoryMessage with the currently directory listing
			client.sendMessage(new DirectoryMessage(player.getDirectoryListing()));
		}	
		else if (message instanceof QuitMessage) {
			client.setConnected(false); // client is disconnecting
		}
	}

}
