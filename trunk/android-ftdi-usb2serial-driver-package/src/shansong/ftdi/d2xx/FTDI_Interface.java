/*
 * This is the code file that defines the class that represents a interface on a FTDI chip.
 * All the hard-core usb operations to operate the usb-to-serial communications are defined here
 */
package shansong.ftdi.d2xx;

import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbConstants;

/**
 * The Class FTDI_Interface.
 */
public class FTDI_Interface {
	
	//==================================================================
	//	1. The section that defines the FTDI USB command for chip control
	//==================================================================
	
	//==================================================================
	//	2. The section of member objects
	//==================================================================
	//contains the usb interface as class member, but it shall be given by FTDI_Device
	/** The usb interface. */
	private UsbInterface mUsbInterface;
	//I still think device connection shall belong to the FTDI_Device class,
	//but this FTDI_interface class need to know it anyway...
	/** The usb device connection. */
	private UsbDeviceConnection mUsbDeviceConnection;
	
	/** The endpoint used for reading data. */
	private UsbEndpoint mEndpointRead;
	
	/** The endpoint used for writing data. */
	private UsbEndpoint mEndpointWrite;
	
	/** The Usb time out setting. it'll be used by usbBulkTransfer or usbControlTransfer. 
	 * But the question is... shall we maintain different timeout value for each?? */
	private int mUsbTimeOut;
	
	/** The class shall know which interface itself is, A, B, C or D?. */
	private int mInterface;
	
	/** The read timeout. */
	private int mReadTimeout	=5000;
	
	/** The write timeout. */
	private int mWriteTimeout	=5000;
	
	//==================================================================
	//	3. The section of member methods
	//==================================================================
	/**
	 * Instantiates a new fTD i_ interface.
	 *
	 * @param newUsbInterface: the usb interface that we'd like to create a FTDI_interface on. It shall be given by FTDI_Device.
	 */
	public FTDI_Interface()
	{
		//TODO: anything else
	}
	
	public int initInterface(UsbInterface newUsbInterface)
	{
		//verify if this interface has 2 endpoints, one is reading and one is writing. If else, it's NOT a FTDI channel endpoint.
		//give mInterface the proper value.
		
		//give mEndpointRead and mEndpointWrite the proper value.
		
		//finally the interface is OK.
		mUsbInterface = newUsbInterface;
		return 0;
	}
	/**
	 * Sets the usb device connection.  This method shall be used by FTDI_Device when open the device.
	 *
	 * @param newUsbDeviceConnection: the new usb device connection that is created by FTDI_Device class when open the ftdi device.
	 */
	public void setUsbDeviceConnection(UsbDeviceConnection newUsbDeviceConnection)
	{
		mUsbDeviceConnection = newUsbDeviceConnection;
	}
	
	/**
	 * Clr usb device connection. This method shall be used by FTDI_Device when close the device.
	 */
	public void clrUsbDeviceConnection()
	{
		mUsbDeviceConnection = null;
	}
	
	/**
	 * Recognize interface is A, B C or D by its endpoint type.
	 *
	 * @param newUsbInterface: the usb interface 
	 * @return INTERFACE_A, B, C or D. -1 if the endpoint is not recognized.
	 */
	private int recognizeInterfaceByEndpoint(UsbInterface newUsbInterface)
	{
		//verify only 2 endpoints on the interface
		int numOfEndpoint = newUsbInterface.getEndpointCount();
		if(numOfEndpoint!=2)
		{
			return -1;
		}
		
		//verify the 2 endpoints is one input and one output
		UsbEndpoint ep1 = newUsbInterface.getEndpoint(0);
		UsbEndpoint ep2 = newUsbInterface.getEndpoint(1);
		UsbEndpoint epIn, epOut;
		if(ep1.getDirection() == UsbConstants.USB_DIR_IN && ep2.getDirection() == UsbConstants.USB_DIR_OUT)
		{
			epIn = ep1;
			epOut = ep2;
		}
		else if(ep1.getDirection() == UsbConstants.USB_DIR_OUT && ep2.getDirection() == UsbConstants.USB_DIR_IN)
		{
			epIn = ep2;
			epOut = ep1;
		}
		else
		{
			return -1;
		}
		
		//check by endpoint address
		if(epIn.getAddress()==FTDI_Constants.INTERFACE_A_ENDPOINT_IN && epOut.getAddress()==FTDI_Constants.INTERFACE_A_ENDPOINT_OUT)
		{
			return FTDI_Constants.INTERFACE_A;
		}
		if(epIn.getAddress()==FTDI_Constants.INTERFACE_B_ENDPOINT_IN && epOut.getAddress()==FTDI_Constants.INTERFACE_B_ENDPOINT_OUT)
		{
			return FTDI_Constants.INTERFACE_B;
		}
		if(epIn.getAddress()==FTDI_Constants.INTERFACE_C_ENDPOINT_IN && epOut.getAddress()==FTDI_Constants.INTERFACE_C_ENDPOINT_OUT)
		{
			return FTDI_Constants.INTERFACE_C;
		}
		if(epIn.getAddress()==FTDI_Constants.INTERFACE_D_ENDPOINT_IN && epOut.getAddress()==FTDI_Constants.INTERFACE_D_ENDPOINT_OUT)
		{
			return FTDI_Constants.INTERFACE_D;
		}
		
		return -1;
	}
	//==================================================================
	//	3.x Serial port control and operation methods
	//==================================================================
	int readData(byte[] buffer, int length)
	{
		return 0;//TODO: detailed implementation
	}
	int writeData(byte[] buffer, int length)
	{
		return 0;//TODO: detailed implementation
	}
	//TODO: decide if we need a asynchronous write method. I think we shall provide one.
	
	//setBaudRate, setLineProperty, readPins, setDTR, setRTS, setDtrRts getModemStatus
	int setBaudRate(int baudrate)
	{
		return 0;//TODO: detailed implementation
	}
	
	int setLineProperty()
	{
		return 0;//TODO: revise input parameters. Detailed implementation
	}
	
	byte readPins()//Well, I do believe the method shall return the actual value directly.
	{
		return 0;//TODO: Detailed implementation. Shall return pin status in return value directly. Note that Byte shall be a referenced type!!
	}
	//setBitMode
	int setBitMode(byte bitmask, byte bitmode)
	{
		return 0;//TODO: revise input parameter definition. Detailed implementation
	}
	//setLatencyTimer, getLatencyTimer	
	int setLatencyTimer(byte latency)
	{
		return 0;//TODO: Detailed implementation
	}
	
	byte getLatencyTimer()//this shall return the actual value, rather than the result of usb operation method.
	{
		return 0;//TODO: detailed implementation
	}
	
	//setEventChar, setErrorChar
	int setEventChar(byte eventChar, byte enable)
	{
		return 0;//TODO: detailed implementation
	}
	int setErrorChar(byte errorChar, byte enable)
	{
		return 0;//TODO: detailed implementation
	}
}
