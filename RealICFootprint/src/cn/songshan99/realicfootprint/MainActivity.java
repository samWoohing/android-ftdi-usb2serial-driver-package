package cn.songshan99.realicfootprint;

import java.io.IOException;

import com.actionbarsherlock.app.SherlockActivity;
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
			footprint = ICFootprintView.parseFootprintFile(getAssets().open(str));
			getAssets().close();
			render =ICFootprintView.createFootprintRender(footprint, 120.0f);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		ICFootprintView v = (ICFootprintView)findViewById(R.id.icfootprintview);
		v.setICFootprint(footprint);
		v.setmICFootprintRender(render);
		
		//ICFootprintView.
		//v.setICFootprint(footprint);
	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		// TODO Auto-generated method stub
		//getMenuInflater().inflate(R.menu.main, (Menu) menu);
		return super.onCreateOptionsMenu(menu);
	}
	
}
