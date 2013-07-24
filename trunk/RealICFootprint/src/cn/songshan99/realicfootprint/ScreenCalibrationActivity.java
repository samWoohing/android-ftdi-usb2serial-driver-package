package cn.songshan99.realicfootprint;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;


public class ScreenCalibrationActivity extends SherlockActivity {
	
	public static final int UNIT_MM=0;
	public static final int UNIT_INCH=1;
	
	public static final String SCRN_CALIBRATION_INFO = "scrn_calibration";
	public static final String KEY_XDPI = "XDPI";
	public static final String KEY_YDPI = "YDPI";
	
	private ScreenCalibrationLinearLayout mScreenCalibrationLinearLayout;
	private float mXDPI, mYDPI;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock_Light);
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.frag_scrn_calibration);
		
		TextView tv = (TextView) findViewById(R.id.textViewCalibrateInstruction);
		tv.setText(Html.fromHtml(readRawTextFile(R.raw.cali_instruction)));
		tv.setMovementMethod(ScrollingMovementMethod.getInstance());
		
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
		
		//set onclicklistener to button
		Button btn = (Button) findViewById(R.id.buttonApply);
		btn.setOnClickListener(mOKBtnOnClickListener);
		btn = (Button) findViewById(R.id.buttonClose);
		btn.setOnClickListener(mCloseBtnOnClickListener);
		btn = (Button) findViewById(R.id.buttonReset);
		btn.setOnClickListener(mResetBtnOnClickListener);
		
		//record the linearlayout for later use
		mScreenCalibrationLinearLayout = (ScreenCalibrationLinearLayout) findViewById(R.id.linearLayoutScrnCalibration);
		
		//read the existing configuration and display it on screen
		mXDPI = getXDPI(this); mYDPI = getYDPI(this);
		mScreenCalibrationLinearLayout.setDisplayUnit(UNIT_MM, mXDPI, mYDPI);
	}
	
	private OnItemSelectedListener mSpinnerItemSelectedListner = new OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position,
				long id) {
			if(position == UNIT_MM)
				mScreenCalibrationLinearLayout.setDisplayUnit(UNIT_MM, mXDPI, mYDPI);
			else if(position == UNIT_INCH)
				mScreenCalibrationLinearLayout.setDisplayUnit(UNIT_INCH, mXDPI, mYDPI);
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private OnClickListener mOKBtnOnClickListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			processCalibration();
			//Do not exit at here
			//finish();
		}
		
	};
	
	private OnClickListener mCloseBtnOnClickListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			finish();
		}
		
	};
	
	private OnClickListener mResetBtnOnClickListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			//TODO: give the user a confirm dialog box?
			// remove the shared preference
			resetCalibration();
		}
		
	};
	/**
	 * Gets the xDPI. 
	 * If SharedPreference exists, read it and return the value. 
	 * If SharedPreference does not exist, read the DisplayMetrics value. (which may not be accurate)
	 *
	 * @param act the activity from which this function is called
	 * @return the xDPI
	 */
	private static float getXDPI(Activity act){
		SharedPreferences pref = act.getSharedPreferences(SCRN_CALIBRATION_INFO,  MODE_PRIVATE);
		//read the SharePreference
		float xdpi = pref.getFloat(KEY_XDPI, 0.0f);
		
		//if exists,we use that number
		if(xdpi == 0.0f){
			//if the pref does not exist, we use DisplayMetrics numbers
			DisplayMetrics dm = new DisplayMetrics();
			act.getWindowManager().getDefaultDisplay().getMetrics(dm);
			xdpi = dm.xdpi;
		}
		return xdpi;
	}
	
	/**
	 * Gets the yDPI.
	 * If SharedPreference exists, read it and return the value. 
	 * If SharedPreference does not exist, read the DisplayMetrics value. (which may not be accurate)
	 *
	 * @param act the activity from which this function is called
	 * @return the yDPI
	 */
	private static float getYDPI(Activity act){
		SharedPreferences pref = act.getSharedPreferences(SCRN_CALIBRATION_INFO,  MODE_PRIVATE);
		//read the SharePreference
		float ydpi = pref.getFloat(KEY_YDPI, 0.0f);
		
		//if exists,we use that number
		if(ydpi == 0.0f){
			//if the pref does not exist, we use DisplayMetrics numbers
			DisplayMetrics dm = new DisplayMetrics();
			act.getWindowManager().getDefaultDisplay().getMetrics(dm);
			ydpi = dm.ydpi;
		}
		return ydpi;
	}
	
	public static DisplayMetrics getDisplayMetrics(Activity act){
		DisplayMetrics dm = new DisplayMetrics();
		act.getWindowManager().getDefaultDisplay().getMetrics(dm);	
		//see if we have a shared preference to store xdpi and ydpi
		dm.xdpi = getXDPI(act);
		dm.ydpi = getYDPI(act);	
		//TODO: see if we need to change dm.DensityDpi.
		return dm;
	}
	
	private static void storeXDPI_YDPI(Activity act, float xdpi, float ydpi){
		SharedPreferences pref = act.getSharedPreferences(SCRN_CALIBRATION_INFO,  MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		editor.putFloat(KEY_XDPI, xdpi).putFloat(KEY_YDPI, ydpi);
		editor.commit();
	}
	
	private void showAlertDialog(int title_id, int message_id){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(message_id).setTitle(title_id);
		builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	               dialog.dismiss();
	           }
	       });
		builder.create().show();
	}
	
	private void processCalibration(){
		//get actual measured numbers. This is the measured
		TextView tvH = (TextView) findViewById(R.id.editTextHeight);
		TextView tvW = (TextView) findViewById(R.id.editTextWidth);
		
		int unit = mScreenCalibrationLinearLayout.getmDisplayUnit();
		
		float height, width;
		try{//check number validity
			height = Float.parseFloat(tvH.getText().toString());
			width = Float.parseFloat(tvW.getText().toString());
		}catch(NumberFormatException e){
			//if the text is invalid to convert, let's remind the user
			showAlertDialog(R.string.dlg_title, R.string.dlg_msg_recognize);
			return;
		}
		
		if(height <= 0 || width <= 0){
			showAlertDialog(R.string.dlg_title, R.string.dlg_msg_negzero);
			return;
		}
		
		//calculate xdpi and ydpi from converted numbers
		float xdpi = mScreenCalibrationLinearLayout.calculateXDPI(width, unit);
		float ydpi = mScreenCalibrationLinearLayout.calculateYDPI(height, unit);
		
		//check if the xdpi and ydpi is valid
		if(xdpi <= 0 || ydpi <= 0){
			showAlertDialog(R.string.dlg_title, R.string.dlg_msg_result);
			return;
		}
		//store the calculated xdpi and ydpi
		storeXDPI_YDPI(this, xdpi, ydpi);
		//update the linear layout view display
		mScreenCalibrationLinearLayout.setDisplayUnit(unit, xdpi, ydpi);
		mXDPI = xdpi; mYDPI = ydpi;
	}
	
	private void resetCalibration(){
		//remove the shared preference and use DisplayMetrics instead.
		SharedPreferences pref = getSharedPreferences(SCRN_CALIBRATION_INFO,  MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		editor.remove(KEY_XDPI).remove(KEY_YDPI);
		editor.commit();
		
		int unit = mScreenCalibrationLinearLayout.getmDisplayUnit();
		//read the existing configuration and display it on screen
		mXDPI = getXDPI(this); mYDPI = getYDPI(this);
		//update the linear layout view display
		mScreenCalibrationLinearLayout.setDisplayUnit(unit, mXDPI, mYDPI);
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
