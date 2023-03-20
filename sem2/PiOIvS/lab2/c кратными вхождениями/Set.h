#pragma once
#pragma once
#include<iostream>
#include<vector>
#include<string>
using namespace std;
class Set {
private:
	vector<string>info;
	void sort(vector<int>& count);
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