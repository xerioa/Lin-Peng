package com.example.vibrator;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper{

	public MyDBHelper(Context context, String name, CursorFactory factory, int version) {
		super(context,"my.db", null, 2);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE person(name varchar(10),score int(10)");
	}

	@Override
	 public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
