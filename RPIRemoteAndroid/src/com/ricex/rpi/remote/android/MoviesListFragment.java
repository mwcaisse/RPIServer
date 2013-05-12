package com.ricex.rpi.remote.android;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

/** The fragment for displaying the movies list
 * 
 * @author Mitchell
 *
 */

public class MoviesListFragment extends Fragment {

	/** The list view that will display the movies in the current directory */
	private ListView moviesListView;
	
	/** The linear layout that will display the navigation buttons */
	private LinearLayout moviesDirectoryView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {		
		View view = inflater.inflate(R.layout.movies_layout, container, false);
		
		moviesListView = (ListView) view.findViewById(R.id.movies_list);
		
		List<String> data = new ArrayList<String>();
		for (int i=0;i<15;i++) {
			data.add("RPI Movie " + i);
		}
		
		moviesListView.setAdapter(new ArrayAdapter<String>(view.getContext(), R.layout.movies_list_item, R.id.movies_text, data));	
		
		return view;
		
	}
}
