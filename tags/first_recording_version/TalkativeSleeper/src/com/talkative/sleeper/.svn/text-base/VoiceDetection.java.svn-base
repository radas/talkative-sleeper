package com.talkative.sleeper;

import java.io.IOException;

import android.media.MediaRecorder;
import android.util.Log;

public class VoiceDetection {
	private MediaRecorder mRecorder = null;

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
