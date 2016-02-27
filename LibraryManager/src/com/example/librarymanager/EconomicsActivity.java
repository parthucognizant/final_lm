package com.example.librarymanager;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class EconomicsActivity extends Fragment implements OnItemClickListener   {

	ListView economiclist;

	String myJSON;

	private static final String TAG_RESULTS="economics";
	private static final String TAG_ID = "id";
	private static final String TAG_NAME = "name";
	private static final String TAG_ADD ="category";

	JSONArray jsoneconomics = null;

	ArrayList<HashMap<String, String>> economicslist;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.activity_economics, container, false);
		economiclist = (ListView)rootView.findViewById(R.id.economics_list);

		economicslist = new ArrayList<HashMap<String,String>>();
		getData();

		economiclist.setOnItemClickListener(this);

		return rootView;
	}

	public void getData() {
		class GetDataJSON extends AsyncTask<String, Void, String>{

			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub

				DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
				HttpPost httppost = new HttpPost("http://10.0.2.2:800/lm/economics.php");

				httppost.setHeader("content-type", "application.json");

				InputStream inputstream = null;
				String result = null;
				try{
					HttpResponse httpresponse = httpclient.execute(httppost);
					HttpEntity entity = httpresponse.getEntity();

					inputstream = entity.getContent();
					BufferedReader reader = new BufferedReader(new InputStreamReader(inputstream, "UTF-8"), 8);
					StringBuilder sb = new StringBuilder();
					String line = null;
					while ((line = reader.readLine()) != null)
					{
						sb.append(line + "\n");
					}
					result = sb.toString();
				}
				catch (Exception e) {
					// Oops
				}
				finally {
					try{
						if(inputstream != null)
							inputstream.close();
					}catch(Exception squish){}
				}
				return result;
			}

			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				myJSON=result;
				showList();
			}
		}
		GetDataJSON g = new GetDataJSON();
		g.execute();

	}

	private void showList() {
		// TODO Auto-generated method stub
		try{
			JSONObject jsonObj = new JSONObject(myJSON);
			jsoneconomics = jsonObj.getJSONArray(TAG_RESULTS);

			for(int i=0;i<jsoneconomics.length();i++){
				JSONObject c = jsoneconomics.getJSONObject(i);
				String id = c.getString(TAG_ID);
				String name = c.getString(TAG_NAME);
				String category = c.getString(TAG_ADD);

				HashMap<String,String> hasheconomics = new HashMap<String,String>();
				hasheconomics.put(TAG_ID,id);
				hasheconomics.put(TAG_NAME,name);
				hasheconomics.put(TAG_ADD,category);
				economicslist.add(hasheconomics);
			}
			ListAdapter adapter = new SimpleAdapter(
					getActivity(),economicslist,
					R.layout.liststyle,
					new String[]{TAG_ID,TAG_NAME,TAG_ADD},
					new int[]{R.id.id, R.id.name, R.id.category}
					);
			economiclist.setAdapter(adapter);
		}catch (JSONException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub


		String bid=economicslist.get(position).get("id");
		String bname=economicslist.get(position).get("name");
		String bcategory=economicslist.get(position).get("category");
		//Log.i(TAG, ""+id+"="+name1+"="+cate);
		Intent detailsintent  = new Intent(getActivity(),BookDetailsActivity.class );
		detailsintent.putExtra("id", bid);
		detailsintent.putExtra("name", bname);
		detailsintent.putExtra("category", bcategory);
		startActivity(detailsintent);



	}

}

