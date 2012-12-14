package com.example.gameapp;

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

	private String difficulty;
	private boolean musicState;
	private boolean soundState;
	private TextView diff;
	private TextView musica;
	private TextView sound;
	private MediaPlayer optionsMusic;
	final private SoundPool optionSound = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
	private int buttonsound;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_options_menu);
		
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

		save.setOnClickListener(optionsButtonListener);
		easy.setOnClickListener(optionsButtonListener);
		normal.setOnClickListener(optionsButtonListener);
		hard.setOnClickListener(optionsButtonListener);
		on.setOnClickListener(optionsButtonListener);
		off.setOnClickListener(optionsButtonListener);
		soundon.setOnClickListener(optionsButtonListener);
		soundoff.setOnClickListener(optionsButtonListener);
		
		
		buttonsound = optionSound.load(this, R.raw.menu_button, 1);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			difficulty = extras.getString("difficulty");
			musicState = extras.getBoolean("music");
			soundState = extras.getBoolean("sound");
		}

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
		optionsMusic.release();
		optionsMusic = null;
		optionSound.release();
		super.onStop();
	}

	@Override
	public void finish(){
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
				if (soundState){ optionSound.play(buttonsound, 1.0f, 1.0f, 0, 0, 1.0f); }
				difficulty = getResources().getString(R.string.d_easy);;
				diff.setText(getResources().getString(R.string.difficulty_options) + " " + difficulty);
				break;
			case R.id.normal_button:
				if (soundState){ optionSound.play(buttonsound, 1.0f, 1.0f, 0, 0, 1.0f); }
				difficulty = getResources().getString(R.string.d_normal);
				diff.setText(getResources().getString(R.string.difficulty_options) + " " + difficulty);
				break;
			case R.id.hard_button:
				if (soundState){ optionSound.play(buttonsound, 1.0f, 1.0f, 0, 0, 1.0f); }
				difficulty = getResources().getString(R.string.d_hard);
				diff.setText(getResources().getString(R.string.difficulty_options) + " " + difficulty);
				break;
			case R.id.off_button:
				if (soundState){ optionSound.play(buttonsound, 1.0f, 1.0f, 0, 0, 1.0f); }
				if (musicState == true){
					musicState = false;
					changeMedia();
					musica.setText(getResources().getString(R.string.music_options) + " " + musicState);
				}
				break;
			case R.id.on_button:
				if (soundState){ optionSound.play(buttonsound, 1.0f, 1.0f, 0, 0, 1.0f); }
				if (musicState == false){
					musicState = true;
					changeMedia();
					musica.setText(getResources().getString(R.string.music_options) + " " + musicState);
				}
				break;
			case R.id.save_button:
				if (soundState)
					optionSound.play(buttonsound, 1.0f, 1.0f, 0, 0, 1.0f);
				finish();
				break;
			case R.id.sound_off:
				soundState = false;
				if (soundState)
					optionSound.play(buttonsound, 1.0f, 1.0f, 0, 0, 1.0f);
				sound.setText(getResources().getString(R.string.sound_effects) + " " + soundState);
				break;
			case R.id.sound_on:
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
		if (musicState){
			openMedia();
		} else {
			optionsMusic.pause();
		}
	}

	public void openMedia(){
		optionsMusic = MediaPlayer.create(getApplicationContext(), R.raw.options_music);
		optionsMusic.start();
		optionsMusic.setLooping(true);
	}

	public void closeMedia(){
		optionsMusic.release();
		optionsMusic = null;
	}

	public void startMedia(){
		if (musicState){
			openMedia();
		} 
	}

}