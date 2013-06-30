package com.ricex.rpi.remote.android;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.ricex.rpi.common.message.remote.RemoteClient;
import com.ricex.rpi.common.video.Directory;
import com.ricex.rpi.common.video.Movie;
import com.ricex.rpi.common.video.Video;
import com.ricex.rpi.remote.android.cache.ClientCache;
import com.ricex.rpi.remote.android.cache.DirectoryChangeListener;

/** The fragment for displaying the movies list
 * 
 * @author Mitchell
 *
 */

public class MoviesListFragment extends Fragment implements OnItemClickListener {

	/** The list view that will display the movies in the current directory */
	private ListView moviesListView;
	
	/** The linear layout that will display the navigation buttons */
	private LinearLayout moviesDirectoryView;
	
	/** The context for this view */
	private Context context;
	
	/** Creates a new movie list view and registers this as a listener in the DirectoryCache
	 */
	
	public MoviesListFragment() {
		moviesListView = null;	
	}
	
	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {		
		View view = inflater.inflate(R.layout.movies_layout, container, false);
		context = view.getContext();
		
		moviesListView = (ListView) view.findViewById(R.id.movies_list);
		
		//update the directory listing
		updateDirectoryListing();
		
		moviesListView.setOnItemClickListener(this);
		
		return view;
		
	}
	
	@Override
	public void onStart() {
		super.onStart();
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Video item = (Video)parent.getAdapter().getItem(position);
		if (item.isDirectory()) {
			Directory dir = (Directory) item;
			VideoAdapter adapter = new VideoAdapter(context, dir.getChildren());
			moviesListView.setAdapter(adapter);
		}
		else {
			MovieDetailFragment fragment = new MovieDetailFragment();
			fragment.setMovie((Movie) item);
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.replace(R.id.fragment_container, fragment);
			ft.addToBackStack(null);
			ft.commit();
		}
	}
	
	private void updateDirectoryListing() {
		RemoteClient client = ClientCache.getInstance().getActiveClient();
		
		List<Video> videos;
		if (client == null) {
			videos = new ArrayList<Video>();
		}
		else {
			videos = client.getDirectoryListing().getChildren();
		}		
		VideoAdapter adapter = new VideoAdapter(context, videos);
		moviesListView.setAdapter(adapter);
	}
}
