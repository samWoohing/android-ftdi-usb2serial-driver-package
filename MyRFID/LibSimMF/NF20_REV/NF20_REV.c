//=====================================================================================================================
//
//	Matlab wrapper of Non-linear function reverse
//
//	The Matlab interface for this function will be defined as following: 
//	
//	state = NF20_REV(ks3)
//	-- ks3 is the 8 4-bit nonces that xor with NACK, in 8 different authorization trials. ks3 is unsigned 32bit integer.
//	-- state are 64bit unsigned int. (48bit actually). It includes all possible states that can generate ks3.
//
//	Written by: Shan Song
//	2010-Sep-17
//=====================================================================================================================
#include <malloc.h>
#include "mex.h"
#include "LibSimMiFare.h"

void mexFunction(int nlhs, mxArray *plhs[], int nrhs,const mxArray *prhs[])
{
	//lhs: left-hand-side, means outputs. rhs: right-hand-side, means inputs.
	const mwSize *dims1;
	mwSize dims_out[2] = {1,1};
	mwSize ndim1;
	U32 *ks3, num, i;
	U64 *states, *ptr_o;

	//check the number of input and output
	if (nrhs != 1)
	{
		mexErrMsgTxt("Function needs 1 inputs: state = NF20_REV(ks3)");
	}
	if (nlhs > 1)
	{
		mexErrMsgTxt("Function needs 1 outputs: state = NF20_REV(ks3)");
	}

	//check if input ks3 has 8 elements
	ndim1 = mxGetNumberOfDimensions(prhs[0]);
	dims1 = mxGetDimensions(prhs[0]);

	if (ndim1 != 2 )
	{
		mexErrMsgTxt("Function input should be a 1-D array of numbers: state = NF20_REV(ks3)");
	}
	if (!(dims1[0] == 1 && dims1[1] == 8 || dims1[0] == 8 && dims1[1] == 1))
	{
		mexErrMsgTxt("Function input should be a 1-D array of 8 32bit unsigned integer numbers: state = NF20_REV(ks3)");
	}

	//check if ks3 is 32 bit unsigned integer
	if(mxGetClassID(prhs[0]) != mxUINT32_CLASS )
	{
		mexErrMsgTxt("Function input should be 32bit unsigned integer: state = NF20_REV(ks3)");
	}
	//get input data
	ks3 = (U32*)mxGetData(prhs[0]);

	//run NF20 reverse
	states = NF20_REV(&num, ks3);
	
	//Judge if the return is nothing, TBD here...

	dims_out[1] = num;
	//allocate result matrix
	plhs[0]	= mxCreateNumericArray(2, dims_out, mxUINT64_CLASS, mxREAL);
	//get output buffer ptr
	ptr_o	= (U64*)mxGetData(plhs[0]);

	//do a copy from states buffer to output buffer.
	for(i=0; i<num; i++)
	{
		ptr_o[i] = states[i];
	}
	//no need to free anything here.
	free(states);
}