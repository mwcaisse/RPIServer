package com.ricex.rpi.server.player;

import java.awt.Dimension;

import javax.swing.JFrame;

import com.ricex.rpi.server.RPIServer;

/** The gui for the server that will report status of the server, as well
 * 		as allowing the user to control the playing movie
 * 
 * @author Mitchell
 *
 */

public class RPIPlayer extends JFrame {

	/** Instance of the server that this GUI will interact with */
	//TODO: should probally make this an interface later on
	private RPIServer server;
	
	public RPIPlayer(RPIServer server) {
		super("RPI Player");
		this.server = server;
		
		setPreferredSize(new Dimension(800, 600));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		pack();		
		setLocationRelativeTo(null);
	}
}
