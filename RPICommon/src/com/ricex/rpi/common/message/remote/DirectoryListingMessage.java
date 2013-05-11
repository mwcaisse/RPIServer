package com.ricex.rpi.common.message.remote;

import com.ricex.rpi.common.message.IMessage;
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
