#include <iostream>
#include <vector>
#include <utility>
#include <cmath>
using namespace std;
void by_enum(), cont(), select_operation(), intersection_of_sets(), combining_sets(), complementA(), complementB(), subtractionAB(), subtractionBA(), sym_subtraction(), inversionA(), inversionB(), compositionAB(), compositionBA(), projectionA(), projectionB();

vector<pair<int, int>> vec1, vec2;
pair<int, int> pr;
int main() {
	setlocale(LC_ALL, "ru");
	system("cls");
	by_enum(); //перечислением
	main();
}

void by_enum() {//способ задания множеств перечислением
	int a = 0, b = 0, pots1 = 0, pots2 = 0;
	cout << "Введите мощность графика A: ";
	cin >> pots1; //мощность множества A
	cout << "Введите мощность графика B: ";
	cin >> pots2; //мощность множества B
	cout << "График A" << endl;
	for (int i = 0; i < pots1; i++) {
		cout << "Введите кортеж графика №" << i + 1 << ": ";
		cin >> a >> b; //пользователь вводит с клавиатуры значение элемента
		pr = make_pair(a, b);
		vec1.push_back(pr);//добавляем элемент в множество A
	}
	cout << "График B" << endl;
	for (int i = 0; i < pots2; i++) {
		cout << "Введите кортеж графика №" << i + 1 << ": ";
		cin >> a >> b;//пользователь вводит с клавиатуры значение элемента
		pr = make_pair(a, b);
		vec2.push_back(pr);//добавляем элемент в множество B
	}
	select_operation();
}

void select_operation() {//пользователь выбирает операцию
	int x = 0;
	cout << "1 - пересечение графиков\n2 - объединение графиков\n3 - разность графиков A и B\n4 - разность графиков B и A\n5 - симметрическая разность\n6 - дополнение графика A\n7 - дополнение графика B\n8 - инверсия графика A\n9 - инверсия графика B\n10 - композиция графиков A и B\n11 - композиция графиков B и A\n12 - проекции на оси графика A\n13 - проекции на оси графика B\nВыберите операцию над графиками: ";
	cin >> x;
	switch (x) {
	case 1:
		intersection_of_sets();//пересечение множеств
		break;
	case 2:
		combining_sets();//объединение множеств
		break;
	case 3:
		subtractionAB();//Разность множеств А и В
		break;
	case 4:
		subtractionBA();//Разность множеств В и А
		break;
	case 5:
		sym_subtraction();//симметрическая разность
		break;
	case 6:
		complementA();//дополнение множества А
		break;
	case 7:
		complementB();//дополнение множества В
		break;
	case 8:
		inversionA();//инверсия множества А
		break;
	case 9:
		inversionB();//инверсия множества В
		break;
	case 10:
		compositionAB();//композиция А и B
		break;
	case 11:
		compositionBA();//композиция В и A
		break;
	case 12:
		projectionA();//проекция на 1 и 2 ось графика A
		break;
	case 13:
		projectionB();//проекция на 1 и 2 ось графика B
		break;
	default:
		select_operation();
		break;
	}
}
void intersection_of_sets() {//пересечение множеств
	system("cls");
	vector<pair<int, int>> vec3;// создаём пустое множество C
	for (int i = 0; i < vec1.size(); i++) {
		for (int j = 0; j < vec2.size(); j++) {
			if (vec1[i] == vec2[j]) {//сравниваем элементы
				vec3.push_back(vec1[i]);//добавляем взятый элемент множества А в С
				break;
			}
		}
	}
	cout << "Пересечение множеств: " << endl;//Выводим на экран получившееся множество C
	for (int i = 0; i < vec3.size(); i++) {
		cout << "<" << vec3[i].first << ", " << vec3[i].second << "> ";
	}
	cout << endl;
	cont();//пользователь выбирает продолжить ли работу алгоритма
}
void combining_sets() {
	system("cls");
	int x;
	bool flag = false;
	vector<pair<int, int>> vec3;//создаём пустое множество C
	for (int i = 0; i < vec1.size(); i++) {//добавляем элементы множества A в C
		vec3.push_back(vec1[i]);
	}
	for (int i = 0; i < vec2.size(); i++) {
		for (int j = 0; j < vec1.size(); j++) {
			if (vec2[i] == vec1[j]) {//сравниваем элементы
				flag = true;
			}
		}
		if (!flag) {
			vec3.push_back(vec2[i]);//добаляем взятый элемент множества В в С
		}
		flag = false;
	}
	cout << "Объединение множеств: " << endl;
	for (int i = 0; i < vec3.size(); i++) {//Выводим на экран получившееся множество C
		cout << "<" << vec3[i].first << ", " << vec3[i].second << "> ";
	}
	cout << endl;
	cont();//пользователь выбирает продолжить ли работу алгоритма
}
void subtractionAB() {
	system("cls");
	bool f = true;
	vector<pair<int, int>> vec3;//создаём пустое множество C
	cout << "Разность: " << endl;
	for (int i = 0; i < vec1.size(); i++) {
		for (int j = 0; j < vec2.size(); j++) {
			if (vec1[i] == vec2[j]) {//сравниваем элементы
				f = false;
				break;
			}
		}
		if (f) {
			vec3.push_back(vec1[i]);//Добавляем взятый элемент множества А в С
		}
		else {
			f = true;
		}
	}
	for (int i = 0; i < vec3.size(); i++) {//Выводим на экран получившееся множество C
		cout << "<" << vec3[i].first << ", " << vec3[i].second << "> ";
	}
	cout << endl;
	cont();//пользователь выбирает продолжить ли работу алгоритма
}

