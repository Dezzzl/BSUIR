#include <iostream>
#include <math.h>
#include <ctime>
using namespace std;
int n1check() {
    int a;
    cin >> a;
    while (cin.fail() || (cin.peek() != '\n' && cin.peek() != ' '))
    {
        cout << "Попробуй снова" << endl;
        cin.clear();
        cin.ignore(256, '\n');
        cin >> a;
    }
    return a;
}
float ncheck() {
    float a;
    cin >> a;
    while (cin.fail() || (cin.peek() != '\n' && cin.peek() != ' '))
    {
        cout << "Попробуй снова" << endl;
        cin.clear();
        cin.ignore(256, '\n');
        cin >> a;
    }
    return a;
}
int main() {
    setlocale(LC_ALL, "Rus");
    int i, n, proizv = 1;

    float ind1, ind2, b;
    ind1 = -1;
    ind2 = -1;

    cout << "Введите длину массива(<=20): ";
    n = n1check();
    int* a = new int[n];
    if (n > 20 || n <= 0) {
        cout << "Размер не подходит по условию.";
        return 0;
    }
    cout << "Вы хотите самостоятельно задать элементы массива или сделать это рандомно? (1 - cам, 2 - ранд): ";
    int elem = n1check();
    switch (elem) {
    case 1: {for (i = 0; i < n; i++) {
        a[i] = ncheck();
        cout << " ";
    }
          break;
    }
    case 2: { { srand(time(0));
        for (int i = 0; i < n; i++) {
            a[i] = rand() % 100 - 50;
        } break; }
    }
    default: { cout << "Недопустимое значение"; return 0; }
    }
    for (i = 0; i < n; i++) {
        cout << "a[" << i + 1 << "]=" << a[i] << "-";
        if (fmod(i, 2) == 0) {
            cout << "нечет" << endl;

        }
        else { cout << "чет" << endl; }
    }
    cout << "----------" << endl;
    for (i = 0; i < n; i++) {
        if (a[i] == 0) {
            ind1 = i;
            break;
        }

    }
    if (ind1 == -1) {
        cout << "нулей нет";
        return 0;
    }
    for (i = ind1 + 1; i < n; i++) {
        if (a[i] == 0) {
            ind2 = i;
            break;
        }

    }
    if (ind2 == -1) {
        cout << "в массиве один 0";
        return 0;
    }
    b = fmod(ind1, 2);
    if (b == 0) {
        if ((ind2 - ind1) <= 3) {
            cout << "нечего умножать";
            return 0;
        }
        else
        {
            for (i = ind1 + 1; i < ind2; i += 2)
            {
                proizv *= a[i];
                cout << "a[" << i + 1 << "]=" << a[i] << " ";
            }
            cout << endl;
        }


    }
    else {
        if ((ind2 - ind1) <= 4)
        {
            cout << "нечего умножать";
            return 0;
        }
        else
        {
            for (i = ind1 + 2; i < ind2; i += 2)
            {
                proizv *= a[i];
                cout << "a[" << i + 1 << "]=" << a[i] << " ";
            }
            cout << endl;
        }
    }
    cout << "-----------" << endl << "произведение=" << proizv;
    delete[]a;
    return 0;
}