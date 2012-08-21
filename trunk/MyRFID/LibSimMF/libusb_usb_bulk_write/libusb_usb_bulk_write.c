//=====================================================================================================================
//
//	Matlab wrapper libusb functions to claim a usb interface
//	Need to receive the device handler as uint64 from Matlab environment, as a opaque pointer to the device.
//
//	result = libusb_usb_claim_interface(usb_dev_hdl, interface)
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
	const mwSize *dims1, *dims2;
	mwSize ndim1, ndim2;
	usb_dev_handle *hdl;
	int usbinterface, result;

	//check the number of input and output
	if (nrhs != 2)
	{
		mexErrMsgTxt("result = libusb_usb_claim_interface(usb_dev_hdl, interface): need 2 inputs, no more no less.");
	}
	if (nlhs > 1)
	{
		mexErrMsgTxt("result = libusb_usb_claim_interface(usb_dev_hdl, interface): only 1 output, no more no less.");
	}

	//check if the inputs are scalar
	ndim1 = mxGetNumberOfDimensions(prhs[0]);
	dims1 = mxGetDimensions(prhs[0]);
	ndim2 = mxGetNumberOfDimensions(prhs[1]);
	dims2 = mxGetDimensions(prhs[1]);
	if (ndim1 != 2 || ndim2 != 2 || dims1[0]*dims1[1] > 1 || dims2[0]*dims2[1] > 1)
	{
		mexErrMsgTxt("result = libusb_usb_claim_interface(usb_dev_hdl, interface) input should be scalar.");
	}
	if(mxGetClassID(prhs[0]) != mxUINT64_CLASS)
	{
		mexErrMsgTxt("Function first input should be unsigned 64 bit int: result = libusb_usb_claim_interface(usb_dev_hdl, interface)");
	}
	if(mxGetClassID(prhs[1]) !=  mxINT32_CLASS)
	{
		mexErrMsgTxt("Function first input should be 32 bit int: result = libusb_usb_claim_interface(usb_dev_hdl, interface)");
	}


	//usb_init(); /* initialize the library */
    //usb_find_busses(); /* find all busses */
    //usb_find_devices(); /* find all connected devices */

	hdl = (usb_dev_handle*)(*(U64*)(mxGetData(prhs[0])));
	usbinterface = *(int*)(mxGetData(prhs[1]));

	plhs[0] = mxCreateNumericArray(2, dims1, mxINT32_CLASS, mxREAL);

	if(hdl == NULL)
	{
		mexPrintf("Device handler is null and cannot claim any interface.\r\n");
		*(int*)mxGetData(plhs[0]) = -1;
	}
	else
	{
		result = usb_claim_interface(hdl,usbinterface);
		if(result < 0)
			mexPrintf("Error when claiming device 0x%x, interface %d return value: %d.\r\n Error string: %s",hdl, usbinterface, result, usb_strerror());
		else
			mexPrintf("Device 0x%x, interface %d successfully claimed. return value: %d.\r\n",hdl, usbinterface, result);

		*(int*)mxGetData(plhs[0]) = result;
	}
}