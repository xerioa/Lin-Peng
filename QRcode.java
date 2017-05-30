package com.example.vibrator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;

public class QRcode extends Activity implements OnClickListener{
	private Button btn = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.qr);
		btn=(Button)findViewById(R.id.btn_bbaacckk);
		btn.setOnClickListener(this);

	}
	public void onClick(View v){
		Intent intent = new Intent();
		intent.setClass(QRcode.this,MainActivity.class);
		QRcode.this.startActivity(intent);
	}
}