void subtractionBA() {
	system("cls");
	bool f = true;
	vector<pair<int, int>> vec3;//создаём пустое множество C
	cout << "Разность: " << endl;
	for (int i = 0; i < vec2.size(); i++) {
		for (int j = 0; j < vec1.size(); j++) {
			if (vec2[i] == vec1[j]) {
				f = false;
				break;
			}
		}
		if (f) {
			vec3.push_back(vec2[i]);//добавляем взятый элемент множества B в С
		}
		else {
			f = true;
		}
	}
	for (int i = 0; i < vec3.size(); i++) {//Выводим на экран получившееся множество C
		cout << "<" << vec3[i].first << ", " << vec3[i].second << "> ";
	}
	cout << endl;
	cont();//пользователь выбирает продолжить ли работу алгоритма
}

void sym_subtraction() {//Симметрическая разность
	system("cls");
	bool f = true, flag = false;
	vector<pair<int, int>> vec3, vec4, vec5;//создаём пустое множество D, E и C
	cout << "Симметрическая разность: " << endl;
	for (int i = 0; i < vec1.size(); i++) {//Разность множеств A и B
		for (int j = 0; j < vec2.size(); j++) {
			if (vec1[i] == vec2[j]) {//сравниваем взятые элементы
				f = false;
			}
		}
		if (f) {
			vec3.push_back(vec1[i]);//добавляем взятый элемент ножества А в D
		}
		else {
			f = true;
		}
	}
	for (int i = 0; i < vec2.size(); i++) {//Разность множеств B и A
		for (int j = 0; j < vec1.size(); j++) {
			if (vec2[i] == vec1[j]) {//сравниваем взятые элементы
				f = false;
			}
		}
		if (f) {
			vec4.push_back(vec2[i]);//добавляем взятый элемент ножества B в E 
		}
		else {
			f = true;
		}
	}//Операция объединения множеств D и E
	for (int i = 0; i < vec3.size(); i++) {
		vec5.push_back(vec3[i]);//добавляем элементы множества D в C
	}
	for (int i = 0; i < vec4.size(); i++) {
		for (int j = 0; j < vec3.size(); j++) {
			if (vec4[i] == vec3[j]) {//сравниваем взятые элементы
				flag = true;
			}
		}
		if (!flag) {
			vec5.push_back(vec4[i]);//добаляем взятый элемент множества E в C
		}
		flag = false;
	}
	for (int i = 0; i < vec5.size(); i++) {//Выводим на экран получившееся множество C
		cout << "<" << vec5[i].first << ", " << vec5[i].second << "> ";
	}
	cout << endl;
	cont();//пользователь выбирает продолжить ли работу алгоритма
}

