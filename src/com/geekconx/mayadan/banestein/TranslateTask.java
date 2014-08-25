package com.geekconx.mayadan.banestein;

import java.io.IOException;
import java.net.URLEncoder;

import us.monoid.json.JSONException;
import us.monoid.json.JSONObject;
import us.monoid.web.Resty;
import static us.monoid.web.Resty.*;
import android.os.AsyncTask;

public class TranslateTask extends AsyncTask<String, Void, String> {
	private static final String transUrl = "https://translate.yandex.net/api/v1.5/tr.json/translate";
	private static final String key = "trnsl.1.1.20140824T225426Z.96c74a6bb1ded30a.971772c586748b28435f70cfaa6cb938071f8b30";
	private static final String lang = "en-he";
	private static final String errStr = "translate error";

	@Override
	protected String doInBackground(String... en) {
		Resty r = new Resty();
		String text = URLEncoder.encode(en[0]);
		String queryUrl = transUrl + "?key=" + key + "&lang=" + lang 
				+ "&text=" + text;
		try {
			JSONObject translated = r.json(queryUrl).object();
			return translated.getJSONArray("text").getString(0);
		} catch (IOException e) {
			e.printStackTrace();
			return errStr;
		} catch (JSONException e) {
			e.printStackTrace();
			return errStr;
		}
	}

	@Override protected void onPostExecute(String result) {
		MainBus.getInstance().post(new TranslateResultEvent(result));
	}

}
