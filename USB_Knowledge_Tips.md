# About USB bulk read, how it works and when it gives error code? #

An interesting question about USB bulk transfer is:

e.g., we define a read size 4096, what will the program do when it receives less than 4096, say 1024 bytes?
will it keep waiting till "timeout" error happens, or it just returns whatever it reads from the USB endpoint?

After a while of searching, here's what I found:

In the source code of libusb, there is a sync.c file, in which a function called libusb\_bulk\_transfer is defined.
In the comments of this function, it says:

Perform a USB bulk transfer. The direction of the transfer is inferred from
the direction bits of the endpoint address.

For bulk reads, the "length" field indicates the maximum length of
data you are expecting to receive. If less data arrives than expected,
this function will return that data, so be sure to check the
"transferred output" parameter.

So it seems that Android shall do the similar thing.
In UsbDeviceConnection, the bulkTransfer function is defined as:

> public int bulkTransfer (UsbEndpoint endpoint, byte[.md](.md) buffer, int length, int timeout)

the return value is: length of data transferred (or zero) for success, or negative value for failure

So for USB bulk read, whenever this function is called, it reads from Usb endpoints, and return how many bytes are actually read.
The actual bytes can be lesser than the "length". Or say, the "length" is the maximum number of bytes to read in this transanction.
This makes perfect sense to me.