package com.ricex.rpi.server.client.handler;

import com.ricex.rpi.common.message.IMessage;
import com.ricex.rpi.common.message.remote.DirectoryListingMessage;
import com.ricex.rpi.common.message.remote.DirectoryListingRequestMessage;
import com.ricex.rpi.common.message.remote.RemoteMovieMessage;
import com.ricex.rpi.server.RPIServer;
import com.ricex.rpi.server.RPIServerProperties;
import com.ricex.rpi.server.client.RPIClient;
import com.ricex.rpi.server.client.RemoteClient;
import com.ricex.rpi.server.player.MovieParser;


public class RemoteClientHandler extends ClientHandler<RemoteClient> {
	
	/** Creates a new Remote Client handler */
	
	public RemoteClientHandler(RemoteClient client) {
		super(client);
	}
	
	/** 
	 * {@inheritDoc}
	 */

	protected void processMessage(IMessage message) {
		if (message instanceof DirectoryListingRequestMessage) {
			MovieParser mp = new MovieParser(RPIServerProperties.getInstance().getBaseDir());
			sendMessage(new DirectoryListingMessage(mp.parseVideos()));
		}	
		else if (message instanceof RemoteMovieMessage) {
			RemoteMovieMessage rmsg = (RemoteMovieMessage) message;
			RPIClient client = RPIServer.getInstance().getClient(rmsg.getClientId());
			client.sendMessage(rmsg);
		}
	}

}
