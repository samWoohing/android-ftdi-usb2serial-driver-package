//=====================================================================================================================
//
//	Matlab wrapper of overall MIFARE hacking function GET_KEY
//
//	The Matlab interface for this function will be defined as following: 
//	
//
//	keys = GET_KEY(UID, nT, ks3s, nR_ks1, suc2nT_ks2, ks_pb)
//	-- UID:		Card Unique ID
//	-- nT:		Card challenge
//	-- ks3s:	The 8 ks3s responses got by varying last 3bits of nR_ks1.
//	-- nR_ks1:	The fake nR_ks1 we feed into card, this is the first one among the 8, other 7 values are got from xor this value by 1, 2, 3...7
//	-- suc2nT_ks2: The fake suc2nT_ks2 we feed into card. this remains invariant among 8 trials.
//	-- ks_pb:	The encrypted parity bits for (nR_ks1, suc2nT_ks2). 
//				for a fixed (nR_ks1, suc2nT_ks2), We tried max 256 times to get this. Used to narrow down searching for keys.
//
//	--keys: return value is all possible keys.
//
//	Written by: Shan Song
//	2010-Sep-28
//=====================================================================================================================
#include <malloc.h>
#include "mex.h"
#include "LibSimMiFare.h"

void mexFunction(int nlhs, mxArray *plhs[], int nrhs,const mxArray *prhs[])
{
	//lhs: left-hand-side, means outputs. rhs: right-hand-side, means inputs.
	const mwSize *dims1, *dims2, *dims3, *dims4, *dims5, *dims6;
	mwSize ndim1, ndim2, ndim3,ndim4, ndim5, ndim6;
	mwSize dims_out[2] = {1,1};

	U32 UID, nT, *ks3s, nR_ks1, suc2nT_ks2, num, i;
	U8 ks_pb;
	U64 *keys, *ptr_o;

	//check the number of input and output
	if (nrhs != 6)
	{
		mexErrMsgTxt("Function needs 6 inputs: keys = GET_KEY(UID, nT, ks3s, nR_ks1, suc2nT_ks2, ks_pb)");
	}
	if (nlhs > 1)
	{
		mexErrMsgTxt("Function needs 1 outputs: keys = GET_KEY(UID, nT, ks3s, nR_ks1, suc2nT_ks2, ks_pb)");
	}

	//======input checking=============
	ndim1 = mxGetNumberOfDimensions(prhs[0]);
	dims1 = mxGetDimensions(prhs[0]);
	ndim2 = mxGetNumberOfDimensions(prhs[1]);
	dims2 = mxGetDimensions(prhs[1]);
	ndim3 = mxGetNumberOfDimensions(prhs[2]);
	dims3 = mxGetDimensions(prhs[2]);
	ndim4 = mxGetNumberOfDimensions(prhs[3]);
	dims4 = mxGetDimensions(prhs[3]);
	ndim5 = mxGetNumberOfDimensions(prhs[4]);
	dims5 = mxGetDimensions(prhs[4]);
	ndim6 = mxGetNumberOfDimensions(prhs[5]);
	dims6 = mxGetDimensions(prhs[5]);

	//check if UID is a 32bit unsigned int
	if(mxGetClassID(prhs[0]) != mxUINT32_CLASS )
	{
		mexErrMsgTxt("Function first input should be a 32-bit unsigned integer: keys = GET_KEY(UID, nT, ks3s, nR_ks1, suc2nT_ks2, ks_pb)");
	}
	if (ndim1 != 2 || dims1[0]*dims1[1] >1 )
	{
		mexErrMsgTxt("Function first input should be a 32-bit unsigned integer scalar: keys = GET_KEY(UID, nT, ks3s, nR_ks1, suc2nT_ks2, ks_pb)");
	}

	//check if nT is a 32bit unsigned int
	if(mxGetClassID(prhs[1]) != mxUINT32_CLASS )
	{
		mexErrMsgTxt("Function second input should be a 32-bit unsigned integer: keys = GET_KEY(UID, nT, ks3s, nR_ks1, suc2nT_ks2, ks_pb)");
	}
	if (ndim2 != 2 || dims2[0]*dims2[1] >1 )
	{
		mexErrMsgTxt("Function second input should be a 32-bit unsigned integer scalar: keys = GET_KEY(UID, nT, ks3s, nR_ks1, suc2nT_ks2, ks_pb)");
	}

	//check if ks3s is a array of 8 32bit unsigned ints
	if(mxGetClassID(prhs[2]) != mxUINT32_CLASS )
	{
		mexErrMsgTxt("Function third input should be a 32-bit unsigned integer: keys = GET_KEY(UID, nT, ks3s, nR_ks1, suc2nT_ks2, ks_pb)");
	}
	if (ndim3 != 2 || dims3[0]*dims3[1] !=8 )
	{
		mexErrMsgTxt("Function third input should be a 8-element 32-bit unsigned integer array: keys = GET_KEY(UID, nT, ks3s, nR_ks1, suc2nT_ks2, ks_pb)");
	}

	//check if nR_ks1 is a 32bit unsigned int
	if(mxGetClassID(prhs[3]) != mxUINT32_CLASS )
	{
		mexErrMsgTxt("Function forth input should be a 32-bit unsigned integer: keys = GET_KEY(UID, nT, ks3s, nR_ks1, suc2nT_ks2, ks_pb)");
	}
	if (ndim4 != 2 || dims4[0]*dims4[1] >1 )
	{
		mexErrMsgTxt("Function forth input should be a 32-bit unsigned integer scalar: keys = GET_KEY(UID, nT, ks3s, nR_ks1, suc2nT_ks2, ks_pb)");
	}

	//check if suc2nT_ks2 is a 32bit unsigned int
	if(mxGetClassID(prhs[4]) != mxUINT32_CLASS )
	{
		mexErrMsgTxt("Function fifth input should be a 32-bit unsigned integer: keys = GET_KEY(UID, nT, ks3s, nR_ks1, suc2nT_ks2, ks_pb)");
	}
	if (ndim5 != 2 || dims5[0]*dims5[1] >1 )
	{
		mexErrMsgTxt("Function fifth input should be a 32-bit unsigned integer scalar: keys = GET_KEY(UID, nT, ks3s, nR_ks1, suc2nT_ks2, ks_pb)");
	}

	//check if ks_pb is a 8bit unsigned int
	if(mxGetClassID(prhs[5]) != mxUINT8_CLASS )
	{
		mexErrMsgTxt("Function last input should be a 8-bit unsigned integer: keys = GET_KEY(UID, nT, ks3s, nR_ks1, suc2nT_ks2, ks_pb)");
	}
	if (ndim6 != 2 || dims6[0]*dims6[1] >1 )
	{
		mexErrMsgTxt("Function last input should be a 8-bit unsigned integer scalar: keys = GET_KEY(UID, nT, ks3s, nR_ks1, suc2nT_ks2, ks_pb)");
	}
	
	//get numbers from input
	UID			= *((U32*)mxGetData(prhs[0]));
	nT			= *((U32*)mxGetData(prhs[1]));
	ks3s		= (U32*)mxGetData(prhs[2]);
	nR_ks1		= *((U32*)mxGetData(prhs[3]));
	suc2nT_ks2	= *((U32*)mxGetData(prhs[4]));
	ks_pb		= *((U8*)mxGetData(prhs[5]));

	//call the core function
	keys = GET_KEYS(UID, nT, ks3s, nR_ks1, suc2nT_ks2, ks_pb, &num);

	//allocate result space
	dims_out[1] = num;
	plhs[0]	= mxCreateNumericArray(2, dims_out, mxUINT64_CLASS, mxREAL);

	//copy results into allocated space
	//get output buffer ptr
	ptr_o	= (U64*)mxGetData(plhs[0]);

	//do a copy from states buffer to output buffer.
	for(i=0; i<num; i++)
	{
		ptr_o[i] = keys[i];
	}

	//free memory
	free(keys);
}