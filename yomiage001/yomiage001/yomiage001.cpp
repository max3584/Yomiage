#include "pch.h"
#include <iostream>
#include <vector>
#include <string>
#include <time.h>
#include <sstream>
#include <fstream>
<<<<<<< Updated upstream
#include "FileNames.h"
#include "BouyomiSocketRequest.h"
//#include "encodeConvert.h"
=======
#include "FileNames.hh"
#include "BouyomiSocketRequest.hh"
#include "TimeStamp.hh"
#include "ConvertEncode.h"
#include "MyFunction.h"
>>>>>>> Stashed changes


int main(int argc, char *argv[])
{
	setlocale(LC_ALL, "");

	//encode comvert
	//encodeConvert* ec = new encodeConvert;

	int b = atoi(argv[3]);
	std::istringstream iss = std::istringstream(argv[3]);

	iss >> b;

	boolean timestamp = b;
	// encode converts
	// 文字コード変換
	// 変数変更
	std::string folderPath = argv[1];
	std::string extension = argv[2];

	std::string searchFileName = argv[5];

	//printf(u8"%s\\.%s\tflgs:%d", folder.c_str(), extension.c_str(), argv[3]);
	
	
	// 内容を保存するための領域
	std::vector<std::vector<std::string>> userDatas;		

	// ファイル名出力用
	FileNames* fn = new FileNames();
	//　棒読みちゃんへの転送と読み上げ用
	//BouyomiSocketRequest* bsr = new BouyomiSocketRequest;
	
	// 現在の日付時刻を取り出すためのもの
	time_t timer;
	struct tm local_time;

	timer = time(NULL);

	localtime_s(&local_time, &timer);

	std::stringstream ss;
	ss << "20" << local_time.tm_year + 1900 << local_time.tm_mon + 1 << local_time.tm_mday;

	//folderPath += ss.str();

	int millisecond = 100 * 1000; // n * 1000で秒単位での設定が可能

	//フォルダーパスのフォーマットを整える処理
<<<<<<< Updated upstream
	std::stringstream folder_format_path;
	folder_format_path << folderPath;
=======
	std::string folder_format_path;

	folder_format_path += folderPath + searchFileName;
>>>>>>> Stashed changes

	if (timestamp) 
	{
		folder_format_path << ss.str();
	}

	folder_format_path << "*";

	std::cout << "dayDebug:" << ts->lastDays(6) << std::endl;

	// end format
<<<<<<< Updated upstream
=======
	// ファイル名のリストを入れておくための領域
	std::vector <std::string> folder_name_lists;
	// exceptions try
	try 
	{
		folder_name_lists = fn->filenames(folder_format_path, extension);
		int day = 1, dayflg = 1;
		int mon = ts->getMonth(), monthflg = 0;
		int year = 0, yearflg = 0;

		std::cout << "monthdebug:" << mon << std::endl;

		while(!folder_name_lists[0].compare("NOT FILE FOUND")) {

			if (year > 50) {
				throw std::runtime_error("not file found exception");
			}

			if (timestamp[1])
			{

				if (day > ts->lastDays(mon)) {
					std::cout << "lastday:true" << std::endl;
					day = 1;
					dayflg = 0;
					monthflg = 1;
					mon++;
					if (mon > 12) {
						mon = 1;
						monthflg = 0;
						yearflg = 1;
						year++;
					}
				}

				// 1日削って再編成
				folder_format_path = folderPath + searchFileName + ts->nday(yearflg, monthflg, dayflg, false);

				std::cout << "debug1:" << folder_format_path << std::endl;

				dayflg = 1;
				monthflg = 0;
				yearflg = 0;

				// 再度チェック
				folder_name_lists = fn->filenames(folder_format_path, extension);
				day++;
			}
			else 
			{
				// 一致するファイルがないためここで処理を終了させる
				std::cout << "not file found exception" << std::endl;
				return -1;
			}

		}
>>>>>>> Stashed changes

	std::vector <std::string> folder_name_lists = fn->filenames(folder_format_path.str(), extension);

	// ファイルの中身を読み取る処理
	for (const std::string& folder_name : folder_name_lists)
	{
		if (folder_name.compare(0, 15, "ChatLog" + ss.str())) {
			std::ifstream ifs(folderPath + folder_name);
			std::string str; //格納用

<<<<<<< Updated upstream
			if (ifs.fail()) {
				// fail
				return -1;
			}
			while (std::getline(ifs, str, '\t')) {
				last_comment.push_back(str);
			}
			break;
		}
=======
		std::cout << folder_name << "\n" << std::endl;
		
		std::wifstream ifs(folderPath + folder_name);
		

		if (ifs.fail()) 
		{
			// fail
			std::cout << "failed" << std::endl;				//debug
			return -1;
		}
		
		ifs.imbue(locale(locale::empty(), new codecvt_utf16<wchar_t, 0x10ffff, consume_header>));
		std::wstring str((istreambuf_iterator<wchar_t>(ifs)), istreambuf_iterator<wchar_t>()); // 格納用
	

		std::wcout << "debug:\tsize:" << str.size() << "\ndata:" << str.data()<< std::endl;

		ConvertEncode* convert = new ConvertEncode;
		MyFunction* funcA = new MyFunction;
		

		// データ一覧が格納されている
		userDatas = funcA->splitt(convert->wide_to_multi_winapi(str), ts->strftoday("%Y-%m-%d"));

		

>>>>>>> Stashed changes
	}

	delete fn;
	//delete bsr;

	for (int i = 0; i < userDatas.size(); i++)
	{
		std::cout << "userData Number[" << i << "]";
		for (int j = 0; j < userDatas[i].size(); j++) 
		{
			std::cout << "datas:\n [" << j << "]" << userDatas[i][j] << std::endl;
		}
		std::cout << "\n";
	}
	return 0;
	/*
	while (true) {

		request(2, last_comment.c_str());

		Sleep(millisecond);
	}
	*/
	

}