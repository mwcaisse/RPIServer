package com.ricex.rpi.remote.player;

import java.util.List;

import com.ricex.rpi.common.RPIStatus;
import com.ricex.rpi.common.video.Video;

/** Represents the RPIPlayer on the server
 * 
 * @author Mitchell Caisse
 *
 */

public class RPIPlayer {

	
	/** The status of this player */
	protected RPIStatus status;
	
	/** The name of this RPI player */
	protected String name;
	
	/** The directory listing of this player */
	protected Video directoryListing;
	
	/** The player module for this RPI player */
	protected RemotePlayerModule playerModule;
	
	/** The list of change listeners for this player */
	private List<RPIPlayerChangeListener> changeListeners;
	
	
	
	/** Creates a new RPI Player 
	 * 
	 */
	
	public RPIPlayer() {
		
	}
	
	/**
	 * @return The status of this player
	 */
	
	public RPIStatus getStatus() {
		return status;
	}
	
	/** Updates the status of this player to the given status, and notifies all change listeners
	 * 
	 * @param status The new status
	 */
	
	public void setStatus(RPIStatus status) {
		this.status = status;
		RPIPlayerChangeEvent changeEvent = new RPIPlayerChangeEvent(this, RPIPlayerChangeEvent.EVENT_STATUS_CHANGE);
		notifyChangeListeners(changeEvent);
	}
	
	/**
	 * @return The name of this player
	 */
	
	public String getName() {
		return name;
	}
	
	/** Updates the name of this player to the given name, and notifies all change listeners
	 * 
	 * @param name The new name
	 */
	
	public void setName(String name) {
		this.name = name;
		RPIPlayerChangeEvent changeEvent = new RPIPlayerChangeEvent(this, RPIPlayerChangeEvent.EVENT_NAME_CHANGE);
		notifyChangeListeners(changeEvent);
	}
	
	
	
	/**
	 * @return the directoryListing
	 */
	public Video getDirectoryListing() {
		return directoryListing;
	}

	/**
	 * @param directoryListing the directoryListing to set
	 */
	public void setDirectoryListing(Video directoryListing) {
		this.directoryListing = directoryListing;
	}

	/**
	 * @param playerModule the playerModule to set
	 */
	public void setPlayerModule(RemotePlayerModule playerModule) {
		this.playerModule = playerModule;
	}

	/** To string, returns the name of the player
	 * 
	 */
	
	public String toString() {
		return name;
	}
	
	/**
	 * @return The player module of this player
	 */
	
	public RemotePlayerModule getPlayerModule() {
		return playerModule;
	}
	
	/** Add the given change listener
	 * 
	 * @param listener THe change listener to add
	 */
	
	public void addChangeListener(RPIPlayerChangeListener listener) {
		changeListeners.add(listener);
	}
	
	/** Removes the given change listener
	 * 
	 * @param listener The listener to remove
	 */
	
	public void removeChangeListener(RPIPlayerChangeListener listener) {
		changeListeners.remove(listener);
	}
	
	/** Notify all change listeners of the specified change event
	 * 
	 * @param changeEvent The change event to send to listeners
	 */
	
	protected void notifyChangeListeners(RPIPlayerChangeEvent changeEvent) {
		for (RPIPlayerChangeListener listener : changeListeners) {
			listener.playerChanged(changeEvent);
		}
	}
}
