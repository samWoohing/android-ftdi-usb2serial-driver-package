package shansong.ftdi.d2xx;


public class FTDI_SerialPort {
		
	private FTDI_Device mFTDI_Device;
	private FTDI_Interface mFTDI_Interface;
	
	private int mBaudRate;
	private int mDataBits;
	private int mParity;
	private int mStopBits;
	private int mFlowControl;
	private int mBreakState;//the break that we'd like to send out.
	
	private FTDI_SerialPortReadWriteTask mSerialPortTask;
	
	public FTDI_SerialPort(FTDI_Device dev, int whichInterface)
	{
		mFTDI_Device = dev;
		mFTDI_Interface = mFTDI_Device.getInterfaces(whichInterface);
		//need to do the initialization by calling FTDI_Device.initDevice
		
		//need to set default port configuration.
		
	}
	
	public void open()
	{
		if(mFTDI_Device.openDevice() < 0)
		{
			//throw exception
		}
		
		
		//need to claim the usb interface
		//set up baud rate, data bits, etc, according to current config
		//need to reset the device? No. or flush the the usb buffer for a port? no. because all of this will influence other 
		//set up a read/write task and let it run
		
		//mSerialPortTask = new FTDI_SerialPortReadWriteTask();
		//I believe the claim interface shall happen in this function.
	}
	
	public void close()
	{
		//need to release the claimed usb interface
		//need to end the read/write async task
	}

	public int getBaudRate()
	{
		return mBaudRate;
	}
	
	public int setBaudRate(int baudrate)
	{
		int actualBaudRate = mFTDI_Interface.setBaudRate(baudrate);
		if(actualBaudRate<0){
			//TODO: throw an exception?
		}
		mBaudRate = baudrate;
		return actualBaudRate;
	}
	
	public int getDataBits()
	{
		return mDataBits;
	}
	
	public int setDataBits(int new_data_bits)
	{
		int result = mFTDI_Interface.setLineProperty(new_data_bits, mStopBits, mParity, mBreakState);
		if(result < 0){
			//TODO: throw proper exceptions
		}
		mDataBits = new_data_bits;
		return result;
	}
	
	public int getParity()
	{
		return mParity;
	}
	public int setParity(int new_parity)
	{
		int result = mFTDI_Interface.setLineProperty(mDataBits, mStopBits, new_parity, mBreakState);
		if(result < 0){
			//TODO: throw proper exceptions
		}
		return result;
	}
	
	public int getStopBits()
	{
		return mStopBits;
	}
	
	public int setStopBits(int new_stop_bits)
	{
		int result = mFTDI_Interface.setLineProperty(mDataBits, new_stop_bits, mParity, mBreakState);
		if(result < 0){
			//TODO: throw proper exceptions
		}
		return result;
	}

	public int getBreakState()
	{
		return mBreakState;
	}
	
	public int setBreakState(int new_break_state)
	{
		int result = mFTDI_Interface.setLineProperty(mDataBits, mStopBits, mParity, new_break_state);
		if(result < 0){
			//TODO: throw proper exceptions
		}
		return result;
	}
	
	public int getFlowControl()
	{
		return mFlowControl;
	}
	
	public int setFlowControl(int new_flow_ctrl_type)
	{
		int result = mFTDI_Interface.setFlowControl(new_flow_ctrl_type);
		if(result < 0){
			//throw proper exception here.
		}
		return result;
	}
		
	public int read(byte[] buf, int startIndex, int length)
	{
		return 0;
	}
	
	public int write(byte[] buf, int startIndex, int length)
	{
		return 0;
	}
	
	
}
