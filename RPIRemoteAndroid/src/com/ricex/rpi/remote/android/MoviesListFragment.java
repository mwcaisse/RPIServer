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

/** The fragment for displaying the movies list
 * 
 * @author Mitchell
 *
 */

public class MoviesListFragment extends Fragment implements OnItemClickListener{

	/** The list view that will display the movies in the current directory */
	private ListView moviesListView;
	
	/** The linear layout that will display the navigation buttons */
	private LinearLayout moviesDirectoryView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {		
		View view = inflater.inflate(R.layout.movies_layout, container, false);
		
		moviesListView = (ListView) view.findViewById(R.id.movies_list);
		
		VideoAdapter adapter = generateVideoAdapter(view.getContext());

		Log.i("RPI", "Adapter: " + adapter);
		
		/*
		List<String> items = new ArrayList<String>();
		items.add("HJ");
		items.add("SD");
		*/
		
		//moviesListView.setAdapter(new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_list_item_1, items));
		moviesListView.setAdapter(adapter);
		moviesListView.setOnItemClickListener(this);
		
		return view;
		
	}
	
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

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Video item = (Video)parent.getAdapter().getItem(position);
		if (item.isDirectory()) {
			
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

}
