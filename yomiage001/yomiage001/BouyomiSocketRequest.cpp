#include "pch.h"
#include "BouyomiSocketRequest.h"
#include <iostream>
#include <fstream>
#include <string>
#include <winsock2.h>
#include <WS2tcpip.h>

BouyomiSocketRequest::BouyomiSocketRequest()
{
}

inline int BouyomiSocketRequest::request(char* meta_msg)
{
	sockaddr_in server;
	WSADATA     wsadata;
	SOCKET      sock;

	short  speed = -1, tone = -1, volume = -1, voice = 0;
	long   len;
	char   *msg;

	//コマンドライン引数処理
	msg = msg;
	
	len = (long)strlen(msg);

	//送信するデータの生成(文字列を除いた先頭の部分)
	char buf[15];
	*((short*)&buf[0]) = 0x0001; //[0-1]  (16Bit) コマンド          （ 0:メッセージ読み上げ）
	*((short*)&buf[2]) = speed;  //[2-3]  (16Bit) 速度              （-1:棒読みちゃん画面上の設定）
	*((short*)&buf[4]) = tone;   //[4-5]  (16Bit) 音程              （-1:棒読みちゃん画面上の設定）
	*((short*)&buf[6]) = volume; //[6-7]  (16Bit) 音量              （-1:棒読みちゃん画面上の設定）
	*((short*)&buf[8]) = voice;  //[8-9]  (16Bit) 声質              （ 0:棒読みちゃん画面上の設定、1:女性1、2:女性2、3:男性1、4:男性2、5:中性、6:ロボット、7:機械1、8:機械2、10001〜:SAPI5）
	*((char*)&buf[10]) = 2;      //[10]   ( 8Bit) 文字列の文字コード（ 0:UTF-8, 1:Unicode, 2:Shift-JIS）
	*((long*)&buf[11]) = len;    //[11-14](32Bit) 文字列の長さ

	//接続先指定用構造体の準備
	server.sin_addr.S_un.S_addr = inet_pton(AF_INET, "127.0.0.1", &server.sin_addr);
	server.sin_port = htons(50001);
	server.sin_family = AF_INET;

	//Winsock2初期化
	WSAStartup(MAKEWORD(1, 1), &wsadata);

	//ソケット作成
	sock = socket(AF_INET, SOCK_STREAM, 0);

	//サーバに接続
	connect(sock, (struct sockaddr *)&server, sizeof(server));

	//データ送信
	send(sock, buf, 15, 0);
	send(sock, msg, len, 0);

	//ソケット終了
	closesocket(sock);

	//Winsock2終了
	WSACleanup();

	return 0;
}

inline char* BouyomiSocketRequest::FileRead(char* file)
{
	std::fstream ifs(file);
	std::string str; //格納用

	if (ifs.fail()) 
	{
		return (char*)"Faild to open File....";
	}
	while (getline(ifs, str)) {
		return (char*)str.c_str();
	}
}