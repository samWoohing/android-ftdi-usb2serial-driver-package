package shansong.ftdi.SandboxApp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

// TODO: Auto-generated Javadoc
/**
 * The Class SerialPortConfigFrag.
 */
public class SerialPortConfigFrag extends Fragment {
	
	//TODO: let this fragment maintain a list of usb devices that are 
	//FTDI devices connected to the current USB host. 
	//this fragment shall be responsible to update this list and give user choices.
	//We may need to rewrite the FTDI_Device.InitDevice function to summarize some checking function
	//and provide the methods needed by this fragment.
	
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
        View v = inflater.inflate(R.layout.serial_port_config, container, false);
        //TODO: put any GUI initialization needed into here
        //Detect whether or not a serial port is already connected.
        //if already connected, all items in this view shall be disabled.
        //Only when no serial port is connected, the items in this view 
        //is enabled and user can operate them.
        
        //Get all the spinners
        Spinner sp_usb_dev		=(Spinner)v.findViewById(R.id.spinner_usb_dev);
        Spinner sp_interface	=(Spinner)v.findViewById(R.id.spinner_interface);
        Spinner sp_baud_rate	=(Spinner)v.findViewById(R.id.spinner_baud_rate);
        Spinner sp_data_bits	=(Spinner)v.findViewById(R.id.spinner_data_bits);
        Spinner sp_stop_bits	=(Spinner)v.findViewById(R.id.spinner_stop_bits);
        Spinner sp_parity		=(Spinner)v.findViewById(R.id.spinner_parity);
        Spinner sp_flow_ctrl	=(Spinner)v.findViewById(R.id.spinner_flow_ctrl);
        
        //TODO: set the onItemClickListener for some spinner here. I believe sp_usb_dev and sp_interface need this.
        //well, maybe sp_interface does not need one.
        
        //decide whether these spinners should be enabled or disabled.
        SandboxAppActivity mApp = (SandboxAppActivity)this.getActivity();
        if(mApp.isSerialPortConnected())
        {	//if the interface is already connected, all spinners should be disabled.
        	sp_usb_dev.setEnabled(false);
        	sp_interface.setEnabled(false);
        	sp_baud_rate.setEnabled(false);
        	sp_data_bits.setEnabled(false);
        	sp_stop_bits.setEnabled(false);
        	sp_parity.setEnabled(false);
        	sp_flow_ctrl.setEnabled(false);
        }
        else
        {	//if the interface is not connected, we need to allow port configuring.
        	//And also at here, we should poll all the USB devices and give a list of FTDI devices.
        	sp_usb_dev.setEnabled(true);
        	sp_interface.setEnabled(true);
        	sp_baud_rate.setEnabled(true);
        	sp_data_bits.setEnabled(true);
        	sp_stop_bits.setEnabled(true);
        	sp_parity.setEnabled(true);
        	sp_flow_ctrl.setEnabled(true);
        }
        return v;
    }
}
