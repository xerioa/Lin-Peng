package com.example.vibrator;

import java.util.Map;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.inputmethodservice.Keyboard.Row;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Final extends Activity{
	
public boolean onKeyDown(int keyCode, KeyEvent event) {//按下back之後會發生什麼事
        
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {	
        	Toast.makeText(Final.this,"請輸入大名",Toast.LENGTH_SHORT).show();
        }
        
        return false;
    }
	
	private double t = 0;
	private EditText edit = null;
	private TextView score = null;
	private Button btn = null;

	private RelativeLayout rl = null;
	private LocationManager lm;
	
	private SQLiteDatabase db;
	private MyDBHelper myhelper;
	private Context mContext;
	
	String strname;
	String strscore;
	String str1;
	int d;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.finalfly);
		mContext = Final.this;
		myhelper = new MyDBHelper(mContext,"my.db",null,2);
		edit=(EditText)findViewById(R.id.edit_final);
		score=(TextView)findViewById(R.id.text_score);
		btn=(Button)findViewById(R.id.btn_final);
		
		lm=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
		rl=(RelativeLayout)findViewById(R.id.final_layout);
		
		btn.setOnClickListener(new Listener4());
		Intent intentget = getIntent();
			str1 = intentget.getStringExtra("score");
			d = Integer.parseInt(str1);
			score.setText(""+d);
	}
	class Listener4 implements OnClickListener{

		@Override
		public void onClick(View v) {
			if(edit.getText().length()==0)
			{
				Toast.makeText(Final.this,"請輸入大名",Toast.LENGTH_SHORT).show();
			}
			else{
			db = myhelper.getWritableDatabase();
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			String str2 = String.valueOf(d);
			intent.setClass(Final.this,MainActivity.class);
			strname = edit.getText().toString();
			strscore = str2;
			ContentValues value = new ContentValues();
			value.put("name",strname);
			value.put("score",strscore);
			db.insert("person",null,value);
			Final.this.startActivity(intent);
			}
		}
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		Global global = (Global)Final.this.getApplicationContext();
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
