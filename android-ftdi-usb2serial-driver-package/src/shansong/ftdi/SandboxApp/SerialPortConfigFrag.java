package shansong.ftdi.SandboxApp;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Fragment;
import android.content.res.Resources;
import android.hardware.usb.UsbDevice;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import shansong.ftdi.d2xx.FTDI_Constants;
import shansong.ftdi.d2xx.FTDI_Device;

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
        buildHashMapsForPortConfig();//build the hashmap for selecting port configuration.
        //these hashmaps are static
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
        
        //TODO: set the onItemClickListener for some spinner here. I believe sp_usb_dev and sp_interface need this.
        //well, maybe sp_interface does not need one.
        //Set the OnItemSelectedListener for usb device list spinner.
        Spinner sp_usb_dev	= (Spinner)v.findViewById(R.id.spinner_usb_dev);
        sp_usb_dev.setOnItemSelectedListener(mUsbDevItemSelectedListener);
        
        //Set the OnClick listener for the button
        Button btn_refresh_usb_dev = (Button)v.findViewById(R.id.button_refresh_usb_dev_list);
        btn_refresh_usb_dev.setOnClickListener(mRefreshUsbDevBtnListener);
        
        return v;
    }
    
    @Override
    public void onHiddenChanged (boolean hidden)
    {
    	//update the enable/disable of each spinner
    	//Only when no serial port is connected, the items in this view 
        //is enabled and user can operate them.
    	if(hidden){
    		//if this is a hiding transition, we should let SandboxAppActivity know what options the user has chosen.
    		updatePortCfgToMainApp();
    	}
    	else{	//if this is a showing transition. Just update spinner status and show them.
    		updateSpinnerStatus();
    	}
    }
    
    @Override
    public void onViewCreated (View view, Bundle savedInstanceState) 
    {
    	//update the enable/disable of each spinner
    	//Only when no serial port is connected, the items in this view 
        //is enabled and user can operate them.
    	updateSpinnerStatus();
    	
    	//TODO: update the USB device selection list here.
    	//general steps:
    	//	1: Enumerate the current list of FTDI devices connected to USB host
    	//	2: Generate a SpinnerAdapter that includes this list
    	//	3: give the adapter to the spinner
    	//	4: Update the interface spinner accordingly. 
    	//following functions did 1~3. still need to update interface spinner items accordingly.
    	refreshUsbDevSpinner();
    }
    
    /**
     * Update spinner enable/disable status, according to SandboxAppActivity.isSerialPortConnected
     * If there's already a serial port connected, all spinners in this GUI are disabled.
     */
    private void updateSpinnerStatus()
    {
    	View v = this.getView();
    	//Get all the spinners
        Spinner sp_usb_dev		=(Spinner)v.findViewById(R.id.spinner_usb_dev);
        Spinner sp_interface	=(Spinner)v.findViewById(R.id.spinner_interface);
        Spinner sp_baud_rate	=(Spinner)v.findViewById(R.id.spinner_baud_rate);
        Spinner sp_data_bits	=(Spinner)v.findViewById(R.id.spinner_data_bits);
        Spinner sp_stop_bits	=(Spinner)v.findViewById(R.id.spinner_stop_bits);
        Spinner sp_parity		=(Spinner)v.findViewById(R.id.spinner_parity);
        Spinner sp_flow_ctrl	=(Spinner)v.findViewById(R.id.spinner_flow_ctrl);
        Button btn_refresh_usb_dev = (Button)v.findViewById(R.id.button_refresh_usb_dev_list);
        
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
        	btn_refresh_usb_dev.setEnabled(false);
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
        	btn_refresh_usb_dev.setEnabled(true);
        }
    }
    
    /** The ItemSelectedListener for USB device list spinner. */
    private OnItemSelectedListener mUsbDevItemSelectedListener = new OnItemSelectedListener(){
    	
    	public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
    		//This callback is invoked only when the newly selected position is different 
        	//from the previously selected position or if there was no selected item.
    		//Note: according to my test, the onItemSelected is called under following situation:
    		//	1. when first setup the selection items in the spinner, e.g. in OnCreateView, the spinner points to the first item.
    		//	2. when user changes the selection from one item to another.
    		//	Important: choosing the same item will NOT trigger this function.
    		
    		//
    		//Need to update the drop-down list of interface selection spinner, according to the device selected.
    		UsbDevice dev = mFTDIDevHashMap.get(((Spinner)getView().findViewById(R.id.spinner_usb_dev)).getSelectedItem());
    		//TODO: update the interface spinner. Need to have another function in FTDI_Device to decide a 
    		//		Hashmap or string array of interfaces for a given usb device.
    		//get how many interfaces should have and refresh the interface spinner accordingly.    		
    		refreshInterfaceSpinner(dev);
    		//test purpose
    		Toast.makeText(getActivity(), "Item selected!", Toast.LENGTH_SHORT).show();
    	}
    	
    	public void onNothingSelected(AdapterView<?> parentView){         
    		//Callback method to be invoked when the selection disappears from this view. 
    		//The selection can disappear for instance when touch is activated or when the adapter becomes empty.
    		//
    		//I haven't tried out when this function is called yet...
    		//Seems that giving no items cannot trigger this function. I think it may happen when we suddenly remove 
    		//all the selection items from the list.
    		//TODO: decide what to do
    		//test purpose
    		Toast.makeText(parentView.getContext(), "Nothing selected!", Toast.LENGTH_SHORT).show();
    	}
	};
	
	
	private OnClickListener mRefreshUsbDevBtnListener = new OnClickListener() {
        public void onClick(View v) {
            //TODO: Whenever this button is clicked, refresh the Usb device list and the interface list.
        	refreshUsbDevSpinner();
        	
        	//testing purpose:
        	Toast.makeText(v.getContext(), "Usb device list updated!", Toast.LENGTH_SHORT).show();
        }
    };	
    
    
    /** The hashmap of FTDI devices. */
    private HashMap<String, UsbDevice> mFTDIDevHashMap;
    /**
     * Refresh the FTDI device list and update content of usb dev spinner.
     */
    private void refreshUsbDevSpinner()
    {
    	//enumerate all FTDI devices
    	mFTDIDevHashMap = FTDI_Device.findAllFTDIDevices(getActivity());
    	if(mFTDIDevHashMap.size()==0){
    		Toast.makeText(getActivity(),"No FTDI device found!",Toast.LENGTH_SHORT).show();
    		return;
    	}
    	//get the key values and use them as spinner's list items by creating an adapter.
    	String[] items = new String[mFTDIDevHashMap.size()];
    	mFTDIDevHashMap.keySet().toArray(items);
    	SpinnerAdapter adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, items);
    	//"adapter" contains all device names.
    	((Spinner)getView().findViewById(R.id.spinner_usb_dev)).setAdapter(adapter);
    	//TODO: this fragment need to keep a record of mFTDIList, add a member hashmap is enough.
    }
    
    private HashMap<String, Integer> mInterfaceHashMap;
    
    private void refreshInterfaceSpinner(UsbDevice dev)
    {
    	mInterfaceHashMap = FTDI_Device.listAllInterfaces(dev);
    	if(mInterfaceHashMap.size()==0){
    		Toast.makeText(getActivity(),"Couldn't recognize the selected device as FTDI device!",Toast.LENGTH_SHORT).show();
    		return;
    	}
    	String[] items = new String[mInterfaceHashMap.size()];
    	mInterfaceHashMap.keySet().toArray(items);
    	SpinnerAdapter adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, items);
    	((Spinner)getView().findViewById(R.id.spinner_interface)).setAdapter(adapter);
    }
    
    private HashMap<String, Integer> mBaudRateHashMap;
    private HashMap<String, Integer> mDataBitsHashMap;
    private HashMap<String, Integer> mStopBitsHashMap;
    private HashMap<String, Integer> mParityHashMap;
    private HashMap<String, Integer> mFlowCtrlHashMap;
    /**
     * Builds the hash maps for port config.
     */
    private void buildHashMapsForPortConfig()
    {
    	Resources res = getResources();
    	String[] baud_rate_list = res.getStringArray(R.array.baud_rate_list);
    	String[] data_bits_list = res.getStringArray(R.array.data_bits_list);
    	String[] stop_bits_list = res.getStringArray(R.array.stop_bits_list);
    	String[] parity_list = res.getStringArray(R.array.parity_list);
    	String[] flow_ctrl_list = res.getStringArray(R.array.flow_ctrl_list);
    	//intialize hashmaps first:
    	mFTDIDevHashMap = new HashMap<String, UsbDevice>();
    	mInterfaceHashMap = new HashMap<String, Integer>();
    	mBaudRateHashMap = new HashMap<String, Integer>();
    	mDataBitsHashMap = new HashMap<String, Integer>();
    	mStopBitsHashMap = new HashMap<String, Integer>();
    	mParityHashMap = new HashMap<String, Integer>();
    	mFlowCtrlHashMap = new HashMap<String, Integer>();
    	
    	//baud rate hashmap
    	for(String str: baud_rate_list){
    		mBaudRateHashMap.put(str, Integer.parseInt(str));
    	}
    	//data bits hashmap
    	for(String str: data_bits_list){
    		if(str == "7"){
    			mDataBitsHashMap.put(str, FTDI_Constants.DATA_BITS_7);
    		}else if(str == "8"){
    			mDataBitsHashMap.put(str, FTDI_Constants.DATA_BITS_8);
    		}
    	}
    	
    	//stop bits hashmap
    	for(String str: stop_bits_list){
    		if(str == "1"){
    			mStopBitsHashMap.put(str, FTDI_Constants.STOP_BITS_1);
    		}else if(str == "1.5"){
    			mStopBitsHashMap.put(str, FTDI_Constants.STOP_BITS_15);
    		}else if(str == "2"){
    			mStopBitsHashMap.put(str, FTDI_Constants.STOP_BITS_2);
    		}
    	}
    	//parity hash map
    	for(String str: parity_list){
    		if(str == "None"){
    			mParityHashMap.put(str, FTDI_Constants.PARITY_NONE);
    		}else if(str == "Odd"){
    			mParityHashMap.put(str, FTDI_Constants.PARITY_ODD);
    		}else if(str == "Even"){
    			mParityHashMap.put(str, FTDI_Constants.PARITY_EVEN);
    		}else if(str == "Mark"){
    			mParityHashMap.put(str, FTDI_Constants.PARITY_MARK);
    		}else if(str == "Space"){
    			mParityHashMap.put(str, FTDI_Constants.PARITY_SPACE);
    		}
    	}
    	//flow control hash map
    	for(String str: flow_ctrl_list){
    		if(str == "None"){
    			mFlowCtrlHashMap.put(str,FTDI_Constants.SIO_DISABLE_FLOW_CTRL);
    		}else if(str == "RTS/CTS"){
    			mFlowCtrlHashMap.put(str,FTDI_Constants.SIO_RTS_CTS_HS);
    		}else if(str == "DTR/DSR"){
    			mFlowCtrlHashMap.put(str,FTDI_Constants.SIO_DTR_DSR_HS);
    		}else if(str == "Xon/Xoff"){
    			mFlowCtrlHashMap.put(str,FTDI_Constants.SIO_XON_XOFF_HS);
    		}
    	}
    }
    
    private void updatePortCfgToMainApp()
    {
    	SandboxAppActivity mApp = (SandboxAppActivity) getActivity();
		UsbDevice dev = mFTDIDevHashMap.get(((Spinner)getView().findViewById(R.id.spinner_usb_dev)).getSelectedItem());
		Integer ftdi_interface = mInterfaceHashMap.get(((Spinner)getView().findViewById(R.id.spinner_interface)).getSelectedItem());
		Integer baudRate = mBaudRateHashMap.get(((Spinner)getView().findViewById(R.id.spinner_baud_rate)).getSelectedItem());
		Integer dataBits = mDataBitsHashMap.get(((Spinner)getView().findViewById(R.id.spinner_data_bits)).getSelectedItem());
		Integer stopBits = mStopBitsHashMap.get(((Spinner)getView().findViewById(R.id.spinner_stop_bits)).getSelectedItem());
		Integer parity = mParityHashMap.get(((Spinner)getView().findViewById(R.id.spinner_parity)).getSelectedItem());
		Integer flowCtrol = mFlowCtrlHashMap.get(((Spinner)getView().findViewById(R.id.spinner_flow_ctrl)).getSelectedItem());
		
		if(dev == null){
			Toast.makeText(this.getActivity(), "Selected USB device cannot found. Try refresh device list.", Toast.LENGTH_SHORT).show();
			return;
		}
		if(ftdi_interface == null){
			Toast.makeText(this.getActivity(), "Selected interface cannot found. Try refresh device list.", Toast.LENGTH_SHORT).show();
			return;
		}
		
		mApp.setSerialPortConfigSelections(dev, ftdi_interface, baudRate, dataBits, stopBits, parity, flowCtrol);
    }
}
