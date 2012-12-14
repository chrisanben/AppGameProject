package com.example.gameapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteAdapter {
	

	//create table MY_DATABASE (ID integer primary key, Content text not null);
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
	
	public SQLiteAdapter openToRead() throws android.database.SQLException {
		sqLiteHelper = new SQLiteHelper(context, MyConstants.MYDATABASE_NAME, null, MyConstants.MYDATABASE_VERSION);
		sqLiteDatabase = sqLiteHelper.getReadableDatabase();
		return this;	
	}
	
	public SQLiteAdapter openToWrite() throws android.database.SQLException {
		sqLiteHelper = new SQLiteHelper(context, MyConstants.MYDATABASE_NAME, null, MyConstants.MYDATABASE_VERSION);
		sqLiteDatabase = sqLiteHelper.getWritableDatabase();
		return this;	
	}
	
	public void close(){
		sqLiteHelper.close();
	}	
	
	
	public long scoreInsert(String initials, int score){
		ContentValues contentValues = new ContentValues();
		contentValues.put(MyConstants.COLUMN_INITIALS, initials);
		contentValues.put(MyConstants.COLUMN_SCORE, score);
		return sqLiteDatabase.insert(MyConstants.MYDATABASE_SCORES_TABLE, null, contentValues);
	}
	
	public int deleteAll(){
		return sqLiteDatabase.delete(MyConstants.MYDATABASE_SCORES_TABLE, null, null);
	}
	
	public Cursor queueFiveScores(){
		String[] columns = new String[]{MyConstants.COLUMN_KEY_ID, MyConstants.COLUMN_INITIALS, MyConstants.COLUMN_SCORE};
		Cursor cursor = sqLiteDatabase.query(MyConstants.MYDATABASE_SCORES_TABLE, columns, 
				null, null, null, null, MyConstants.COLUMN_SCORE + MyConstants.DESC_ORDER, MyConstants.LIMIT_FIVE);
		
		return cursor;
	}
	
	 /*// get a Cursor containing all information about the list specified
	   // by the given id
	   public Cursor getOneSList(long id) 
	   {
		   String[] columns = new String[]{MyConstants.COLUMN_NAME_LIST};
		   String[] columns2 = new String[]{MyConstants.COLUMN_KEY_ID, MyConstants.COLUMN_NAME_ITEM_NAME, MyConstants.COLUMN_NAME_QUANTITY};
		   String tableName;
	     tableName = (sqLiteDatabase.query(MyConstants.MYDATABASE_SLISTS_TABLE, columns, 
	    		  "_id=" + id, null, null, null, null)).toString();
	     return sqLiteDatabase.query(
	             tableName, columns2, null, null, null, null, null);
	   } // end method getOnContact*/
	
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
