package com.example.gameapp;

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
import android.widget.TextView;

public class Menu extends Activity {

final private SoundPool menuSound = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);

private String passdiff;
private boolean passmus;
private boolean passsound;
private MediaPlayer menuPlayer;
private SQLiteAdapter mySQLiteAdapter;
private String scoreString1;
private String scoreString2;
final Context context = this;

private int buttonsound;

@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_menu);

	Button startButton = (Button) findViewById(R.id.startButton);
	Button scoreButton = (Button) findViewById(R.id.scoreButton);
	Button optionsButton = (Button) findViewById(R.id.optionsButton);
	Button helpButton = (Button) findViewById(R.id.helpButton);
	
	scoreString1 = "Name\t\tMaximum Round \n \n";
	scoreString2 = "";
	startButton.setOnClickListener(menuButtonListener);
	scoreButton.setOnClickListener(menuButtonListener);
	optionsButton.setOnClickListener(menuButtonListener);
	helpButton.setOnClickListener(menuButtonListener);

	//buttonsound = menuSound.load(this, R.raw.menu_button, 1);
	passsound = true;

	menuPlayer = MediaPlayer.create(getApplicationContext(), R.raw.menu_music);
	menuPlayer.start();
	menuPlayer.setLooping(true);
}

@Override
protected void onStop(){
	if(passmus){
		menuPlayer.release();
		menuPlayer = null;
	}
	menuSound.release();
	super.onStop();
}

@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
if (resultCode == RESULT_OK && requestCode == 101) {
if (data.hasExtra("difficulty")) {
passdiff = data.getExtras().getString("difficulty");
}
if (data.hasExtra("music")) {
passmus = data.getExtras().getBoolean("music");
checkMedia();
}
if (data.hasExtra("sound")) {
passsound = data.getExtras().getBoolean("sound");
}
}
}

private void checkMedia(){
     if (passmus == true){
     menuPlayer = MediaPlayer.create(getApplicationContext(), R.raw.menu_music);
     menuPlayer.start();
     menuPlayer.setLooping(true);
     }
    
}

private OnClickListener menuButtonListener = new OnClickListener() {
@Override
public void onClick(View v) {
switch(v.getId()){
case R.id.startButton:
//if (passsound){ menuSound.play(buttonsound, 1.0f, 1.0f, 0, 0, 1.0f); }
startGame();
break;
case R.id.scoreButton:
//if (passsound){ menuSound.play(buttonsound, 1.0f, 1.0f, 0, 0, 1.0f); }
scoreGame();
break;
case R.id.optionsButton:
//if (passsound){ menuSound.play(buttonsound, 1.0f, 1.0f, 0, 0, 1.0f); }
optionsGame();
break;
case R.id.helpButton:
//if (passsound){ menuSound.play(buttonsound, 1.0f, 1.0f, 0, 0, 1.0f); }
helpButton();
break;
default:
break;
}
}

private void optionsGame() {
if (passdiff == null) {
passdiff = getResources().getString(R.string.d_normal);
passmus = true;
passsound = true;
}
Intent optionsIntent = new Intent(Menu.this, OptionsMenu.class);
optionsIntent.putExtra("difficulty", passdiff);
optionsIntent.putExtra("music", passmus);
optionsIntent.putExtra("sound", passsound);
startActivityForResult(optionsIntent, 101);
}

private void helpButton() {
AlertDialog.Builder helpbuilder = new AlertDialog.Builder(context);
helpbuilder.setTitle("How to Play!");
//builder.setMessage("Stuff");
helpbuilder.setMessage(String.format("%s",
("Press the button before the opponent does. However press it too soon and you'll lose! Each round gets more difficult!")));
helpbuilder.setCancelable(false);
helpbuilder.setPositiveButton("Ok", null);

AlertDialog scoreDialog = helpbuilder.create();
scoreDialog.show();
}

@SuppressWarnings("deprecation")
private void scoreGame() {
	mySQLiteAdapter = new SQLiteAdapter(context);
	mySQLiteAdapter.openToRead();
	Cursor cursor = mySQLiteAdapter.queueFiveScores();
	AlertDialog.Builder builder = new AlertDialog.Builder(context);
	builder.setTitle("High Scores");
	if (cursor.getCount() != 0) {
			startManagingCursor(cursor);
			String[] columns = new String[]{MyConstants.COLUMN_INITIALS, MyConstants.COLUMN_SCORE};
			String[] result = new String[cursor.getCount()];
			String[] result2 = new String[cursor.getCount()];
             if(cursor.moveToFirst()){
                  for (int i = 0; i < cursor.getCount(); i++){
                      result[i] = cursor.getString(cursor.getColumnIndex(MyConstants.COLUMN_INITIALS));
                      result2[i] = cursor.getString(cursor.getColumnIndex(MyConstants.COLUMN_SCORE));
                      cursor.moveToNext();
                    }//end of for
             }
             for (int i = 0; i < result.length; i++) {
     			scoreString2 = scoreString2 + result[i] + "\t\t\t\t\t\t\t\t" + result2[i] + "\n";
     		}
			
		}
		scoreString1 = scoreString1 + scoreString2;
			builder.setMessage(scoreString1);
	builder.setCancelable(false);
	builder.setPositiveButton("Ok", null);

	AlertDialog scoreDialog = builder.create();
	scoreDialog.show();
	scoreString1 = "Name\t\tMaximum Round \n \n";
	scoreString2 = ""; 

}

private void startGame() {
if (passdiff == null) {
passdiff = getResources().getString(R.string.d_normal);
passmus = true;
passsound = true;
}
Intent gameIntent = new Intent(Menu.this, GameActivity.class);
gameIntent.putExtra("difficulty", passdiff);
gameIntent.putExtra("music", passmus);
gameIntent.putExtra("sound", passsound);
startActivityForResult(gameIntent, 101);
}
};

/*@Override
public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
getMenuInflater().inflate(R.menu.activity_menu, menu);
return true;
}*/

}