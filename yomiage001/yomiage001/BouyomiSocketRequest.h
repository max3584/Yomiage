#include "pch.h"

#ifndef _BouyomiSocketRequest_H_
#define _BouyomiSocketRequest_H_

/*
	�_�ǂ݂̃T���v���R�[�h���̂܂�
*/
class BouyomiSocketRequest
{
private:
	BouyomiSocketRequest* bsr_p;
public:
	BouyomiSocketRequest();
	int request(char* meta_msg);
	char* FileRead(char* file);
};

#endif