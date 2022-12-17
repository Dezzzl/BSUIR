//Вывод на экран чисел строки(разделены пробелами) в порядке возрастания их значений


#include <iostream>
#include<math.h>
using namespace std;
int main() {
	char st[100], sl[100];
	int* mass;
	int j=0, k = 0, i=0, count=0;
	int buff = 0;
	int rec = 0;
	gets_s(st);
	strcat_s(st, " ");
	int n = strlen(st);
	sl[0] = '\0';
	for (i = 0; i < n; i++) {
		if (st[i] != ' ')
		{
			sl[k] = st[i];
			sl[k + 1] = '\0';
			k++;
		}
		else
		{
			if (strlen(sl) > 0) {
				count++;
				

			}
			k = 0;
			sl[0] = '\0';
		}
	}
		mass = new int[count];
		count = 0;

		for (i = 0; i < n; i++) {
			if (st[i] != ' ')
			{
				sl[k] = st[i];
				sl[k + 1] = '\0';
				k++;
			}
			else
			{
				if (strlen(sl) > 0) {
					 buff = 0;
					 rec = 0;
					for (k = 0; k < strlen(sl); k++) {
						rec = (pow(10, (strlen(sl) - (k+1))))*(int(sl[k])-48);
						buff += rec;
						rec = 0;
					}
					
					mass[count] = buff;
					count++;
					buff = 0;
					rec = 0;
					
				}
				k = 0;
				sl[0] = '\0';
				buff = 0;
				 rec = 0;
			}
		} 
		for (i = 0; i < count; i++) {
			for (j =0; j < count; j++) {
				if (mass[i] < mass[j]) {
					buff = mass[i];
					mass[i] = mass[j];
					mass[j] = buff;
				}
			}
			
		}
		for (i = 0; i < count; i++) { cout << mass[i] << " "; }
		delete []mass;
		return 0;
	}
