package com.ricex.rpi.server;

import com.ricex.rpi.common.PlayerModule;
import com.ricex.rpi.common.video.MovieParser;
import com.ricex.rpi.common.video.Video;
import com.ricex.rpi.server.client.handler.RPIPlayerService;

/** The RPIPlayer that controls the PlayerModule and directory listing
 * 
 * @author Mitchell Caisse
 *
 */

public class RPIPlayer {

	
	/** The player module used to play videos */
	protected PlayerModule playerModule;
	
	/**The parser used to parse movies */
	protected MovieParser movieParser;
	
	/** The service to interface with the server */
	protected RPIPlayerService service;
	
	/** Creates a new RPI Player with the given player module and movie parser
	 * 
	 * @param playerModule The player module to use with this player
	 * @param movieParser The movie parser to use with this player
	 */
	
	public RPIPlayer(PlayerModule playerModule, MovieParser movieParser) {
		this.playerModule = playerModule;
		this.movieParser = movieParser;
	}
	
	/** Parses the movies with the movie parser
	 * 
	 */
	
	public void parseMovies() {
		Video rootDirectory = movieParser.parseVideos(RPIServerProperties.getInstance().getBaseDir());
		service.updateDirectoryListing(rootDirectory);
	}
	
	/** Returns the player module that is being used
	 * 
	 * @return the player module
	 */
	
	public PlayerModule getPlayerModule() {
		return playerModule;
	}
	

}
