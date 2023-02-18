#include<iostream>
using namespace std;
struct List {
	int info;
	List* next;
	List* prev;
};

void push_back(int info, List** begin, List** end) {
	List* st = new List;
	st->info = info;
	st->next = NULL;
	if (*begin == NULL) { st->prev = NULL; *begin = st; *end = st; }
	else {
		(*end)->next = st;
		st->prev = *end;
		*end = st;
	}
}
void push_front(int info, List** begin, List** end) {
	List* st = new List;
	st->info = info;
	st->prev = NULL;
	if (*begin == NULL) { st->next = NULL; *begin = st; *end = st; }
	else {
		(*begin)->prev = st;
		st->next = (*begin);
		*begin = st;
	}
}

void info(List* begin) {
	List* st = begin;
	while (st != NULL) {
		cout << st->info << " ";
		st = st->next;
	}
	cout << endl;

}

void sort(List* begin) {
	List* st = begin;
	List* q = begin;
	if (q == NULL)return;
	while (q->next != NULL) {
		while (st->next != NULL) {
			if (st->info > st->next->info) {
				int buff = st->info;
				st->info = st->next->info;
				st->next->info = buff;
			}
			st = st->next;
		}
		q = q->next;
		st = begin;
	}
}
List* pop_back(List** end, List** begin) {
	if ((*end)->prev == NULL)return nullptr;
	List* st = *end;
	*end = (*end)->prev;
	(*end)->next = NULL;
	delete st;
	return *begin;
}
List* pop_front(List** begin, List **end) {
	if ((*end)->prev == NULL)return nullptr;
	List* st = *begin;
	*begin =(*begin)->next;
	(*begin)->prev = NULL;
	delete st;
	return *begin;
}
void del(List** begin, List** end) {
	List* st;
	while (*begin != NULL) {
		st = *begin;
		*begin = (*begin)->next;
		delete st;
	}
	*end = NULL;
}
void del_info(int info, List** begin, List**end) {
	List* st = *begin;
	while (st->info != info) {
		st = st->next;
	}
	if (st == NULL)cout << "Такого элемента нет\n";
	else if (st == *begin) {
		*begin = (*begin)->next;
		(*begin)->prev = NULL;
		delete st;
	}
	else if (st == *end) {
		*end = (*end)->prev;
		(*end)->next = NULL;
		delete st;
	}
	else {
		st->prev->next = st->next;
		delete st;
	}
}
int find_max(List* begin) {
	List* st = begin;
	int max = begin->info;
	while (st != NULL) {
		if (st->info > max)max = st->info;
		st = st->next;
	}
	return max;
}
void task(List* begin, List** b1, List** e1, int max) {
	List* st = begin;
	while (st->info != max) {
		push_back(st->info, b1, e1);
		st = st->next;
	}
	push_back(max, b1, e1);
}
void menu() {
	cout << "Меню:" << endl << "1-Создать или добавить элемент в начало списка" << endl
		<<"2-Создать или добавить элемент в конец списка" <<endl<< "3-Просмотр" << endl << "4-Сортировка" << endl << "5-Удаление элемента с конца" <<
		endl <<"6-Удаление с начала" <<endl<< "7-Удаление очереди" << endl << "8-Удаление по значеню" << endl
		<< "9-Индвивдуальное задание" << endl << "10-Основной список" << endl << "11-Меню" << endl << "0-Выход" << endl;
}
int main() {
	setlocale(LC_ALL, "russian");
	int i = 0;
	bool peek = true;
	bool main = true;
	List* begin = NULL;
	List* end = NULL;
	List* end1 = NULL;
	List* begin1 = NULL;
	List* buff1 = NULL;
	List* buff2 = NULL;
	int choice;
	menu();
	while (1) {
		cin >> choice;
		if (choice == 1) {
			int a;
			cout << "Введите элемент\n";
			cin >> a;
			push_front(a, &begin, &end);
		}
		else if (choice == 2) {
			int a;
			cout << "Введите элемент\n";
			cin >> a;
			push_back(a, &begin, &end);
		}
		else if (choice == 3) {
			cout << "Список: ";
			info(begin);
		}
		else if (choice == 4) {
			sort(begin);
			cout << "Список после сортировки: ";
			info(begin);
		}
		else if (choice == 5) {
			begin = pop_back(&end, &begin);
			cout << "Список: ";
			info(begin);
		}
		else if (choice == 6) {
			begin = pop_front(&begin, &end);
			cout << "Список: ";
			info(begin);
		}
		else if (choice == 7) {
			del(&begin, &end);
		}
		else if (choice == 8) {
			int znach;
			cout << "Введите значение\n";
			cin >> znach;
			del_info(znach, &begin, &end);
		}
		else if (choice == 9) {
			if (main) {
				int max = find_max(begin);
				task(begin, &begin1, &end1, max);
				info(begin1);
				cout << "Хотите поработать с этим списком?\n 1-да, 2-нет\n";
				int choice1;
				cin >> choice1;
				switch (choice1)
				{
				case 1:
				{
					buff1 = begin; buff2 = end; begin = begin1; end = end1; peek = false;	main = false; break;
				}
				case 2:default:
				{
					del(&begin1, &end1); break;
				}
				}
			}
			else cout << "Только с основным списком!\n";
		}
		else if (choice == 10) {
			if (!peek) {
				del(&begin, &end);
				begin1 = begin; end1 = end;
				begin = buff1; end = buff2;
				main = true;
				peek = true;
			}
			else cout << "Вы работате с основным списком!\n";
		}
		else if (choice == 11) menu();
		else if (choice == 0) { del(&begin, &end); break; }
	}
	return 0;
}
