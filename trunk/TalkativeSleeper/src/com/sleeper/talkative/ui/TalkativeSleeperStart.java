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

import java.io.IOException;

import com.sleeper.talkative.db.RecordsListDatabaseHelper;
import com.sleeper.talkative.engine.Engine;
import com.sleeper.talkative.engine.VoiceDetection;
import com.sleeper.talkative.engine.VoiceRecording;
import com.talkative.sleeper.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class TalkativeSleeperStart extends Activity {
	/** running state **/
	private boolean isRunning = true;
	private boolean isRecording = false;
	private int recordsCount = 0;

	/** config state **/
	private int sensitivity;

	/** Handlers and Threads **/
	private Handler handler;
	private Thread thread;

	/** data source **/
	private VoiceDetection voicedetection;
	private VoiceRecording voicerecording;
	private RecordsListDatabaseHelper databaseHelper;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

		setContentView(R.layout.running);
		databaseHelper = new RecordsListDatabaseHelper(this);

		voicedetection = new VoiceDetection(databaseHelper);
		voicerecording = new VoiceRecording(databaseHelper);

		resetDisplay();
		main();
		showStartStopButton();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.main_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {

		switch (item.getItemId()) {
		case R.id.home:
			finishSoundDetection();
			finish();
			break;

		case R.id.preferences:
			Log.d(Engine.TAG, "Show preferences");
			Intent prefs = new Intent(this, Preferences.class);
			startActivity(prefs);
			break;

		case R.id.help:
			Intent helpIntent = new Intent();
			helpIntent.setClass(this, HelpActivity.class);
			startActivity(helpIntent);
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	private void resetDisplay() {
		String status;
		if (isRecording) {
			status = "Recording";
		} else if (isRunning) {
			status = "Running";
		} else {
			status = "Stopped";
		}

		showStatus(status);
		showSleeperPicture();
		showRecordsCount(Integer.toString(recordsCount));

	}

	private void showStartStopButton() {
		final Button startStop = (Button) findViewById(R.id.startStop);

		startStop.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				isRunning = !isRunning;

				if (isRunning) {
					main();
				} else {
					isRecording = false;
					finishSoundDetection();
					resetDisplay();
				}

				String result = (isRunning) ? "Stop" : "Start";
				Log.d(Engine.TAG, "Start/Stop pressed");
				startStop.setText(result);

			}

		});

	}

	private void showStatus(String status) {
		TextView appStatus = (TextView) findViewById(R.id.status);
		appStatus.setText(status);
	}

	private void showSleeperPicture() {
		ImageView sleeperImage = (ImageView) findViewById(R.id.sleeper);
		int picture = (isRecording) ? R.drawable.sleepertalk
				: R.drawable.sleepersleep;
		sleeperImage.setImageResource(picture);
	}

	private void showRecordsCount(String records) {
		TextView recordCount = (TextView) findViewById(R.id.countRecords);
		recordCount.setText("number of actual records: " + records);
	}

	private void soundDetectionStart() {
		voicedetection.start();
	}

	private void soundDetectionStop() {
		voicedetection.stop();
	}

	private final Runnable runDetection = new Runnable() {
		private boolean status;

		@Override
		public void run() {
			if (voicedetection.getmRecorder() == null
					&& voicerecording.getRecorder() == null) {
				voicedetection.start();
			}

			status = voicedetection.isBlowing();
			
			if (thread != null && thread.isAlive()) {
				isRecording = true;
			}
			
			else if (status) {
				isRecording = true;
				recordsCount++;
				voicedetection.stop();

				thread = new Thread() {
					@Override
					public void run() {
						try {
							voicerecording.start();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				};
				thread.start();

			} else {
				isRecording = false;
			}
			resetDisplay();
			handler.postDelayed(this, Engine.DETECTION_REFRESH_MS);
		}
	};

	private void finishSoundDetection() {
		handler.removeCallbacks(runDetection);
		soundDetectionStop();
	}

	private void readApplicationPreferences() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		sensitivity = Integer.parseInt(prefs
				.getString("updates_interval", null));
		Log.d(Engine.TAG, "sensitivity = " + sensitivity);
	}

	@Override
	protected void onResume() {
		readApplicationPreferences();
		super.onResume();
	}

	private void main() {
		soundDetectionStart();
		handler = new Handler();
		handler.post(runDetection);
	}

}