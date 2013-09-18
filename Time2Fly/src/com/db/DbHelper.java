package com.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper{

	public static final String DATABASE_NAME = "Time2FlyDb";
	public static final int CURRENT_DATABASE_VERSION = 1;
	
	public DbHelper(Context context) {
		super(context, DATABASE_NAME, null, CURRENT_DATABASE_VERSION);
	}

	

	
	

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TabsTable.CREATE_TABLE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
