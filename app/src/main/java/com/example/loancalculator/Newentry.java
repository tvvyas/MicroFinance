package com.example.loancalculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Newentry extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newentry);
		Button newentry = (Button) findViewById(R.id.newentry);
		Button showold = (Button) findViewById(R.id.showold);
		Button update = (Button) findViewById(R.id.update);
		Button history = (Button) findViewById(R.id.history);
		Button image = (Button) findViewById(R.id.saveImage);
		Button about = (Button) findViewById(R.id.about);

		newentry.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// setContentView(R.layout.newentry);
				startActivity(new Intent(getApplicationContext(),
						MainActivity.class));

			}
		});

		showold.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(),
						FetchData.class));

			}
		});

		update.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), Update.class));

			}
		});

		history.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(),
						ListDisplay.class));

			}
		});

		image.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(),
						CameraPhotoCapture.class));

			}
		});

		about.setOnClickListener(new View.OnClickListener() {

			// startActivity(new Intent(getApplicationContext(),
			// ExportOnDevice.class));
			// setContentView(R.layout.about_detail);
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(),
						ExportCSV.class));
				// setContentView(R.layout.about_detail);
			}

		});

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);// Menu Resource, Menu
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.item1:
			startActivity(new Intent(getApplicationContext(), AboutDetail.class));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

}
