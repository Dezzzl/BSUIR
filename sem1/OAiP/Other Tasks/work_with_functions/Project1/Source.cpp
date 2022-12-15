////lab5.2 8 variant
#include <iostream>
using namespace std;   
void ArrayInit(int** A, int N, int M)
{
    srand(time(0));
    for (int i = 0; i < N; i++)
        for (int j = 0; j < M; j++)
            A[i][j] = rand() % (100) - 50;
}

void PrintMatrix(int** matrix, int n, int m) {
    for (int i = 0; i < n; i++) {
        cout << "Row " << (i + 1) << ": ";
        for (int j = 0; j < m; j++) {
            cout << "\t" << matrix[i][j];
        }
        cout << "\n";
    }
    cout << "\n";
}
void Operation(int** a, int n, int m)
{
    int i, j, k, p, buff1 = 0, buff2 = 0, summ = 0;
    bool flag = true;
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
    cout<<"quantity of numbers=" << summ;
}
int main() {
    int **a,n,m,i,j;
    bool flag = true;
    cout << "Input size" << endl;
    cin >> n >> m;
    a = new int* [n];
    for (i = 0; i < n; i++)a[i] = new int[m];
    ArrayInit(a, n, m);
    PrintMatrix(a, n, m);
    Operation(a, n, m);
    for (i = 0; i < n; i++) { delete[]a[i]; }
    delete[]a;
    return 0;
}
