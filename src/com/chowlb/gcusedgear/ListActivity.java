package com.chowlb.gcusedgear;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class ListActivity extends Activity{

	Context local;
	ListActivity listactivity;
	private AdView adView;
	EditText inputSearch;
	LazyAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rsslist);
		adView = new AdView(this);
		adView.setAdUnitId("ca-app-pub-8858215261311943/5955890318");
		adView.setAdSize(AdSize.BANNER);
		 
		RelativeLayout layout = (RelativeLayout)findViewById(R.id.RelativeLayoutList);
		RelativeLayout.LayoutParams rLParam = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		rLParam.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 1);
		layout.addView(adView, rLParam);
		AdRequest adRequest = new AdRequest.Builder().build();
		adView.loadAd(adRequest);
		
		inputSearch = (EditText) findViewById(R.id.inputSearch);
		
		local = this;
		listactivity = this;
		
		GetRSSDataTask task = new GetRSSDataTask();
		
		String urlType = getIntent().getStringExtra("message"); 
		
		String url = "";
		if(urlType.equalsIgnoreCase("guitar")) {
			url = "http://used.guitarcenter.com/usedGear/usedListings_rss_cat_37.xml";
		}else if(urlType.equalsIgnoreCase("bass")) {
			url = "http://used.guitarcenter.com/usedGear/usedListings_rss_cat_38.xml";
		}else if(urlType.equalsIgnoreCase("drums")) {
			url = "http://used.guitarcenter.com/usedGear/usedListings_rss_cat_39.xml";
		}else if(urlType.equalsIgnoreCase("live sound")) {
			url = "http://used.guitarcenter.com/usedGear/usedListings_rss_cat_40.xml";
		}else if(urlType.equalsIgnoreCase("amps")) {
			url = "http://used.guitarcenter.com/usedGear/usedListings_rss_cat_41.xml";
		}else if(urlType.equalsIgnoreCase("keyboards")) {
			url = "http://used.guitarcenter.com/usedGear/usedListings_rss_cat_42.xml";
		}else if(urlType.equalsIgnoreCase("recording")) {
			url = "http://used.guitarcenter.com/usedGear/usedListings_rss_cat_43.xml";
		}else if(urlType.equalsIgnoreCase("dj")) {
			url = "http://used.guitarcenter.com/usedGear/usedListings_rss_cat_44.xml";
		}else if(urlType.equalsIgnoreCase("accessories")) {
			url = "http://used.guitarcenter.com/usedGear/usedListings_rss_cat_50.xml";
		}else if(urlType.equalsIgnoreCase("wind instruments")) {
			url = "http://used.guitarcenter.com/usedGear/usedListings_rss_cat_60.xml";
		}else if(urlType.equalsIgnoreCase("strings")) {
			url = "http://used.guitarcenter.com/usedGear/usedListings_rss_cat_54.xml";
		}
		
		setTitle("");
		setTitle(urlType);
		task.execute(url);
		
		
		inputSearch.addTextChangedListener(new TextWatcher() {
            
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
            	if(ListActivity.this.adapter != null) {
	            	if(cs != null) {
	            		ListActivity.this.adapter.getFilter().filter(cs);          
	            	}	
            	}
            }
             
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                    int arg3) {
                // TODO Auto-generated method stub
                 
            }
             
            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub                          
            }
        });
		
		
	}
	
	private class GetRSSDataTask extends AsyncTask<String, Void, List<RssItem>>{

		@Override
		protected List<RssItem> doInBackground(String... urls) {
			try {
				RssReader rssReader = new RssReader(urls[0]);
				return rssReader.getItems();
			}catch(Exception e) {
				Log.e("chowlb", e.getMessage());
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(List<RssItem> result) {
			if(result != null && !result.isEmpty()) {
				ListView gcItems = (ListView) findViewById(R.id.gcListView);
				adapter = new LazyAdapter(local, result);
				//add the footer before adding the adapter, else the footer will not load!
				//View footerView = ((LayoutInflater) local.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer, null, false);
				//gcItems.addFooterView(footerView);
				gcItems.setAdapter(adapter);
				gcItems.setOnItemClickListener(new ListListener(result, listactivity));
				
//				gcItems.setOnScrollListener(new OnScrollListener() {
//					
//					@Override
//					public void onScrollStateChanged(AbsListView view, int scrollState) {
//						// TODO Auto-generated method stub
//						
//					}
//					
//					@Override
//					public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//						int lastInScreen = firstVisibleItem + visibleItemCount;
//						
//						if((lastInScreen == totalItemCount) && !(loadingMore)) {
//							Thread thread = new Thread(null, loadMoreListItems);
//							thread.start();
//						}
//						
//					}
//				});
//				
			}else {
				
				Toast.makeText(local, "Could not retrieve items.", Toast.LENGTH_LONG).show();
			}
		}
		
	}
}
