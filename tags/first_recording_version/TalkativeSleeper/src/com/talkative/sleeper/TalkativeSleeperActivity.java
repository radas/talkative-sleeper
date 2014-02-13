package com.talkative.sleeper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class TalkativeSleeperActivity extends Activity {
	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splashscreen);

		/* Start a new thread */
		new Handler().postDelayed(new Thread() {
			@Override
			public void run() {
				Intent mainMenu = new Intent(TalkativeSleeperActivity.this,
						MainMenu.class);
				TalkativeSleeperActivity.this.startActivity(mainMenu);
				TalkativeSleeperActivity.this.finish();
				overridePendingTransition(R.layout.fadein, R.layout.fadeout);

			}
		}, Engine.SPLASH_SCREEN_DELAY);
	}
}