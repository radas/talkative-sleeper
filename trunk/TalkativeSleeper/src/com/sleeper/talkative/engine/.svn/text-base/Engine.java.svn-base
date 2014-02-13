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


package com.sleeper.talkative.engine;

import android.view.View;

public class Engine {
	/* constants */
	public static final int SPLASH_SCREEN_DELAY = 4000;
	public static final int MENU_BUTTON_ALPHA = 0;
	public static final boolean HAPTIC_BUTTON_FEEDBACK = true;
	public static final String TAG = "TalkativeSleeperLogging";
	public static final double AMPLITUDE_THRESHOLD = 0.5;
	public static final int DETECTION_REFRESH_MS = 300;
	public static final String AUDIO_FILE_PATH = "/talkativesleeper/voicerecord_";
	public static final int RECORD_LENGTH_MS = 10000;
	/* SQL constants */
	public static final String DBNAME = "sleeper.db";
	public static final String TABLE_NAME = "sleeper";

  

	/* Kill app and exit */
	public static boolean onExit(@SuppressWarnings("unused") View v) {
		try {
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
