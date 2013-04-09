package cn.songshan99.AWGReference;

import java.io.IOException;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockDialogFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.actionbarsherlock.view.Window;

import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v4.app.*;
import android.support.v4.widget.*;
import android.support.v4.view.*;
import android.support.v4.widget.*;
import android.support.v4.app.*;
import android.support.v4.content.*;
import android.support.v4.os.*;
import android.support.v4.database.*;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TableLayout;
import android.widget.Toast;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;

public class AWGReferenceActivity extends SherlockFragmentActivity { 
	
	public static final int UNIT_METRIC=1;
	private static final int UNIT_IMPERIAL=2;
	private static final int ABOUT = 3;
	private int mCurrentUnit;
	
	private MenuItem mSubMenuItem;
	
	private AWGWire mAWGWire;
	
	private ShowAWGRealSizeDialogFragment mShowAWGRealSizeDialogFragment;
	
	private String AWG_REAL_SIZE_DIALOG_TAG = "AWGRealSzDlg";
	
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
        
        //initialize mShowAWGRealSizePopupWindow
        
        //LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        //mShowAWGRealSizePopupWindow = new PopupWindow(inflater.inflate(R.layout.test, null, false),200,200,true);
        //mShowAWGRealSizePopupWindow.setWindowLayoutMode(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.MATCH_PARENT);
        //mShowAWGRealSizePopupWindow.showAtLocation(findViewById(R.id.main), Gravity.CENTER, 0, 0);
        mShowAWGRealSizeDialogFragment = new ShowAWGRealSizeDialogFragment();
        
