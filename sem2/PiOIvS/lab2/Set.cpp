#include"Set.h"
void Set::sort(vector<int>& count) {
	for (int i = 0; i < count.size(); i++)
		for (int j = 0; j < count.size(); j++)
			if (count[i] > count[j])swap(count[i], count[j]);
}
Set::Set() = default;
Set::~Set() = default;
void Set::add(string elem)
{
	vector<string>v;
	if (elem[0] == '{' || elem[0] == '<')
	{
		v = parsing_string(elem);
		elem = parsing_vec(v);
	}
	info.push_back(elem);
}
void Set::printSet()
{
	cout << "{";
	for (int i = 0; i < info.size(); i++) {
		cout << info[i];
		if (i < info.size() - 1)
			cout << ",";
	}
	cout << "}\n";
}
void Set::remove(string elem) {
	for (int i = 0; i < info.size(); i++)
		if (elem == info[i])
			for (int j = i; j < info.size() - 1; j++)
				info[j] = info[j + 1];
	info.pop_back();
}
bool Set::contains(string elem) {
	for (int i = 0; i < info.size(); i++)
		if (elem == info[i])return true;
	return false;
}
int Set::size() {
	return info.size();
}
Set Set::symmetricDifference(Set set2) {
	Set result;
	Set used;
	int diff=0;
	vector<int> count(2);
	for (int i = 0; i < size(); i++)
		if (!used.contains(info[i]))
			used.add(info[i]);
	for(int i=0; i<set2.size(); i++)
		if (!used.contains(set2.info[i]))
			used.add(set2.info[i]);
	for (int i = 0; i < used.size(); i++) {
		count[0] = 0; count[1] = 0;
		for (int j = 0; j < size(); j++)
			if (used.info[i] == info[j])count[0]++;
		for (int j = 0; j < set2.size(); j++)
			if (used.info[i] == set2.info[j])count[1]++;
		if (count[0] == count[1])continue;
		else diff = fabs(count[0] - count[1]);
		for (int k = 0; k < diff; k++)result.add(used.info[i]);
	}
	return result;
}
Set Set::symmetricDifference(vector<Set>sets) {
	vector<int>count(sets.size());
	int diff = 0;
	Set result;
	Set used;
	for (int i = 0; i < sets.size(); i++) 
		for (int j = 0; j < sets[i].size(); j++)
			if (!used.contains(sets[i].info[j]))
				used.add(sets[i].info[j]);

		for (int i = 0; i < used.size(); i++) {
			for (int l = 0; l < count.size(); l++)count[l] = 0;
			for (int j = 0; j < sets.size(); j++) {
				for (int k = 0; k < sets[j].size(); k++)
					if (used.info[i] == sets[j].info[k])
						count[j]++;
			}
			sort(count);
			if (count[0] == count[1])continue;
			else diff = fabs(count[0] - count[1]);
			for (int p = 0; p < diff; p++)result.add(used.info[i]);
		}
	return result;
}
vector<string> Set::parsing_string(string elem)
{
	vector<string>result;
	string member;
	for (int i = 0; i < elem.length(); i++) {
		if (elem[i] == '{' || elem[i] == '}' || elem[i] == '<' || elem[i] == '>') {
			if (!member.empty()) {
				result.push_back(member);
				member.clear();
			}
			member.clear();
			member += elem[i];
			result.push_back(member);
			member.clear();
		}
		else if (elem[i] == ',')
		{
			if (member.empty())continue;
			result.push_back(member);
			member.clear();
		}
		else {
			member += elem[i];
		}

	}
	return result;
}
string Set::parsing_vec(vector<string>& set)
{
	stack<elem>st;
	elem buff;
	for (int i = 0; i < set.size(); i++) {
		if (set[i] == "{" || set[i] == "<") {
			buff.index = i;
			buff.bracket = set[i];
			st.push(buff);
		}
		else if (set[i] == "}")
		{
			buff = st.top();
			st.pop();
			sort(set, buff.index + 1, i - 1);
			compare(set, buff.index + 1, i - 1);
		}
		else if (set[i] == ">")
		{
			buff = st.top();
			st.pop();
			compare(set, buff.index + 1, i - 1);
		}

	}
	return set[0];
}
void Set::sort(vector<string>& set, int start, int finish)
{
	if (finish < start || finish == start)return;
	for (int i = start; i <= finish; i++)
		for (int j = start; j < finish; j++) {
			if (set[i].empty() || set[j].empty())continue;
			if (set[i] > set[j])swap(set[i], set[j]);
		}
}
void Set::compare(vector<string>& set, int start, int finish)
{
	for (int i = start; i <= finish; i++)
	{
		if (set[i].empty())continue;
		set[start - 1] += set[i];
		set[start - 1] += ",";
		set[i].clear();
	}
	if (set[start - 1][set[start - 1].length() - 1] == ',')
		set[start - 1].pop_back();
	set[start - 1] += set[finish + 1];
	set[finish + 1].clear();
}