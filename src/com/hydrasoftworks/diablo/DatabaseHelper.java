package com.hydrasoftworks.diablo;

import com.hydrasoftworks.diablo.model.BattleTag;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "diablo.db";
	
    
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);

	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + BattleTag.TABLE_NAME + " (" +
				BattleTag.BATTLETAG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "	+
				BattleTag.BATTLETAG  + " TEXT NOT NULL, " +
				BattleTag.VALUE + " INT, " +
				BattleTag.SERVER + " TEXT NOT NULL);");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + BattleTag.TABLE_NAME);
        onCreate(db);
	}

}
