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
			cout << "Введите целое число\n";
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
	cout << "Test 2:\n";
	queue lst;
	lst.push(5);
	lst.push(4);
	lst.push(3);
	lst.push(2);
	lst.push(1);
	while (!lst.empty()) {
		lst.pop();
	}
	if (lst.empty())cout << "Очередь не содержит элементо, функция pop в связке с empty отработали\n";
}
void test3() {
	cout << "Test 3:\n";
	queue lst;
	lst.push(22);
	lst.push(23);
	lst.push(10);
	lst.push(19);
	lst.push(100);
	lst.clear();
	if (lst.empty())cout << "Очередь не содержит функция clear отработала\n";
}
void test4() {
	cout << "Test 4:\n";
	queue lst;
	lst.push(22);
	lst.push(23);
	lst.push(10);
	lst.push(19);
	lst.push(100);
	if (lst.empty())cout << "Очередь не содержит элементов, функция empty отработала\n";
	else cout << "В очереди есть элементы, функция empty отработала\n";
}
void test5() {
	cout << "Test 5:\n";
	queue lst;
	lst.push(5);
	lst.push(4);
	lst.push(3);
	lst.push(2);
	lst.push(1);
	int data;
	while (!lst.empty()) {
		lst.pop(data);
		cout << data << " ";
	}
	cout << "функция void pop(&data) отработала\n";
}
int main() {
	setlocale(LC_ALL, "rus");
	cout << "Выберите тест:" << endl << "1-Очередь, для которой имеется возможность выбора операции" << endl << "2-Очередь, для которой не имеется возмoжность выбора операции" << endl <<
		"3-Тест, в котором продемонстрирован метод очистки очереди" << endl << "4-Тест, в котором продемонстрирован метод empty" <<endl<<"5-Тест, в котором продемонстрирован метод void pop(int &data)"<<endl << "6- Прогнать тесты 2-5" << endl;
	int choice;
	cin >> choice;
	switch (choice) {
	case 1: { test1(); break; }
	case 2: {test2(); break; }
	case 3: {test3(); break; }
	case 4: {test4(); break; }
	case 5: {test5(); break; }
	case 6: {test2(); test3(); test4(); test5(); break; }
	}
	return 0;
}