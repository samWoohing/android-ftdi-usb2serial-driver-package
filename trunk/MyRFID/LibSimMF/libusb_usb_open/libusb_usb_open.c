//=====================================================================================================================
//
//	Matlab wrapper libusb functions to close an opened usb device
//	Need to receive a device pointer, and return the device handler as uint64 to Matlab environment, 
//	as a opaque pointer for furture usage.
//
//	dev_hdl = libusb_usb_open(dev_ptr)
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
	struct usb_device *dev;
	usb_dev_handle *hdl;
	U64* ptr;

	//check the number of input and output
	if (nrhs != 1)
	{
		mexErrMsgTxt("dev_hdl = libusb_usb_open(dev_ptr): need 1 input, no more no less.");
	}
	if (nlhs > 1)
	{
		mexErrMsgTxt("dev_hdl = libusb_usb_open(dev_ptr): only 1 output, no more no less.");
	}

	//check if the input is scalar
	ndim1 = mxGetNumberOfDimensions(prhs[0]);
	dims1 = mxGetDimensions(prhs[0]);

	if (ndim1 != 2 || dims1[0]*dims1[1] > 1 )
	{
		mexErrMsgTxt("Input should be scalar: dev_hdl = libusb_usb_open(dev_ptr)");
	}
	if(mxGetClassID(prhs[0]) != mxUINT64_CLASS)
	{
		mexErrMsgTxt("Function input should be unsigned 64 bit int: dev_hdl = libusb_usb_open(dev_ptr)");
	}
	//check if a uint64 is the proper type for the return value:
	if(sizeof(usb_dev_handle *) > sizeof(U64))
	{
		mexErrMsgTxt("unsigned int 64 is NOT large enough to store the usb device handler.");
	}

	//usb_init(); /* initialize the library */
    //usb_find_busses(); /* find all busses */
    //usb_find_devices(); /* find all connected devices */

	dev = (struct usb_device*)(*(U64*)(mxGetData(prhs[0])));

	plhs[0] = mxCreateNumericArray(2, dims1, mxUINT64_CLASS, mxREAL);
	ptr = (U64*)mxGetData(plhs[0]);

	if(dev == NULL)
	{
		mexPrintf("Device pointer is null and cannot be opened.\r\n",dev);
		*ptr = (U64)0;
	}
	else
	{
		hdl = usb_open(dev);
		if(hdl == NULL)
			mexPrintf("Device 0x%x and cannot be opened.\r\n",dev);
		else
			mexPrintf("Device 0x%x is opened.\r\n",dev);
		*ptr = (U64) hdl;
	}
}