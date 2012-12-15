package com.example.gameapp;

//Names: Joel Murphy & Chris Bentley
//Purpose: Options activity: Player can click the different buttons to change the
//settings for the game, including difficulty, music and sound. The values are
//passed through setResult back to Main Menu

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class OptionsMenu extends Activity {

	//Declare variables
	private String difficulty; //Stores the user choice of difficulty
	private boolean musicState; //Stores the user choice of music state
	private boolean soundState; //Stores the user choice of sound state
	private TextView diff; //Determines the textview difficulty
	private TextView musica; //Determines the textview music
	private TextView sound; //Determines the textview sound
	private MediaPlayer optionsMusic; //Stores the Music
	private int buttonsound; //Stores the id of the sound of buttons
	
	//Constant soundpool that plays sound effects
	final private SoundPool optionSound = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_options_menu);
		
		//finds the id's of the buttons from the xml file
		diff = (TextView) findViewById(R.id.difficulty);
		musica = (TextView) findViewById(R.id.music);
		sound = (TextView) findViewById(R.id.sound);
		Button easy = (Button) findViewById(R.id.easy_button);
		Button normal = (Button) findViewById(R.id.normal_button);
		Button hard = (Button) findViewById(R.id.hard_button);
		Button save = (Button) findViewById(R.id.save_button);
		Button on = (Button) findViewById(R.id.on_button);
		Button off = (Button) findViewById(R.id.off_button);
		Button soundoff = (Button) findViewById(R.id.sound_off);
		Button soundon = (Button) findViewById(R.id.sound_on);

		//sets the buttons to be clicked
		save.setOnClickListener(optionsButtonListener);
		easy.setOnClickListener(optionsButtonListener);
		normal.setOnClickListener(optionsButtonListener);
		hard.setOnClickListener(optionsButtonListener);
		on.setOnClickListener(optionsButtonListener);
		off.setOnClickListener(optionsButtonListener);
		soundon.setOnClickListener(optionsButtonListener);
		soundoff.setOnClickListener(optionsButtonListener);
		
		//Sets the id for the button
		buttonsound = optionSound.load(this, R.raw.menu_button, 1);

		//Get the values sent by intent
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			difficulty = extras.getString("difficulty");
			musicState = extras.getBoolean("music");
			soundState = extras.getBoolean("sound");
		}

		//sets the text accordingly to the passed choices
		diff.setText(getResources().getString(R.string.difficulty_options) + difficulty);
		musica.setText(getResources().getString(R.string.music_options) + " " + musicState);
		sound.setText(getResources().getString(R.string.sound_effects) + " " + soundState);
	}

	@Override
	protected void onStart(){
		super.onStart();
		startMedia();
	}

	@Override
	protected void onStop(){
		//Releases resources when the activity is no longer in use
		if (musicState){
		optionsMusic.release();
		optionsMusic = null;
		}
		optionSound.release();
		super.onStop();
	}

	@Override
	public void finish(){
		//Passes the settings selected back to the main menu
		Intent passData = new Intent(OptionsMenu.this, Menu.class);
		passData.putExtra("difficulty", difficulty);
		passData.putExtra("music", musicState);
		passData.putExtra("sound", soundState);
		setResult(RESULT_OK, passData);
		super.finish();
	}

	private OnClickListener optionsButtonListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.easy_button:
				//if easy button is selected change the textview and the stored difficulty
				//if the sound is on, then play sound on the press of any button
				if (soundState){ optionSound.play(buttonsound, 1.0f, 1.0f, 0, 0, 1.0f); }
				difficulty = getResources().getString(R.string.d_easy);;
				diff.setText(getResources().getString(R.string.difficulty_options) + " " + difficulty);
				break;
			case R.id.normal_button:
				//if normal button is selected change the textview and the stored difficulty
				if (soundState){ optionSound.play(buttonsound, 1.0f, 1.0f, 0, 0, 1.0f); }
				difficulty = getResources().getString(R.string.d_normal);
				diff.setText(getResources().getString(R.string.difficulty_options) + " " + difficulty);
				break;
			case R.id.hard_button:
				//if hard button is selected change the textview and the stored difficulty
				if (soundState){ optionSound.play(buttonsound, 1.0f, 1.0f, 0, 0, 1.0f); }
				difficulty = getResources().getString(R.string.d_hard);
				diff.setText(getResources().getString(R.string.difficulty_options) + " " + difficulty);
				break;
			case R.id.off_button:
				//turn the music off if clicked
				if (soundState){ optionSound.play(buttonsound, 1.0f, 1.0f, 0, 0, 1.0f); }
				if (musicState == true){
					musicState = false;
					changeMedia();
					musica.setText(getResources().getString(R.string.music_options) + " " + musicState);
				}
				break;
			case R.id.on_button:
				//turn the music on if clicked
				if (soundState){ optionSound.play(buttonsound, 1.0f, 1.0f, 0, 0, 1.0f); }
				if (musicState == false){
					musicState = true;
					changeMedia();
					musica.setText(getResources().getString(R.string.music_options) + " " + musicState);
				}
				break;
			case R.id.save_button:
				//close activity and send to meny all saved data
				if (soundState)
					optionSound.play(buttonsound, 1.0f, 1.0f, 0, 0, 1.0f);
				finish();
				break;
			case R.id.sound_off:
				//turn sound effects off
				soundState = false;
				if (soundState)
					optionSound.play(buttonsound, 1.0f, 1.0f, 0, 0, 1.0f);
				sound.setText(getResources().getString(R.string.sound_effects) + " " + soundState);
				break;
			case R.id.sound_on:
				//turn sound effects on
				soundState = true;
				if (soundState)
					optionSound.play(buttonsound, 1.0f, 1.0f, 0, 0, 1.0f);
				sound.setText(getResources().getString(R.string.sound_effects) + " " + soundState);
				break;
			default:
				break;
			}
		}
	};

	public void changeMedia(){
		//if the setting for music is on, then turn it on, else pause it.
		if (musicState){
			openMedia();
		} else {
			optionsMusic.pause();
		}
	}

	public void openMedia(){
		//opens the MediaPlayer for the game and starts the music
		optionsMusic = MediaPlayer.create(getApplicationContext(), R.raw.options_music);
		optionsMusic.start();
		optionsMusic.setLooping(true);
	}

	public void closeMedia(){
		//releases the resources of the music
		optionsMusic.release();
		optionsMusic = null;
	}

	public void startMedia(){
		if (musicState){
			openMedia();
		} 
	}

}