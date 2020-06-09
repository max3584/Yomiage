#include "pch.h"
#include "FileNames.h"
#include <Windows.h>
#include <vector>
#include <string>
#include <stdexcept>

FileNames::FileNames()
{
}

std::vector<std::string> FileNames::filenames(std::string & dir_name, const std::string & extension) noexcept(false)
{
	HANDLE hFind;
	WIN32_FIND_DATA win32fd;//defined at Windwos.h
	std::vector<std::string> file_names;

	//ägí£éqÇÃê›íË
	const char* search_name = (dir_name + "\\*." + extension).c_str();

	hFind = FindFirstFile(search_name, &win32fd);

	if (hFind == INVALID_HANDLE_VALUE) {
		throw std::runtime_error("file not found");
	}

	do {
		if (win32fd.dwFileAttributes & FILE_ATTRIBUTE_DIRECTORY) {
		}
		else {
			file_names.push_back();
			printf("%s\n", file_names.back().c_str());

		}
	} while (FindNextFile(hFind, &win32fd));

	FindClose(hFind);

	return file_names;
}
