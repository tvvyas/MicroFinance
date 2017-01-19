package com.example.loancalculator;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class Update extends Activity implements OnItemSelectedListener {

	EditText rname, ramount;
	TextView resultText1, resultText2, resultText3;

	// Spinner element
	Spinner spinner;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update);
		Button update = (Button) findViewById(R.id.update);
		ramount = (EditText) findViewById(R.id.ramount);
		// final String edit1=rname.getText().toString();

		// Spinner element
		spinner = (Spinner) findViewById(R.id.spinner2);

		// Loading spinner data from database
		loadSpinnerData();
		// Spinner click listener
		spinner.setOnItemSelectedListener(this);

		final LoanOperation db = new LoanOperation(this);
		db.open();

		update.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				final String edit2 = ramount.getText().toString();
				if (edit2.length() > 0) {

					AlertDialog.Builder alertDialog = new AlertDialog.Builder(
							Update.this);
					// Setting Dialog Title
					alertDialog.setTitle("Confirm Update");
					// Setting Dialog Message
					alertDialog
							.setMessage("Are you sure you want update this?");
					// Setting Icon to Dialog
					alertDialog.setIcon(R.drawable.ic_launcher);
					// Setting Positive "Yes" Button
					alertDialog.setPositiveButton("YES",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {

									String label = spinner.getSelectedItem()
											.toString();

									int loanobj = db.UpdateUser(label, edit2);

									// Write your code here to invoke YES event
									Toast.makeText(getApplicationContext(),
											"Amount Updated Successfully",
											Toast.LENGTH_SHORT).show();
									// Write your code here to invoke YES event

								}
							});
					// Setting Negative "NO" Button
					alertDialog.setNegativeButton("NO",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									// Write your code here to invoke NO event
									Toast.makeText(getApplicationContext(),
											"You clicked on NO",
											Toast.LENGTH_SHORT).show();
									dialog.cancel();
								}
							});
					// Showing Alert Message
					alertDialog.show();

				}

				else {
					Toast.makeText(getApplicationContext(),
							"Please Enter Amount to Update", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});

	}

	private void loadSpinnerData() {
		// database handler
		LoanOperation db = new LoanOperation(getApplicationContext());
		db.open();
		// Spinner Drop down elements
		List<String> test = db.Get_Contacts();
		db.close();
		// Creating adapter for spinner
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, test);

		try {

			// Drop down layout style - list view with radio button
			dataAdapter
					.setDropDownViewResource(android.R.layout.simple_spinner_item);

			// attaching data adapter to spinner
			spinner.setAdapter(dataAdapter);
			db.close();
		} catch (Exception e) {

			Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}

	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// On selecting a spinner item
		// String label = parent.getItemAtPosition(position).toString();
		String label = parent.getSelectedItem().toString();

	}

	// @Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}
}
