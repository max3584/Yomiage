#include "pch.h"
#include <Windows.h>
#include <vector>
#include <string>
#include <stdexcept>

#ifndef _FileNames_H_
#define _FileNames_H_

class FileNames
{
private:
	FileNames* fn_p;
public:
	FileNames();
	std::vector<std::string> filenames(std::string& dir_name, const std::string& extension) noexcept(false);

};
#endif