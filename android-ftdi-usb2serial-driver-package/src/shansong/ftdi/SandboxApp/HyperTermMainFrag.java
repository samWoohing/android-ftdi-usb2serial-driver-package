package shansong.ftdi.SandboxApp;

import java.nio.ByteBuffer;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.Spinner;
import shansong.ftdi.d2xx.FTDI_Interface;

// TODO: Auto-generated Javadoc
/**
 * The Class HyperTermMainFrag.
 */
public class HyperTermMainFrag extends Fragment {
	
	/**
	 * When creating, TODO: decide what to do.
	 *
	 * @param savedInstanceState the saved instance state
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    /**
     * The Fragment's UI is loaded in onCreateView.
     *
     * @param inflater the inflater
     * @param container the container
     * @param savedInstanceState the saved instance state
     * @return the view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.hyper_term_main, container, false);
        //TODO: put any GUI initialization needed into here. Below items needs to be finalized
        //Get all the buttons and spinners that we need to setup.
        ToggleButton tb = (ToggleButton)v.findViewById(R.id.toggleButton_connect);
        //some other buttons here...
         
        //set the click listener for the button.
        tb.setOnClickListener(mConnectBtnListener);
        
        return v;
    }
    
    /** The button click listener of the "connect" button. */
    private OnClickListener mConnectBtnListener = new OnClickListener() {
        public void onClick(View v) {
            // use following example to get the current activity:
        	//TODO: detailed implementation. Currently just dummy code.
        	Toast.makeText(getActivity(), "Connect button clicked!", Toast.LENGTH_SHORT).show();
        	SandboxAppActivity mApp = (SandboxAppActivity)getActivity();
        	mApp.mIsSerialPortConnected = mApp.mIsSerialPortConnected ? false:true;
        }
    };
    
    
}
