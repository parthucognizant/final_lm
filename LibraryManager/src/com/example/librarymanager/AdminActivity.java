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
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AdminActivity extends ActionBarActivity{
	private static final String TAG = "AdminActivity";
	EditText bookid,bookname;
	private ProgressDialog pDialog;
	private Spinner spinner;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin);
		bookid = (EditText)findViewById(R.id.bookid_edit);
		bookname = (EditText)findViewById(R.id.bookname_edit);
		spinner = (Spinner)findViewById(R.id.category_spinner);

	}
	public void save(View v) {
		//Log.i(TAG,constants.logs.BookAdding);
		new AttemptAdmin().execute();
	}
	class AttemptAdmin extends AsyncTask<String, String, String> {
		boolean failure = false;
		@Override protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(AdminActivity.this);
			pDialog.setMessage("Saving.....");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub

			//Log.i(TAG,constants.logs.SignUpDoInBackground);
			try{

				String bid = bookid.getText().toString();
				String bnamee = bookname.getText().toString();
				String bcategory = spinner.getSelectedItem().toString();


				InputStream is = null;
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("bid", bid));
				nameValuePairs.add(new BasicNameValuePair("bname", bnamee));
				nameValuePairs.add(new BasicNameValuePair("bcategory", bcategory));
				String result = null;

				HttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost("http://10.0.2.2:800/lm/AdminConnect.php");
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
				Toast.makeText(AdminActivity.this, "Book Added Successfully", Toast.LENGTH_SHORT).show();
			}
			else
			{
				Toast.makeText(AdminActivity.this, result, Toast.LENGTH_SHORT).show();
			}

		}
	}
}