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

	std::cout << "Myfunction:debug:" << day << std::endl;

	istringstream iss(str);
	string data;

	vector<string> user;

	vector<vector<string>> res;
	while (iss >> data ) {
		std::cout << "flg:debug:" << data.compare(1, 10, day) << std::endl;
		if (data.compare(1, 10, day) == -1 & user.size() > 0) {
			//�ꗗ�Ɋi�[
			res.push_back(user);
			//�f�[�^����x�폜����
			user.clear();
			//�f�[�^�i�[
			user.push_back(data);
		} else {
			//�f�[�^�i�[
			user.push_back(data);
		}
		

		
	}
	return res;
}