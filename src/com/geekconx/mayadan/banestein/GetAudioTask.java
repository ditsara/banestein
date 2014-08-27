package com.geekconx.mayadan.banestein;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

import us.monoid.json.JSONException;
import us.monoid.json.JSONObject;
import us.monoid.web.Resty;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class GetAudioTask extends AsyncTask<String, Void, File> {
	private static String phUrl = "http://www.almagu5.com/webreader";
	private static String mp3Url = "http://www.almagu5.com/webreader/audio";
	private static String id = "CID";
	private static String callback = "?";
	private static String speed = "s";
	private static String voice = "Gilad";
	//private static String tempFilePrefix = "audio_";
	private Context context;
	
	public GetAudioTask(Context c) {
		this.context = c;
	}

	@Override
	protected File doInBackground(String... args) {
		Resty r = new Resty();
		String text = URLEncoder.encode(args[0]);
		String query1Url = phUrl + "?callback=" + callback 
				+ "&CID=" + id 
				+ "&markup=" + text;
		try {
			// File audio = File.createTempFile(tempFilePrefix, null);
			File audio = new File(context.getCacheDir(), "audio.mp3");
			audio.setReadable(true, false);
			String q1result = r.text(query1Url).toString();
			String fixJson = q1result.substring(2, q1result.length() - 2);
			Log.d("Banestein", fixJson);
			String phraseHash = new JSONObject(fixJson).getString("PhraseHash");
			String query2Url = mp3Url + "/"
					+ id
					+ "_" + phraseHash
					+ "_" + speed
					+ "_" + voice
					+ ".mp3";
			return r.bytes(query2Url).save(audio);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} 
		catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override protected void onPostExecute(File result) {
		MainBus.getInstance().post(new GetAudioEvent(result));
	}


}
