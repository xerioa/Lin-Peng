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
			Toast.makeText(Gps.this,"�Х��}gps",Toast.LENGTH_SHORT).show();//�u�X�T����ܭn�}gps
			openGPS2();
		}
		Location lc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);//�]�ۨ���m
		updateShow(lc);
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,2000,8,new LocationListener(){

			@Override
			public void onLocationChanged(Location location) {//��ϥΪ̦�m���ܮ�
				// TODO Auto-generated method stub
				updateShow(location);
				
			}

			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {//��ϥΪ�provider���A���ܮ�
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onProviderEnabled(String provider) {//�P�_provider��ϥή�
				// TODO Auto-generated method stub
				updateShow(lm.getLastKnownLocation(provider));
			}

			@Override
			public void onProviderDisabled(String provider) {//provider����ϥή�
				// TODO Auto-generated method stub
				updateShow(null);
			}
		});
	}
	private void updateShow(Location location){//��ܧ�s����k
		if(location != null){
		StringBuilder sb = new StringBuilder();
		sb.append("�ثe��m�G\n");
		sb.append("�g�סG"+location.getLongitude()+"\n");
		sb.append("�n�סG"+location.getLatitude()+"\n");
		sb.append("���סG"+location.getAltitude()+"\n");
		sb.append("�t�סG"+location.getSpeed()+"\n");
		sb.append("��V�G"+location.getBearing()+"\n");
		sb.append("�w���סG"+location.getAccuracy()+"\n");
		if(location.getLatitude()>23 && location.getLatitude()<25 && location.getLongitude()>120 && location.getLongitude()<122){
			Toast.makeText(Gps.this,"�z�ثe���x�W",Toast.LENGTH_SHORT).show();
			ll.setBackgroundResource(R.drawable.back);//���I��
		}
		if(location.getLatitude()>33.5 && location.getLatitude()<36.5 && location.getLongitude()>132.5 && location.getLongitude()<138.5){
			Toast.makeText(Gps.this,"�z�ثe���饻",Toast.LENGTH_SHORT).show();
			text.setTextColor(Color.parseColor("#000000"));
			ll.setBackgroundResource(R.drawable.backjp);
		}
		text.setText(sb.toString());
		}else
			text.setText("");
	}
	
	
	private boolean isGpsAble(LocationManager lm){//�P�_�O�_���}gps
		return lm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER) ? true : false;
	}
	private void openGPS2() {//�۰ʶ]��gps�}�ҵ���
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivityForResult(intent, 0);
    }
	@Override
	public void onClick(View v) {//��s���s
		// TODO Auto-generated method stub
		updateShow(lm.getLastKnownLocation(LocationManager.GPS_PROVIDER));
		
	}
}
