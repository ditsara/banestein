package com.geekconx.mayadan.banestein;

import java.io.File;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.AsyncTask;
import android.util.Log;

public class PlayTask extends AsyncTask<File, Void, Void> {
	public static boolean soundLoaded;
	private Context context;
	
	public PlayTask(Context c) {
		this.context = c;
	}
	
	protected Void doInBackground(File... args) {
		File file = args[0];
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
		return null;
	}
}
