package shansong.ftdi.d2xx;

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
	
	public void open()
	{	//Open the FTDI_Device.
		if(mFTDI_Device.openDevice() < 0)
		{
			//TODO: think if we should open the device outside this class.
			//because one FTDI_Device class can manage more than one FTDI_Interface.
		}
		//set up baud rate, data bits, etc, according to current config		
		if( mFTDI_Interface.setBaudRate(mBaudRate) < 0 ||
				mFTDI_Interface.setLineProperty(mDataBits, mStopBits, mParity, mBreakState) < 0 ||
				mFTDI_Interface.setFlowControl(mFlowControl)<0)
		{
			//throw exception
		}
		
		//TODO:need to claim the usb interface//I believe the claim interface shall happen in this function.
		if(!mFTDI_Interface.claimInterface(false))
		{
			//throw exception
		}
		//need to reset the device? No. or flush the the usb buffer for a port? no. 
		//because all of this will influence other interfaces that are possibly operating.
		
		//set up a read/write task and let it run
		mSerialPortTask = new FTDI_SerialPortReadWriteTask(mFTDI_Interface,mReadBufferSize,mReadTimeOut,mBulkReadSize,
															mWriteBufferSize,mWriteTimeOut,mBulkWriteSize);	
		mSerialPortTask.setOnDataReceivedListener(mOnDataReceivedListener);
		mSerialPortTask.setOnErrorReceivedListener(mOnErrorReceivedListener);
		mSerialPortTask.setOnPinChangedListener(mOnPinChangedListener);
		mSerialPortTask.execute();
		mIsOpened = true;
	}
	
	public void close()
	{
		//need to release the claimed usb interface
		if(!mFTDI_Interface.releaseInterface()){
			//TODO: throw exception;
		}
		//need to end the read/write async task
		mSerialPortTask.cancel(true);//TODO: shall we change back to a stop() function?
		mIsOpened = false;
	}

	public int getBaudRate()
	{
		return mBaudRate;
	}
	
	public int setBaudRate(int baudrate)
	{
		if(mIsOpened){
			int actualBaudRate = mFTDI_Interface.setBaudRate(baudrate);
			if(actualBaudRate<0){
				//TODO: throw an exception?
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
	
	public int setDataBits(int new_data_bits)
	{
		if(mIsOpened){
			int result = mFTDI_Interface.setLineProperty(new_data_bits, mStopBits, mParity, mBreakState);
			if(result < 0){
				//TODO: throw proper exceptions
			}
			mDataBits = new_data_bits;
			return result;
		}
		else{
			mDataBits = new_data_bits;
			return 0;
		}
	}
	
	public int getParity()
	{
		return mParity;
	}
	
	public int setParity(int new_parity)
	{
		if(mIsOpened){
			int result = mFTDI_Interface.setLineProperty(mDataBits, mStopBits, new_parity, mBreakState);
			if(result < 0){
				//TODO: throw proper exceptions
			}
			mParity = new_parity;
			return result;
		}
		else{
			mParity = new_parity;
			return 0;
		}
	}
	
	public int getStopBits()
	{
		return mStopBits;
	}
	
	public int setStopBits(int new_stop_bits)
	{
		if(mIsOpened){
			int result = mFTDI_Interface.setLineProperty(mDataBits, new_stop_bits, mParity, mBreakState);
			if(result < 0){
				//TODO: throw proper exceptions
			}
			mStopBits = new_stop_bits;
			return result;
		}
		else{
			mStopBits = new_stop_bits;
			return 0;
		}
	}

	public int getBreakState()
	{
		return mBreakState;
	}
	
	public int setBreakState(int new_break_state)
	{
		if(mIsOpened){
			int result = mFTDI_Interface.setLineProperty(mDataBits, mStopBits, mParity, new_break_state);
			if(result < 0){
				//TODO: throw proper exceptions
			}
			mBreakState = new_break_state;
			return result;
		}
		else{
			mBreakState = new_break_state;
			return 0;
		}
	}
	
	public int getFlowControl()
	{
		return mFlowControl;
	}
	
	public int setFlowControl(int new_flow_ctrl_type)
	{
		if(mIsOpened){
			int result = mFTDI_Interface.setFlowControl(new_flow_ctrl_type);
			if(result < 0){
				//TODO:throw proper exception here.
			}
			mFlowControl = new_flow_ctrl_type;
			return result;
		}
		else{
			mFlowControl = new_flow_ctrl_type;
			return 0;
		}
	}
		
	public int read(byte[] buf, int startIndex, int length) throws TimeoutException
	{
		if(!mIsOpened){
			//TODO: throw exception.
		}
		return mSerialPortTask.readData(buf, startIndex, length);
	}
	
	public int write(byte[] buf, int startIndex, int length) throws TimeoutException
	{
		if(!mIsOpened){
			//TODO: throw exception.
		}
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
