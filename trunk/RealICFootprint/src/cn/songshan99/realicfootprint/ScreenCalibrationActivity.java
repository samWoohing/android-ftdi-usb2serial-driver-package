package cn.songshan99.realicfootprint;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;


public class ScreenCalibrationActivity extends SherlockActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock_Light);
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.frag_scrn_calibration);
		
		TextView tv = (TextView) findViewById(R.id.textViewCalibrateInstruction);
		tv.setText(Html.fromHtml(readRawTextFile(R.raw.cali_instruction)));
		//TODO: load the unit spinner
		//TODO: prevent rotating the screen
		//TODO: read the existing configuration and display it on screen
		
	}
	
	private float getXDPI(){
		//read the SharePreference
		//if exists,we use that number
		//if no, we use DisplayMetrics numbers
		//.getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
		return 0.0f;
	}
	
	private float getYDPI(){
		return 0.0f;
	}
	
	private float calcWidth(int unit){
		return 0.0f;
	}
	
	private float calcHeight(int unit){
		return 0.0f;
	}
	
	private String readRawTextFile(int id) {
		InputStream inputStream = getResources().openRawResource(id);
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
