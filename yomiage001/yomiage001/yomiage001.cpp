#include "pch.h"
#include <iostream>
#include <vector>
#include <string>
#include <time.h>
#include <sstream>
#include <fstream>
#include "FileNames.h"
#include "BouyomiSocketRequest.h"
//#include "encodeConvert.h"


int main(int argc, char *argv[])
{

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

	//printf(u8"%s\\.%s\tflgs:%d", folder.c_str(), extension.c_str(), argv[3]);

	std::vector<std::string> last_comment;		// 内容を保存するための領域で(一番最後に取得したものを保存するためのもの)

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
	std::stringstream folder_format_path;
	folder_format_path << folderPath;

	if (timestamp) 
	{
		folder_format_path << ss.str();
	}

	folder_format_path << "*";

	// end format

	std::vector <std::string> folder_name_lists = fn->filenames(folder_format_path.str(), extension);

	for (const std::string& folder_name : folder_name_lists)
	{
		if (folder_name.compare(0, 15, "ChatLog" + ss.str())) {
			std::ifstream ifs(folderPath + folder_name);
			std::string str; //格納用

			if (ifs.fail()) {
				// fail
				return -1;
			}
			while (std::getline(ifs, str, '\t')) {
				last_comment.push_back(str);
			}
			break;
		}
	}

	delete fn;
	//delete bsr;
	printf("%s",last_comment[last_comment.size() - 1].c_str());
	return 0;
	/*
	while (true) {

		request(2, last_comment.c_str());

		Sleep(millisecond);
	}
	*/
	

}