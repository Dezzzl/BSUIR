#include<iostream>
#include<string>
#include<math.h>
using namespace std;
int check_int() {
	char max[] = "2147483647";
	int i, count_of_numbers = 0;
	char temp[100];
	int x = 0;
	bool flag = true;
	bool predel = true;
	while (flag) {
		gets_s(temp);
		int n = strlen(temp);
		for (i = 0; i < n; i++) {
			if (temp[i] >= '0' && temp[i] <= '9') {
				count_of_numbers++;
			}
		}
		if (count_of_numbers == n && n == 10) {
			for (i = 0; i < 10; i++) {
				if (int(temp[i]) > int(max[i])) {
					predel = false;
					break;
				}
			}
		}
		if (count_of_numbers == n && n<=10 && predel==true) {
			if (temp[0] == '0') { count_of_numbers = 0; predel=true, cout << "try again\n"; }
			else flag = false;
		}
		else { count_of_numbers = 0; cout << "try again\n"; predel = true; }
	
		}
	for (i = 0; i < strlen(temp); i++) {
		x += int((int(temp[i]) - 48) * pow(10, strlen(temp) - (i + 1)));
	}
	return x;
	}
	

double check_double() {
	int i, count_of_points=0, count_of_numbers=0, point=0;
	char temp[100];
	double x=0;
	bool flag=true;
	while (flag) {
		gets_s(temp);
		int n = strlen(temp);
		for (i = 0; i < n; i++) {
			if (temp[i] >= '0' && temp[i] <= '9') {
				count_of_numbers++;
		}
			if (temp[i] == '.') {
				count_of_points++;
				point = i;
			}
		}
		if (((count_of_points + count_of_numbers == n) && count_of_points == 1)) {
			if ((temp[0] == '0' && temp[1] == '0') || temp[0] == '.' || temp[n - 1] == '.') { count_of_points = 0; count_of_numbers = 0; cout << "try again\n"; }
			else flag = false;
		}
		else { count_of_points = 0; count_of_numbers = 0; cout << "try again\n"; };
	

	}
	for (i = 0; i < point; i++) {
		x += double((int(temp[i]) - 48.0) * pow(10.0, point-(i+1)));	
}
	for (i = point + 1; i < strlen(temp); i++) {
		x+= double((int(temp[i]) - 48.0) / pow(10.0, (i-point)));
	}
	return x;
}
int main() {
	int f1;
	f1 = check_int();
	cout << f1<<"\n";
	double f;
	f = check_double();
	cout << f;
	return 0;
}
