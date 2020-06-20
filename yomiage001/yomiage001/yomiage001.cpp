#include "pch.h"

#include <iostream>
#include <vector>
#include <string>
#include <time.h>
#include <sstream>
#include <fstream>
#include <thread>

#include "FileNames.hh"
#include "TimeStamp.hh"
#include "ConvertEncode.h"
#include "MyFunction.h"

/*
	読み上げの少し前までは作成完了
	引数詳細
	argc : argv[]のサイズ(大きさ)

	argv : 処理に必要なデータ
	[1] ディレクトリ
	[2] 拡張子
	[3] 時間がファイル名で使われているか->フォーマットは(年月日)です
	[4] 時間が使われていて、その日付を変更するオプション
	[5] ファイル名(なくても大丈夫)

*/

int main(int argc, char *argv[])
{
	if (argc < 4) {
		return -1;
	}
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

	std::string searchFileName = argv[5];

	//printf(u8"%s\\.%s\tflgs:%d", folder.c_str(), extension.c_str(), argv[3]);
	
	
	// 内容を保存するための領域
	std::vector<std::vector<std::string>> userDatas;		

	// ファイル名出力用
	FileNames* fn = new FileNames();
	
	// 現在の日付時刻を取り出すためのもの
	TimeStamp* ts = new TimeStamp();

	//folderPath += ss.str();

	// ファイルの中身を取得する時間
	int millisecond = 200; // n * 1000で秒単位での設定が可能

	//フォルダーパスのフォーマットを整える処理
	std::string folder_format_path;

	folder_format_path += folderPath + searchFileName;

	if (timestamp[0]) 
	{
		folder_format_path += ts->today();
	}

	//debug
	/*
	std::cout << "debug0::" << folder_format_path << std::endl;

	std::cout << "dayDebug:" << ts->lastDays(6) << std::endl;
	*/

	// end format
	// ファイル名のリストを入れておくための領域
	std::vector <std::string> folder_name_lists;
	// exceptions try
	try 
	{
		folder_name_lists = fn->filenames(folder_format_path, extension);
		int day = 1, dayflg = 1;
		int mon = ts->getMonth(), monthflg = 0;
		int year = 0, yearflg = 0;

		//debug
		//std::cout << "monthdebug:" << mon << std::endl;

		// 一致するファイルを探す
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

				// debug
				//std::cout << "debug1:" << folder_format_path << std::endl;

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


	}
	catch (std::runtime_error)
	{
		std::cout << "runtime error exception" << std::endl;
		return -1;
	}
	std::cout << "vector size:" << folder_name_lists.size() << std::endl;

	// ファイルの中身を読み取る処理
	MyFunction* funcA = new MyFunction;

	userDatas = funcA->FileRead(folderPath, folder_name_lists, ts);

	//delete fn;
	//delete bsr;

	// debug
	for (int i = 0; i < userDatas.size(); i++)
	{
		std::cout << "userData Number[" << i << "]" << std::endl;
		for (int j = 0; j < userDatas[i].size(); j++) 
		{
			std::cout << "datas:\n [" << j << "]" << userDatas[i][j] << std::endl;
		}
		std::cout << "\n";
	}

	//終わりにするためのフラグ
	bool endflg = true;

	// 読み上げソフトに転送するためのクラス
	//ReadSpeakSocket* rss = new ReadSpeakSocket;
	
	//非同期処理()
	auto th = std::thread([funcA, &endflg] {endflg = funcA->changeflg(); });

	std::string prevComment, prevUser;

	while (endflg) 
	{

		// init (データ精査)
		int last_vector = userDatas.size() - 1;

		int nomber  = atoi(userDatas[last_vector][1].c_str());
		std::string group = userDatas[last_vector][2];
		std::string name = userDatas[last_vector][4];

		std::string comment;

		for (int i = 5; i < userDatas[last_vector].size(); i++) {
			comment += userDatas[last_vector][i] + " ";
		}

		

		if (prevComment.compare(comment) != 0 & prevUser.compare(comment) != 0) {
			prevComment = comment;
			prevUser = name;
			std::cout << "userName:" << name << std::endl << "chat:" << comment << std::endl;
		}
		
		// 転送処理
		//rss->speackSocket(comment.c_str());

		//再読み込み処理の待機時間
		std::this_thread::sleep_for(std::chrono::milliseconds(200));

		//再読み込み処理
		userDatas = funcA->FileRead(folderPath, folder_name_lists, ts);
	}

	th.join();

	std::cout << "End Application" << "see you!!" << std::endl;

	return 0;
}