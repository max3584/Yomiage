#include "pch.h"


#ifndef _BouyomiSocketRequest_H_

#define _BouyomiSocketRequest_H_

/*
	棒読みのサンプルコードそのまま
*/
class BouyomiSocketRequest
{
private:
	BouyomiSocketRequest* bsr_p;
public:
	BouyomiSocketRequest();
	int request(int argc, char *argv[]);
	char* FileRead(char* file);
	void close();
};

#endif