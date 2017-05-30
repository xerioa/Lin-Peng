package com.example.vibrator;
import java.io.*;
import java.util.Random;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Hard extends Activity{
	
public boolean onKeyDown(int keyCode, KeyEvent event) {
        
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {	
        	Toast.makeText(Hard.this,"你還在進行遊戲",Toast.LENGTH_SHORT).show();
        }
        
        return false;
    }
	private int vi;
	private float soundnum;
	private int j = 0;
	private int i = 0;
	private TextView timenum = null;
	private TextView text = null;
	private ImageView mouse;
	private ImageView mouse2;
	private Handler handler2;
	private Handler handler ;
	private SoundPool soundpool;
	private int soundid;
	private MediaPlayer mplay;
	public int[][] position = new int[][]{{203,255},{124,549},{521,256},{543,718},{119,245},{432,292},{102,858}};
@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hard);
		
		Intent intget = getIntent();
		String str2 = intget.getStringExtra("score");
		final int s = Integer.parseInt(str2);
		mouse=(ImageView)findViewById(R.id.image5);
		mouse2=(ImageView)findViewById(R.id.image6);
		text = (TextView)findViewById(R.id.text_number3);
		timenum = (TextView)findViewById(R.id.text_timenum3);
		text.setText(""+s);
		try{
			soundpool = new SoundPool.Builder().build();
			soundid = soundpool.load(Hard.this,R.raw.hit,1);
			mplay = MediaPlayer.create(Hard.this,R.raw.backfire);
			mplay.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mplay.setLooping(true);
			mouse.setOnTouchListener(new OnTouchListener(){
				public boolean onTouch(View v,MotionEvent event){
					v.setVisibility(View.INVISIBLE);
					Vibrator myVibrator = (Vibrator)getApplication().getSystemService(Service.VIBRATOR_SERVICE);
					myVibrator.vibrate(vi);
					soundpool.play(soundid,soundnum,soundnum,0,0,1);
					i++;
					text.setText(""+(s+i+j));
					return false;
				}
			});
			mouse2.setOnTouchListener(new OnTouchListener(){
				public boolean onTouch(View v,MotionEvent event){
					v.setVisibility(View.INVISIBLE);
					Vibrator myVibrator = (Vibrator)getApplication().getSystemService(Service.VIBRATOR_SERVICE);
					myVibrator.vibrate(vi);
					j++;
					soundpool.play(soundid,soundnum,soundnum,0,0,1);
					text.setText(""+(s+i+j));
					return false;
				}
			});
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		handler = new Handler(){
			@SuppressLint("NewApi")
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
		handler2 = new Handler(){
			@SuppressLint("NewApi")
			public void handleMessage(Message msg){
				int index = 0;
				if(msg.what==0x101)
				{
					index = msg.arg1;
					mouse2.setX(position[index][0]);
					mouse2.setY(position[index][1]);
					mouse2.setVisibility(View.VISIBLE);
				}
				super.handleMessage(msg);
			}
		};
		final Thread t = new Thread(new Runnable(){
			public void run(){
				int index = 0;
				while(!Thread.currentThread().isInterrupted()){
					index = new Random().nextInt(position.length);
					Message m = handler.obtainMessage();
					m.what = 0x101;//設置消息標誌
					m.arg1 = index;
					handler.sendMessage(m);
					try{
						Thread.sleep(1000);
					}catch(InterruptedException e){
						e.printStackTrace();
					}
				}
			}
		});
		final Thread t2 = new Thread(new Runnable(){
			public void run(){
				int index = 0;
				while(!Thread.currentThread().isInterrupted()){
					index = new Random().nextInt(position.length);
					Message m = handler2.obtainMessage();
					m.what = 0x101;//設置消息標誌
					m.arg1 = index;
					handler2.sendMessage(m);
					try{
						Thread.sleep(index*100);
					}catch(InterruptedException e){
						e.printStackTrace();
					}
				}
			}
		});
		new CountDownTimer(30000,1000){

			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub
				timenum.setText(""+(int)(millisUntilFinished/1000));
			}

			@Override
			public void onFinish() {
				timenum.setText("0");
				int total = s+i+j;
				String str1 = String.valueOf(total);
				Intent intent = new Intent();
				intent.setClass(Hard.this,Final.class);
				intent.putExtra("score",str1);
				Hard.this.startActivity(intent);
				
			}
			
		}.start();
	t2.start();
	t.start();
	}
@Override
protected void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	Global global = (Global)Hard.this.getApplicationContext();
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
