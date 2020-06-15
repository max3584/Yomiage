#include "pch.h"
#include "FileNames.h"
#include <iostream>
#include <Windows.h>
#include <wchar.h>
#include <vector>
#include <string>
#include <sstream>
#include <stdexcept>
#include <filesystem>

#define buffer_size 260

FileNames::FileNames()
{
}

std::vector<std::string> FileNames::filenames(const std::string dir_name, const std::string & extension) noexcept(false)
{

	// init fields;
	HANDLE hFind = new HANDLE();
	WIN32_FIND_DATA *win32fd = nullptr;//defined at Windwos.h
	std::vector<std::string> file_names;

	// stock valiable
	std::stringstream stock;

	stock << dir_name.c_str() << "." << extension.c_str();

	//拡張子の設定
	//LPCSTR search_name = stock.str().c_str();

	hFind = FindFirstFile(stock.str().c_str(), win32fd);

	if (hFind == INVALID_HANDLE_VALUE) 
	{
		throw std::runtime_error("file not found");
	}

	do {
		if (win32fd->dwFileAttributes & FILE_ATTRIBUTE_DIRECTORY) // Microsoft C++ の例外 std::runtime_error ハンドルされない例外
		{

		}
		else 
		{

			// 配列にファイル名を格納(vector)
			file_names.push_back(std::string(win32fd->cFileName));

			//printf("%s\n", file_names.back().c_str());

		}
	} while (FindNextFile(hFind, win32fd));

	FindClose(hFind);

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