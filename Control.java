package com.example.vibrator;

import android.app.Activity;
import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class Control extends Activity implements OnClickListener{

	private MyDBHelper myhelper;
	private SQLiteDatabase db;
	private Context mContext;
	private RelativeLayout rl;
private TextView textv1 = null;
private TextView textv2 = null;
private SeekBar sb1 = null;
private SeekBar sb2 = null;
private RadioGroup rg = null;
private RadioGroup rgsound = null;
private AudioManager mg = null;
private Button btn = null;
private Button btn_back = null;
private ImageView image = null;
private int max,now;
private MediaPlayer mplay;
private SoundPool soundpool;
private int soundid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.control);

		try{
			soundpool = new SoundPool.Builder().build();
			
			soundid=soundpool.load(Control.this,R.raw.hit,1);
			mplay = MediaPlayer.create(this,R.raw.backmusic);
			mplay.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mplay.setLooping(true);
			}catch(Exception e){
				e.printStackTrace();
			}
		mContext = Control.this;
		myhelper = new MyDBHelper(mContext,"my.db",null,2);
		db = myhelper.getWritableDatabase();
		textv1 = (TextView)findViewById(R.id.progress);
		sb1 = (SeekBar)findViewById(R.id.volume);
		rg = (RadioGroup)findViewById(R.id.radioGroup);
		rl = (RelativeLayout)findViewById(R.id.control_relativelayout);
		rgsound = (RadioGroup)findViewById(R.id.radioGroupsound);
		mg = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);
		max = mg.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		now = mg.getStreamVolume(AudioManager.STREAM_MUSIC);
		btn = (Button)findViewById(R.id.btn_delete);
		btn_back = (Button)findViewById(R.id.controlbtn_back);
		image = (ImageView)findViewById(R.id.btn_background);
		sb1.setProgress(100);
		sb1.setMax(max);
		sb1.setProgress(now);
		image.setOnTouchListener(new OnTouchListener(){
			public boolean onTouch(View v, MotionEvent event){
				Global globalkey = (Global)Control.this.getApplicationContext();
				if(globalkey.key==false)
				{
				globalkey.setkey(true);
				rl.setBackgroundResource(R.drawable.backjp);
				}
				else
				{
				globalkey.setkey(false);
				rl.setBackgroundResource(R.drawable.back);
				}			
				return false;
			}
		});
		db = myhelper.getWritableDatabase();
		btn.setOnClickListener(this);
		btn_back.setOnClickListener(this);
		rg.setOnCheckedChangeListener(new Listener1());
		rgsound.setOnCheckedChangeListener(new Listener2());
		sb1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
		
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				mg.setStreamVolume(AudioManager.STREAM_MUSIC,progress*6,0);
				textv1.setText("音量:"+ progress );
				
			}
		});
		
	}
	class Listener1 implements OnCheckedChangeListener{
		Global global = (Global)Control.this.getApplicationContext();
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			switch(checkedId){
			case R.id.btn_yes:
				Vibrator myVibrator = (Vibrator)getApplication().getSystemService(Service.VIBRATOR_SERVICE);
				myVibrator.vibrate(300);
				global.setvi(300);
				break;
			case R.id.btn_no:
				global.setvi(0);
				break;
			}
			
		}
		
	}
	class Listener2 implements OnCheckedChangeListener{
		Global global2 = (Global)Control.this.getApplicationContext();
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			
			
			// TODO Auto-generated method stub
			switch(checkedId){
			
			case R.id.btn_yess1:
				float f = 0.5f;
				soundpool.play(soundid,f,f,0,0,1);
				global2.setf(f);
				
				
				
				break;
			case R.id.btn_nos1:
				float f2 = 0;
				global2.setf(f2);
				
				break;
			}
			
		}
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.btn_delete:
			db.delete("person",null,null);
			Toast.makeText(Control.this,"排行榜已清除完畢",Toast.LENGTH_SHORT).show();
			break;
		case R.id.controlbtn_back:
			Intent intent = new Intent();
            intent.setClass(Control.this,MainActivity.class);
            startActivity(intent);
            break;
		}
	}
	@Override
	protected void onResume() {
		super.onResume();
		Global global3 = (Global)Control.this.getApplicationContext();
		// TODO Auto-generated method stub
		
		if(global3.key() == true){
			rl.setBackgroundResource(R.drawable.backjp);
		}
		else
			rl.setBackgroundResource(R.drawable.back);
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
