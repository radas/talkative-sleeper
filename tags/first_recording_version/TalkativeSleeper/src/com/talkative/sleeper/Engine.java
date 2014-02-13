/**
 * @author radek
 * 
 *
 */

package com.talkative.sleeper;

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
  

	/* Kill app and exit */
	public boolean onExit(View v) {
		try {
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
