package com.db;

import android.database.sqlite.SQLiteDatabase;

public abstract class SQLInsertExecuter extends SQLExecuter {

	@Override
	Object runSQL(SQLiteDatabase db) {
		return new Long(runSQLInsert(db));
	}

	abstract long runSQLInsert(SQLiteDatabase db);
}