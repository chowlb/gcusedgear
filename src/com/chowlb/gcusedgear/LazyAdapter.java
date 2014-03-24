package com.chowlb.gcusedgear;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("DefaultLocale")
public class LazyAdapter extends BaseAdapter implements Filterable {

	private List<RssItem> result;
	private List<RssItem> filteredData = null;
	ArrayList<Integer> hlPositions = new ArrayList<Integer>();
	Context context;
	int[] imageId;
	private static LayoutInflater inflater=null;
	private ItemFilter mFilter = new ItemFilter();
	public ImageLoader imageLoader;
	
	public LazyAdapter(Context mainActivity, List<RssItem> list) {
		result = list;
		filteredData = list;
		context=mainActivity;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		 imageLoader=new ImageLoader(context.getApplicationContext());
	}
	
	@Override
	public int getCount() {
		return filteredData.size();
	}

	@Override
	public Object getItem(int position) {
		return filteredData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public class Holder{
		TextView tv;
		ImageView img;
	}
	
	public void addHighlight(int guid) {
		hlPositions.add(guid);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final Holder holder = new Holder();
		View rowView;
			rowView = inflater.inflate(R.layout.list_row, null);
			holder.tv=(TextView) rowView.findViewById(R.id.itemTitle);
			
			holder.img=(ImageView) rowView.findViewById(R.id.list_image);
			RssItem rssitem = filteredData.get(position);
						
			if(hlPositions.contains(rssitem.getGuid())) {
				rowView.setBackgroundResource(R.color.DarkGrey);
			}
		holder.tv.setText(rssitem.getDescription().toString());
		imageLoader.DisplayImage(rssitem.getImage(), holder.img);
		return rowView;
	}
	
	public Filter getFilter() {
		return mFilter;
	}
	
	private class ItemFilter extends Filter {
		@SuppressLint("DefaultLocale")
		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			
			String filterString = constraint.toString().toLowerCase();
			//Log.e("chowlb", "FilterString: '"+filterString+"'");
			FilterResults results = new FilterResults();
			
			final List<RssItem> list = result;
 
			int count = list.size();
			
			//Log.e("chowlb", "Lis Size in filter: " + count);
			
			final ArrayList<RssItem> nlist = new ArrayList<RssItem>(count);
 
			String filterableString ;
			
			for (int i = 0; i < count; i++) {
				filterableString = list.get(i).getDescription().toString();
				if (filterableString.toLowerCase().contains(filterString)) {
					nlist.add(list.get(i));
				}
			}
			
			results.values = nlist;
			results.count = nlist.size();
 
			return results;
		}
 
		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint, FilterResults results) {
			filteredData = (ArrayList<RssItem>) results.values;
			notifyDataSetChanged();
		}
 
	}

}
