package com.geekconx.mayadan.banestein;

import java.io.File;
import java.io.IOException;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.media.audiofx.PresetReverb;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

public class PlayTask extends AsyncTask<File, Void, Void> 
implements MediaPlayer.OnCompletionListener{
	public static boolean soundLoaded;
	private Context context;
	private MediaPlayer mp;
	
	public PlayTask(Context c) {
		this.context = c;
	}
	
	protected Void doInBackground(File... args) {
		File file = args[0];

		try {
			mp = MediaPlayer.create(context, Uri.fromFile(file));
			mp.setOnCompletionListener(this);
			
			PresetReverb mReverb = new PresetReverb(1, 0);
			mReverb.setPreset(PresetReverb.PRESET_PLATE);
			mReverb.setEnabled(true);
			mp.attachAuxEffect(mReverb.getId());
			mp.setAuxEffectSendLevel(1.0f);
			
			mp.start();
		} catch (IllegalStateException e) {
			Log.e("Banestein", e.toString());
			e.printStackTrace();
		}
		
		/*
		soundLoaded = false;
		SoundPool sp = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
	
		sp.setOnLoadCompleteListener(new OnLoadCompleteListener(){
		        @Override
		        public void onLoadComplete(SoundPool soundPool, int sampleId,
		                  int status) {
		        	soundLoaded = true;
		       }
		});
		 
		 //loading file
		int soundId = sp.load(file.getAbsolutePath(), 1);
		
		try
		{
			//waiting for sound to be loaded
			for (int i=1;i<12;i++)
			{
				if (!soundLoaded) {
					Log.d("Banestein", "not loaded; sleeping");
					Thread.sleep(250);
				}
			}
			
			sp.play(soundId, 1, 1, 1, 0, 0.9f);
			Log.d("Banestein", "Played Sound");
			//sp.release();
		}
		catch (InterruptedException e)
		{
		   Thread.currentThread().interrupt();
		}
		*/
		return null;
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		Log.d("Banestein", "media playback complete; releasing resources");
		mp.release();
	}
}
