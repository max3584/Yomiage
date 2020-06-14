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

#ifndef _encodeConvert_H_
#define _encodeConvert_H_

class encodeConvert
{
private:
	//std::size_t converted{};
	//std::vector<wchar_t> dest(src.size(), L'\0');
public:
	// constructor
	encodeConvert();
	// stringŒ^‚©‚çwstringŒ^‚É•ÏŠ·
	std::wstring multi_for_wide_capi(const std::string& src);
	std::wstring multi_for_wide_winapi(const std::string& src);
	
	// wstringŒ^‚©‚çstringŒ^‚É•ÏŠ·
	std::string wide_for_multi_capi(const std::wstring& src);
	std::string wide_for_multi_winapi(const std::wstring& src);
	
	// wstringŒ^‚©‚çstringŒ^‚É•ÏŠ·(UTF-8)
	std::string wide_for_utf8_cppapi(const std::wstring& src);
	std::string wide_for_utf8_winapi(const std::wstring& src);
	
	// stringŒ^‚©‚çwstringŒ^‚É•ÏŠ·(UTF-8)
	std::wstring utf8_for_wide_cppapi(const std::string& src);
	std::wstring utf8_for_wide_winapi(const std::string& src);
	
	// stringŒ^‚Ìencode•ÏŠ·(SJIS -> UTF-8)
	std::string multi_for_utf8_cppapi(const std::string& src);
	std::string multi_for_utf8_winapi(const std::string& src);

	// stringŒ^‚Ìencode•ÏŠ·(UTF-8 -> SJIS)
	std::string utf8_for_multi_cppapi(const std::string& src);
	std::string utf8_for_multi_winapi(const std::string& src);

};

#endif