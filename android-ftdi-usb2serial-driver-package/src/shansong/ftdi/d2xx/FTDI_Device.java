/*
 * This is the class that represents a entire FTDI chip, or say, a FTDI device.
 * A FTDI device shall have one or multiple USB interfaces, and optionally, it shall have a EEPROM chip
 */
package shansong.ftdi.d2xx;

import android.content.Context;
import android.util.Log;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.hardware.usb.UsbConstants;

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
	
	/** The device type defined by FTDI VID and Device release number. */
	private int mDeviceType = 0;
	//==================================================================
	//	3. The section of member methods
	//==================================================================
	/**
	 * Instantiates a new FTDI_Device.
	 *
	 * @param dev the dev
	 */
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
		//////////////////////////////////////////////////
		//Step1: check VID is the FTDI VID. Decide the device type from VID. And design how many interfaces the device should have
		//////////////////////////////////////////////////
		//VID check
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
		
		//////////////////////////////////////////////////
		//Step2: Initialize the FTDI device Interfaces, recognize the interfaces and write information into mFTDI_Interfaces[]
		//////////////////////////////////////////////////
		
		//check if the device really have that many interfaces:
		if(dev.getInterfaceCount() != expectedNumOfInterfaces)
		{
			Log.e(TAG, "Expected "+Integer.toString(expectedNumOfInterfaces)+" Interfaces, but only find "
					+Integer.toString(dev.getInterfaceCount())+" interfaces on UsbDevice: "+dev.toString());
			return -1;
		}
		
		//Initialize the mFTDI_Interfaces
		mFTDI_Interfaces = new FTDI_Interface[expectedNumOfInterfaces];
		UsbInterface tempUsbInterface = null;
		FTDI_Interface tempFTDI_Interface = null;
		for(int i=0; i<expectedNumOfInterfaces; i++)
		{
			tempUsbInterface = dev.getInterface(i);
			tempFTDI_Interface = new FTDI_Interface(this);
			if(tempFTDI_Interface.initInterface(tempUsbInterface) < 0)
			{
				//Initialization of this FTDI_Interface failed. For some reason, 
				//this UsbInterface cannot be recognized as a FTDI_Interface.
				Log.e(TAG,"Cannot recogonize the UsbInterface: "+tempUsbInterface.toString()+" as FTDI_Interface.");
				return -1;
			}
			
			//Now the tempFTDI_Interface is recognized as a valid FTDI_Interface.
			//We check which channel it is, and put it in the correct location of mFTDI_Interface array.
			switch(tempFTDI_Interface.getWhichInterfaceThisIs())
			{
			case FTDI_Constants.INTERFACE_A:
				if(mFTDI_Interfaces.length < FTDI_Constants.INTERFACE_A)
				{
					Log.e(TAG,"mFTDI_Interfaces has insufficient length. Current length is: "+Integer.toString(mFTDI_Interfaces.length)+ 
							", but desired length is:"+Integer.toString(FTDI_Constants.INTERFACE_A));
					return -1;
				}
				mFTDI_Interfaces[0]=tempFTDI_Interface;
				break;
				
			case FTDI_Constants.INTERFACE_B:
				if(mFTDI_Interfaces.length < FTDI_Constants.INTERFACE_B)
				{
					Log.e(TAG,"mFTDI_Interfaces has insufficient length. Current length is: "+Integer.toString(mFTDI_Interfaces.length)+ 
							", but desired length is:"+Integer.toString(FTDI_Constants.INTERFACE_B));
					return -1;
				}
				mFTDI_Interfaces[1]=tempFTDI_Interface;
				break;
				
			case FTDI_Constants.INTERFACE_C:
				if(mFTDI_Interfaces.length < FTDI_Constants.INTERFACE_C)
				{
					Log.e(TAG,"mFTDI_Interfaces has insufficient length. Current length is: "+Integer.toString(mFTDI_Interfaces.length)+ 
							", but desired length is:"+Integer.toString(FTDI_Constants.INTERFACE_C));
					return -1;
				}
				mFTDI_Interfaces[2]=tempFTDI_Interface;
				break;
				
			case FTDI_Constants.INTERFACE_D:
				if(mFTDI_Interfaces.length < FTDI_Constants.INTERFACE_D)
				{
					Log.e(TAG,"mFTDI_Interfaces has insufficient length. Current length is: "+Integer.toString(mFTDI_Interfaces.length)+ 
							", but desired length is:"+Integer.toString(FTDI_Constants.INTERFACE_D));
					return -1;
				}
				mFTDI_Interfaces[3]=tempFTDI_Interface;
				break;
			default:
				Log.e(TAG,"Unrecognized FTDI device port index:"+Integer.toString(tempFTDI_Interface.getWhichInterfaceThisIs()));
				return -1;
			}
		}
		
		//////////////////////////////////////////////////
		//Step3: Initialize the EEPROM
		//////////////////////////////////////////////////
		//TODO: design the EEPROM initialization here.
		
		//After checking and making sure this is a FTDI chip, do the actual initialization of this class
		mUsbDevice = dev;
		
		return 0;//TODO: revise return value, detailed implementation
	}
	
	/**
	 * Gets the interfaces.
	 *
	 * @param whichFTDI_Interface: should be FTDI_Constants.INTERFACE_A, B, C, or D.
	 * @return the corresponding interface. If required interfaces doesn't exist: 
	 * 			e.g. only have interface A and B but request interface D, the function returns null.
	 */
	public FTDI_Interface getInterfaces(int whichFTDI_Interface)
	{
		if(mFTDI_Interfaces.length<whichFTDI_Interface)
		{	//TODO: shall we generate any log.d here?
			return null;
		}
		switch(whichFTDI_Interface)
		{
		case FTDI_Constants.INTERFACE_A:
			return mFTDI_Interfaces[0];
		case FTDI_Constants.INTERFACE_B:
			return mFTDI_Interfaces[1];
		case FTDI_Constants.INTERFACE_C:
			return mFTDI_Interfaces[2];
		case FTDI_Constants.INTERFACE_D:
			return mFTDI_Interfaces[3];
		default:
			return null;
		}
	}
	
	/**
	 * Gets the device type.
	 *
	 * @return the device type
	 */
	public int getDeviceType()
	{
		return mDeviceType;
	}
	
	/**
	 * Sets the device type.
	 *
	 * @param newDeviceType the new device type
	 */
	protected void setDeviceType(int newDeviceType)
	{
		mDeviceType = newDeviceType;
	}
	
	//==================================================================
	//	x. methods that perform FTDI chip control over USB
	//==================================================================
	
	//openDevice, closeDevice, resetDevice
	public int openDevice()
	{
		//TODO: decide if we need a check if device is initialized or not.
		
		UsbManager mManager = (UsbManager)mContext.getSystemService(Context.USB_SERVICE);
		//check if we have the permission to this device
		if(!mManager.hasPermission(mUsbDevice))
		{
			//write the permission problem to Android log, and return -1
			Log.e(TAG,"Permission denied when opening the device:"+mUsbDevice.toString());
			return -1;
		}
		
		mUsbDeviceConnection = mManager.openDevice(mUsbDevice);
		
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
		//TODO: decide if there's more cleaning up jobs to do when closing the device.
	}
	
	//TODO: resetDevice, usbPurgeRXBuffer, usbPurgeTXBuffer, Hmm... maybe this shall be defined as private functions just for internal use only?
	private int resetDevice()
	{
		return mUsbDeviceConnection.controlTransfer(FTDI_Constants.FTDI_DEVICE_OUT_REQTYPE, FTDI_Constants.SIO_RESET_REQUEST, 
				FTDI_Constants.SIO_RESET_SIO, FTDI_Constants.INTERFACE_ANY, null, 0, mWriteTimeout);
		//TODO: I give it a INTERFACE_ANY as parameter. Need to verify if it is correct. I believe the Index doesn't matter.
	}
	
	
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
