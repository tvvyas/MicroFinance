package com.example.loancalculator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MembersActivity extends Activity {

	EditText txtUserName;
	EditText txtPassword;
	Button btnLogin;
	Button btnRegister;
	TextView btnForgot;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		txtUserName = (EditText) findViewById(R.id.et_user);
		txtPassword = (EditText) findViewById(R.id.et_pw);
		btnLogin = (Button) findViewById(R.id.btn_login);
		btnRegister = (Button) findViewById(R.id.btn_reg);
		btnForgot = (TextView) findViewById(R.id.btnforgot);
		btnForgot.isClickable();
		btnForgot.setTextColor(Color.BLUE);

		final LoanOperation dbAdapter = new LoanOperation(this);
		dbAdapter.open();

		btnLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(txtUserName.getWindowToken(), 0);
				imm.hideSoftInputFromWindow(txtPassword.getWindowToken(), 0);
				String username = txtUserName.getText().toString();
				String password = txtPassword.getText().toString();
				if (username.length() > 0 && password.length() > 0) {
					try {

						if (dbAdapter.Login(username, password)) {
							Toast.makeText(MembersActivity.this,
									"Successfully Logged In", Toast.LENGTH_LONG)
									.show();
							txtUserName.setText("");
							txtPassword.setText("");
							startActivity(new Intent(getApplicationContext(),
									Newentry.class));
						} else {
							Toast.makeText(
									MembersActivity.this,
									"Either Invalid username or password or not registered",
									Toast.LENGTH_LONG).show();
						}

					} catch (Exception e) {
						Toast.makeText(MembersActivity.this,
								"Some problem occurred", Toast.LENGTH_LONG)
								.show();

					}
				} else {
					Toast.makeText(MembersActivity.this,
							"Username or Password is empty", Toast.LENGTH_LONG)
							.show();
				}
			}
		});

		btnRegister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(txtUserName.getWindowToken(), 0);
				imm.hideSoftInputFromWindow(txtPassword.getWindowToken(), 0);
				try {

					String username = txtUserName.getText().toString();
					String password = txtPassword.getText().toString();
					long i = dbAdapter.register(username, password);
					if (i != -1) {
						Toast.makeText(MembersActivity.this,
								"You have successfully registered",
								Toast.LENGTH_LONG).show();
					} else {
						Toast.makeText(MembersActivity.this,
								"You have already registered once",
								Toast.LENGTH_LONG).show();
					}
				} catch (SQLException e) {
					Toast.makeText(MembersActivity.this,
							"Some problem occurred", Toast.LENGTH_LONG).show();

				}

			}
		});

		}
}