void complementA() {
	system("cls");
	vector<pair<int, int>> vec3;//создаём универсальное множество U
	bool f = true;
	for (int x = 1; x <= 100; x++) {//заполняем множество U
		for (int y = 1; y <= 100; y++) {
			pr = make_pair(x, y);
			vec3.push_back(pr);
		}
	}
	cout << "Дополнение: " << endl;
	for (int i = 0; i < vec3.size(); i++) {//Операция разности U и A
		for (int j = 0; j < vec1.size(); j++) {
			if (vec3[i] == vec1[j]) {//сравниваем взятые элементы
				f = false;
			}
		}
		if (f) {
			cout << "<" << vec3[i].first << ", " << vec3[i].second << "> ";//выводим получившееся множество C
		}
		else {
			f = true;
		}
	}
	cout << endl;
	cont();//пользователь выбирает продолжить ли работу алгоритма
}

void complementB() {
	system("cls");
	vector<pair<int, int>> vec3;//создаём универсальное множество U
	pair<int, int> pr;
	bool f = true;
	for (int x = 1; x <= 100; x++) {//заполняем множество U
		for (int y = 1; y <= 100; y++) {
			pr = make_pair(x, y);
			vec3.push_back(pr);
		}
	}
	cout << "Дополнение: " << endl;
	for (int i = 0; i < vec3.size(); i++) {//разность множества U и A
		for (int j = 0; j < vec2.size(); j++) {
			if (vec3[i] == vec2[j]) {//сравниваем взятые элементы
				f = false;
			}
		}
		if (f) {//Выводим на экран получившееся множество C
			cout << "<" << vec3[i].first << ", " << vec3[i].second << "> ";
		}
		else {
			f = true;
		}
	}
	cout << endl;
	cont();//пользователь выбирает продолжить ли работу алгоритма
}

void inversionA() {
	system("cls");
	cout << "Инверсия графика A" << endl;
	for (int i = 0; i < vec1.size(); i++) {
		cout << "<" << vec1[i].second << ", " << vec1[i].first << "> ";
	}
	cout << endl;
	cont();
}

void inversionB() {
	system("cls");
	cout << "Инверсия графика B" << endl;
	for (int i = 0; i < vec2.size(); i++) {
		cout << "<" << vec2[i].second << ", " << vec2[i].first << "> ";
	}
	cout << endl;
	cont();
}

void compositionAB() {
	system("cls");
	vector<pair<int, int>> vec3;
	pair<int, int> pr;
	for (int i = 0; i < vec1.size(); i++) {
		for (int j = 0; j < vec2.size(); j++) {
			if (vec1[i].second == vec2[j].first) {
				pr = make_pair(vec1[i].first, vec2[j].second);
				vec3.push_back(pr);
			}
		}
	}
	cout << "Композиция графиков A и B" << endl;
	for (int i = 0; i < vec3.size(); i++) {
		cout << "<" << vec3[i].first << ", " << vec3[i].second << "> ";
	}
	cout << endl;
	cont();
}

void compositionBA() {
	system("cls");
	vector<pair<int, int>> vec3;
	pair<int, int> pr;
	for (int i = 0; i < vec2.size(); i++) {
		for (int j = 0; j < vec1.size(); j++) {
			if (vec2[i].second == vec1[j].first) {
				pr = make_pair(vec2[i].first, vec1[j].second);
				vec3.push_back(pr);
			}
		}
	}
	cout << "Композиция графиков B и A" << endl;
	for (int i = 0; i < vec3.size(); i++) {
		cout << "<" << vec3[i].first << ", " << vec3[i].second << "> ";
	}
	cout << endl;
	cont();
}

void projectionA() {
	system("cls");
	cout << "Проекция на первую ось" << endl;
	for (int i = 0; i < vec1.size(); i++) {
		cout << vec1[i].first << " ";
	}
	cout << endl;
	cout << "Проекция на вторую ось" << endl;
	for (int i = 0; i < vec1.size(); i++) {
		cout << vec1[i].second << " ";
	}
	cout << endl;
	cont();
}

void projectionB() {
	system("cls");
	cout << "Проекция на первую ось" << endl;
	for (int i = 0; i < vec2.size(); i++) {
		cout << vec2[i].first << " ";
	}
	cout << endl;
	cout << "Проекция на вторую ось" << endl;
	for (int i = 0; i < vec2.size(); i++) {
		cout << vec2[i].second << " ";
	}
	cout << endl;
	cont();
}

void cont() {//пользователь выбирает продолжить работу алгоритма, либо завершить работу алгоритма
	int x = 0;
	cout << "Введите 1 чтобы продолжить или 0, чтобы закончить: ";
	cin >> x;
	if (x == 1) {
		select_operation();
	}
	else {
		exit(0);
	}
}
