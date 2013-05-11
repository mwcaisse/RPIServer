package com.ricex.rpi.common.message;

import com.ricex.rpi.common.video.Video;


public class DirectoryListingMessage implements IMessage {

	private Video rootDirectory;
	
	public DirectoryListingMessage(Video rootDirectory) {
		this.rootDirectory = rootDirectory;
	}
	
	public Video getRootDirectory() {
		return rootDirectory;
	}
}
