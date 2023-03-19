#include<iostream>
#include<string>
#include<vector>
#include<fstream>
#include<math.h>
using namespace std;
struct flight {
	string destination;
	int Number;
	string date;
};
void add(flight fl, int key, vector<flight>& flights_table) {
	int i = fabs(key % flights_table.size());
	if (flights_table[i].Number != -1)
	{
		int c = 1 + fabs(key % (flights_table.size() - 2));
		while (flights_table[i].Number != -1)
		{
			i = i - c;
			if (i < 0)
				i = i + flights_table.size();
		}
	}
	flights_table[i] = fl;
}
int search(int key, vector<flight>& flights_table) {
	int i = fabs(key % flights_table.size());
	if (flights_table[i].Number == -1)
	{
		int c = 1 + fabs(key % (flights_table.size() - 2));
		while (flights_table[i].Number != -1)
		{
			i = i - c;
			if (i < 0)
				i = i + flights_table.size();
			if (flights_table[i].Number == key)return i;
		}
		return -1;	
	}
	return i;
}
void read(const string filename, vector<flight>&flights)
{
	ifstream fin;
	fin.open(filename);
	if (!fin.is_open())
		return;
	else {
		string str;
		flight buff;
		while (fin)
		{
			getline(fin, str);
			buff.destination = str;
			getline(fin, str);
			buff.date = str;
			getline(fin, str);
			int res = stoi(str);
			buff.Number = res;
			flights.push_back(buff);
		}
		flights.pop_back();
	}

	fin.close();
}
void menu() {
	cout << "Меню:" << endl << "1-Поиск по номеру рейса" << endl << "2-Выход" << endl << endl;
}
int main() {
	setlocale(LC_ALL, "rus");
	vector<flight>flights;
	bool exit = true;
	int choice;
	read("рейсы.txt", flights);
	vector<flight>flights_table(2*flights.size());
	for (int i = 0; i < flights_table.size(); i++)
		flights_table[i].Number = -1;
	for (int i = 0; i < flights.size(); i++)
		add(flights[i], flights[i].Number, flights_table);
	cout << "Список рейсов:\n";
	for (int i = 0; i < flights.size(); i++)
		cout << "Номер: " << flights[i].Number << " Дата: " << flights[i].date << " Пункт назначения: " << flights[i].destination << endl;
	/*for (int i = 0; i < flights_table.size(); i++)
		cout << "Номер: " << flights_table[i].Number << " Дата: " << flights_table[i].date << " Пункт назначения: " << flights_table[i].destination << endl;*/
	menu();
	while (exit) {
		cout << "1 или 2?\n";
		cin >> choice;
		switch (choice)
		{
		case 1: {
			int key;
			cout << "Введите номер рейса\n";
			cin >> key;
			int i = search(key, flights_table);
			if (i != -1)
				cout << "Номер: " << flights_table[i].Number << " Дата: " << flights_table[i].date << " Пункт назначения: " << flights_table[i].destination << endl;
			else cout << "Рейс с таким номером не найден\n";
			break;
		}
		case 2: exit = false;
			break;
		}
	}
	return 0;
}