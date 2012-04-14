package shansong.ftdi.d2xx;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import android.os.AsyncTask;
import android.os.CountDownTimer;

// TODO: Auto-generated Javadoc
/**
 * The Class FTDI_SerialPortReadWriteTask.
 */
public class FTDI_SerialPortReadWriteTask extends AsyncTask<Void, Integer, Integer> {
	//TODO: Need further completion. 
	//Current thoughts of this class:
	//1. Maintain a read buffer and a write buffer
	//2. provide readData and WriteData interface. They only operate the buffers, and does not operate the USB interface.
	//3. Use background task to handle the usb read and write
	//4. Provide callback listener for data receiving, communication errors, and pin changed events etc.
	
	/** The m rx fifo. */
    private ByteCircularFifoBuffer mRxFifo, mTxFifo;
    
    /** The pre-defined size of USB bulk read and write */
    private int mBulkReadSize, mBulkWriteSize;
    
    /** The m write time out. */
    private int mReadTimeOut,mWriteTimeOut;
    
    /** The flag used to exit the main loop. */
    //private boolean mContinueToRun;
    
    /** The ftdi interface that is used as serial port. */
    FTDI_Interface mFTDI_Interface;
    
    /** The flag of whether or not the background task shall be kept running. */
    private boolean mKeepRunning;
    
    public void stop()
    {
    	mKeepRunning = false;
    }
    /**
     * The listener interface for receiving onDataReceived events.
     * The class that is interested in processing a onDataReceived
     * event implements this interface, and the object created
     * with that class is registered with a component using the
     * component's <code>setOnDataReceivedListener<code> method. When
     * the onDataReceived event occurs, that object's appropriate
     * method is invoked.
     *
     * @see OnDataReceivedEvent
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
	//	1. Serial communication error listener.
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
     * The listener interface for receiving onErrorReceived events.
     * The class that is interested in processing a onErrorReceived
     * event implements this interface, and the object created
     * with that class is registered with a component using the
     * component's <code>setOnErrorReceivedListener<code> method. When
     * the onErrorReceived event occurs, that object's appropriate
     * method is invoked.
     *
     * @see OnErrorReceivedEvent
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
    
    
    //		2. Modem status bit listener
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
	 * The listener interface for receiving onPinChanged events.
	 * The class that is interested in processing a onPinChanged
	 * event implements this interface, and the object created
	 * with that class is registered with a component using the
	 * component's <code>setOnPinChangedListener<code> method. When
	 * the onPinChanged event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see OnPinChangedEvent
	 */
	public interface OnPinChangedListener {
        
        /**
         * On data received.
         *
         * @param sender the sender
         * @param pinChange the pin change
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
     * @param claimedFTDI_Interface the claimed ftd i_ interface
     * @param readBufferSize the read buffer size
     * @param readTimeOut the read time out
     * @param writeBufferSize the write buffer size
     * @param writeTimeOut the: write time out in ms.
     */
    public FTDI_SerialPortReadWriteTask(FTDI_Interface claimedFTDI_Interface, int readBufferSize, int readTimeOut,
    		int bulkReadSize, int bulkWriteSize, int writeBufferSize, int writeTimeOut)
    {    	
    	mRxFifo = new ByteCircularFifoBuffer(readBufferSize);
    	mTxFifo = new ByteCircularFifoBuffer(writeBufferSize);
    	mBulkReadSize = bulkReadSize;
    	mBulkWriteSize = bulkWriteSize;
    	mReadTimeOut = readTimeOut;
    	mWriteTimeOut = writeTimeOut;
    	mFTDI_Interface = claimedFTDI_Interface;
    	mKeepRunning = true;
    	
    }
    
    /**
     * The Class ReadDataAsyncTask, use this when reading more than existing num of bytes in fifo.
     * TODO: revise parameter, return value definitions.
     */
    private class ReadDataAsyncTask extends AsyncTask<Void, Integer, Integer> {
    	
	    /** The start index. */
	    private int length, startIndex;
    	
	    /** The buffer. */
	    private byte[] buffer;
	    
	    /**
    	 * Instantiates a new ReadDataAsyncTask.
    	 *
    	 * @param buf the buf
    	 * @param stIdx the st idx
    	 * @param len the len
    	 */
    	public ReadDataAsyncTask(byte[] buf, int stIdx, int len)
	    {
	    	buffer = buf;
	    	startIndex = stIdx;
	    	length = len;
	    }
    	
 		/* (non-Javadoc)
		  * @see android.os.AsyncTask#doInBackground(Params[])
		  */
    	@Override
		protected Integer doInBackground(Void... params) {
    		int numAlreadyRead=0;
    		int numLeftToRead = length;
    		int currentStartIndex = startIndex;
    		do{
    			int numOccupied = mRxFifo.occupiedLength();
    			
    			if(numOccupied>0)//to save CPU resource, only run when data exists in fifo.
    			{
    				int numToReadOnce = (numOccupied <= numLeftToRead)?numOccupied:numLeftToRead;
    				numAlreadyRead += mRxFifo.readData(buffer, currentStartIndex, numToReadOnce);
    				//TODO: Need to catch exceptions?? 
    				//yes!! need to throw out how many actual bytes are read
    				currentStartIndex+=numToReadOnce;
    				numLeftToRead-=numToReadOnce;
    			}
 	    		
    		}while(numLeftToRead>0 && !isCancelled());
    		return numAlreadyRead;//TODO: review if this return value makes sense.
    	}
	}
    
