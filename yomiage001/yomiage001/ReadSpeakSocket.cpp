#pragma once
#include "pch.h"
#include "ReadSpeakSocket.h"

int ReadSpeakSocket::speackSocket(std::string meta_msg)
{
	int check_num  = 1;

	sockaddr_in server;
	WSADATA     wsadata;
	SOCKET      sock;

	short  speed = -1, tone = -1, volume = -1, voice = 0;
	long   len;
	const char   *msg;

	//�@��菈���p�Ɏ኱����

	msg = meta_msg.c_str();

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

	//Winsock2������
	WSAStartup(MAKEWORD(1, 1), &wsadata);

	//�\�P�b�g�쐬
	sock = socket(AF_INET, SOCK_STREAM, 0);

	//�ڑ���w��p�\���̂̏���
	server.sin_family = AF_INET;
	check_num = inet_pton( server.sin_family, "127.0.0.1" , &server.sin_addr.S_un.S_addr);
	//server.sin_addr.S_un.S_addr = inet_addr("127.0.0.1");
	server.sin_port = htons(50001);
	
	if (check_num <= 0) {
		if (check_num == 0)
			fprintf(stderr, "Not in presentation format");
		else
			perror("inet_pton");
		exit(EXIT_FAILURE);
	}


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
