package cn.songshan99.realicfootprint;

import com.actionbarsherlock.app.SherlockActivity;
import cn.songshan99.realicfootprint.R;

import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends SherlockActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.main, (Menu) menu);
		return super.onCreateOptionsMenu(menu);
	}
	
}
