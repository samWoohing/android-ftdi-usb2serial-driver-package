/*
*	Mifare crack function rountines for OpenPCD
*	written by: Shan Song	Date: 2012-sept-04
*	songshan99@gmail.com
*
*	This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by 
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/
#include "rc632.h"
#include "mifare_crack.h"

u_int8_t REQA=0x26;
u_int8_t SELECT[2]={0x93,0x20};
u_int8_t SELECT_UID[]={0x93,0x70,0,0,0,0,0};
//maybe we don't need a return value at all...
int mifare_fixed_Nt_attack(struct mifare_crack_params *params)
{
	//send idle command
	opcd_rc632_reg_write(NULL, RC632_REG_COMMAND, RC632_CMD_IDLE);
	
	//disable TX1 and TX2
	opcd_rc632_reg_write(NULL, RC632_REG_TX_CONTROL, 0x58);
	
	//////////////////////////////
	//do REQA
	
	//set 7bit short frame
	opcd_rc632_reg_write(NULL, RC632_REG_BIT_FRAMING, 0x07);
	
	//disable CRC and enable parity
	opcd_rc632_reg_write(NULL, RC632_REG_CHANNEL_REDUNDANCY, 0x03);
	
	//enable TX1 and TX2
	opcd_rc632_reg_write(NULL, RC632_REG_TX_CONTROL, 0x5B);
	
	//do the REQA tranceive
	opcd_rc632_fifo_write(NULL, sizeof(REQA), &REQA, 0);
	opcd_rc632_reg_write(NULL, RC632_REG_COMMAND, RC632_CMD_TRANSCEIVE);
	//opcd_rc632_fifo_read(NULL, 2, poh->data);//read fifo, should get 0x20,00. Do we need to wait here?
	
	//////////////////////////////
	//do anti collision
	
	//tranceive, send 0x93 20
	//read fifo, should get UID BCC
	
	//enable TX CRC and parity, RX CRC disabled
	//load crc initial value, LSB and MSB
	
	//do a tranceive: select(UID): 0x93, 70, UID, BCC 
	//read fifo, should get SAK, with 2-byte CRC-A
	
	//////////////////////////////
	//do Auth block N
	
	//do a tranceive: 0x60 NN CRC_A
	//read fifo, should get 32-bit Nt
	
	//////////////////////////////
	//send the hacking Nr_Ar_Parity
	
	//disable CRC and parity for TX and RX
	
	//do a tranceive: Nr_Ar_Parity, 9bytes with parity bits embedded in bit stream
	//read fifo, should get NACK or nothing
	
	//////////////////////////////
	//prepare information to return through USB
	
	//disable TX1 and TX2 before we leave
	
	
	return 0;
}

