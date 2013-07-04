package com.ricex.rpi.server.player;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.ricex.rpi.server.RPIServer;
import com.ricex.rpi.server.RemoteServer;

/** The RPIPlayer
 * 
 *   Launches the PlayerUI as well as the servers
 *   Uses Java Swing for th UI
 *   
 * @author Mitchell
 *
 */

public class RPIPlayer extends JFrame {

	
	
	public static void main(String[] args) {
		RPIPlayer player = new RPIPlayer();
		player.setVisible(true);
	}
	
	
	/** The tabbed pane to display the different content */
	private JTabbedPane tabbedPane;
	
	/** The view for displaying the tree of videos */
	private VideoTreeView videoTreeView;
	
	/** The view for displaying playlists */
	private PlaylistView playlistView;
	
	/** The list view for displaying the connected clients */
	private ClientListView clientListView;
	
	/** The thread for the RPI client server to run in */
	private Thread clientServerThread;
	
	/** The thread for the remote server to run in */
	private Thread remoteServerThread;
	
	/** Creates a new instance of RPI Player
	 * 
	 */
	
	public RPIPlayer() {
		setTitle("RPI Player -- Swing Build");
		setPreferredSize(new Dimension(800, 600));
		pack();
		setResizable(false);
		
		//setSystemLookAndFeel();
		
		tabbedPane = new JTabbedPane();
		
		videoTreeView = new VideoTreeView();
		playlistView = new PlaylistView();
		clientListView = new ClientListView();
		
		tabbedPane.add("Videos", videoTreeView);
		tabbedPane.add("Playlists", playlistView);
		tabbedPane.add("Clients", clientListView);
		
		setContentPane(tabbedPane);
	}
	
	/** Sets the look and feel to the System Look and Feel
	 * 
	 */
	
	private void setSystemLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			System.out.println("Could not set the look and feel to System look and feel");
		}
	}
	
	/** Start the servers
	 * 
	 */
	
	private void startServers() {
		clientServerThread = new Thread(RPIServer.getInstance());
		remoteServerThread = new Thread(RemoteServer.getInstance());
		
		clientServerThread.start();
		remoteServerThread.start();
	}
	
}
