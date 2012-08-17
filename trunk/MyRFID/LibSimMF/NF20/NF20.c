//=====================================================================================================================
//
//	Matlab wrapper of Non-linear output function (Odd bits only)
//
//	The Matlab interface for this function will be defined as following: 
//	result = NF20(state)
//	result, state should have the same size.
//	result should be 32bit unsigned integer.
//	state should be 64bit unsigned integer
//
//	Written by: Shan Song
//	2010-Sep-18
//=====================================================================================================================
#include "mex.h"
#include "LibSimMiFare.h"

void mexFunction(int nlhs, mxArray *plhs[], int nrhs,const mxArray *prhs[])
{
	//lhs: left-hand-side, means outputs. rhs: right-hand-side, means inputs.
	const mwSize *dims1;
	mwSize ndim1;
	U32 *result, i;
	U64 *state;

	//check the number of input and output
	if (nrhs != 1)
	{
		mexErrMsgTxt("Function needs 1 input: result = NF20(state)");
	}
	if (nlhs > 1)
	{
		mexErrMsgTxt("Function needs 1 output: result = NF20(state)");
	}

	//check if both inputs have the same size
	ndim1 = mxGetNumberOfDimensions(prhs[0]);
	dims1 = mxGetDimensions(prhs[0]);

	if (ndim1 != 2)
	{
		mexErrMsgTxt("Function input should be a 1-D array of numbers: result = NF20(state)");
	}
	if (!(dims1[0] == 1 && dims1[1] > 0 || dims1[0] > 0 && dims1[1] == 1))
	{
		mexErrMsgTxt("Function input should be a 1-D array of numbers: result = NF20(state)");
	}

	//check if input are unsigned 64bit integer
	if( mxGetClassID(prhs[0])!= mxUINT64_CLASS)
	{
		mexErrMsgTxt("Function inputs should be 32bit unsigned integer: result = NF20(state)");
	}

	//get odd data ptr
	state = (U64*)mxGetData(prhs[0]);

	//allocate result matrix
	plhs[0] = mxCreateNumericArray(2, dims1, mxUINT32_CLASS, mxREAL);
	result = (U32*)mxGetData(plhs[0]);

	//loop to calculate all random number output
	for(i=0; i<dims1[0]*dims1[1]; i++)
		result[i] = NF20(state[i]);

	//no need to free anything here.
}