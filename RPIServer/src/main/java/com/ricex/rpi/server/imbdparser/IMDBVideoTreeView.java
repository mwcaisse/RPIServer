package com.ricex.rpi.server.imbdparser;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ricex.rpi.common.video.Video;
import com.ricex.rpi.server.player.view.video.VideoTreeView;

/**  IMDB Video Tree View
 * 
 *  Adds movie information from IMDB to the plain Video Tree View
 * 
 * @author Mitchell Caisse
 *
 *  TODO: Add UI to fetch / add movie information
 */

public class IMDBVideoTreeView extends VideoTreeView implements TreeSelectionListener {

	/** The logger */
	private static final Logger log = LoggerFactory.getLogger(IMDBVideoTreeView.class);
	
	/** The horizontal padding for the spring layout */
	private static final int HORIZONTAL_PADDING = 5;
	
	/** The vertical padding for the spring layout */
	private static final int VERTICAL_PADDING = 5;
	
	/** The side panel that will be used to display the movie information */
	protected JPanel sidePanel;
	
	/** The label to display the name of the movie */
	protected JLabel labMovieName;
	
	/** The table to display the release date of the movie */
	protected JLabel labMovieReleaseDate;
	
	/** The label to display the description of the movie */
	protected JLabel labMovieDescription;
	
	/** Initializes all of the components needed to display the movie information for the selected
	 *  video
	 */
	
	public IMDBVideoTreeView() {
		sidePanel = new JPanel();
		sidePanel.setPreferredSize(new Dimension(300, 100));
		
		//initialize the labels for displaying the movie information
		labMovieName = new JLabel("Airplane!");		
		labMovieDescription = new JLabel(concatHtml("An airplane crew takes ill. Surely the only person capable of landing the plane is an ex-pilot afraid to fly. But don't call him Shirley."));		
		labMovieReleaseDate = new JLabel("2 July 1980 (USA)");
		
		labMovieDescription.setMaximumSize(new Dimension(100, 25));
		
		SpringLayout layout = new SpringLayout();
		
		layout.putConstraint(SpringLayout.WEST, labMovieName, HORIZONTAL_PADDING, SpringLayout.WEST, sidePanel);
		layout.putConstraint(SpringLayout.NORTH, labMovieName, VERTICAL_PADDING, SpringLayout.NORTH, sidePanel);
		layout.putConstraint(SpringLayout.EAST, labMovieName, -HORIZONTAL_PADDING, SpringLayout.EAST, sidePanel);
		
		layout.putConstraint(SpringLayout.WEST, labMovieReleaseDate, HORIZONTAL_PADDING, SpringLayout.WEST, sidePanel);
		layout.putConstraint(SpringLayout.NORTH, labMovieReleaseDate, VERTICAL_PADDING, SpringLayout.SOUTH, labMovieName);
		
		layout.putConstraint(SpringLayout.WEST, labMovieDescription, HORIZONTAL_PADDING, SpringLayout.WEST, sidePanel);
		layout.putConstraint(SpringLayout.NORTH, labMovieDescription, VERTICAL_PADDING , SpringLayout.SOUTH, labMovieReleaseDate);
		layout.putConstraint(SpringLayout.EAST, labMovieDescription, -2 * HORIZONTAL_PADDING , SpringLayout.EAST, sidePanel);
		
		sidePanel.setLayout(layout);
		
		sidePanel.add(labMovieName);
		sidePanel.add(labMovieDescription);
		sidePanel.add(labMovieReleaseDate);	
		
		add(sidePanel, BorderLayout.EAST);
		
		// add ourselves as a selection listener to the tree
		addTreeSelectionListener(this);
	}
	
	/** Updates the movie information in the pane, with the in formation from the 
	 *  video
	 * @param video The new video to get the information from
	 */
	
	protected void updateMovieInformation(Video video) {
		if (video.isDirectory()) {
			clearMovieInformation();
		}
		else if (video instanceof IMDBMovie) {
			addMovieInformation((IMDBMovie) video);
		}
		else {
			log.error("IMDBVideoTreeView is being used without IMDBMovieParser, will not have any movie information");
			
		}
	}
	
	/** Adds the movie information from the IMDBMovie
	 * 
	 * @param movie The movie to get the information from
	 */
	
	protected void addMovieInformation(IMDBMovie movie) {
		if (movie.getDescription() == null) {
			clearMovieInformation();
			labMovieName.setText("No information found");
		}
		else {
			labMovieName.setText(movie.getName());
			labMovieDescription.setText(concatHtml(movie.getDescription()));
			labMovieReleaseDate.setText(movie.getReleaseDate());
		}
	}
	
	/** Adds the HTML body and paragraph around the string so it will be
	 *   properly word wrapped in a JLabel
	 * @param str The string to wrap the html around
	 * @return The string with the html
	 */
	
	private String concatHtml(String str) {
		return "<html><body><p>" + str + "</p></body></html>";
	}
	
	/** Clears all of the information from the fields
	 * 
	 */
	
	protected void clearMovieInformation() {
		labMovieName.setText("");
		labMovieDescription.setText("");
		labMovieReleaseDate.setText("");
	}

	/** Updates the movie information to the video that is selected
	 * 
	 */
	
	@Override
	public void valueChanged(TreeSelectionEvent e) {
		if (multipleVideosSelected()) {
			//if multiple movies are selected, we can't really show information
			clearMovieInformation();
		}
		else {		
			updateMovieInformation(getSelectedVideo());
		}
	}
	
}
