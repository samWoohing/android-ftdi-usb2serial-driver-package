package cn.songshan99.AWGReference;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;

import android.os.Bundle;
import android.widget.Toast;
import android.widget.TextView;

public class AWGReferenceActivity extends SherlockActivity {
	
	public static final int UNIT_METRIC=1;
	private static final int UNIT_IMPERIAL=2;
	
	private MenuItem mSubMenuItem;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	setTheme(R.style.Theme_Sherlock_Light);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
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
		//update text boxes
		if(UNIT == UNIT_IMPERIAL){
			TextView tv = (TextView)this.findViewById(R.id.textViewDiameter);
			tv.setText(R.string.diameter_inch);
			tv = (TextView)this.findViewById(R.id.textViewArea);
			tv.setText(R.string.area_kcmil);
			tv = (TextView)this.findViewById(R.id.textViewResistance);
			tv.setText(R.string.res_mOhm_per_ft);
			//TODO: converter displayed numbers
		}
		else if(UNIT == UNIT_METRIC){
			TextView tv = (TextView)this.findViewById(R.id.textViewDiameter);
			tv.setText(R.string.diameter_mm);
			tv = (TextView)this.findViewById(R.id.textViewArea);
			tv.setText(R.string.area_mm2);
			tv = (TextView)this.findViewById(R.id.textViewResistance);
			tv.setText(R.string.res_mOhm_per_m);
			//TODO: converter displayed numbers
		}	
	}
	
	
}