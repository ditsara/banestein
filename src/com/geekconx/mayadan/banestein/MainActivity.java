package com.geekconx.mayadan.banestein;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Bundle;
import android.os.SystemClock;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

public class MainActivity extends Activity {
	private static final int SPEECH_RECOG_REQ_CODE = 1;
	private TextView txtResults;
	private static boolean soundLoaded = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		txtResults = (TextView)findViewById(R.id.txt_output);
		MainBus.getInstance().register(this);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		Log.d("Banestein", "onStart()");
	}
	@Override
	protected void onStop() {
		super.onStop();
		Log.d("Banestein", "onStop()");
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		MainBus.getInstance().unregister(this);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// 1. Starts callback chain by creating an Intent to recognize speech
	public void recognize(View view) {
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

		// specify calling package to identify this app
		intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
				getClass().getPackage().getName());

		// show user a hint about what to say
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT, R.string.recog_hint);

		// give recognizer a hint about what the user is going to say
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		startActivityForResult(intent, SPEECH_RECOG_REQ_CODE);
	}

	// 2. Callback for speech recognition activity; starts Translate task.
	// Also populates txtResult with first (highest-confidence) result
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent data) {
		if (requestCode == SPEECH_RECOG_REQ_CODE) {
			if (resultCode == RESULT_OK) {
				ArrayList<String> results = data.getStringArrayListExtra(
						RecognizerIntent.EXTRA_RESULTS);
				String resultStr = results.get(0).toString();
				Log.d("Banestein", resultStr);
				txtResults.setText(resultStr);
				new TranslateTask().execute(resultStr);
			}
		}
		// display message for the various voice recognition errors
		else if(resultCode == RecognizerIntent.RESULT_AUDIO_ERROR){
			showToastMessage("Audio Error");
		}else if(resultCode == RecognizerIntent.RESULT_CLIENT_ERROR){
			showToastMessage("Client Error");
		}else if(resultCode == RecognizerIntent.RESULT_NETWORK_ERROR){
			showToastMessage("Network Error");
		}else if(resultCode == RecognizerIntent.RESULT_NO_MATCH){
			showToastMessage("No Match");
		}else if(resultCode == RecognizerIntent.RESULT_SERVER_ERROR){
			showToastMessage("Server Error");
		}
	}
	
	// helper method to display short toasts (for errors)
	private void showToastMessage(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
	
	// 3. Translated text callback; starts GetAudio task
	@Subscribe
	public void onTranslate(TranslateEvent translated) {
		Log.d("Banestein", translated.getResult());
		txtResults.append("\n" + translated.getResult());
		new GetAudioTask(this).execute(translated.getResult());
	}

	// 4. GetAudio callback and start PlayTask
	@Subscribe
	public void onGetAudio(GetAudioEvent audio) {
		Log.d("Banestein", audio.getResult().toString());
		//playRecord(audio.getResult());
		//playMediaFile(audio.getResult());
		new PlayTask(this).execute(audio.getResult());
	}
	
	// Intercepts headset button key-up and triggers recognize()
	// (same method as button)
	@Override
	public boolean dispatchKeyEvent(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.KEYCODE_HEADSETHOOK &&
				e.getAction() == KeyEvent.ACTION_UP) {
			Log.d("Banestein", "dispatchKey");
			recognize(txtResults);
			return true;
		}
		return super.dispatchKeyEvent(e);
	}
	
}