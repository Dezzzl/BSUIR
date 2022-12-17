#include <iostream>
#include<math.h>
using namespace std;
int main() {
	char st[100], sl[100], result[100];
	int i = 0, k = 0, j = 0, count = 0;
	int* mass;
	gets_s(st);
	strcat_s(st, " ");
	int n = strlen(st);
	sl[0] = '\0';
	for (i = 0; i < n; i++) {
		if (st[i] == '+' || st[i] == '-' || st[i] == '*' || st[i] == '/')
		{
			sl[k] = st[i];
			sl[k + 1] = '\0';
			k++;

		}
		else
		{
			if (strlen(sl) > count) {
				count = strlen(sl);
				k = 0;
				for (j = 0; j < count; j++) {
					result[j] = sl[k];
					result[j + 1] = '\0';
					k++;
				}

			}
			k = 0;
			sl[0] = '\0';
		}
	}
	puts(result);
}