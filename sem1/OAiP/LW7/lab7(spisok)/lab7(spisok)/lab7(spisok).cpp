#define _CRT_SECURE_NO_WARNINGS
#include <iostream>
#include <io.h>
#include <stdio.h>
using namespace std;

struct St
{
	char FIO[50];
	char Group[10];
	int mat, phisica, infa;
	double sr_ball;
};

void Create(FILE* file);
void PokazatStudents(FILE* file);
void Individ(FILE* file);
void Redact(FILE* file);
void Delete(FILE* file);
void SortFIO(FILE* file);
bool SravnenieBukv(char one[], char two[]);
void Sort_Sr_ball_Vbivanie(FILE* file);
void InfaStudentov(St* s);
void Sort_Sr_ball_Vozrastanie(FILE* file);


void Menu() {
	FILE* file = fopen("student.txt", "wb");
	fclose(file);
	bool menu = 1;
	while (menu == 1) {
		cout << "1. Создать студента" << endl;
		cout << "2. Просмотреть всех студентов" << endl;
		cout << "3. Решение индивидуального задания" << endl;
		cout << "4. Изменить информацию" << endl;
		cout << "5. Удалить студента" << endl;
		cout << "6. Сортировка ФИО(по алфавиту)" << endl;
		cout << "7. Сортировка по среднему балу(возрастание" << endl;
		cout << "8. Сортировка по среднему балу(убывание)" << endl;
		cout << "9. Выйти из программы" << endl;
		int n;
		cin >> n;
		switch (n) {
		case 1: Create(file); break;
		case 2: PokazatStudents(file); break;
		case 3: Individ(file); break;
		case 4: Redact(file); break;
		case 5: Delete(file); break;
		case 6: SortFIO(file); break;
		case 7:Sort_Sr_ball_Vozrastanie(file); break;
		case 8:Sort_Sr_ball_Vbivanie(file); break;
		case 9: menu = 0; break;
		default: cout << "Такого пункта нет";
		}
	}
}
int main()
{
	setlocale(LC_ALL, "Russian");
	Menu();
}

void Create(FILE* file) {
	file = fopen("student.txt", "ab");
	cout << "Введите фамилию, номер группы, оценки за математику, физику и информатику\n";
	St s;
	cin >> s.FIO >> s.Group >> s.mat >> s.phisica >> s.infa;
	s.sr_ball = (s.mat + s.phisica + s.infa) / 3;
	fwrite(&s, sizeof(St), 1, file);
	fclose(file);
}

void PokazatStudents(FILE* file) {
	file = fopen("student.txt", "rb");
	St s;
	int kolvo = _filelength(_fileno(file)) / sizeof(St);
	for (int i = 0; i < kolvo; i++) {
		fread(&s, sizeof(St), 1, file);
		InfaStudentov(&s);
	}

	fclose(file);
}
void InfaStudentov(St* s)
{
	cout << "ФИО: " << s->FIO << "\nГруппа: " << s->Group << endl;
	cout << "Оценки:\nМатематика: " << s->mat << "\nФизика: " << s->phisica << " \nИнформатика: " << s->infa << endl;
	cout << "Ср балл: " << s->sr_ball << endl << endl;
}

void Individ(FILE* file) {
	file = fopen("student.txt", "rb");
	St s;
	int kolvo = _filelength(_fileno(file)) / sizeof(St);
	for (int i = 0; i < kolvo; i++) {
		fread(&s, sizeof(St), 1, file);
		if (s.mat>4 && s.infa>4) {
			InfaStudentov(&s);
		}
	}
	fclose(file);
}

void Redact(FILE* file) {
	file = fopen("student.txt", "r+b");
	cout << "Введите номер студента\n";
	int n;
	cin >> n;
	cout << "Выберите критерий, который хотите изменить \n1) ФИО \n2) Группу \n3) Оценки\n";
	int choose;
	cin >> choose;
	St s;
	fseek(file, (int)(sizeof(St)) * n - 1, 0);
	fread(&s, sizeof(St), 1, file);
	InfaStudentov(&s);
	switch (choose)
	{
	case 1: cin >> s.FIO; break;
	case 2: cin >> s.Group; break;
	case 3:
	{
		cin >> s.mat >> s.phisica >> s.infa;
		s.sr_ball = (s.infa + s.mat + s.phisica) / 3;
	}
	fwrite(&s, sizeof(St), 1, file);
	}
	fclose(file);
}

void Delete(FILE* file) {
	file = fopen("student.txt", "r+b");
	int kolvo = _filelength(_fileno(file)) / sizeof(St);
	int n;
	cout << "Введите номер студента";
	cin >> n;
	cout << "Вы уверены, что хотите удалить студента?\n 1.Да\n 2.Нет\n";
	int choose;
	cin >> choose;
	if (choose == 1)
	{

		for (int i = n; i < kolvo; i++)
		{
			St s;
			fseek(file, (int)(sizeof(St)) * (i + 1), 0);
			fread(&s, sizeof(St), 1, file);
			fseek(file, (int)(sizeof(St)) * i, 0);
			fwrite(&s, sizeof(St), 1, file);
		}
		kolvo--;
		_chsize(_fileno(file), kolvo * (int)(sizeof(St)));
	}
	fclose(file);
}

void SortFIO(FILE* file)
{
	file = fopen("student.txt", "r+b");
	int kolvo = _filelength(_fileno(file)) / sizeof(St);
	for (int i = kolvo - 1; i > 0; i--)
		for (int j = 0; j < i; j++) {
			St s1, s2;
			fseek(file, (int)(sizeof(St)) * j, 0);
			fread(&s1, sizeof(St), 1, file);
			fread(&s2, sizeof(St), 1, file);
			if (SravnenieBukv(s1.FIO, s2.FIO)) {
				fseek(file, (int)(sizeof(St)) * j, 0);
				fwrite(&s2, sizeof(St), 1, file);
				fwrite(&s1, sizeof(St), 1, file);

			}
		}

	fclose(file);
}
bool SravnenieBukv(char one[], char two[]) {
	for (int i = 0; i < 50; i++)
		if (one[i] < two[i])
			return true;
	return false;
}
void Sort_Sr_ball_Vbivanie(FILE* file) {
	file = fopen("student.txt", "r+b");
	int kolvo = _filelength(_fileno(file)) / sizeof(St);
	for (int i = kolvo - 1; i > 0; i--)
		for (int j = 0; j < i; j++) {
			St s1, s2;
			fseek(file, (int)(sizeof(St)) * j, 0);
			fread(&s1, sizeof(St), 1, file);
			fread(&s2, sizeof(St), 1, file);
			if (s1.sr_ball < s2.sr_ball) {
				fseek(file, (int)(sizeof(St)) * j, 0);
				fwrite(&s2, sizeof(St), 1, file);
				fwrite(&s1, sizeof(St), 1, file);

			}
		}
	fclose(file);
}
void Sort_Sr_ball_Vozrastanie(FILE* file) {
	file = fopen("student.txt", "r+b");
	int kolvo = _filelength(_fileno(file)) / sizeof(St);
	for (int i = kolvo - 1; i > 0; i--)
		for (int j = 0; j < i; j++) {
			St s1, s2;

			fseek(file, (int)(sizeof(St)) * j, 0);
			fread(&s1, sizeof(St), 1, file);
			fread(&s2, sizeof(St), 1, file);
			if (s1.sr_ball > s2.sr_ball) {
				fseek(file, (int)(sizeof(St)) * j, 0);
				fwrite(&s2, sizeof(St), 1, file);
				fwrite(&s1, sizeof(St), 1, file);

			}
		}
	fclose(file);
}
