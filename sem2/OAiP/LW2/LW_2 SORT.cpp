#include<iostream>
#include<iomanip>
#include <io.h>
#include<stdio.h>
#include<vector>
#include<string>
#include<algorithm>
using namespace std;
struct Athlete {
	char Name[50];
	int Number;
	int age;
	double height;
	double weight;
	char command[50];
};
void addName(Athlete* s) {
	char Name[50];
	gets_s(Name, 50);
	for (int i = 0; i < strlen(Name); i++) { s->Name[i] = Name[i]; }
	s->Name[strlen(Name)] = '\0';
}
void menu() {
	cout << "Меню:" << endl << "1-Добавить спортсмена" << endl << "2-Удалить спортсмена" << endl <<
		"3-Редактировать спортсмена" << endl << "4-Список спортсменов" << endl << "5-Линейный поиск в файле" << endl << "6-QuickSort" << endl <<
		"7-Сортировка методом прямого выбора" << endl << "8-Двоичный поиск в отсортированном массиве" << endl << "9-Меню" << endl << "0-Выход" << endl;
}
void add(FILE* file, vector<Athlete>& sp) {
	fopen_s(&file, "athlets.dat", "ab");
	if (!file) {
		cout << "ошибка\n";
		return;
	}
	Athlete st;
	cout << "Введите ФИО\n";
	getchar();
	addName(&st);
	cout << "Введите номер спортсмена\n";
	cin >> st.Number;
	cout << "Введите возраст\n";
	cin >> st.age;
	cout << "Введите вес\n";
	cin >> st.weight;
	cout << "Введите рост\n";
	cin >> st.height;
	cout << "Введите Команду\n";
	getchar();
	gets_s(st.command);
	fwrite(&st, sizeof(st), 1, file);
	fclose(file);
	sp.push_back(st);
}
void addAfterSort(vector<Athlete>& sp, FILE* file) {
	fopen_s(&file, "athlets.dat", "wb");
	if (!file) {
		cout << "ошибка" << endl;
		return;
	}
	Athlete s;
	for (int i = 0; i < sp.size(); i++) {
		fwrite(&sp, sizeof(sp), 1, file);
	}
	fclose(file);
}
void inf(Athlete* s) {
	cout << setw(30) << s->Name << setw(8) << s->Number << setw(12) << s->age << setw(13) << s->height << setw(8) << s->weight << setw(30) << s->command << "\n";
}
void pokaz(FILE* file) {
	fopen_s(&file, "athlets.dat", "rb");
	if (!file) {
		cout << "ошибка" << endl;
		return;
	}
	Athlete s;
	int n = _filelength(_fileno(file)) / sizeof(s);
	cout << setw(20) << "ФИО спортсмена" << setw(8) << "Номер" << setw(12) << "Возраст" << setw(13) << "Рост" << setw(8) << "Вес" << setw(30) << "Команда" << "\n";
	for (int i = 0; i < n; i++) {
		fread(&s, sizeof(s), 1, file);
		inf(&s);
	}
	fclose(file);
}
void info_from_vec(vector<Athlete>& sp) {
	if (sp.empty())return;
	else {
		cout << setw(30) << "ФИО спортсмена" << setw(8) << "Номер" << setw(12) << "Возраст" << setw(13) << "Рост" << setw(8) << "Вес" << setw(30) << "Команда" << "\n";
		for (int i = 0; i < sp.size(); i++) {
			inf(&sp[i]);
		}
	}
}
void stringVec(vector<Athlete>& sp, vector<string>& str) {
	if (str.empty()) {
		str.resize(sp.size());
		for (int i = 0; i < sp.size(); i++) {
			str[i] = sp[i].Name;
		}
	}
	else {
		str.clear();
		str.resize(sp.size());
		for (int i = 0; i < sp.size(); i++) {
			str[i] = sp[i].Name;
		}
	}
}
void addAfterDelete(FILE* file, vector<Athlete>& sp) {
	fopen_s(&file, "athlets.dat", "rb");
	if (!file) {
		cout << "ошибка" << endl;
		return;
	}
	sp.clear();
	Athlete s;
	int n = _filelength(_fileno(file)) / sizeof(s);
	for (int i = 0; i < n; i++) {
		fread(&s, sizeof(s), 1, file);
		sp.push_back(s);
	}
	fclose(file);
}
void del(FILE* file, vector<Athlete>& sp) {
	vector<string>buff;
	stringVec(sp, buff);
	fopen_s(&file, "athlets.dat", "r+b");
	if (!file) {
		cout << "ошибка" << endl;
		return;
	}
	cout << "\nВведите имя спортсмена которого хотите удалить\n";
	string Name_del = string(50, '\0'); bool del_stud = true;
	getchar();
	getline(cin, Name_del);
	Athlete s;
	int n = _filelength(_fileno(file)) / sizeof(s);
	for (int i = 0; i < n; i++) {
		fread(&s, sizeof(s), 1, file);
		if (Name_del==buff[i]) {
			del_stud = false;
			int position = i;
			for (position; position < n - 1; position++) {
				fseek(file, (sizeof s) * (position + 1), 0);
				fread(&s, sizeof(s), 1, file);
				fseek(file, (sizeof s) * (position), 0);
				fwrite(&s, sizeof s, 1, file);
			}
			_chsize(_fileno(file), (n - 1) * sizeof(s));
			fclose(file);
			addAfterDelete(file, sp);
			break;
		}

	}
	if (del_stud)
	{
		cout << " Спортсмена с таким ФИО нет\n";
		fclose(file);
	}

}


