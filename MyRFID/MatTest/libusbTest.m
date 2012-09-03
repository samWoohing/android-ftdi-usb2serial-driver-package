%test the libusb related functions
vidOpenPCD = uint16(hex2dec('16C0'));
pidOpenPCD = uint16(hex2dec('076B'));

interface0 = int32(0);

endpoint1=int32(hex2dec('1'));
endpoint2=int32(hex2dec('82')); %note that read endpoint always begins with 8

readsz=int32(4);
timeout = int32(255);

config1=int32(1);%for OpenPCD, only 1 will work, 2 does not exist.
pcd_dev = GetOpenPcdUsbDev(vidOpenPCD,pidOpenPCD)
pcd_dev_hdl = libusb_usb_open(pcd_dev)
result = libusb_usb_set_configuration(pcd_dev_hdl, config1);
result = libusb_usb_claim_interface(pcd_dev_hdl, interface0)

%bytedata = int8([22, 0, 17, 1]);
%result = libusb_usb_bulk_write(pcd_dev_hdl, endpoint1, bytedata, timeout)
%[bytes, result] = libusb_usb_bulk_read(pcd_dev_hdl, endpoint2, readsz, timeout)

%[regvalue, flag, result] = OpenPCD_ReadReg(pcd_dev_hdl, 34)

%try to do a anti-collision stage on a Mifare card

%need to refer to function rc632_iso14443a_transceive_sf (short frame
%transmitting function)
%Following sequence can now successfully get responds of REQA from card.!
%RC632_REG_BIT_FRAMING=15
result = OpenPCD_WriteReg(pcd_dev_hdl, 17, 93)%Enable the TX pins! don't forget!
result = OpenPCD_WriteReg(pcd_dev_hdl, 1, 0) %idle command
result = OpenPCD_WriteReg(pcd_dev_hdl, 15, 7)%set 7bit short frame,
result = OpenPCD_WriteReg(pcd_dev_hdl, 34, 3)%disable crc and enable parity

%req type A (0x26)
result = OpenPCD_WriteFIFO(pcd_dev_hdl,38) 
result = OpenPCD_WriteReg(pcd_dev_hdl, 1, 30) %tranceive command
[bytes, result] = OpenPCD_ReadFIFO(pcd_dev_hdl, 2)

%go back to 8 bit standard frame
%result = OpenPCD_WriteReg(pcd_dev_hdl, 15, 0); %unnecessary, because the RC632 automatically reset the lower 3 bits
%result = OpenPCD_WriteReg(pcd_dev_hdl, 34, int8(3))%disable crc and enable parity
%select command: 0x93 20
result = OpenPCD_WriteFIFO(pcd_dev_hdl, [hex2dec('93'),hex2dec('20')])
	%the card shall return its UID,BCC
result = OpenPCD_WriteReg(pcd_dev_hdl, 1, 30) %tranceive command
[bytes, result] = OpenPCD_ReadFIFO(pcd_dev_hdl, 5)


%NOTE:: need to enable CRC or not?
%according to rfid_asic_rc632.c, function rc632_iso14443ab_transceive(),
%line 1039, the regular MIFARE or 14443A frame need TX, RX CRC both
%enabled. And Odd parity enabled too.
%CRC_A is used, initial value 0x6363.
result = OpenPCD_WriteReg(pcd_dev_hdl, 34, 7)%enable TX CRC and parity, RX CRC disabled

%crc initial value, LSB and MSB
result = OpenPCD_WriteReg(pcd_dev_hdl, 35,hex2dec('63'));
result = OpenPCD_WriteReg(pcd_dev_hdl, 36, hex2dec('63'));

%then do a select(UID): 0x93, 70, UID, BCC 
result = OpenPCD_WriteFIFO(pcd_dev_hdl, [uint8([hex2dec('93'),hex2dec('70')]),bytes])
	%the card shall return its type
result = OpenPCD_WriteReg(pcd_dev_hdl, 1, 30) %tranceive command
[bytes, result] = OpenPCD_ReadFIFO(pcd_dev_hdl, 3)

%then do a auth(block N)
result = OpenPCD_WriteFIFO(pcd_dev_hdl,uint8([hex2dec('60'),hex2dec('01')]))
%no need to append CRC here. They are auto-calculated!

	%the card shall return 32bit Nt challenge
result = OpenPCD_WriteReg(pcd_dev_hdl, 1, 30) %tranceive command
[bytes, result] = OpenPCD_ReadFIFO(pcd_dev_hdl, 6)

%TODO: try to generate the same Nt every time.
result = libusb_usb_release_interface(pcd_dev_hdl, interface0)
result = libusb_usb_close(pcd_dev_hdl)