        //TODO: set an onclick listern for all number boxes to copy number to clipboard.
        Button btn = (Button)findViewById(R.id.buttonShowActualSize);
        btn.setOnClickListener(mShowWirelButtonClickListener);
        
        
        findViewById(R.id.textViewDiameterNum).setOnClickListener(mNumberTextViewClickListener);
        findViewById(R.id.textViewAreaNum).setOnClickListener(mNumberTextViewClickListener);
        findViewById(R.id.textViewResistanceNum).setOnClickListener(mNumberTextViewClickListener);
        findViewById(R.id.textViewNumStrandNum).setOnClickListener(mNumberTextViewClickListener);
        findViewById(R.id.textViewStrandDiaNum).setOnClickListener(mNumberTextViewClickListener);
        findViewById(R.id.textViewFuseCurrPreece10sNum).setOnClickListener(mNumberTextViewClickListener);
        findViewById(R.id.textViewFuseCurrOnderdonk1sNum).setOnClickListener(mNumberTextViewClickListener);
        findViewById(R.id.textViewFuseCurrOnderdonk30msNum).setOnClickListener(mNumberTextViewClickListener);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		SubMenu subMenu1 = menu.addSubMenu("Unit: Metric");
        subMenu1.add(1,UNIT_METRIC,UNIT_METRIC,"Metric").setChecked(true);
        subMenu1.add(1,UNIT_IMPERIAL,UNIT_IMPERIAL,"Imperial");
        subMenu1.setGroupCheckable(1, true, true);
        
        
        mSubMenuItem = subMenu1.getItem();
        mSubMenuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        
        MenuItem item = menu.add(2, ABOUT, ABOUT, "About");
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		//Toast.makeText(super.getApplicationContext(), "item "+item.toString()+" selected", Toast.LENGTH_SHORT).show();
		switch(item.getItemId()){
		case UNIT_METRIC:
			//Toast.makeText(super.getApplicationContext(), "item "+item.toString()+" selected", Toast.LENGTH_SHORT).show();
			mSubMenuItem.setTitle("Unit: Metric");
			item.setChecked(true);
			toggleUnit(UNIT_METRIC);
			//mShowAWGRealSizePopupWindow.showAsDropDown(findViewById(R.id.main).getRootView());
			
			//mShowAWGRealSizeDialogFragment.show(getSupportFragmentManager(), "test");
			//mShowAWGRealSizeDialogFragment.showDialog();
			break;
			
		case UNIT_IMPERIAL:
			//Toast.makeText(super.getApplicationContext(), "item "+item.toString()+" selected", Toast.LENGTH_SHORT).show();
			mSubMenuItem.setTitle("Unit: Imperial");
			item.setChecked(true);
			toggleUnit(UNIT_IMPERIAL);
			break;
		case ABOUT:
				AboutDialog dlg = new AboutDialog(this);
				dlg.show();
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
			tv.setText(R.string.res_mohm_per_ft);
			tv = (TextView)this.findViewById(R.id.textViewStrandDia);
			tv.setText(R.string.strand_dia_in);
			updateMetricImperialDisplayNum();
			//TODO: convert displayed numbers
		}
		else if(mCurrentUnit == UNIT_METRIC){
			TextView tv = (TextView)this.findViewById(R.id.textViewDiameter);
			tv.setText(R.string.diameter_mm);
			tv = (TextView)this.findViewById(R.id.textViewArea);
			tv.setText(R.string.area_mm2);
			tv = (TextView)this.findViewById(R.id.textViewResistance);
			tv.setText(R.string.res_mohm_per_m);
			tv = (TextView)this.findViewById(R.id.textViewStrandDia);
			tv.setText(R.string.strand_dia_mm);
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
    		tv = (TextView)findViewById(R.id.textViewStrandDiaNum);
    		tv.setText(Float.toString(mAWGWire.getStrand_dia_in()));
    		
		}
		else if(mCurrentUnit == UNIT_METRIC){
			tv = (TextView)findViewById(R.id.textViewDiameterNum);
    		tv.setText(Float.toString(mAWGWire.getDiameter_mm()));
    		tv = (TextView)findViewById(R.id.textViewAreaNum);
    		tv.setText(Float.toString(mAWGWire.getArea_mm2()));
    		tv = (TextView)findViewById(R.id.textViewResistanceNum);
    		tv.setText(Float.toString(mAWGWire.getResistance_mOhm_per_m()));
    		tv = (TextView)findViewById(R.id.textViewStrandDiaNum);
    		tv.setText(Float.toString(mAWGWire.getStrand_dia_mm()));
		}	
	}
	
    private OnItemSelectedListener mAWGSizeSelectedListener = new OnItemSelectedListener(){
    	
    	public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
    		//lookup the AWG information
    		mAWGWire.setAWGSize((String)parentView.getSelectedItem());
    		//update the drawing
    		updateMetricImperialDisplayNum();
    		
    		TextView tv;
    		tv = (TextView)findViewById(R.id.textViewNumStrandNum);
    		tv.setText(Float.toString(mAWGWire.getStrand_num()));
    		tv = (TextView)findViewById(R.id.textViewFuseCurrPreece10sNum);
    		tv.setText(Float.toString(mAWGWire.getFusingCurrentPreece10s()));
    		tv = (TextView)findViewById(R.id.textViewFuseCurrOnderdonk1sNum);
    		tv.setText(Float.toString(mAWGWire.getFusingCurrentOnderdonk1s()));
    		tv = (TextView)findViewById(R.id.textViewFuseCurrOnderdonk30msNum);
    		tv.setText(Float.toString(mAWGWire.getFusingCurrentOnderdonk30ms()));
    		
    		//AWGDisplayView v = (AWGDisplayView)findViewById(R.id.awgDisplayView);
			//v.setAWGWire(mAWGWire);
    	}
    	
    	public void onNothingSelected(AdapterView<?> parentView){         
    		//this should not happen
    		
    	}
	};
	
	/** The click listener show wire button */
	private OnClickListener mShowWirelButtonClickListener = new OnClickListener(){
		public void onClick(View v){
			mShowAWGRealSizeDialogFragment.showDialog();
		}
	};
	
	/** The click listener to copy text view number to clipper board */
	private OnClickListener mNumberTextViewClickListener = new OnClickListener(){
		public void onClick(View v){
			//ClipboardManager clipboard = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
			//ClipData clip = ClipData.newPlainText("AWG data",((TextView)v).getText());
			//clipboard.setPrimaryClip(clip);
			int sdk = android.os.Build.VERSION.SDK_INT;
		    if(sdk < android.os.Build.VERSION_CODES. HONEYCOMB) {
		        android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
		        clipboard.setText(((TextView)v).getText());
		    } else {
		        android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE); 
		        android.content.ClipData clip = ClipData.newPlainText("AWG data",((TextView)v).getText());
		        clipboard.setPrimaryClip(clip);
		    }
		    
			Toast.makeText(v.getContext(), "copied \""+((TextView)v).getText()+"\" to clipboard", Toast.LENGTH_SHORT).show();
		}
	};
	
	@Override
	public void onBackPressed(){
		// show the main activity layout, so they won't be clicked!
		if(mShowAWGRealSizeDialogFragment.checkIsVisible()){
			mShowAWGRealSizeDialogFragment.hideDialog();
		}
		else{
			this.finish();
		}
		
		//super.onBackPressed();
	}
	
	private class ShowAWGRealSizeDialogFragment extends SherlockDialogFragment{

		@Override
		public void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			
			Dialog dialog = super.onCreateDialog(savedInstanceState);
	        dialog.requestWindowFeature((int) Window.FEATURE_NO_TITLE);
	        return dialog;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			
			View v = inflater.inflate(R.layout.test, container, false);
			
			// TODO set the onclick listner here!
			Button btn = (Button)v.findViewById(R.id.buttonClose);
			btn.setOnClickListener(mCancelButtonClickListener);
			
			AWGDisplayView view = (AWGDisplayView) v.findViewById(R.id.awgDisplayViewInDlg);
			view.setAWGWire(mAWGWire);
			
			TextView textview = (TextView)v.findViewById(R.id.textViewAWGInfo);
			textview.setText("AWG# "+mAWGWire.getAWGSize());
			return v;    
		}
		
		public void showDialog() {
			FragmentManager fragmentManager = getSupportFragmentManager();
			
			FragmentTransaction transaction = fragmentManager.beginTransaction();

			Fragment prev = fragmentManager.findFragmentByTag(
					AWG_REAL_SIZE_DIALOG_TAG);
			ShowAWGRealSizeDialogFragment dialogFragment;
			
			// if a previous frag already exists, just show it!
			if (prev != null) {
				transaction.show(prev);
				transaction.commit();
				dialogFragment = (ShowAWGRealSizeDialogFragment)prev;
				AWGDisplayView view = (AWGDisplayView) dialogFragment.getView().findViewById(R.id.awgDisplayViewInDlg);
				view.setAWGWire(mAWGWire);
				TextView textview = (TextView)dialogFragment.getView().findViewById(R.id.textViewAWGInfo);
				textview.setText("AWG# "+mAWGWire.getAWGSize());				
			} else {// if prev does not exist, create a new one
				dialogFragment = new ShowAWGRealSizeDialogFragment();
				//below doesn't work
				//newFragment.show(transaction, AWG_REAL_SIZE_DIALOG_TAG);return;
				// For a little polish, specify a transition animation
				transaction
						.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
				// To make it fullscreen, use the 'content' root view as the
				// container
				// for the fragment, which is always the root view for the
				// activity
				transaction.add(android.R.id.content, dialogFragment,AWG_REAL_SIZE_DIALOG_TAG)
				.addToBackStack(null).commit();
			}
			//Set up to refresh according to the given AWGWire
			//AWGDisplayView v = (AWGDisplayView) dialogFragment.getView().findViewById(R.id.awgDisplayViewInDlg);
			//v.setAWGWire(10);
			
			// hide the main activity layout, so they won't be clicked!
			TableLayout tl = (TableLayout) findViewById(R.id.main);
			tl.setVisibility(View.GONE);
		}
		
		public void hideDialog(){
			FragmentManager fragmentManager = getSupportFragmentManager();
			
			FragmentTransaction transaction = fragmentManager.beginTransaction();

			Fragment prev = fragmentManager.findFragmentByTag(
					AWG_REAL_SIZE_DIALOG_TAG);

			// if a previous frag already exists, just hide it!
			if (prev != null && prev.isVisible()) {
				transaction.hide(prev);
//				transaction
//				.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
//				transaction.remove(prev);
				transaction.commit();
				TableLayout tl = (TableLayout) findViewById(R.id.main);
			}
		}
		
		public boolean checkIsVisible(){
			FragmentManager fragmentManager = getSupportFragmentManager();
			
			FragmentTransaction transaction = fragmentManager.beginTransaction();

			Fragment prev = fragmentManager.findFragmentByTag(
					AWG_REAL_SIZE_DIALOG_TAG);

			// if a previous frag already exists, just hide it!
			if (prev != null && prev.isVisible()) return true;
			return false;
		}
		@Override
		public void onCancel(DialogInterface dialog) {
			TableLayout tl = (TableLayout) findViewById(R.id.main);
			tl.setVisibility(View.VISIBLE);
		}

		@Override
		public void onDismiss(DialogInterface dialog) { 
			// show the main activity layout, so they won't be clicked!
			TableLayout tl = (TableLayout) findViewById(R.id.main);
			tl.setVisibility(View.VISIBLE);
		}

		@Override
		public void onHiddenChanged(boolean hidden) {
			if(hidden){
			// show the main activity layout, so they won't be clicked!
			TableLayout tl = (TableLayout) findViewById(R.id.main);
			tl.setVisibility(View.VISIBLE);
			}
		}
		
		private OnClickListener mCancelButtonClickListener = new OnClickListener(){
			public void onClick(View v){
				hideDialog();//NOTE: cannot use dismiss here because the fragment still exist after dismissing
				//TableLayout tl = (TableLayout) findViewById(R.id.main);
				//tl.setVisibility(View.VISIBLE);
				//dismiss();
			}
		};
	}
}