package com.example.gameapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Menu extends Activity {

	private boolean mIsBound = false;
	private TextView mainTitle;
	final Context context = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		
		Button startButton = (Button) findViewById(R.id.startButton);
		Button scoreButton = (Button) findViewById(R.id.scoreButton);
		Button optionsButton = (Button) findViewById(R.id.optionsButton);
		Button helpButton = (Button) findViewById(R.id.helpButton);
		
		mainTitle = (TextView) findViewById(R.id.textView1);
		
		startButton.setOnClickListener(menuButtonListener);
		scoreButton.setOnClickListener(menuButtonListener);
		optionsButton.setOnClickListener(menuButtonListener);
		helpButton.setOnClickListener(menuButtonListener);
		
		mainTitle.setText("Critical Hat");
	}

	private OnClickListener menuButtonListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.startButton:
				mainTitle.setText("Start");
				startGame();
				break;
			case R.id.scoreButton:
				mainTitle.setText("Score");
				scoreGame();
				break;
			case R.id.optionsButton:
				optionsGame();
				break;
			case R.id.helpButton:
				mainTitle.setText("Help");
				helpButton();
				break;
			default:
				break;
			}
		}

		private void optionsGame() {
			String passString = "Options Menu";
			Intent optionsIntent = new Intent(Menu.this, OptionsMenu.class);
			optionsIntent.putExtra("new_variable", passString);
			Menu.this.startActivity(optionsIntent);
		}

		private void helpButton() {
			// TODO Auto-generated method stub
			
		}

		private void scoreGame() {
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setTitle("Score");
			//builder.setMessage("Stuff");
			builder.setMessage(String.format("%s\n\n %s%d\n %s%d\n %s%d\n %s%d\n %s%d\n",
					("Name\t\tMaximum Round"),
					("AAA\t\t\t\t\t\t\t\t"), (50),
					("BBB\t\t\t\t\t\t\t\t"), (40),
					("CCC\t\t\t\t\t\t\t\t"), (30),
					("DDD\t\t\t\t\t\t\t"), (20),
					("EEE\t\t\t\t\t\t\t\t"), (10)));
			builder.setCancelable(false);
			builder.setPositiveButton("Ok", null);
			
			AlertDialog scoreDialog = builder.create();
			scoreDialog.show();
			
		}

		private void startGame() {
			// TODO Auto-generated method stub
		}
	};

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_menu, menu);
		return true;
	}*/

}
