#include <iostream>
#include <cmath>
using namespace std;
double f(double x, double& n) {
	return pow(2.7182818284590, 2 * x);
}

double summ(double x, double& n) {
	double rec = 1;
	double sum = 1;
	double eps = n;
	n = 1;
	while (fabs(sum - f(x, n)) >= eps) {
		rec *= 2 * x / n;
		sum += rec;
		n++;
	}
	n = eps;
	return sum;
}
double raznost(double x, double& n)
{
	return fabs(summ(x, n) - f(x, n));
}


void Out_Rez(double (*f)(double, double&), double a, double b, double h, double& eps) {
	system("cls");
	int number = 1;
	for (double x = a; x <= b; x += h) {
		cout << number << "\t" << f(x, eps) << endl;
		number++;
	}

}
int main() {
	int z;
	double a, b, h, eps;
	setlocale(LC_ALL, "ru");
	cout << "Введите a: ";
	cin >> a;
	cout << "Введите b: ";
	cin >> b;
	cout << "Введите h: ";
	cin >> h;
	cout << "Введите Эпс: ";
	cin >> eps;
	cout << "1 - вычислить summ(x)\n2 - вычислить f(x)\n3 - |summ(x)-f(x)|\n0 - закрыть программу\n";
	cin >> z;
	switch (z) {
	case 1:
		Out_Rez(summ, a, b, h, eps);
		break;
	case 2:
		Out_Rez(f, a, b, h, eps);
		break;
	case 3:
		Out_Rez(raznost, a, b, h, eps);
	case 0:
		break;
	default:
		main();
		break;
	}
	return 0;
}