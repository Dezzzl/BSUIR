#include <iostream>
#include <vector>
using namespace std;
void select_operation(vector<int>& vec1, vector<int>& vec2), intersection_of_sets(vector<int>& vec1, vector<int>& vec2), combining_sets(vector<int> &vec1, vector<int> &vec2);
int main() {
	setlocale(LC_ALL, "ru");
	vector<int> vec1, vec2;
	int n = 5, a = 0, pots1 = 0, pots2 = 0;
	cout << "Введите мощность множества 1: ";
	cin >> pots1;
	cout << "Введите мощность множества 2: ";
	cin >> pots2;
	int pots1c = pots1, pots2c = pots2;
	cout << "Множество 1" << endl;
	while (pots1 != 0) {
		cout << "Введите элемент №" << pots1c + 1 - pots1 << ": ";
		cin >> a;
		vec1.push_back(a);
		pots1--;
	}
	cout << "Множество 2" << endl;
	while (pots2 != 0) {
		cout << "Введите элемент №" << pots2c + 1 - pots2 << ": ";
		cin >> a;
		vec2.push_back(a);
		pots2--;
	}
	select_operation(vec1, vec2);
}
void select_operation(vector<int> &vec1, vector<int> &vec2) {
	system("cls");
	int x = 0;
	cout << "1 - пересечение множеств\n2 - объединение множеств\nВыберите операцию над множествами: ";
	cin >> x;
	switch (x) {
		case 1:
			intersection_of_sets(vec1, vec2);
			break;
		case 2:
			combining_sets(vec1, vec2);
			break;
		default:
			cout << "Такой операции нет попробуйте снова...";
			select_operation(vec1, vec2);
			break;
	}
}
void intersection_of_sets(vector<int> &vec1, vector<int> &vec2){
	int x = 0;
	vector<int> vec3;
	for (int i = 0; i < vec1.size(); i++) {
		for (int j = 0; j<vec2.size(); j++) {
			if (vec1[i]==vec2[j]) {
				vec3.push_back(vec1[i]);
				break;
			}
		}
	}
	cout << "Пересечение множеств:"<<endl;
	for (int i = 0; i < vec3.size(); i++) {
		cout << vec3[i] << ' ';
	}
	cout << endl << "Введите 1 чтобы продолжить, 0 чтобы выйти: ";
	cin >> x;
	if (x == 1) {
		select_operation(vec1, vec2);
	}
}
void combining_sets(vector<int> &vec1, vector<int> &vec2) {
	bool flag = false;
	int x = 0;
	vector<int> vec3;
	for (int i = 0; i < vec1.size(); i++) {
		vec3.push_back(vec1[i]);
	}
	for (int i = 0; i < vec2.size(); i++) {
		for (int j = 0; j < vec1.size(); j++) {
			if (vec2[i] == vec1[j]) {
				flag = true;
			}
		}
		if (!flag) {
			vec3.push_back(vec2[i]);
		}
		flag = false;
	}
	cout << "Объединение множеств" << endl;
	for (int i = 0; i < vec3.size(); i++) {
		cout << vec3[i] << ' ';
	}
	cout << endl << "Введите 1 чтобы продолжить, 0 чтобы выйти: ";
	cin >> x;
	if (x == 1) {
		select_operation(vec1, vec2);
	}
}