package com.example.gameapp;

//Names: Joel Murphy & Chris Bentley
//Purpose: Menu activity: Player can click on one of four buttons, options and start take
//the player to another activity. Help and Score opens up a dialog that displays information.

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Menu extends Activity {

	//Constant soundpool that plays sound effects
	final private SoundPool menuSound = new SoundPool(1,
			AudioManager.STREAM_MUSIC, 0);

	//Declare Constants
	private String passdiff; //Stores the user choice of difficulty
	private boolean passmus; //Stores the user choice of music state
	private boolean passsound; //Stores the user choice of sound state
	private MediaPlayer menuPlayer;  //Stores the Music
	private SQLiteAdapter mySQLiteAdapter; //Stores the database connections
	private String scoreString1; //Variable used for displaying scores
	private String scoreString2; //Variable used for displaying scores
	final Context context = this; //Select context for activity
	private int buttonsound; //Stores the id of the sound of buttons

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);

		//finds the id's of the buttons from the xml file
		Button startButton = (Button) findViewById(R.id.startButton);
		Button scoreButton = (Button) findViewById(R.id.scoreButton);
		Button optionsButton = (Button) findViewById(R.id.optionsButton);
		Button helpButton = (Button) findViewById(R.id.helpButton);

		//sets initial display score strings
		scoreString1 = getResources().getString(R.string.score_string1);
		scoreString2 = "";
		//sets the buttons to be clicked
		startButton.setOnClickListener(menuButtonListener);
		scoreButton.setOnClickListener(menuButtonListener);
		optionsButton.setOnClickListener(menuButtonListener);
		helpButton.setOnClickListener(menuButtonListener);

		//loads the 
		buttonsound = menuSound.load(this, R.raw.menu_button, 1);
		passsound = true;

		menuPlayer = MediaPlayer.create(getApplicationContext(),
				R.raw.menu_music);
		menuPlayer.start();
		menuPlayer.setLooping(true);
	}

	@Override
	protected void onStart() {
		buttonsound = menuSound.load(this, R.raw.menu_button, 1);
		super.onStop();
	}
	
	@Override
	protected void onStop() {
		//releases all music/sound resources when the activity is stopped
		if (passmus) {
			menuPlayer.release();
			menuPlayer = null;
		}
		menuSound.release();
		super.onStop();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//when this activity comes back to focus, it checks if there is something returned
		if (resultCode == RESULT_OK && requestCode == 101) {
			if (data.hasExtra("difficulty")) {
				//stores sent difficulty from activities
				passdiff = data.getExtras().getString("difficulty");
			}
			if (data.hasExtra("music")) {
				//stores sent music state from activities
				passmus = data.getExtras().getBoolean("music");
				checkMedia();
			}
			if (data.hasExtra("sound")) {
				//stores sent sound state from activities
				passsound = data.getExtras().getBoolean("sound");
			}
		}
	}

	private void checkMedia() {
		//if the state of music is true, then it starts playing the music
		if (passmus == true) {
			menuPlayer = MediaPlayer.create(getApplicationContext(),
					R.raw.menu_music);
			menuPlayer.start();
			menuPlayer.setLooping(true);
		}

	}

	private OnClickListener menuButtonListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.startButton:
				//if player clicks start, go to start activity
				if (passsound) { //if sound state is on, play a sound when clicked
					menuSound.play(buttonsound, 1.0f, 1.0f, 0, 0, 1.0f);
				}
				startGame();
				break;
			case R.id.scoreButton:
				//if player clicks score, display a alertdialog with scores
				if (passsound) {
					menuSound.play(buttonsound, 1.0f, 1.0f, 0, 0, 1.0f);
				}
				scoreGame();
				break;
			case R.id.optionsButton:
				//if player clicks score, go to options activity
				if (passsound) {
					menuSound.play(buttonsound, 1.0f, 1.0f, 0, 0, 1.0f);
				}
				optionsGame();
				break;
			case R.id.helpButton:
				//if player clicks score, display a alertdialog with help
				if (passsound) {
					menuSound.play(buttonsound, 1.0f, 1.0f, 0, 0, 1.0f);
				}
				helpButton();
				break;
			default:
				break;
			}
		}

		private void optionsGame() {
			//if any passed states are unassigned, assign them
			if (passdiff == null) {
				passdiff = getResources().getString(R.string.d_normal);
				passmus = true;
				passsound = true;
			}
			//Create an intent and pass data to Options Activity
			Intent optionsIntent = new Intent(Menu.this, OptionsMenu.class);
			optionsIntent.putExtra("difficulty", passdiff);
			optionsIntent.putExtra("music", passmus);
			optionsIntent.putExtra("sound", passsound);
			startActivityForResult(optionsIntent, 101);
		}

		private void helpButton() {
			//Set a AlertDialog with a simple how to play guide
			AlertDialog.Builder helpbuilder = new AlertDialog.Builder(context);
			helpbuilder.setTitle(getResources().getString(R.string.how_to_play));
			helpbuilder.setMessage(getResources().getString(R.string.description));
			helpbuilder.setCancelable(false);
			helpbuilder.setPositiveButton("Ok", null);

			//Create and display the alertdialog
			AlertDialog scoreDialog = helpbuilder.create();
			scoreDialog.show();
		}
		
		//show the score from the database
		@SuppressWarnings("deprecation")
		private void scoreGame() {
			//set up sqlite database
			mySQLiteAdapter = new SQLiteAdapter(context);
			mySQLiteAdapter.openToRead();
			Cursor cursor = mySQLiteAdapter.queueFiveScores();
			//set up alertdialog
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setTitle(getResources().getString(R.string.high_scores));
			if (cursor.getCount() != 0) {
				startManagingCursor(cursor);
				//set up string arrays to collect score table records
				String[] result = new String[cursor.getCount()];
				String[] result2 = new String[cursor.getCount()];
				//if cursor is at the first line then use a for loop to collect
				//the records in the table
				if (cursor.moveToFirst()) {
					for (int i = 0; i < cursor.getCount(); i++) {
						result[i] = cursor.getString(cursor
								.getColumnIndex(MyConstants.COLUMN_INITIALS));
						result2[i] = cursor.getString(cursor
								.getColumnIndex(MyConstants.COLUMN_SCORE));
						cursor.moveToNext();
					}
				}
				//assigning all the records to one string
				for (int i = 0; i < result.length; i++) {
					scoreString2 = scoreString2 + result[i]
							+ "\t\t\t\t\t\t\t\t" + result2[i] + "\n";
				}

			}
			//combine the records with the Column header
			scoreString1 = scoreString1 + scoreString2;
			builder.setMessage(scoreString1);
			builder.setCancelable(false);
			builder.setPositiveButton("Ok", null);
			//show the alert dialog
			AlertDialog scoreDialog = builder.create();
			scoreDialog.show();
			//reset the string values
			scoreString1 = getResources().getString(R.string.score_string1);
			scoreString2 = "";

		}

		private void startGame() {
			//if any passed states are unassigned, assign them
			if (passdiff == null) {
				passdiff = getResources().getString(R.string.d_normal);
				passmus = true;
				passsound = true;
			}
			//Create an intent and pass data to Game Activity
			Intent gameIntent = new Intent(Menu.this, GameActivity.class);
			gameIntent.putExtra("difficulty", passdiff);
			gameIntent.putExtra("music", passmus);
			gameIntent.putExtra("sound", passsound);
			startActivityForResult(gameIntent, 101);
		}
	};

	/*
	 * @Override public boolean onCreateOptionsMenu(Menu menu) { // Inflate the
	 * menu; this adds items to the action bar if it is present.
	 * getMenuInflater().inflate(R.menu.activity_menu, menu); return true; }
	 */

}