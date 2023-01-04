#include <iostream>
#include <fstream>
#include <string>
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
    case 6: { path = "input6.txt"; }
          break;
    }
    return path;
}
void read(ifstream& out) {
    if (out.is_open()) {
        char ch;
        while (out.get(ch))
        {
            cout << ch;
        }
    }
}
int** file_read(int& n, int& m, string& path, ofstream& out, int& number_of_first, int& number_of_second) {
    ifstream in(path);
    if (in.is_open())
    {
  
        in >> n;
        in >> m;

        int** x = new int* [n];
        for (int i = 0; i < n; i++) x[i] = new int[m];
        if (n > 1) {
            in >> number_of_first;
            in >> number_of_second;
            out << "the incidence matrix of the original graph\n";

            for (int i = 0; i < n; i++)
                for (int j = 0; j < m; j++)
                    in >> x[i][j];

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++)
                {
                    out << x[i][j] << " ";
                }
                out << "\n";
            }
        }
        else {
            out << "the incidence matrix of the original graph";
            char s;
            if (in.is_open()) {
                char ch;
                while (in.get(ch))
                {
                    out << ch;
                }
            }
            out << "\nit is impossible to make a closure because there is only one vertex in the graph\n";
        }
        in.close();
        return x;
    }
    else
    {
        cout << "file not found";
    }
}
void check(int** x, int& n, int& m, ofstream& out, int& number_of_first, int& number_of_second) {
    int i, j;
    if (n > 1) {
        for (j = 0; j < m; j++) {
            if (x[number_of_second - 1][j] == 1) {
                x[number_of_first - 1][j] = 1;
            }
        }
        for (i = number_of_second - 1; i < n - 1; i++) {
            x[i] = x[i + 1];
        }
        out << "the incidence matrix of the closure graph\n";
        for (i = 0; i < n - 1; i++) {
            for (j = 0; j < m; j++)
            {
                out << x[i][j] << " ";
            }
            out << "\n";
        }
        out << "closed vertex is " << number_of_first << "," << number_of_second << "\n\n";
    }
    for (i = 0; i < n; i++) { delete x[i]; }
    delete[]x;
}
int main() {
    int number_of_first = 0;
    int number_of_second = 0;
    int par_path = 1, m = 0, n = 0;
    ofstream out("Output.txt");
    for (par_path = 1; par_path <= 6; par_path++)
    {
        int** a;
        out << "TEST-" << par_path << "\n";
        string path = tests(par_path);
        a = file_read(n, m, path, out, number_of_first, number_of_second);
        check(a, n, m, out, number_of_first, number_of_second);
    }
    out.close();
    ifstream in("Output.txt");
    read(in);
    in.close();
    return 0;
}