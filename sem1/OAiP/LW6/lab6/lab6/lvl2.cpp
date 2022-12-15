#include<iostream>
using namespace std;
int main() {
	char st[100], sl[100];
	int k = 0, j, d = 0, g = -1, p = -1;
		int h = 0, i, m = 0, count = 0;
	gets_s(st);
	strcat_s(st, " ");
	int n = strlen(st);
	if (n < 2) return 1;
	sl[0] = '\0';
	for (i = 0; i < n; i++)
		if (st[i] != ' ')
		{
			sl[k] = st[i];
			sl[k + 1] = '\0';
			k++;
		}
		else
		{
			if (strlen(sl) > 0) {
				m = strlen(sl);
			
				for (j = 0; j < m ; j++) {
					if (sl[j] == '.') { d = d + 1; h = j; }

				}	
				if (d!=1 ||(m==1 && h==0))
				{
					sl[0] = '\0';
					k = 0;
					d = 0;
					continue;
				}
					if (sl[0] !='-') {
						for (j = 0; j < m; j++) {
							if (sl[j] >= '0' && sl[j] <= '9' && sl[0] != '.' && sl[m - 1] != '.' && sl[m - 1] != '0' || sl[j] == '.') { continue; }
							else {
								p = j;
								break;

							}
						}

						if (p != -1) {
							sl[0] = '\0';
							k = 0;
							d = 0; p = -1;
							continue;
						}
					}
					  if(sl[0]=='-') {
						for (j = 1; j < m; j++) {
							if (sl[j] >= '0' && sl[j] <= '9' && sl[1] != '.' && sl[m - 1] != '.' && sl[m - 1] != '0' || sl[j] == '.') { continue; }
							else {
								g = j;
								break;

							}
						}
						
						if (g != -1) {
							sl[0] = '\0';
							k = 0;
							d = 0;
							g = -1;
							continue;
						}
					
				}
					
				if (sl[0] == '0' && h > 1 || sl[0] == '-' && sl[1] == '0' && h > 2 || sl[0] == '-' && sl[1] == '.'||m<=2) {
					sl[0] = '\0';
					k = 0;
					d = 0;
					continue;
				}
				else
				{
					puts(sl );
					sl[0] = '\0';
					k = 0;
					d = 0;
					count = count + 1;
						}
				}
					
		
				}
	if (count == 0)cout << "Nothing";
	return 0;
				}
			
		
		

//00.132 0.0 1312.00    0 1 2 (3) 4 5 6           kl.jf 06.8 1342.23423 234.0000023
