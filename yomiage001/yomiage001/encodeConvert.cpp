#include "pch.h"
#include "encodeConvert.h"



encodeConvert::encodeConvert()
{

}

std::wstring encodeConvert::multi_for_wide_capi(const std::string & src)
{
	std::size_t converted{};
	std::vector<wchar_t> dest(src.size(), L'\0');
	if (::_mbstowcs_s_l(&converted, dest.data(), dest.size(), src.data(), _TRUNCATE, ::_create_locale(LC_ALL, "jpn")) != 0) {
		throw std::system_error{ errno, std::system_category() };
	}
	dest.resize(std::char_traits<wchar_t>::length(dest.data()));
	dest.shrink_to_fit();
	return std::wstring(dest.begin(), dest.end());
}

std::wstring encodeConvert::multi_for_wide_winapi(const std::string & src)
{
	auto const dest_size = ::MultiByteToWideChar(CP_ACP, 0U, src.data(), -1, nullptr, 0U);
	std::vector<wchar_t> dest(dest_size, L'\0');
	if (::MultiByteToWideChar(CP_ACP, 0U, src.data(), -1, dest.data(), dest.size()) == 0) {
		throw std::system_error{ static_cast<int>(::GetLastError()), std::system_category() };
	}
	dest.resize(std::char_traits<wchar_t>::length(dest.data()));
	dest.shrink_to_fit();
	return std::wstring(dest.begin(), dest.end());
}

std::string encodeConvert::wide_for_multi_capi(const std::wstring & src)
{
	std::size_t converted{};
	std::vector<char> dest(src.size() * sizeof(wchar_t) + 1, '\0');
	if (::_wcstombs_s_l(&converted, dest.data(), dest.size(), src.data(), _TRUNCATE, ::_create_locale(LC_ALL, "jpn")) != 0) {
		throw std::system_error{ errno, std::system_category() };
	}
	dest.resize(std::char_traits<char>::length(dest.data()));
	dest.shrink_to_fit();
	return std::string(dest.begin(), dest.end());
}

std::string encodeConvert::wide_for_multi_winapi(const std::wstring & src)
{
	auto const dest_size = ::WideCharToMultiByte(CP_ACP, 0U, src.data(), -1, nullptr, 0, nullptr, nullptr);
	std::vector<char> dest(dest_size, '\0');
	if (::WideCharToMultiByte(CP_ACP, 0U, src.data(), -1, dest.data(), dest.size(), nullptr, nullptr) == 0) {
		throw std::system_error{ static_cast<int>(::GetLastError()), std::system_category() };
	}
	dest.resize(std::char_traits<char>::length(dest.data()));
	dest.shrink_to_fit();
	return std::string(dest.begin(), dest.end());
}

std::string encodeConvert::wide_for_utf8_cppapi(const std::wstring & src)
{
	std::wstring_convert<std::codecvt_utf8_utf16<wchar_t>> converter;
	return converter.to_bytes(src);
}

std::string encodeConvert::wide_for_utf8_winapi(const std::wstring & src)
{
	static_assert(sizeof(wchar_t) == 2, "this function is windows only");
	const int len = ::WideCharToMultiByte(CP_UTF8, 0, src.c_str(), -1, nullptr, 0, nullptr, nullptr);
	std::string re(len * 2, '\0');
	if (!::WideCharToMultiByte(CP_UTF8, 0, src.c_str(), -1, &re[0], len, nullptr, nullptr)) {
		const auto er = ::GetLastError();
		throw std::system_error(std::error_code(er, std::system_category()), "WideCharToMultiByte:(" + std::to_string(er) + ')');
	}
	const std::size_t real_len = std::char_traits<char>::length(re.c_str());
	re.resize(real_len);
	re.shrink_to_fit();
	return re;
}

std::wstring encodeConvert::utf8_for_wide_cppapi(const std::string & src)
{
	std::wstring_convert<std::codecvt_utf8_utf16<wchar_t>> converter;
	return converter.from_bytes(src);
}

std::wstring encodeConvert::utf8_for_wide_winapi(const std::string & src)
{
	auto const dest_size = ::MultiByteToWideChar(CP_UTF8, 0U, src.data(), -1, nullptr, 0U);
	std::vector<wchar_t> dest(dest_size, L'\0');
	if (::MultiByteToWideChar(CP_UTF8, 0U, src.data(), -1, dest.data(), dest.size()) == 0) {
		throw std::system_error{ static_cast<int>(::GetLastError()), std::system_category() };
	}
	dest.resize(std::char_traits<wchar_t>::length(dest.data()));
	dest.shrink_to_fit();
	return std::wstring(dest.begin(), dest.end());
}

std::string encodeConvert::multi_for_utf8_cppapi(const std::string & src)
{
	auto const wide = multi_for_wide_capi(src);
	return wide_for_utf8_cppapi(wide);
}

std::string encodeConvert::multi_for_utf8_winapi(const std::string & src)
{
	auto const wide = multi_for_wide_winapi(src);
	return wide_for_utf8_winapi(wide);
}

std::string encodeConvert::utf8_for_multi_cppapi(const std::string & src)
{
	auto const wide = utf8_for_wide_cppapi(src);
	return wide_for_multi_capi(wide);
}

std::string encodeConvert::utf8_for_multi_winapi(const std::string & src)
{
	auto const wide = utf8_for_wide_winapi(src);
	return wide_for_multi_winapi(wide);
}
