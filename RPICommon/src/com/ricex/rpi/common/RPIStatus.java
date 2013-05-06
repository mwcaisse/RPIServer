package com.ricex.rpi.common;

import java.io.Serializable;

public class RPIStatus implements Serializable {

	/** Status constants */
	public static final int PLAYING = 11587;
	public static final int PAUSED = PLAYING + 1;
	public static final int IDLE = PLAYING + 2;

	/** The name of the video that is being played */
	private final String videoPlaying;

	/** Int representing the status */
	private final int status;

	/**
	 * Creates an RPIStatus with the given status, if the status is PLAYING or
	 * PAUSED, will throw exception, as playing and paused need videoPlaying
	 * 
	 * @param status
	 */

	public RPIStatus(int status) {
		this.status = status;
		this.videoPlaying = "";
		if (status == PLAYING || status == PAUSED) {
			throw new UnsupportedOperationException("videoPlaying cannot be blank with a status of PLAYING or PAUSED");
		}
	}
	
	/** Returns the current status
	 * 
	 * @return
	 */
	
	public int getStatus() {
		return status;
	}

	/**
	 * Creates an RPI Status with the given status, and video playing file
	 * 
	 * @param status
	 * @param videoPlaying
	 */

	public RPIStatus(int status, String videoPlaying) {
		this.status = status;
		this.videoPlaying = videoPlaying;
	}
	
	/** Returns the string of this status, including the videoPlaying if necesary 
	 * 
	 */
	
	public String toString() {
		switch (status) {		
		case PLAYING:
			return "Playing " + videoPlaying;
		case PAUSED:
			return "Paused " + videoPlaying;
		case IDLE:
			return "Idle";
		}
		
		return "";
	}

}
