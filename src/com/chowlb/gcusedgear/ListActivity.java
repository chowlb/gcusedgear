package com.chowlb.gcusedgear;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
	private ProgressDialog mProgressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rsslist);
		adView = new AdView(this);
		adView.setAdUnitId("ca-app-pub-8858215261311943/5955890318");
		adView.setAdSize(AdSize.BANNER);
		 
		RelativeLayout layout = (RelativeLayout)findViewById(R.id.adMainLayoutList);
		layout.addView(adView);
		AdRequest adRequest = new AdRequest.Builder().build();
		adView.loadAd(adRequest);
		
		inputSearch = (EditText) findViewById(R.id.inputSearch);
		
		local = this;
		listactivity = this;
		
		GetRSSDataTask task = new GetRSSDataTask();
		
		String urlType = getIntent().getStringExtra("message"); 
		
		List<String> url = new ArrayList<String>();
		if(urlType.equalsIgnoreCase("guitar") || urlType.equalsIgnoreCase("all")) {
			url.add("http://used.guitarcenter.com/usedGear/usedListings_rss_cat_37.xml");
		}
		if(urlType.equalsIgnoreCase("bass") || urlType.equalsIgnoreCase("all")) {
			url.add("http://used.guitarcenter.com/usedGear/usedListings_rss_cat_38.xml");
		}
		if(urlType.equalsIgnoreCase("drums") || urlType.equalsIgnoreCase("all")) {
			url.add("http://used.guitarcenter.com/usedGear/usedListings_rss_cat_39.xml");
		}
		if(urlType.equalsIgnoreCase("live sound") || urlType.equalsIgnoreCase("all")) {
			url.add("http://used.guitarcenter.com/usedGear/usedListings_rss_cat_40.xml");
		}
		if(urlType.equalsIgnoreCase("amps") || urlType.equalsIgnoreCase("all")) {
			url.add("http://used.guitarcenter.com/usedGear/usedListings_rss_cat_41.xml");
		}
		if(urlType.equalsIgnoreCase("keyboards") || urlType.equalsIgnoreCase("all")) {
			url.add("http://used.guitarcenter.com/usedGear/usedListings_rss_cat_42.xml");
		}
		if(urlType.equalsIgnoreCase("recording") || urlType.equalsIgnoreCase("all")) {
			url.add("http://used.guitarcenter.com/usedGear/usedListings_rss_cat_43.xml");
		}
		if(urlType.equalsIgnoreCase("dj") || urlType.equalsIgnoreCase("all")) {
			url.add("http://used.guitarcenter.com/usedGear/usedListings_rss_cat_44.xml");
		}
		if(urlType.equalsIgnoreCase("accessories") || urlType.equalsIgnoreCase("all")) {
			url.add("http://used.guitarcenter.com/usedGear/usedListings_rss_cat_50.xml");
		}
		if(urlType.equalsIgnoreCase("wind instruments") || urlType.equalsIgnoreCase("all")) {
			url.add("http://used.guitarcenter.com/usedGear/usedListings_rss_cat_60.xml");
		}
		if(urlType.equalsIgnoreCase("strings") || urlType.equalsIgnoreCase("all")) {
			url.add("http://used.guitarcenter.com/usedGear/usedListings_rss_cat_54.xml");
		}
		
		setTitle("");
		setTitle(urlType);
		if(!url.isEmpty()) {
			//Log.e("chowlb", "URL LIST IS: " + url.size());
			task.execute(url);
		}
		
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
                 
            }
             
            @Override
            public void afterTextChanged(Editable arg0) {                      
            }
        });
		
		
	}
	
	public class GetRSSDataTask extends AsyncTask<List<String>, Void, List<RssItem>>{

		
		
		 @Override
	   	protected void onPreExecute() {
	   		  mProgressDialog = new ProgressDialog(ListActivity.this);
	 		  mProgressDialog =ProgressDialog.show(ListActivity.this, "", "Loading Items, Please Wait",true,false);
	 		  super.onPreExecute();
	   }
		
		@Override
		protected List<RssItem> doInBackground(List<String>... urls) {
			try {
				List<String> urlList = urls[0];
				List<RssItem> rssList = new ArrayList<RssItem>();
				for(int i=0; i<urlList.size();i++) {
					RssReader rssReader = new RssReader(urlList.get(i));
					rssList.addAll(rssReader.getItems());
					//Log.e("chowlb", "RSS LIST SIZE: " + rssList.size());
				}
				
				return rssList;
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
				gcItems.setAdapter(adapter);
				gcItems.setOnItemClickListener(new ListListener(result, listactivity));
								
			}else {
				
				Toast.makeText(local, "Could not retrieve items.", Toast.LENGTH_LONG).show();
			}
			if (mProgressDialog != null || mProgressDialog.isShowing()){
		         mProgressDialog.dismiss();
			 }
		}
		
	}
}
