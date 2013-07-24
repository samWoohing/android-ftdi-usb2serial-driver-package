package cn.songshan99.realicfootprint;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.util.Linkify;
import android.widget.TextView;

public class AboutDialog extends Dialog {
	private static Context mContext = null;

	public AboutDialog(Context context) {
		super(context);
		mContext = context;
	}

	/**
	 * Standard Android on create method that gets called when the activity
	 * initialized.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.about);

		TextView tv = (TextView) findViewById(R.id.info_text);
		tv.setText(Html.fromHtml(readRawTextFile(R.raw.info)));
		tv.setLinkTextColor(Color.BLUE);
		Linkify.addLinks(tv, Linkify.ALL);
		
		tv = (TextView) findViewById(R.id.instruction_txt);
		tv.setText(Html.fromHtml(readRawTextFile(R.raw.instruction))); 
		
		tv = (TextView) findViewById(R.id.version_history_txt);
		tv.setText(Html.fromHtml(readRawTextFile(R.raw.version_history)));
		
		tv = (TextView) findViewById(R.id.disclaimer_txt);
		tv.setText(Html.fromHtml(readRawTextFile(R.raw.disclaimer)));
		this.setTitle("About this software:");
	}

	public static String readRawTextFile(int id) {
		InputStream inputStream = mContext.getResources().openRawResource(id);
		InputStreamReader in = new InputStreamReader(inputStream);
		BufferedReader buf = new BufferedReader(in);
		String line;
		StringBuilder text = new StringBuilder();
		try {
			while ((line = buf.readLine()) != null)
				text.append(line);
		} catch (IOException e) {
			return null;
		}
		return text.toString();
	}
}