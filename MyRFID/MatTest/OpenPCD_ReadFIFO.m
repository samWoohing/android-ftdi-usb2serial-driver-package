%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%   Read bytes from RC632 fifo
%
%   [bytes, result] = OpenPCD_ReadFIFO(hdl, length)
%
%   bytes: the int8 bytes read from fifo
%   result: returns 0 of no error. 
%           -1 if write error, 
%           -2 if read error, 
%           -3 if flag error
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
function [bytes, result] = OpenPCD_ReadFIFO(hdl, len)
EP1W=int32(hex2dec('1'));%write endpoint
EP2R=int32(hex2dec('82')); %note that read endpoint always begins with 8
timeout = int32(1024);

byteW = [uint8([23, 0, 0, len]), zeros(1,len, 'uint8')];%refer to OpenPCD.h for command codes.

w_result = libusb_usb_bulk_write(hdl, EP1W, byteW, timeout);

if w_result < 0
    result = -1;
    bytes = uint8(0);
    return;
end

[byteR, r_result] = libusb_usb_bulk_read(hdl, EP2R, int32(length(byteW)), timeout);
flag=byteR(2);

if r_result < 0
	bytes = uint8(0);
	result = -2;
    return;
end

if flag == 128  %#define OPENPCD_FLAG_ERROR	0x80
	bytes = uint8(0);
    result = -3;
    return;
end
%if everything is OK:
bytes=byteR(5:end);
result = 0;
