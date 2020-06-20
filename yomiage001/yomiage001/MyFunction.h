#pragma once
#include "pch.h"

#include <string>
#include <iostream>
#include <vector>
#include <sstream>
#include <fstream>

#include "TimeStamp.hh"
#include "ConvertEncode.h"


#ifndef _MyFunction_H_
#define _MyFunction_H_

using namespace std;

class MyFunction
{
private:
	int n;
public:
	vector<string> split(string str, char delim = ',');
	vector<vector<string>> splitt(string str, string day);
	std::vector<std::vector<std::string>> FileRead(std::string folder_path, std::vector<string> files_list, TimeStamp* ts);
	bool changeflg();
};

#endif