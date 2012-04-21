package shansong.ftdi.d2xx;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import shansong.ftdi.d2xx.FTDI_SerialPortReadWriteTask.OnDataReceivedListener;
import shansong.ftdi.d2xx.FTDI_SerialPortReadWriteTask.OnErrorReceivedListener;
import shansong.ftdi.d2xx.FTDI_SerialPortReadWriteTask.OnPinChangedListener;


public class FTDI_SerialPort {
		
	private FTDI_Device mFTDI_Device;
	private FTDI_Interface mFTDI_Interface;
	
	private int mBaudRate;
	private int mDataBits;
	private int mParity;
	private int mStopBits;
	private int mFlowControl;
	private int mBreakState;//the break that we'd like to send out.
	private static byte mBitMode = FTDI_Constants.MPSSE_BITMODE_RESET;//the default bitmode shall be used for serial port.
	private static byte mBitMask = 0;//TODO: i think seial port shouldn't provide GPIO. may review this later.
	
	private int mReadBufferSize,mWriteBufferSize,mReadTimeOut,mWriteTimeOut,mBulkReadSize,mBulkWriteSize;
	
	private OnDataReceivedListener mOnDataReceivedListener;
	private OnErrorReceivedListener mOnErrorReceivedListener;
	private OnPinChangedListener mOnPinChangedListener;
	
	private boolean mIsOpened;
	
	private FTDI_SerialPortReadWriteTask mSerialPortTask;
	
	public FTDI_SerialPort(FTDI_Device dev, int whichInterface)
	{
		mFTDI_Device = dev;
		mFTDI_Interface = mFTDI_Device.getInterfaces(whichInterface);
		//need to do the initialization by calling FTDI_Device.initDevice
		
		//need to set default port configuration.
		mBaudRate 	= 9600;
		mDataBits 	= FTDI_Constants.DATA_BITS_8;
		mParity 	= FTDI_Constants.PARITY_NONE;
		mStopBits 	= FTDI_Constants.STOP_BITS_1;
		mFlowControl= FTDI_Constants.SIO_DISABLE_FLOW_CTRL;
		mBreakState = FTDI_Constants.BREAK_OFF;
		
		mReadBufferSize		= 4096;
		mWriteBufferSize 	= 4096;
		mBulkReadSize 		= 512;
		mBulkWriteSize 		= 512;
		mReadTimeOut 		= 2000;
		mWriteTimeOut		= 2000;
		
		mIsOpened = false;//equals true when mFTDI_Device is opened and FTDI_Interface is claimed.
	}
	
	public void open() throws IOException, IllegalArgumentException
	{
		//TODO: think if we should open the device outside this class.
		//because one FTDI_Device class can manage more than one FTDI_Interface.
		int result;
		result = mFTDI_Device.openDevice();
		if(result == -1) throw new IOException("openDevice: Usb operation failed");
		if(result == -2) throw new IOException("openDevice: Device access permission denied.");
				
		result = mFTDI_Interface.setBaudRate(mBaudRate);
		if(result == -1) throw new IOException("setBaudRate: Usb operation failed");
		if(result == -2) throw new IllegalArgumentException("setBaudRate: Cannot implement the given baudrate");
		if(result == -3) throw new IllegalArgumentException("setBaudRate: Implement baudrate has >5% error");
		
		result = mFTDI_Interface.setLineProperty(mDataBits, mStopBits, mParity, mBreakState);
		if(result == -1) throw new IOException("setLineProperty: Usb operation failed");
		if(result == -2) throw new IllegalArgumentException("setLineProperty: Invalid input parameter");
		
		result = mFTDI_Interface.SetBitMaskBitMode(mBitMask, mBitMode);
		if(result == -1) throw new IOException("SetBitMaskBitMode: Usb operation failed");
		if(result == -2) throw new IllegalArgumentException("SetBitMaskBitMode: Invalid input parameter");
		
		if(!mFTDI_Interface.claimInterface(false)) throw new IOException("claimInterface: failed to claim interface.");

		//set up a read/write task and let it run
		mSerialPortTask = new FTDI_SerialPortReadWriteTask(mFTDI_Interface,mReadBufferSize,mReadTimeOut,mBulkReadSize,
															mWriteBufferSize,mWriteTimeOut,mBulkWriteSize);	
		mSerialPortTask.setOnDataReceivedListener(mOnDataReceivedListener);
		mSerialPortTask.setOnErrorReceivedListener(mOnErrorReceivedListener);
		mSerialPortTask.setOnPinChangedListener(mOnPinChangedListener);
		mSerialPortTask.start();
		mIsOpened = true;
	}
	
	public void close() throws IOException
	{
		//need to release the claimed usb interface
		if(!mFTDI_Interface.releaseInterface())throw new IOException("releaseInterface: failed to release interface.");
		//need to end the read/write async task
		mSerialPortTask.stop();
		int result=0;
		try {
			result = mSerialPortTask.get();
		} catch (InterruptedException e) {
			// TODO decide what to do with excution exceptions
		} catch (ExecutionException e) {
			// TODO decide what to do with excution exceptions
			e.printStackTrace();
		}
		
		mIsOpened = false;
	}

	public int getBaudRate()
	{
		return mBaudRate;
	}
	
