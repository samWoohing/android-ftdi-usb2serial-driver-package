package shansong.ftdi.SandboxApp;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.Spinner;


public class HyperTermMainFrag extends Fragment {
	/**
     * When creating, TODO: decide what to do
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    /**
     * The Fragment's UI is loaded in onCreateView
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
    
    private OnClickListener mConnectBtnListener = new OnClickListener() {
        public void onClick(View v) {
            // use following example to get the current activity:
        	Toast.makeText(getActivity(), "Connect button clicked!", Toast.LENGTH_SHORT).show();
        	SandboxAppActivity mApp = (SandboxAppActivity)getActivity();
        	mApp.mIsSerialPortConnected = mApp.mIsSerialPortConnected ? false:true;
        }
    };
}
