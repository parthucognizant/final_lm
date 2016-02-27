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

public class SignupActivity extends ActionBarActivity {

	private static final String TAG = "SignupActivity";
	EditText name,username,password,confirm_password,phone_number;
	private ProgressDialog pDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(TAG,constants.logs.signuponCreate);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		name = (EditText)findViewById(R.id.name_edit);
		username = (EditText)findViewById(R.id.username_edit);
		password = (EditText)findViewById(R.id.password_edit);
		confirm_password = (EditText)findViewById(R.id.confirm_password_edit);
		phone_number = (EditText)findViewById(R.id.phone_number_edit);
	}

	public void signUp(View v) {
		//Log.i(TAG,constants.logs.SignUpgo);
		new AttemptSignUp().execute();
	}
	class AttemptSignUp extends AsyncTask<String, String, String> {
		boolean failure = false;
		@Override protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(SignupActivity.this);
			pDialog.setMessage("Hold on Registering...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		@Override
		protected String doInBackground(String... arg0) {
			//Log.i(TAG,constants.logs.SignUpDoInBackground);
			try{

				String pname = name.getText().toString();
				String uname = username.getText().toString();
				String pass = password.getText().toString();
				String cpass = confirm_password.getText().toString();
				String phone = phone_number.getText().toString();


				InputStream is = null;
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("name", pname));
				nameValuePairs.add(new BasicNameValuePair("username", uname));
				nameValuePairs.add(new BasicNameValuePair("password", pass));
				nameValuePairs.add(new BasicNameValuePair("confirmpassword", cpass));
				nameValuePairs.add(new BasicNameValuePair("phonenumber", phone));
				String result = null;

				HttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost("http://10.0.2.2:800/lm/Signupconnect.php");
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
			if((result.trim()).equalsIgnoreCase("success")){
				Intent bookintent = new Intent(SignupActivity.this, LoginActivity.class);
				startActivity(bookintent);
			}
			else
			{
				Toast.makeText(SignupActivity.this, result, Toast.LENGTH_SHORT).show();
			}

		}
	}
}
