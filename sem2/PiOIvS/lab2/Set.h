#pragma once
#pragma once
#include<iostream>
#include<vector>
#include<string>
#include<stack>
using namespace std;
class Set {
private:
	struct elem
	{
		int index;
		string bracket;
	};
	vector<string>info;
	void sort(vector<int>& count);
	vector<string>parsing_string(string elem);
	string parsing_vec(vector<string>& set);
	void sort(vector<string>& set, int start, int finish);
	void compare(vector<string>& set, int start, int finish);
public:
	Set();
	~Set();
	void add(string elem);
	void printSet();
	void remove(string elem);
	bool contains(string elem);
	int size();
	Set symmetricDifference(Set set2);
	Set symmetricDifference(vector<Set>sets);
};