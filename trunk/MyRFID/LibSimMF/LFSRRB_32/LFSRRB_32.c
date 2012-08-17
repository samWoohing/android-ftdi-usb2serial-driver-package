//=====================================================================================================================
//
//	Matlab wrapper of 32-bit Crypto-1 cipher lfsr rollback function
//
//	The Matlab interface for this function will be defined as following: 
//	
//	should have the same size.
//	should all be 32bit unsigned integer
//	[ks, oldstate] = LFSRRB_32(state, input, fdbk)
//	-- ks, oldstate, state, input should have the same size	
//	-- oldstate, state are 64bit unsigned int. (48bit actually)
//	-- ks, input are 32bit unsigned int.
//	-- fdbk is 32bit unsigned int. It's a scalar that will be used for all vector elements.
//
//	Written by: Shan Song
//	2010-Sep-13
//=====================================================================================================================
#include "mex.h"
#include "LibSimMiFare.h"

void mexFunction(int nlhs, mxArray *plhs[], int nrhs,const mxArray *prhs[])
{
	//lhs: left-hand-side, means outputs. rhs: right-hand-side, means inputs.
	const mwSize *dims1, *dims2, *dims3;
	mwSize ndim1, ndim2, ndim3;
	U32 *input, *ks, fdbk, i;
	U64 *state, *oldstate, tmp;

	//check the number of input and output
	if (nrhs != 3)
	{
		mexErrMsgTxt("Function needs 3 inputs: [ks, oldstate] = LFSRRB_32(state, input, fdbk)");
	}
	if (nlhs != 2)
	{
		mexErrMsgTxt("Function needs 2 outputs: [ks, oldstate] = LFSRRB_32(state, input, fdbk)");
	}

	//check if both state and input have the same size
	ndim1 = mxGetNumberOfDimensions(prhs[0]);
	dims1 = mxGetDimensions(prhs[0]);
	ndim2 = mxGetNumberOfDimensions(prhs[1]);
	dims2 = mxGetDimensions(prhs[1]);
	ndim3 = mxGetNumberOfDimensions(prhs[2]);
	dims3 = mxGetDimensions(prhs[2]);

	if (ndim1 != 2 || ndim2 != 2)
	{
		mexErrMsgTxt("Function first and second input should be a 1-D array of numbers: [ks, oldstate] = LFSRRB_32(state, input, fdbk)");
	}
	if (!(dims1[0] == 1 && dims1[1] > 0 || dims1[0] > 0 && dims1[1] == 1) || !(dims2[0] == 1 && dims2[1] > 0 || dims2[0] > 0 && dims2[1] == 1))
	{
		mexErrMsgTxt("Function first and second input should be a 1-D array of numbers: [ks, oldstate] = LFSRRB_32(state, input, fdbk)");
	}
	if(dims1[0]!=dims2[0] || dims1[1]!=dims2[1])
	{
		mexErrMsgTxt("Function first and second input should have the same size: [ks, oldstate] = LFSRRB_32(state, input, fdbk)");
	}

	//check if state is 64 bit unsigned integer
	if(mxGetClassID(prhs[0]) != mxUINT64_CLASS )
	{
		mexErrMsgTxt("Function first input should be 64bit unsigned integer: LFSRRB_32(state, input, fdbk)");
	}
	//check if input is 32 bit unsigned integer
	if(mxGetClassID(prhs[1]) != mxUINT32_CLASS )
	{
		mexErrMsgTxt("Function second input should be 32bit unsigned integer: LFSRRB_32(state, input, fdbk)");
	}

	//check if fdbk is 32 bit unsigned integer
	if(mxGetClassID(prhs[2]) != mxUINT32_CLASS )
	{
		mexErrMsgTxt("Function third input should be 32bit unsigned integer scalar: LFSRRB_32(state, input, fdbk)");
	}
	//check if fdbk is scalar
	if (ndim3 != 2 || dims3[0]*dims3[1] !=1)
	{
		mexErrMsgTxt("Function third input and second input should be a 1-D array of numbers: [ks, oldstate] = LFSRRB_32(state, input, fdbk)");
	}

	//get state and input data ptr
	state = (U64*)mxGetData(prhs[0]);
	input = (U32*)mxGetData(prhs[1]);
	fdbk = *((U32*)mxGetData(prhs[2]));

	//allocate result matrix
	plhs[0] = mxCreateNumericArray(2, dims1, mxUINT32_CLASS, mxREAL);
	plhs[1] = mxCreateNumericArray(2, dims1, mxUINT64_CLASS, mxREAL);
	ks = (U32*)mxGetData(plhs[0]);
	oldstate = (U64*)mxGetData(plhs[1]);

	//loop to calculate all cipher keystream output
	for(i=0; i<dims1[0]*dims1[1]; i++)
	{
		tmp = state[i];
		ks[i] = LFSRRB_32bit(&tmp, input[i], fdbk);
		oldstate[i] = tmp;
	}

	//no need to free anything here.
}