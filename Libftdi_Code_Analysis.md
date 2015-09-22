# Functions in libusb that are used by libftdi #

## usb\_control\_msg: ##

Send a control message to a device. The interface definition is:
int usb\_control\_msg(usb\_dev\_handle **dev, int requesttype, int request, int value, int index, char** bytes, int size, int timeout);

This is a function used to send usb control info to the device. This function is used widely in Libftdi to modifiy the configuration of the chip and port. Functions that use usb\_control\_msg includes:
  * ftdi\_usb\_reset
  * ftdi\_usb\_purge\_rx\_buffer
  * ftdi\_usb\_purge\_tx\_buffer
  * ftdi\_set\_baudrate
  * ftdi\_set\_line\_property2
  * ftdi\_enable\_bitbang
  * ftdi\_disable\_bitbang
  * ftdi\_set\_bitmode
  * ftdi\_read\_pins
  * ftdi\_set\_latency\_timer
  * ftdi\_get\_latency\_timer
  * ftdi\_poll\_modem\_status
  * ftdi\_setflowctrl
  * ftdi\_setdtr
  * ftdi\_setrts
  * ftdi\_setdtr\_rts
  * ftdi\_set\_event\_char
  * ftdi\_set\_error\_char
  * ftdi\_read\_eeprom\_location
  * ftdi\_read\_eeprom
  * ftdi\_read\_chipid
  * ftdi\_read\_eeprom\_getsize
  * ftdi\_write\_eeprom\_location
  * ftdi\_write\_eeprom
  * ftdi\_erase\_eeprom

## usb\_bulk\_read: ##
Read data from a bulk endpoint. The interface definition is
int usb\_bulk\_read(usb\_dev\_handle **dev, int ep, char** bytes, int size, int timeout);
The function is ONLY used to read bulk data from usb endpoint. So there's only one function calls it:
  * ftdi\_read\_data

## usb\_bulk\_write: ##
Write data to a bulk endpoint. The interface definition is:
int usb\_bulk\_write(usb\_dev\_handle **dev, int ep, char** bytes, int size, int timeout);
The function is ONLY used to write bulk data to usb endpoint. So there's only one function calls it:
  * ftdi\_write\_data

## usb\_open: ##
Opens a USB device.
usb\_dev\_handle **usb\_open(struct** usb\_device dev);
This function is used in following Libftdi functions:
  * ftdi\_usb\_open\_dev
  * ftdi\_usb\_open\_desc\_index
  * ftdi\_usb\_get\_strings

## usb\_close: ##
Closes a USB device
int usb\_close(usb\_dev\_handle **dev);
This function is ONLY used in following Libftdi function:
  * ftdi\_usb\_close\_internal**

## usb\_init: ##
Initialize libusb
void usb\_init(void);
This function is used in following libftdi functions:
  * ftdi\_usb\_find\_all
  * ftdi\_usb\_open\_desc\_index
  * ftdi\_usb\_open\_string

## usb\_find\_busses: ##
Finds all USB busses on system
int usb\_find\_busses(void);
The function is used in following libftdi functions:
  * ftdi\_usb\_find\_all
  * ftdi\_usb\_open\_desc
  * ftdi\_usb\_open\_desc\_index
  * ftdi\_usb\_open\_string
  * ftdi\_usb\_find\_all

## usb\_find\_devices: ##
Find all devices on all USB devices
This function is used in following libftdi functions:
int usb\_find\_devices(void);
  * ftdi\_usb\_find\_all
  * ftdi\_usb\_open\_desc\_index
  * ftdi\_usb\_open\_string

## usb\_get\_busses ##
Return the list of USB busses found
struct usb\_bus **usb\_get\_busses(void);
This function is used in following libftdi functions:
  * ftdi\_usb\_find\_all
  * ftdi\_usb\_open\_desc\_index
  * ftdi\_usb\_open\_string**

## usb\_strerror ##

## usb\_get\_string\_simple ##
Retrieves a string descriptor from a device using the first language
int usb\_get\_string\_simple(usb\_dev\_handle **dev, int index, char** buf, size\_t buflen);
This function is used in following libftdi functions:
  * ftdi\_usb\_get\_strings
  * ftdi\_usb\_open\_desc\_index

## usb\_detach\_kernel\_driver\_np ##
Detach kernel driver from interface
int usb\_detach\_kernel\_driver\_np(usb\_dev\_handle **dev, int interface);
This function is used in following libftdi functions:
  * ftdi\_usb\_open\_dev**

## usb\_set\_configuration ##
Sets the active configuration of a device
int usb\_set\_configuration(usb\_dev\_handle **dev, int configuration);
This function is used in following libftdi functions:
  * ftdi\_usb\_open\_dev**

