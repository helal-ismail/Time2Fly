package com.db;

import android.database.sqlite.SQLiteDatabase;

public abstract class SQLExecuter {

	abstract Object runSQL(SQLiteDatabase db);
	
}