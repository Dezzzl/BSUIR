#include <iostream>
using namespace std;
int main() {
    setlocale(LC_ALL, "russian");
    int** a, n, m, i, j, k, p, buff1 = 0, buff2 = 0, summ = 0;
    bool flag = true;
    cout << "Введите  n, m" << endl;
    cin >> n >> m;
    a = new int* [n];
    for (i = 0; i < n; i++)a[i] = new int[m];
    for (i = 0; i < n; i++) {
        for (j = 0; j < m; j++)
        {
            cout << "Введи a[" << i + 1 << "][" << j + 1 << "]=";
            cin >> a[i][j];
        }
    }
    for (i = 0; i < n; i++) {
        for (j = 0; j < m; j++)
        {
            if (j == m - 1) { buff2 = 0; buff1++; }
            else { buff2 = j + 1; }
            for (p = buff1; p < n; p++) {
                for (k = buff2; k < m; k++)
                {
                    if (a[i][j] == a[p][k]) { flag = false; }
                }
                buff2 = 0;
            }

            if (flag == true) { summ += 1; }
            else {
                flag = true;
            }
        }


    }
    cout << "кол-во элементов=" << summ;
    for (i = 0; i < n; i++) { delete[]a[i]; }
    delete[]a;
    return 0;
}