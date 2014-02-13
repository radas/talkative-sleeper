/**
 * @author radek
 *
 */

package com.talkative.sleeper;

import java.io.IOException;

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
	/** variables **/
	boolean isRunning = true;
	boolean isRecording = false;
	int recordsCount = 0;

	/** config state **/
	private int sensitivity;

	/** Handlers **/
	private Handler handler;

	/** data source **/
	private VoiceDetection voicedetection;
	private VoiceRecording voicerecording;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

		setContentView(R.layout.running);

		voicedetection = new VoiceDetection();
		voicerecording = new VoiceRecording();

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
		case R.id.menu:
			finishSoundDetection();
			finish();
			break;

		case R.id.settings:
			Log.d(Engine.TAG, "Show settings");
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
		
		Log.d(Engine.TAG, "Show state" + status);
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
		recordCount.setText(records);
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

			if (status) {
				isRecording = true;
				resetDisplay();
				voicedetection.stop();

				try {
					voicerecording.start();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} else {
				isRecording = false;
				resetDisplay();
			}
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

		Thread th = new Thread() {

			@Override
			public void run() {
				handler.post(runDetection);
			}
		};
		th.start();
	}

}