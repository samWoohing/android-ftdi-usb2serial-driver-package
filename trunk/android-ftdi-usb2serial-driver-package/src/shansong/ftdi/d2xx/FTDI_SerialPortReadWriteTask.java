package shansong.ftdi.d2xx;

import java.nio.ByteBuffer;

import android.os.AsyncTask;

// TODO: Auto-generated Javadoc
/**
 * The Class FTDI_SerialPortReadWriteTask.
 */
public class FTDI_SerialPortReadWriteTask extends AsyncTask<FTDI_Interface, Integer, Integer> {
	//TODO: Need further completion. 
	//Current thoughts of this class:
	//1. Maintain a read buffer and a write buffer
	//2. provide readData and WriteData interface. They only operate the buffers, and does not operate the USB interface.
	//3. Use background task to handle the usb read and write
	//4. Provide callback listener for data receiving, communication errors, and pin changed events etc.
	
	/** The read buffer. */
    private ByteBuffer mReadBuffer,mWriteBuffer;
    
    /** The m write time out. */
    private int mReadTimeOut,mWriteTimeOut;
    
    /** The flag used to exit the main loop. */
    private boolean mContinueToRun;
    
    
    /**
     * The listener interface for receiving onDataReceived events.
     * The class that is interested in processing a onDataReceived
     * event implements this interface, and the object created
     * with that class is registered with a component using the
     * component's <code>setOnDataReceivedListener<code> method. When
     * the onDataReceived event occurs, that object's appropriate
     * method is invoked.
     *
     * 
     */
    public interface OnDataReceivedListener {
        
        /**
         * On data received.
         *
         * @param sender the sender
         */
        void onDataReceived(Object sender);
        
        //to call the Listener within this class: mOnDataReceivedListener.onDataReceived(this);
    }
    
    /** The m on data received listener. */
    private OnDataReceivedListener mOnDataReceivedListener;
    
    /**
     * Sets the on data received listener.
     *
     * @param l the new on data received listener
     */
    public void setOnDataReceivedListener(OnDataReceivedListener l)
    {
    	mOnDataReceivedListener = l;
    }
    
    //TODO: define more event listener for communication error conditions.
    //	the things that I'm currently think of are:
    //		1. Modem status bit listener
    //			refer to Microsoft serial port class status bit change definition:
    //			http://msdn.microsoft.com/en-us/library/system.io.ports.serialpinchange.aspx
    //			it defines following status bit changes:
    //			Member name Description 
	//		    CtsChanged	The Clear to Send (CTS) signal changed state. This signal is used to indicate whether data can be sent over the serial port.  
	//		    DsrChanged	The Data Set Ready (DSR) signal changed state. This signal is used to indicate whether the device on the serial port is ready to operate.  
	//		    CDChanged	The Carrier Detect (CD) signal changed state. This signal is used to indicate whether a modem is connected to a working phone line and a data carrier signal is detected.  
	//		    Ring		A ring indicator was detected.  
	//		    Break		A break was detected on input.  
    /**
     * The listener interface for receiving onErrorReceived events.
     * The class that is interested in processing a onErrorReceived
     * event implements this interface, and the object created
     * with that class is registered with a component using the
     * component's <code>setOnErrorReceivedListener<code> method. When
     * the onErrorReceived event occurs, that object's appropriate
     * method is invoked.
     *
     * 
     */
    public interface OnErrorReceivedListener {
        
        /**
         * On data received.
         *
         * @param sender the sender
         * @param error the error
         */
        void onErrorReceived(Object sender, int error);
        
        //to call the Listener within this class: mOnErrorReceivedListener.onDataReceived(this);
    }
    /** The m on error received listener. */
    private OnErrorReceivedListener mOnErrorReceivedListener;
    
    /**
     * Sets the on error received listener.
     *
     * @param l the new on data received listener
     */
    public void setOnErrorReceivedListener(OnErrorReceivedListener l)
    {
    	mOnErrorReceivedListener = l;
    }
    
