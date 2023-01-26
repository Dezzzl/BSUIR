#include <iostream>
#include <conio.h>
using namespace std;
double sqrt_with_recursion(int n, double a) {
	return n == 0 ? 0.5 * (1 + a) : 0.5 * (sqrt_with_recursion(n - 1, a) + a / sqrt_with_recursion(n - 1, a));
}
double sqrt_without_recursion(int n, double a) {
	double x= 0.5 * (1 + a);;
		while (n != 0) {
			x = 0.5 * (x + a / x);
			n--;
		}
	return x;
}
int main() {
	do {
		double x;
		int n;
		cout << "enter the number\n";
		cin >> x;
		cout << "enter the accuracy\n";
		cin >> n;
		cout << "sqrt(with recursion)=" << sqrt_with_recursion(n, x);
		cout << "\nsqrt(without recursion)=" << sqrt_without_recursion(n, x);
		cout << "\nPress n to exit, any other key to repeat.\n";
	} while (_getch() != 'n');
	return 0;
}
