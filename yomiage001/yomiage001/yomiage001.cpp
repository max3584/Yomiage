#include "pch.h"
#include <iostream>
#include <vector>
#include <string>
#include <time.h>
#include <sstream>
#include <fstream>
#include "FileNames.hh"
#include "BouyomiSocketRequest.hh"
#include "TimeStamp.hh"


int main(int argc, char *argv[])
{

	// 文字を数値に変換
	int b = atoi(argv[3]);
	int k = atoi(argv[4]);
	std::istringstream isb = std::istringstream(argv[3]);
	std::istringstream isk = std::istringstream(argv[4]);

	isb >> b;
	isk >> k;

	/*
		timestamp説明
			配列格納式で添え字1つ目はプログラム上のタイムスタンプを使用するかどうか
			2つ目の添え字は1日ずつずらす処理を入れるかどうか
			timestamp = 
			{
				b, <- プログラム上のタイムスタンプを使用するか
				k　<- 1日ずつずらす処理を入れるか(マイナス方向)
			}

	*/
	boolean timestamp[] = {(boolean)b, (boolean)k};

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
	TimeStamp* ts = new TimeStamp();

	//folderPath += ss.str();

	// ファイルの中身を取得する時間
	int millisecond = 100 * 1000; // n * 1000で秒単位での設定が可能

	//フォルダーパスのフォーマットを整える処理
	std::string folder_format_path;

	folder_format_path += folderPath;

	if (timestamp[0]) 
	{
		folder_format_path += ts->today();
	}

	//debug
	std::cout << "debug0::" << folder_format_path << std::endl;

	// end format
	// ファイル名のリストを入れておくための領域
	std::vector <std::string> folder_name_lists;
	// exceptions try
	try 
	{
		folder_name_lists = fn->filenames(folder_format_path, extension);
		int day = 1;
		int mon = 0;
		int year = 0;
		while(!folder_name_lists[0].compare("NOT FILE FOUND")) {
			if (timestamp[1])
			{

				if (day >= ts->lastDays(ts->getMonth() - mon)) {
					day = 0;
					mon++;
					if (mon > 12) {
						mon = 1;

					}
				}

				// 1日削って再編成
				folder_format_path = folderPath + ts->nday(year, mon, day);

				std::cout << "debug1:" << folder_format_path << std::endl;

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


	}
	catch (std::runtime_error)
	{
		std::cout << "runtime error exception" << std::endl;
		return -1;
	}
	std::cout << "vector size:" << folder_name_lists.size() << std::endl;

	for (const std::string& folder_name : folder_name_lists)
	{

		std::cout << folder_name << "\n" << std::endl;
		
		std::ifstream ifs(folderPath + folder_name);
		std::string str; //格納用

		if (ifs.fail()) 
		{
			// fail
			std::cout << "failed" << std::endl;				//debug
			return -1;
		}
		while (std::getline(ifs, str, '\t')) {
			last_comment.push_back(str);
		}
		break;
	}

	//delete fn;
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