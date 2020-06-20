#pragma once

#include "pch.h"
#include <iostream>
#include <Windows.h>
#include <wchar.h>
#include <vector>
#include <string>
#include <sstream>
#include <stdexcept>
#include <filesystem>

#ifndef _FileNames_H_
#define _FileNames_H_

class FileNames
{
private:
	FileNames* fn_p;
public:
	FileNames();
	std::vector<std::string> filenames(const std::string dir_name, const std::string& extension) noexcept(false);
	//std::vector<std::string> getFileName(std::string folderPath, std::vector<std::string> &file_names);
};
#endif