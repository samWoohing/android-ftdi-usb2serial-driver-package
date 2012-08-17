//=====================================================================================================================
//
//	Matlab wrapper of Random Number Generator
//
//	The Matlab interface for this function will be defined as following: 
//	[RandNum] = RNG(Seed, Step)
//	RandNum, Seed and Step should have the same size.
//	RandNum, Seed and Step should all be 32bit unsigned integer
//
//	Written by: Shan Song
//	2010-Aug-31
//=====================================================================================================================
#include "mex.h"
#include "LibSimMiFare.h"

void mexFunction(int nlhs, mxArray *plhs[], int nrhs,const mxArray *prhs[])
{
	//lhs: left-hand-side, means outputs. rhs: right-hand-side, means inputs.
	const mwSize *dims1, *dims2;
	mwSize ndim1, ndim2;
	U32 *seed, *step, *randnum, i;

	//check the number of input and output
	if (nrhs != 2)
	{
		mexErrMsgTxt("Function needs 2 inputs: [RandNum] = RNG(Seed, Step)");
	}
	if (nlhs > 1)
	{
		mexErrMsgTxt("Function needs 1 output: [RandNum] = RNG(Seed, Step)");
	}

	//check if both inputs have the same size
	ndim1 = mxGetNumberOfDimensions(prhs[0]);
	dims1 = mxGetDimensions(prhs[0]);
	ndim2 = mxGetNumberOfDimensions(prhs[1]);
	dims2 = mxGetDimensions(prhs[1]);
	if (ndim1 != 2 || ndim2 != 2)
	{
		mexErrMsgTxt("Function input should be a 1-D array of numbers: [RandNum] = RNG(Seed, Step)");
	}
	if (!(dims1[0] == 1 && dims1[1] > 0 || dims1[0] > 0 && dims1[1] == 1) || !(dims2[0] == 1 && dims2[1] > 0 || dims2[0] > 0 && dims2[1] == 1))
	{
		mexErrMsgTxt("Function input should be a 1-D array of numbers: [RandNum] = RNG(Seed, Step)");
	}
	if(dims1[0]!=dims2[0] || dims1[1]!=dims2[1])
	{
		mexErrMsgTxt("Function first and second input should have the same size: [RandNum] = RNG(Seed, Step)");
	}

	//check if both inputs are unsigned 32bit integer
	if( mxGetClassID(prhs[0])!= mxUINT32_CLASS || mxGetClassID(prhs[1]) != mxUINT32_CLASS )
	{
		mexErrMsgTxt("Function inputs should be 32bit unsigned integer: [RandNum] = RNG(Seed, Step)");
	}

	//get seed and step data ptr
	seed = (U32*)mxGetData(prhs[0]);
	step = (U32*)mxGetData(prhs[1]);

	//allocate result matrix
	plhs[0] = mxCreateNumericArray(2, dims1, mxUINT32_CLASS, mxREAL);
	randnum = (U32*)mxGetData(plhs[0]);

	//loop to calculate all random number output
	for(i=0; i<dims1[0]*dims1[1]; i++)
		randnum[i] = RNG(seed[i],step[i]);

	//no need to free anything here.
}