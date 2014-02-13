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
				overridePendingTransition(R.anim.fadein, R.anim.fadeout);

			}
		}, Engine.SPLASH_SCREEN_DELAY);
	}
}