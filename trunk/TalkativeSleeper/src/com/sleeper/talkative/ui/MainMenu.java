/*
* Copyright (C) 2012 Radek Kavan
*
* This file is part of TalkativeSleeper.
*
* TalkativeSleeper is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* TalkativeSleeper is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with TalkativeSleeper. If not, see <http://www.gnu.org/licenses/>.
*
*/



/**
 * @author Radek Kavan
 * 
 *
 */
package com.sleeper.talkative.ui;
import com.sleeper.talkative.engine.Engine;
import com.talkative.sleeper.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;


public class MainMenu extends Activity {

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
				clean = Engine.onExit(v);
				if (clean) {
					int pid = android.os.Process.myPid();
					android.os.Process.killProcess(pid);
				}
			}
		});
	}
}
