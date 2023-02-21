#include<iostream>
#include"queue.h"
using namespace std;
void test1() {
	queue que;
	bool ch = true;
	int chosen;
	cout << "Меню:" << endl << "1-Добавить в очередь" << endl << "2-Извлечь из очереди" << endl << "3-Очистить очередь" << endl << "4-Проверить состояние очереди" << endl << "0-Выход"<<endl;
	while (ch) {
		cout << "Выберите операцию\n";
		cin >> chosen;
		switch (chosen) {
		case 1: {
			cout << "Введите число\n";
			int data;
			cin >> data;
			que.push(data);
			break;
		}
		case 2: {que.pop(); cout << "\n"; break; }
		case 3: {que.clear(); cout << "\n"; break; }
		case 4: {if (que.empty())cout << "В очереди нет элементов\n";
			  else cout << "В очереди есть элементы\n";
			break;
		}
		case 0: {ch = false; break; }
		}
	}
}
void test2() {
	queue lst;
	lst.push(5);
	lst.push(4);
	lst.push(3);
	lst.push(2);
	lst.push(1);
	while (!lst.empty()) {
		lst.pop();
	}
}
void test3() {
	queue lst;
	lst.push(22);
	lst.push(23);
	lst.push(10);
	lst.push(19);
	lst.push(100);
	lst.clear();
}
void test4() {
	queue lst;
	lst.push(22);
	lst.push(23);
	lst.push(10);
	lst.push(19);
	lst.push(100);
	cout << "Хотите очистить очередь?\n1-Да\n2-Нет\n";
	int choice;
	cin >> choice;
	switch (choice) {
	case 1: { lst.clear(); break; };
	case 2: { break; }
	}
	if (lst.empty())cout << "\nОчередь не содержит элементов";
	else cout << "В очереди есть элементы\n";
}

int main() {
	setlocale(LC_ALL, "rus");
	cout << "Выберите тест:" << endl << "1-Очередь, для которой имеется возможность выбора операции" << endl << "2-Очередь, для которой не имеется возмoжность выбора операции" << endl <<
		"3-Тест, в котором продемонстрирован метод очистки очереди" << endl << "4-Тест, в котором продемонстрирован метод empty" << endl;
	int choice;
	cin >> choice;
	switch (choice) {
	case 1: { test1(); break; }
	case 2: {test2(); break; }
	case 3: {test3(); break; }
	case 4: {test4(); break; }
	}
	return 0;
}