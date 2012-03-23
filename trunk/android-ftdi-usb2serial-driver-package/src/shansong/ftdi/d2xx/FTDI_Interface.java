/*
 * This is the code file that defines the class that represents a interface on a FTDI chip.
 * All the hard-core usb operations to operate the usb-to-serial communications are defined here
 */
package shansong.ftdi.d2xx;

import android.hardware.usb.UsbInterface;
import android.util.Log;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbConstants;

// TODO: Auto-generated Javadoc
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
	
	/** The Constant TAG string used for incident logging. */
	private static final String TAG = "FTDI_Interface";
	
	/** The usb interface. */
	private UsbInterface mUsbInterface;
	//I still think device connection shall belong to the FTDI_Device class,
	//but this FTDI_interface class need to know it anyway...
	/** The usb device connection. */
	private UsbDeviceConnection mUsbDeviceConnection;
	
	/** The endpoint used for reading data. */
	private UsbEndpoint mEndpointIn;
	
	/** The endpoint used for writing data. */
	private UsbEndpoint mEndpointOut;
	
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
	 */
	protected FTDI_Interface()
	{
		//TODO: anything else
	}
	
	/**
	 * Inits the interface instance according to the given newUsbInterface.
	 * Recognize interface is A, B C or D by its endpoint type.
	 * If checking shows OK, assign the recognized endpoints
	 * to member endpoints mEndpointIn and mEndpointOut.
	 *
	 * @param newUsbInterface the new usb interface
	 * @return 0 if everything is OK. -1 if anything is wrong.
	 */
	protected int initInterface(UsbInterface newUsbInterface)
	{
		//verify only 2 endpoints on the interface
		if(newUsbInterface.getEndpointCount()!=2)
		{
			Log.e(TAG, "Total number of endpoints does not equal to 2 on UsbInterface: "+newUsbInterface.toString());
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
			Log.e(TAG, "The 2 endpoints are not 1 input and 1 output on UsbInterface: "+newUsbInterface.toString());
			return -1;
		}
		
		//check by endpoint address
		if(epIn.getAddress()==FTDI_Constants.INTERFACE_A_ENDPOINT_IN && epOut.getAddress()==FTDI_Constants.INTERFACE_A_ENDPOINT_OUT)
		{
			mUsbInterface = newUsbInterface;
			mEndpointIn = epIn;
			mEndpointOut = epOut;
			mInterface = FTDI_Constants.INTERFACE_A;
			return 0;
		}
		if(epIn.getAddress()==FTDI_Constants.INTERFACE_B_ENDPOINT_IN && epOut.getAddress()==FTDI_Constants.INTERFACE_B_ENDPOINT_OUT)
		{
			mUsbInterface = newUsbInterface;
			mEndpointIn = epIn;
			mEndpointOut = epOut;
			mInterface = FTDI_Constants.INTERFACE_B;
			return 0;
		}
		if(epIn.getAddress()==FTDI_Constants.INTERFACE_C_ENDPOINT_IN && epOut.getAddress()==FTDI_Constants.INTERFACE_C_ENDPOINT_OUT)
		{
			mUsbInterface = newUsbInterface;
			mEndpointIn = epIn;
			mEndpointOut = epOut;
			mInterface = FTDI_Constants.INTERFACE_C;
			return 0;
		}
		if(epIn.getAddress()==FTDI_Constants.INTERFACE_D_ENDPOINT_IN && epOut.getAddress()==FTDI_Constants.INTERFACE_D_ENDPOINT_OUT)
		{
			mUsbInterface = newUsbInterface;
			mEndpointIn = epIn;
			mEndpointOut = epOut;
			mInterface = FTDI_Constants.INTERFACE_D;
			return 0;
		}
		Log.e(TAG, "The 2 endpoints addresses are incorrect on UsbInterface: "+newUsbInterface.toString());
		return -1;
	}
	
	/**
	 * Sets the usb device connection.  This method shall be used by FTDI_Device when open the device.
	 *
	 * @param newUsbDeviceConnection the new usb device connection
	 */
	protected void setUsbDeviceConnection(UsbDeviceConnection newUsbDeviceConnection)
	{
		mUsbDeviceConnection = newUsbDeviceConnection;
	}
	
	/**
	 * Clr usb device connection. This method shall be used by FTDI_Device when close the device.
	 */
	protected void clrUsbDeviceConnection()
	{
		mUsbDeviceConnection = null;
	}
	
	/**
	 * Gets the which interface this is.
	 *
	 * @return the which interface this is
	 */
	public int getWhichInterfaceThisIs()
	{
		return mInterface;
	}
	//==================================================================
	//	3.x Serial port control and operation methods
	//==================================================================
	/**
	 * Read data.
	 *
	 * @param buffer the buffer
	 * @param length the length
	 * @return the int
	 */
	int readData(byte[] buffer, int length)
	{
		return mUsbDeviceConnection.bulkTransfer(mEndpointIn, buffer, length, mReadTimeout);//TODO: detailed implementation
	}
	
	/**
	 * Write data.
	 *
	 * @param buffer the buffer
	 * @param length the length
	 * @return the int
	 */
	int writeData(byte[] buffer, int length)
	{
		return mUsbDeviceConnection.bulkTransfer(mEndpointOut, buffer, length, mReadTimeout);//TODO: detailed implementation
	}
	//TODO: decide if we need a asynchronous write method. I think we shall provide one.
	
	//setBaudRate, setLineProperty, readPins, setDTR, setRTS, setDtrRts getModemStatus
	/**
	 * Sets the baud rate.
	 *
	 * @param baudrate the baudrate
	 * @return the int
	 */
	int setBaudRate(int baudrate)
	{
		return 0;//TODO: detailed implementation
	}
	
	/**
	 * Sets the line property, including num of databits, num of stop bits, parity, and break.
	 * The job is done by sending usb control message to FTDI device.
	 *
	 * @param data_bits_type the data_bits_type
	 * @param stop_bits_type the stop_bits_type
	 * @param parity_type the parity_type
	 * @param break_type the break_type
	 * @return integer value. 0 or positive value if successful. Return negative value if anything goes wrong.
	 */
	int setLineProperty(int data_bits_type, int stop_bits_type, int parity_type, int break_type)
	{
		//check the number of data bits is valid.
		switch(data_bits_type)//FTDI document D2xx programming guide says it only supports 7 or 8.
		{
		case FTDI_Constants.DATA_BITS_7:
		case FTDI_Constants.DATA_BITS_8:
			break;
		default:
			Log.e(TAG,"Cannot recognize the data bits setting: "+ Integer.toString(data_bits_type));
			return -1;
		}
		//check if the stop bits type is valid
		switch(stop_bits_type)
		{
		case FTDI_Constants.STOP_BITS_1:
		case FTDI_Constants.STOP_BITS_15:
		case FTDI_Constants.STOP_BITS_2:
			break;
		default:
			Log.e(TAG,"Cannot recognize the stop bits setting: "+ Integer.toString(stop_bits_type));
			return -1;
		}
		
		//check if the parity type is valid
		switch(parity_type)
		{
		case FTDI_Constants.PARITY_EVEN:
		case FTDI_Constants.PARITY_MARK:
		case FTDI_Constants.PARITY_NONE:
		case FTDI_Constants.PARITY_ODD:
		case FTDI_Constants.PARITY_SPACE:
			break;
		default:
			Log.e(TAG,"Cannot recognize the parity setting: "+ Integer.toString(parity_type));
			return -1;
		}
		
		//check if the break type is valid
		switch(break_type)
		{
		case FTDI_Constants.BREAK_OFF:
		case FTDI_Constants.BREAK_ON:
			break;
		default:
			Log.e(TAG,"Cannot recognize the break setting: "+ Integer.toString(break_type));
			return -1;
		}
		
		//if we run to here, then every setting is valid. Just throw it through usb control message.
		int combinedSetupValue = data_bits_type|(parity_type << 8)|(stop_bits_type << 11)|(break_type << 14);
		
		return mUsbDeviceConnection.controlTransfer(FTDI_Constants.FTDI_DEVICE_OUT_REQTYPE, FTDI_Constants.SIO_SET_DATA_REQUEST,
											combinedSetupValue, mInterface, null, 0, mWriteTimeout);
	}
	
	/**
	 * Sets the flow control.
	 *
	 * @param flow_ctrl_type the flow_ctrl_type
	 * @return integer value. 0 or positive if successful. Negative value if failed.
	 */
	int setFlowControl(int flow_ctrl_type)
	{
		//Only allow the pre-defined flow control type.
		switch(flow_ctrl_type)
		{
		case FTDI_Constants.SIO_DISABLE_FLOW_CTRL:
		case FTDI_Constants.SIO_RTS_CTS_HS:
		case FTDI_Constants.SIO_DTR_DSR_HS:
		case FTDI_Constants.SIO_XON_XOFF_HS:
			break;
		default:
			Log.e(TAG,"Cannot recognize the flow control type: "+ Integer.toString(flow_ctrl_type));
			return -1;
		}
		
		return mUsbDeviceConnection.controlTransfer(FTDI_Constants.FTDI_DEVICE_OUT_REQTYPE, FTDI_Constants.SIO_SET_FLOW_CTRL_REQUEST, 
										0, (flow_ctrl_type|mInterface), null, 0, mWriteTimeout);
	}
	
	/**
	 * Read pins.
	 *
	 * @return the byte
	 */
	byte readPins()//Well, I do believe the method shall return the actual value directly.
	{
		return 0;//TODO: Detailed implementation. Shall return pin status in return value directly. Note that Byte shall be a referenced type!!
	}
	
	/**
	 * Sets the bit mask bit mode.
	 *
	 * @param bitmask : Every bit marks the I/O configuration for GPIO pins. High/ON value configures the pin as output. LOW/OFF as input. ?? TODO: need to verify this!
	 * @param bitmode : set the bitbang mode. Must be MPSSE_BITMODE_RESET, MPSSE_BITMODE_BITBANG, 
	 * 					MPSSE_BITMODE_MPSSE, MPSSE_BITMODE_SYNCBB, MPSSE_BITMODE_MCU,MPSSE_BITMODE_OPTO
	 * 					MPSSE_BITMODE_CBUS, or MPSSE_BITMODE_SYNCFF
	 * @return the int
	 */
	int SetBitMaskBitMode(byte bitmask, byte bitmode)
	{
		switch(bitmode)
		{
		case FTDI_Constants.MPSSE_BITMODE_RESET:
		case FTDI_Constants.MPSSE_BITMODE_BITBANG:
		case FTDI_Constants.MPSSE_BITMODE_MPSSE:
		case FTDI_Constants.MPSSE_BITMODE_SYNCBB:
		case FTDI_Constants.MPSSE_BITMODE_MCU:
		case FTDI_Constants.MPSSE_BITMODE_OPTO:
		case FTDI_Constants.MPSSE_BITMODE_CBUS:
		case FTDI_Constants.MPSSE_BITMODE_SYNCFF:
			break;
		default:
			Log.e(TAG,"Cannot recognize the bit mode: "+ Integer.toString(bitmode));
			return -1;
		}
		
		int combinedSetupValue = (bitmode << 8) | bitmask;
		return mUsbDeviceConnection.controlTransfer(FTDI_Constants.FTDI_DEVICE_OUT_REQTYPE, FTDI_Constants.SIO_SET_BITMODE_REQUEST, 
										combinedSetupValue, mInterface, null, 0, mWriteTimeout);
		//TODO: I think we need to keep a record of the bitmode, and let upper level fucntions to poll the bitmode status.
	}
	
	//setBitMode
	/**
	 * Sets the bit mode.
	 *
	 * @param bitmask the bitmask
	 * @param bitmode the bitmode
	 * @return the int
	 */
	int setBitMode(byte bitmask, byte bitmode)
	{
		return 0;//TODO: revise input parameter definition. Detailed implementation
	}
	
	//setLatencyTimer, getLatencyTimer	
	/**
	 * Sets the latency timer.
	 *
	 * @param latency the latency
	 * @return the int
	 */
	int setLatencyTimer(byte latency)
	{
		return 0;//TODO: Detailed implementation
	}
	
	/**
	 * Gets the latency timer.
	 *
	 * @return the latency timer
	 */
	byte getLatencyTimer()//this shall return the actual value, rather than the result of usb operation method.
	{
		return 0;//TODO: detailed implementation
	}
	
	//setEventChar, setErrorChar
	/**
	 * Sets the event char.
	 *
	 * @param eventChar the event char
	 * @param enable the enable
	 * @return the int
	 */
	int setEventChar(byte eventChar, byte enable)
	{
		return 0;//TODO: detailed implementation
	}
	
	/**
	 * Sets the error char.
	 *
	 * @param errorChar the error char
	 * @param enable the enable
	 * @return the int
	 */
	int setErrorChar(byte errorChar, byte enable)
	{
		return 0;//TODO: detailed implementation
	}
}
