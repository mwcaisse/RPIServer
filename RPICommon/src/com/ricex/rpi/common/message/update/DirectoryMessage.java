package com.ricex.rpi.common.message.update;

import com.ricex.rpi.common.video.Video;

/** Message that contains the directory listing of the RPI Player
 * 
 * @author Mitchell Caisse
 *
 */

public class DirectoryMessage extends UpdateMessage {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -597409087011630141L;
	
	/** The root directory of the specified client */
	private Video rootDirectory;
	
	public DirectoryMessage(Video rootDirectory) {
		this.rootDirectory = rootDirectory;
	}
	
	/**
	 * @return the root directory 
	 */
	
	public Video getRootDirectory() {
		return rootDirectory;
	}
	
}
