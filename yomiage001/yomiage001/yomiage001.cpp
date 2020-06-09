#include "pch.h"
#include <iostream>
#include <vector>
#include <string>
#include <time.h>
#include <sstream>
#include <fstream>
#include "FileNames.h"
#include "BouyomiSocketRequest.h"


int main(std::string folderPath)
{
	std::string list;				// ファイルの一覧を確保するための領域
	std::string last_comment;		// 内容を保存するための領域で(一番最後に取得したものを保存するためのもの)

	// ファイル名出力用
	FileNames* fn = new FileNames;
	//　棒読みちゃんへの転送と読み上げ用
	BouyomiSocketRequest* bsr = new BouyomiSocketRequest;
	
	// 現在の日付時刻を取り出すためのもの
	time_t timer;
	struct tm local_time;

	timer = time(NULL);

	localtime_s(&local_time, &timer);

	std::stringstream ss;
	ss << "20" << local_time.tm_year + 1900 << local_time.tm_mon + 1 << local_time.tm_mday;

	std::string s = ss.str();

	int millisecond = 100 * 1000; // n * 1000で秒単位での設定が可能
	
	for (const std::string& list : fn->filenames(folderPath, "txt"))
	{
		if (list.compare(0, 15, "ChatLog" + s)) {
			std::ifstream ifs(folderPath + list);
			std::string str; //格納用

			if (ifs.fail()) {
				// fail
				return -1;
			}
			while (getline(ifs, str)) {
				last_comment = (char*)str.c_str();
			}
			break;
		}
	}
	printf("%s",(char*)last_comment.c_str());
	/*
	while (true) {

		request(2, last_comment.c_str());

		Sleep(millisecond);
	}
	*/

}