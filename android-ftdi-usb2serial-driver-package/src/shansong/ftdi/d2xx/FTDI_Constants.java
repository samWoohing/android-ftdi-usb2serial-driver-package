/*
 * This is the definition of all USB constants used in d2xx package. 
 * Including all USB commands that are sent to FTDI chips
 */
package shansong.ftdi.d2xx;

import android.hardware.usb.UsbConstants;

// TODO: Auto-generated Javadoc
/**
 * The Class FTDI_Constants.
 */
public final class FTDI_Constants {
	//The Android usb control message transfer function is defined as:
	//public int controlTransfer (int requestType, int request, int value, int index, byte[] buffer, int length, int timeout) 
	//So the requestType, request, value, index are all used to send control messages. Below are some pre-defined values.
	
	/* Pre-defined RequestType: */
	//FTDI usb-to-serial chips only use 2 request types. In request and out request:
	/** The Constant USB_RECIP_DEVICE. */
	protected static final int USB_RECIP_DEVICE = 0x00;
	
	/** The Constant FTDI_DEVICE_OUT_REQTYPE. */
	public static final int FTDI_DEVICE_OUT_REQTYPE = UsbConstants.USB_TYPE_VENDOR|USB_RECIP_DEVICE|UsbConstants.USB_DIR_OUT;
	
	/** The Constant FTDI_DEVICE_IN_REQTYPE. */
	public static final int FTDI_DEVICE_IN_REQTYPE = UsbConstants.USB_TYPE_VENDOR|USB_RECIP_DEVICE|UsbConstants.USB_DIR_IN ;
		
	/* Pre-defined Requests:  */	
	/** The Constant SIO_RESET_REQUEST. */
	public static final int SIO_RESET_REQUEST				=0x00;
	
	/** The Constant SIO_SET_MODEM_CTRL_REQUEST. */
	public static final int SIO_SET_MODEM_CTRL_REQUEST		=0x01;
	
	/** The Constant SIO_SET_FLOW_CTRL_REQUEST. */
	public static final int SIO_SET_FLOW_CTRL_REQUEST		=0x02;
	
	/** The Constant SIO_SET_BAUDRATE_REQUEST. */
	public static final int SIO_SET_BAUDRATE_REQUEST		=0x03;
	
	/** The Constant SIO_SET_DATA_REQUEST. */
	public static final int SIO_SET_DATA_REQUEST			=0x04;	
	
	/** The Constant SIO_POLL_MODEM_STATUS_REQUEST. */
	public static final int SIO_POLL_MODEM_STATUS_REQUEST	=0x05;
	
	/** The Constant SIO_SET_EVENT_CHAR_REQUEST. */
	public static final int SIO_SET_EVENT_CHAR_REQUEST		=0x06;
	
	/** The Constant SIO_SET_ERROR_CHAR_REQUEST. */
	public static final int SIO_SET_ERROR_CHAR_REQUEST		=0x07;
	
	/** The Constant SIO_SET_LATENCY_TIMER_REQUEST. */
	public static final int SIO_SET_LATENCY_TIMER_REQUEST	=0x09;
	
	/** The Constant SIO_GET_LATENCY_TIMER_REQUEST. */
	public static final int SIO_GET_LATENCY_TIMER_REQUEST	=0x0A;
	
	/** The Constant SIO_SET_BITMODE_REQUEST. */
	public static final int SIO_SET_BITMODE_REQUEST			=0x0B;
	
	/** The Constant SIO_READ_PINS_REQUEST. */
	public static final int SIO_READ_PINS_REQUEST			=0x0C;
	
	/** The Constant SIO_READ_EEPROM_REQUEST. */
	public static final int SIO_READ_EEPROM_REQUEST			=0x90;
	
	/** The Constant SIO_WRITE_EEPROM_REQUEST. */
	public static final int SIO_WRITE_EEPROM_REQUEST		=0x91;
	
