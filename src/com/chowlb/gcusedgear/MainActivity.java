package com.chowlb.gcusedgear;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;


public class MainActivity extends Activity {
	//reference to local object
	private AdView adView;
	public final static String EXTRA_MESSAGE = "com.chowlb.gcusedgear.MESSAGE";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		 adView = new AdView(this);
		 adView.setAdUnitId("ca-app-pub-8858215261311943/5955890318");
		 adView.setAdSize(AdSize.BANNER);
		 
		 RelativeLayout layout = (RelativeLayout)findViewById(R.id.RelativeLayoutMain);
		 RelativeLayout.LayoutParams rLParam = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		 rLParam.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 1);
		 layout.addView(adView, rLParam);
		 AdRequest adRequest = new AdRequest.Builder().build();
		 adView.loadAd(adRequest);
	}
	
	public void loadRss(View view) {
		final ConnectivityManager conMgr =  (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
		if (activeNetwork != null && activeNetwork.isConnected()) {
			Button clickedButton = (Button) findViewById(view.getId());
			Intent intent = new Intent(this, ListActivity.class);
			String message = (String) clickedButton.getText();
			intent.putExtra("message", message);
			startActivity(intent);
		} 
		else {
			Toast.makeText(this, "No Network Access Found. Check Internet Settings" , Toast.LENGTH_LONG).show();
		} 
		
	}
	

}
