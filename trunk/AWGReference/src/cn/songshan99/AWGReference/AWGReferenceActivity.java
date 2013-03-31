package cn.songshan99.AWGReference;

import java.io.IOException;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;

import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import android.widget.TextView;

public class AWGReferenceActivity extends SherlockActivity {
	
	public static final int UNIT_METRIC=1;
	private static final int UNIT_IMPERIAL=2;
	private int mCurrentUnit;
	
	private MenuItem mSubMenuItem;
	
	private AWGWire mAWGWire;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	setTheme(R.style.Theme_Sherlock_Light);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Spinner spn = (Spinner)findViewById(R.id.spinnerAWG);
        
        try {
			mAWGWire = new AWGWire(this);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        String[] awgSizes = mAWGWire.queryAWGSizes();
        SpinnerAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, awgSizes);
        spn.setAdapter(adapter);
        spn.setOnItemSelectedListener(mAWGSizeSelectedListener);
        spn.setSelection(0);
        
        mCurrentUnit=UNIT_METRIC;
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		SubMenu subMenu1 = menu.addSubMenu("Unit: Metric");
        subMenu1.add(1,UNIT_METRIC,UNIT_METRIC,"Metric").setChecked(true);
        subMenu1.add(1,UNIT_IMPERIAL,UNIT_IMPERIAL,"Imperial");
        subMenu1.setGroupCheckable(1, true, true);
        
        
        mSubMenuItem = subMenu1.getItem();
        mSubMenuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		//Toast.makeText(super.getApplicationContext(), "item "+item.toString()+" selected", Toast.LENGTH_SHORT).show();
		switch(item.getItemId()){
		case 1:
			//Toast.makeText(super.getApplicationContext(), "item "+item.toString()+" selected", Toast.LENGTH_SHORT).show();
			mSubMenuItem.setTitle("Unit: Metric");
			item.setChecked(true);
			toggleUnit(UNIT_METRIC);
			break;
			
		case 2:
			//Toast.makeText(super.getApplicationContext(), "item "+item.toString()+" selected", Toast.LENGTH_SHORT).show();
			mSubMenuItem.setTitle("Unit: Imperial");
			item.setChecked(true);
			toggleUnit(UNIT_IMPERIAL);
			break;
			
		default:
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	private void toggleUnit(int UNIT){
		mCurrentUnit = UNIT;
		//update text boxes
		if(mCurrentUnit == UNIT_IMPERIAL){
			TextView tv = (TextView)this.findViewById(R.id.textViewDiameter);
			tv.setText(R.string.diameter_inch);
			tv = (TextView)this.findViewById(R.id.textViewArea);
			tv.setText(R.string.area_kcmil);
			tv = (TextView)this.findViewById(R.id.textViewResistance);
			tv.setText(R.string.res_mOhm_per_ft);
			
			updateMetricImperialDisplayNum();
			//TODO: convert displayed numbers
		}
		else if(mCurrentUnit == UNIT_METRIC){
			TextView tv = (TextView)this.findViewById(R.id.textViewDiameter);
			tv.setText(R.string.diameter_mm);
			tv = (TextView)this.findViewById(R.id.textViewArea);
			tv.setText(R.string.area_mm2);
			tv = (TextView)this.findViewById(R.id.textViewResistance);
			tv.setText(R.string.res_mOhm_per_m);
			
			updateMetricImperialDisplayNum();
			//TODO: convert displayed numbers
			//test only:
			//AWGDisplayView v = (AWGDisplayView)this.findViewById(R.id.awgDisplayView);
			//v.setAWGWire(0);
		}	
	}
	
	private void updateMetricImperialDisplayNum(){
		TextView tv;
		if(mCurrentUnit == UNIT_IMPERIAL){
			tv = (TextView)findViewById(R.id.textViewDiameterNum);
    		tv.setText(Float.toString(mAWGWire.getDiameter_in()));
    		tv = (TextView)findViewById(R.id.textViewAreaNum);
    		tv.setText(Float.toString(mAWGWire.getArea_kcmil()));
    		tv = (TextView)findViewById(R.id.textViewResistanceNum);
    		tv.setText(Float.toString(mAWGWire.getResistance_mOhm_per_ft()));
		}
		else if(mCurrentUnit == UNIT_METRIC){
			tv = (TextView)findViewById(R.id.textViewDiameterNum);
    		tv.setText(Float.toString(mAWGWire.getDiameter_mm()));
    		tv = (TextView)findViewById(R.id.textViewAreaNum);
    		tv.setText(Float.toString(mAWGWire.getArea_mm2()));
    		tv = (TextView)findViewById(R.id.textViewResistanceNum);
    		tv.setText(Float.toString(mAWGWire.getResistance_mOhm_per_m()));
		}	
	}
	
    private OnItemSelectedListener mAWGSizeSelectedListener = new OnItemSelectedListener(){
    	
    	public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
    		//lookup the AWG information
    		mAWGWire.setAWGSize((String)parentView.getSelectedItem());
    		//update the drawing
    		updateMetricImperialDisplayNum();
    		
    		TextView tv;
    		tv = (TextView)findViewById(R.id.textViewFuseCurrPreece10sNum);
    		tv.setText(Float.toString(mAWGWire.getFusingCurrentPreece10s()));
    		tv = (TextView)findViewById(R.id.textViewFuseCurrOnderdonk1sNum);
    		tv.setText(Float.toString(mAWGWire.getFusingCurrentOnderdonk1s()));
    		tv = (TextView)findViewById(R.id.textViewFuseCurrOnderdonk30msNum);
    		tv.setText(Float.toString(mAWGWire.getFusingCurrentOnderdonk30ms()));
    		
    		AWGDisplayView v = (AWGDisplayView)findViewById(R.id.awgDisplayView);
			v.setAWGWire(mAWGWire.getDiameter_mm());
    	}
    	
    	public void onNothingSelected(AdapterView<?> parentView){         
    		//this should not happen
    	}
	};
}