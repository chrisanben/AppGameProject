package com.example.gameapp;

import java.util.Random;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GameActivity extends Activity {
	
	private final long countBy = 1000;
	
	private long startTime = 5000;
	private ImageButton[] button = new ImageButton[9];
	private TextView timerTextView;
	private TextView scoreTextView;
	private LinearLayout gameLayout;
	private MyCountDownTimer timer;
	private String difficulty;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_activity);
		
		timerTextView = (TextView) findViewById(R.id.timerTextView);
		scoreTextView = (TextView) findViewById(R.id.scoreTextView);
		
		timer = new MyCountDownTimer(startTime, countBy);
		
		gameLayout = (LinearLayout) findViewById(R.id.gameLayout);
		//gameLayout.setOnClickListener(buttonClickListener);
		gameLayout.setOnTouchListener(buttonTouchListener);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			difficulty = extras.getString("difficulty");
		}
		
		button[0] = (ImageButton) findViewById(R.id.button0);
		button[1] = (ImageButton) findViewById(R.id.button1);
		button[2] = (ImageButton) findViewById(R.id.button2);
		button[3] = (ImageButton) findViewById(R.id.button3);
		button[4] = (ImageButton) findViewById(R.id.button4);
		button[5] = (ImageButton) findViewById(R.id.button5);
		button[6] = (ImageButton) findViewById(R.id.button6);
		button[7] = (ImageButton) findViewById(R.id.button7);
		button[8] = (ImageButton) findViewById(R.id.button8);
		/*for (int i = 0; i < 9; i++) {
			button[i].setOnClickListener(buttonClickListener);
		}*/
		
		for (int i = 0; i < 9; i++) {
			button[i].setOnTouchListener(buttonTouchListener);
		}
		
		timer.start();
	}
	
	OnTouchListener buttonTouchListener = new OnTouchListener(){

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN){
        	if (v.getId() == R.id.gameLayout) {
				scoreTextView.setText("LAYOUT");
			} else {
			String idString = String.valueOf(((ImageButton) v).getId());
			scoreTextView.setText(idString);
			for (int i = 0; i < 9; i++) {
				button[i].setVisibility(-1);
			}
			button[randomButton()].setVisibility(1);
			
			}
        }
		return false;
	}
	};

	public class MyCountDownTimer extends CountDownTimer {
		
		public MyCountDownTimer(long startTime, long countBy) {
			super(startTime, countBy);
		}
		
		//when the timer finishes and they haven't cleared all the cards
		//tell them they lost and disable the buttons because the Alert Dialog doesn't work
		@Override
		public void onFinish() {
			timerTextView.setText("FINISHED");
			/*if (allCleared == false) {
				winTextView.setText(R.string.you_lose);
				// AlertDialog.Builder(endGame);
				for (int row = 0; row < numOfRows; row++) {
					for (int col = 0; col < rowObjects; col++) {
						cardImage[row][col].setEnabled(false);
					}
				}
			}*/
		}
		
		//tick the timer down by 1 second
		@Override
		public void onTick(long millisUntilFinished) {
			timerTextView.setText(String.valueOf(millisUntilFinished));
		}
	}
	public int randomButton() {
		int rButton;
		Random random = new Random();
		rButton = random.nextInt(9);
		return rButton;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game_activity, menu);
		return true;
	}

}
