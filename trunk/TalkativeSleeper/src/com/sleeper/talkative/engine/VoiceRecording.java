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

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.sleeper.talkative.db.RecordsListDatabaseHelper;


import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

public class VoiceRecording {
	private MediaRecorder recorder = null;
	private RecordsListDatabaseHelper databaseHelper;

	public VoiceRecording(RecordsListDatabaseHelper databaseHelper) {
		this.databaseHelper = databaseHelper;
	}

	@SuppressWarnings("static-method")
	private String getFilePath() {
		SimpleDateFormat timeStampFormat = new SimpleDateFormat(
				"yyyy-MM-dd-HH.mm.ss");

		String fileName = Engine.AUDIO_FILE_PATH + timeStampFormat.format(new Date())
				+ ".3gp";

		if (!fileName.startsWith("/")) {
			fileName = "/" + fileName;
		}
		if (!fileName.contains(".")) {
			fileName += ".3gp";
		}
		return Environment.getExternalStorageDirectory().getAbsolutePath()
				+ fileName;
	}

	/**
	 * Starts a new recording.
	 */
	public void start() throws IOException {
		if (recorder == null) {
			recorder = new MediaRecorder();
		}
			
		String state = android.os.Environment.getExternalStorageState();
		if (!state.equals(android.os.Environment.MEDIA_MOUNTED)) {
			throw new IOException("SD Card is not mounted.  It is " + state
					+ ".");
		}

		String audioFileName = getFilePath();
		// make sure the directory we plan to store the recording in exists
		File directory = new File(audioFileName).getParentFile();
		if (!directory.exists() && !directory.mkdirs()) {
			throw new IOException("Path to file could not be created.");
		}

		Log.d(Engine.TAG, "Start recording" + audioFileName);
		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		
		Log.d(Engine.TAG, "DB logging");
		databaseHelper.saveRecords(audioFileName, audioFileName, Integer.toString(Engine.RECORD_LENGTH_MS));
		
		recorder.setOutputFile(audioFileName);
		recorder.prepare();
		recorder.start();
		
		try {
			Thread.sleep(Engine.RECORD_LENGTH_MS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		stop();
	}

	/**
	 * Stops a recording that has been previously started.
	 */
	public void stop() {
		Log.d(Engine.TAG, "Finish recording");
		recorder.stop();
		recorder.reset();
		recorder.release();
		recorder = null;
	}

	public MediaRecorder getRecorder() {
		return recorder;
	}

	public void setRecorder(MediaRecorder recorder) {
		this.recorder = recorder;
	}

}
