package com.ricex.rpi.server.player;

import javafx.scene.control.ContextMenu;

import com.ricex.rpi.common.video.Video;


public class VideoContextMenu extends ContextMenu {

	/** The video that this context menu was opened on */
	private final Video video;	
	
	public VideoContextMenu(Video video) {
		this.video = video;
	}	
}
