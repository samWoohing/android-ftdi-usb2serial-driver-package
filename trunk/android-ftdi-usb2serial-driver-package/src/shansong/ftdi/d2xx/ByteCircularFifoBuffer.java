package shansong.ftdi.d2xx;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;


// TODO: Auto-generated Javadoc
/**
 * The Class ByteCircularFifoBuffer. This is a class of byte data fifo implement on a circular buffer.
 * This class is used by FTDI_SerialPortReadWriteTask class for data exchange between background task
 * of USB operation and foreground task to read and write data to the serial port.
 * This class is NOT thread safe because it doesn't exclusively lock the buffer to prevent asynchronous
 * readings/writings. But for one reading task and one writing task going on asynchronously, this class
 * is safe under this condition.
 */
public class ByteCircularFifoBuffer {
	
	//TODO: need to define my own exceptions to cover the exceptional cases.
	
	/** The last. */
	//"first" is the index for first element. 
	//"last" is the index for last element+1. or say, it points to the first empty slot.
	int first, last; 
	
	/** The fifo buffer. */
	byte[] mFifoBuffer;
	
	/**
	 * Instantiates a new byte circular fifo buffer.
	 *
	 * @param size the size
	 */
	public ByteCircularFifoBuffer(int size)
	{
		first = 0;
		last = 0;
		//Always leave one extra items, for marking "buffer full".
		//and when buffer is full, the actual filled-in size equals to "size"
		mFifoBuffer = new byte[size+1];
	}
	
	/**
	 * Read data.
	 *
	 * @param buf the buf
	 * @param startIndex the start index
	 * @param length the length
	 * @return the int
	 */
	public int readData(byte[] buf, int startIndex, int length)
	{
		int numRead=0;
		while(numRead<length)
		{
			if(first == last) break;//this means the fifo is empty
			buf[startIndex]=mFifoBuffer[first];
			startIndex++;
			numRead++;
			first=(first++) % mFifoBuffer.length;//always update "first" after doing the actual copy.
			
		}
		if(numRead<length)
		{
			//throw the exception of buffer underflow
			throw new BufferUnderflowException();
		}
		
		return numRead;
	}
	
	/**
	 * Write data.
	 *
	 * @param buf the buf
	 * @param startIndex the start index
	 * @param length the length
	 * @return the int
	 */
	public int writeData(byte[] buf, int startIndex, int length)
	{
		int numWrite=0;
		while(numWrite<length)
		{
			if((last+1)% mFifoBuffer.length == first) break;//this means the fifo is already full
			mFifoBuffer[last]=buf[startIndex];
			startIndex++;
			numWrite++;
			last=(last++) % mFifoBuffer.length;
		}
		if(numWrite<length)
		{
			//throw the exception of buffer overflow
			throw new BufferOverflowException();
		}
		return numWrite;
	}
	
	/**
	 * Existing.
	 *
	 * @return the int
	 */
	public int occupiedLength()
	{
		return (last-first) % mFifoBuffer.length;
	}
	
	/**
	 * Remaining.
	 *
	 * @return the int
	 */
	public int freeLength()
	{
		return mFifoBuffer.length-1 - ((last-first) % mFifoBuffer.length);//remember we allocated a size+1 buffer.
	}
	
	/**
	 * Reset the Fifo buffer. Just let the first/last index return to 0
	 */
	public void reset()
	{
		first = 0;
		last = 0;
	}
}
