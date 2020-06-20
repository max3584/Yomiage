#pragma once

#include "pch.h"
#include "TimeStamp.hh"


#define default_format "%Y%m%d"

TimeStamp::TimeStamp()
{
	this->timer = time(nullptr);
	localtime_s(&this->local_time, &this->timer);
}

string TimeStamp::today()
{
	return this->strftoday(default_format);
}

string TimeStamp::strftoday(string format)
{
	return this->strftodaymake(format, this->local_time);
}

string TimeStamp::strftodaymake(string format, tm today)
{
	char cFormat[32];
	strftime(cFormat, sizeof(cFormat), format.c_str(), &today);
	return cFormat;
}

string TimeStamp::prevday()
{
	stringstream sstream;

	int day = this->local_time.tm_mday;
	int month = this->local_time.tm_mon + 1;
	int year = this->local_time.tm_year + 1900;

	bool day_flg = (day != 0);
	bool month_flg = (month != 0 | day_flg);
	bool year_flg = (year == 0 | month_flg);

	sstream << (year - (year_flg) ? 1 : 0)
				<< ((month - (month_flg) ? 1 : 0 <= 9) ? "0" + (month - (month_flg) ? 1 : 0) : to_string(month - (month_flg) ? 1 : 0))
					<< (day - (day_flg) ? 1 : 0 <= 9) ? "0" + (day - (day_flg) ? 1 : 0) : to_string(day - (day_flg) ? 1 : 0);

	return sstream.str();

}

string TimeStamp::nextday()
{
	stringstream sstream;

	MyMath mathA;

	int day = this->local_time.tm_mday;
	int month = this->local_time.tm_mon + 1;
	int year = this->local_time.tm_year + 1900;

	bool day_flg = (day != 28
		+ abs(mathA.sign((month - 2) * (month - 4) * (month - 6) * (month - 9) * (month - 11))) * 3
		+ abs(mathA.sign((month - 2) * (month - 1) * (month - 3) * (month - 5) * (month - 7) * (month - 8) * (month - 10) * (month - 12))) * 2
		+ (year % 100 != 0 & year % 400 == 0) ? (month == 2) ? 1 : 0 : 0);
	bool month_flg = (month != 12 | day_flg);
	bool year_flg = (year == 99 | month_flg);

	sstream << (year + (year_flg) ? 1 : 0)
				<< ((month + (month_flg) ? 1 : 0 <= 9) ? "0" + (month + (month_flg) ? 1 : 0) : to_string(month + (month_flg) ? 1 : 0))
					<< (day + (day_flg) ? 1 : 0 <= 9) ? "0" + (day + (day_flg) ? 1 : 0) : to_string(day + (day_flg) ? 1 : 0);

	return sstream.str();
}

string TimeStamp::nday(int yearptr, int monthptr, int dayptr, bool flg)
{
	// my Math
	MyMath mathA;

	int nyear = 0;
	int nmon = 0;
	int nday = 0;

	// inits
	if (flg) {
		nyear = local_time.tm_year + yearptr;
		nmon = local_time.tm_mon + 1 + monthptr;
		nday = local_time.tm_mday + dayptr;
	}
	else 
	{
		nyear = local_time.tm_year - yearptr;
		nmon = local_time.tm_mon + 1 - monthptr;
		nday = local_time.tm_mday - dayptr;
	}

	int last_day = this->lastDays(nmon);

	// day
	if (nday >= last_day)
	{
		nmon += (nday / last_day);
		nday = nday % last_day + (nday % last_day == 0)? 1 : 0;

		if (nmon > 12)
		{
			nmon = nmon % 12;
			nyear++;
		}
	}

	//localtime_s(this->flocal_time, &this->timer);

	struct tm convertDay = { 0, 0, 0, nday, nmon - 1, nyear };
	time_t valiable_day = mktime(&convertDay);

	return this->strftodaymake(default_format, convertDay);

	/*
	//stringstream sstream;

	sstream << nyear << ((nmon <= 9)? "0"+ nmon : to_string(nmon)) << ((nday <= 9)? "0" + nday : to_string(nday));

	std::cout << "debug2:" << sstream.str() << std::endl;

	return sstream.str();
	*/
}

int TimeStamp::lastDays(int month)
{
	MyMath mathA;

	int year = this->local_time.tm_year + 1900;

	//debug
	/*
	std::cout << "lastDays:Month:debug:" << month << std::endl;
	std::cout << "lastDays:lastdayMain:debug:" << 28 + abs(mathA.sign((month - 2) * (month - 4) * (month - 6) * (month - 9) * (month - 11))) * 3
		+ abs(mathA.sign((month - 2) * (month - 1) * (month - 3) * (month - 5) * (month - 7) * (month - 8) * (month - 10) * (month - 12))) * 2 << std::endl;
	*/

	int day = 28 + abs(mathA.sign((month - 2) * (month - 4) * (month - 6) * (month - 9) * (month - 11))) * 3
		+ abs(mathA.sign((month - 2) * (month - 1) * (month - 3) * (month - 5) * (month - 7) * (month - 8) * (month - 10) * (month - 12))) * 2;

	day += (year % 100 != 0 & year % 400 == 0) ? ((month + 1) == 2) ? 1 : 0 : 0;

	return day;
}

int TimeStamp::getDay()
{
	return this->local_time.tm_mday;
}

int TimeStamp::getMonth()
{
	return this->local_time.tm_mon + 1;
}

int TimeStamp::getYear()
{
	return this->local_time.tm_year;
}

