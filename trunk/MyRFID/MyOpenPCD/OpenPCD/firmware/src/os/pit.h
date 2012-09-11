#ifndef _PIT_H
#define _PIT_H

#include <sys/types.h>

#define HZ	100

/* This API (but not the code) is modelled after the Linux API */
//refer to Cstartup_SAM7.c the device uses PLL clock divided by 2, Finally 48MHz,
//So master clock (MCLK) is 48MHz, and PIT runs on MCLK/16, according to datasheet,
//So PIT input clock is 3MHz

struct timer_list {
	struct timer_list *next;
	unsigned long expires;
	void (*function)(void *data);
	void *data;
};

extern volatile unsigned long jiffies;

extern void timer_add(struct timer_list *timer);
extern int timer_del(struct timer_list *timer);

extern void pit_init(void);
extern void pit_mdelay(u_int32_t ms);
extern void usleep(u_int32_t us);

#endif
