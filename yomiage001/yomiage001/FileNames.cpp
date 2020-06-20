#pragma once

#include "pch.h"
#include "FileNames.hh"
#include "ConvertEncode.h"


#define buffer_size 260

FileNames::FileNames()
{
}

std::vector<std::string> FileNames::filenames(const std::string dir_name, const std::string & extension) noexcept(false)
{

	// init fields;
	HANDLE hFind = new HANDLE();
	WIN32_FIND_DATA *win32fd = new WIN32_FIND_DATA();//defined at Windwos.h

	ConvertEncode* ce = new ConvertEncode;

	std::vector<std::string> file_names;

	// stock valiable
	std::stringstream stock;

	stock << dir_name.c_str() << "*." << extension.c_str();

	std::cout << "path:test:" << stock.str() << std::endl;

	//release only
	hFind = FindFirstFile(ce->multi_to_wide_winapi(stock.str()).c_str(), win32fd);
	//debug only
	//hFind = FindFirstFile(stock.str().c_str(), win32fd);


	if (hFind == INVALID_HANDLE_VALUE)
	{
		std::cout << "fall" << std::endl;
		return {"NOT FILE FOUND"};
	}

	do {
		if (win32fd->dwFileAttributes & FILE_ATTRIBUTE_DIRECTORY) // Microsoft C++ の例外 std::runtime_error ハンドルされない例外
		{
			printf("debug1:%s (DIR)\n", win32fd->cFileName);
		}
		else 
		{
			printf("debug1:%s\n", win32fd->cFileName);
			// 配列にファイル名を格納(vector)
			//release only
			file_names.push_back(ce->wide_to_multi_winapi(win32fd->cFileName));
			// debug only
			//file_names.push_back(win32fd->cFileName);
			//printf("%s\n", file_names.back().c_str());

		}
	} while (FindNextFile(hFind, win32fd));

	FindClose(hFind);

	//debug
	/*
	for (int i = 0; i < file_names.size(); i++) {
		std::cout << file_names[i] << std::endl;
	}
	*/


	return file_names;
}

/*
std::vector<std::string> FileNames::getFileName(std::string folderPath, std::vector<std::string> &file_names)
{
	using namespace std::filesystem;
	directory_iterator iter(folderPath), end;
	std::error_code err;

	for (; iter != end && !err; iter.increment(err)) {
		const directory_entry entry = *iter;

		file_names.push_back(entry.path().string());
		printf("%s\n", file_names.back().c_str());
	}

	// エラー処理
	if (err) {
		std::cout << err.value() << std::endl;
		std::cout << err.message() << std::endl;
		return file_names;
	}
	return file_names;
}
*/