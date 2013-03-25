function [bytes, result] = MifareFixedNtCrack(hdl, Nr_Ar_Parity)
EP1W=int32(hex2dec('1'));%write endpoint
EP2R=int32(hex2dec('82')); %note that read endpoint always begins with 8
timeout = int32(8192);

params = zeros(1,21, 'uint8');
params(2)=uint8(1);%TODO: change this to input parameter later
params(3:7)=uint8([179   41  218   79   15]);%blank sample card
params(3:7)=uint8([14  135   61  234   94]);%yuanshen card
params(12:20)=uint8(Nr_Ar_Parity);
byteW = [uint8([28, 0, 0, 21]),params];

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