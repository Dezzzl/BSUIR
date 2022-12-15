#include <iostream>
#include <vector>
#include <cmath>
using namespace std;
void by_stat(), by_enum(), cont(), select_operation(), intersection_of_sets(), combining_sets(), complementA(), complementB(), subtractionAB(), subtractionBA(), sym_subtraction(), cartesian_productAB(), cartesian_productBA();

vector<int> vec1, vec2;
int main() {
	setlocale(LC_ALL, "ru");
	system("cls");
	int c = 0;
	cout << "Выбирите способ задания множеств:\n1 - перечислением\n2 - высказыванием\n";
	cin >> c;
	switch (c) { //выбираем способ задания множест
	case 1:
		by_enum(); //перечислением
		break;
	case 2:
		by_stat();//высказыванием
		break;
	default:
		main();
	}
}

void by_enum() {//способ задания множеств перечислением
	int a = 0, pots1 = 0, pots2 = 0;
	cout << "Введите мощность множества A: ";
	cin >> pots1; //мощность множества A
	cout << "Введите мощность множества B: ";
	cin >> pots2; //мощность множества B
	cout << "Множество A" << endl;
	for (int i = 0; i < pots1; i++) {
		cout << "Введите элемент №" << i + 1 << ": ";
		cin >> a; //пользователь вводит с клавиатуры значение элемента
		vec1.push_back(a);//добавляем элемент в множество B
	}
	cout << "Множество B" << endl;
	for (int i = 0; i < pots2; i++) {
		cout << "Введите элемент №" << i + 1 << ": ";
		cin >> a;//пользователь вводит с клавиатуры значение элемента
		vec2.push_back(a);//добавляем элемент в множество B
	}
	select_operation();
}
void by_stat() {//способ задания множеств высказыванием
	int pots1 = 0, pots2 = 0, a = 0;
	cout << "Введите мощность множества A: ";
	cin >> pots1;//пользователь вводит мощность множества A
	for (int x = 1; x <= pots1; x++) {//задаём множество А высказыванием
		a = fabs(0.5 * pow((x - 10), 2) - 10 * x + 63);//высчитываем значение элемента
		vec1.push_back(a);//добавляем элемент в множество A
	}
	cout << "Множество A:" << endl;
	for (int i = 0; i < vec1.size(); i++) {//Выводим на экран множество А
		cout << vec1[i] << " ";
	}
	cout << endl;
	cout << "Введите мощность множества B: ";
	cin >> pots2;//пользователь вводит мощность множества B
	for (int x = 1; x <= pots2; x++) {// задаём множество В высказыванием
		a = fabs(0.3 * pow((x - 13), 2) - 7 * x + 40);//высчитываем значение элемента
		vec2.push_back(a);//добавляем знэлемент в множество B
	}
	cout << "Множество B:" << endl;
	for (int i = 0; i < vec2.size(); i++) {//Выводим на экран множество В
		cout << vec2[i] << " ";
	}
	cout << endl;
	select_operation();
}
void select_operation() {//пользователь выбирает операцию
	int x = 0;
	cout << "1 - пересечение множеств\n2 - объединение множеств\n3 - разность множеств A и B\n4 - разность множеств B и A\n5 - симметрическая разность\n6 - дополнение множества A\n7 - дополнение множества B\n8 - декартово произведение множеств A и B\n9 - декартово произведение множеств B и A\nВыберите операцию над множествами: ";
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
		cartesian_productAB();//декартово произведение мноежеств А и В
		break;
	case 9:
		cartesian_productBA();//декартово произведение множеств В и А
		break;
	default:
		select_operation();
		break;
	}
}
void intersection_of_sets() {//пересечение множеств
	system("cls");
	vector<int> vec3;// создаём пустое множество C
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
		cout << vec3[i] << ' ';
	}
	cout << endl;
	cont();//пользователь выбирает продолжить ли работу алгоритма
}
void combining_sets() {
	system("cls");
	int x;
	bool flag = false;
	vector<int> vec3;//создаём пустое множество C
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
		cout << vec3[i] << ' ';
	}
	cout << endl;
	cont();//пользователь выбирает продолжить ли работу алгоритма
}
void subtractionAB() {
	system("cls");
	bool f = true;
	vector<int> vec3;//создаём пустое множество C
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
		cout << vec3[i] << " ";
	}
	cout << endl;
	cont();//пользователь выбирает продолжить ли работу алгоритма
}

