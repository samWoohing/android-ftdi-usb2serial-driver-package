package shansong.ftdi.SandboxApp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        //TODO: put any GUI initialization needed into here
        return v;
    }
}
