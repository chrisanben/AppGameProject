package com.example.gameapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class OptionsMenu extends Activity {

	private String difficulty;
	private TextView diff;
	private Integer MY_REQUEST_ID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_options_menu);
		
		diff = (TextView) findViewById(R.id.difficulty);
		Button easy = (Button) findViewById(R.id.easy_button);
		Button normal = (Button) findViewById(R.id.normal_button);
		Button hard = (Button) findViewById(R.id.hard_button);
		Button save = (Button) findViewById(R.id.save_button);
		
		save.setOnClickListener(optionsButtonListener);
		easy.setOnClickListener(optionsButtonListener);
		normal.setOnClickListener(optionsButtonListener);
		hard.setOnClickListener(optionsButtonListener);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			difficulty = extras.getString("difficulty");
		}
		
		diff.setText("Difficulty: " + difficulty);
		
	}
	
	@Override
	public void finish(){
		Intent passData = new Intent(OptionsMenu.this, Menu.class);
		passData.putExtra("difficulty", difficulty);
		setResult(RESULT_OK, passData);
		super.finish();
	}
	
	private OnClickListener optionsButtonListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.easy_button:
				difficulty = "Easy";
				diff.setText("Difficulty: " + difficulty);
				break;
			case R.id.normal_button:
				difficulty = "Normal";
				diff.setText("Difficulty: " + difficulty);
				break;
			case R.id.hard_button:
				difficulty = "Hard";
				diff.setText("Difficulty: " + difficulty);
				break;
			case R.id.save_button:
				finish();
				break;
			default:
				break;
			}
		}
	};

}