	//	2. Serial communication error listener.
	//			refer to Microsoft serial port class communication error definition: 
	//			http://msdn.microsoft.com/en-us/library/system.io.ports.serialerror.aspx
	//			it defines the following communication error:
	//			Member		name Description 
	//			TXFull		The application tried to transmit a character, but the output buffer was full.  
	//			RXOver		An input buffer overflow has occurred. There is either no room in the input buffer, or a character was received after the end-of-file (EOF) character.  
	//			Overrun		A character-buffer overrun has occurred. The next character is lost.  
	//			RXParity	The hardware detected a parity error.  
	//			Frame  		The hardware detected a framing error.  
	/**
	 * The listener interface for receiving onPinChanged events.
	 * The class that is interested in processing a onPinChanged
	 * event implements this interface, and the object created
	 * with that class is registered with a component using the
	 * component's <code>setOnPinChangedListener<code> method. When
	 * the onPinChanged event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * 
	 */
	public interface OnPinChangedListener {
        
        /**
         * On data received.
         *
         * @param sender the sender
         * @param error the error
         */
        void onPinChanged(Object sender, int pinChange);
        
        //to call the Listener within this class: mOnErrorReceivedListener.onDataReceived(this);
    }
	
    /** The m on pin changed listener. */
    private OnPinChangedListener mOnPinChangedListener;
    
    /**
     * Sets the on pin changed listener.
     *
     * @param l the new on data received listener
     */
    public void setOnPinChangedListener(OnPinChangedListener l)
    {
    	mOnPinChangedListener = l;
    }
    
    
    /**
     * Instantiates a new fTD i_ serial port read task.
     *
     * @param readBufferSize the read buffer size
     * @param readTimeOut the read time out
     * @param writeBufferSize the write buffer size
     * @param writeTimeOut the: write time out in ms.
     */
    public FTDI_SerialPortReadWriteTask(int readBufferSize, int readTimeOut,int writeBufferSize, int writeTimeOut)
    {
    	mReadBuffer = ByteBuffer.allocate(readBufferSize);
    	mReadTimeOut = readTimeOut;
    	mWriteBuffer = ByteBuffer.allocate(writeBufferSize);
    	mWriteTimeOut = writeTimeOut;
    }
    
    /**
     * Read data.
     *
     * @param buffer the buffer
     * @param length the length
     * @return the int
     */
    public int readData(byte[] buffer, int startIndex, int length)
    {
    	//TODO: detailed implementation
    	return 0;
    }
    
    /**
     * Write data.
     *
     * @param buffer the buffer
     * @param length the length
     * @return the int
     */
    public int writeData(byte[] buffer, int startIndex, int length)
    {
    	//TODO: detailed implementation
    	return 0;
    }
    
	/* (non-Javadoc)
     * @see android.os.AsyncTask#doInBackground(Params[])
     */
    protected Integer doInBackground(FTDI_Interface...opened_interface)
	{
		//TODO: detailed implementation.
    	//here we shall have a main loop of reading and writing FTDI USB endpoints.
    	
    	//1. check and only allow one parameter.
    	
    	//2. check if the interface is claimed?? seems that we don't have a method to check this
    	
    	//3. the main loop
    	do{
    		//3.1 if there's anything remained in transmit buffer, do the transmit
    		
    		//3.2 do a receive USB read, note that we get the modem status at the same time
    		
    		//3.3 put the read data into receive buffer.
    		
    		//3.4 call the event listeners according to modem status
    		
    	}while(mContinueToRun);
    	
		return 0;
	}
	
	/* (non-Javadoc)
     * @see android.os.AsyncTask#onProgressUpdate(Progress[])
     */
    protected void onProgressUpdate(Integer... progress) 
	{         
    	
	}
	
    /* (non-Javadoc)
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	protected void onPreExecute()
    {
    	
    }
	/* (non-Javadoc)
     * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
     */
    protected void onPostExecute(Integer result) 
	{
		
	}
}
