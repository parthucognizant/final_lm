package com.example.librarymanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;

import com.example.librarymanager.util.constants;

public class MainActivity extends ActionBarActivity {

	private static final String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(TAG,constants.logs.mainOncreate);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void signupClick(View v){
		Log.i(TAG,constants.logs.signupClick);
		Intent signupintent = new Intent(this, SignupActivity.class);
		startActivity(signupintent);
		//Toast.makeText(this, "Signup clicked", Toast.LENGTH_SHORT).show();
	}

	public void loginClick(View v){
		Log.i(TAG,constants.logs.loginClick);
		Intent loginintent = new Intent(this, LoginActivity.class);
		startActivity(loginintent);
		//Toast.makeText(this, "Login clicked", Toast.LENGTH_SHORT).show();
	}
}
