package com.example.vibrator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class SecondPage extends Activity implements OnClickListener{
	private Button btn = null;//簡單按鈕設置
	private Button btn2 = null;//中等按鈕設置
	private Button btn3 = null;//困難按鈕設置
	private Button btn4 = null;
	private RelativeLayout rl = null;
	private LocationManager lm;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second);
		lm=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
		btn = (Button)findViewById(R.id.btn_second);
		btn2 = (Button)findViewById(R.id.btn2_second);
		btn3=(Button)findViewById(R.id.btn3_second);
		btn.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		rl=(RelativeLayout)findViewById(R.id.second_rela);
	}
		@Override
public void onClick(View v) {		
			// TODO Auto-generated method stub
	switch(v.getId()){
		case R.id.btn_second:
			Intent intent = getIntent();
			intent.setClass(SecondPage.this,Easy.class);
			SecondPage.this.startActivity(intent);
			break;
		case R.id.btn2_second:
			Toast.makeText(SecondPage.this,"尚未解鎖",Toast.LENGTH_SHORT).show();
			break;
		case R.id.btn3_second:
			Toast.makeText(SecondPage.this,"尚未解鎖",Toast.LENGTH_SHORT).show();
			break;
			}
	
		}
		@Override
		protected void onResume() {
			
			
			// TODO Auto-generated method stub
			super.onResume();
			
			Global global = (Global)SecondPage.this.getApplicationContext();
			// TODO Auto-generated method stub
			
			if(global.key() == true){
				rl.setBackgroundResource(R.drawable.backjp);
			}
			else
				rl.setBackgroundResource(R.drawable.back);
			lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,2000,8,new LocationListener(){
				@Override
				public void onLocationChanged(Location location) {
					// TODO Auto-generated method stub
					updateShow(location);
				}
				@Override
				public void onStatusChanged(String provider, int status, Bundle extras) {
					// TODO Auto-generated method stub
				}
				@Override
				public void onProviderEnabled(String provider) {
					// TODO Auto-generated method stub
					updateShow(lm.getLastKnownLocation(provider));
				}

				@Override
				public void onProviderDisabled(String provider) {
					// TODO Auto-generated method stub
					updateShow(null);
				}
			});
		}
		private void updateShow(Location location){
			if(location != null){
				Location lc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
				if(lc.getLatitude()>24 && lc.getLatitude()<25 && lc.getLongitude()>120 && lc.getLongitude()<122){
					rl.setBackgroundResource(R.drawable.back);
				}
				if(lc.getLatitude()>33.5 && lc.getLatitude()<36.5 && lc.getLongitude()>132.5 && lc.getLongitude()<138.5){
					rl.setBackgroundResource(R.drawable.backjp);
				}else
				{
					rl.setBackgroundResource(R.drawable.back);
				}
			}else{

	}
	}
	}
