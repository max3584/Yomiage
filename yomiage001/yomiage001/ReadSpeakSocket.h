#pragma once

#include <stdio.h>
#include <winsock2.h>
#include <string>
#include <WS2tcpip.h>

class ReadSpeakSocket
{
private:

public:
	int speackSocket(std::string meta_msg);
};