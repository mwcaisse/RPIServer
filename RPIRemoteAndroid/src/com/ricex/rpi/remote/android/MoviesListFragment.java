package com.ricex.rpi.remote.android;

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

import com.ricex.rpi.common.video.Directory;
import com.ricex.rpi.common.video.Movie;
import com.ricex.rpi.common.video.Video;
import com.ricex.rpi.remote.android.cache.DirectoryCache;
import com.ricex.rpi.remote.android.cache.DirectoryChangeListener;

/** The fragment for displaying the movies list
 * 
 * @author Mitchell
 *
 */

public class MoviesListFragment extends Fragment implements OnItemClickListener, DirectoryChangeListener {

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
		DirectoryCache.getInstance().addListener(this);
	}
	
	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {		
		View view = inflater.inflate(R.layout.movies_layout, container, false);
		context = view.getContext();
		
		moviesListView = (ListView) view.findViewById(R.id.movies_list);
		
		//VideoAdapter adapter = generateVideoAdapter(view.getContext());
		VideoAdapter adapter = new VideoAdapter(context, DirectoryCache.getInstance().getRootDirectory().getChildren());

		Log.i("RPI", "Adapter: " + adapter);

		moviesListView.setAdapter(adapter);
		moviesListView.setOnItemClickListener(this);
		
		return view;
		
	}
	
	@Override
	public void onStart() {
		super.onStart();
	}
	
	/** Generates a temporary video adapter for testing purposes
	 */
	
	private VideoAdapter generateVideoAdapter(Context context) {
		VideoAdapter adapter = new VideoAdapter(context);
		
		for (int i=0;i<10;i++) {
			if (i % 2 == 0) {
				adapter.addItem(new Movie("Movie: " + i/2, ""));
			}
			if (i % 2 == 0) {
				adapter.addItem(new Directory("Directory: " + (i/2+ 1)));
			}
		}
		
		return adapter;
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

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public void rootDirectoryChanged(Video rootDirectory) {
		if (moviesListView != null) {
			VideoAdapter adapter = new VideoAdapter(context, rootDirectory.getChildren());
			moviesListView.setAdapter(adapter);
		}
		
	}

}
