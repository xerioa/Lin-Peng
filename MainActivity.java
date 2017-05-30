package com.example.vibrator;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{
	

public boolean onKeyDown(int keyCode, KeyEvent event) {
        
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            // Show home screen when pressing "back" button,
            //  so that this app won't be closed accidentally
            Intent intentHome = new Intent(Intent.ACTION_MAIN);
            intentHome.addCategory(Intent.CATEGORY_HOME);
            startActivity(intentHome);
            
            return true;
        }
        
        return super.onKeyDown(keyCode, event);
    }
	private RelativeLayout rl = null;
	private LocationManager lm;
	
	private TextView t= null;
	private Button btn = null;
	private Button btn2= null;
	private Button btn3= null;
	private Button btn_gps = null;
	private Button btn_qr = null;
	private Button btn_ct = null;
	private MediaPlayer mplay;
	private WifiManager mWifi = null; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		try{
		mplay = MediaPlayer.create(this,R.raw.backmusic);
		mplay.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mplay.setLooping(true);
		}catch(Exception e){
			e.printStackTrace();
		}
		mWifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		lm=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
		t= (TextView)findViewById(R.id.text);
		btn=(Button)findViewById(R.id.btn_start);
		btn2=(Button)findViewById(R.id.btn_row);
		btn3=(Button)findViewById(R.id.btn_exit);
		btn_gps = (Button)findViewById(R.id.btn_GPS);
		btn_qr = (Button)findViewById(R.id.btn_qr);
		btn_ct = (Button)findViewById(R.id.btn_controlx);
		btn.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		btn_gps.setOnClickListener(this);
		btn_qr.setOnClickListener(this);
		btn_ct.setOnClickListener(this);
		rl=(RelativeLayout)findViewById(R.id.main_relativelayout);
	}
	
public void onClick(View v) {
	switch(v.getId()){
		case R.id.btn_row:	
			Intent intent = new Intent();
			intent.setClass(MainActivity.this,Rower.class);
			MainActivity.this.startActivity(intent);
			break;
		case R.id.btn_start:
			Intent intent2 = new Intent();
			intent2.setClass(MainActivity.this,SecondPage.class);
			MainActivity.this.startActivity(intent2);
			break;
		case R.id.btn_exit:
			Intent intentHome = new Intent(Intent.ACTION_MAIN);
            intentHome.addCategory(Intent.CATEGORY_HOME);
            startActivity(intentHome);
			break;
		case R.id.btn_GPS:
			Intent intentgps = new Intent();
			intentgps.setClass(MainActivity.this,Gps.class);
			MainActivity.this.startActivity(intentgps);
			break;
		case R.id.btn_qr:
			Intent intentqr = new Intent();
			intentqr.setClass(MainActivity.this,QRcode.class);
			MainActivity.this.startActivity(intentqr);
			break;
		case R.id.btn_controlx:
			Intent intentct = new Intent();
			intentct.setClass(MainActivity.this,Control.class);
			MainActivity.this.startActivity(intentct);
			break;
			}
		

		}
	@Override
	protected void onResume() {
		super.onResume();
		
		Global globalkey = (Global)MainActivity.this.getApplicationContext();
		if(globalkey.key==true)
		{
		rl.setBackgroundResource(R.drawable.backjp);
		}
		else
		{
		rl.setBackgroundResource(R.drawable.back);
		}
		
		if(mWifi.isWifiEnabled()){
			Toast.makeText(MainActivity.this,"目前正連上wifi\n"+"名稱:"+mWifi.getConnectionInfo().getSSID()+"\n"+"連線ID:" + mWifi.getConnectionInfo().getNetworkId()+"\n"+"連線位置:"+mWifi.getConnectionInfo().getIpAddress(),Toast.LENGTH_SHORT).show();
			rl.setBackgroundResource(R.drawable.backjp);
		}
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
		

		mplay.start();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mplay.pause();
	}
	@Override
	protected void onDestroy() {
		//TODO Auto-generated method stub
		super.onDestroy();
		mplay.release();
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
