package com.example.vibrator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Gps extends Activity implements OnClickListener{
	private LinearLayout ll = null;
	private LocationManager lm;
	private TextView text;
	private Button btn = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gps);
		text = (TextView)findViewById(R.id.gps_show);
		lm=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
		btn = (Button)findViewById(R.id.btn_CHANGE);
		ll=(LinearLayout)findViewById(R.id.layout_gps);
		btn.setOnClickListener(this);
		if(!isGpsAble(lm)){
			Toast.makeText(Gps.this,"請打開gps",Toast.LENGTH_SHORT).show();//彈出訊息表示要開gps
			openGPS2();
		}
		Location lc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);//設自身位置
		updateShow(lc);
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,2000,8,new LocationListener(){

			@Override
			public void onLocationChanged(Location location) {//當使用者位置改變時
				// TODO Auto-generated method stub
				updateShow(location);
				
			}

			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {//當使用者provider狀態改變時
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onProviderEnabled(String provider) {//判斷provider能使用時
				// TODO Auto-generated method stub
				updateShow(lm.getLastKnownLocation(provider));
			}

			@Override
			public void onProviderDisabled(String provider) {//provider不能使用時
				// TODO Auto-generated method stub
				updateShow(null);
			}
		});
	}
	private void updateShow(Location location){//顯示更新的方法
		if(location != null){
		StringBuilder sb = new StringBuilder();
		sb.append("目前位置：\n");
		sb.append("經度："+location.getLongitude()+"\n");
		sb.append("緯度："+location.getLatitude()+"\n");
		sb.append("高度："+location.getAltitude()+"\n");
		sb.append("速度："+location.getSpeed()+"\n");
		sb.append("方向："+location.getBearing()+"\n");
		sb.append("定位精度："+location.getAccuracy()+"\n");
		if(location.getLatitude()>23 && location.getLatitude()<25 && location.getLongitude()>120 && location.getLongitude()<122){
			Toast.makeText(Gps.this,"您目前位於台灣",Toast.LENGTH_SHORT).show();
			ll.setBackgroundResource(R.drawable.back);//更改背景
		}
		if(location.getLatitude()>33.5 && location.getLatitude()<36.5 && location.getLongitude()>132.5 && location.getLongitude()<138.5){
			Toast.makeText(Gps.this,"您目前位於日本",Toast.LENGTH_SHORT).show();
			text.setTextColor(Color.parseColor("#000000"));
			ll.setBackgroundResource(R.drawable.backjp);
		}
		text.setText(sb.toString());
		}else
			text.setText("");
	}
	
	
	private boolean isGpsAble(LocationManager lm){//判斷是否有開gps
		return lm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER) ? true : false;
	}
	private void openGPS2() {//自動跑到gps開啟視窗
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivityForResult(intent, 0);
    }
	@Override
	public void onClick(View v) {//更新按鈕
		// TODO Auto-generated method stub
		updateShow(lm.getLastKnownLocation(LocationManager.GPS_PROVIDER));
		
	}
}
