#include"Set.h"
Set Set::Difference(Set set2, Set &used)
{
	Set result;
	for (int i = 0; i < size(); i++)
		if (!set2.contains(info[i]))
			result.add(info[i]);
		else used.add(info[i]);
	return result;
}
Set Set::Unification(Set set2, Set& used)
{
	Set result;
	for (int i = 0; i < size(); i++)
		if(!used.contains(info[i]))
		result.add(info[i]);
	for (int i = 0; i < set2.size(); i++)
		if (!contains(set2.info[i])&&!used.contains(set2.info[i]))
			result.add(set2.info[i]);
	return result;
}
	Set::Set() = default;
	Set::~Set() = default;
	void Set::add(string elem) {
		for (int i = 0; i < info.size(); i++)
			if (info[i] == elem)return;
		info.push_back(elem);
	}
	void Set:: printSet()
	{
		cout << "{ ";
		for (int i = 0; i < info.size(); i++) {
			cout << info[i];
			if (i < info.size() - 1)
				cout << ", ";
		}
		cout << " }\n";
	}
	void Set::remove(string elem) {
		for (int i = 0; i < info.size(); i++)
			if (elem == info[i])
				for (int j = i; j < info.size() - 1; j++)
					info[j] = info[j + 1];
		info.pop_back();
	}
	bool Set:: contains(string elem) {
		for (int i = 0; i < info.size(); i++)
			if (elem == info[i])return true;
		return false;
	}
	int Set:: size() {
		return info.size();
	}
	Set Set::symmetricDifference(Set set2)
	{
		Set result;
		for (int i = 0; i < size(); i++)
			if (!set2.contains(info[i]))
				result.add(info[i]);
		for (int i = 0; i < set2.size(); i++)
			if (!contains(set2.info[i]))
				result.add(set2.info[i]);
		return result;
	}
	Set Set::symmetricDifference(vector<Set>sets) {
		vector<Set> Differences(sets.size());
		Set used;
		Set result;
		for (int i = 0; i < sets.size(); i++) {
			for (int k = 0; k < sets[i].size(); k++)
				Differences[i].add(sets[i].info[k]);
			for (int j = 0; j < sets.size(); j++) {
				if (i == j)continue;
				Differences[i] = Differences[i].Difference(sets[j], used);
			}
		}
		for (int i = 0; i < Differences[0].size(); i++)
			if(!used.contains(Differences[0].info[i]))
			result.add(Differences[0].info[i]);
		for (int i = 1; i < sets.size(); i++)
			result = result.Unification(Differences[i], used);
		return result;
	}