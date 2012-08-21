//=====================================================================================================================
//
//	Matlab wrapper libusb functions to close an opened usb device
//	Need to receive the device handler as uint64 to Matlab environment, as a opaque pointer to the device.
//
//	result = libusb_usb_close(dev_hdl)
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
	const mwSize *dims1;
	mwSize ndim1;
	usb_dev_handle *hdl;
	int result;

	//check the number of input and output
	if (nrhs != 1)
	{
		mexErrMsgTxt("result = libusb_usb_close(dev_hdl): need 1 input, no more no less.");
	}
	if (nlhs > 1)
	{
		mexErrMsgTxt("result = libusb_usb_close(dev_hdl): only 1 output, no more no less.");
	}

	//check if the input is scalar
	ndim1 = mxGetNumberOfDimensions(prhs[0]);
	dims1 = mxGetDimensions(prhs[0]);

	if (ndim1 != 2 || dims1[0]*dims1[1] > 1 )
	{
		mexErrMsgTxt("Input should be scalar: result = libusb_usb_close(dev_hdl)");
	}
	if(mxGetClassID(prhs[0]) != mxUINT64_CLASS)
	{
		mexErrMsgTxt("Function input should be unsigned 64 bit int: result = libusb_usb_close(dev_hdl)");
	}

	//usb_init(); /* initialize the library */
    //usb_find_busses(); /* find all busses */
    //usb_find_devices(); /* find all connected devices */

	hdl = (usb_dev_handle*)(*(U64*)(mxGetData(prhs[0])));

	plhs[0] = mxCreateNumericArray(2, dims1, mxINT32_CLASS, mxREAL);

	if(hdl == NULL)
	{
		mexPrintf("Device handler is null and does not need to close.\r\n");
		*(int*)mxGetData(plhs[0]) = 0;
	}
	else
	{
		result = usb_close(hdl);
		if(result < 0)
			mexPrintf("Error when closing device: 0x%x, return value: %d.\r\n",hdl,result);
		else
			mexPrintf("Device successfully closed: 0x%x, return value: %d.\r\n",hdl,result);

		*(int*)mxGetData(plhs[0]) = result;
	}
}