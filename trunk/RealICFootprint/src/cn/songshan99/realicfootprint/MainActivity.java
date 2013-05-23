package cn.songshan99.realicfootprint;

import java.io.IOException;
import java.io.InputStream;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;

import cn.songshan99.FootprintParser.ICFootprint;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.support.v4.widget.SimpleCursorAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends SherlockActivity {
	//TODO: TO18, TO39, TO92 display may have problems.(solved, parser problem)
	//TODO: the color display
	//TODO: the ListPopupWindow selection of search results (done)
	//TODO: revise the search method to support " " (blank) in search string
	//TODO: prevent screen rotating. Portrait only.
	//TODO: freeze button?
	//TODO: about dialog
	//TODO: DIN41651_40S has display problem, bound box inaccurate.(solved)
	//TODO: DBXX has display problem. Perhaps Because their mark appear at a weirdo location in footprint file. (fixed, this is the absolute/relative coordination problem.)
	private Spinner mSpinnerICFootprint;
	private ICFootprintView mICFootprintView;
	private com.actionbarsherlock.view.Menu mMenu;
	
	private static String[] LISTVIEW_FROM = new String[]{ICFootprintDescDatabase.KEY_FILENAME,ICFootprintDescDatabase.KEY_DESCRIPTION};
	private static int[] LISTVIEW_TO = new int[]{R.id.textViewFPName, R.id.textViewFPDesc};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTheme(R.style.Theme_Sherlock_Light);
		setContentView(R.layout.activity_main);
		
		//set the image button onclick listener
		ImageButton btn = (ImageButton)findViewById(R.id.imageButtonLock);
		mICFootprintView = (ICFootprintView)findViewById(R.id.icfootprintview);
		
		btn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// Toggle the lock/unlock status
				mICFootprintView.setmLockICFootprint(!mICFootprintView.ismLockICFootprint());
				if(mICFootprintView.ismLockICFootprint())((ImageButton)v).setImageResource(R.drawable.lock_icon);
				else((ImageButton)v).setImageResource(R.drawable.unlock_icon);
			}
			
		});
		
		btn = (ImageButton)findViewById(R.id.imageButtonRotateCCW);
		btn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				rotateICFootprint(ICFootprintView.DIR_CCW);
			}		
		});
		
		btn = (ImageButton)findViewById(R.id.imageButtonRotateCW);
		btn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				rotateICFootprint(ICFootprintView.DIR_CW);
			}		
		});
		
		String str="TQFP208_28.fp";
		ICFootprint footprint;
		ICFootprintRender render;
		
		
		try {
			InputStream stream = getAssets().open(str);
			footprint = ICFootprintView.parseFootprintFile(stream);
			//footprint.offsetTheFootprint(30000,40000);
			stream.close();
			mICFootprintView.setICFootprint(footprint);
			
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		render=mICFootprintView.getmICFootprintRender();
		render.setLayerVisible(ICFootprintRender.LAYER_COPPER, true);
		render.setLayerVisible(ICFootprintRender.LAYER_DRAFT, true);
		render.setLayerVisible(ICFootprintRender.LAYER_DRILL, true);
		render.setLayerVisible(ICFootprintRender.LAYER_MASK, false);
		//render.setLayerVisible(ICFootprintRender.LAYER_CLEARANCE, true);
		render.setLayerColor(ICFootprintRender.LAYER_COPPER, getResources().getColor(R.color.Black));
		render.setLayerColor(ICFootprintRender.LAYER_DRAFT,  getResources().getColor(R.color.Green));
		render.setLayerColor(ICFootprintRender.LAYER_DRILL,  getResources().getColor(R.color.Red));
		render.setLayerColor(ICFootprintRender.LAYER_MASK,  getResources().getColor(R.color.Red));
		
		mSpinnerICFootprint = (Spinner)findViewById(R.id.spinnerICFootprint);
		setSpinnerContent(mSpinnerICFootprint);
		
		handleIntent(getIntent());
		
	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		
		//Note: This helps to get the actionbarsherlock specifit menu inflater.
		
		com.actionbarsherlock.view.MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.main, menu);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
	        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
	        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
	        searchView.setIconifiedByDefault(true);
	        //searchView.setOnQueryTextListener(mQueryTextListener);
        }
        //menu.add(2, 2, 2, "About");
        mMenu = menu;
        return true;
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
            	if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB)onSearchRequested();
            	//NOTE: seems we DON'T need to explicitely call above line on SDK>11,
            	//since the system can show the searchview automatically by default
            	//So do this for compatibility purpose
                return true;
            case R.id.about:
            	AboutDialog dlg = new AboutDialog(this);
				dlg.show();
            	return true;
            default:
                return false;
        }
    }
	
	@Override
	protected void onNewIntent(Intent intent) {
		
		handleIntent(intent);
	}
	
	private void handleIntent(Intent intent) {
		if (Intent.ACTION_VIEW.equals(intent.getAction())) {
			//The URI shall contain the rowID information, we will use this to retrieve filename data.
			Cursor cursor = getContentResolver().query(intent.getData(), null, null, null, null);
			
			if(cursor == null) return;
			if(cursor.getCount() <=0) return;
			cursor.moveToFirst();
			int index=-1;
			String filename = null;
			index = cursor.getInt(0)-1;
			if(index >=0)	mSpinnerICFootprint.setSelection(index);
			//if(filename != null) //TODO: refresh the footprint display
			//This should be handled by the itemselectedlistener of the spinner. Right?
		} else if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			// handles a search query
			String query = intent.getStringExtra(SearchManager.QUERY);
			final Cursor cursor = getContentResolver().query(ICFootprintDescProvider.CONTENT_URI, null, null,
                    new String[] {query}, null);
			if(cursor!=null){
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.icfootprint_info, cursor, LISTVIEW_FROM, LISTVIEW_TO,0);
				
				builder.setAdapter(adapter, new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						cursor.moveToPosition(which);
						Integer rowid = cursor.getInt(0);
						if(rowid!=null)mSpinnerICFootprint.setSelection(rowid-1);//Note: rowid is 1-based but spinner selection is 0-based.
						else Toast.makeText(getApplicationContext(), "Cannot find footprint!", Toast.LENGTH_SHORT).show();
						//Note: below is for compatibility purpose. Only do when SDK>11
						if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
					        SearchView searchView = (SearchView) mMenu.findItem(R.id.search).getActionView();
					        searchView.clearFocus();
				        }
						dialog.dismiss();
					}
					});
				
				builder.setTitle("Select a footprint: ");
				//builder.setOnItemSelectedListener(mOnICFootprintSelectedListener);
				builder.create().show();
			}
		}
	}
	    
	private void setSpinnerContent(Spinner spinner){
    	//.getContentResolver().query(uri, projection, selection, selectionArgs, sortOrder)
		Cursor cursor;
		cursor = getContentResolver()
				.query(ICFootprintDescProvider.CONTENT_ALL_URI, null, null,
						null, null);

		// query all information from database
    	
    	//create cursor adapter and set it as the spinner's adapter
    	SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.icfootprint_info, cursor, LISTVIEW_FROM, LISTVIEW_TO,0);
    	spinner.setAdapter(adapter);
    	spinner.setOnItemSelectedListener(mOnICFootprintSelectedListener);
    }
    
    private OnItemSelectedListener mOnICFootprintSelectedListener = new OnItemSelectedListener(){

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
			TextView tv = (TextView)view.findViewById(R.id.textViewFPName);
			//TODO: use tv.getText(); to update the footprint choice
			if(tv!=null) setICFootprint(tv.getText().toString()+".fp");
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		}
    	
    };
    
    private void setICFootprint(String filename){
    	ICFootprint footprint;
		ICFootprintRender render;
		
		try {
			InputStream stream = getAssets().open(filename);
			footprint = ICFootprintView.parseFootprintFile(stream);
			//footprint.offsetTheFootprint(30000,40000);
			stream.close();
			mICFootprintView.setICFootprint(footprint);
			
		} catch (IOException e) {
			Toast.makeText(this, "Cannot find footprint: "+filename, Toast.LENGTH_SHORT).show();
			e.printStackTrace();
			return;
		}
		
		//TODO: clean the render anc color setup later.
		render=mICFootprintView.getmICFootprintRender();
		render.setLayerVisible(ICFootprintRender.LAYER_COPPER, true);
		render.setLayerVisible(ICFootprintRender.LAYER_DRAFT, true);
		render.setLayerVisible(ICFootprintRender.LAYER_DRILL, true);
		render.setLayerVisible(ICFootprintRender.LAYER_MASK, false);
		//render.setLayerVisible(ICFootprintRender.LAYER_CLEARANCE, true);
		render.setLayerColor(ICFootprintRender.LAYER_COPPER, getResources().getColor(R.color.Black));
		render.setLayerColor(ICFootprintRender.LAYER_DRAFT,  getResources().getColor(R.color.Green));
		render.setLayerColor(ICFootprintRender.LAYER_DRILL,  getResources().getColor(R.color.Red));
		render.setLayerColor(ICFootprintRender.LAYER_MASK,  getResources().getColor(R.color.Red));
		mICFootprintView.invalidate();
    }
    
    private void rotateICFootprint(int dir){
    	if(mICFootprintView.ismLockICFootprint()){
    		//promote user to unlock the screen
    		Toast.makeText(getApplicationContext(), "Unlock the footprint first...", Toast.LENGTH_SHORT).show();
    		return;
    	}
    	mICFootprintView.rotateICFootprint(dir);
    }
//	private SearchView.OnQueryTextListener mQueryTextListener = new SearchView.OnQueryTextListener(){
//
//		@Override
//		public boolean onQueryTextSubmit(String query) {
//			// TODO Auto-generated method stub
//			return false;
//		}
//
//		@Override
//		public boolean onQueryTextChange(String newText) {
//			// TODO Auto-generated method stub
//			return false;
//		}
//	
//	};
	
}
