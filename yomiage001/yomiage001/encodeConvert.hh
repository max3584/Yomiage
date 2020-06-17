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
	// string�^����wstring�^�ɕϊ�
	std::wstring multi_for_wide_capi(const std::string& src);
	std::wstring multi_for_wide_winapi(const std::string& src);
	
	// wstring�^����string�^�ɕϊ�
	std::string wide_for_multi_capi(const std::wstring& src);
	std::string wide_for_multi_winapi(const std::wstring& src);
	
	// wstring�^����string�^�ɕϊ�(UTF-8)
	std::string wide_for_utf8_cppapi(const std::wstring& src);
	std::string wide_for_utf8_winapi(const std::wstring& src);
	
	// string�^����wstring�^�ɕϊ�(UTF-8)
	std::wstring utf8_for_wide_cppapi(const std::string& src);
	std::wstring utf8_for_wide_winapi(const std::string& src);
	
	// string�^��encode�ϊ�(SJIS -> UTF-8)
	std::string multi_for_utf8_cppapi(const std::string& src);
	std::string multi_for_utf8_winapi(const std::string& src);

	// string�^��encode�ϊ�(UTF-8 -> SJIS)
	std::string utf8_for_multi_cppapi(const std::string& src);
	std::string utf8_for_multi_winapi(const std::string& src);

};

#endif