    /**
     * Read data.
     *
     * @param buffer the buffer
     * @param startIndex the start index
     * @param length the length
     * @return the int
     * @throws TimeoutException 
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    public int readData(byte[] buffer, int startIndex, int length) throws InterruptedException, ExecutionException, TimeoutException
    {
    	//TODO: detailed implementation
    	//Another solution is to use the get(...) method in an AsyncTask.
    	//I think we need to create a AsyncTask only when necessary.
    	
    	//if the read buffer already have the required number of bytes,
    	//just do a direct read
    	int result = 0;
    	if(mRxFifo.occupiedLength() >= length)
    	{
    		result = mRxFifo.readData(buffer, startIndex, length);
    	}
    	else
    	{
    		ReadDataAsyncTask readTask = new ReadDataAsyncTask(buffer, startIndex, length);
    		readTask.execute();
    		result = readTask.get(mReadTimeOut, TimeUnit.MILLISECONDS);//this may throw the exception directly.
    		
    	}
       	return result;
    }
    
    /**
     * The Class WriteDataAsyncTask. Use write num of bytes more than existing vacancies in fifo.
     * TODO: revise parameter, return value definitions.
     */
    private class WriteDataAsyncTask extends AsyncTask<Void, Integer, Integer> {
    	/** The start index. */
	    private int length, startIndex;
    	
	    /** The buffer. */
	    private byte[] buffer;
	    
	    /**
    	 * Instantiates a new write data async task.
    	 *
    	 * @param buf the buf
    	 * @param stIdx the st idx
    	 * @param len the len
    	 */
    	public WriteDataAsyncTask(byte[] buf, int stIdx, int len){
	    	buffer = buf;
	    	startIndex = stIdx;
	    	length = len;
	    }
    	
    	/* (non-Javadoc)
	     * @see android.os.AsyncTask#doInBackground(Params[])
	     */
	    @Override
		protected Integer doInBackground(Void... params) {
    		int numAlreadyWritten = 0;
    		int numLeftToWrite = length;
    		int currentStartIndex = startIndex;
    		do{
    			int numFree = mTxFifo.freeLength();
    			
    			if(numFree>0)
    			{
    				int numToWriteOnce = (numFree <= numLeftToWrite)? numFree:numLeftToWrite;
    				numAlreadyWritten += mTxFifo.writeData(buffer, currentStartIndex, numToWriteOnce);
    				//TODO: add exception handling.
    				currentStartIndex += numToWriteOnce;
    				numLeftToWrite -= numToWriteOnce;
    			}
    		}while(numLeftToWrite > 0 && !isCancelled());//when user cancel the operation, break the loop
    		
    		return numAlreadyWritten;
    	}
    }
    /**
     * Write data.
     *
     * @param buffer the buffer
     * @param startIndex the start index
     * @param length the length
     * @return the int
     * @throws TimeoutException 
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    public int writeData(byte[] buffer, int startIndex, int length) throws InterruptedException, ExecutionException, TimeoutException
    {
    	int result = 0;
    	if(mTxFifo.freeLength() >= length)
    	{
    		result = mTxFifo.writeData(buffer, startIndex, length);
    	}
    	else
    	{
    		//TODO: call the async task.
    		WriteDataAsyncTask mWriteDataAsyncTask = new WriteDataAsyncTask(buffer, startIndex, length);
    		mWriteDataAsyncTask.execute();
    		result = mWriteDataAsyncTask.get(mWriteTimeOut, TimeUnit.MILLISECONDS);//this may throw the exception directly.
    		//TODO: still need to catch the timeout exception, and cancel the execution
    	}
    	return result;
    }
    
	/* (non-Javadoc)
     * @see android.os.AsyncTask#doInBackground(Params[])
     */
    protected Integer doInBackground(Void...Params)
	{
		//TODO: detailed implementation.
    	//here we shall have a main loop of reading and writing FTDI USB endpoints.
    	//attention: the exception handling of this task is tricky. 
		//We should handle as much as possible for the task to continue without breaking.
    	
    	//1. prepare the usb bulk read/write buffer
    	byte[] bulkReadBuf = new byte[mBulkReadSize];
    	byte[] bulkWriteBuf = new byte[mBulkWriteSize];
    	//2. check if the interface is claimed?? seems that we don't have a method to check this
    	
    	//3. the main loop
    	do{
    		//3.1 if there's anything remained in transmit buffer, do the transmit
    		int numTxBytes,numRxFree, modemStatus=0;
    		if((numTxBytes = mTxFifo.occupiedLength()) > 0)//if there's anything to send
    		{
    			int numToWrite = (numTxBytes <= mBulkWriteSize)? numTxBytes:mBulkWriteSize;
    			mTxFifo.readData(bulkWriteBuf, 0, numToWrite);
    			int numActuallyWritten = mFTDI_Interface.writeData(bulkWriteBuf, numToWrite);
    			//after this, all bytes in bulkWriteBuf are written out to FTDI chip, nothing useful left.
    			//TODO: handle the possible usb writing error. 
    			//the desired behavior is to handle the exception/mistakes and call the callbacks later.
    			//so that this thread can keep running rather than exit with exception.
    		}
    		//3.2 do a receive USB read, note that we get the modem status at the same time
    		if((numRxFree = mRxFifo.freeLength())>0)
    		{
    			int numToRead = (numRxFree <= mBulkReadSize)? numRxFree:mBulkReadSize;
    			int numActuallyRead = mFTDI_Interface.readData(bulkReadBuf, numToRead);
    			modemStatus = ((bulkReadBuf[1] << 8) | (bulkReadBuf[0] & 0xFF));//assembly the 2 bytes for modemStatus
    			mRxFifo.writeData(bulkReadBuf, 2, numActuallyRead-2);//the remaining are actual bytes read from rx.
    			//TODO: modify FTDI_Interface.readData, think about what is the good way to get modem status update.
    		}
    		
    		//3.4 call the event listeners according to modem status and other status
    		
    		
    		
    	}while(mKeepRunning);
    	
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
