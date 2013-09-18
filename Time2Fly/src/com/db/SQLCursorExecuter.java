package com.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public abstract class SQLCursorExecuter extends SQLExecuter {

	public Object runSQL(SQLiteDatabase db) {
		return runSQLCursor(db);
	}
	public abstract Cursor runSQLCursor(SQLiteDatabase db);


}