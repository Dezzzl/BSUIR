#include<iostream>
#include"queue.h"
using namespace std;
void test1() {
	queue que;
	bool ch = true;
	int chosen;
	cout << "����:" << endl << "1-�������� � �������" << endl << "2-������� �� �������" << endl << "3-�������� �������" << endl << "4-��������� ��������� �������" << endl << "0-�����"<<endl;
	while (ch) {
		cout << "�������� ��������\n";
		cin >> chosen;
		switch (chosen) {
		case 1: {
			cout << "������� ����� �����\n";
			int data;
			cin >> data;
			que.push(data);
			break;
		}
		case 2: {que.pop(); cout << "\n"; break; }
		case 3: {que.clear(); cout << "\n"; break; }
		case 4: {if (que.empty())cout << "� ������� ��� ���������\n";
			  else cout << "� ������� ���� ��������\n";
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
	if (lst.empty())cout << "������� �� �������� ��������, ������� pop � ������ � empty ����������\n";
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
	if (lst.empty())cout << "������� �� �������� ������� clear ����������\n";
}
void test4() {
	cout << "Test 4:\n";
	queue lst;
	lst.push(22);
	lst.push(23);
	lst.push(10);
	lst.push(19);
	lst.push(100);
	if (lst.empty())cout << "������� �� �������� ���������, ������� empty ����������\n";
	else cout << "� ������� ���� ��������, ������� empty ����������\n";
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
	cout << "������� void pop(&data) ����������\n";
}
int main() {
	setlocale(LC_ALL, "rus");
	cout << "�������� ����:" << endl << "1-�������, ��� ������� ������� ����������� ������ ��������" << endl << "2-�������, ��� ������� �� ������� ����o������ ������ ��������" << endl <<
		"3-����, � ������� ����������������� ����� ������� �������" << endl << "4-����, � ������� ����������������� ����� empty" <<endl<<"5-����, � ������� ����������������� ����� void pop(int &data)"<<endl << "6- �������� ����� 2-5" << endl;
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