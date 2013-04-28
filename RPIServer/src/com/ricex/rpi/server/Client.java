package com.ricex.rpi.server;

import java.net.Socket;

import com.ricex.rpi.common.MovieMessage;


public class Client {

	/** This Clients socket */
	private Socket socket;
	
	/** The client handler for this client */
	private ClientHandler handler;
	
	/** The thread that this client is executing in */
	private Thread clientThread;
	
	/** Indicates wether this client is still connected or not */
	private boolean connected = false;
	
	public Client (Socket socket) {
		this.socket = socket;
		this.handler = new ClientHandler(this);		
		
		connected = true;
		
		clientThread = new Thread(handler);
		clientThread.start();	
		
		//after we connect, lets send a message to play a video, and see how it works. muahah
		handler.sendMessage(new MovieMessage("Movies/Red_720p.mkv", MovieMessage.MovieCommand.PLAY));
		
		//sleep for 60 seconds
		try {
			Thread.sleep(1000 * 60);
		}
		catch (InterruptedException e) {
			//gah
		}
		//after 20 seconds stop the movie to see if this works
		handler.sendMessage(new MovieMessage("Movies/Red_720p.mkv", MovieMessage.MovieCommand.STOP));
		
	}
	
	public Socket getSocket() {
		return socket;
	}
	
	public boolean isConnected() {
		return connected;
	}
	
	protected void setConnected(boolean connected) {
		this.connected = connected;
	}
}
