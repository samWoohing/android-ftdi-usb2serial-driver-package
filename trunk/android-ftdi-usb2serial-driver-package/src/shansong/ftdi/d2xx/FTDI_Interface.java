/*
 * This is the code file that defines the class that represents a interface on a FTDI chip.
 * All the hard-core usb operations to operate the usb-to-serial communications are defined here
 */
package shansong.ftdi.d2xx;

import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;

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
	private UsbInterface usbInterface;
	//I still think device connection shall belong to the FTDI_Device class,
	//but this FTDI_interface class need to know it anyway...
	/** The usb device connection. */
	private UsbDeviceConnection usbDeviceConnection;
	
	/** The endpoint used for reading data. */
	private UsbEndpoint epRead;
	
	/** The endpoint used for writing data. */
	private UsbEndpoint epWrite;
	
	/** The Usb time out setting. it'll be used by usbBulkTransfer or usbControlTransfer. 
	 * But the question is... shall we maintain different timeout value for each?? */
	private int UsbTimeOut;
	//TODO: decide if we need separated timeout value setting later.
	
	//==================================================================
	//	3. The section of member methods
	//==================================================================
	/**
	 * Instantiates a new fTD i_ interface.
	 *
	 * @param newUsbInterface: the usb interface that we'd like to create a FTDI_interface on. It shall be given by FTDI_Device.
	 */
	public FTDI_Interface(UsbInterface newUsbInterface)
	{
		usbInterface = newUsbInterface;
		
		//verify if this interface has 2 endpoints, one is reading and one is writing. If else, it's NOT a FTDI channel endpoint.
	}
	
	/**
	 * Sets the usb device connection.  This method shall be used by FTDI_Device when open the device.
	 *
	 * @param newUsbDeviceConnection: the new usb device connection that is created by FTDI_Device class when open the ftdi device.
	 */
	public void setUsbDeviceConnection(UsbDeviceConnection newUsbDeviceConnection)
	{
		usbDeviceConnection = newUsbDeviceConnection;
	}
	
	/**
	 * Clr usb device connection. This method shall be used by FTDI_Device when close the device.
	 */
	public void clrUsbDeviceConnection()
	{
		usbDeviceConnection = null;
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
