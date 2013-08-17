package com.ricex.rpi.server.imbdparser;

import java.util.List;

import com.ricex.rpi.common.video.Movie;

/** Contains all of the IMDB information about the movies
 * 
 * @author Mitchell
 *
 */

public class IMDBMovie extends Movie {

	/** The description of the movie */
	protected String description;
	
	/** The date the movie was released */
	protected String releaseDate;
	
	/** The list of actors in the movie and thier parts */
	protected List<String> actors;
	
	public IMDBMovie(String movieName, String filePath) {
		super(movieName, filePath);
	}
	
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * @return the releaseDate
	 */
	public String getReleaseDate() {
		return releaseDate;
	}
	
	/**
	 * @param releaseDate the releaseDate to set
	 */
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	
	/**
	 * @return the actors
	 */
	public List<String> getActors() {
		return actors;
	}
	
	/**
	 * @param actors the actors to set
	 */
	public void setActors(List<String> actors) {
		this.actors = actors;
	}
}
