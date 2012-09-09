/*
*	data structure that used to describe the input parameter
*	of mifare crack functions. This data is from host USB comm, 
* 	and stored in "data" field of struct openpcd_hdr.
*/

struct mifare_crack_params{
	//the result of function execution
	int8_t result;
	//block that we'd like to hack
	u_int8_t BLOCK;
	//UID, BCC of the card
	u_int8_t UID_BCC[5];
	//expected tag Nt. We expect the same Nt every time
	//u_int32_t Nt_expected;
	//actual tag Nt. The actual Nt genareted by the Mifare tag.
	u_int8_t Nt_actual[4];
	//Nr_Ar_Parity, reader's response to tag's Nt, with parity bits embedded into it, 9 bytes total!!
	u_int8_t Nr_Ar_Parity[9];
	//Is the Mifare tag respond to the given Nr_Ar_Parity, can merge into result!
	//u_int8_t IsResponded;
	//4-bit NACK encrypted, responded by the Mifare tag to the given Nr_Ar_Parity
	u_int8_t NACK_encrypted;
}__attribute__ ((packed));

extern int mifare_fixed_Nt_attack(struct mifare_crack_params *params);
extern void clearFlagIdleTimeout();
