//=====================================================================================================================
//
//	Crypto-1 functions library
//	This library includes functions that implements crypto-1 
//	algorithm. Its purpose is to create a simulated MiFare Classic
//	under Matlab command line.
//
//	Written by: Shan Song
//	2010-Aug-31
//=====================================================================================================================

//=====================================================================================================================
//		Endianess convention of this code
//=====================================================================================================================
//	For all communications between tag and reader, here we use the same endian as described in: 
//	Dismantling MIFARE classic, Flavio D. Garcia, et al. Chapter 2.
//	This is also the notation convention used when presented in ISO/IEC 14443
//
//	For a single byte:
//	1. When transmitting a byte, LSB is transmitted first, MSB is transmitted last.
//
//	2. When representing a transmitted byte, MSB at left, LSB at right. Should be the same as what we read from the chip fifo. (need to confirm this)

//	For a 32bit word or longer sequence that includes several bytes and represents a trace:
//	1. When representing a trace, the byte transmitted first is written on left (Most significant byte of 32bit uint). 
//		The byte transmitted last is written on right (Least significant byte of 32bit uint).
//
//	2. Every byte in the trace are represented and transmitted as stated above
//
//	3. For 32bit word trace that has 4 bytes, assume bits are transmitted as b0, b1,... b31. (b0 first and b31 last),
//		then following the above representing convention, these bits when represented as a 32bit integer, are written as:
//
//		(MSB of 32bit uint) <------------------------------------------------------------------------------> (LSB of 32bit uint)
//		b7,b6,b5,b4,b3,b2,b1,b0, b15,b14,b13,b12,b11,b10,b9,b8, b23,b22,b21,b20,b19,b18,b17,b16, b31,b30,b29,b28,b27,b26,b25,b24
//
//		In order to access bit i (0 <=i<=31, i is the bit sending order) of this integer, we can use following macro:
//		#define GETBIT(x, i) (x>>(i^24))&1
//		So accessing i=0 will direct you to 24th bit, and so on...
//
//=====================================================================================================================
//		RNG feeding
//=====================================================================================================================
//	1. The oldest bit in RNG status register is fed into cipher to get the oldest keystream bit.
//
//	2. The newest bit in RNG status register is calculated from feedback equation.
//
//	3. left or right shifting on RNG is equivalent, as long as we change the feedback bits equation.
//
//	4. "old" means the bit is sent out first. "newest" means the bit is sent out late.
//
//=====================================================================================================================
//		LFSR feeding
//=====================================================================================================================
//	1. Use the Crypto1 Cipher.png convention. MSB is on the left, marked as b0. LSB on the right, marked as b47
//	2. LFSR rolls to the left, or say toward MSB.
//	3. Newest bit is LSB, b47, calculated from linear feedback.

#define U32 unsigned int
#define U64 unsigned long long
#define U8  unsigned char

//The random number generator
U32 RNG(U32 seed, U32 step);

//The Nonlinear output function
U32 NF20(U64 state);
U32 NF20_odd(U32 odd);
//The linear feedback function
U32 LF18(U64 state);

//The 32bit Crypto1 run
typedef enum  {NONE = 0, LINEAR=1, NONLINEAR=2, BOTH=3} FEEDBACK;
U32 CRYPTO1_bit(U64 *state, U32 in_bit, FEEDBACK fdbk);
U32 CRYPTO1_32(U64 *state, U32 in, FEEDBACK fdbk);
U32 CRYPTO1_4(U64 *state, U32 in_4bit, FEEDBACK fdbk);

//Non-linear output function reverse
U32 NF20_DIST_FILTER(U8 *result, U32 ks3_hlf, U32 dist);
U64 *NF20_REV(U32 *num, U32 *ks3);

//The LFSR roll-back function
U32 LFSRRB_1bit(U64 *state, U32 in_bit, FEEDBACK fdbk);
U32 LFSRRB_32bit(U64 *state, U32 in_32bit, FEEDBACK fdbk);
U32 LFSRRB_32bit(U64 *state, U32 in_4bit, FEEDBACK fdbk);

U8 PARITY(U32 nR, U32 suc2nT);
U64* GET_KEYS(U32 UID, U32 nT, U32* ks3s, U32 nR_ks1, U32 suc2nT_ks2, U8 ks_pb, U32 *num);
