package shansong.ftdi.SandboxApp;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.os.Bundle;
import android.widget.Toast;
import shansong.ftdi.d2xx.*;

public class SandboxAppActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        
        //It seems that if we use action bar, we are NOT able to use any theme. Or the
        final ActionBar bar = this.getActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        bar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);

        bar.addTab(bar.newTab()
                .setText("Simple")
                .setTabListener(new TabListener<Fragment>(
                        this, "simple", Fragment.class)));
        bar.addTab(bar.newTab()
                .setText("Contacts")
                .setTabListener(new TabListener<Fragment>(
                        this, "contacts", Fragment.class)));
        
    }
    
    public static class TabListener<T extends Fragment> implements ActionBar.TabListener {
        private final Activity mActivity;
        private final String mTag;
        private final Class<T> mClass;
        private final Bundle mArgs;
        private Fragment mFragment;

        public TabListener(Activity activity, String tag, Class<T> clz) {
            this(activity, tag, clz, null);
        }

        public TabListener(Activity activity, String tag, Class<T> clz, Bundle args) {
            mActivity = activity;
            mTag = tag;
            mClass = clz;
            mArgs = args;

            // Check to see if we already have a fragment for this tab, probably
            // from a previously saved state.  If so, deactivate it, because our
            // initial state is that a tab isn't shown.
            mFragment = mActivity.getFragmentManager().findFragmentByTag(mTag);
            if (mFragment != null && !mFragment.isDetached()) {
                FragmentTransaction ft = mActivity.getFragmentManager().beginTransaction();
                ft.detach(mFragment);
                ft.commit();
            }
        }

        public void onTabSelected(Tab tab, FragmentTransaction ft) {
            if (mFragment == null) {
                mFragment = Fragment.instantiate(mActivity, mClass.getName(), mArgs);
                ft.add(android.R.id.content, mFragment, mTag);
            } else {
                ft.attach(mFragment);
            }
        }

        public void onTabUnselected(Tab tab, FragmentTransaction ft) {
            if (mFragment != null) {
                ft.detach(mFragment);
            }
        }

        public void onTabReselected(Tab tab, FragmentTransaction ft) {
            Toast.makeText(mActivity, "Reselected!", Toast.LENGTH_SHORT).show();
        }
    }
}