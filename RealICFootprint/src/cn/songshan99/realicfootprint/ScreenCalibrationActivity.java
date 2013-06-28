package cn.songshan99.realicfootprint;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;


public class ScreenCalibrationActivity extends SherlockActivity {
	
	public static final int UNIT_MM=0;
	public static final int UNIT_INCH=1;
	
	private ScreenCalibrationLinearLayout mScreenCalibrationLinearLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock_Light);
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.frag_scrn_calibration);
		
		TextView tv = (TextView) findViewById(R.id.textViewCalibrateInstruction);
		tv.setText(Html.fromHtml(readRawTextFile(R.raw.cali_instruction)));
		//load the unit spinner
		Spinner spinner = (Spinner) findViewById(R.id.spinnerUnit);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.scrn_dim_unit, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		spinner.setSelection(0);
		
		spinner.setOnItemSelectedListener(mSpinnerItemSelectedListner);
		
		//record the linearlayout for later use
		mScreenCalibrationLinearLayout = (ScreenCalibrationLinearLayout) findViewById(R.id.linearLayoutScrnCalibration);
		
		//TODO: read the existing configuration and display it on screen		
	}
	
	private OnItemSelectedListener mSpinnerItemSelectedListner = new OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position,
				long id) {
			if(position == UNIT_MM)
				mScreenCalibrationLinearLayout.setDisplayUnit(UNIT_MM, 120, 120);
			else if(position == UNIT_INCH)
				mScreenCalibrationLinearLayout.setDisplayUnit(UNIT_INCH, 120, 120);
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private float getXDPI(){
		//TODO: make this a static function??
		//read the SharePreference
		//if exists,we use that number
		//if no, we use DisplayMetrics numbers
		//.getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
		return 0.0f;
	}
	
	private float getYDPI(){
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