	/** The Constant SIO_ERASE_EEPROM_REQUEST. */
	public static final int SIO_ERASE_EEPROM_REQUEST		=0x92;
	
	/* pre-defined Values: */
	//Note: these 3 values are always used with SIO_RESET_REQUEST
	/** The Constant SIO_RESET_SIO. */
	public static final int SIO_RESET_SIO			=0x00;
	
	/** The Constant SIO_RESET_PURGE_RX. */
	public static final int SIO_RESET_PURGE_RX		=0x01;
	
	/** The Constant SIO_RESET_PURGE_TX. */
	public static final int SIO_RESET_PURGE_TX		=0x02;

	//Note: for set DTR and RTS, the *_HIGH and *_LOW sets the high and low value.
	//the controlTransfer input parameter "index" represents the interface A,B,C or D.
	/** The Constant SIO_SET_DTR_MASK. */
	public static final int SIO_SET_DTR_MASK		=0x1;
	
	/** The Constant SIO_SET_DTR_HIGH. */
	public static final int SIO_SET_DTR_HIGH		=( 1 | ( SIO_SET_DTR_MASK  << 8));
	
	/** The Constant SIO_SET_DTR_LOW. */
	public static final int SIO_SET_DTR_LOW 		=( 0 | ( SIO_SET_DTR_MASK  << 8));
	
	/** The Constant SIO_SET_RTS_MASK. */
	public static final int SIO_SET_RTS_MASK		=0x2;
	
	/** The Constant SIO_SET_RTS_HIGH. */
	public static final int SIO_SET_RTS_HIGH		=( 2 | ( SIO_SET_RTS_MASK << 8 ));
	
	/** The Constant SIO_SET_RTS_LOW. */
	public static final int SIO_SET_RTS_LOW			=( 0 | ( SIO_SET_RTS_MASK << 8 ));
	
	/* pre-defined Index: */
	//The FTDI channel(interface) is the most commonly used as Index parameter in usb control message tranmitting.
	//Different sources designates different index for interfaces. Some says it begins with 0 for A and ends 3 for D.
	//While some says it begin with 1 for A and 4 for D. After a while of searching, I tend to believe it 1 for A and 4 for D.
	/** The Constant INTERFACE_ANY. */
	public static final int INTERFACE_ANY = 0;
	
	/** The Constant INTERFACE_A. */
	public static final int INTERFACE_A = 1;
	
	/** The Constant INTERFACE_B. */
	public static final int INTERFACE_B = 2;
	
	/** The Constant INTERFACE_C. */
	public static final int INTERFACE_C = 3;
	
	/** The Constant INTERFACE_D. */
	public static final int INTERFACE_D = 4;
	
	//Note: these are used together with SIO_SET_FLOW_CTRL_REQUEST.
	//flow control setting is a little special, the following values are actually sent in "Index"
	//while higher byte is flow control, lower byte is chip channel index (channel A, B, C or D)
	/** The Constant SIO_DISABLE_FLOW_CTRL. */
	public static final int SIO_DISABLE_FLOW_CTRL	=0x0;
	
	/** The Constant SIO_RTS_CTS_HS. */
	public static final int SIO_RTS_CTS_HS			=(0x1 << 8);
	
	/** The Constant SIO_DTR_DSR_HS. */
	public static final int SIO_DTR_DSR_HS			=(0x2 << 8);
	
	/** The Constant SIO_XON_XOFF_HS. */
	public static final int SIO_XON_XOFF_HS			=(0x4 << 8);
	
	
	/*Endpoint address for each interface (A, B, C and D)*/
	/** The Constant INTERFACE_A_ENDPOINT_IN. */
	public static final int INTERFACE_A_ENDPOINT_IN 	= 0x02;
	
	/** The Constant INTERFACE_A_ENDPOINT_OUT. */
	public static final int INTERFACE_A_ENDPOINT_OUT 	= 0x81;
	
