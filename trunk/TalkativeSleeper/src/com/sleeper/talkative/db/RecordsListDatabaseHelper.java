package com.sleeper.talkative.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sleeper.talkative.engine.Engine;

public class RecordsListDatabaseHelper {
	public static final int VERSION = 3;
	public static final String DATABASE_NAME = Engine.DBNAME;
	public static final String TABLE_NAME = Engine.TABLE_NAME;

	public static final String SLEEPER_COLUMN_ID = "id";
	public static final String SLEEPER_COLUMN_DATETIME = "datetime";
	public static final String SLEEPER_COLUMN_AUDIONAME = "audioname";
	public static final String SLEEPER_COLUMN_PATH = "path";
	public static final String SLEEPER_COLUMN_LENGTH = "length";

	private SleeperOpenHelper openHelper;
	private SQLiteDatabase db;

	public RecordsListDatabaseHelper(Context context) {
		openHelper = new SleeperOpenHelper(context);
		db = openHelper.getWritableDatabase();
	}
	
	public void saveRecords(String audioname, String path, String length) {
		ContentValues contentValues = new ContentValues();

		contentValues.put(SLEEPER_COLUMN_AUDIONAME, audioname);
		contentValues.put(SLEEPER_COLUMN_PATH, path);
		contentValues.put(SLEEPER_COLUMN_LENGTH, length);

		db.insert(TABLE_NAME, null, contentValues);
	}
	

	private class SleeperOpenHelper extends SQLiteOpenHelper {
		public SleeperOpenHelper(Context context) {
			super(context, DATABASE_NAME, null, VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + SLEEPER_COLUMN_ID
					+ " INTEGER PRIMARY KEY, "
					+ SLEEPER_COLUMN_DATETIME
					+ " DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP, "
					+ SLEEPER_COLUMN_AUDIONAME + " TEXT, " + SLEEPER_COLUMN_PATH
					+ " TEXT, " + SLEEPER_COLUMN_LENGTH + " INTEGER DEFAULT 0 )");

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
			onCreate(db);
		}
	}
}
