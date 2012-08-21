//=====================================================================================================================
//
//	Matlab wrapper libusb functions to set configuration for a device
//	Need to receive the device handler as uint64 from Matlab environment, as a opaque pointer to the device.
//
//	per libusb-win32 forum's explanation, the usb_set_configuration needs to be called before claiming a usb device,
//	or the usb_claim_interface/usb_release_interface will return -22 (-EINVAL) error.
//
//	result = libusb_usb_set_configuration(usb_dev_hdl, configuration)
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
	int configuration, result;

	//check the number of input and output
	if (nrhs != 2)
	{
		mexErrMsgTxt("result = libusb_usb_set_configuration(usb_dev_hdl, configuration): need 2 inputs, no more no less.");
	}
	if (nlhs > 1)
	{
		mexErrMsgTxt("result = libusb_usb_set_configuration(usb_dev_hdl, configuration): only 1 output, no more no less.");
	}

	//check if the inputs are scalar
	ndim1 = mxGetNumberOfDimensions(prhs[0]);
	dims1 = mxGetDimensions(prhs[0]);
	ndim2 = mxGetNumberOfDimensions(prhs[1]);
	dims2 = mxGetDimensions(prhs[1]);
	if (ndim1 != 2 || ndim2 != 2 || dims1[0]*dims1[1] > 1 || dims2[0]*dims2[1] > 1)
	{
		mexErrMsgTxt("result = libusb_usb_set_configuration(usb_dev_hdl, configuration) input should be scalar");
	}
	if(mxGetClassID(prhs[0]) != mxUINT64_CLASS)
	{
		mexErrMsgTxt("Function first input should be unsigned 64 bit int: result = libusb_usb_set_configuration(usb_dev_hdl, configuration)");
	}
	if(mxGetClassID(prhs[1]) !=  mxINT32_CLASS)
	{
		mexErrMsgTxt("Function second input should be 32 bit int: result = libusb_usb_set_configuration(usb_dev_hdl, configuration)");
	}


	//usb_init(); /* initialize the library */
    //usb_find_busses(); /* find all busses */
    //usb_find_devices(); /* find all connected devices */

	hdl = (usb_dev_handle*)(*(U64*)(mxGetData(prhs[0])));
	configuration = *(int*)(mxGetData(prhs[1]));

	plhs[0] = mxCreateNumericArray(2, dims1, mxINT32_CLASS, mxREAL);

	if(hdl == NULL)
	{
		mexPrintf("Device handler is null and cannot set configuration.\r\n");
		*(int*)mxGetData(plhs[0]) = -1;
	}
	else
	{
		result = usb_set_configuration(hdl,configuration);
		if(result < 0)
			mexPrintf("Error when setting device 0x%x, configuration: %d,  return value: %d.\r\n Error string: %s\r\n",hdl, configuration, result, usb_strerror());
		else
			mexPrintf("Device 0x%x configuration %d set successfully. return value: %d.\r\n",hdl,configuration,result);

		*(int*)mxGetData(plhs[0]) = result;
	}
}