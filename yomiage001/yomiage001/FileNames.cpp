#include "pch.h"
#include "FileNames.h"
#include <Windows.h>
#include <wchar.h>
#include <vector>
#include <string>
#include <stdexcept>

#define buffer_size 260

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

	hFind = FindFirstFile((LPCWSTR)search_name, &win32fd);

	if (hFind == INVALID_HANDLE_VALUE) {
		throw std::runtime_error("file not found");
	}

	do {
		if (win32fd.dwFileAttributes & FILE_ATTRIBUTE_DIRECTORY) {
		}
		else 
		{
			// å^ÇÃïœä∑
			// inits
			const wchar_t *wcString = win32fd.cFileName;
			char mbs[buffer_size];
			size_t count;
			mbstate_t state;
			errno_t err;
			
			// ïœä∑é¿çs
			err = wcstombs_s(&count, mbs, wcString, buffer_size);

			// îzóÒÇ…äiî[(vector)
			file_names.push_back(std::string(mbs));
			//printf("%s\n", file_names.back().c_str());

		}
	} while (FindNextFile(hFind, &win32fd));

	FindClose(hFind);

	return file_names;
}

void FileNames::close()
{
	close();
}
