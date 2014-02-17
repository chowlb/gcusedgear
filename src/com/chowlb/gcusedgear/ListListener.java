package com.chowlb.gcusedgear;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class ListListener implements OnItemClickListener{
	List<RssItem> listItems;
	
	Activity activity;
	
	public ListListener(List<RssItem> aListItems, Activity anActivity) {
		listItems = aListItems;
		activity = anActivity;
	}
	
	public void onItemClick(AdapterView parent, View view, int pos, long id) {
		view.setBackgroundResource(R.color.DarkGrey);
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(listItems.get(pos).getLink()));
		activity.startActivity(i);
	}
}
