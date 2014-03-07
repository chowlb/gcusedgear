package com.chowlb.gcusedgear;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class ListListener implements OnItemClickListener{
	List<RssItem> listItems;
	
	Activity activity;
	
	public ListListener(List<RssItem> aListItems, Activity anActivity) {
		listItems = aListItems;
		activity = anActivity;
	}
	
	public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
		
		Intent i = new Intent(Intent.ACTION_VIEW);
		RssItem rss = (RssItem) parent.getAdapter().getItem(pos);
		LazyAdapter la = (LazyAdapter) parent.getAdapter();
		la.addHighlight(rss.getGuid());
		
		i.setData(Uri.parse(rss.getLink()));
		activity.startActivity(i);
		la.notifyDataSetChanged();
	}
}