void subtractionBA() {
	system("cls");
	bool f = true;
	vector<int> vec3;//создаём пустое множество C
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
		cout << vec3[i] << " ";
	}
	cout << endl;
	cont();//пользователь выбирает продолжить ли работу алгоритма
}

void sym_subtraction() {//Симметрическая разность
	system("cls");
	bool f = true, flag = false;
	vector<int> vec3, vec4, vec5;//создаём пустое множество D, E и C
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
		cout << vec5[i] << " ";
	}
	cout << endl;
	cont();//пользователь выбирает продолжить ли работу алгоритма
}

void complementA() {
	system("cls");
	vector<int> vec3;//создаём универсальное множество U
	bool f = true;
	for (int x = 1; x <= 100; x++) {//заполняем множество U
		vec3.push_back(x);
	}
	cout << "Дополнение: " << endl;
	for (int i = 0; i < vec3.size(); i++) {//Операция разности U и A
		for (int j = 0; j < vec1.size(); j++) {
			if (vec3[i] == vec1[j]) {//сравниваем взятые элементы
				f = false;
			}
		}
		if (f) {
			cout << vec3[i] << " ";//выводим получившееся множество C
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
	vector<int> vec3;//создаём универсальное множество U
	bool f = true;
	for (int x = 1; x <= 100; x++) {//заполняем множество U
		vec3.push_back(x);
	}
	cout << "Дополнение: " << endl;
	for (int i = 0; i < vec3.size(); i++) {//разность множества U и A
		for (int j = 0; j < vec2.size(); j++) {
			if (vec3[i] == vec2[j]) {//сравниваем взятые элементы
				f = false;
			}
		}
		if (f) {//Выводим на экран получившееся множество C
			cout << vec3[i] << " ";
		}
		else {
			f = true;
		}
	}
	cout << endl;
	cont();//пользователь выбирает продолжить ли работу алгоритма
}

void cartesian_productAB() {
	system("cls");
	vector<int> vec3;
	cout << "Декартово произведение: " << endl;
	for (int i = 0; i < vec1.size(); i++) {
		for (int j = 0; j < vec2.size(); j++) {
			vec3.push_back(vec1[i]);//первому элементу кортежа зададим значение взятого элемента А
			vec3.push_back(vec2[j]);//второму элементу кортежа зададим значение взятого элемента B
		}
	}
	cout << "{ ";
	for (int i = 0; i < vec3.size() - 1; i += 2) {//Выводим на экран получившееся множество C
		cout << "<" << vec3[i] << ", " << vec3[i + 1] << "> ";
	}
	cout << "}" << endl;
	cont();//пользователь выбирает продолжить ли работу алгоритма
}

void cartesian_productBA() {
	system("cls");
	vector<int> vec3;
	cout << "Декартово произведение: " << endl;
	for (int i = 0; i < vec2.size(); i++) {
		for (int j = 0; j < vec1.size(); j++) {
			vec3.push_back(vec2[i]);//первому элементу кортежа зададим значение взятого элемента В
			vec3.push_back(vec1[j]);//второму элементу кортежа зададим значение взятого элемента A
		}
	}
	cout << "{ ";
	for (int i = 0; i < vec3.size() - 1; i += 2) {//Выводим на экран получившееся множество C
		cout << "<" << vec3[i] << ", " << vec3[i + 1] << "> ";
	}
	cout << "}" << endl;
	cont();//пользователь выбирает продолжить ли работу алгоритма
}

void cont() {//пользователь выбирает продолжить работу алгоритма, либо завершить работу алгоритма
	int x = 0;
	cout << "Введите 1 чтобы продолжить или 0, чтобы закончить: ";
	cin >> x;
	if (x == 1) {
		select_operation();
	}
}