#include <iostream>
#include <cmath>
using namespace std;
double S(double), Y(double), S_Y(double x);
void Out_Rez(double (*f)(double), double, double, double);
int main() {
	int z;
	double a, b, h;
	setlocale(LC_ALL, "ru");
	cout << "Введите a: ";
	cin >> a;
	cout << "Введите b: ";
	cin >> b;
	cout << "Введите h: ";
	cin >> h;
	cout << "1 - вычислить S(x)\n2 - вычислить Y(x)\n3 - |S(x)-Y(x)|\n0 - закрыть программу\n";
	cin >> z;
	switch (z) {
	case 1:
		Out_Rez(S, a, b, h);
		break;
	case 2:
		Out_Rez(Y, a, b, h);
		break;
	case 3:
		Out_Rez(S_Y, a, b, h);
		break;
	case 0:
		break;
	default:
		main();
		break;
	}
}

double S(double x) {
	double s = 0;
	for (int k = 1; k <= 15; k++) {
		s += (pow(-1.0, k + 1) * pow(x, 2 * k + 1) / (4 * pow(k, 2) - 1));
	}
	return s;
}

double Y(double x) {
	return ((1 + pow(x, 2)) / 2.0) * atan(x) - x / 2.0;
}

double S_Y(double x) {
	return fabs(S(x) - Y(x));
}

void Out_Rez(double (*f)(double), double a, double b, double h) {
	system("cls");
	int number = 1;
	for (double x = a; x <= b; x += h) {
		cout << number << "\t" << f(x) << endl;
		number++;
	}
	main();
}