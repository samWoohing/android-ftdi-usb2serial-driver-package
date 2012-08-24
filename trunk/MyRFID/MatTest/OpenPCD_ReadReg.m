%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%   Read a register value from OpenPCD device
%
%   [regvalue, result] = OpenPCD_ReadReg(hdl, reg)
%
%   regvalue: register value read from RC632
%   result: returns 0 of no error. 
%           -1 if write error, 
%           -2 if read error, 
%           -3 if flag error
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
function [regvalue, result] = OpenPCD_ReadReg(hdl, reg)
EP1W=int32(hex2dec('1'));%write endpoint
EP2R=int32(hex2dec('82')); %note that read endpoint always begins with 8
timeout = int32(127);

byteW = int8([22, 0, reg, 0]);%refer to OpenPCD.h for command codes.
w_result = libusb_usb_bulk_write(hdl, EP1W, byteW, timeout);

if w_result < 0
    result = -1;
    regvalue = int8(0);
    return;
end

[byteR, r_result] = libusb_usb_bulk_read(hdl, EP2R, int32(4), timeout);
flag=byteR(2);

if r_result < 0
	regvalue = int8(0);
	result = -2;
    return;
end

if flag == 128  %#define OPENPCD_FLAG_ERROR	0x80
	regvalue = int8(0);
    result = -3;
    return;
end
%if everything is OK:
regvalue=byteR(4);
result = 0;