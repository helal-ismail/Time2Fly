package com.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public abstract class TableHelper {
	DbHelper DBHelper;
	SQLiteDatabase db;
	
	
	String TABLE;
	String[] COLUMNS;
	String PKEY;
	
	public TableHelper(Context ctx) {
		DBHelper = new DbHelper(ctx);
	}
	
	public TableHelper open() throws SQLException {
		db = DBHelper.getWritableDatabase();
		db.rawQuery("PRAGMA synchronous = OFF", null);
		db.rawQuery("PRAGMA journal_mode = MEMORY", null);
		return this;
	}
	
	public void close() {
		DBHelper.close();
	}
	public void insertRecord(Object[] args)	{
		open();
		insert(args);
		close();
	}

	
	
	Cursor queryDB() {
		return db.query(TABLE, COLUMNS, null, null, null, null, null);
	}
	
	public void fillCache(){
		open();
		updateCash();
		close();
	}
	

	protected long insert(final ContentValues initialValues) {
		return executeInsertSQL(new SQLInsertExecuter() {
			
			@Override
			long runSQLInsert(SQLiteDatabase db) {
			return db.insert(getTableName(), null, initialValues);
			}
		});
	}
	

	
	public void deleteAll() {
		executeSQL(new SQLExecuter() {
			
			@Override
			public Object runSQL(SQLiteDatabase db) {
				db.delete(getTableName(), "", null);
				return null;
			}
		});
	}
	
	private long executeInsertSQL(SQLInsertExecuter executor) {
		Long longValue = (Long)executeSQL(executor);
		return longValue.longValue();
	}

	public Cursor executeCursorSQL(final SQLCursorExecuter executor) {
		SQLiteDatabase db = DBHelper.getReadableDatabase();		
		 return (Cursor)executor.runSQL(db);
	}
	
	private Object executeSQL(SQLExecuter executor) {		
		SQLiteDatabase db = DBHelper.getWritableDatabase();		
		Object returnVal = executor.runSQL(db);
		db.close();
		return returnVal;
	}
	/** Returns a list of model objects */
	public List selectAll() {
		List values = new ArrayList();
		open();
		Cursor c = queryDB();

		if (c.moveToFirst()) {
			do {
			//	values.add(hydrateNewObject(c));
			} while (c.moveToNext());
		}
		
		c.close();
		close();
		
		return values;
	}
	
	
	
	
	
	public void deleteWhere(final String whereClause) {
		executeSQL(new SQLExecuter() {			
			@Override
			public Object runSQL(SQLiteDatabase db) {
				db.delete(TABLE, whereClause, null);
				return null;
			}
		});
	}

	public void beginTransaction() {
		db.rawQuery("BEGIN TRANSACTION", null);
	}
	
	public void endTransaction() {
		db.rawQuery("END TRANSACTION", null);
	}
	
	public long getLastRowID(String table) {
		String query = "SELECT ROWID FROM "+table+" ORDER BY ROWID DESC LIMIT 1";
		Cursor c = db.rawQuery(query, null);
		long lastId = 0;
		if (c != null && c.moveToFirst()) {
		    lastId = c.getLong(0);
		}
		return lastId;
	}
	
	
	public abstract String getTableName();
	protected abstract void insert(Object[] args);
	abstract void updateCash();
	
	public String[] getColumns() {
		return COLUMNS;
	}
}
