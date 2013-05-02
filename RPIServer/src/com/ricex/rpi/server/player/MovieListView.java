package com.ricex.rpi.server.player;

import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;


public class MovieListView extends BorderPane {

	/** The list view for the movies */
	private ListView<Movie> movieList;
	
	public MovieListView() {		
		movieList = new ListView<Movie>();
		
		movieList.getItems().add(new Movie());
		movieList.getItems().add(new Movie());
		movieList.getItems().add(new Movie());
		movieList.getItems().add(new Movie());
		movieList.getItems().add(new Movie());
		
		setCenter(movieList);

	}
	
	
}
