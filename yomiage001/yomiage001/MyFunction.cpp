#pragma once

#include "pch.h"
#include "MyFunction.h"


vector<string> MyFunction::split(string str, char delim) {
	istringstream iss(str);
	string tmp;
	vector<string> res;
	while (getline(iss, tmp, delim)) res.push_back(tmp);
	return res;
}

vector<vector<string>> MyFunction::splitt(string str, string day)
{

	//std::cout << "Myfunction:debug:" << day << std::endl;

	istringstream iss(str);
	string data;

	vector<string> user;

	vector<vector<string>> res;
	while (iss >> data ) {
		//std::cout << "flg:debug:" << data.compare(1, 10, day) << std::endl;
		if ((data.compare(1, 10, day) == -1) & user.size() > 0) {
			//ˆê——‚ÉŠi”[
			res.push_back(user);
			//ƒf[ƒ^‚ğˆê“xíœ‚·‚é
			user.clear();
			//ƒf[ƒ^Ši”[
			user.push_back(data);
		} else {
			//ƒf[ƒ^Ši”[
			user.push_back(data);

		}
		

		
	}
	res.push_back(user);

	return res;
}

std::vector<std::vector<std::string>> MyFunction::FileRead(std::string folder_path, std::vector<string> files_list, TimeStamp* ts)
{
	std::vector<std::vector<std::string>> userData;

	for (const std::string& folder_name : files_list)
	{

		//std::cout << folder_name << "\n" << std::endl;

		std::wifstream ifs(folder_path + folder_name);


		if (ifs.fail())
		{
			// fail
			//std::cout << "failed" << std::endl;				//debug
			continue;
		}

		ifs.imbue(locale(locale::empty(), new codecvt_utf16<wchar_t, 0x10ffff, consume_header>));
		std::wstring str((istreambuf_iterator<wchar_t>(ifs)), istreambuf_iterator<wchar_t>()); // Ši”[—p



		std::wcout << "debug:\tsize:" << str.size() << "\ndata:" << str.data() << std::endl;

		ConvertEncode* convert = new ConvertEncode;
		MyFunction* funcA = new MyFunction;



		// ƒf[ƒ^ˆê——‚ªŠi”[‚³‚ê‚Ä‚¢‚é
		userData = funcA->splitt(convert->wide_to_multi_winapi(str), ts->strftoday("%Y-%m-%d"));

		// faile close
		ifs.close();

	}

	return userData;
}

bool MyFunction::changeflg()
{
	std::string command;
	std::cout << "use key type please :: <key word is 'end words'>" << std::endl;
	cin >> command;

	const std::string command_character[] = { "end", "exit", "quit", "disaiable" };

	bool flg = false;

	for (std::string if_char : command_character)
	{
		if (flg = if_char.compare(command) == 0) return !flg;
	}
	return flg;
}