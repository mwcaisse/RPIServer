package com.ricex.rpi.remote.android;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ricex.rpi.common.Playlist;
import com.ricex.rpi.common.message.MovieMessage;
import com.ricex.rpi.common.message.remote.RemoteClient;
import com.ricex.rpi.common.message.remote.RemoteMovieMessage;
import com.ricex.rpi.common.video.Movie;
import com.ricex.rpi.remote.android.cache.ClientCache;
import com.ricex.rpi.remote.android.network.ServerConnector;


public class MovieDetailFragment extends Fragment implements OnClickListener {

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
			Button moviePlay = (Button) view.findViewById(R.id.movie_detail_but_play);
			
			movieName.setText(movie.getName());
			movieDescription.setText(movie.getVideoFile());
			moviePlay.setOnClickListener(this);
		}
		
		return view;
		
	}

	@Override
	public void onClick(View v) {
		if (movie != null) {			
			//get the active client and send it the movie message
			RemoteClient client = ClientCache.getInstance().getActiveClient();			
			if (client != null) {
				Playlist playlist = new Playlist();
				playlist.addItem(movie);
				RemoteMovieMessage message = new RemoteMovieMessage(playlist,
						MovieMessage.Command.PLAY, client.getId());
				ServerConnector.getInstance().sendMessage(message);
				getFragmentManager().popBackStack();
			}
		}		
	}

}
