//=====================================================================================================================
//
//	Matlab wrapper libusb functions to do a bulk read on a given endpoint
//	Need to receive the device handler as uint64 from Matlab environment, as a opaque pointer to the device.
//
//	[bytes, result] = libusb_usb_bulk_read(usb_dev_hdl, endpoint, size, timeout)
//
//	Written by: Shan Song
//	2012-Aug-21
//=====================================================================================================================
#include "mex.h"
#include "LibSimMiFare.h"
#include "lusb0_usb.h"


void mexFunction(int nlhs, mxArray *plhs[], int nrhs,const mxArray *prhs[])
{
	//lhs: left-hand-side, means outputs. rhs: right-hand-side, means inputs.
	const mwSize *dims1, *dims2, *dims3, *dims4;
	mwSize dims_read[2] = {1,1};
	mwSize ndim1, ndim2, ndim3, ndim4;
	usb_dev_handle *hdl;
	int usbendpoint, readsize, timeout, result, i;
	char *data, *ptr;

	//check the number of input and output
	if (nrhs != 4)
	{
		mexErrMsgTxt("[bytes, result] = libusb_usb_bulk_read(usb_dev_hdl, endpoint, size, timeout): need 4 inputs, no more no less.");
	}
	if (nlhs > 2)
	{
		mexErrMsgTxt("[bytes, result] = libusb_usb_bulk_read(usb_dev_hdl, endpoint, size, timeout): max 2 outputs, no more.");
	}

	//check if the inputs are scalar
	ndim1 = mxGetNumberOfDimensions(prhs[0]);
	dims1 = mxGetDimensions(prhs[0]);
	ndim2 = mxGetNumberOfDimensions(prhs[1]);
	dims2 = mxGetDimensions(prhs[1]);
	ndim3 = mxGetNumberOfDimensions(prhs[2]);
	dims3 = mxGetDimensions(prhs[2]);
	ndim4 = mxGetNumberOfDimensions(prhs[3]);
	dims4 = mxGetDimensions(prhs[3]);
	if (ndim1 != 2 || ndim2 != 2 || ndim3 != 2 || ndim4 != 2 || dims1[0]*dims1[1] > 1 || dims2[0]*dims2[1] > 1 || dims3[0]*dims3[1] > 1 || dims4[0]*dims4[1] > 1)
	{
		mexErrMsgTxt("[bytes, result] = libusb_usb_bulk_read(usb_dev_hdl, endpoint, size, timeout) inputs should be scalar.");
	}
	if(mxGetClassID(prhs[0]) != mxUINT64_CLASS)
	{
		mexErrMsgTxt("Function first input should be unsigned 64 bit int: [bytes, result] = libusb_usb_bulk_read(usb_dev_hdl, endpoint, size, timeout)");
	}
	if(mxGetClassID(prhs[1]) !=  mxINT32_CLASS)
	{
		mexErrMsgTxt("Function second input should be 32 bit int: [bytes, result] = libusb_usb_bulk_read(usb_dev_hdl, endpoint, size, timeout)");
	}
	if(mxGetClassID(prhs[2]) !=  mxINT32_CLASS)
	{
		mexErrMsgTxt("Function third input should be 32 bit int: [bytes, result] = libusb_usb_bulk_read(usb_dev_hdl, endpoint, size, timeout)");
	}
	if(mxGetClassID(prhs[3]) !=  mxINT32_CLASS)
	{
		mexErrMsgTxt("Function fourth input should be 32 bit int: [bytes, result] = libusb_usb_bulk_read(usb_dev_hdl, endpoint, size, timeout)");
	}


	//usb_init(); /* initialize the library */
    //usb_find_busses(); /* find all busses */
    //usb_find_devices(); /* find all connected devices */

	hdl = (usb_dev_handle*)(*(U64*)(mxGetData(prhs[0])));
	usbendpoint = *(int*)(mxGetData(prhs[1]));
	readsize = *(int*)(mxGetData(prhs[2]));
	timeout = *(int*)(mxGetData(prhs[3]));

	//plhs[0] = mxCreateNumericArray(2, dims1, mxINT32_CLASS, mxREAL);
	

	if(hdl == NULL)
	{
		mexPrintf("Device handler is null and cannot do any buld read.\r\n");
		//return a byte anyway
		plhs[0] = mxCreateNumericArray(2, dims1, mxUINT8_CLASS, mxREAL);
		*(unsigned char*)mxGetData(plhs[0]) = 0;
		//and return error code -1
		if(nlhs ==2 )
		{
			plhs[1] = mxCreateNumericArray(2, dims1, mxINT32_CLASS, mxREAL);
			*(int*)mxGetData(plhs[1]) = -1;
		}
	}
	else
	{
		data = (char*)mxMalloc(readsize);
		mexPrintf("bulk read size: %d\r\n", readsize);
		result = usb_bulk_read(hdl,usbendpoint,data,readsize,timeout);

		if(result < 0)
		{
			mexPrintf("Error when bulk reading device 0x%x, endpoint: %d return value: %d.\r\n Error string: %s\r\n",hdl, usbendpoint, result, usb_strerror());
			//return a byte anyway
			plhs[0] = mxCreateNumericArray(2, dims1, mxUINT8_CLASS, mxREAL);
			*(unsigned char*)mxGetData(plhs[0]) = 0;
		}
		else
		{
			mexPrintf("Device 0x%x, endpoint %d bulk read done. num of bytes read: %d.\r\n",hdl, usbendpoint, result);

			dims_read[0]=1;
			dims_read[1]=result;
			plhs[0] = mxCreateNumericArray(2,dims_read,mxUINT8_CLASS,mxREAL);
			//do the memory copy
			ptr = (char*)mxGetData(plhs[0]);
			for(i=0; i<result; i++)
				ptr[i] = data[i];
		}

		//if we have a second result to return
		if(nlhs ==2 )
		{
			plhs[1] = mxCreateNumericArray(2, dims1, mxINT32_CLASS, mxREAL);
			*(int*)mxGetData(plhs[1]) = result;
		}
		//do not forget to free the memory
		mxFree(data);
	}
}