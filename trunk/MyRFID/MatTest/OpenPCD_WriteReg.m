%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%   Write a register value from OpenPCD device
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
function result = OpenPCD_WriteReg(hdl, reg, val)
EP1W=int32(hex2dec('1'));%write endpoint
EP2R=int32(hex2dec('82')); %note that read endpoint always begins with 8
timeout = int32(127);

byteW = int8([17, 0, reg, val]);%refer to OpenPCD.h for command codes.
result = libusb_usb_bulk_write(hdl, EP1W, byteW, timeout);