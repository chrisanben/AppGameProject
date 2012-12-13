package com.example.gameapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Menu extends Activity {

private final String NO_INITIALS = "---";
private final int NO_SCORE = 0;

private TextView mainTitle;
private String passdiff;
private String passmus;
private MediaPlayer menuPlayer;
private SQLiteAdapter mySQLiteAdapter;
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

mainTitle.setText("Critical Hit");

menuPlayer = MediaPlayer.create(getApplicationContext(), R.raw.menu_music);
menuPlayer.start();
menuPlayer.setLooping(true);
}

@Override
protected void onStop(){
if(passmus == "on"){
menuPlayer.release();
menuPlayer = null;
}
super.onStop();
}

@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
if (resultCode == RESULT_OK && requestCode == 101) {
if (data.hasExtra("difficulty")) {
passdiff = data.getExtras().getString("difficulty");
}
if (data.hasExtra("music")) {
passmus = data.getExtras().getString("music");
if (passmus == "on"){
menuPlayer = MediaPlayer.create(getApplicationContext(), R.raw.menu_music);
menuPlayer.start();
menuPlayer.setLooping(true);
}
}
}
}

private OnClickListener menuButtonListener = new OnClickListener() {
@Override
public void onClick(View v) {
switch(v.getId()){
case R.id.startButton:
startGame();
break;
case R.id.scoreButton:
scoreGame();
break;
case R.id.optionsButton:
optionsGame();
break;
case R.id.helpButton:
helpButton();
break;
default:
break;
}
}

private void optionsGame() {
if (passdiff == null) {
passdiff = "Normal";
passmus = "on";
}
Intent optionsIntent = new Intent(Menu.this, OptionsMenu.class);
optionsIntent.putExtra("difficulty", passdiff);
optionsIntent.putExtra("music", passmus);
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

	private void scoreGame() {
		mySQLiteAdapter = new SQLiteAdapter(context);
		mySQLiteAdapter.openToRead();
		Cursor cursor = mySQLiteAdapter.queueFiveScores();
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Score");
		if (cursor.getCount() == 0) {
			builder.setMessage(NO_INITIALS, NO_SCORE);
		} else {
			startManagingCursor(cursor);
			// create column info for cursor adapter
	        String[] columns = new String[]{MyConstants.COLUMN_INITIALS, MyConstants.COLUMN_SCORE};
	        int[] to = new int[]{R.id.shoppingListNameTextView};
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
if (passdiff == null) {
passdiff = "Normal";
passmus = "on";
}
Intent optionsIntent = new Intent(Menu.this, GameActivity.class);
optionsIntent.putExtra("difficulty", passdiff);
optionsIntent.putExtra("music", passmus);
startActivityForResult(optionsIntent, 102);
}
};

/*@Override
public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
getMenuInflater().inflate(R.menu.activity_menu, menu);
return true;
}*/

}