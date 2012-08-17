#include <stdio.h>
#include <conio.h>
#include <ctype.h>
#include <string.h>
#include "..\..\api\openpcd.h"

MIFARE_HANDLE handle;

void hexdump (const char *buffer, unsigned int len)
{
	while (len--)
		printf (" %02X", (unsigned char)*buffer++);
	printf ("\n");
}

int main (void)
{
	int res,ErrorCount;
	unsigned int uid;
/*	const char text1[] = "Hello!", text2[] = "World!", *text;*/
	char buffer[20];
	bool allow_reset,first;

	allow_reset=first=true;
	handle=NULL;
	ErrorCount=0;

	while(1)
	{
		if(!handle)
		{
			if ((res = openpcd_open_reader (&handle)) < 0)
				handle=NULL;
			else
				printf ("STATUS: Reader=%02X\n",(unsigned int)handle);
		}
		else
		{
			if ((res = openpcd_select_card (handle)) < 0)
			{
				// optionally reset reader once after some errors
				if(++ErrorCount>3)
				{
					printf ("ERROR: Can't select card: %s\n", openpcd_get_error_text (res));
					printf ("STATUS: resetting reader\n");
					ErrorCount=0;
					
					if(allow_reset)
					{
						allow_reset=false;
						openpcd_reset_reader(handle);
					}
				}
				openpcd_close_reader (handle);
				handle=NULL;
			}
			else
			{
				if(openpcd_get_card_id(handle,&uid)<0)
					uid=-1;

				printf ("DONE: selected card [0x%08X]\n",uid);

				if ((res = openpcd_read (handle, 2, buffer, 6)) < 0)
					printf ("ERROR: Read card error: %s\n",
					openpcd_get_error_text (res));
				else
				{
					printf ("DONE - %i bytes read:", res);
					hexdump (buffer, res);
				}

/*				text = strncmp (buffer, text1, 6) ? text1 : text2;

				if ((res = openpcd_write (handle, 2, text, 6)) < 0)
					printf ("ERROR: Write card error: %s\n",
					openpcd_get_error_text (res));
				else
				{
					buffer[res] = 0;
					printf ("DONE: written %i bytes [%s]\n", res, text);
				}*/

				openpcd_deselect_card (handle);

				printf ("STATUS: deselected card\n");
			}
		}
	}

	openpcd_close_reader (handle);
	printf ("STATUS: closed reader\n");

	return 0;
}
