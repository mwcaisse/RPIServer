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

/** The fragment for displaying the list view of the RPI Players
 * 
 * @author Mitchell
 *
 */

public class PlayersListFragment extends Fragment {
	
	/** The list view that will display the movies in the current directory */
	private ListView playerListView;
	
	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {		
		View view = inflater.inflate(R.layout.players_layout, container, false);
		
		playerListView = (ListView) view.findViewById(R.id.players_list);
		
		List<String> data = new ArrayList<String>();
		for (int i=0;i<15;i++) {
			data.add("RPI Player " + i);
		}
		
		playerListView.setAdapter(new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, data));	
		
		return view;
		
	}
}