void redact(FILE* file, vector<Athlete>& sp) {
	fopen_s(&file, "athlets.dat", "r+b");
	if (!file) {
		cout << "ошибка" << endl;
		return;
	}
	Athlete st;
	bool check = true;
	string Name1(50, '\0');
	vector<string>buff;
	stringVec(sp, buff);
	cout << "\nВведите ФИО спортсмена\n";
	getchar();
	getline(cin, Name1);
	int n = _filelength(_fileno(file)) / sizeof(st);
	for (int i = 0; i < n; i++) {
		fread(&st, sizeof(st), 1, file);
		if (Name1==buff[i]) {
			check = false;
			cout << "Введите ФИО\n";
			
			addName(&st);
			cout << "Введите номер спортсмена\n";
			cin >> st.Number;
			cout << "Введите возраст\n";
			cin >> st.age;
			cout << "Введите вес\n";
			cin >> st.weight;
			cout << "Введите рост\n";
			cin >> st.height;
			cout << "Введите Команду\n";
			getchar();
			gets_s(st.command);
			fseek(file, (sizeof st) * i, 0);
			fwrite(&st, sizeof st, 1, file);
			break;
		}
	}
	if (check == true) {
		cout << "Такого спортсмена нет\n";
	}
	fclose(file);
}

void bubbleSort(vector<Athlete>& sp) {
	for (int i = 0; i < sp.size() - 1; i++) {
		for (int j = i + 1; j < sp.size(); j++) {
			if (sp[i].age > sp[j].age) {
				swap(sp[i], sp[j]);
			}

		}
	}
}
void linearSearch(vector<Athlete>& sp, int age_key) {
	int i_key = 0, kod = 0;
	for (int i = 0; i < sp.size(); i++)
		if (sp[i].age == age_key)
		{
			kod = 1;
			i_key = i;
			inf(&sp[i]);

		}
	if (kod == 0)cout << "\nСпортсмен с таким возрастом не найден\n";
}
void quickSort(vector<Athlete>& sp, int begin, int end)
{
	if (begin > end)return;
	else {
		int i = begin; int j = end; int x; x = sp[(begin + end) / 2].age;
		while (i <= j) {
			while (sp[i].age < x) i++;
			while (sp[j].age > x) j--;
			if (i <= j) {
				Athlete buff = sp[i];
				sp[i] = sp[j];
				sp[j] = buff; i++; j--;
			}

		}
		if (j > begin && j != end)
			quickSort(sp, begin, j);
		if (i < end && i != begin)
			quickSort(sp, i, end);
	}
}
void binarySearch(vector<Athlete>& sp, int begin, int end, int age_key)
{
	int x = (begin + end) / 2;
	int age = sp[x].age;
	if (age > age_key)binarySearch(sp, begin, x - 1, age_key);
	if (age < age_key)binarySearch(sp, x + 1, end, age_key);
	else inf(&sp[x]);
}
void task(vector<Athlete>&sp){
	vector<string>commands;
	vector<string>buff;
	stringVec(sp, buff);
	string st(50, '\0');
	stringVec(sp, commands);
	std::sort(begin(commands), end(commands));
	commands.erase(std::unique(begin(commands), end(commands)), end(commands));
	cout << commands.size()<<"\n";
	vector<int>sum_of_age(commands.size(), 0);
	vector<int>count_sp_in_command(commands.size(), 0);
	vector<float>sr(commands.size(), 0);
	for (int i = 0; i < sp.size(); i++) {
		for (int j = 0; j < commands.size(); j++) {
			if (commands[j] == buff[i]) { sum_of_age[j] += sp[i].age; count_sp_in_command[j]++; break; }
		}
	}
	for (int i = 0; i < commands.size(); i++) {
		sr[i] = (float)(sum_of_age[i] / count_sp_in_command[i]);
	}
	for (int i = 0; i < commands.size()-1; i++) {
		for (int j =i+1; j < commands.size(); j++) {
			if (sr[i] > sr[j]) {
				swap(sr[i], sr[j]);
				swap(commands[i], commands[j]);
			}
		}
	}
	cout <<"\n"<< "Информация о самой молодой команде:\n";
	cout << setw(30) << "ФИО спортсмена" << setw(8) << "Номер" << setw(12) << "Возраст" << setw(13) << "Рост" << setw(8) << "Вес" << setw(30) << "Команда" << "\n";
	for (int i = 0; i < sp.size(); i++) {

		if (commands[0] == buff[i]) { inf(&sp[i]); }
	}
	cout << "\n" << setw(30) <<"Средний возраст самой молодой комманды: " << sr[0] << " лет\n";
}
int main()
{
	setlocale(LC_ALL, "Russian");
	FILE* file;
	fopen_s(&file, "athlets.dat", "wb");
	if (!file) {
		cout << "ошибка" << endl;
		return 2;
	}
	fclose(file);
	vector<Athlete>sp;
	menu();
	bool exit = true;
	int choice;
	while (exit) {
		cin >> choice;
		switch (choice) {
		case 1: {add(file, sp); break; }
		case 2: { del(file, sp); break; }

		case 3: {redact(file, sp); addAfterDelete(file, sp); break; }
		case 4: {	info_from_vec(sp); break; }

		case 5: {int age_key;
			cout << "Введите возраст\n";
			cin >> age_key;
			linearSearch(sp, age_key); break;
		}
		case 6: {
			quickSort(sp, 0, sp.size() - 1); addAfterSort(sp, file); break; }
		case 7: {bubbleSort(sp); addAfterSort(sp, file); break; }
		case 8: {int age;
			cout << "Введите возраст\n";
			cin >> age;
			binarySearch(sp, 0, sp.size() - 1, age); break; }
		case 9: { menu(); break; }
		case 10: {task(sp); break; }
		case 0: { exit = false; break; }
		}
	}
	return 0;
}