## usb\_claim\_interface ##
Claim an interface of a device
int usb\_claim\_interface(usb\_dev\_handle **dev, int interface);
This function is used in following libftdi functions:
  * ftdi\_usb\_open\_dev**

## usb\_release\_interface ##
Releases a previously claimed interface
int usb\_release\_interface(usb\_dev\_handle **dev, int interface);
This function is used in following libftdi functions:
  * ftdi\_usb\_close**

# Importance of the above functions #
Below functions are those we must have, in order to perform the FTDI device operation,
So we have to find what Android functions exactly implements these functionalities.:
  * usb\_control\_msg
  * usb\_bulk\_read
  * usb\_bulk\_write
  * usb\_open
  * usb\_close
  * usb\_claim\_interface: used to claim more than one interface on a device. We have to find functions that does the same thing in Android.
  * usb\_release\_interface: similar to above reason.
  * usb\_set\_configuration: This function sets the USB port configuration, such as support 100mA, or more. Android may have something similar to this.

Below functions are for USB infromation retrieving, Doesn't matter if Android provides exactly the same thing. We can always use what Android provides.
  * usb\_find\_busses
  * usb\_find\_devices
  * usb\_get\_busses
  * usb\_get\_string\_simple

usb\_init is only for libusb internal use only. we don't need it at all.

And these functions needs further look into:
  * usb\_detach\_kernel\_driver\_np: This function will detach a kernel driver from the interface specified by parameter interface. usually used before opening a device. We don't need it cause android will handle these things.


# What Android functions could be used #
Below are the corrisponding Android equivalent of the above must-have libusb functions. Note that Android put everything in java classes. So the actual usage of these functions shall follow the objective-oriented style of coding, no need to be too ridgid to C-style.
## usb\_control\_msg: ##
This libusb function can be replaced by Android function:

public class UsbDeviceConnection

public int controlTransfer (int requestType, int request, int value, int index, byte[.md](.md) buffer, int length, int timeout)

## usb\_bulk\_read and usb\_bulk\_write ##
Both of these two functions can be replaced by Android function:

public class UsbDeviceConnection

public int bulkTransfer (UsbEndpoint endpoint, byte[.md](.md) buffer, int length, int timeout)

## usb\_open: ##
This libusb function can be replaced by Android function:

public class UsbManager

public UsbDeviceConnection openDevice (UsbDevice device)



## usb\_close: ##
This libusb function can be replaced by Android function:

public class UsbDeviceConnection

public void close ()

Note that Android's USB device open and close are implemented in different classes, which makes sense. The UsbManager class opens the device. The UsbDeviceConnection class closes itself.

## usb\_claim\_interface ##
This libusb function can be replaced by Android function:

public class UsbDeviceConnection

public boolean claimInterface (UsbInterface intf, boolean force)

## usb\_release\_interface ##
This libusb function can be replaced by Android function:

public class UsbDeviceConnection

public boolean releaseInterface (UsbInterface intf)

## usb\_set\_configuration ##

Don't know yet if Android has such capabilities or not...



# Data structures used in libFTDI that is from Libusb #

## usb\_config\_descriptor ##

## usb\_interface ##

## usb\_interface\_descriptor ##

## usb\_bus ##

## usb\_device ##

# How the usb\_control\_msg set DTR, RTS or other stuff for each individual channels on a FTDI chip? #
Since usb\_control\_msg is only for "control message", or say it transmitt only on usb endpoint 0.
Then the question rises: How by calling such a function can set individual channel's DTR, RTS and other stuff??

The answer turns out to be as following:

The usb\_control\_msg function has following prototype:

int usb\_control\_msg(usb\_dev\_handle **dev, int requesttype, int request, int value, int index, char**bytes, int size, int timeout);

The first parameter is definitely the device context pointer.

The second is requesttype, for FTDI chip control, it's usually FTDI\_DEVICE\_OUT\_REQTYPE

The third parameter is the request itself, or the command that directs FTDI chip what to do. e.g. for setDTR, the request is defined in ftdi.h as: SIO\_SET\_MODEM\_CTRL\_REQUEST

The fourth parameter is the parameter to the request. e.g. for setDTR, the request is SIO\_SET\_MODEM\_CTRL\_REQUEST, the value is SIO\_SET\_DTR\_HIGH or SIO\_SET\_DTR\_LOW in order to set DTR to high or low.

The fifth parameter is the KEY POINT here, the index is corrisponding to WHICH FTDI CHIP CHANNEL YOU WANT TO SEND THE USB CONTROL MESSAGE TO.
so this is how the control message is differentiated by different channels.