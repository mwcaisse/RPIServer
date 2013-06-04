package com.ricex.rpi.server;

import com.ricex.rpi.common.message.IMessage;
import com.ricex.rpi.common.message.MovieMessage;
import com.ricex.rpi.server.client.ClientConnectionListener;
import com.ricex.rpi.server.client.RPIClient;

/** Interface between the GUI and the Server
 * 
 * @author Mitchell
 *
 */

public class ServerPlayerModule implements ClientConnectionListener<RPIClient> {

	/** The active client */
	private RPIClient activeClient;
	
	/** Creates a new ServerPlayerModule with no active client */
	
	public ServerPlayerModule() {
		activeClient = null;
	}		                                            

	/** Starts playing the movie with the given path
	 * 
	 * @param videoPath Path to the movie to play
	 */
	
	public void play(String videoPath) {
		sendMessage(new MovieMessage(videoPath, MovieMessage.Command.PLAY));		
	}
	
	/** Stops the currently playing movie */
	
	public void stop() {
		sendMessage(new MovieMessage(MovieMessage.Command.STOP));
	}
	
	/** Pauses / resumes the currently playing movie
	 * 
	 */
	
	public void pause() {
		sendMessage(new MovieMessage(MovieMessage.Command.PAUSE));
	}
	
	/** Move to the next chapter */
	
	public void nextChapter() {
		sendMessage(new MovieMessage(MovieMessage.Command.NEXT_CHAPER));
	}
	
	/** Move the previous chapter */
	
	public void previousChapter() {
		sendMessage(new MovieMessage(MovieMessage.Command.PREVIOUS_CHAPTER));
	}
	
	/** Turn the volume up */
	
	public void volumeUp() {
		sendMessage(new MovieMessage(MovieMessage.Command.VOLUME_UP));
	}
	
	/** Turn the volume down */
	
	public void volumeDown() {
		sendMessage(new MovieMessage(MovieMessage.Command.VOLUME_DOWN));
	}
	
	/** Seek to the right 30 seconds */
	
	public void seekForwardSlow() {
		sendMessage(new MovieMessage(MovieMessage.Command.SEEK_FORWARD_SLOW));
	}
	
	/** Seek to the right 600 seconds */
	
	public void seekForwardFast() {
		sendMessage(new MovieMessage(MovieMessage.Command.SEEK_FORWARD_FAST));
	}
	
	/** Seek to the left 30 seconds */
	
	public void seekBackwardSlow() {
		sendMessage(new MovieMessage(MovieMessage.Command.SEEK_BACKWARD_SLOW));
	}
	
	/** Seek to the right 600 seconds */
	
	public void seekBackwardFast() {
		sendMessage(new MovieMessage(MovieMessage.Command.SEEK_BACKWARD_FAST));
	}
	
	/** Sends a message to all of the clients conencted to the server,
	 *  will obviouisly need to be refactored
	 *  
	 * @param message The message to send
	 */
	
	private void sendMessage(IMessage message) {
		if (activeClient != null) {
			activeClient.sendMessage(message);
		}

	}

	/** Sets the active client to the given client 
	 * 
	 * @param client
	 */
	
	public void setActiveClient(RPIClient client) {
		activeClient = client;
	}
	
	/** Removes the active client, any calls made to this module will not be sent to any client
	 * 
	 */
	
	public void removeActiveClient() {
		activeClient = null;
	}

	@Override
	public void clientConnected(RPIClient client) {
		
	}

	/** Removes the active client if it has disconnected */
	
	@Override
	public void clientDisconnected(RPIClient client) {
		if (client.equals(activeClient)) {
			removeActiveClient();
		}
	}
}

