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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



public class Medium extends Activity{
	
public boolean onKeyDown(int keyCode, KeyEvent event) {
        
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {	
        	Toast.makeText(Medium.this,"你還在進行遊戲",Toast.LENGTH_SHORT).show();
        }
        
        return false;
    }
	private int vi;
	private float soundnum;
	private int k = 0;
	private boolean G = false;
	private TextView timenum = null;
	private TextView text = null;
	private Button btn = null;
	private ImageView mouse;
	private Handler handler ;
	private SoundPool soundpool;
	private int soundid;
	public int[][] position = new int[][]{{231,235},{124,549},{521,256},{543,718},{119,245},{432,292},{102,858}};
	private MediaPlayer mplay;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mediun);
		Intent intget = getIntent();
		String str2 = intget.getStringExtra("score");
		final int s = Integer.parseInt(str2);
		mouse=(ImageView)findViewById(R.id.image3);
		text = (TextView)findViewById(R.id.text_number2);
		timenum = (TextView)findViewById(R.id.text_timenum2);
		btn =(Button)findViewById(R.id.btn_mediumtohard);
		btn.setVisibility(View.INVISIBLE);
		text.setText(""+s);
		try{
			soundpool = new SoundPool.Builder().build();
			soundid=soundpool.load(Medium.this,R.raw.hit,1);
			mplay = MediaPlayer.create(this,R.raw.backmuiscice);
			mplay.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mplay.setLooping(true);
			mouse.setOnTouchListener(new OnTouchListener(){
				
				
				
			public boolean onTouch(View v,MotionEvent event){
					v.setVisibility(View.INVISIBLE);
					Vibrator myVibrator = (Vibrator)getApplication().getSystemService(Service.VIBRATOR_SERVICE);
					myVibrator.vibrate(vi);
					soundpool.play(soundid,soundnum,soundnum,0,0,1);
					k++;
					text.setText(""+(s+k));
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
						Thread.sleep((index*100)+300);
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
					G =true;
					timenum.setText("0");
					mouse.setVisibility(View.INVISIBLE);
					btn.setVisibility(View.VISIBLE);
					btn.setOnTouchListener(new OnTouchListener(){
						public boolean onTouch(View v,MotionEvent event){
						int total = k+s;
						String str3 = String.valueOf(total);
						Intent intent = new Intent();
						intent.setClass(Medium.this,Hard.class);
						intent.putExtra("score",str3);
						Medium.this.startActivityForResult(intent,0);
						return false;
						}
					});
				}
			
		}.start();
	t.start();
	}
	@Override
	protected void onResume() {
		super.onResume();
		Global global = (Global)Medium.this.getApplicationContext();
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