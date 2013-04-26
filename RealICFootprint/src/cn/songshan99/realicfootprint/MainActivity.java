package cn.songshan99.realicfootprint;

import java.io.IOException;
import java.io.InputStream;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;

import cn.songshan99.FootprintParser.ICFootprint;
import cn.songshan99.realicfootprint.R;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends SherlockActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		String str="DIP16.fp";
		ICFootprint footprint;
		ICFootprintRender render;
		try {
			InputStream stream = getAssets().open(str);
			footprint = ICFootprintView.parseFootprintFile(stream);
			stream.close();
			render =ICFootprintView.createFootprintRender(footprint, 120.0f);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		ICFootprintView v = (ICFootprintView)findViewById(R.id.icfootprintview);
		v.setICFootprint(footprint);
		v.setmICFootprintRender(render);
		
		
		
		//TODO: use above code as debuging start, and finish ICFootprintView.
		//set the visibility of each layer, and make sure they are shown correctly.
	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		menu.add(2, 1, 1, "About");
		return super.onCreateOptionsMenu(menu);
	}


	
}
