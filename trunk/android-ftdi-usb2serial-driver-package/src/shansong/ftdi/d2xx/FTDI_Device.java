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
 * @author Shan Song
 */
public class FTDI_Device{
	//TODO: Design the method to retrieve bcdDevice using standard USB request.
	
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
	 * TODO: revise the return value here
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
		//Step3: Decide the device type
		//////////////////////////////////////////////////		
		// get the device type here.
		int temp_dev_type;
		if ((temp_dev_type = decideDeviceType()) <0)
		{
			Log.e(TAG,"Cannot decide device type. decideDeviceType() failed.");
			return -1;
		}
		
		//After checking and making sure this is a FTDI chip, do the actual initialization of this class
		mDeviceType = temp_dev_type;
		mUsbDevice = dev;
		mFTDI_EEPROM = new FTDI_EEPROM();
		
		return 0;
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
		for(int i=0; i < mFTDI_Interfaces.length; i++)
		{
			mFTDI_Interfaces[i].setUsbDeviceConnection(mUsbDeviceConnection);
		}
		mFTDI_EEPROM.setUsbDeviceConnection(mUsbDeviceConnection);
		
		//Reset and initialize the device to a known state.
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
		
		//TODO: also need to keep sync with FTDI_Interfaces' baud rate, bit mode, etc.
		
