package com.example.librarymanager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class BookDetailsActivity extends Activity {

	TextView bookid,bookname,bookcategoy;
	String bid,bname,bcategory;


	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book_details);
		bookid = (TextView)findViewById(R.id.bookid_view);
		bookname = (TextView)findViewById(R.id.bookname_view);
		bookcategoy = (TextView)findViewById(R.id.bookcategory_view);

		bid= getIntent().getStringExtra("id");
		bname= getIntent().getStringExtra("name");
		bcategory= getIntent().getStringExtra("category");
		bookid.setText(bid);
		bookname.setText(bname);
		bookcategoy.setText(bcategory);
		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	public void reserve(View v){
		Toast.makeText(this, "Book reserved", Toast.LENGTH_SHORT).show();
		Intent bookintent = new Intent(this, BooksActivity.class);
		startActivity(bookintent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.book_details, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
