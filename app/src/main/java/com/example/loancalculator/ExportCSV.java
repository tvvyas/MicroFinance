package com.example.loancalculator;

import java.io.File;
import java.io.FileWriter;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import au.com.bytecode.opencsv.CSVWriter;

public class ExportCSV extends Activity {

	private SQLiteDatabase database;
	private DataBaseWrapper dbHelper;
	Button exportbtn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		final LoanOperation db = new LoanOperation(this);
		db.open();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.export_csv);
		final Button expbutton = (Button) findViewById(R.id.exportbtn);

		expbutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				File dbFile = getDatabasePath("loan6.sqlite");
				File exportDir = new File(Environment
						.getExternalStorageDirectory(), "");
				if (!exportDir.exists()) {
					exportDir.mkdirs();
				}

				File file = new File(exportDir, "Loan_Data.csv");
				try {

					file.createNewFile();
					CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
					Cursor curCSV = db.getAllComments();

					csvWrite.writeNext(curCSV.getColumnNames());
					if (curCSV.moveToFirst()) {
						do {
							// Which column you want to
							// String arrStr[]
							// ={itemId,name,amount,rate,time,agent,location,date,emi,nofemi,rembal};

							String arrStr[] = {
									String.valueOf(curCSV.getInt(0)),
									curCSV.getString(1),
									String.valueOf(curCSV.getFloat(2)),
									String.valueOf(curCSV.getFloat(3)),
									String.valueOf(curCSV.getInt(4)),
									String.valueOf(curCSV.getString(5)),
									curCSV.getString(6), curCSV.getString(7),
									String.valueOf(curCSV.getFloat(8)),
									String.valueOf(curCSV.getInt(9)),
									String.valueOf(curCSV.getFloat(10)) };
							csvWrite.writeNext(arrStr);

						} while (curCSV.moveToNext());
					}
					csvWrite.close();
					curCSV.close();
					Toast.makeText(ExportCSV.this,
							"Data Exported to file Loan_Data.csv",
							Toast.LENGTH_LONG).show();
				} catch (Exception sqlEx) {
					Log.e("ExportCSV", sqlEx.getMessage(), sqlEx);
					Toast.makeText(ExportCSV.this, "Error Exporting Data",
							Toast.LENGTH_LONG).show();
				}

			}
		});
	}
}
