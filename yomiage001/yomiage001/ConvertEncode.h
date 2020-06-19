#pragma once
#include "pch.h"
#include <codecvt>
#include <cstdlib>
#include <iomanip>
#include <iostream>
#include <locale>
#include <string>
#include <system_error>
#include <vector>
#include <Windows.h>

#ifndef _ConvertEncode_H_
#define _ConvertEncode_H_

class ConvertEncode
{
public:
	std::wstring multi_to_wide_capi(std::string const& src);
	std::wstring multi_to_wide_winapi(std::string const& src);
	std::string wide_to_multi_capi(std::wstring const& src);
	std::string wide_to_multi_winapi(std::wstring const& src);
	std::string wide_to_utf8_cppapi(std::wstring const& src);
	std::string wide_to_utf8_winapi(std::wstring const& src);
	std::wstring utf8_to_wide_cppapi(std::string const& src);
	std::wstring utf8_to_wide_winapi(std::string const& src);
	std::string multi_to_utf8_cppapi(std::string const& src);
	std::string multi_to_utf8_winapi(std::string const& src);
	std::string utf8_to_multi_cppapi(std::string const& src);
	std::string utf8_to_multi_winapi(std::string const& src);
	//void skip_utf8_bom(std::ifstream& fs);
};
#endif