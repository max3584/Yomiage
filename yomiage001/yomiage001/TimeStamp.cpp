#include "pch.h"
#include "TimeStamp.hh"
#include "MyMath.h"

#define default_format "%Y%m%d"

TimeStamp::TimeStamp()
{
	this->timer = time(nullptr);
	localtime_s(this->local_time, &this->timer);
}

string TimeStamp::today()
{
	return this->strftoday(default_format);
}

string TimeStamp::strftoday(string format)
{
	return this->strftodaymake(format, this->local_time);
}

string TimeStamp::strftodaymake(string format, tm* today)
{
	char cFormat[32];
	strftime(cFormat, sizeof(cFormat), format.c_str(), today);
	return cFormat;
}

string TimeStamp::prevday()
{
	stringstream sstream;

	bool day_flg = (local_time->tm_mday != 0);
	bool month_flg = (local_time->tm_mon != 0 | day_flg);
	bool year_flg = (local_time->tm_year + 1900 == 0 | month_flg);

	sstream << (local_time->tm_year + 1900 - (year_flg) ? 1 : 0)
				<< ((local_time->tm_mon + 1 - (month_flg) ? 1 : 0 <= 9) ? "0" + (local_time->tm_mon + 1 - (month_flg) ? 1 : 0) : to_string(local_time->tm_mon + 1 - (month_flg) ? 1 : 0))
					<< (local_time->tm_mday - (day_flg) ? 1 : 0 <= 9) ? "0" + (local_time->tm_mday - (day_flg) ? 1 : 0) : to_string(local_time->tm_mday - (day_flg) ? 1 : 0);

	return sstream.str();

}

string TimeStamp::nextday()
{
	stringstream sstream;

	MyMath mathA;

	bool day_flg = (local_time->tm_mday != 28
		+ abs(mathA.sign((local_time->tm_mon + 1) - 2 * (local_time->tm_mon + 1) - 4 * (local_time->tm_mon + 1) - 6 * (local_time->tm_mon + 1) - 9 * (local_time->tm_mon + 1) - 11)) * 3
		+ abs(mathA.sign((local_time->tm_mon + 1) - 2 * (local_time->tm_mon + 1) - 1 * (local_time->tm_mon + 1) - 3 * (local_time->tm_mon + 1) - 5 * (local_time->tm_mon + 1) - 7 * (local_time->tm_mon + 1) - 8 * (local_time->tm_mon + 1) - 10 * (local_time->tm_mon + 1) - 11)) * 2
		+ (local_time->tm_year % 100 != 0 & local_time->tm_year % 400 == 0) ? ((local_time->tm_mon + 1) == 2) ? 1 : 0 : 0);
	bool month_flg = (local_time->tm_mon != 12 | day_flg);
	bool year_flg = (local_time->tm_year + 1900 == 99 | month_flg);

	sstream << (local_time->tm_year + 1900 + (year_flg) ? 1 : 0)
				<< ((local_time->tm_mon + 1 + (month_flg) ? 1 : 0 <= 9) ? "0" + (local_time->tm_mon + 1 + (month_flg) ? 1 : 0) : to_string(local_time->tm_mon + 1 + (month_flg) ? 1 : 0))
					<< (local_time->tm_mday + (day_flg) ? 1 : 0 <= 9) ? "0" + (local_time->tm_mday + (day_flg) ? 1 : 0) : to_string(local_time->tm_mday + (day_flg) ? 1 : 0);

	return sstream.str();
}

string TimeStamp::nday(int year, int month, int day)
{
	// my Math
	MyMath mathA;

	// inits
	int nyear = local_time->tm_year + 1900 + year;
	int nmon = local_time->tm_mon + 1 + month;
	int nday = local_time->tm_mday + day;

	// day
	if (nday >= 28
		+ abs(mathA.sign((nmon + 1) - 2 * (nmon + 1) - 4 * (nmon + 1) - 6 * (nmon + 1) - 9 * (nmon + 1) - 11)) * 3
		+ abs(mathA.sign((nmon + 1) - 2 * (nmon + 1) - 1 * (nmon + 1) - 3 * (nmon + 1) - 5 * (nmon + 1) - 7 * (nmon + 1) - 8 * (nmon + 1) - 10 * (nmon + 1) - 11)) * 2
		+ (nyear % 100 != 0 & nyear % 400 == 0) ? ((nmon + 1) == 2) ? 1 : 0 : 0)
	{
		nday = 0;
		nmon++;
		if (nmon > 12)
		{
			nmon = 1;
			nyear++;
		}
	}

	//localtime_s(this->flocal_time, &this->timer);

	struct tm convertDay = { 0, 0, 0, (this->local_time->tm_mday) + nday, (this->local_time->tm_mon) + nmon, (this->local_time->tm_year) + nyear };
	time_t valiable_day = mktime(&convertDay);

	return this->strftodaymake(default_format, &convertDay);

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

	return 28
		+ abs(mathA.sign((month + 1) - 2 * (month + 1) - 4 * (month + 1) - 6 * (month + 1) - 9 * (month + 1) - 11)) * 3
		+ abs(mathA.sign((month + 1) - 2 * (month + 1) - 1 * (month + 1) - 3 * (month + 1) - 5 * (month + 1) - 7 * (month + 1) - 8 * (month + 1) - 10 * (month + 1) - 11)) * 2
		+ (local_time->tm_year % 100 != 0 & local_time->tm_year % 400 == 0) ? ((month + 1) == 2) ? 1 : 0 : 0;
}

int TimeStamp::getDay()
{
	return local_time->tm_mday;
}

int TimeStamp::getMonth()
{
	return local_time->tm_mon + 1;
}

