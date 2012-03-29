/*
 * This is the definition of all USB constants used in d2xx package. 
 * Including all USB commands that are sent to FTDI chips
 */
package shansong.ftdi.d2xx;

import android.hardware.usb.UsbConstants;

public final class FTDI_Constants {
	//The Android usb control message transfer function is defined as:
	//public int controlTransfer (int requestType, int request, int value, int index, byte[] buffer, int length, int timeout) 
	//So the requestType, request, value, index are all used to send control messages. Below are some pre-defined values.
	
	/* Pre-defined RequestType: */
	//FTDI usb-to-serial chips only use 2 request types. In request and out request:
	protected static final int USB_RECIP_DEVICE = 0x00;
	public static final int FTDI_DEVICE_OUT_REQTYPE = UsbConstants.USB_TYPE_VENDOR|USB_RECIP_DEVICE|UsbConstants.USB_DIR_OUT;
	public static final int FTDI_DEVICE_IN_REQTYPE = UsbConstants.USB_TYPE_VENDOR|USB_RECIP_DEVICE|UsbConstants.USB_DIR_IN ;
		
	/* Pre-defined Requests:  */	
	public static final int SIO_RESET_REQUEST				=0x00;
	public static final int SIO_SET_MODEM_CTRL_REQUEST		=0x01;
	public static final int SIO_SET_FLOW_CTRL_REQUEST		=0x02;
	public static final int SIO_SET_BAUDRATE_REQUEST		=0x03;
	public static final int SIO_SET_DATA_REQUEST			=0x04;	
	public static final int SIO_POLL_MODEM_STATUS_REQUEST	=0x05;
	public static final int SIO_SET_EVENT_CHAR_REQUEST		=0x06;
	public static final int SIO_SET_ERROR_CHAR_REQUEST		=0x07;
	public static final int SIO_SET_LATENCY_TIMER_REQUEST	=0x09;
	public static final int SIO_GET_LATENCY_TIMER_REQUEST	=0x0A;
	public static final int SIO_SET_BITMODE_REQUEST			=0x0B;
	public static final int SIO_READ_PINS_REQUEST			=0x0C;
	public static final int SIO_READ_EEPROM_REQUEST			=0x90;
	public static final int SIO_WRITE_EEPROM_REQUEST		=0x91;
	public static final int SIO_ERASE_EEPROM_REQUEST		=0x92;
	
	/* pre-defined Values: */
	//Note: these 3 values are always used with SIO_RESET_REQUEST
	public static final int SIO_RESET_SIO			=0x00;
	public static final int SIO_RESET_PURGE_RX		=0x01;
	public static final int SIO_RESET_PURGE_TX		=0x02;

	//Note: for set DTR and RTS, the *_HIGH and *_LOW sets the high and low value.
	//the controlTransfer input parameter "index" represents the interface A,B,C or D.
	public static final int SIO_SET_DTR_MASK		=0x1;
	public static final int SIO_SET_DTR_HIGH		=( 1 | ( SIO_SET_DTR_MASK  << 8));
	public static final int SIO_SET_DTR_LOW 		=( 0 | ( SIO_SET_DTR_MASK  << 8));
	public static final int SIO_SET_RTS_MASK		=0x2;
	public static final int SIO_SET_RTS_HIGH		=( 2 | ( SIO_SET_RTS_MASK << 8 ));
	public static final int SIO_SET_RTS_LOW			=( 0 | ( SIO_SET_RTS_MASK << 8 ));
	
	/* pre-defined Index: */
	//The FTDI channel(interface) is the most commonly used as Index parameter in usb control message tranmitting.
	//Different sources designates different index for interfaces. Some says it begins with 0 for A and ends 3 for D.
	//While some says it begine with 1 for A and 4 for D. After a while of searching, I tend to believe it 1 for A and 4 for D.
	public static final int INTERFACE_ANY = 0;
	public static final int INTERFACE_A = 1;
	public static final int INTERFACE_B = 2;
	public static final int INTERFACE_C = 3;
	public static final int INTERFACE_D = 4;
	
