package cn.songshan99.realicfootprint;

import java.io.IOException;
import java.io.InputStream;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.actionbarsherlock.widget.SearchView;

import cn.songshan99.FootprintParser.ICFootprint;
import cn.songshan99.realicfootprint.R;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends SherlockActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTheme(R.style.Theme_Sherlock_Light);
		setContentView(R.layout.activity_main);
		
		String str="TQFP208_28.fp";
		ICFootprint footprint;
		ICFootprintRender render;
		ICFootprintView v = (ICFootprintView)findViewById(R.id.icfootprintview);
		
		try {
			InputStream stream = getAssets().open(str);
			footprint = ICFootprintView.parseFootprintFile(stream);
			//footprint.offsetTheFootprint(30000,40000);
			stream.close();
			v.setICFootprint(footprint);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		render=v.getmICFootprintRender();
		render.setLayerVisible(ICFootprintRender.LAYER_COPPER, true);
		render.setLayerVisible(ICFootprintRender.LAYER_DRAFT, true);
		render.setLayerVisible(ICFootprintRender.LAYER_DRILL, true);
		render.setLayerVisible(ICFootprintRender.LAYER_MASK, false);
		//render.setLayerVisible(ICFootprintRender.LAYER_CLEARANCE, true);
		render.setLayerColor(ICFootprintRender.LAYER_COPPER, getResources().getColor(R.color.Black));
		render.setLayerColor(ICFootprintRender.LAYER_DRAFT,  getResources().getColor(R.color.Green));
		render.setLayerColor(ICFootprintRender.LAYER_DRILL,  getResources().getColor(R.color.Red));
		render.setLayerColor(ICFootprintRender.LAYER_MASK,  getResources().getColor(R.color.Red));

		//TODO: use above code as debuging start, and finish ICFootprintView.
		//set the visibility of each layer, and make sure they are shown correctly.
	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		
		//Used to put dark icons on light action bar

        //Create the search view
        SearchView searchView = new SearchView(getSupportActionBar().getThemedContext());
        searchView.setQueryHint("Search for countries…");

        menu.add(1,1,1,"Search")
            .setIcon(R.drawable.ic_search_inverse)
            .setActionView(searchView)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        //searchView.setIconifiedByDefault(false);
        
        //searchView.setOnQueryTextListener(mQueryTextListener);
        menu.add(2, 2, 2, "About");
        return true;
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO: perform the actual search job
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
		      String query = intent.getStringExtra(SearchManager.QUERY);
		      //call the search function
		    }
	}
	
	
	private SearchView.OnQueryTextListener mQueryTextListener = new SearchView.OnQueryTextListener(){

		@Override
		public boolean onQueryTextSubmit(String query) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean onQueryTextChange(String newText) {
			// TODO Auto-generated method stub
			return false;
		}
	
	};
	
}
