#pragma once

#include "pch.h"
#include <iostream>
#include <string>
#include <time.h>
#include <fstream>
#include <sstream>

#include "MyMath.h"

#ifndef _TimeStamp_H_
#define _TimeStamp_H_

using namespace std;

class TimeStamp
{
private:
	time_t timer;
	struct tm local_time;
public:
	TimeStamp();
	string today();
	string strftoday(string format);
	string strftodaymake(string format, tm today);
	string prevday();
	string nextday();
	string nday(int year, int month, int day, bool flg);
	int lastDays(int month);
	int getDay();
	int getMonth();
	int getYear();
};
#endif