package cn.songshan99.realicfootprint;

import java.io.IOException;
import java.io.InputStream;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;

import cn.songshan99.FootprintParser.ICFootprint;

import android.app.SearchManager;
import android.content.Context;
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
import android.widget.Spinner;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends SherlockActivity {
	
	private Spinner mSpinnerICFootprint;
	private ICFootprintView mICFootprintView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTheme(R.style.Theme_Sherlock_Light);
		setContentView(R.layout.activity_main);
		
		String str="TQFP208_28.fp";
		ICFootprint footprint;
		ICFootprintRender render;
		mICFootprintView = (ICFootprintView)findViewById(R.id.icfootprintview);
		
		try {
			InputStream stream = getAssets().open(str);
			footprint = ICFootprintView.parseFootprintFile(stream);
			//footprint.offsetTheFootprint(30000,40000);
			stream.close();
			mICFootprintView.setICFootprint(footprint);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
        
        return true;
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                onSearchRequested();
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
			//TODO: intent.getData() can get the detailed intent URI.
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
			showResults(query);
		}
	}
	 
    /**
     * Searches the dictionary and displays results for the given query.
     * @param query The search query
     */
    private void showResults(String query) {

    }
    
    @SuppressWarnings("deprecation")
	private void setSpinnerContent(Spinner spinner){
    	//.getContentResolver().query(uri, projection, selection, selectionArgs, sortOrder)
		Cursor cursor;
		cursor = getContentResolver()
				.query(ICFootprintDescProvider.CONTENT_ALL_URI, null, null,
						null, null);

		// query all information from database
    	
    	//create cursor adapter and set it as the spinner's adapter
    	String[] from = new String[]{ICFootprintDescDatabase.KEY_FILENAME,ICFootprintDescDatabase.KEY_DESCRIPTION};
    	int[] to = new int[]{R.id.textViewFPName, R.id.textViewFPDesc};
    	SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.icfootprint_info, cursor, from, to,0);
    	spinner.setAdapter(adapter);
    	spinner.setOnItemSelectedListener(mOnICFootprintSelectedListener);
    }
    
    private OnItemSelectedListener mOnICFootprintSelectedListener = new OnItemSelectedListener(){

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
			TextView tv = (TextView)view.findViewById(R.id.textViewFPName);
			//TODO: use tv.getText(); to update the footprint choice
			setICFootprint(tv.getText().toString()+".fp");
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
