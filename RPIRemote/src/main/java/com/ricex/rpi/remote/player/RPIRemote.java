package com.ricex.rpi.remote.player;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ricex.rpi.common.imbdparser.IMDBMovieParser;
import com.ricex.rpi.common.video.MovieParser;
import com.ricex.rpi.common.video.Video;
import com.ricex.rpi.remote.RPIPlayer;
import com.ricex.rpi.remote.RPIServerProperties;
import com.ricex.rpi.remote.player.view.ControllerPane;
import com.ricex.rpi.remote.player.view.playlist.PlaylistView;
import com.ricex.rpi.remote.player.view.video.IMDBVideoTreeView;
import com.ricex.rpi.remote.player.view.video.VideoTreeView;

/** The RPIPlayer
 * 
 *   Launches the PlayerUI as well as the servers
 *   Uses Java Swing for th UI
 * 
 * @author Mitchell Caisse
 *
 */

public class RPIRemote extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(RPIRemote.class);
	
	/** The singleton instance of this class */
	private static RPIRemote _instance;

	public static RPIRemote getInstance() {
		if (_instance == null) {
			_instance = new RPIRemote();
		}
		return _instance;
	}

	/** Starts the RPIPlayer and the servers
	 * 
	 * @param args the command line arguments.
	 */

	public static void main(String[] args) {
		RPIRemote player = getInstance();
		player.initializeWindow();
		player.setVisible(true);
	}

	/** The tabbed pane to display the different content */
	private JTabbedPane tabbedPane;

	/** The view for displaying the tree of videos */
	private VideoTreeView videoTreeView;

	/** The view for displaying playlists */
	private PlaylistView playlistView;

	/** The view for the controller pane */
	private ControllerPane controllerPane;

	/** The playlist controller for this player */
	private PlaylistController playlistController;

	/** The root directory / parsed videos of this player */
	private Video rootDirectory;

	/** The player the we are connected to */
	private RPIPlayer activePlayer;
	
	/** The parser to use when parsing the mo vies */
	private MovieParser movieParser;

	/** Creates a new instance of RPI Player
	 */

	private RPIRemote() {
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
		controllerPane = new ControllerPane();

		tabbedPane.add("Videos", videoTreeView);
		tabbedPane.add("Playlists", playlistView);

		tabbedPane.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				Component selectedComponent = tabbedPane.getSelectedComponent();
				controllerPane.updatePlayableView(selectedComponent);
				if (selectedComponent.equals(playlistView)) {
					//TODO: Re-implement this, add a tab interface with refresh method
					playlistView.refresh();
				}
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
				//TODO: add server disconection hook
			}

		});
	}

	/** Sets the look and feel to the System Look and Feel
	 * 
	 */

	protected void setSystemLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			log.error("Could not set the look and feel to system look and feel", e);
		}
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
	
	public boolean activePlayerExists() {
		return activePlayer != null;
	}

	
	/**
	 * @return the activePlayer
	 */
	public RPIPlayer getActivePlayer() {
		return activePlayer;
	}

	
	/**
	 * @param activePlayer the activePlayer to set
	 */
	public void setActivePlayer(RPIPlayer activePlayer) {
		this.activePlayer = activePlayer;
	}
}
