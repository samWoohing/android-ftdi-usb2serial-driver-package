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

[regvalue, flag] = OpenPCD_ReadReg(pcd_dev_hdl, 34)

result = libusb_usb_release_interface(pcd_dev_hdl, interface0)
result = libusb_usb_close(pcd_dev_hdl)