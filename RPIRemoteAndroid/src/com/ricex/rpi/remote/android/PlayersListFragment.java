package com.ricex.rpi.remote.android;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.ricex.rpi.common.RPIStatus;
import com.ricex.rpi.common.message.remote.RemoteClient;

/** The fragment for displaying the list view of the RPI Players
 * 
 * @author Mitchell
 *
 */

public class PlayersListFragment extends Fragment implements OnItemClickListener {
	
	/** The list view that will display the movies in the current directory */
	private ListView playerListView;
	
	/** The data being shown in this list */
	private List<RemoteClient> data;
	
	public PlayersListFragment() {
		data = new ArrayList<RemoteClient>();
		for (int i=0;i<15;i++) {
			data.add( new RemoteClient(i, "RPI Player " + (i + 1), new RPIStatus(RPIStatus.IDLE)));
		}	
	}
	
	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {		
		View view = inflater.inflate(R.layout.players_layout, container, false);
		
		playerListView = (ListView) view.findViewById(R.id.players_list);
		
		
		
		//playerListView.setAdapter(new ArrayAdapter<RemoteClient>(view.getContext(), R.layout.players_list_item, R.id.player_checkbox, data));
		playerListView.setAdapter(new ClientAdapter(view.getContext(), data));
		playerListView.setOnItemClickListener(this);
		
		return view;
		
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		RemoteClient item = (RemoteClient)parent.getAdapter().getItem(position);
	
		PlayerDetailFragment fragment = new PlayerDetailFragment();
		fragment.setClient(item);
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.fragment_container, fragment);
		ft.addToBackStack(null);
		ft.commit();

	}
}
