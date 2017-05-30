package com.example.vibrator;

import android.app.Application;

public class Global extends Application{
	public float f = 0.5f;
	public int vi = 300;
	public boolean key = false;
	public void setf(float f){
		this.f=f;
	}
	public float getf(){
		return this.f;
	}
	public void setvi(int vi){
		this.vi = vi;
	}
	public int getvi(){
		return this.vi;
	}
	public void setkey(boolean key){
		this.key = key;
	}
	public boolean key(){
		return key;
	}
}
