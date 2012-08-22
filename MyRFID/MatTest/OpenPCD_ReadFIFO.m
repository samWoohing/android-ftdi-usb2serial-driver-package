%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%   Read bytes from RC632 fifo
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
function [bytes, result] = OpenPCD_ReadFIFO(hdl, length)
EP1W=int32(hex2dec('1'));%write endpoint
EP2R=int32(hex2dec('82')); %note that read endpoint always begins with 8
timeout = int32(127);

byteW = [int8([23, 0, 0, length]), zeros(length, 'int8')];%refer to OpenPCD.h for command codes.
result = libusb_usb_bulk_write(hdl, EP1W, byteW, timeout);
[byteR, result] = libusb_usb_bulk_read(hdl, EP2R, int32(length(byteW)), timeout);
bytes=byteR(5:end);