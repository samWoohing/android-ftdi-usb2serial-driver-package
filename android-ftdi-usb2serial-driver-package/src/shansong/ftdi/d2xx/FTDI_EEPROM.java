/*
 * This is the code file that defines the class to represent the EEPROM attached to a FTDI device.
 * All EEPROM related data and functions are defined here.
 */
package shansong.ftdi.d2xx;

import android.hardware.usb.UsbDeviceConnection;
import android.util.Log;

/**
 * The Class FTDI_EEPROM.
 */
public class FTDI_EEPROM {
	
	//==================================================================
	//	1. member objects
	//==================================================================
	/** The Constant TAG string used for incident logging. */
	private static final String TAG = "FTDI_EEPROM";
	
	/** The usb device connection. */
	UsbDeviceConnection mUsbDeviceConnection;
	
	/** The read timeout. */
	private int mReadTimeout	= 5000;
	
	/** The write timeout. */
	private int mWriteTimeout	= 5000;
	//==================================================================
	//	2. The section of member methods
	//==================================================================
	//Constructor
	FTDI_EEPROM()
	{
		//TODO: later, decide if this class need to keep a UsbDevice member.
	}
	
	//Setter and getter function
	/**
	 * Sets the usb device connection.  This method shall be used by FTDI_Device when open the device.
	 *
	 * @param newUsbDeviceConnection the new usb device connection
	 */
	protected void setUsbDeviceConnection(UsbDeviceConnection newUsbDeviceConnection)
	{
		mUsbDeviceConnection = newUsbDeviceConnection;
	}
	
	//readEEPROM, writeEEPROM, eraseEEPROM
	//TODO: it seems that FTDI chip read/write is always 2-byte wise. Let's verify this later.
	/**
	 * Read eeprom.
	 *
	 * @return the int
	 */
	public byte[] readEEPROM()
	{
		return null;//TODO: detailed implementation. Just call readEEPROMLocation multiple times.
	}
	
	/**
	 * Write eeprom.
	 *
	 * @return the int
	 */
	public int writeEEPROM(byte[] eepromContent)
	{
		return 0;//TODO: detailed implementation. Just call writeEEPROMLocation multiple times.
	}
	
	/**
	 * Erase eeprom.
	 *
	 * @return 0: everything is OK.
	 * @return -1: USB controlTransfer failed.
	 */
	public int eraseEEPROM()
	{
		int r;
		if((r = mUsbDeviceConnection.controlTransfer(FTDI_Constants.FTDI_DEVICE_OUT_REQTYPE, FTDI_Constants.SIO_ERASE_EEPROM_REQUEST, 
						0, 0, null, 0, mWriteTimeout)) != 0)
		{
			Log.e(TAG,"USB controlTransfer operation failed. controlTransfer return value is:"+Integer.toString(r));
			return -1;
		}
		else
			return 0;
	}
	
	//readChipID
	
	//readEEPROMLocation, writeEEPROMLocation
	/**
	 * Read 2-byte data from a given eeprom location.
	 *
	 * @param addr: the address that we'd like to read data from.
	 * @return 0 or positive: the lower 2 bytes includes the data read from EEPROM. Higher 2 bytes are always 0.
	 * @return -1: Usb controlTransfer function failed. 
	 */
	public int readEEPROMLocation(int addr)
	{
		byte[] buf = new byte[2];
		int r;
		if((r = mUsbDeviceConnection.controlTransfer(FTDI_Constants.FTDI_DEVICE_IN_REQTYPE, FTDI_Constants.SIO_READ_EEPROM_REQUEST, 
						0, addr, buf, 2, mReadTimeout)) != 2)
		{
			Log.e(TAG,"USB controlTransfer operation failed. controlTransfer return value is:"+Integer.toString(r));
			return -1;
		}
		else
		{	//combine the two byte's value into one. It should only take the lower 2 bytes of the returned int value.
			return ((int)buf[0] & 0xff) | (((int)buf[1] & 0xff)<<8);//TODO: verify this return value is non-negative.
		}
	}
	
	/**
	 * Write 2-byte data to a given eeprom location.
	 *
	 * @param addr: the address that we'd like to write data to
	 * @param value: the 2-byte value that we'd like to write to the given address.
	 * @return 0: Everything is OK.
	 * @return -1: Usb controlTransfer function failed.
	 */
	public int writeEEPROMLocation(int addr, short value)
	{
		int r;
		if((r = mUsbDeviceConnection.controlTransfer(FTDI_Constants.FTDI_DEVICE_OUT_REQTYPE, FTDI_Constants.SIO_WRITE_EEPROM_REQUEST,
						value, addr, null, 0, mWriteTimeout)) != 0)
		{
			Log.e(TAG,"USB controlTransfer operation failed. controlTransfer return value is:"+Integer.toString(r));
			return -1;
		}
		else
			return 0;
	}
	
	//TODO: i believe we also need some more EEPROM tooling functions here. like check the data format, convert from raw buffer data to structure, etc
}
