package ca.joelmurphy.gameappjc;

import java.util.Random;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GameActivity extends Activity {
	
	private final long roundTime = 3000;
	private final long countBy = 1000;
	private final int E_BUTTON_INCREMENT = 75;
	private final int N_BUTTON_INCREMENT = 100;
	private final int H_BUTTON_INCREMENT = 150;
	private final int MIN_BUTTON = 100;
	private final String WIN = "You Win!!";
	private final String LOSE = "You Lost!";
	private final String ROUND = "Round ";
	private final String FINISHED = "Finished ";
	private final String TOO_SLOW_LOSE = "Too Slow!";
	private final String TOO_SOON_LOSE = "Too Soon!";
	final Context context = this;
	
	private long startTime;
	private long randomTime;
	private ImageButton[] button = new ImageButton[9];
	private int randButton;
	private int score;
	private int roundNum;
	private TextView timerTextView;
	private TextView scoreTextView;
	private TextView roundTextView;
	private TextView winLoseTextView;
	private TextView buttonHereTextView;
	private TextView inbetweenTextView;
	private TextView roundDisplayTextView;
	private TextView waitingTextView;
	private TextView newWinTextView;
	private LinearLayout gameLayout;
	private MyCountDownTimer roundTimer;
	private MyCountDownTimer randomTimer;
	private MyCountDownTimer buttonTimer;
	private MyCountDownTimer waitTimer;
	private boolean roundDisplay;
	private boolean inbetween;
	private boolean waiting;
	private boolean buttonHere;
	private boolean win;
	private int newWin;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_activity);
		
		startTime = 2000;
		newWin = 0;
		score = 0;
		roundNum = 1;
		randomTime = 500 + randomTime();
		waiting = false;
		buttonHere = false;
		roundDisplay = true;
		inbetween = false;
		win = true;
		newWinTextView = (TextView) findViewById(R.id.newWinTextView);
		buttonHereTextView = (TextView) findViewById(R.id.buttonHereTextView);
		inbetweenTextView = (TextView) findViewById(R.id.inbetweenTextView);
		roundDisplayTextView = (TextView) findViewById(R.id.roundDisplayTextView);
		waitingTextView = (TextView) findViewById(R.id.waitingTextView);
		timerTextView = (TextView) findViewById(R.id.timerTextView);
		scoreTextView = (TextView) findViewById(R.id.scoreTextView);
		roundTextView = (TextView) findViewById(R.id.roundTextView);
		winLoseTextView = (TextView) findViewById(R.id.winLoseTextView);
		roundTimer = new MyCountDownTimer(roundTime, countBy);
		randomTimer = new MyCountDownTimer(randomTime, countBy);
		buttonTimer = new MyCountDownTimer(startTime, countBy);
		waitTimer = new MyCountDownTimer(roundTime, countBy);
		
		gameLayout = (LinearLayout) findViewById(R.id.gameLayout);
		//gameLayout.setOnClickListener(buttonClickListener);
		gameLayout.setOnTouchListener(buttonTouchListener);
		
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
			button[i].setVisibility(-1);
		}
		
		roundTimer.start();
			
	}
	
	OnTouchListener buttonTouchListener = new OnTouchListener(){
		
	
	@Override
    public boolean onTouch(View v, MotionEvent event) {
		int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN){
        	if ((!roundDisplay) && (!waiting)) {
        		if (v.getId() == button[randButton].getId()) {
        			if (newWin == 0) {
        				newWin = 1;
        			}
        			buttonHereTextView.setText(String.valueOf(buttonHere));
        			inbetweenTextView.setText(String.valueOf(inbetween));
        			roundDisplayTextView.setText(String.valueOf(roundDisplay));
        			waitingTextView.setText(String.valueOf(waiting));
        			newWinTextView.setText(String.valueOf(newWin));	
        			/*if (buttonHere) {
    				buttonTimer.cancel();
        			} else if (inbetween) {
    				randomTimer.cancel();
        			}*/
    			} else {
    				newWin = -1;
    				//win = false;
    				//buttonTimer.cancel();
    				//randomTimer.cancel();
    			}
        	}
        }
        return false;
	}
	};
	
	/*private OnTouchListener buttonTouchListener() {
		@Override
	    public boolean onTouch(View v, MotionEvent event) {
	        ImageView img = (ImageView) v;
	        int action = event.getAction();
	        if (action == MotionEvent.ACTION_DOWN){
	            img.setImageResource(R.drawable.port);
	        }else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL){
	            img.setImageResource(R.drawable.bar);
	        }               
	        return false;
	    }
	}*/

	/*OnClickListener buttonClickListener = new OnClickListener(){
		
		@Override
		public void onClick(View v) {
			if (v.getId() == R.id.gameLayout) {
				scoreTextView.setText("LAYOUT");
			} else {
			String idString = String.valueOf(((ImageButton) v).getId());
			scoreTextView.setText(idString);
			for (int i = 0; i <= 8; i++) {
				button[i].setVisibility(-1);
			}
			button[randomButton()].setVisibility(1);
			
			}
			
		}
	};*/
	
	public class MyCountDownTimer extends CountDownTimer {
		
		public MyCountDownTimer(long startTime, long countBy) {
			super(startTime, countBy);
		}
		
		//when the timer finishes and they haven't cleared all the cards
		//tell them they lost and disable the buttons because the Alert Dialog doesn't work
		@Override
		public void onFinish() {
			if (newWin != -1) {
				if (roundDisplay) {
					randButton = randomButton();
					randomTime = 500 + randomTime();
					randomTimer = null;
					randomTimer = new MyCountDownTimer(randomTime, countBy);
					roundTextView.setVisibility(-1);
					roundDisplay = false;
					//winLoseTextView.setVisibility(-1);
					randomTimer.start();
					inbetween = true;
				} else if (waiting) {
					if (startTime - N_BUTTON_INCREMENT >= 100) {
						startTime -=  N_BUTTON_INCREMENT;
					} else {
						startTime = MIN_BUTTON;
					}
					buttonTimer = null;
					buttonTimer = new MyCountDownTimer(startTime, countBy);
					button[randButton].setVisibility(-1);
					winLoseTextView.setVisibility(-1);
					roundTextView.setText(ROUND + String.valueOf(roundNum));
					roundTextView.setVisibility(1);
					waiting = false;
					roundTimer.start();
					roundDisplay = true;
					newWin = 0;
				} else if (inbetween) {
					inbetween = false;
					buttonTimer.start();
					buttonHere = true;
					button[randButton].setVisibility(1);
				} else if (buttonHere) {
					if (newWin == 1) {
						winLoseTextView.setText(WIN);
						winLoseTextView.setVisibility(1);
						score += 1;
						scoreTextView.setText(String.valueOf(score));
						buttonHere = false;
						roundNum += 1;
						roundTimer.start();
						waiting = true;
					} else {
						winLoseTextView.setText("Time's Up!");
						loseCondition();
					}
				}
			} else {
				winLoseTextView.setText(TOO_SOON_LOSE);
				loseCondition();
			}
		}
		
		//tick the timer down by 1 second
		@Override
		public void onTick(long millisUntilFinished) {
			timerTextView.setText(String.valueOf(millisUntilFinished));
			buttonHereTextView.setText(String.valueOf(buttonHere));
			inbetweenTextView.setText(String.valueOf(inbetween));
			roundDisplayTextView.setText(String.valueOf(roundDisplay));
			waitingTextView.setText(String.valueOf(waiting));
			newWinTextView.setText(String.valueOf(newWin));
		}
	}
	public int randomTime() {
		int rTime;
		Random random = new Random();
		rTime = random.nextInt(2000);
		return rTime;
	}
	public int randomButton() {
		int rButton;
		Random random = new Random();
		rButton = random.nextInt(9);
		return rButton;
	}
	
	public void loseCondition() {
		button[randButton].setEnabled(false);
		winLoseTextView.setVisibility(1);
		roundTextView.setText(FINISHED);
		roundTextView.setVisibility(1);
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(LOSE);
		builder.setMessage("Your score is: " + String.valueOf(score));
		builder.setCancelable(false);
		builder.setPositiveButton("Back to Menu", backToMenu());
		AlertDialog finishedDialog = builder.create();
		finishedDialog.show();
	}
	
	public android.content.DialogInterface.OnClickListener backToMenu() {
		Intent menuIntent = new Intent(context, Menu.class);
		startActivityForResult(menuIntent, 101);
		return null;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game_activity, menu);
		return true;
	}

}
