#include <iostream>
using namespace std;
int main() {
    int n, i = 0, k = 0, j = 0, degree, rec = 0, buff, p = 0, d = 1;
    int** a;
    int** b;
    int** c;
    cout << "Input  size" << endl;
    cin >> n;
    a = new int* [n];
    for (i = 0; i < n; i++)a[i] = new int[n];
    b = new int* [n];
    for (i = 0; i < n; i++)b[i] = new int[n];
    c = new int* [n];
    for (i = 0; i < n; i++)c[i] = new int[n];
    for (i = 0; i < n; i++) {
        for (j = 0; j < n; j++)
        {
            cout << "Input a[" << i + 1 << "][" << j + 1 << "]=";
            cin >> a[i][j];
        }
    }
    cout << "Your matrix\n";
    for (i = 0; i < n; i++) {
        for (j = 0; j < n; j++) {
            cout << a[i][j] << " ";
        }
        cout << "\n";
    }
    cout << "Choose a degree\n";
    cin >> degree;
    for (i = 0; i < n; i++) {
        for (j = 0; j < n; j++) {
            b[i][j] = a[i][j];
            c[i][j] = a[i][j];
        }
    }
    j = 0; i = 0;
    for (d = 2; d <= degree; d++) {
        while (i < n) {

            for (k = 0; k < n; k++) {
                buff = b[i][k] * a[k][j];
                rec += buff;

            }
            c[i][j] = rec;
            rec = 0;
            buff = 0;
            if (j == n - 1) { j = 0; i++; }
            else { j++; }
        }

        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                b[i][j] = c[i][j];
            }
        }
        j = 0;
        i = 0;

    }
    cout << "Matrix in " << degree << " degree\n";
    for (i = 0; i < n; i++) {
        for (j = 0; j < n; j++) {
            cout << c[i][j] << " ";
        }
        cout << "\n";
    }
    for (i = 0; i < n; i++) {
        delete[] a[i];
        delete[] b[i];
        delete[] c[i];
    }
    delete[] a;
    delete[] b;
    delete[] c;
    return 0;
}
