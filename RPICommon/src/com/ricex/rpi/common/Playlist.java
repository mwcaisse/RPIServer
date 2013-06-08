package com.ricex.rpi.common;

import java.util.ArrayList;
import java.util.List;

import com.ricex.rpi.common.video.Video;

/** Play list class which represents a series of movies to play in sequence on a certian device
 * 
 * @author Mitchell
 *
 */

public class Playlist {

	/** The name of the play list */
	private final String name;

	/** The items that the play list will play */
	private List<Video> items;

	/** the index of the currently playing item */
	private int currentIndex;

	/** Repeat the whole playlist once it is done playing */
	private boolean repeat;

	/** Repeat the current video when it is done playing */
	private boolean repeatCurrent;

	/** Creates a playlist with an empty name, not meant to be saved, nor created by user
	 * 
	 */

	public Playlist() {
		this("");
	}

	public Playlist(String name) {
		this.name = name;
		currentIndex = 0;
		items = new ArrayList<Video>();
	}

	/** Returns the name of this play list */
	public String getName() {
		return name;
	}

	/** Adds the given item to the end of the play list
	 * 
	 * @param item The item to add
	 */

	public void addItem(Video item) {
		items.add(item);
	}

	/** Removes the given item
	 * 
	 * @param item The item to remove
	 */

	public void removeItem(Video item) {
		items.remove(item);
	}

	/** returns the list of items */
	public List<Video> getItems() {
		return items;
	}

	/** Returns the next item to be played, or null if at the end
	 */
	public Video getNextItem() {
		if (items.size() < 1) {
			//the playlist is empty return null
			return null;
		}
		if (repeatCurrent) {
			return items.get(currentIndex);
		}
		currentIndex++; //advance to the next item
		if (items.size() >= currentIndex) {
			//currentIndex is over the limit.
			if (repeat) {
				// set the current index to the first item
				currentIndex = 0;
			}
			else {
				// not repeating and we made it to the end.
				return null;
			}
		}
		return items.get(currentIndex);
	}


	/**
	 * @return the repeat
	 */
	public boolean isRepeat() {
		return repeat;
	}

	/**
	 * @param repeat the repeat to set
	 */
	public void setRepeat(boolean repeat) {
		this.repeat = repeat;
	}

	/**
	 * @return the repeatCurrent
	 */
	public boolean isRepeatCurrent() {
		return repeatCurrent;
	}

	/**
	 * @param repeatCurrent the repeatCurrent to set
	 */
	public void setRepeatCurrent(boolean repeatCurrent) {
		this.repeatCurrent = repeatCurrent;
	}

	/**
	 * {@inheritDoc}
	 */

	@Override
	public String toString() {
		return getName();
	}
}
