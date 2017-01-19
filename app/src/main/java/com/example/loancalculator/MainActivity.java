package com.example.loancalculator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.DatePicker;

public class MainActivity extends Activity {

	EditText editText1, editText2, editText3, editText4, editText5, editText6,
			editText7;
	TextView resultText;
	Button button1, pickDate1;
	DatePicker picker;

	private String[] intrest;
	private Calendar cal;
	private int mDay;
	private int mMonth;
	private int mYear;
	public String t;
	private float result;
	static final int DATE_DIALOG_ID = 1111;
	private final static int ICE_CREAM_BUILD_ID = 14;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		editText6 = (EditText) findViewById(R.id.inputdate);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		// String dateFormat = "dd/MM/yyyy";
		editText6 = (EditText) findViewById(R.id.inputdate);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		editText6.setText(sdf.format(c.getTime()));

		Button pickDate = (Button) findViewById(R.id.btndate);
		pickDate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
			}
		});
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		updateDisplay();

		// Spinner element
		intrest = getResources().getStringArray(R.array.intrest_list);
		Spinner spinner = (Spinner) findViewById(R.id.spinner1);

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, intrest);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(dataAdapter);
		// Spinner click listener

		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				String label = parent.getItemAtPosition(position).toString();

				if (label.equals("Year")) {
					t = "Year";
				} else if (label.equals("Month")) {
					t = "Month";
				} else {
					t = "Quarter";
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

		// Spinner Drop down elements

	}

	public void calculateClickHandler(View view) {
		if (view.getId() == R.id.newentry) {
			LoanOperation db = new LoanOperation(this);
			db.open();
			editText1 = (EditText) findViewById(R.id.usname);
			editText2 = (EditText) findViewById(R.id.ramount);
			editText3 = (EditText) findViewById(R.id.editText3);
			editText4 = (EditText) findViewById(R.id.editText4);
			editText5 = (EditText) findViewById(R.id.agent);

			// picker=(DatePicker)findViewById(R.id.datePicker1);

			editText7 = (EditText) findViewById(R.id.location);
			button1 = (Button) findViewById(R.id.newentry);
			// changedate=(Button)findViewById(R.id.btndate);
			TextView resultText = (TextView) findViewById(R.id.textView5);

			String edit1 = editText1.getText().toString();
			String edit2 = editText2.getText().toString();
			String edit3 = editText3.getText().toString();
			String edit4 = editText4.getText().toString();
			String edit5 = editText5.getText().toString();
			String edit6 = editText6.getText().toString();
			String edit7 = editText7.getText().toString();

			// updates the date in the TextView

			float sum = 0, rate = 0, time = 0;
			if ((edit2.length() > 0) && (edit3.length() > 0)
					&& (edit4.length() > 0)) {
				sum = Float.parseFloat(editText2.getText().toString());
				rate = Float.parseFloat(editText3.getText().toString());
				time = Float.parseFloat(editText4.getText().toString());
			}
			Log.v("LOG Val of t:", t);
			float emiValue = calculateEMI(sum, rate, time, t);

			final String edit8 = Float.toString(emiValue);

			if (edit1.length() > 0 && edit2.length() > 0 && edit3.length() > 0
					&& edit4.length() > 0 && edit5.length() > 0
					&& edit6.length() > 0 && edit7.length() > 0
					&& edit8.length() > 0) {
				long result = db.addUser(edit1, edit2, edit3, edit4, edit5,
						edit6, edit7, edit8);

				Toast.makeText(MainActivity.this, "DataSaved",
						Toast.LENGTH_LONG).show();// TODO Auto-generated method
													// stub
				Calendar c1 = Calendar.getInstance(TimeZone
						.getTimeZone("GMT+5:30"));
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				try {
					c1.setTime(sdf.parse(edit6));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				Calendar cal = Calendar.getInstance();
				cal.set(2014, 03 - 1, 13, 7, 30);
				ContentValues l_event = new ContentValues();

				try {
					Intent intent = new Intent(Intent.ACTION_EDIT);
					intent.setType("vnd.android.cursor.item/event");
					intent.putExtra("beginTime", c1.getTimeInMillis());
					intent.putExtra("allDay", false);
					intent.putExtra("rrule", "FREQ=MONTHLY");
					intent.putExtra("endTime",
							c1.getTimeInMillis() + 60 * 60 * 1000);
					intent.putExtra("title", edit1);
					intent.putExtra("description", "Monthly EMI : " + emiValue
							+ " due to paid");
					intent.putExtra("eventLocation", edit7);
					startActivity(intent);
				} catch (Exception e) {
					Toast.makeText(MainActivity.this,
							"Please add/sync the calendar", Toast.LENGTH_LONG)
							.show();
				}

				this.finish();
			} else {
				Toast.makeText(MainActivity.this,
						"Please enter madatory details", Toast.LENGTH_LONG)
						.show();
			}
		}

	}

	protected Dialog onCreateDialog(int id) {
		switch (id) {

		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
					mDay);
		}
		return null;
	}

	protected void onPrepareDialog(int id, Dialog dialog) {
		switch (id) {

		case DATE_DIALOG_ID:
			((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);
			break;
		}
	}

	private void updateDisplay() {
		editText6.setText(new StringBuilder()
				// Month is 0 based so add 1
				.append(mDay).append("/").append(mMonth + 1).append("/")
				.append(mYear).append(""));
	}

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			updateDisplay();
		}
	};

	private float calculateEMI(float sum, float rate, float time, String t) {
		// TODO Auto-generated method stub
		if (t == "Year") {
			Log.v("LOG", "in Year");
			result = (float) (sum + (sum * (rate / 100) * time)) / (time * 12);
		} else if (t == "Month") {
			Log.v("LOG", "in Month");
			result = (float) (sum + (sum * (rate / 100) * (time / 12))) / time;
		}

		else {
			Log.v("LOG", "in Quarter");
			result = (float) (sum + (sum * (rate / 100) * (time / 4))) / time;
		}
		return result;
	}
}
