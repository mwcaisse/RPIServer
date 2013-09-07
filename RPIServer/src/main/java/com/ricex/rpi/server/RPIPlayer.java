package com.ricex.rpi.server;

import com.ricex.rpi.common.PlayerModule;
import com.ricex.rpi.common.PlayerModuleStatusListener;
import com.ricex.rpi.common.RPIStatus;
import com.ricex.rpi.common.message.IMessage;
import com.ricex.rpi.common.message.update.DirectoryMessage;
import com.ricex.rpi.common.message.update.StatusMessage;
import com.ricex.rpi.common.video.BasicMovieParser;
import com.ricex.rpi.common.video.MovieParser;
import com.ricex.rpi.common.video.Video;
import com.ricex.rpi.server.player.ThreadedPlayerModule;

/** The RPIPlayer that controls the PlayerModule and directory listing
 * 
 * @author Mitchell Caisse
 *
 */

public class RPIPlayer implements PlayerModuleStatusListener {
	
	/** The player module used to play videos */
	protected PlayerModule playerModule;
	
	/**The parser used to parse movies */
	protected MovieParser movieParser;
	
	/** The server used to update clients */
	protected Server<?> server;
	
	/** The current root directory */
	protected Video rootDirectory;
	
	/** Creates a nwe RPIPlayer with the given server and default player module and movie parser
	 * 
	 * @param server The server
	 */
	
	public RPIPlayer(Server<?> server) {
		this(server, new ThreadedPlayerModule(), new BasicMovieParser());
	}
	
	/** Creates a new RPI Player with the given player module and movie parser
	 * 
	 * @param server The server that is running
	 * @param playerModule The player module to use with this player	 * 
	 * @param movieParser The movie parser to use with this player 
	 */
	
	public RPIPlayer(Server<?> server, PlayerModule playerModule, MovieParser movieParser) {
		this.server = server;
		this.playerModule = playerModule;
		this.movieParser = movieParser;
		
		playerModule.addPlayerModuleStatusListener(this);
	}
	
	/** Returns the current directory listing of the player
	 * 
	 * @return The directory listing
	 */
	
	public Video getDirectoryListing() {
		return rootDirectory;
	}
	
	/** Returns the current status of the player module
	 * 
	 * @return the status
	 */
	
	public RPIStatus getPlayerStatus() {
		return playerModule.getStatus();
	}
	
	/** Parses the movies with the movie parser
	 * 
	 */
	
	public void parseMovies() {
		rootDirectory = movieParser.parseVideos(RPIServerProperties.getInstance().getBaseDir());
		updateClients(new DirectoryMessage(rootDirectory));
	}
	
	/** Returns the player module that is being used
	 * 
	 * @return the player module
	 */
	
	public PlayerModule getPlayerModule() {
		return playerModule;
	}

	/**
	 * {@inheritDoc}
	 */
	
	public void statusUpdated(PlayerModule playerModule, RPIStatus status) {
		updateClients(new StatusMessage(status));
	}
	
	/** Sends the given message to the clients to update them of any changes
	 * 
	 * @param msg The message to send
	 */
	
	protected void updateClients(IMessage msg) {
		server.sendToAllClients(msg);
	}	

}
