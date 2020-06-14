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

	//�R�}���h���C����������
	msg = msg;
	
	len = (long)strlen(msg);

	//���M����f�[�^�̐���(��������������擪�̕���)
	char buf[15];
	*((short*)&buf[0]) = 0x0001; //[0-1]  (16Bit) �R�}���h          �i 0:���b�Z�[�W�ǂݏグ�j
	*((short*)&buf[2]) = speed;  //[2-3]  (16Bit) ���x              �i-1:�_�ǂ݂�����ʏ�̐ݒ�j
	*((short*)&buf[4]) = tone;   //[4-5]  (16Bit) ����              �i-1:�_�ǂ݂�����ʏ�̐ݒ�j
	*((short*)&buf[6]) = volume; //[6-7]  (16Bit) ����              �i-1:�_�ǂ݂�����ʏ�̐ݒ�j
	*((short*)&buf[8]) = voice;  //[8-9]  (16Bit) ����              �i 0:�_�ǂ݂�����ʏ�̐ݒ�A1:����1�A2:����2�A3:�j��1�A4:�j��2�A5:�����A6:���{�b�g�A7:�@�B1�A8:�@�B2�A10001�`:SAPI5�j
	*((char*)&buf[10]) = 2;      //[10]   ( 8Bit) ������̕����R�[�h�i 0:UTF-8, 1:Unicode, 2:Shift-JIS�j
	*((long*)&buf[11]) = len;    //[11-14](32Bit) ������̒���

	//�ڑ���w��p�\���̂̏���
	server.sin_addr.S_un.S_addr = inet_pton(AF_INET, "127.0.0.1", &server.sin_addr);
	server.sin_port = htons(50001);
	server.sin_family = AF_INET;

	//Winsock2������
	WSAStartup(MAKEWORD(1, 1), &wsadata);

	//�\�P�b�g�쐬
	sock = socket(AF_INET, SOCK_STREAM, 0);

	//�T�[�o�ɐڑ�
	connect(sock, (struct sockaddr *)&server, sizeof(server));

	//�f�[�^���M
	send(sock, buf, 15, 0);
	send(sock, msg, len, 0);

	//�\�P�b�g�I��
	closesocket(sock);

	//Winsock2�I��
	WSACleanup();

	return 0;
}

inline char* BouyomiSocketRequest::FileRead(char* file)
{
	std::fstream ifs(file);
	std::string str; //�i�[�p

	if (ifs.fail()) 
	{
		return (char*)"Faild to open File....";
	}
	while (getline(ifs, str)) {
		return (char*)str.c_str();
	}
}