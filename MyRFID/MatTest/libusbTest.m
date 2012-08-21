%test the libusb related functions
vidOpenPCD = uint16(hex2dec('16C0'));
pidOpenPCD = uint16(hex2dec('076B'));
interface0 = int32(0);
endpoint2=int32(hex2dec('82')); %note that read endpoint always begins with 8
readsz=int32(1024);
timeout = int32(64);

config1=int32(1);%for OpenPCD, only 1 will work, 2 does not exist.
pcd_dev = GetOpenPcdUsbDev(vidOpenPCD,pidOpenPCD)
pcd_dev_hdl = libusb_usb_open(pcd_dev)
result = libusb_usb_set_configuration(pcd_dev_hdl, config1);
result = libusb_usb_claim_interface(pcd_dev_hdl, interface0)

[bytes, result] = libusb_usb_bulk_read(pcd_dev_hdl, endpoint2, readsz, timeout);

result = libusb_usb_release_interface(pcd_dev_hdl, interface0)
result = libusb_usb_close(pcd_dev_hdl)