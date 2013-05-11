package com.ricex.rpi.server;

import java.util.ArrayList;
import java.util.List;

import com.ricex.rpi.common.IMessage;
import com.ricex.rpi.common.MovieMessage;
import com.ricex.rpi.common.PlayerModule;
import com.ricex.rpi.common.RPIStatus;
import com.ricex.rpi.server.client.Client;
import com.ricex.rpi.server.client.RPIClient;

/** Interface between the GUI and the Server
 * 
 * @author Mitchell
 *
 */

public class ServerPlayerModule {

	/** The list of clients to send the commands to */
	private List<RPIClient> clients;
	
	/** Creates a new ServerPlayerModule with no clients */
	
	public ServerPlayerModule() {
		this(new ArrayList<RPIClient>());
	}
	
	/** Creates a new ServerPlayerModule with the given clients */
	public ServerPlayerModule(List<RPIClient> clients) {
		this.clients = clients;
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
		for (RPIClient client: clients) {
			client.sendMessage(message);
		}
	}

	/** Adds the given client to the list to send commands to */
	
	public void addClient(RPIClient client) {
		clients.add(client);
	}	
	
	/** Removes the given client from the list to send commands to */
	public void removeClient(RPIClient client) {
		clients.remove(client);
	}

}
