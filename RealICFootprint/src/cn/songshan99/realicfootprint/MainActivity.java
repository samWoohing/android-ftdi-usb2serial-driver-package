package cn.songshan99.realicfootprint;

import java.io.IOException;
import java.io.InputStream;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;

import cn.songshan99.FootprintParser.ICFootprint;
import cn.songshan99.realicfootprint.R;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends SherlockActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTheme(R.style.Theme_Sherlock_Light);
		setContentView(R.layout.activity_main);
		
		String str="DIP16.fp";
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
		menu.add(2, 1, 1, "About");
		return super.onCreateOptionsMenu(menu);
	}


	
}
