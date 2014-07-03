package com.procedural.demo;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.glass.media.Sounds;

public class MainActivity extends Activity {
	
	private enum Stage {
		Start, Option1, Option2, End
	}
	
	private Stage mCurrentStage;
	
	private TextView mText;
	
	// User answers
	private String mColor;
	private String mNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

	// Don't let the screen go to sleep
	getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_main);
        
        mText = (TextView) findViewById(R.id.text);
        
        mCurrentStage = Stage.Start;
        mText.setText(getString(R.string.stage_start));
    }

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_DPAD_CENTER){
			switch(mCurrentStage) {
			case Start:
				// We currently showing the start screen,
				// On tap we should show the next step: option1
				setText(getString(R.string.stage_option1));
				mCurrentStage = Stage.Option1;
				playClickSound();
				break;
			case Option1:
				// We are currently showing the option1 screen
				// On tap we should show the option1 menu to give the
				// user a choice
				playClickSound();
				invalidateOptionsMenu();
				openOptionsMenu();
				break;
			case Option2:
				// We are currently showing the option1 screen
				// On tap we should show the option1 menu to give the
				// user a choice
				playClickSound();
				invalidateOptionsMenu();
				openOptionsMenu();
				break;
			case End:
				// The "Tap to finish" message is being displayed,
				// so on tap, we should finish this Activity
				playSuccessSound();
				finish();
				break;
			}
			
			return true;
		}
		
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		if(mCurrentStage == Stage.Option1){
			// If we're on the color option, the menu should have
			// the colors on it
			getMenuInflater().inflate(R.menu.menu_color, menu);
		} else {
			// Otherwise use the menu with numbers
			getMenuInflater().inflate(R.menu.menu_number, menu);
		}

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case R.id.red:
			mColor = "#FF0000";
			mCurrentStage = Stage.Option2;
			setText(getString(R.string.stage_option2));
			return true;
		case R.id.blue:
			mColor = "#0000FF";
			mCurrentStage = Stage.Option2;
			setText(getString(R.string.stage_option2));
			return true;
			
		case R.id.number1:
		case R.id.number2:
		case R.id.number3:
		case R.id.number4:
		case R.id.number5:
			mNumber = item.getTitle().toString();
			mCurrentStage = Stage.End;

			// Show the number in the color that was picked,
			// in addition to the "tap to finish" (in R.string.stage_end)
			String coloredNumber = "<big><big><font color='" + mColor + "'>" + mNumber + "</font></big></big><br>";
			setText(coloredNumber + getString(R.string.stage_end));
			return true;
		
		default:
			return super.onOptionsItemSelected(item);
		}
		
	}

	protected void setText(String text){
		mText.setText(Html.fromHtml(text));
		mText.setTranslationY(mText.getHeight());
		mText.setAlpha(0f);
		mText.animate().translationY(0).alpha(1f).start();
	}
    
	/**
	 * Play the standard Glass tap sound
	 */
	protected void playClickSound() {
		AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		audio.playSoundEffect(Sounds.TAP);
	}

    	/**
	 * Play the standard Glass tap sound
	 */
	protected void playSuccessSound() {
		AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		audio.playSoundEffect(Sounds.SUCCESS);
	}

}
