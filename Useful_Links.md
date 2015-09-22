# Links that are useful to project development #

# FTDI hardware related #
FT2232H chip that is a comparatively new product of FTDI and with most functionalities:

http://www.ftdichip.com/Products/ICs/FT2232H.html

FTDI's D2xx driver page. They provide Windows, Linux and Android drivers. However, then Android driver is only a JNI wrapper of C library, and needs root priviledge to use...

http://www.ftdichip.com/Drivers/D2XX.htm

Application notes of FTDI. The D2xx library function definitions and JTAG library function definitions are worth to look at:

http://www.ftdichip.com/Support/Documents/AppNotes.htm

FTDI document that shows how to configure the MPSSE engine in a FT2232H to JTAG port:

http://www.ftdichip.com/Support/Documents/AppNotes/AN_129_FTDI_Hi_Speed_USB_To_JTAG_Example.pdf

# Libftdi related #

Libftdi is a C library that provides functions to operate FTDI chips. It works on Windows and Linux. It relies on Libusb, an open-source USB library. Libftdi provides similar functions to FTDI's D2xx library. But while FTDI doesn't open their source code, Libftdi is definitely the good place to find how to operate FTDI chips over USB:

http://www.intra2net.com/en/developer/libftdi/

The source code of libftdi. It's source code is pretty simple, just a libftdi.c and libftdi.h:

http://developer.intra2net.com/git/?p=libftdi

A very useful link that describes the FTDI chip's USB command set. Check it out and save a copy to your self:

http://yosemitefoothills.com/Electronics/FTDI_Chip_Commands.html

A library that operates FTDI chip:

http://hackage.haskell.org/packages/archive/ftdi/0.1/doc/html/System-FTDI.html#v%3AmsTransmitterHoldingRegister

The above page mentioned something interesting about FTDI chip USB read: the first 2 bytes are always ModemStatus bytes. Need to verify this!

# Android related #

Some idea on Android FTDI support:

http://android.serverbox.ch/?p=370

Android USB host develop guide

http://developer.android.com/guide/topics/usb/host.html

Androir programming book:

http://andbook.anddev.org/files/andbook.pdf

http://www.scandevconf.se/db/Marakana-Android-UI.pdf

Android's example of building tab-based GUI. Since TabActivity is deprecated, we'd better follow this new example:

http://developer.android.com/reference/android/app/TabActivity.html


Since API 11 on, Android also has a better choice called "ActionBar". I would prefer to use this, but check and see first:

http://developer.android.com/guide/topics/ui/actionbar.html

This page contains information of how to create a custom widget in Android. We may need to use it to custom Anroid's textView:

http://developer.android.com/guide/topics/ui/custom-components.html

# USB related #

This "USB in a nutshell" is very useful. Following page explains what is bcdDevice, etc, and all the details of a USB descriptor:

http://www.beyondlogic.org/usbnutshell/usb5.shtml

MUST LOOK AT THIS PAGE!! The standard USB request list in this page gives the chance to get bcdDevice even if Android does not provide a function to do this:

http://www.beyondlogic.org/usbnutshell/usb6.shtml#StandardDeviceRequests

SmartHost that helps jump through GFW:
http://code.google.com/p/smarthosts/