package com.example.vibrator;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Map;

import android.app.Activity;
import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Rower extends Activity implements OnClickListener{
		@Override
 public boolean onKeyDown(int keyCode, KeyEvent event) {
        
        if (keyCode == KeyEvent.KEYCODE_BACK)//按下back之後會發生什麼事
        {
            Intent intentHome = new Intent();
            intentHome.setClass(Rower.this,MainActivity.class);
            startActivity(intentHome);
            return true;
        }
        
        return super.onKeyDown(keyCode, event);
    }
		private TextView n1 = null;
		private TextView s1 = null;
		private Button btn = null;
		private MyDBHelper myhelper;
		private SQLiteDatabase db;
		private Context mContext;
		private StringBuilder sb;
		private LocationManager lm;
		private LinearLayout ll = null;

		

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.row);
		mContext = Rower.this;
		myhelper = new MyDBHelper(mContext,"my.db",null,2);
		db = myhelper.getWritableDatabase();
		n1 = (TextView)findViewById(R.id.row_name1);
		btn = (Button)findViewById(R.id.btn_rowback);
		lm=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
		btn.setOnClickListener(this);
		sb = new StringBuilder();
		ll=(LinearLayout)findViewById(R.id.layout_row);
		Cursor cursor = db.query("person",new String[]{"name,score"},null,null,null,null,"score desc","5");
		if(cursor.moveToFirst()){
			do{
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String scores =cursor.getString(cursor.getColumnIndex("score"));
			sb.append("使用者："+name+"，共得了："+scores+"分\n");
			n1.setText(sb.toString());
			}while(cursor.moveToNext());
		}
		cursor.close();

		
	}
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.btn_rowback:
				Intent intent = new Intent();
	            intent.setClass(Rower.this,MainActivity.class);
	            startActivity(intent);
	            break;

			}
			
		}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Global global = (Global)Rower.this.getApplicationContext();
		if(global.key()==true)
			ll.setBackgroundResource(R.drawable.backjp);
		else
			ll.setBackgroundResource(R.drawable.back);
		
		
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
				ll.setBackgroundResource(R.drawable.back);
			}
			if(lc.getLatitude()>33.5 && lc.getLatitude()<36.5 && lc.getLongitude()>132.5 && lc.getLongitude()<138.5){
				ll.setBackgroundResource(R.drawable.backjp);
			}else
			{
				ll.setBackgroundResource(R.drawable.back);
			}
		}else{

	}
}
	
}
