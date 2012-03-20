/*
 * This is the class that represents a entire FTDI chip, or say, a FTDI device.
 * A FTDI device shall have one or multiple USB interfaces, and optionally, it shall have a EEPROM chip
 */
package shansong.ftdi.d2xx;

import android.content.Context;
import android.util.Log;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.hardware.usb.UsbConstants;

// TODO: Auto-generated Javadoc
/**
 * The Class FTDI_Device.
 *
 * @author 307004396
 */
public class FTDI_Device{

	//==================================================================
	//	1. USB command codes
	//==================================================================
	
	//==================================================================
	//	2. member objects
	//==================================================================
	/** The Constant TAG string used for incident logging. */
	private static final String TAG = "FTDI_Device";
	
	/** The FTDI_Device class keeps a record of the caller Activity or Service's context. */
	private Context mContext;
	
	/** The FTDI_Device class keeps a record of which usbDevice it's using. */
	private UsbDevice mUsbDevice;
	
	/** The FTDI_Device class keeps a record of the usbDeviceConnection. */
	private UsbDeviceConnection mUsbDeviceConnection;
	
	/** The ftdi interfaces. FTDI device(chip) may have 1, 2 or 4 interfaces */
	private FTDI_Interface[] mFTDI_Interfaces;
	
	/** The ftdi eeprom. One FTDI device(chip) shall only have 1 eeprom*/
	private FTDI_EEPROM mFTDI_EEPROM;
	
	/** The read timeout. */
	private int mReadTimeout	= 5000;
	
	/** The write timeout. */
	private int mWriteTimeout	= 5000;
	
	/** The device type defined by FTDI VID. */
	private int mDeviceType = 0;
	//==================================================================
	//	3. The section of member methods
	//==================================================================
	/**
	 * Instantiates a new FTDI_Device.
	 *
	 * @param dev the dev
	 */
	//TODO: shall we give a separated constructor and init function for both FTDI_Device and FTDI_Interface?? Think!
	public FTDI_Device(Context context, UsbDevice dev)
	{
		//Write down the caller Activity or Service's context
		mContext = context;
	}
	
	/**
	 * Inits the FTDI_Device class according to the device information given by dev.
	 *
	 * @param dev the dev
	 * @return the int
	 */
	public int initDevice(UsbDevice dev)
	{
		//check VID is the FTDI VID
		if(dev.getVendorId() != FTDI_Constants.VID_FTDI)
		{
			Log.e(TAG, "Failed to recognize the Vendor ID: " + Integer.toString(dev.getVendorId()) + " on UsbDevice"+dev.toString());
			return -1;
		}
		
		//PID, see if this is a valid FTDI device, and decide the chip type
		int expectedNumOfInterfaces = 0;
		
		switch(dev.getProductId())
		{
		case FTDI_Constants.PID_FT232B_FT245B_FT232R_FT245R:
			expectedNumOfInterfaces = 1;
			break;
			
		case FTDI_Constants.PID_FT232H:
			expectedNumOfInterfaces = 1;
			break;
			
		case FTDI_Constants.PID_FT2232C_FT2232D_FT2232L_FT2232H:
			expectedNumOfInterfaces = 2;
			break;
			
		case FTDI_Constants.PID_FT4232H:
			expectedNumOfInterfaces = 4;
			break;
			
		default:
			Log.e(TAG, "Failed to recognize the Product ID:"+Integer.toString(dev.getProductId())+" on UsbDevice"+dev.toString());
			return -1;
		}
		
		//check if the device really have that many interfaces:
		if(dev.getInterfaceCount() != expectedNumOfInterfaces)
		{
			Log.e(TAG, "Expected "+Integer.toString(expectedNumOfInterfaces)+" Interfaces, but only find "
					+Integer.toString(dev.getInterfaceCount())+" interfaces on UsbDevice: "+dev.toString());
			return -1;
		}
		
		//TODO: think how to initialize the interfaces...
		
		//After checking and making sure this is a FTDI chip, do the actual initialization of this class
		mUsbDevice = dev;
		
		//Initialize the correct numbers of interfaces
		
		//Initialize the EEPROM
		
		return 0;//TODO: revise returnvalue, detailed implementation
	}
	/**
	 * Gets the interfaces.
	 *
	 * @return the interfaces
	 */
	public FTDI_Interface[] getInterfaces()
	{
		return mFTDI_Interfaces;
	}
	
	//==================================================================
	//	x. methods that perform FTDI chip control over USB
	//==================================================================
	
	//openDevice, closeDevice, resetDevice
	public int openDevice()
	{
		UsbManager mManager = (UsbManager)mContext.getSystemService(Context.USB_SERVICE);
		//check if we have the permission to this device
		if(!mManager.hasPermission(mUsbDevice))
		{
			//write the permission problem to Android log, and return -1
			Log.e(TAG,"Permission denied when opening the device:"+mUsbDevice.toString());
			return -1;
		}
		
		mUsbDeviceConnection = mManager.openDevice(mUsbDevice);
		//TODO: consider if we need to reset the device, purge TX/RX buffer.
		if(resetDevice()!=0)
		{
			Log.e(TAG,"Failed to reset device:"+mUsbDevice.toString());
			return -1;
		}
		if(usbPurgeRXBuffer()!=0)
		{
			Log.e(TAG,"Failed to purge RX buffer on device:"+mUsbDevice.toString());
			return -1;
		}
		if(usbPurgeTXBuffer()!=0)
		{
			Log.e(TAG,"Failed to purge TX buffer on device:"+mUsbDevice.toString());
			return -1;
		}
		//arrive here then everything is fine.
		Log.d(TAG,"USB device opened:"+mUsbDevice.toString());
		return 0;
	}
	
	public void closeDevice()
	{
		mUsbDeviceConnection.close();
		mUsbDeviceConnection = null;
	}
	
	//TODO: decide if we shall define these 3 resetting functions as private.
	private int resetDevice()
	{
		return mUsbDeviceConnection.controlTransfer(FTDI_Constants.FTDI_DEVICE_OUT_REQTYPE, FTDI_Constants.SIO_RESET_REQUEST, 
				FTDI_Constants.SIO_RESET_SIO, FTDI_Constants.INTERFACE_ANY, null, 0, mWriteTimeout);
		//TODO: I give it a INTERFACE_ANY as parameter. Need to verify if it is correct. I believe the Index doesn't matter.
	}
	
	//usbPurgeRXBuffer, usbPurgeTXBuffer, Hmm... maybe this shall be defined as private functions just for internal use only?
	private int usbPurgeRXBuffer()
	{
		return mUsbDeviceConnection.controlTransfer(FTDI_Constants.FTDI_DEVICE_OUT_REQTYPE, FTDI_Constants.SIO_RESET_PURGE_RX,
				FTDI_Constants.SIO_RESET_SIO, FTDI_Constants.INTERFACE_ANY, null, 0, mWriteTimeout);
		//TODO: I give it a INTERFACE_ANY as parameter. Need to verify if it is correct. I believe the Index doesn't matter.
	}
	private int usbPurgeTXBuffer()
	{
		return mUsbDeviceConnection.controlTransfer(FTDI_Constants.FTDI_DEVICE_OUT_REQTYPE, FTDI_Constants.SIO_RESET_PURGE_TX, 
				FTDI_Constants.SIO_RESET_SIO, FTDI_Constants.INTERFACE_ANY, null, 0, mWriteTimeout);
		//TODO: I give it a INTERFACE_ANY as parameter. Need to verify if it is correct. I believe the Index doesn't matter.
	}
}