	//Note: these are used together with SIO_SET_FLOW_CTRL_REQUEST.
	//flow control setting is a little special, the following values are actually sent in "Index"
	//while higher byte is flow control, lower byte is chip channel index (channel A, B, C or D)
	public static final int SIO_DISABLE_FLOW_CTRL	=0x0;
	public static final int SIO_RTS_CTS_HS			=(0x1 << 8);
	public static final int SIO_DTR_DSR_HS			=(0x2 << 8);
	public static final int SIO_XON_XOFF_HS			=(0x4 << 8);
	
	
	/*Endpoint address for each interface (A, B, C and D)*/
	public static final int INTERFACE_A_ENDPOINT_IN 	= 0x02;
	public static final int INTERFACE_A_ENDPOINT_OUT 	= 0x81;
	public static final int INTERFACE_B_ENDPOINT_IN 	= 0x04;
	public static final int INTERFACE_B_ENDPOINT_OUT 	= 0x83;
	public static final int INTERFACE_C_ENDPOINT_IN 	= 0x06;
	public static final int INTERFACE_C_ENDPOINT_OUT 	= 0x85;
	public static final int INTERFACE_D_ENDPOINT_IN 	= 0x08;
	public static final int INTERFACE_D_ENDPOINT_OUT	= 0x87;
	
	/*Default Vendor ID FTDI*/
	public static final int VID_FTDI = 0x0403;
	/*Default Product ID for FTDI chips*/
	public static final int PID_FT232B_FT245B_FT232R_FT245R		= 0x6001;
	public static final int PID_FT2232C_FT2232D_FT2232L_FT2232H	= 0x6010;
	public static final int PID_FT4232H 						= 0x6011;
	public static final int PID_FT232H 							= 0x6014;
	
	/*Pre-defined constants for Line properties. Use these to set up line property*/
	public static final int PARITY_NONE	=0;
	public static final int PARITY_ODD	=1;
	public static final int PARITY_EVEN	=2;
	public static final int PARITY_MARK	=3;
	public static final int PARITY_SPACE=4;
	
	public static final int STOP_BITS_1	=0;
	public static final int STOP_BITS_15	=1;
	public static final int STOP_BITS_2	=2;
	
	//The FTDI D2XX programming guide says this must be 7 or 8 data bits length.
	//So i didn't list more here. And I let the program claim error when the setting is not 7 or 8.
	//But Microsoft's SerialPort class does support data bits = 5. Need to gether more information on this.
	public static final int DATA_BITS_7	=7;
	public static final int DATA_BITS_8	=8;
	
	public static final int BREAK_OFF	=0;
	public static final int BREAK_ON	=1;
	
	/*Pre-defined MPSSE bitbang modes */
    public static final byte MPSSE_BITMODE_RESET  = 0x00;    /**< switch off bitbang mode, back to regular serial/FIFO */
    public static final byte MPSSE_BITMODE_BITBANG= 0x01;    /**< classical asynchronous bitbang mode, introduced with B-type chips */
    public static final byte MPSSE_BITMODE_MPSSE  = 0x02;    /**< MPSSE mode, available on 2232x chips */
    public static final byte MPSSE_BITMODE_SYNCBB = 0x04;    /**< synchronous bitbang mode, available on 2232x and R-type chips  */
    public static final byte MPSSE_BITMODE_MCU    = 0x08;    /**< MCU Host Bus Emulation mode, available on 2232x chips */
    /* CPU-style fifo mode gets set via EEPROM */
    public static final byte MPSSE_BITMODE_OPTO   = 0x10;    /**< Fast Opto-Isolated Serial Interface Mode, available on 2232x chips  */
    public static final byte MPSSE_BITMODE_CBUS   = 0x20;    /**< Bitbang on CBUS pins of R-type chips, configure in EEPROM before */
    public static final byte MPSSE_BITMODE_SYNCFF = 0x40;    /**< Single Channel Synchronous FIFO mode, available on 2232H chips */
    
    /*Pre-defined device type*/
    public static final int DEVICE_TYPE_AM		=0;
    public static final int DEVICE_TYPE_BM		=1;
    public static final int DEVICE_TYPE_2232C	=2;
    public static final int DEVICE_TYPE_R		=3;
    public static final int DEVICE_TYPE_2232H	=4;
    public static final int DEVICE_TYPE_4232H	=5;
            
}
