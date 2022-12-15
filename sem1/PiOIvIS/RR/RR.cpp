#include <iostream>
#include <fstream>
using namespace std;
string tests(int numb) {
    string path;
    switch (numb) {
    case 1: { path = "input1.txt"; }
          break;
    case 2: { path = "input2.txt"; }
          break;
    case 3: { path = "input3.txt"; }
          break;
    case 4: { path = "input4.txt"; }
          break;
    case 5: { path = "input5.txt"; }
          break;
    default: cout << "Invalid input. Therefore , the path is first \n"; path = "input1.txt";
    }
    return path;
}
int** file_read(int& n, int& m, string& path) {
    ifstream in(path);
    if (in.is_open())
    {
        int count = 0;
        int temp;
        while (!in.eof())
        {
            in >> temp;
            count++;
        }
        in.seekg(0, ios::beg);
        in.clear();
        int count_space = 0;
        char symbol;
        while (!in.eof())
        {
            in.get(symbol);
            if (symbol == ' ') count_space++;
            if (symbol == '\n') break;
        }
        in.seekg(0, ios::beg);
        in.clear();
        int** x;
        n = count / (count_space + 1);
        m = count_space + 1;
        x = new int* [n];
        cout << "the incidence matrix of the original graph\n";
        for (int i = 0; i < n; i++) x[i] = new int[m];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                in >> x[i][j];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++)
            {
                cout << x[i][j] << " ";
            }
            cout << "\n";
        }
        in.close();
        return x;
    }
    else
    {
        cout << "The file was not found.";
    }
}
void check(int** x, int& n, int& m) {

    int i, j;
    int number_of_first = 0, number_of_second = 0, number_of_ones = 0, stolb = 0;
    bool flag = true;
    for (i = 0; i < m; i++) {
        for (j = 0; j < n; j++) {
            if (x[j][i] == 1) { number_of_ones++; }
        }
        if (number_of_ones == 2)
        {
            stolb = i;
            for (i = 0; i < n; i++) {
                if (x[i][stolb] == 1)
                {
                    number_of_first = i;
                    break;
                }
            }
            for (i = number_of_first + 1; i < n; i++) {
                if (x[i][stolb] == 1)
                {
                    number_of_second = i;
                    break;
                }
            }
            break;
        }
        else number_of_ones = 0;
    }
    if (!(number_of_first == 0 && number_of_second == 0)) {
        for (j = 0; j < m; j++) {
            if (x[number_of_second][j] == 1) {
                x[number_of_first][j] = 1;
            }
        }
        for (i = number_of_second; i < n - 1; i++) {

            x[i] = x[i + 1];

        } ofstream out("Output.txt");
        cout << "the incidence matrix of the closure graph\n";
        out << "the incidence matrix of the closure graph\n";
        for (i = 0; i < n - 1; i++) {
            for (j = 0; j < m; j++)
            {
                out << x[i][j] << " ";
                cout << x[i][j] << " ";
            }
            cout << "\n";
            out << "\n";
        }

        cout << "closed vertex is " << number_of_first + 1 << "," << number_of_second + 1;
        out << "closed vertex is " << number_of_first + 1 << "," << number_of_second + 1;
        out.close();
    }
    else {
        ofstream out("Output.txt");
        cout << "there are no incident vertices in this graph";
        out << "there are no incident vertices in this graph";
        out.close();
    }
    for (i = 0; i < n - 1; i++) { delete[]x[i]; }
    delete[]x;
}
int main() {
    cout << "select path(int from 1 to 5)=";
    int par_path, m = 0, n = 0;
    int** a;
    cin >> par_path;
    string path = tests(par_path);
    a = file_read(n, m, path);
    check(a, n, m);
    return 0;
}