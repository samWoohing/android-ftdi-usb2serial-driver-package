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
	
	//define a convention for function return values.
	//Currently I suggest the following:
	//	-1: usb operation error in usb control transfer, or usb bulk transfer.
	//	-2: the given input parameter is not reasonable.
	//
	//==================================================================
	//	1. The section that defines the FTDI USB command for chip control
	//==================================================================
	
	//==================================================================
	//	2. The section of member objects
	//==================================================================
	//contains the usb interface as class member, but it shall be given by FTDI_Device
	
	/** The Constant TAG string used for incident logging. */
	private static final String TAG = "FTDI_Interface";
	
	/** The mFTDI_Device specifies which device this interface belongs to. */
	private FTDI_Device mFTDI_Device;
	
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
	
	/** The class shall know which interface itself is, A, B, C or D?. */
	private int mInterface;
	
	/** The read timeout. */
	private int mReadTimeout	=5000;
	
	/** The write timeout. */
	private int mWriteTimeout	=5000;
	
	/** The baud rate. This member is kept synchronized with FTDI chip's setting */
	private int mBaudRate = -1;
	
	/** The bit bang mode. This member is kept synchronized with FTDI chip's setting*/
	private int mBitMode = 0;
	//==================================================================
	//	3. The section of member methods
	//==================================================================
	/**
	 * Instantiates a new fTD i_ interface.
	 *
	 */
	protected FTDI_Interface(FTDI_Device newFTDI_Device)
	{
		mFTDI_Device = newFTDI_Device;
		//TODO: anything else?
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
			return -2;
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
			return -2;
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
		return -2;
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
	
	/**
	 * Sets the usb read timeout.
	 *
	 * @param newtimeout the new usb read timeout
	 */
	public void setUsbReadTimeout(int newtimeout)
	{
		mReadTimeout = newtimeout;
	}
	
	/**
	 * Gets the usb read timeout.
	 *
	 * @return the usb read timeout
	 */
	public int getUsbReadTimeout()
	{
		return mReadTimeout;
	}
	
	/**
	 * Sets the usb write timeout.
	 *
	 * @param newtimeout the new usb write timeout
	 */
	public void setUsbWriteTimeout(int newtimeout)
	{
		mWriteTimeout = newtimeout;
	}
	
	/**
	 * Gets the usb write timeout.
	 *
	 * @return the usb write timeout
	 */
	public int getUsbWriteTimeout()
	{
		return mWriteTimeout;
	}
	//==================================================================
	//	3.x Serial port control and operation methods
	//==================================================================
	//TODO: Need to find a proper function definition to place claimInterface and releaseInterface.
	public boolean claimInterface(boolean force)
	{
		//Things I believe we need to do here: 
		//	1. give a reset to the device (already handled by OpenDevice function)
		//	2. set bitmode to known state, should know its default setting after reset.
		//	3. set baudrate to known state, should know its default setting after reset
		//Question is: how do these sync with all other member methods here.
		return mUsbDeviceConnection.claimInterface(mUsbInterface, force);
		
	}
	
	public boolean releaseInterface()
	{
		return mUsbDeviceConnection.releaseInterface(mUsbInterface);
	}
	/**
	 * Read data.
	 * Note that this method does not strip the two modem status bits automatically.
	 * User has to handle this after calling this function.
	 *
	 * @param buffer the buffer
	 * @param length the length
	 * @return the return value of bulkTransfer method.
	 */
	public int readData(byte[] buffer, int length)
	{
		return mUsbDeviceConnection.bulkTransfer(mEndpointIn, buffer, length, mReadTimeout);//TODO: detailed implementation
	}
	
	/**
	 * Write data.
	 *
	 * @param buffer the buffer
	 * @param length the length
	 * @return the return value of bulkTransfer method.
	 */
	public int writeData(byte[] buffer, int length)
	{
		return mUsbDeviceConnection.bulkTransfer(mEndpointOut, buffer, length, mReadTimeout);//TODO: detailed implementation
	}
	//TODO: decide if we need a asynchronous write method. I think we shall provide one.
	
	//setBaudRate, setLineProperty, readPins, setDTR, setRTS, setDtrRts getModemStatus
	
	/**
	 * Convert baud rate. This function is for internal use only.
	 *
	 * @param baudrate the baudrate
	 * @param value_index: values that gives to usb control transfer. First int is "value", second is "index"
	 * 
	 * @return: the actual baud rate we get. The actual baud rate cannot always equals to the desired baud rate
	 * due to clock pre-scale stuff inside the FTDI chips.
	 */
	private int convertBaudRate(int baudrate, int[] value_index)
	{
	    final byte[] am_adjust_up = {0, 0, 0, 1, 0, 3, 2, 1};
	    final byte[] am_adjust_dn = {0, 0, 0, 1, 0, 1, 2, 3};
	    final byte[] frac_code = {0, 3, 2, 4, 1, 5, 6, 7};
	    int divisor, best_divisor, best_baud, best_baud_diff, value, index;
	    long encoded_divisor;
	    
	    int deviceType = mFTDI_Device.getDeviceType();

	    if (baudrate <= 0)
	    {
	        // Return error
	        return -1;
	    }

	    divisor = 24000000 / baudrate;

	    if (deviceType == FTDI_Constants.DEVICE_TYPE_AM)
	    {
	        // Round down to supported fraction (AM only)
	        divisor -= am_adjust_dn[divisor & 7];
	    }

	    // Try this divisor and the one above it (because division rounds down)
	    best_divisor = 0;
	    best_baud = 0;
	    best_baud_diff = 0;
	    for (int i = 0; i < 2; i++)
	    {
	        int try_divisor = divisor + i;
	        int baud_estimate;
	        int baud_diff;

	        // Round up to supported divisor value
	        if (try_divisor <= 8)
	        {
	            // Round up to minimum supported divisor
	            try_divisor = 8;
	        }
	        else if (deviceType != FTDI_Constants.DEVICE_TYPE_AM && try_divisor < 12)
	        {
	            // BM doesn't support divisors 9 through 11 inclusive
	            try_divisor = 12;
	        }
	        else if (divisor < 16)
	        {
	            // AM doesn't support divisors 9 through 15 inclusive
	            try_divisor = 16;
	        }
	        else
	        {
	            if (deviceType == FTDI_Constants.DEVICE_TYPE_AM)
	            {
	                // Round up to supported fraction (AM only)
	                try_divisor += am_adjust_up[try_divisor & 7];
	                if (try_divisor > 0x1FFF8)
	                {
	                    // Round down to maximum supported divisor value (for AM)
	                    try_divisor = 0x1FFF8;
	                }
	            }
	            else
	            {
	                if (try_divisor > 0x1FFFF)
	                {
	                    // Round down to maximum supported divisor value (for BM)
	                    try_divisor = 0x1FFFF;
	                }
	            }
	        }
	        // Get estimated baud rate (to nearest integer)
	        baud_estimate = (24000000 + (try_divisor / 2)) / try_divisor;
	        // Get absolute difference from requested baud rate
	        if (baud_estimate < baudrate)
	        {
	            baud_diff = baudrate - baud_estimate;
	        }
	        else
	        {
	            baud_diff = baud_estimate - baudrate;
	        }
	        if (i == 0 || baud_diff < best_baud_diff)
	        {
	            // Closest to requested baud rate so far
	            best_divisor = try_divisor;
	            best_baud = baud_estimate;
	            best_baud_diff = baud_diff;
	            if (baud_diff == 0)
	            {
	                // Spot on! No point trying
	                break;
	            }
	        }
	    }
	    // Encode the best divisor value
	    encoded_divisor = (best_divisor >> 3) | (frac_code[best_divisor & 7] << 14);
	    // Deal with special cases for encoded value
	    if (encoded_divisor == 1)
	    {
	        encoded_divisor = 0;    // 3000000 baud
	    }
	    else if (encoded_divisor == 0x4001)
	    {
	        encoded_divisor = 1;    // 2000000 baud (BM only)
	    }
	    // Split into "value" and "index" values
	    value = (short)(encoded_divisor & 0xFFFF);
	    if (deviceType == FTDI_Constants.DEVICE_TYPE_2232C || deviceType == FTDI_Constants.DEVICE_TYPE_2232H || deviceType == FTDI_Constants.DEVICE_TYPE_4232H)
	    {
	        index = (short)(encoded_divisor >> 8);
	        index &= 0xFF00;
	        index |= mInterface;
	    }
	    else
	    {
	        index = (short)(encoded_divisor >> 16);
	    }
	    value_index[0]=value;
	    value_index[1]=index;
	    // Return the nearest baud rate
	    return best_baud;
	}
	/**
	 * Sets the baud rate.
	 *
	 * @param baudrate the baudrate
	 * @return postive num: Everything is OK. num is the actual baud rate.
	 *  		-1: the usb controlTransfer function has failed.
	 *  		-2: Cannot implement the desired baud rate.
	 *  		-3: The error between implemented baud rate and desired baud rate >5 percent.
	 */
	public int setBaudRate(int baudrate)
	{
	    int value, index;
	    int actual_baudrate;
	    int[] value_index = {0,0};

	    //TODO: Verify if this works fine. the FTDI_Interface need to know if the bitbang mode is enabled or not.
	    //		Let's read more about FTDI hardware documents.
	    //		So far it seems set, as long as the bitmode is NOT MPSSE_BITMODE_RESET, it shall be classified as "bigbang_enabled"
	    if(this.isBitBangEnabled())
	    {
	    	baudrate = baudrate*4;
	    }
	    
	    actual_baudrate = convertBaudRate(baudrate, value_index);//this function will change the items in value_index.
	    value = value_index[0];
	    index = value_index[1];
	    
	    if (actual_baudrate <= 0)
	    {
	    	//write to log, this is a impossible baudrate
	    	Log.e(TAG,"The actual baud rate calculated as zero or negative value, impossible to implement: " + Integer.toString(actual_baudrate));
	    	return -2;
	    }

	    // Check within tolerance (about 5%)
	    if ((actual_baudrate * 2 < baudrate /* Catch overflows */ )
	            || ((actual_baudrate < baudrate) ? (actual_baudrate * 21 < baudrate * 20) : (baudrate * 21 < actual_baudrate * 20)))
	    {
	        //Unsupported baudrate. Note: bitbang baudrates are automatically multiplied by 4
	    	Log.e(TAG, "The actual baud rate: "+ Integer.toString(actual_baudrate)+" has >5% error from desired baud rate: "+ Integer.toString(baudrate));
	    	return -3;
	    }
	    
	    int r;
	    if(( r = mUsbDeviceConnection.controlTransfer(FTDI_Constants.FTDI_DEVICE_OUT_REQTYPE, FTDI_Constants.SIO_SET_BAUDRATE_REQUEST,
	    		value, index, null, 0, mWriteTimeout)) != 0)
	    {
	    	//write to log: setting new baudrate failed
	    	Log.e(TAG,"USB controlTransfer operation failed. controlTransfer return value is:"+Integer.toString(r));
	    	return -1;
	    }
	    else
	    {
		    //need to keep a record of what baudrate is setup.
	    	mBaudRate = actual_baudrate;
		    return actual_baudrate;
	    }
	}
	
	/**
	 * Gets the baud rate.
	 *
	 * @return the mBaudRate. The actual baud rate calculated and set when calling setBaudRate method.
	 */
	public int getBaudRate()
	{
		return mBaudRate;
	}
	
	static protected boolean validateDataBits(int data_bits_type)
	{
		switch(data_bits_type){//FTDI document D2xx programming guide says it only supports 7 or 8.
		case FTDI_Constants.DATA_BITS_7:
		case FTDI_Constants.DATA_BITS_8:
			return true;
		default:
			Log.e(TAG,"Cannot recognize the data bits setting: "+ Integer.toString(data_bits_type));
			return false;
		}
	}
	
	static protected boolean validateStopBits(int stop_bits_type)
	{
		switch(stop_bits_type){
		case FTDI_Constants.STOP_BITS_1:
		case FTDI_Constants.STOP_BITS_15:
		case FTDI_Constants.STOP_BITS_2:
			return true;
		default:
			Log.e(TAG,"Cannot recognize the stop bits setting: "+ Integer.toString(stop_bits_type));
			return false;
		}
	}
	
	static protected boolean validateParity(int parity_type)
	{
		switch(parity_type){
		case FTDI_Constants.PARITY_EVEN:
		case FTDI_Constants.PARITY_MARK:
		case FTDI_Constants.PARITY_NONE:
		case FTDI_Constants.PARITY_ODD:
		case FTDI_Constants.PARITY_SPACE:
			return true;
		default:
			Log.e(TAG,"Cannot recognize the parity setting: "+ Integer.toString(parity_type));
			return false;
		}
	}
	
	static protected boolean validateBreak(int break_type)
	{
		switch(break_type)
		{
		case FTDI_Constants.BREAK_OFF:
		case FTDI_Constants.BREAK_ON:
			return true;
		default:
			Log.e(TAG,"Cannot recognize the break setting: "+ Integer.toString(break_type));
			return false;
		}
	}
	static protected boolean validateFlowCtrl(int flow_ctrl_type)
	{
		switch(flow_ctrl_type){
		case FTDI_Constants.SIO_DISABLE_FLOW_CTRL:
		case FTDI_Constants.SIO_RTS_CTS_HS:
		case FTDI_Constants.SIO_DTR_DSR_HS:
		case FTDI_Constants.SIO_XON_XOFF_HS:
			return true;
		default:
			Log.e(TAG,"Cannot recognize the flow control type: "+ Integer.toString(flow_ctrl_type));
			return false;
		}
	}
	/**
	 * Sets the line property, including num of databits, num of stop bits, parity, and break.
	 * The job is done by sending usb control message to FTDI device.
	 *
	 * @param data_bits_type the data_bits_type
	 * @param stop_bits_type the stop_bits_type
	 * @param parity_type the parity_type
	 * @param break_type the break_type
	 * @return 0: everything is OK.
	 *  -1: USB controlTransfer method failed.
	 *  -2: one input parameter is not reasonable.
	 */
	public int setLineProperty(int data_bits_type, int stop_bits_type, int parity_type, int break_type)
	{
		//check the number of data bits is valid.
		if(!validateDataBits(data_bits_type)) return -2;
		//check if the stop bits type is valid
		if(!validateStopBits(stop_bits_type)) return -2;
		//check if the parity type is valid
		if(!validateParity(parity_type)) return -2;
		//check if the break type is valid
		if(!validateBreak(break_type))return -2;
		
		//if we run to here, then every setting is valid. Just throw it through usb control message.
		int combinedSetupValue = data_bits_type|(parity_type << 8)|(stop_bits_type << 11)|(break_type << 14);
		int r;
		if((r = mUsbDeviceConnection.controlTransfer(FTDI_Constants.FTDI_DEVICE_OUT_REQTYPE, FTDI_Constants.SIO_SET_DATA_REQUEST,
											combinedSetupValue, mInterface, null, 0, mWriteTimeout)) != 0)
		{
			Log.e(TAG,"USB controlTransfer operation failed. controlTransfer return value is:"+Integer.toString(r));
			return -1;
		}
		else
			return 0;
	}
	
	/**
	 * Sets the flow control.
	 *
	 * @param flow_ctrl_type the flow_ctrl_type
	 * @return 0: everything is OK.
	 *  -1: USB controlTransfer method failed.
	 *  -2: one input parameter is not reasonable.
	 */
	public int setFlowControl(int flow_ctrl_type)
	{
		//Only allow the pre-defined flow control type.
		if(!validateFlowCtrl(flow_ctrl_type))return -2;
		int r;
		if ((r = mUsbDeviceConnection.controlTransfer(FTDI_Constants.FTDI_DEVICE_OUT_REQTYPE, FTDI_Constants.SIO_SET_FLOW_CTRL_REQUEST, 
										0, (flow_ctrl_type|mInterface), null, 0, mWriteTimeout)) != 0)
		{
			Log.e(TAG,"USB controlTransfer operation failed. controlTransfer return value is:"+Integer.toString(r));
			return -1;
		}
		else
			return 0;
	}
	
	/**
	 * Read pins status from GPIO pins.
	 *
	 * @return 0 or positive value: The lower byte is the pin status. Can only be 0~255.
	 *  -1: negative value: The error value returned by usb control msg operation.
	 */
	public int readPins()//Well, I do believe the method shall return the actual value directly.
	{
		byte[] buf = new byte[1];
		int r = 0;
		r = mUsbDeviceConnection.controlTransfer(FTDI_Constants.FTDI_DEVICE_IN_REQTYPE, FTDI_Constants.SIO_READ_PINS_REQUEST,
								0, mInterface, buf, 1, mReadTimeout);
		if(r != 1)
		{
			Log.e(TAG,"USB controlTransfer operation failed. controlTransfer return value is:"+Integer.toString(r));
			return -1;
		}
		else
		{
			return ((int)buf[1]) & (int)0x000000ff;//TODO: verify and make sure this return value is non-negative.
		}
	}
	
	/**
	 * Gets the modem status.
	 * This function allows the retrieve the two status bytes of the device.
	 * The device sends these bytes also as a header for each read access
	 * where they are discarded by ftdi_read_data(). The chip generates
	 * the two stripped status bytes in the absence of data every 40 ms
	 * 
	 * Layout of the first byte:
	 * - B0..B3 - must be 0
	 * - B4       Clear to send (CTS)
	 * 		0 = inactive
	 * 		1 = active
	 * - B5       Data set ready (DTS)
	 * 		0 = inactive
	 * 		1 = active
	 * - B6       Ring indicator (RI)
	 * 		0 = inactive
	 * 		1 = active
	 * - B7       Receive line signal detect (RLSD)
	 * 		0 = inactive
	 * 		1 = active
	 * 
	 * Layout of the second byte:
	 * - B0       Data ready (DR)
	 * - B1       *Overrun error (OE)
	 * - B2       *Parity error (PE)
	 * - B3       *Framing error (FE)
	 * - B4       *Break interrupt (BI)
	 * - B5       Transmitter holding register (THRE)
	 * - B6       Transmitter empty (TEMT)
	 * - B7       Error in RCVR FIFO
	 *
	 * @return non-negative value: lower 2 bytes represents the modem status.
	 *  -1: USB controlTransfer operation failed.
	 */
	public int getModemStatus()
	{
		byte[] buf = new byte[2];
		int r = 0;
		r = mUsbDeviceConnection.controlTransfer(FTDI_Constants.FTDI_DEVICE_IN_REQTYPE, FTDI_Constants.SIO_READ_PINS_REQUEST,
								0, mInterface, buf, 2, mReadTimeout);
		if(r != 2)
		{
			Log.e(TAG,"USB controlTransfer operation failed. controlTransfer return value is:"+Integer.toString(r));
			return -1;
		}
		else
		{
			return ((int)buf[1]) | (buf[2]<<8);//TODO: verify if this return value is truely a non-negative value of Modem status.
		}
	}
	
	/**
	 * Sets the dtr.
	 *
	 * @param dtr: DTR setting. Can only be SIO_SET_DTR_HIGH or SIO_SET_DTR_LOW.
	 * @return 0: Everything is OK.
	 *  -1: USB controlTransfer method failed.
	 *  -2: input value cannot be recognized.
	 */
	public int setDTR(int dtr)
	{
		switch(dtr)
		{
		case FTDI_Constants.SIO_SET_DTR_HIGH:
		case FTDI_Constants.SIO_SET_DTR_LOW:
			break;
		default:
			Log.e(TAG,"The DTR value can only be SIO_SET_DTR_HIGH or SIO_SET_DTR_LOW: "+ Integer.toString(dtr));
			return -2;
		}
		int r;
		if((r = mUsbDeviceConnection.controlTransfer(FTDI_Constants.FTDI_DEVICE_OUT_REQTYPE, FTDI_Constants.SIO_SET_MODEM_CTRL_REQUEST,
									dtr, mInterface, null, 0, mWriteTimeout)) != 0)
		{
			Log.e(TAG,"USB controlTransfer operation failed. controlTransfer return value is:"+Integer.toString(r));
			return -1;
		}
		else
			return 0;
	}
	
	/**
	 * Sets the rts.
	 *
	 * @param rts: the RTS setting. Can only be SIO_SET_RTS_HIGH or SIO_SET_RTS_LOW.
	 * @return 0: Everything is OK.
	 *  -1: USB controlTransfer method failed.
	 *  -2: input value cannot be recognized.
	 */
	public int setRTS(int rts)
	{
		switch(rts)
		{
		case FTDI_Constants.SIO_SET_RTS_HIGH:
		case FTDI_Constants.SIO_SET_RTS_LOW:
			break;
		default:
			Log.e(TAG,"The RTS value can only be SIO_SET_RTS_HIGH or SIO_SET_RTS_LOW: "+ Integer.toString(rts));
			return -2;
		}
		int r;
		if((r = mUsbDeviceConnection.controlTransfer(FTDI_Constants.FTDI_DEVICE_OUT_REQTYPE, FTDI_Constants.SIO_SET_MODEM_CTRL_REQUEST,
									rts, mInterface, null, 0, mWriteTimeout)) != 0)
		{
			Log.e(TAG,"USB controlTransfer operation failed. controlTransfer return value is:"+Integer.toString(r));
			return -1;
		}
		else
			return 0;
	}
	
	/**
	 * Sets the dtr and rts in one single function.
	 *
	 * @param dtr: the DTR setting. Can only be SIO_SET_DTR_HIGH or SIO_SET_DTR_LOW.
	 * @param rts: the RTS setting. Can only be SIO_SET_RTS_HIGH or SIO_SET_RTS_LOW.
	 * @return 0: Everything is OK.
	 *  -1: USB controlTransfer method failed.
	 *  -2: input value cannot be recognized.
	 */
	public int setDTR_RTS(int dtr, int rts)
	{
		switch(dtr)
		{
		case FTDI_Constants.SIO_SET_DTR_HIGH:
		case FTDI_Constants.SIO_SET_DTR_LOW:
			break;
		default:
			Log.e(TAG,"The DTR value can only be SIO_SET_DTR_HIGH or SIO_SET_DTR_LOW: "+ Integer.toString(dtr));
			return -2;
		}
		switch(rts)
		{
		case FTDI_Constants.SIO_SET_RTS_HIGH:
		case FTDI_Constants.SIO_SET_RTS_LOW:
			break;
		default:
			Log.e(TAG,"The RTS value can only be SIO_SET_RTS_HIGH or SIO_SET_RTS_LOW: "+ Integer.toString(rts));
			return -2;
		}
		int r;
		if((r = mUsbDeviceConnection.controlTransfer(FTDI_Constants.FTDI_DEVICE_OUT_REQTYPE, FTDI_Constants.SIO_SET_MODEM_CTRL_REQUEST,
				(dtr|rts), mInterface, null, 0, mWriteTimeout)) != 0 )
		{
			Log.e(TAG,"USB controlTransfer operation failed. controlTransfer return value is:"+Integer.toString(r));
			return -1;
		}
		else
			return 0;
	}
	//setBitMode
	/**
	 * Sets the bit mask bit mode.
	 *
	 * @param bitmask : Every bit marks the I/O configuration for GPIO pins. High/ON value configures the pin as output. LOW/OFF as input. ?? TODO: need to verify this!
	 * @param bitmode : set the bitbang mode. Must be MPSSE_BITMODE_RESET, MPSSE_BITMODE_BITBANG, 
	 * 					MPSSE_BITMODE_MPSSE, MPSSE_BITMODE_SYNCBB, MPSSE_BITMODE_MCU,MPSSE_BITMODE_OPTO
	 * 					MPSSE_BITMODE_CBUS, or MPSSE_BITMODE_SYNCFF
	 * @return 0: Everything is OK.
	 *  -1: USB controlTransfer method failed.
	 *  -2: input value cannot be recognized.
	 */
	public int SetBitMaskBitMode(byte bitmask, byte bitmode)
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
			return -2;
		}
		
		int combinedSetupValue = (bitmode << 8) | bitmask;
		int r;
		if((r = mUsbDeviceConnection.controlTransfer(FTDI_Constants.FTDI_DEVICE_OUT_REQTYPE, FTDI_Constants.SIO_SET_BITMODE_REQUEST, 
										combinedSetupValue, mInterface, null, 0, mWriteTimeout))  != 0)
		{
			Log.e(TAG,"USB controlTransfer operation failed. controlTransfer return value is:"+Integer.toString(r));
			return -1;
		}
		else
		{
			//need to keep a record of the bitmode, and let upper level methods be able to poll the bitmode status.
			mBitMode = bitmode;
			return 0;
		}
	}
	
	/**
	 * Gets the bit mode.
	 *
	 * @return the int represents mBitMode
	 */
	public int getBitMode()
	{
		return mBitMode;
	}
	
	/**
	 * Checks if bitbang mode is enabled.
	 *
	 * @return true, if bit bang mode is enabled
	 */
	public boolean isBitBangEnabled()
	{
		if(mBitMode == FTDI_Constants.MPSSE_BITMODE_RESET)
			return false;
		else
			return true;
	}
	//setLatencyTimer, getLatencyTimer	
	/**
	 * Sets the latency timer.
	 *
	 * @param latency: the desired latency in ms. Must be 1~255
	 * @return 0: Everything is OK.
	 *  -1: USB controlTransfer method failed.
	 *  -2: input value cannot be recognized.
	 */
	public int setLatencyTimer(int latency)
	{
		if(latency < 1 || latency > 255)
		{
			Log.e(TAG,"latency must be a 1~255, but the parameter gives: "+ Integer.toString(latency));
			return -2;
		}
		int r;
		if((r = mUsbDeviceConnection.controlTransfer(FTDI_Constants.FTDI_DEVICE_OUT_REQTYPE, FTDI_Constants.SIO_SET_LATENCY_TIMER_REQUEST,
								latency, mInterface, null, 0, mWriteTimeout)) != 0)
		{
			Log.e(TAG,"USB controlTransfer operation failed. controlTransfer return value is:"+Integer.toString(r));
			return -1;
		}
		else
			return 0;
	}
	
	/**
	 * Gets the latency timer.
	 *
	 * @return positive number: The latency timer value. Can only be 1~255. By design, this method WILL NOT return 0.
	 *  -1: USB controlTransfer method failed.
	 */
	public int getLatencyTimer()//this shall return the actual value, rather than the result of usb operation method.
	{
		byte[] buf = new byte[1];
		int r = 0;
		r = mUsbDeviceConnection.controlTransfer(FTDI_Constants.FTDI_DEVICE_IN_REQTYPE, FTDI_Constants.SIO_GET_LATENCY_TIMER_REQUEST,
								0, mInterface, buf, 1, mReadTimeout);
		if(r!= 1)
		{
			Log.e(TAG,"USB controlTransfer operation failed. controlTransfer return value is:"+Integer.toString(r));
			return -1;
		}
		else
		{
			return ((int)buf[1] & 0x000000ff);//TODO: verify this function returns a non-negative value as latency timer setup.
		}
	}
	
	//setEventChar, setErrorChar
	/**
	 * Set or reset the event char.
	 *
	 * @param eventChar: the byte that is used as the event char.
	 * @param enable: indicate the event char is enabled or not.
	 * @return 0: everything is fine.
	 *  -1: USB controlTransfer method failed.
	 */
	public int setEventChar(byte eventChar, boolean enable)
	{
		//make sure the high bits doesn't set to 1 during type casting.
		int combinedValue = ((int)eventChar) & 0x000000ff;//TODO: make sure the combined value is as what we've desired
		if(enable) combinedValue |= (0x01<<8);
		int r;
		if((r = mUsbDeviceConnection.controlTransfer(FTDI_Constants.FTDI_DEVICE_OUT_REQTYPE, FTDI_Constants.SIO_SET_EVENT_CHAR_REQUEST,
						combinedValue, mInterface, null, 0, mWriteTimeout)) != 0)
		{
			Log.e(TAG,"USB controlTransfer operation failed. controlTransfer return value is:"+Integer.toString(r));
			return -1;
		}
		else
			return 0;
	}
	
	/**
	 * Sets the error char.
	 *
	 * @param errorChar: the byte that is used as the error char.
	 * @param enable: indicate the error char is enabled or not.
	 * @return 0: everything is fine.
	 *  -1: USB controlTransfer method failed.
	 */
	public int setErrorChar(byte errorChar, boolean enable)
	{
		//make sure the high bits doesn't set to 1 during type casting.
		int combinedValue = ((int)errorChar & 0x000000ff);//TODO: make sure the combined value is as what we've desired
		if(enable) combinedValue |= (0x01<<8);
		int r;
		if((r = mUsbDeviceConnection.controlTransfer(FTDI_Constants.FTDI_DEVICE_OUT_REQTYPE, FTDI_Constants.SIO_SET_ERROR_CHAR_REQUEST,
						combinedValue, mInterface, null, 0, mWriteTimeout)) != 0)
		{
			Log.e(TAG,"USB controlTransfer operation failed. controlTransfer return value is:"+Integer.toString(r));
			return -1;
		}
		else
			return 0;
	}
}
