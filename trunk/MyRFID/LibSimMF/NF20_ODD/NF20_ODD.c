//=====================================================================================================================
//
//	Matlab wrapper of Non-linear output function (Odd bits only)
//
//	The Matlab interface for this function will be defined as following: 
//	result = NF20_ODD(odd)
//	result, odd should have the same size.
//	result, odd should all be 32bit unsigned integer
//
//	Written by: Shan Song
//	2010-Sep-13
//=====================================================================================================================
#include "mex.h"
#include "LibSimMiFare.h"

void mexFunction(int nlhs, mxArray *plhs[], int nrhs,const mxArray *prhs[])
{
	//lhs: left-hand-side, means outputs. rhs: right-hand-side, means inputs.
	const mwSize *dims1;
	mwSize ndim1;
	U32 *odd, *result, i;

	//check the number of input and output
	if (nrhs != 1)
	{
		mexErrMsgTxt("Function needs 1 input: result = NF20_ODD(odd)");
	}
	if (nlhs > 1)
	{
		mexErrMsgTxt("Function needs 1 output: result = NF20_ODD(odd)");
	}

	//check if both inputs have the same size
	ndim1 = mxGetNumberOfDimensions(prhs[0]);
	dims1 = mxGetDimensions(prhs[0]);

	if (ndim1 != 2)
	{
		mexErrMsgTxt("Function input should be a 1-D array of numbers: result = NF20_ODD(odd)");
	}
	if (!(dims1[0] == 1 && dims1[1] > 0 || dims1[0] > 0 && dims1[1] == 1))
	{
		mexErrMsgTxt("Function input should be a 1-D array of numbers: result = NF20_ODD(odd)");
	}

	//check if input are unsigned 32bit integer
	if( mxGetClassID(prhs[0])!= mxUINT32_CLASS)
	{
		mexErrMsgTxt("Function inputs should be 32bit unsigned integer: result = NF20_ODD(odd)");
	}

	//get odd data ptr
	odd = (U32*)mxGetData(prhs[0]);

	//allocate result matrix
	plhs[0] = mxCreateNumericArray(2, dims1, mxUINT32_CLASS, mxREAL);
	result = (U32*)mxGetData(plhs[0]);

	//loop to calculate all random number output
	for(i=0; i<dims1[0]*dims1[1]; i++)
		result[i] = NF20_odd(odd[i]);

	//no need to free anything here.
}