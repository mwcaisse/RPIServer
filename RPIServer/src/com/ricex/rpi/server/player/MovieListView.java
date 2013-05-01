package com.ricex.rpi.server.player;

import java.awt.BorderLayout;

import javax.swing.JList;
import javax.swing.JPanel;


/** The list view that will display the list of movies
 * 
 * @author Mitchell
 *
 */

public class MovieListView extends JPanel {

	
	/** JList for dispalying the movies */
	private JList<Movie> movieList;
	
	public MovieListView() {
		
		movieList = new JList<Movie>();
		
		Movie[] movies = new Movie[] { new Movie(), new Movie(), new Movie()};
		
		movieList.setListData(movies);
		setLayout(new BorderLayout());
		add(movieList, BorderLayout.CENTER);
	}
}
