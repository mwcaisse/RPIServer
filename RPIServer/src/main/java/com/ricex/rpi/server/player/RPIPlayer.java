package com.ricex.rpi.server.player;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ricex.rpi.common.video.MovieParser;
import com.ricex.rpi.common.video.Video;
import com.ricex.rpi.server.RPIServer;
import com.ricex.rpi.server.RPIServerProperties;
import com.ricex.rpi.server.RemoteServer;
import com.ricex.rpi.server.client.RPIClient;
import com.ricex.rpi.server.imbdparser.IMDBMovieParser;
import com.ricex.rpi.server.imbdparser.IMDBVideoTreeView;
import com.ricex.rpi.server.player.view.ControllerPane;
import com.ricex.rpi.server.player.view.client.ClientTableView;
import com.ricex.rpi.server.player.view.playlist.PlaylistView;
import com.ricex.rpi.server.player.view.video.VideoTreeView;

/** The RPIPlayer
 * 
 *   Launches the PlayerUI as well as the servers
 *   Uses Java Swing for th UI
 * 
 * @author Mitchell Caisse
 *
 */

public class RPIPlayer extends JFrame {

	private static final Logger log = LoggerFactory.getLogger(RPIPlayer.class);
	
	/** The singleton instance of this class */
	private static RPIPlayer _instance;

	public static RPIPlayer getInstance() {
		if (_instance == null) {
			_instance = new RPIPlayer();
		}
		return _instance;
	}

	/** Starts the RPIPlayer and the servers
	 * 
	 * @param args the command line arguments.
	 */

	public static void main(String[] args) {
		RPIPlayer player = getInstance();
		player.startServers();
		player.initializeWindow();
		player.setVisible(true);
	}

	/** The tabbed pane to display the different content */
	private JTabbedPane tabbedPane;

	/** The view for displaying the tree of videos */
	private VideoTreeView videoTreeView;

	/** The view for displaying playlists */
	private PlaylistView playlistView;

	/** The list view for displaying the connected clients */
	private ClientTableView clientTableView;

	/** The view for the controller pane */
	private ControllerPane controllerPane;

	/** The thread for the RPI client server to run in */
	private Thread clientServerThread;

	/** The thread for the remote server to run in */
	private Thread remoteServerThread;

	/** The playlist controller for this player */
	private PlaylistController playlistController;

	/** The root directory / parsed videos of this player */
	private Video rootDirectory;

	/** The currently active client */
	private RPIClient activeClient;
	
	/** The parser to use when parsing the mo vies */
	private MovieParser movieParser;

	/** Creates a new instance of RPI Player
	 */

	private RPIPlayer() {
		playlistController = new PlaylistController();
		movieParser = new IMDBMovieParser();		
		parseRootDirectory();
	}

	/** Initialize the RPIPlayer window
	 * 
	 */

	private void initializeWindow() {
		setTitle("RPI Player -- Swing Build");
		setPreferredSize(new Dimension(800, 600));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setResizable(false);
		setLocationRelativeTo(null);

		//setSystemLookAndFeel();

		JPanel contentPane = new JPanel();
		BorderLayout layout = new BorderLayout();
		contentPane.setLayout(layout);
		tabbedPane = new JTabbedPane();

		videoTreeView = new IMDBVideoTreeView();
		playlistView = new PlaylistView();
		clientTableView = new ClientTableView();
		controllerPane = new ControllerPane();

		tabbedPane.add("Videos", videoTreeView);
		tabbedPane.add("Playlists", playlistView);
		tabbedPane.add("Clients", clientTableView);

		tabbedPane.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				Component selectedComponent = tabbedPane.getSelectedComponent();
				controllerPane.updatePlayableView(selectedComponent);
			}

		});
		
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		contentPane.add(controllerPane, BorderLayout.SOUTH);

		setContentPane(contentPane);

		addShutdownHook();
	}

	/** Adds the shutdown hook for the program, that will shutdown both servers
	 * 
	 */

	private void addShutdownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread() {

			@Override
			public void run() {
				RPIServer.getInstance().shutdown();
				RemoteServer.getInstance().shutdown();
			}

		});
	}

	/** Sets the look and feel to the System Look and Feel
	 * 
	 */

	private void setSystemLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			log.error("Could not set the look and feel to system look and feel", e);
		}
	}

	/** Start the servers
	 * 
	 */

	private void startServers() {
		clientServerThread = new Thread(RPIServer.getInstance());
		remoteServerThread = new Thread(RemoteServer.getInstance());

		clientServerThread.setDaemon(true);
		remoteServerThread.setDaemon(true);

		clientServerThread.start();
		remoteServerThread.start();
	}
	
	/** Parses the root directory for videos
	 * 
	 */
	
	public void parseRootDirectory() {
		rootDirectory = movieParser.parseVideos(RPIServerProperties.getInstance().getBaseDirectory());
	}

	/** Returns the currently displayed view
	 * 
	 * @return The currently displayed view
	 */

	public Component getCurrentView() {
		return tabbedPane.getSelectedComponent();
	}

	/** Returns the playlist controller for this RPIPlayer
	 * 
	 * @return The rpi player
	 */

	public PlaylistController getPlaylistController() {
		return playlistController;
	}

	/** Returns the root directory of this player
	 * 
	 * @return
	 */

	public Video getRootDirectory() {
		return rootDirectory;
	}
	
	/** Determines whether or not an active client exists
	 * 
	 * @return True if there is an active client, false if not
	 */
	
	public boolean activeClientExists() {
		return activeClient != null;
	}

	
	/**
	 * @return the activeClient
	 */
	public RPIClient getActiveClient() {
		return activeClient;
	}

	
	/**
	 * @param activeClient the activeClient to set
	 */
	public void setActiveClient(RPIClient activeClient) {
		this.activeClient = activeClient;
	}
}
