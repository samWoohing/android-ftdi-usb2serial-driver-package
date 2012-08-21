%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%   Read a register value from OpenPCD device
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
function [regvalue, flag] = OpenPCD_ReadReg(hdl, reg)
EP1W=int32(hex2dec('1'));%write endpoint
EP2R=int32(hex2dec('82')); %note that read endpoint always begins with 8
timeout = int32(127);

byteW = int8([22, 0, reg, 0]);%refer to OpenPCD.h for command codes.
result = libusb_usb_bulk_write(hdl, EP1W, byteW, timeout);
[byteR, result] = libusb_usb_bulk_read(hdl, EP2R, int32(4), timeout);
regvalue=byteR(4);
flag=byteR(2);