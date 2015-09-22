Add support for FTDI usb-to-serial chips in Android 4.0.x. The package, as well as the demo program provided with it, can operate FTDI chips without root privilege on a Android device.

The current code targets for providing following functionalities:

1. A package that implements the major functions of FTDI's D2xx library, which are the fundamental operation of USB to serial chips throug USB connection.

2. A class that implements a serial port based the fundamental functions provided in above D2xx-like library.

3. An Android program that works as a hyper terminal software, working on Android 4.0.x systems.

Futher development targets may include JTAG function support, SPI, I2C support, etc.