#include <iostream>
#include<math.h>
using namespace std;
int main() {
	char st[100], sl[200];
	int i = 0;
	gets_s(st);
	strcat_s(st, " ");
	int n = strlen(st);
	for (i = 0; i < 2 * n; i++) {
		sl[i] = ' ';
	}
	cout << "First output\n";
	for (i = 0; i < n; i++) {
		cout << st[i] << "\n";

	}
	cout << "second output\n";
	for (i = 0; i < n; i++) {
		for (int j = 0; j < 2*i; j++) {
			cout << sl[j];

		}
		cout << st[i];
		
		cout << "\n";
	}
}