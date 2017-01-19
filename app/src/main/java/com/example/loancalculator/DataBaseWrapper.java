package com.example.loancalculator;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseWrapper extends SQLiteOpenHelper {

	public static final String LOAN = "Loan";
	public static final String USER_ID = "_id";
	public static final String USER_NAME = "_name";
	public static final String AMOUNT = "_amt";
	public static final String RATE = "_rate";
	public static final String TIME = "_time";
	public static final String AGENT = "_agent";
	public static final String DATE = "_date";
	public static final String LOCATION = "location";
	public static final String EMI = "emi";
	public static final String REMBAL = "rembal";
	public static final String NOFEMI = "nofemi";
	private static final String DATABASE_NAME = "Loan6.db";
	private static final int DATABASE_VERSION = 1;

	// creation SQLite statement
	private static final String DATABASE_CREATE = "create table " + LOAN + "("
			+ USER_ID + " integer primary key autoincrement, " + USER_NAME
			+ " text not null, " + AMOUNT + " text not null, " + RATE
			+ " integer not null, " + TIME + " integer not null, " + AGENT
			+ " text not null, " + DATE + " text not null, " + LOCATION
			+ " text not null, " + EMI + " integer not null, " + NOFEMI
			+ " integer not null, " + REMBAL + " integer not null "

			+ ");";

	private static final String DATABASE_CREATE2 = "CREATE TABLE  members(_id integer primary key autoincrement,username text not null,password text not null);";

	private static final String LOGCAT = null;

	public DataBaseWrapper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
		Log.d(LOGCAT, "Created");
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(DATABASE_CREATE);
		Log.d(LOGCAT, "table1 Created");

		db.execSQL(DATABASE_CREATE2);
		Log.d(LOGCAT, "table2 Created");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + LOAN);
		db.execSQL("DROP TABLE IF EXISTS members");
		// if (oldVersion==1 && newVersion==2) {
		// db.execSQL("ALTER TABLE " + LOAN + " ADD COLUMN " + REMBAL);
		// db.execSQL("ALTER TABLE " + LOAN + " ADD COLUMN " + NOFEMI);
		// }
		onCreate(db);

	}

}
