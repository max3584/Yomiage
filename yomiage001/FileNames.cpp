#include "pch.h"
#include "FileNames.h"
#include <Windows.h>
#include <wchar.h>
#include <vector>
#include <string>
#include <sstream>
#include <stdexcept>

#define buffer_size 260

FileNames::FileNames()
{
}

std::vector<std::string> FileNames::filenames(const std::string & dir_name, const std::string & extension) noexcept(false)
{

	// init fields;
	HANDLE hFind;
	WIN32_FIND_DATA win32fd;//defined at Windwos.h
	std::vector<std::string> file_names;

	// stock valiable
	std::stringstream stock;

	stock << dir_name << "\\." << extension;

	//Šg’£Žq‚ÌÝ’è
	LPCWSTR search_name = (LPCWSTR)stock.str().c_str();

	hFind = FindFirstFile(search_name, &win32fd);

	if (hFind == INVALID_HANDLE_VALUE) {
		throw std::runtime_error("file not found");
	}

	do {
		if (win32fd.dwFileAttributes & FILE_ATTRIBUTE_DIRECTORY) {
		}
		else 
		{
			// Œ^‚Ì•ÏŠ·
			// inits
			const wchar_t *wcString = win32fd.cFileName;
			char mbs[buffer_size];
			size_t count;
			errno_t err;
			
			// •ÏŠ·ŽÀs
			err = wcstombs_s(&count, mbs, wcString, buffer_size);

			// ”z—ñ‚ÉŠi”[(vector)
			file_names.push_back(std::string(mbs));

			//printf("%s\n", file_names.back().c_str());

		}
	} while (FindNextFile(hFind, &win32fd));

	FindClose(hFind);

	return file_names;
}