	/** The Constant INTERFACE_B_ENDPOINT_IN. */
	public static final int INTERFACE_B_ENDPOINT_IN 	= 0x04;
	
	/** The Constant INTERFACE_B_ENDPOINT_OUT. */
	public static final int INTERFACE_B_ENDPOINT_OUT 	= 0x83;
	
	/** The Constant INTERFACE_C_ENDPOINT_IN. */
	public static final int INTERFACE_C_ENDPOINT_IN 	= 0x06;
	
	/** The Constant INTERFACE_C_ENDPOINT_OUT. */
	public static final int INTERFACE_C_ENDPOINT_OUT 	= 0x85;
	
	/** The Constant INTERFACE_D_ENDPOINT_IN. */
	public static final int INTERFACE_D_ENDPOINT_IN 	= 0x08;
	
	/** The Constant INTERFACE_D_ENDPOINT_OUT. */
	public static final int INTERFACE_D_ENDPOINT_OUT	= 0x87;
	
	/*Default Vendor ID FTDI*/
	/** The Constant VID_FTDI. */
	public static final int VID_FTDI = 0x0403;
	/*Default Product ID for FTDI chips*/
	/** The Constant PID_FT232B_FT245B_FT232R_FT245R. */
	public static final int PID_FT232B_FT245B_FT232R_FT245R		= 0x6001;
	
	/** The Constant PID_FT2232C_FT2232D_FT2232L_FT2232H. */
	public static final int PID_FT2232C_FT2232D_FT2232L_FT2232H	= 0x6010;
	
	/** The Constant PID_FT4232H. */
	public static final int PID_FT4232H 						= 0x6011;
	
	/** The Constant PID_FT232H. */
	public static final int PID_FT232H 							= 0x6014;
	
	/*Pre-defined constants for Line properties. Use these to set up line property*/
	/** The Constant PARITY_NONE. */
	public static final int PARITY_NONE	=0;
	
	/** The Constant PARITY_ODD. */
	public static final int PARITY_ODD	=1;
	
	/** The Constant PARITY_EVEN. */
	public static final int PARITY_EVEN	=2;
	
	/** The Constant PARITY_MARK. */
	public static final int PARITY_MARK	=3;
	
	/** The Constant PARITY_SPACE. */
	public static final int PARITY_SPACE=4;
	
	/** The Constant STOP_BITS_1. */
	public static final int STOP_BITS_1	=0;
	
	/** The Constant STOP_BITS_15. */
	public static final int STOP_BITS_15	=1;
	
	/** The Constant STOP_BITS_2. */
	public static final int STOP_BITS_2	=2;
	
	//The FTDI D2XX programming guide says this must be 7 or 8 data bits length.
	//So i didn't list more here. And I let the program claim error when the setting is not 7 or 8.
	//But Microsoft's SerialPort class does support data bits = 5. Need to gether more information on this.
	/** The Constant DATA_BITS_7. */
	public static final int DATA_BITS_7	=7;
	
	/** The Constant DATA_BITS_8. */
	public static final int DATA_BITS_8	=8;
	
	/** The Constant BREAK_OFF. */
	public static final int BREAK_OFF	=0;
	
	/** The Constant BREAK_ON. */
	public static final int BREAK_ON	=1;
	
