package com.example.loancalculator;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ListDisplay extends ListActivity {
	ListView listView;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ArrayList<Map<String, String>> list = buildData();
		setContentView(R.layout.historylist);
		listView = (ListView) findViewById(android.R.id.list);
		final LoanOperation db = new LoanOperation(this);
		db.open();

		// ArrayList<LoanObj> list = (ArrayList<LoanObj>) db.Get_Contacts();
		Cursor values = (Cursor) db.getAllComments();
		// final String itemId = values.getString(values.getColumnIndex("id"));
		// use the SimpleCursorAdapter to show the
		// elements in a ListView
		String[] from = new String[] { DataBaseWrapper.USER_NAME,
				DataBaseWrapper.AMOUNT, DataBaseWrapper.REMBAL };
		int[] to = new int[] { R.id.textView1, R.id.textView2, R.id.textView3 };

		final SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				R.layout.listcontent, values, from, to);

		setListAdapter(adapter);

		db.close();

		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					final long itemId) {
				// TODO Auto-generated method stub
				AlertDialog.Builder ad = new AlertDialog.Builder(
						ListDisplay.this);
				ad.setTitle("Warning");
				ad.setMessage("Sure you want to delete item ?");
				ad.setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// Delete of record from Database and List view.
								final LoanOperation db = new LoanOperation(
										getApplicationContext());
								db.open();
								db.remove(itemId);
								Cursor values = (Cursor) db.getAllComments();
								adapter.changeCursor(values);
								// values.requery();
								adapter.notifyDataSetChanged();
								listView.setAdapter(adapter);

							}
						});
				ad.setNegativeButton(android.R.string.cancel,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								dialog.dismiss();
							}
						});
				ad.show();
				return;
			}
		});

	}

}
