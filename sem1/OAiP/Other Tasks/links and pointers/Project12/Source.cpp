#include <iostream>
using namespace std;
int operations(int a, int b, int &razn, int &proizv, int &summ_of_kvadr) {
	razn = a - b;
	proizv = a * b;
	summ_of_kvadr = a * a + b * b;
	return a + b;
}
int other_operations(int *a, int *b, int &razn) {
	razn = *a - *b;
	return *a + *b;
}
int main() {
	int a = 8, b = 4, razn, proizv, summ_of_kvadr, m = 6, n = 3;
	int razn1;
	int* l;
	int* p = &m;
	int* k = &n;
	l = &a;
	cout << "summ=" << operations(a, b, razn, proizv, summ_of_kvadr) << "\n";
	cout << "razn=" << razn << "\n";
	cout << "ptoizv=" << proizv << "\n";
	cout << "summ_of_kvadr=" << summ_of_kvadr << "\n";
	cout << "summ2=" << other_operations(p, k, razn1) << "\n";
	cout << "razn2=" << razn1 << "\n";
	cout << "l=" << *l << "\n";
	cout << "ptr_l=" << l << "\n";
	return 0;
}