	/*Pre-defined MPSSE bitbang modes */
    /** The Constant MPSSE_BITMODE_RESET. */
	public static final byte MPSSE_BITMODE_RESET  = 0x00;    
    /** < switch off bitbang mode, back to regular serial/FIFO. */
    public static final byte MPSSE_BITMODE_BITBANG= 0x01;    
    /** < classical asynchronous bitbang mode, introduced with B-type chips. */
    public static final byte MPSSE_BITMODE_MPSSE  = 0x02;    
    /** < MPSSE mode, available on 2232x chips. */
    public static final byte MPSSE_BITMODE_SYNCBB = 0x04;    
    /** < synchronous bitbang mode, available on 2232x and R-type chips. */
    public static final byte MPSSE_BITMODE_MCU    = 0x08;    
    /** < MCU Host Bus Emulation mode, available on 2232x chips. */
    /* CPU-style fifo mode gets set via EEPROM */
    public static final byte MPSSE_BITMODE_OPTO   = 0x10;    
    /** < Fast Opto-Isolated Serial Interface Mode, available on 2232x chips. */
    public static final byte MPSSE_BITMODE_CBUS   = 0x20;    
    /** < Bitbang on CBUS pins of R-type chips, configure in EEPROM before. */
    public static final byte MPSSE_BITMODE_SYNCFF = 0x40;    
    /** < Single Channel Synchronous FIFO mode, available on 2232H chips. */
    
    /*Pre-defined device type*/
    public static final int DEVICE_TYPE_AM		=0;
    
    /** The Constant DEVICE_TYPE_BM. */
    public static final int DEVICE_TYPE_BM		=1;
    
    /** The Constant DEVICE_TYPE_2232C. */
    public static final int DEVICE_TYPE_2232C	=2;
    
    /** The Constant DEVICE_TYPE_R. */
    public static final int DEVICE_TYPE_R		=3;
    
    /** The Constant DEVICE_TYPE_2232H. */
    public static final int DEVICE_TYPE_2232H	=4;
    
    /** The Constant DEVICE_TYPE_4232H. */
    public static final int DEVICE_TYPE_4232H	=5;
    
    
//    Layout of the first byte:
//    - B0..B3 - must be 0
//    - B4       Clear to send (CTS)
//                 0 = inactive
//                 1 = active
//    - B5       Data set ready (DSR)
//                 0 = inactive
//                 1 = active
//    - B6       Ring indicator (RI)
//                 0 = inactive
//                 1 = active
//    - B7       Receive line signal detect (RLSD) also called Carrier Detect
//                 0 = inactive
//                 1 = active
//
//    Layout of the second byte:
//    - B0       Data ready (DR)
//    - B1       Overrun error (OE)
//    - B2       Parity error (PE)
//    - B3       Framing error (FE)
//    - B4       Break interrupt (BI)
//    - B5       Transmitter holding register (THRE)
//    - B6       Transmitter empty (TEMT)
//    - B7       Error in RCVR FIFO (ERFF)
    /*Pre-defined modem status bit mask, and event enums*/
    public static final int MODEM_STATUS_CTS_MSK =0x01 << 4;
    public static final int MODEM_STATUS_DSR_MSK =0x01 << 5;
    public static final int MODEM_STATUS_RI_MSK =0x01 << 6;
    public static final int MODEM_STATUS_RLSD_MSK =0x01 << 7;
    
    public static final int MODEM_STATUS_DR_MSK =0x01 << 8;
    public static final int MODEM_STATUS_OE_MSK =0x01 << 9;
    public static final int MODEM_STATUS_PE_MSK =0x01 << 10;
    public static final int MODEM_STATUS_FE_MSK =0x01 << 11;
    public static final int MODEM_STATUS_BI_MSK =0x01 << 12;
    public static final int MODEM_STATUS_THRE_MSK =0x01 << 13;
    public static final int MODEM_STATUS_TEMT_MSK =0x01 << 14;
    public static final int MODEM_STATUS_ERFF_MSK =0x01 << 15;
    
    public enum FTDI_CommErroEnum{
    	DATA_READY, OVERRUN_ERR, PARITY_ERR, FRAME_ERR, BREAK_INT, TRANS_HOLD_REG, TRANS_EMPTY, ERROR_RCVR_FIFO
    }
    
    public enum FTDI_PinChangeEnum{
    	CTS_CHANGED,DSR_CHANGED,RI_CHANGED, CD_CHANGED
    }
}
