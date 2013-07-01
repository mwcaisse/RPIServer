package com.ricex.rpi.remote.android;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.ricex.rpi.common.message.remote.RemoteClient;
import com.ricex.rpi.remote.android.cache.ClientCache;

/** The fragment for displaying the list view of the RPI Players
 * 
 * @author Mitchell
 *
 */

public class PlayersListFragment extends Fragment implements OnItemClickListener {
	
	/** The list view that will display the movies in the current directory */
	private ListView playerListView;
	
	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {		
		View view = inflater.inflate(R.layout.players_layout, container, false);
		
		playerListView = (ListView) view.findViewById(R.id.players_list);
		
		
		//playerListView.setAdapter(new ArrayAdapter<RemoteClient>(view.getContext(), R.layout.players_list_item, R.id.player_checkbox, data));
		playerListView.setAdapter(new ClientAdapter(view.getContext(), ClientCache.getInstance().getClients()));
		playerListView.setOnItemClickListener(this);
		
		return view;
		
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		RemoteClient item = (RemoteClient)parent.getAdapter().getItem(position);
	
		Log.i("RPI Players List", "We are opening up a remote client pls");
		
		PlayerDetailFragment fragment = new PlayerDetailFragment();
		fragment.setClient(item);
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.fragment_container, fragment);
		ft.addToBackStack(null);
		ft.commit();

	}
}
