package shansong.ftdi.d2xx;

import java.nio.BufferUnderflowException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import shansong.ftdi.d2xx.FTDI_Constants.FTDI_CommErroEnum;
import shansong.ftdi.d2xx.FTDI_Constants.FTDI_PinChangeEnum;

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
        void onErrorReceived(Object sender, FTDI_CommErroEnum error);
        
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
        void onPinChanged(Object sender, FTDI_PinChangeEnum pinChange, boolean isPinActive);
        
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
    		int bulkReadSize, int writeBufferSize, int writeTimeOut,  int bulkWriteSize)
    {    	
    	mRxFifo = new ByteCircularFifoBuffer(readBufferSize);
    	mTxFifo = new ByteCircularFifoBuffer(writeBufferSize);
    	mBulkReadSize = bulkReadSize;
    	mBulkWriteSize = bulkWriteSize;
    	mReadTimeOut = readTimeOut;
    	mWriteTimeOut = writeTimeOut;
    	mFTDI_Interface = claimedFTDI_Interface;
    	
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
	    
	    private CountDownTimer mTimer;
	    
	    boolean isTimeOut;
	    /**
    	 * Instantiates a new ReadDataAsyncTask.
    	 *
    	 * @param buf the buf
    	 * @param stIdx the st idx
    	 * @param len the len
    	 */
    	public ReadDataAsyncTask(byte[] buf, int stIdx, int len, int readTimeOut)
	    {
	    	buffer = buf;
	    	startIndex = stIdx;
	    	length = len;
	    	isTimeOut=false;
	    	mTimer = new CountDownTimer(readTimeOut, readTimeOut+1){
	    		public void onTick(long millisUntilFinished) {
	    			//do nothing
	    		}
	    		public void onFinish() {
	    			  isTimeOut=true;
	    		}
	    	};
	    }
    	
 		/* (non-Javadoc)
		  * @see android.os.AsyncTask#doInBackground(Params[])
		  * @return 0 or positive: the num of actual bytes read before time out. 
		  * -1 if no bytes is read until time out
		  */
    	@Override
		protected Integer doInBackground(Void... params) {
    		int numAlreadyRead=0;
    		int numLeftToRead = length;
    		int currentStartIndex = startIndex;
    		mTimer.start();
    		do{
    			int numOccupied = mRxFifo.occupiedLength();
    			
    			if(numOccupied>0)//to save CPU resource, only run when data exists in fifo.
    			{	//always read as much as we can
    				int numToReadOnce = (numOccupied <= numLeftToRead)?numOccupied:numLeftToRead;
    				numAlreadyRead += mRxFifo.readData(buffer, currentStartIndex, numToReadOnce);
    				//TODO: Need to catch exceptions?? 
    				//no. This branch shall not throw any exception.
    				currentStartIndex+=numToReadOnce;
    				numLeftToRead-=numToReadOnce;
    			}
 	    		
    		}while(numLeftToRead>0 && !isTimeOut);
    		
    		if(isTimeOut && numAlreadyRead == 0){
    			return -1;
    		}
    		return numAlreadyRead;//TODO: review if this return value makes sense.
    	}
	}
    
    /**
     * Read data. Perform an asynchronous non-blocking read.
     *
     * @param buffer the buffer
     * @param startIndex the start index
     * @param length the length
     * @return Positive number: the actual bytes read.
     * @throws BufferUnderflowException the buffer underflow exception
     * @throws IndexOutOfBoundsException the index out of bounds exception
     * @throws IllegalArgumentException the illegal argument exception
     * @throws TimeoutException the timeout exception
     */
    public int readData(byte[] buffer, int startIndex, int length) 
    		throws BufferUnderflowException, IndexOutOfBoundsException, IllegalArgumentException, TimeoutException
    {
    	//TODO: detailed implementation
    	//Another solution is to use the get(...) method in an AsyncTask.
    	//I think we need to create a AsyncTask only when necessary.
    	
    	//if the read buffer already have the required number of bytes,
    	//just do a direct read
    	int result = 0;
    	if(mRxFifo.occupiedLength() >= length){
    		result = mRxFifo.readData(buffer, startIndex, length);
    	}
    	else{
    		//find illegal argument before we perform any async read.
    		if(startIndex<0 || length<0){
    			throw new IndexOutOfBoundsException();
    		}
    		if(startIndex+length > buffer.length){
    			throw new IllegalArgumentException();
    		}
    		ReadDataAsyncTask readTask = new ReadDataAsyncTask(buffer, startIndex, length, mReadTimeOut);
    		readTask.execute();
    		try {
				result = readTask.get();
			} catch (InterruptedException e) {
				// TODO: write log
			} catch (ExecutionException e) {
				// TODO: write log
			}catch (CancellationException e){
				// TODO: write log
			}
    		if(result == -1) throw new TimeoutException();
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
	    private CountDownTimer mTimer;
	    boolean isTimeOut;
	    
	    /**
    	 * Instantiates a new write data async task.
    	 *
    	 * @param buf the buf
    	 * @param stIdx the st idx
    	 * @param len the len
    	 */
    	public WriteDataAsyncTask(byte[] buf, int stIdx, int len, int writeTimeOut){
	    	buffer = buf;
	    	startIndex = stIdx;
	    	length = len;
	    	isTimeOut=false;
	    	mTimer = new CountDownTimer(writeTimeOut, writeTimeOut+1){
	    		public void onTick(long millisUntilFinished) {
	    			//do nothing
	    		}
	    		public void onFinish() {
	    			  isTimeOut=true;
	    		}
	    	};
	    }
    	
    	/* (non-Javadoc)
	     * @see android.os.AsyncTask#doInBackground(Params[])
	     * @return 0 or positive: the num of actual bytes written before time out. -1 if no bytes is written until time ou
	     */
	    @Override
		protected Integer doInBackground(Void... params) {
    		int numAlreadyWritten = 0;
    		int numLeftToWrite = length;
    		int currentStartIndex = startIndex;
    		mTimer.start();
    		do{
    			int numFree = mTxFifo.freeLength();
    			if(numFree>0)
    			{
    				int numToWriteOnce = (numFree <= numLeftToWrite)? numFree:numLeftToWrite;
    				numAlreadyWritten += mTxFifo.writeData(buffer, currentStartIndex, numToWriteOnce);
    				
    				currentStartIndex += numToWriteOnce;
    				numLeftToWrite -= numToWriteOnce;
    			}
    		}while(numLeftToWrite > 0 && !isTimeOut);//timeout reached, break the loop
    		if(isTimeOut && numAlreadyWritten==0){
    			return -1;
    		}
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
    public int writeData(byte[] buffer, int startIndex, int length) 
    		throws BufferUnderflowException, IndexOutOfBoundsException, IllegalArgumentException, TimeoutException
    {
    	int result = 0;
    	if(mTxFifo.freeLength() >= length){
    		result = mTxFifo.writeData(buffer, startIndex, length);
    	}
    	else{
    		//find illegal argument before we perform any async read.
    		if(startIndex<0 || length<0){
    			throw new IndexOutOfBoundsException();
    		}
    		if(startIndex+length > buffer.length){
    			throw new IllegalArgumentException();
    		}
    		//call the async task.
    		WriteDataAsyncTask mWriteDataAsyncTask = new WriteDataAsyncTask(buffer, startIndex, length, mWriteTimeOut);
    		mWriteDataAsyncTask.execute();
    		try {
				result = mWriteDataAsyncTask.get();
			} catch (InterruptedException e) {
				// TODO: write log				
			} catch (ExecutionException e){
				// TODO: write log
				
			} catch (CancellationException e){
				// TODO: write log
			}
    		//TODO: still need to catch the timeout exception, and cancel the execution
    		if(result == -1) throw new TimeoutException();
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
    	
    	//1. prepare the usb bulk read/write buffer, and other variables needed in the loop
    	byte[] bulkReadBuf = new byte[mBulkReadSize];
    	byte[] bulkWriteBuf = new byte[mBulkWriteSize];
    	int numTxBytes,numRxFree, modemStatus;
    	int oldModemStatus;
    	if((oldModemStatus = mFTDI_Interface.getModemStatus())<0)
    	{
    		//this is error branch, should throw a exception and end the execution of this task.
    	}
    	
    	//2. check if the interface is claimed?? seems that we don't have a method to check this
    	
    	//3. the main loop
    	do{
    		//3.1 if there's anything remained in transmit buffer, do the transmit
    		if((numTxBytes = mTxFifo.occupiedLength()) > 0)//if there's anything to send
    		{
    			int numToWrite = (numTxBytes <= mBulkWriteSize)? numTxBytes:mBulkWriteSize;
    			mTxFifo.readData(bulkWriteBuf, 0, numToWrite);
    			int numActuallyWritten = mFTDI_Interface.writeData(bulkWriteBuf, numToWrite);
    			//TODO: handle the possible usb writing error represented by numActuallyWritten 
    			//the desired behavior is to handle the exception/mistakes and call the callbacks later.
    			//so that this thread can keep running rather than exit with exception.
    			if(numActuallyWritten < numToWrite)
    			{
    				//this is error branch, should through a exception and end the execution of this task.
    			}
    			//after this, all bytes in bulkWriteBuf are written out to FTDI chip, nothing useful left.
    		}
    		//3.2 do a receive USB read, note that we get the modem status at the same time
    		if((numRxFree = mRxFifo.freeLength())>0)
    		{
    			int numToRead = (numRxFree <= mBulkReadSize)? numRxFree:mBulkReadSize;
    			int numActuallyRead = mFTDI_Interface.readData(bulkReadBuf, numToRead);
    			modemStatus = ((bulkReadBuf[1] << 8) | (bulkReadBuf[0] & 0xFF));
    			if(numActuallyRead >2)//Data received
    			{
    				mRxFifo.writeData(bulkReadBuf, 2, numActuallyRead-2);//the remaining are actual bytes read from rx.
        			if(mOnDataReceivedListener != null)//call the event listener
    	    			mOnDataReceivedListener.onDataReceived(this);
    			}
    			else if(numActuallyRead < 2)
    			{
    				//this is error branch, should through a exception and end the execution of this task.
    			}
    		}
    		else//if RX fifo is full and we cannot perform a read, at least read the modem status.
    		{
    			modemStatus = mFTDI_Interface.getModemStatus();
    		}
    		
    		//3.4 call the event listeners according to modem status and other status
    		if(modemStatus >= 0)
    		{    			
    		    if(this.mOnPinChangedListener != null)
    		    {
    		    	if((modemStatus & FTDI_Constants.MODEM_STATUS_CTS_MSK) != 
    		    			(oldModemStatus & FTDI_Constants.MODEM_STATUS_CTS_MSK))
    		    		mOnPinChangedListener.onPinChanged(this, FTDI_Constants.FTDI_PinChangeEnum.CTS_CHANGED, 
    		    				((modemStatus & FTDI_Constants.MODEM_STATUS_CTS_MSK) != 0)?true:false );

    		    	if((modemStatus & FTDI_Constants.MODEM_STATUS_DSR_MSK) != 
    		    			(oldModemStatus & FTDI_Constants.MODEM_STATUS_DSR_MSK))
    		    		mOnPinChangedListener.onPinChanged(this, FTDI_Constants.FTDI_PinChangeEnum.DSR_CHANGED, 
    		    				((modemStatus & FTDI_Constants.MODEM_STATUS_DSR_MSK) != 0)?true:false );

    		    	if((modemStatus & FTDI_Constants.MODEM_STATUS_RI_MSK) != 
    		    			(oldModemStatus & FTDI_Constants.MODEM_STATUS_RI_MSK))
    		    		mOnPinChangedListener.onPinChanged(this, FTDI_Constants.FTDI_PinChangeEnum.RI_CHANGED, 
    		    				((modemStatus & FTDI_Constants.MODEM_STATUS_RI_MSK) != 0)?true:false );

    		    	if((modemStatus & FTDI_Constants.MODEM_STATUS_RLSD_MSK) != 
    		    			(oldModemStatus & FTDI_Constants.MODEM_STATUS_RLSD_MSK))
    		    		mOnPinChangedListener.onPinChanged(this, FTDI_Constants.FTDI_PinChangeEnum.CD_CHANGED, 
    		    				((modemStatus & FTDI_Constants.MODEM_STATUS_RLSD_MSK) != 0)?true:false );
    		    }
    		    
    		    if(this.mOnErrorReceivedListener != null)
    		    {
    		    	if((modemStatus & FTDI_Constants.MODEM_STATUS_DR_MSK) != 0)
    		    		mOnErrorReceivedListener.onErrorReceived(this, FTDI_Constants.FTDI_CommErroEnum.DATA_READY);
    		    	if((modemStatus & FTDI_Constants.MODEM_STATUS_OE_MSK) != 0)
    		    		mOnErrorReceivedListener.onErrorReceived(this, FTDI_Constants.FTDI_CommErroEnum.OVERRUN_ERR);
    		    	if((modemStatus & FTDI_Constants.MODEM_STATUS_PE_MSK) != 0)
    		    		mOnErrorReceivedListener.onErrorReceived(this, FTDI_Constants.FTDI_CommErroEnum.PARITY_ERR);
    		    	if((modemStatus & FTDI_Constants.MODEM_STATUS_FE_MSK) != 0)
    		    		mOnErrorReceivedListener.onErrorReceived(this, FTDI_Constants.FTDI_CommErroEnum.FRAME_ERR);
    		    	if((modemStatus & FTDI_Constants.MODEM_STATUS_BI_MSK) != 0)
    		    		mOnErrorReceivedListener.onErrorReceived(this, FTDI_Constants.FTDI_CommErroEnum.BREAK_INT);
    		    	if((modemStatus & FTDI_Constants.MODEM_STATUS_THRE_MSK) != 0)
    		    		mOnErrorReceivedListener.onErrorReceived(this, FTDI_Constants.FTDI_CommErroEnum.TRANS_HOLD_REG);
    		    	if((modemStatus & FTDI_Constants.MODEM_STATUS_TEMT_MSK) != 0)
    		    		mOnErrorReceivedListener.onErrorReceived(this, FTDI_Constants.FTDI_CommErroEnum.TRANS_EMPTY);
    		    	if((modemStatus & FTDI_Constants.MODEM_STATUS_ERFF_MSK) != 0)
    		    		mOnErrorReceivedListener.onErrorReceived(this, FTDI_Constants.FTDI_CommErroEnum.ERROR_RCVR_FIFO);
    		    }
    		    //update the oldModemStatus for next loop.
    		    oldModemStatus = modemStatus;
    		}
    		else//(modemStatus<0)
    		{
    			//TODO: handle the exception in modem status reading
    			//this is error branch, should through a exception and end the execution of this task.
    			continue;
    		}
    		
    	}while(this.isCancelled());
    	
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
