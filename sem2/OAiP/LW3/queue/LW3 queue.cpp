#include<iostream>
#include<iomanip>;
using namespace std;
struct queue {
	int info;
	queue* next;
};

void push(int info, queue**begin, queue**end){
	queue* st = new queue;
	st->info = info;
	st->next = NULL;
	if (*begin == NULL) { *begin = st; *end = st; }
	else {
		(*end)->next = st;
		*end = st;
	}
}

void info(queue*begin) {
	queue* st = begin;
	while (st != NULL) {
		cout << st->info<<" ";
		st = st->next;
	}
	cout << endl;

}

void sort(queue* begin) {
	queue* st = begin;
	queue* q = begin;
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
queue* pop(queue* begin) {
	if (begin == NULL)return nullptr;
	queue* st=begin;
	begin = begin->next;
	delete st;
	return begin;
}
void del(queue** begin, queue **end) {
	queue* st;
	while (*begin != NULL) {
		st = *begin;
		*begin=(*begin)->next;
		delete st;
	}
	*end = NULL;
}
void del_info(int info, queue**begin) {
	queue* st = *begin;
	queue* st1=*begin;
	while (st->info != info) {
		if (st->next->info == info)st1 = st;
		st = st->next;
	}
	if (st == NULL)cout << "Такого элемента нет\n";
	else if(st==*begin) {
		*begin = (*begin)->next;
		delete st;
	}
	else{
		st1->next = st->next;
		delete st;
	}
}
int find_max(queue* begin) {
	queue* st = begin;
	int max = begin->info;
	while (st != NULL) {
		if (st->info > max)max = st->info;
		st = st->next;
	}
	return max;
}
void task(queue* begin, queue** b1, queue** e1, int max) {
	queue* st = begin;
	while (st->info != max) {
		push(st->info, b1, e1);
		st = st->next;
	}
	push(max, b1, e1);

}
void menu() {
	cout << "Меню:" << endl << "1-Создать или добавить элемент в очередь" << endl
		<< "2-Просмотр" << endl << "3-Сортировка" << endl << "4-Удаление элемента" << 
		endl << "5-Удаление очереди" << endl<<"6-Удаление по значеню"<<endl
		<<"7-Индвивдуальное задание" << endl << "0-Выход" << endl;
}
int main() {
	setlocale(LC_ALL, "russian");
	int i = 0;
	bool peek = true;
	bool main = true;
	queue* begin = NULL;
	queue* end = NULL;
	queue* end1 = NULL;
	queue* begin1 = NULL;
	queue* buff1 = NULL;
	queue* buff2 = NULL;
	int choice;
	menu();
	while (1) {
		cin >> choice;
		if (choice == 1) {
			int a;
			cout << "Введите элемент\n";
			cin >> a;
			push(a, &begin, &end);
		}
		else if (choice == 2) {
			cout << "Очередь: ";
			info(begin);
		}
		else if (choice == 3) {
			sort(begin);
			cout << "Очередь после сортировки: ";
			info(begin);
		}
		else if (choice == 4) {
			begin = pop(begin);
			cout << "Очередь: ";
			info(begin);
		}
		else if (choice == 5) {
			del(&begin, &end);
		}
		else if (choice == 6) {
			int znach;
			cout << "Введите значение\n";
			cin >> znach;
			del_info(znach, &begin);
		}
		else if (choice == 7) {
			if (main) {
				int max = find_max(begin);
				task(begin, &begin1, &end1, max);
				info(begin1);
				cout << "Хотите поработать с этой очередью?\n 1-да, 2-нет\n";
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
			else cout << "Только с основной очередью!\n";
		}
		else if (choice == 8) {
			if (!peek) {
				del(&begin, &end);
				begin1 = begin; end1 = end;
				begin = buff1; end = buff2;
				main = true;
				peek = false;
			}
			else cout << "Вы работате с основной очередью!\n";
		}
		else if (choice == 0) { del(&begin, &end); break; }
	}
	return 0;
}