	public int setBaudRate(int baudrate) throws IOException, IllegalArgumentException
	{
		if(mIsOpened){
			int actualBaudRate = mFTDI_Interface.setBaudRate(baudrate);
			if(actualBaudRate == -2) throw new IllegalArgumentException("calculated actual baud rate is negative.");
			if(actualBaudRate == -3) throw new IllegalArgumentException("calculated actual baud rate has greater than 5% error");
			if(actualBaudRate == -1) throw new IOException();
			if(actualBaudRate<0){
				//TODO: throw an exception, 
				//need to separate USB failure, invalid arguments, and >5% error result
			}
			mBaudRate = baudrate;
			return actualBaudRate;
		}
		else{
			return mBaudRate = baudrate;
		}
	}
	
	public int getDataBits()
	{
		return mDataBits;
	}
	
	public int setDataBits(int new_data_bits) throws IOException, IllegalArgumentException
	{
		if(mIsOpened){
			int result = mFTDI_Interface.setLineProperty(new_data_bits, mStopBits, mParity, mBreakState);
			if(result == -2) throw new IllegalArgumentException();
			if(result == -1) throw new IOException();
			
			mDataBits = new_data_bits;
			return result;
		}
		else{
			if(!FTDI_Interface.validateDataBits(new_data_bits)) throw new IllegalArgumentException();
			mDataBits = new_data_bits;
			return 0;
		}
	}
	
	public int getParity()
	{
		return mParity;
	}
	
	public int setParity(int new_parity) throws IOException, IllegalArgumentException
	{
		if(mIsOpened){
			int result = mFTDI_Interface.setLineProperty(mDataBits, mStopBits, new_parity, mBreakState);
			if(result == -2) throw new IllegalArgumentException();
			if(result == -1) throw new IOException();

			mParity = new_parity;
			return result;
		}
		else{
			if(!FTDI_Interface.validateParity(new_parity)) throw new IllegalArgumentException();
			mParity = new_parity;
			return 0;
		}
	}
	
	public int getStopBits()
	{
		return mStopBits;
	}
	
	public int setStopBits(int new_stop_bits) throws IOException, IllegalArgumentException
	{
		if(mIsOpened){
			int result = mFTDI_Interface.setLineProperty(mDataBits, new_stop_bits, mParity, mBreakState);
			if(result == -2) throw new IllegalArgumentException();
			if(result == -1) throw new IOException();
			mStopBits = new_stop_bits;
			return result;
		}
		else{
			if(!FTDI_Interface.validateStopBits(new_stop_bits))throw new IllegalArgumentException();
			mStopBits = new_stop_bits;
			return 0;
		}
	}

	public int getBreakState()
	{
		return mBreakState;
	}
	
	public int setBreakState(int new_break_state) throws IOException, IllegalArgumentException
	{
		if(mIsOpened){
			int result = mFTDI_Interface.setLineProperty(mDataBits, mStopBits, mParity, new_break_state);
			if(result == -2) throw new IllegalArgumentException();
			if(result == -1) throw new IOException();
			mBreakState = new_break_state;
			return result;
		}
		else{
			if(!FTDI_Interface.validateBreak(new_break_state))throw new IllegalArgumentException();
			mBreakState = new_break_state;
			return 0;
		}
	}
	
	public int getFlowControl()
	{
		return mFlowControl;
	}
	
	public int setFlowControl(int new_flow_ctrl_type) throws IOException, IllegalArgumentException
	{
		if(mIsOpened){
			int result = mFTDI_Interface.setFlowControl(new_flow_ctrl_type);
			if(result == -2) throw new IllegalArgumentException();
			if(result == -1) throw new IOException();
			mFlowControl = new_flow_ctrl_type;
			return result;
		}
		else{
			if(!FTDI_Interface.validateFlowCtrl(new_flow_ctrl_type))throw new IllegalArgumentException();
			mFlowControl = new_flow_ctrl_type;
			return 0;
		}
	}
		
	public int read(byte[] buf, int startIndex, int length) throws TimeoutException, IllegalAccessException
	{	//TODO: more exception shall be thrown from here
		if(!mIsOpened)
			throw new IllegalAccessException("Port is not opened. Cannot perform port read.");
		return mSerialPortTask.readData(buf, startIndex, length);
	}
	
	public int write(byte[] buf, int startIndex, int length) throws TimeoutException, IllegalAccessException
	{
		if(!mIsOpened)
			throw new IllegalAccessException("Port is not opened. Cannot perform port write.");
		return mSerialPortTask.writeData(buf, startIndex, length);
	}
	
	public void setOnDataReceivedListener(OnDataReceivedListener l)
	{
		if(mIsOpened)
			mSerialPortTask.setOnDataReceivedListener(l);
		mOnDataReceivedListener = l;
	}
	
	public void setOnErrorReceivedListener(OnErrorReceivedListener l)
	{
		if(mIsOpened)
			mSerialPortTask.setOnErrorReceivedListener(l);
		mOnErrorReceivedListener = l;
	}
	
	public void setOnPinChangedListener(OnPinChangedListener l)
	{
		if(mIsOpened)
			mSerialPortTask.setOnPinChangedListener(l);
		mOnPinChangedListener = l;
	}
}
