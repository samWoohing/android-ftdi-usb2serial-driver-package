%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%   Write bytes from RC632 fifo
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
function result = OpenPCD_WriteFIFO(hdl, bytes)
EP1W=int32(hex2dec('1'));%write endpoint
EP2R=int32(hex2dec('82')); %note that read endpoint always begins with 8
timeout = int32(1024);

byteW = uint8([18, 0, 0, length(bytes), bytes]);%refer to OpenPCD.h for command codes.
result = libusb_usb_bulk_write(hdl, EP1W, byteW, timeout);
