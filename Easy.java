package com.example.vibrator;
import java.io.*;

import java.lang.*;
import java.util.Random;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



public class Easy extends Activity{
	
public boolean onKeyDown(int keyCode, KeyEvent event) {//按下back之後會發生什麼事
        
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {	
        	Toast.makeText(Easy.this,"你還在進行遊戲",Toast.LENGTH_SHORT).show();
        }
        
        return false;
    }

	private SQLiteDatabase db;
	private MyDBHelper myhelper;
	private Context mContext;
	
	private int vi;
	private float soundnum;
	private int i = 0;
	private boolean G = false;                 //用來判斷該不該讓地鼠繼續出現
	private TextView timenum = null;
	private TextView text = null;
	private ImageView mouse;
	private Handler handler ;
	private SoundPool soundpool;               //音效
	private int soundid;
	private Button btn = null;
	public int[][] position = new int[][]{{231,235},{124,549},{521,256},{543,718},{119,245},{432,292},{102,858}};
	private MediaPlayer mplay;                 //背景音樂
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		
		
		setContentView(R.layout.easy);
		mContext = Easy.this;
		myhelper = new MyDBHelper(mContext,"my.db",null,2);
		
		
		
		mouse=(ImageView)findViewById(R.id.image1);
		text = (TextView)findViewById(R.id.text_number);
		timenum = (TextView)findViewById(R.id.text_timenum);
		btn = (Button)findViewById(R.id.btn_easytomedium);
		btn.setVisibility(View.INVISIBLE);
		try{
			
			
			soundpool = new SoundPool.Builder().build();
			
			soundid=soundpool.load(Easy.this,R.raw.hit,1);
			mplay = MediaPlayer.create(this,R.raw.backmusic);
			mplay.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mplay.setLooping(true);
			mouse.setOnTouchListener(new OnTouchListener(){
				
				
				
				public boolean onTouch(View v,MotionEvent event){
					v.setVisibility(View.INVISIBLE);
					Vibrator myVibrator = (Vibrator)getApplication().getSystemService(Service.VIBRATOR_SERVICE);
					myVibrator.vibrate(vi);
					soundpool.play(soundid,soundnum,soundnum,0,0,1);
					i++;
					text.setText(""+i);
					return false;
				}
			});
			}catch(Exception e){
				e.printStackTrace();
			}
		
		handler = new Handler(){
			public void handleMessage(Message msg){
				int index = 0;
				if(msg.what==0x101)
				{
					index = msg.arg1;
					mouse.setX(position[index][0]);
					mouse.setY(position[index][1]);
					mouse.setVisibility(View.VISIBLE);
				}
				super.handleMessage(msg);
			}
		};

		Thread t = new Thread(new Runnable(){
			public void run(){
				int index = 0;
				while(!G){
					index = new Random().nextInt(position.length);
					Message m = handler.obtainMessage();
					m.what = 0x101;//設置消息標誌
					m.arg1 = index;
					handler.sendMessage(m);
					try{
						Thread.sleep((index*100)+500);
					}catch(InterruptedException e){
						e.printStackTrace();
					}
				}
			}
		});
		new CountDownTimer(25000,1000){

			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub
				timenum.setText(""+(int)(millisUntilFinished/1000));
			}
				
			@Override
			public void onFinish() {
				timenum.setText("0");
				if(i>10){
					G =true;
				mouse.setVisibility(View.INVISIBLE);
				btn.setVisibility(View.VISIBLE);
				btn.setOnTouchListener(new OnTouchListener(){
					public boolean onTouch(View v,MotionEvent event){
					String str1 = String.valueOf(i);
					Intent intent = new Intent();
					intent.setClass(Easy.this,Medium.class);
					intent.putExtra("score",str1);
					Easy.this.startActivityForResult(intent,0);
					return false;
					}
				});
			}
				else if(i<=10){
					timenum.setText("0");
					String str1 = String.valueOf(i);
					Intent intent = new Intent();
					intent.setClass(Easy.this,Final.class);
					intent.putExtra("score",str1);
					Easy.this.startActivityForResult(intent,0);
				}
					
			}
			
		}.start();
	t.start();
	}
	@Override
	protected void onResume() {
		super.onResume();
		Global global = (Global)Easy.this.getApplicationContext();
		soundnum = global.getf();
		vi = global.getvi();
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
		// TODO Auto-generated method stub
		super.onDestroy();
		mplay.release();
	}
}
