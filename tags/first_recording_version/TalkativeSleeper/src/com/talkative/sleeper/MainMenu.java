/**
 * 
 */
package com.talkative.sleeper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

/**
 * @author radek
 * 
 */

public class MainMenu extends Activity {

	final Engine engine = new Engine();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		/** Set menu button options */
		ImageButton start = (ImageButton) findViewById(R.id.btnStart);
		ImageButton exit = (ImageButton) findViewById(R.id.btnExit);

		start.getBackground().setAlpha(Engine.MENU_BUTTON_ALPHA);
		start.setHapticFeedbackEnabled(Engine.HAPTIC_BUTTON_FEEDBACK);

		exit.getBackground().setAlpha(Engine.MENU_BUTTON_ALPHA);
		exit.setHapticFeedbackEnabled(Engine.HAPTIC_BUTTON_FEEDBACK);

		start.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				/** Start App */
				Log.d(Engine.TAG, "Start pressed");

				Intent startApp = new Intent(getApplicationContext(),
						TalkativeSleeperStart.class);
				MainMenu.this.startActivity(startApp);

			}

		});

		exit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				/** Exit App */
				Log.d(Engine.TAG, "Exit pressed");
				boolean clean = false;
				clean = engine.onExit(v);
				if (clean) {
					int pid = android.os.Process.myPid();
					android.os.Process.killProcess(pid);
				}
			}
		});
	}
}
