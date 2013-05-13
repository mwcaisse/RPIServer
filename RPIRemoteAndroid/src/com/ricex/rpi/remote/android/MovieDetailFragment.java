package com.ricex.rpi.remote.android;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ricex.rpi.common.video.Movie;


public class MovieDetailFragment extends Fragment {

	public static final String MOVIE_KEY = "MOVIE";
	
	/** The movie that this will display */
	private Movie movie;
	

	public void setMovie(Movie movie) {
		this.movie = movie;
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {		
		View view = inflater.inflate(R.layout.movie_detail_layout, container, false);
		
		if (movie != null) {
			TextView movieName = (TextView) view.findViewById(R.id.movie_detail_title);
			TextView movieDescription = (TextView) view.findViewById(R.id.movie_detail_description);
			
			movieName.setText(movie.getName());
			movieDescription.setText(movie.getVideoFile());
		}
		
		return view;
		
	}

}
