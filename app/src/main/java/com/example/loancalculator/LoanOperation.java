package com.example.loancalculator;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class LoanOperation {

	// Database fields
	private DataBaseWrapper dbHelper;
	private String[] LOAN_TABLE_COLUMNS = { DataBaseWrapper.USER_ID,
			DataBaseWrapper.USER_NAME, DataBaseWrapper.AMOUNT,
			DataBaseWrapper.RATE, DataBaseWrapper.TIME, DataBaseWrapper.AGENT,
			DataBaseWrapper.LOCATION, DataBaseWrapper.DATE,
			DataBaseWrapper.EMI, DataBaseWrapper.NOFEMI, DataBaseWrapper.REMBAL };
	private SQLiteDatabase database;
	/******** if debug is set true then it will show all Logcat message *******/
	public static final boolean DEBUG = true;

	/******************** Logcat TAG ************/
	public static final String LOG_TAG = "DBAdapter";

	private static final String DATABASE_TABLE = "members";
	public static final String KEY_ROW_ID = "_id";
	public static final String KEY_USERNAME = "username";
	public static final String KEY_PASSWORD = "password";

	public LoanOperation(Context context) {
		if (DEBUG)
			Log.i("DBAdapter", context.toString());
		dbHelper = new DataBaseWrapper(context);

	}

	public void open() throws SQLException {

		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public long addUser(String name, String amt, String rate, String time,
			String agent, String date, String location, String emi) {

		ContentValues values = new ContentValues();

		values.put(DataBaseWrapper.USER_NAME, name);
		values.put(DataBaseWrapper.AMOUNT, amt);
		values.put(DataBaseWrapper.RATE, rate);
		values.put(DataBaseWrapper.TIME, time);
		values.put(DataBaseWrapper.AGENT, agent);
		values.put(DataBaseWrapper.DATE, date);
		values.put(DataBaseWrapper.LOCATION, location);
		values.put(DataBaseWrapper.EMI, emi);
		values.put(DataBaseWrapper.NOFEMI, 0);
		values.put(DataBaseWrapper.REMBAL, 0);

		long loanId = database.insert(DataBaseWrapper.LOAN, null, values);

		database.close();

		return loanId;

	}

	// Fetch the records to display
	public LoanObj findUser(String name) {
		Log.v("Field Val", name);
		String query = "Select * FROM " + DataBaseWrapper.LOAN + " WHERE "
				+ DataBaseWrapper.USER_NAME + " =  \"" + name + "\"";

		Log.v("LOG", query);
		Cursor cursor = database.rawQuery(query, null);
		LoanObj loanobj = new LoanObj();
		Log.v("Cursor Object", DatabaseUtils.dumpCursorToString(cursor));
		// int count = 0;
		try {
			if (cursor.moveToFirst()) {
				// cursor.moveToFirst();

				loanobj.setId((cursor.getInt(0)));
				loanobj.setName(cursor.getString(1));
				loanobj.setAmt(cursor.getFloat(2));
				loanobj.setRate(cursor.getFloat(3));
				loanobj.setTime(cursor.getInt(4));
				loanobj.setAgent(cursor.getString(5));
				loanobj.setDate(cursor.getString(6));
				loanobj.setLocation(cursor.getString(7));
				loanobj.setEmi(cursor.getFloat(8));
				loanobj.setNofEmi(cursor.getInt(9));
				loanobj.setRemBal(cursor.getFloat(10));

			}
			return loanobj;
		} finally {
			if (cursor != null) {
				cursor.close();
				// database.close();
			}
		}
	}

	// Update the records to display
	public int UpdateUser(String name, String amt) {

		// 1. get reference to writable DB
		// SQLiteDatabase db = this.database;
		// LoanObj loanobj = new LoanObj();
		ContentValues values = new ContentValues();
		LoanObj loanobj1 = findUser(name);
		// float amt1= (float) loanobj1.getRemBal();
		float amt2;

		int i = (int) loanobj1.getNofEmi();

		float Balance = (float) loanobj1.getRemBal();

		i = i + 1;

		if (Balance == 0) {
			amt2 = Float.parseFloat(amt);
			Log.v("Field Val", "when bal is 0");
		} else {

			amt2 = Balance + Float.parseFloat(amt);
			Log.v("Field Val", "when bal is from amt");
		}

		values.put(DataBaseWrapper.REMBAL, amt2);
		values.put(DataBaseWrapper.NOFEMI, i);

		String whereClause = DataBaseWrapper.USER_NAME + " = ? ";// and
																	// " + DATE_COLUMN + "
																	// = ?";
		String[] whereArgs = new String[] { name };

		return database.update(DataBaseWrapper.LOAN, values, whereClause,
				whereArgs);

	}

	// Getting the total row Count
	public int getContactsCount() {
		String countQuery = "SELECT  * FROM " + DataBaseWrapper.LOAN;

		Cursor cursor = database.rawQuery(countQuery, null);
		int count = 0;
		try {
			if (cursor.moveToFirst()) {
				count = cursor.getCount();

			}
			return count;
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}

	}

	public Cursor getAllComments() {
		ArrayList<LoanObj> comments = new ArrayList<LoanObj>();

		Cursor cursor = database.query(DataBaseWrapper.LOAN,
				LOAN_TABLE_COLUMNS, null, null, null, null, null);
		Log.v("Cursor Details", DatabaseUtils.dumpCursorToString(cursor));
		cursor.moveToFirst();
		// LoanObj loan = new LoanObj();
		if (cursor.moveToFirst()) {
			do {
				LoanObj comment = parseLoanObj(cursor);

				comments.add(comment);
			} while (cursor.moveToNext());
		}
		// make sure to close the cursor
		// cursor.close();
		return cursor;
	}

	public List<String> Get_Contacts() {

		List<String> test = new ArrayList<String>();
		// Select All Query
		// String selectQuery = "SELECT * FROM " + DataBaseWrapper.LOAN;
		Cursor cursor = database.query(DataBaseWrapper.LOAN,
				LOAN_TABLE_COLUMNS, null, null, null, null, null);
		// Log.v("LOG", selectQuery);
		// Cursor cursor = database.rawQuery(selectQuery, null);
		try {
			// looping through all rows and adding to list
			Log.v("Cursor Object", DatabaseUtils.dumpCursorToString(cursor));
			// try{
			if (cursor.moveToFirst()) {
				do {
					test.add(cursor.getString(1));
				} while (cursor.moveToNext());
			}
			cursor.close();
			database.close();
			return test;
		}

		finally {
			if (cursor != null) {
				cursor.close();
			}
			// }

		}

	}

	private LoanObj parseLoanObj(Cursor cursor) {
		LoanObj loan = new LoanObj();

		loan.setId((cursor.getInt(0)));
		loan.setName(cursor.getString(1));
		loan.setAmt(cursor.getFloat(2));
		loan.setRate(cursor.getFloat(3));
		loan.setTime(cursor.getInt(4));
		loan.setAgent(cursor.getString(5));
		loan.setDate(cursor.getString(6));
		loan.setLocation(cursor.getString(7));
		loan.setEmi(cursor.getFloat(8));
		loan.setNofEmi(cursor.getInt(9));
		loan.setRemBal(cursor.getFloat(10));

		return loan;
	}

	public void remove(long arg3) {
		String string = String.valueOf(arg3);
		database.execSQL("DELETE FROM " + DataBaseWrapper.LOAN
				+ " WHERE _id = '" + string + "'");// generated method stub

	}

	public long register(String user, String pw) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_USERNAME, user);
		initialValues.put(KEY_PASSWORD, pw);

		String countQuery = "SELECT  * FROM " + DATABASE_TABLE;
		Cursor mCursor = database.rawQuery(countQuery, null);
		if (mCursor != null) {
			if (mCursor.getCount() < 1) {
				return database.insert(DATABASE_TABLE, null, initialValues);

			}

			else {

				return -1;
			}

		}
		return -1;

	}

	public boolean Login(String username, String password) throws SQLException {
		Cursor mCursor = database.rawQuery("SELECT * FROM " + DATABASE_TABLE
				+ " WHERE username=? AND password=?", new String[] { username,
				password });
		if (mCursor != null) {
			if (mCursor.getCount() > 0) {
				return true;
			}
		}
		return false;
	}

	public String GetPassword(String uname) throws SQLException {
		String query = "SELECT * FROM " + DATABASE_TABLE + " WHERE username= '"
				+ uname + "'";

		Cursor mCursor = database.rawQuery(query, null);

		if (mCursor.moveToFirst()) {

			return mCursor.getString(mCursor.getColumnIndex("password"));

		}

		return null;

	}
}
