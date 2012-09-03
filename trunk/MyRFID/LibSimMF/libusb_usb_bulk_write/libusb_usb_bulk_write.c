//=====================================================================================================================
//
//	Matlab wrapper libusb functions to claim a usb interface
//	Need to receive the device handler as uint64 from Matlab environment, as a opaque pointer to the device.
//
//	result = libusb_usb_bulk_write(usb_dev_hdl, endpoint, data, timeout)
//
//	TODO: all below this line needs to be fixed.
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
	int usbendpoint, writesize, timeout, result, i, *ptr_result;
	unsigned char *data;

	//check the number of input and output
	if (nrhs != 4)
	{
		mexErrMsgTxt("result = libusb_usb_bulk_write(usb_dev_hdl, endpoint, data, timeout): need 4 inputs, no more no less.");
	}
	if (nlhs > 1)
	{
		mexErrMsgTxt("result = libusb_usb_bulk_write(usb_dev_hdl, endpoint, data, timeout): max 1 output, no more.");
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
	if (ndim1 != 2 || ndim2 != 2 || ndim3 != 2 || ndim4 != 2 || dims1[0]*dims1[1] > 1 || dims2[0]*dims2[1] > 1 || (dims3[0]!=1 && dims3[1]!=1) || dims4[0]*dims4[1] > 1)
	{
		mexErrMsgTxt("result = libusb_usb_bulk_write(usb_dev_hdl, endpoint, data, timeout) inputs should be scalar.");
	}
	if(mxGetClassID(prhs[0]) != mxUINT64_CLASS)
	{
		mexErrMsgTxt("Function first input should be unsigned 64 bit int: result = libusb_usb_bulk_write(usb_dev_hdl, endpoint, data, timeout)");
	}
	if(mxGetClassID(prhs[1]) !=  mxINT32_CLASS)
	{
		mexErrMsgTxt("Function second input should be 32 bit int: result = libusb_usb_bulk_write(usb_dev_hdl, endpoint, data, timeout)");
	}
	if(mxGetClassID(prhs[2]) !=  mxUINT8_CLASS)
	{
		mexErrMsgTxt("Function third input should be unsigned byte array: result = libusb_usb_bulk_write(usb_dev_hdl, endpoint, data, timeout)");
	}
	if(mxGetClassID(prhs[3]) !=  mxINT32_CLASS)
	{
		mexErrMsgTxt("Function fourth input should be 32 bit int: result = libusb_usb_bulk_write(usb_dev_hdl, endpoint, data, timeout)");
	}


	//usb_init(); /* initialize the library */
    //usb_find_busses(); /* find all busses */
    //usb_find_devices(); /* find all connected devices */

	hdl = (usb_dev_handle*)(*(U64*)(mxGetData(prhs[0])));
	usbendpoint = *(int*)(mxGetData(prhs[1]));
	data = (unsigned char*)(mxGetData(prhs[2]));
	timeout = *(int*)(mxGetData(prhs[3]));	

	plhs[0] = mxCreateNumericArray(2, dims1, mxINT32_CLASS, mxREAL);
	ptr_result = (int*)mxGetData(plhs[0]);
	if(hdl == NULL)
	{
		mexPrintf("Device handler is null and cannot do any bulk read.\r\n");
		*ptr_result = -1;
	}
	else
	{
		writesize = dims3[0]*dims3[1];
		mexPrintf("usb bulk write size: %d\r\n",writesize);
		result = usb_bulk_write(hdl,usbendpoint,(char*)data,writesize,timeout);

		if(result < 0)
			mexPrintf("Error when bulk writing device 0x%x, endpoint: %d return value: %d.\r\n Error string: %s\r\n",hdl, usbendpoint, result, usb_strerror());
		else
			mexPrintf("Device 0x%x, endpoint %d bulk write done. num of bytes written: %d.\r\n",hdl, usbendpoint, result);

		//return error code
		plhs[0] = mxCreateNumericArray(2, dims1, mxUINT8_CLASS, mxREAL);
		*ptr_result = result;	
	}
}