		//arrive here then everything is fine.
		Log.d(TAG,"USB device opened:"+mUsbDevice.toString());
		return 0;
	}
	
	public void closeDevice()
	{
		mUsbDeviceConnection.close();
		mUsbDeviceConnection = null;
		//Also the device connection kept by mFTDI_Interfaces are not valid any more.
		for(int i=0; i < mFTDI_Interfaces.length; i++)
		{
			mFTDI_Interfaces[i].setUsbDeviceConnection(null);
		}
		//And mFTDI_EEPROM's mUsbDeviceConnection needs to be cleared.
		mFTDI_EEPROM.clrUsbDeviceConnection();
		//TODO: decide if there's more cleaning up jobs to do when closing the device.
	}
	
	/**
	 * Find all ftdi devices connected to USB host, and return them in an array
	 *
	 * @return array that includes all USB devices that are recognized as FTDI devices.
	 */
	public UsbDevice[] findAllFTDIDevices()
	{
		//use UsbManager function:
		//public HashMap<String, UsbDevice> getDeviceList () 
		//Returns a HashMap containing all USB devices currently attached. 
		//USB device name is the key for the returned HashMap. 
		//The result will be empty if no devices are attached, or if USB host mode is inactive or unsupported.
		
		//get all usb devce in a hashmap
		//go through the hashmap and 
		//TODO: detailed implementation.
		UsbManager mManager = (UsbManager)mContext.getSystemService(Context.USB_SERVICE);
		//mManager.getDeviceList().values().toArray();
		
		return null;
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
	
	//==================================================================
	//	3. The section of privately used methods
	//==================================================================
	/*Special definitions ONLY used for FTDI_Device.getbcdDevice*/
    protected static final int STD_USB_REQUEST_GET_DESCRIPTOR = 0x06;//This is a standard usb request. Used ONLY in FTDI_Device.getbcdDevice().
	protected static final int STD_USB_DT_DEVICE = 0x01;//Description type: device
	protected static final int STD_USB_DT_DEVICE_LEN = 18;//standard device descriptor length is 18
	protected static final int STD_USB_BCDDEVICE_OFFSET=12;//offset of bcdDevice in the entire descriptor.
	protected static final int STD_USB_ISERIALNUMBER_OFFSET=16;//offset of bcdDevice in the entire descriptor.
	/**
	 * Gets the entire device descriptor. We need bcdDevice and iSerialNum to decide the chip type, but android does not give this function interface.
	 * So let's do it by ourselves, by sending standard USB controlTransfer command.
	 *
	 * @return the buffer that contains the DeviceDescriptor.
	 * @return null:if anything goes wrong
	 */
	private byte[] getDeviceDescriptor()
	{
		//This is how libusb implement it:
	//static inline int libusb_get_descriptor(libusb_device_handle *dev,
	//		uint8_t desc_type, uint8_t desc_index, unsigned char *data, int length)
	//{
	//	return libusb_control_transfer(dev, LIBUSB_ENDPOINT_IN,
	//			LIBUSB_REQUEST_GET_DESCRIPTOR, (desc_type << 8) | desc_index, 0, data,
	//			length, 1000);
	//}
		//Note: in above code:
		//		LIBUSB_ENDPOINT_IN = UsbConstants.USB_DIR_IN = 0x80.
		// 		LIBUSB_REQUEST_GET_DESCRIPTOR = 0x06 is the standard usb request to get descriptor.
		//		desc_type is the descriptor type, refer to libusb's "libusb_descriptor_type" for more details. 
		//			In this case, we need to get device descriptor, so desc_type = LIBUSB_DT_DEVICE = 0x01
		//		desc_index, since for usb device descriptor, there's only one. so I believe desc_index=0 is the only choice.
		
		UsbManager mManager = (UsbManager)mContext.getSystemService(Context.USB_SERVICE);
		//check if we have the permission to this device
		if(!mManager.hasPermission(mUsbDevice))
		{
			//write the permission problem to Android log, and return -1
			Log.e(TAG,"Permission denied when opening the device:"+mUsbDevice.toString());
			return null;
		}
		//use a temporary connection to open the device
		UsbDeviceConnection tempConn = mManager.openDevice(mUsbDevice);
		if(tempConn == null)
		{
			Log.e(TAG,"Cannot open the device:"+mUsbDevice.toString());
			return null;
		}

		//Device descriptor total size is 18 byte. So prepare a 18 byte array
		byte[] buf = new byte[STD_USB_DT_DEVICE_LEN];
		int r;
		
		if ((r = tempConn.controlTransfer(UsbConstants.USB_DIR_IN, STD_USB_REQUEST_GET_DESCRIPTOR, 
				(STD_USB_DT_DEVICE << 8), 0, buf, STD_USB_DT_DEVICE_LEN, mReadTimeout)) 
				!= STD_USB_DT_DEVICE_LEN)
		{
			Log.e(TAG,"USB controlTransfer operation failed. controlTransfer return value is:"+Integer.toString(r));
			tempConn.close();
			return null;
		}
		
		//always remember to close the temporary connection
		tempConn.close();
		return buf;
	}
	
	/**
	 * Gets the bcdDevice from a raw device descriptor retrieved by getDeviceDescriptor.
	 *
	 * @param dev_desc the device descriptor retrieved by getDeviceDescriptor.
	 * @return the bcdDevice from device descriptor
	 */
	private int getBcdDeviceFromDeviceDescriptor(byte[] dev_desc)
	{
		return ((int)dev_desc[STD_USB_BCDDEVICE_OFFSET] & 0xff) |
				(((int)dev_desc[STD_USB_BCDDEVICE_OFFSET+1] & 0xff)<< 8);
	}
	
	/**
	 * Gets the iSerialNumber from a raw device descriptor retrieved by getDeviceDescriptor.
	 *
	 * @param dev_desc the device descriptor retrieved by getDeviceDescriptor.
	 * @return the iSerialNumber from device descriptor
	 */
	private int getiSerialNumberFromDeviceDescriptor(byte[] dev_desc)
	{
		return ((int)dev_desc[STD_USB_ISERIALNUMBER_OFFSET] & 0xff);
	}
	
	//TODO: now it's time for us to define the data representation for device types. Check the device types defined in FTDI_Constants.
	//		Make the definition simple and tidy pls!!!
	/**
	 * Decide device type by reading bcdDevice and iSerialNumber.
	 *
	 * @return 0 or positive: the device type. The value can only be DEVICE_TYPE_AM,DEVICE_TYPE_BM,
	 * 							DEVICE_TYPE_2232C,DEVICE_TYPE_R,DEVICE_TYPE_2232H,DEVICE_TYPE_4232H	
	 * @return -1: something is wrong.
	 */
	private int decideDeviceType()
	{
		byte[] dev_desc = getDeviceDescriptor();
		if(dev_desc == null)
		{
			return -1;
		}
		
		int bcdDevice = getBcdDeviceFromDeviceDescriptor(dev_desc);
		int iSerialNumber = getiSerialNumberFromDeviceDescriptor(dev_desc);
		// Try to guess chip type
	    // Bug in the BM type chips: bcdDevice is 0x200 for serial == 0
	    if (bcdDevice == 0x400 || (bcdDevice == 0x200
	            && iSerialNumber == 0))
	    	return FTDI_Constants.DEVICE_TYPE_BM;
	    else if (bcdDevice == 0x200)
	    	return FTDI_Constants.DEVICE_TYPE_AM;
	    else if (bcdDevice == 0x500)
	    	return FTDI_Constants.DEVICE_TYPE_2232C;
	    else if (bcdDevice == 0x600)
	    	return FTDI_Constants.DEVICE_TYPE_R;
	    else if (bcdDevice == 0x700)
	    	return FTDI_Constants.DEVICE_TYPE_2232H;
	    else if (bcdDevice == 0x800)
	        return FTDI_Constants.DEVICE_TYPE_4232H;
	    else
	    	return -1;//If cannot decide, return -1 for marking error
	        
	}
	
	/**
	 * Checks if a given UsbDevice is a FTDI usb-to-serial device.
	 *
	 * @param dev: the UsbDevice we'd like to check.
	 * @return true, if it is a FTDI USB-to-serial device supported by this library. false if not.
	 */
	private boolean is_FTDI_USB_to_Serial_Device(UsbDevice dev)
	{
		//check VID, 
		//check PID,
		//check bcdDevice, I... don't think we need to go this far...
		//TODO: detailed implementation.
		return false;
	}
}
