package com.songshan99.DynamicSpine;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.Toast;

/**
 * @author 307004396
 *
 */
public class ArcheryDynamicSpineActivity extends Activity {
    /**
	 * The listener interface for receiving tab events.
	 * The class that is interested in processing a tab
	 * event implements this interface, and the object created
	 * with that class is registered with a component using the
	 * component's <code>addTabListener<code> method. When
	 * the tab event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @param <T> the generic type
	 * @see TabEvent
	 */
	public static class TabListener<T extends Fragment> implements ActionBar.TabListener {
	    
	    /** The m activity. */
	    private final Activity mActivity;     
	    /** The m tag. */
	    private final String mTag;        
	    /** The m class. */
	    private final Class<T> mClass;        
	    /** The m args. */
	    private final Bundle mArgs;        
	    /** The m fragment. */
	    private Fragment mFragment;
	
	    /**
	     * Instantiates a new tab listener.
	     *
	     * @param activity the activity
	     * @param tag the tag
	     * @param clz the clz
	     */
	    public TabListener(Activity activity, String tag, Class<T> clz) {
	        this(activity, tag, clz, null);
	    }
	
	    /**
	     * Instantiates a new tab listener.
	     *
	     * @param activity the activity
	     * @param tag the tag
	     * @param clz the clz
	     * @param args the args
	     */
	    public TabListener(Activity activity, String tag, Class<T> clz, Bundle args) {
	        mActivity = activity;
	        mTag = tag;
	        mClass = clz;
	        mArgs = args;
	
	        // Check to see if we already have a fragment for this tab, probably
	        // from a previously saved state.  If so, deactivate it, because our
	        // initial state is that a tab isn't shown.
	
	        mFragment = mActivity.getFragmentManager().findFragmentByTag(mTag);
	        //Original android sample uses isDetached() and Detach() here. But since we use show/hide 
	        //in onTabSelected/OnTabDeselected, we should change here to isHidden/Hide accordingly.
	        //This is very important and solves a lot of weird problem in UI behavior.
	        if (mFragment != null && !mFragment.isHidden()) 
	        {
	            FragmentTransaction ft = mActivity.getFragmentManager().beginTransaction();
	            ft.hide(mFragment);
	            ft.commit();
	        }
	    }
	
	    /* (non-Javadoc)
	     * @see android.app.ActionBar.TabListener#onTabSelected(android.app.ActionBar.Tab, android.app.FragmentTransaction)
	     */
	    public void onTabSelected(Tab tab, FragmentTransaction ft) 
	    {
	        if (mFragment == null){	//this is actually where a new fragment is instantiated.
	            mFragment = Fragment.instantiate(mActivity, mClass.getName(), mArgs);
	            ft.add(android.R.id.content, mFragment, mTag);
	            //Toast.makeText(mActivity, "Fragment add: "+mTag, Toast.LENGTH_SHORT).show();
	        } 
	        else{
	            //ft.attach(mFragment);
	        	//I modified this class, to show and hide, not attach and detach.
	        	ft.show(mFragment);
	        	//Toast.makeText(mActivity, "Fragment show: "+mTag, Toast.LENGTH_SHORT).show();
	        }
	    }
	
	    /* (non-Javadoc)
	     * @see android.app.ActionBar.TabListener#onTabUnselected(android.app.ActionBar.Tab, android.app.FragmentTransaction)
	     */
	    public void onTabUnselected(Tab tab, FragmentTransaction ft)
	    {
	        if (mFragment != null){
	            //ft.detach(mFragment);
	        	//I modified this class, to show and hide, not attach and detach.
	        	ft.hide(mFragment);
	        	//Toast.makeText(mActivity, "Fragment hide: "+mTag, Toast.LENGTH_SHORT).show();
	        }
	    }
	
	    /* (non-Javadoc)
	     * @see android.app.ActionBar.TabListener#onTabReselected(android.app.ActionBar.Tab, android.app.FragmentTransaction)
	     */
	    public void onTabReselected(Tab tab, FragmentTransaction ft) 
	    {
	        Toast.makeText(mActivity, "Reselected!", Toast.LENGTH_SHORT).show();
	    }
	}

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
      //It seems that if we use action bar, we are NOT able to use any theme if the theme has NoTitle. 
        //Or the getActionBar will return null.
        final ActionBar bar = this.getActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        //disable the DISPLAY_SHOW_HOME and DISPLAY_SHOW_TITLE, then only tabs are shown on the top of screen.
        //Note that this setDisplayOptions function is a little tricky. 
        //First parameter is value, Second parameter is Mask
        bar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE );
               
        bar.addTab(bar.newTab()
                .setText("Arrow")
                .setTabListener(new TabListener<ArrowConfigFrag>(
                        this, "arrow", ArrowConfigFrag.class)));
        bar.addTab(bar.newTab()
                .setText("Bow")
                .setTabListener(new TabListener<BowConfigFrag>(
                        this, "bow", BowConfigFrag.class)));
        bar.addTab(bar.newTab()
                .setText("Result")
                .setTabListener(new TabListener<ResultFrag>(
                        this, "Result", ResultFrag.class)));
        
        //bar.setSelectedNavigationItem(0);
        //retrieve the previously saved Tab status
        if (savedInstanceState != null) {
        	int index = savedInstanceState.getInt("tab", 0);
        	if(index != 0)
        		bar.setSelectedNavigationItem(index);
        }
    }
}