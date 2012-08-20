//=====================================================================================================================
//
//	Matlab wrapper libusb functions to find OpenPCD device connected to the current system
//	Need to return the device pointer as uint64 to Matlab environment, as a opaque pointer for furture usage.
//
//	dev_ptr = GetOpenPcdUsbDev(vendorID, ProductID)
//	Written by: Shan Song
//	2012-Aug-21
//=====================================================================================================================
#include "mex.h"
#include "LibSimMiFare.h"
#include "lusb0_usb.h"

struct usb_device *find_dev(unsigned short my_vid, unsigned short my_pid)
{	//note that this function only returns the first match it finds
    struct usb_bus *bus;
    struct usb_device *dev;
    
    for(bus = usb_get_busses(); bus; bus = bus->next)
    {
        for(dev = bus->devices; dev; dev = dev->next)
        {
            if(dev->descriptor.idVendor == my_vid
            && dev->descriptor.idProduct == my_pid)
            {
                return dev;
            }
        }
    }
    return NULL;
}

void mexFunction(int nlhs, mxArray *plhs[], int nrhs,const mxArray *prhs[])
{
	//lhs: left-hand-side, means outputs. rhs: right-hand-side, means inputs.
	const mwSize *dims1, *dims2;
	mwSize ndim1, ndim2;
	struct usb_device* dev;
	unsigned short vid, pid;
	U64 *ptr;

	//check the number of input and output
	if (nrhs != 2)
	{
		mexErrMsgTxt("dev_ptr = GetOpenPcdUsbDev(vendorID, ProductID): need 2 inputs, no more no less.");
	}
	if (nlhs != 1)
	{
		mexErrMsgTxt("dev_ptr = GetOpenPcdUsbDev(vendorID, ProductID): only 1 output, no more no less.");
	}

	//check if the vendorID and productID is scalar
	ndim1 = mxGetNumberOfDimensions(prhs[0]);
	dims1 = mxGetDimensions(prhs[0]);
	ndim2 = mxGetNumberOfDimensions(prhs[1]);
	dims2 = mxGetDimensions(prhs[1]);
	if (ndim1 != 2 || ndim2 != 2 || dims1[0]*dims1[1] > 1 || dims2[0]*dims2[1] > 1)
	{
		mexErrMsgTxt("Inputs should be scalars: dev_ptr = GetOpenPcdUsbDev(vendorID, ProductID)");
	}
	if(mxGetClassID(prhs[0]) != mxUINT16_CLASS ||  mxGetClassID(prhs[1]) != mxUINT16_CLASS)
	{
		mexErrMsgTxt("Function input should be unsigned 16 bit int: dev_ptr = GetOpenPcdUsbDev(vendorID, ProductID)");
	}
	//check if a uint64 is the proper type for the return value:
	if(sizeof(struct usb_device *) > sizeof(U64))
	{
		mexErrMsgTxt("unsigned int 64 is NOT large enough to store the pointer to the usb device.");
	}

	usb_init(); /* initialize the library */
    usb_find_busses(); /* find all busses */
    usb_find_devices(); /* find all connected devices */

	vid = (*(unsigned short*)(mxGetData(prhs[0])));
	pid = (*(unsigned short*)(mxGetData(prhs[1])));

	dev = find_dev(vid, pid);
	if(dev == NULL)
		mexPrintf("Device with VID 0x%x and PID 0x%x cannot be found.\r\n",vid,pid);

	plhs[0] = mxCreateNumericArray(2, dims1, mxUINT64_CLASS, mxREAL);
	ptr = (U64*)mxGetData(plhs[0]);
	*ptr = (U64) dev;
}