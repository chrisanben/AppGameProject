package com.example.gameapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

public class OptionsMenu extends Activity {

	private String passedtext;
	
	private TextView test;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_options_menu);
		
		TextView test = (TextView) findViewById(R.id.test);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			passedtext = extras.getString("new_variable");
		}
		
		test.setText(passedtext);
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_options_menu, menu);
		return true;
	}

}
