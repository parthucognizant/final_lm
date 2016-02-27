package com.example.librarymanager;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.librarymanager.util.constants;

public class LoginActivity extends ActionBarActivity {

	private static final String TAG = "LoginActivity";
	EditText username,password;
	private ProgressDialog pDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(TAG,constants.logs.loginonCreate);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		username = (EditText)findViewById(R.id.username_edit);
		password = (EditText)findViewById(R.id.password_edit);
	}

	public void login(View v){
		Log.i(TAG,constants.logs.logingo);
		new AttemptLogin().execute();
	}
	class AttemptLogin extends AsyncTask<String, String, String> {

		boolean failure = false;

		@Override protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(LoginActivity.this);
			pDialog.setMessage("Attempting for login...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}


		@Override
		protected String doInBackground(String... args) {
			Log.i(TAG,constants.logs.doInBackground);
			// TODO Auto-generated method stub
			try{
				String uname = username.getText().toString();
				String pass = password.getText().toString();

				InputStream is = null;
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("username", uname));
				nameValuePairs.add(new BasicNameValuePair("password", pass));
				String result = null;

				HttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost("http://10.0.2.2:800/lm/login.php");
				httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				HttpResponse response = httpClient.execute(httpPost);
				HttpEntity entity = response.getEntity();
				is = entity.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null)
				{
					sb.append(line + "\n");
				}
				result = sb.toString();
				return result;
			}
			catch(Exception e){
				return new String("Exception: " + e.getMessage());
			}
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			//super.onPostExecute(result);
			pDialog.hide();
			if((result.trim()).equalsIgnoreCase("student")){
				Intent bookintent = new Intent(LoginActivity.this, BooksActivity.class);
				bookintent.putExtra("username", username.getText().toString());
				startActivity(bookintent);
			}
			else if((result.trim()).equalsIgnoreCase("admin")){
				Intent adminintent = new Intent(LoginActivity.this, AdminActivity.class);
				startActivity(adminintent);

			}
			else
			{
				Toast.makeText(LoginActivity.this, result, Toast.LENGTH_SHORT).show();
			}
		}
	}
}
