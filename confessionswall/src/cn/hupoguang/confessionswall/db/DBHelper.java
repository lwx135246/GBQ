/**
 * GATModel.java
 * cn.hupoguang.confessionofwall.model
 * Function： TODO
 *
 * date ：   2013-7-11
 * author：李文响
 * Copyright (c) 2013,hupoguang All Rights Reserved.
*/

package cn.hupoguang.confessionswall.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import cn.hupoguang.confessionswall.util.ConfessionApplication;

public class DBHelper extends SQLiteOpenHelper{
	
	public static final String DB_NAME = "confessionofwall";
	public static final int DB_VERSION = 1;
	
	public DBHelper(Context context){
		super(context, DB_NAME, null, DB_VERSION);
	}
	

	@Override
	public void onCreate(SQLiteDatabase db) {
		/*被赞告白记录表*/
		db.execSQL("CREATE TABLE IF NOT EXISTS  " + ConfessionApplication.PRAISE_DB +
				"(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
				" likeUserName VARCHAR," +
				" confessionId VARCHAR not null unique)");
		
		db.execSQL("CREATE TABLE IF NOT EXISTS  " + ConfessionApplication.THEMES_DB +
				"(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
				" imagePath VARCHAR," +
				" dailyContext VARCHAR,"+
				" dailyEngContext VARCHAR,"+
				" publishDate VARCHAR,"+
				" color VARCHAR)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(" DROP TABLE IF EXISTS " + ConfessionApplication.PRAISE_DB);
		db.execSQL(" DROP TABLE IF EXISTS " + ConfessionApplication.THEMES_DB);
	}
	
}
