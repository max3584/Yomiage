#pragma once
#include "pch.h"
#include <string>
#include <iostream>
#include <vector>
#include <sstream>


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
};

#endif