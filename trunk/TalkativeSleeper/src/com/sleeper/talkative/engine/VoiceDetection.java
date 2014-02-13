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

import java.io.IOException;

import com.sleeper.talkative.db.RecordsListDatabaseHelper;


import android.media.MediaRecorder;
import android.util.Log;

public class VoiceDetection {
	private MediaRecorder mRecorder = null;
	private RecordsListDatabaseHelper databaseHelper;

	public VoiceDetection(RecordsListDatabaseHelper databaseHelper) {
		this.databaseHelper = databaseHelper;
	}

	public void start() {
		if (mRecorder == null) {
			mRecorder = new MediaRecorder();
			mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
			mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

			mRecorder.setAudioChannels(1);
			mRecorder.setAudioSamplingRate(8000);

			mRecorder.setOutputFile("/dev/null");

			try {
				mRecorder.prepare();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			mRecorder.start();
		}
	}

	public MediaRecorder getmRecorder() {
		return mRecorder;
	}

	public void stop() {
		if (mRecorder != null) {
			mRecorder.stop();
			mRecorder.reset();
			mRecorder.release();
			mRecorder = null;
		}
	}

	public boolean isBlowing() {
		if (mRecorder != null) {
			double amplitude = (mRecorder.getMaxAmplitude() / 2700.0);
			Log.d(Engine.TAG, "BlowValue=" + amplitude);
			if (amplitude > Engine.AMPLITUDE_THRESHOLD) {
				return true;
			}
		}
		return false;
	}
}
