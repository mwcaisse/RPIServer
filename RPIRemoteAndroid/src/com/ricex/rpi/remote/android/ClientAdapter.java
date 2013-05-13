package com.ricex.rpi.remote.android;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

import com.ricex.rpi.common.message.remote.RemoteClient;


public class ClientAdapter  extends BaseAdapter {

	/** The context of this adapter */
	private Context context;
	
	/** The list of items in this adapter */
	private List<RemoteClient> items;
	
	/** Creates a new Video Adapter with an empty list of videos
	 * 
	 */
	
	public ClientAdapter(Context context) {
		this(context, new ArrayList<RemoteClient>());
	}
	
	/** Creates a new Video Adapter with a list of the given items
	 * 
	 * @param items
	 */
	
	public ClientAdapter(Context context, List<RemoteClient> items) {
		this.context = context;
		this.items = items;
	}
	
	/** Adds the given client to the item list 
	 * 
	 * @param item Client to add
	 */
	
	public void addItem(RemoteClient item) {
		items.add(item);
	}
	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public int getCount() {
		return items.size();
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public RemoteClient getItem(int position) {
		return items.get(position);
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			convertView = inflater.inflate(R.layout.players_list_item, null);
		}
		
		RemoteClient client = getItem(position);
		
		Log.i("RPI Client Adapter", "Setting view for client: " + client.getName() + "{" + client + "} Enabled: " + client.isEnabled());
		
		if (client != null) {	
			CheckedTextView checkedText = (CheckedTextView) convertView.findViewById(R.id.player_checkbox);
			
			checkedText.setText(client.getName());
			checkedText.setChecked(client.isEnabled());	
			
		}
		
		return convertView;
	}

	private Context getContext() {
		return context;
	}

}
