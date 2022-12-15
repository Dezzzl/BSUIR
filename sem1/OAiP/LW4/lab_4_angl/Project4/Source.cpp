#include <iostream>
#include <math.h>
#include <ctime>
using namespace std;
int n1check() {
    int a;
    cin >> a;
    while (cin.fail() || (cin.peek() != '\n' && cin.peek() != ' '))
    {
        cout << "try again" << endl;
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
        cout << "try again" << endl;
        cin.clear();
        cin.ignore(256, '\n');
        cin >> a;
    }
    return a;
}
int main() {

    int i, n, proizv = 1;

    float ind1, ind2, b;
    ind1 = -1;
    ind2 = -1;
    int* a = new int[n];
    cout << "Enter the length of the array(<=20): ";
    n = n1check();

    if (n > 20 || n <= 0) {
        cout << "The size does not fit the condition.";
        return 0;
    }
    cout << "Do you want to set the array elements yourself or do it randomly? (1 - himself, 2 - rand): ";
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
    default: { cout << "Invalid value"; return 0; }
    }
    for (i = 0; i < n; i++) {
        cout << "a[" << i + 1 << "]=" << a[i] << "-";
        if (fmod(i, 2) == 0) {
            cout << "odd" << endl;

        }
        else { cout << "even-numbered" << endl; }
    }
    cout << "----------" << endl;
    for (i = 0; i < n; i++) {
        if (a[i] == 0) {
            ind1 = i;
            break;
        }

    }
    if (ind1 == -1) {
        cout << "there are no zeros";
        return 0;
    }
    for (i = ind1 + 1; i < n; i++) {
        if (a[i] == 0) {
            ind2 = i;
            break;
        }

    }
    if (ind2 == -1) {
        cout << "there is one 0 in the array";
        return 0;
    }
    b = fmod(ind1, 2);
    if (b == 0) {
        if ((ind2 - ind1) <= 3) {
            cout << "nothing to multiply";
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
            cout << "nothing to multiply";
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
    cout << "-----------" << endl << "composition=" << proizv;
    delete[]a;
    return 0;
}