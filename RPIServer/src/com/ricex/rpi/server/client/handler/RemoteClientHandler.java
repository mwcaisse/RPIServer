package com.ricex.rpi.server.client.handler;

import com.ricex.rpi.common.message.DirectoryListingMessage;
import com.ricex.rpi.common.message.DirectoryListingRequestMessage;
import com.ricex.rpi.common.message.IMessage;
import com.ricex.rpi.common.message.QuitMessage;
import com.ricex.rpi.common.message.remote.RemoteMovieMessage;
import com.ricex.rpi.common.video.MovieParser;
import com.ricex.rpi.server.RPIServer;
import com.ricex.rpi.server.RPIServerProperties;
import com.ricex.rpi.server.client.RPIClient;
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
		if (message instanceof RemoteMovieMessage) {
			RemoteMovieMessage rmsg = (RemoteMovieMessage) message;
			RPIClient client = RPIServer.getInstance().getClient(rmsg.getClientId());
			client.sendMessage(rmsg);
		}
		else if (message instanceof QuitMessage) {
			client.setConnected(false); // client is disconnecting
		}
	}

}
