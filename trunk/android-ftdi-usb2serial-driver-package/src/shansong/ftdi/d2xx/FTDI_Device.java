/*
 * This is the class that represents a entire FTDI chip, or say, a FTDI device.
 * A FTDI device shall have one or multiple USB interfaces, and optionally, it shall have a EEPROM chip
 */
package shansong.ftdi.d2xx;

import android.hardware.usb.UsbDevice;

// TODO: Auto-generated Javadoc
/**
 * The Class FTDI_Device.
 *
 * @author 307004396
 */
public class FTDI_Device {

	//==================================================================
	//	1. USB command codes
	//==================================================================
	
	//==================================================================
	//	2. member objects
	//==================================================================
	/** The ftdi interfaces. FTDI device(chip) may have 1, 2 or 4 interfaces */
	private FTDI_Interface[] ftdiInterfaces;
	
	/** The ftdi eeprom. One FTDI device(chip) shall only have 1 eeprom*/
	private FTDI_EEPROM ftdiEEPROM;
	
	//==================================================================
	//	3. The section of member methods
	//==================================================================
	/**
	 * Instantiates a new fTD i_ device.
	 *
	 * @param dev the dev
	 */
	public FTDI_Device(UsbDevice dev)
	{
		
	}
	
	/**
	 * Gets the interfaces.
	 *
	 * @return the interfaces
	 */
	public FTDI_Interface[] getInterfaces()
	{
		return ftdiInterfaces;
	}
	
	//==================================================================
	//	x. methods that perform FTDI chip control over USB
	//==================================================================
	
	//openDevice, closeDevice, resetDevice
	void openDevice()
	{
		
	}
	
	void closeDevice()
	{
		
	}
	
	int resetDevice()
	{
		return 0;//TODO: detailed implementation. set reset command through USB control message
	}
	
	//usbPurgeRXBuffer, usbPurgeTXBuffer, Hmm... maybe this shall be defined as private functions just for internal use only?
	int usbPurgeRXBuffer()
	{
		return 0;//TODO: detailed implementation
	}
	int usbPurgeTXBuffer()
	{
		return 0;//TODO: detailed implementation
	}
}
