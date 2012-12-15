package com.example.gameapp;

//Names: Joel Murphy & Chris Bentley
//Purpose: Accessing the database and a scores table for the game.

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteAdapter {
	
	//string to create the scores table
	private static final String SCRIPT_CREATE_SCORES_DATABASE =
		"CREATE TABLE IF NOT EXISTS " + MyConstants.MYDATABASE_SCORES_TABLE + " ("
		+ MyConstants.COLUMN_KEY_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
		+ MyConstants.COLUMN_INITIALS + " CHAR(3) NOT NULL, " + MyConstants.COLUMN_SCORE
		+ " INTEGER NOT NULL);";	

	
	private SQLiteHelper sqLiteHelper;
	private SQLiteDatabase sqLiteDatabase;

	private Context context;
	
	public SQLiteAdapter(Context c){
		context = c;
	}
	
	//open the database to read
	public SQLiteAdapter openToRead() throws android.database.SQLException {
		sqLiteHelper = new SQLiteHelper(context, MyConstants.MYDATABASE_NAME, null, MyConstants.MYDATABASE_VERSION);
		sqLiteDatabase = sqLiteHelper.getReadableDatabase();
		return this;	
	}
	
	//open the database to write
	public SQLiteAdapter openToWrite() throws android.database.SQLException {
		sqLiteHelper = new SQLiteHelper(context, MyConstants.MYDATABASE_NAME, null, MyConstants.MYDATABASE_VERSION);
		sqLiteDatabase = sqLiteHelper.getWritableDatabase();
		return this;	
	}
	
	//close the database
	public void close(){
		sqLiteHelper.close();
	}	
	
	//insert score into table
	public long scoreInsert(String initials, int score){
		ContentValues contentValues = new ContentValues();
		contentValues.put(MyConstants.COLUMN_INITIALS, initials);
		contentValues.put(MyConstants.COLUMN_SCORE, score);
		return sqLiteDatabase.insert(MyConstants.MYDATABASE_SCORES_TABLE, null, contentValues);
	}
	
	//delete all records for future reset scores button
	public int deleteAll(){
		return sqLiteDatabase.delete(MyConstants.MYDATABASE_SCORES_TABLE, null, null);
	}
	
	//select the top five scores for display
	public Cursor queueFiveScores(){
		String[] columns = new String[]{MyConstants.COLUMN_KEY_ID, MyConstants.COLUMN_INITIALS, MyConstants.COLUMN_SCORE};
		Cursor cursor = sqLiteDatabase.query(MyConstants.MYDATABASE_SCORES_TABLE, columns, 
				null, null, null, null, MyConstants.COLUMN_SCORE + MyConstants.DESC_ORDER, MyConstants.LIMIT_FIVE);
		
		return cursor;
	}
	
	public class SQLiteHelper extends SQLiteOpenHelper {

		public SQLiteHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL(SCRIPT_CREATE_SCORES_DATABASE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub

		}

	}

}
