#include <iostream>
#include <fstream>
using namespace std;
void shifr(string &st) {

    ofstream fout;
    fout.open(st);
    char stroke[100];
    gets_s(stroke);
    int dlina = strlen(stroke);
    for (int i = 0; i < dlina; i++)
        if (stroke[i] == 'a' || stroke[i] == 'e' || stroke[i] == 'y' || stroke[i] == 'u' || stroke[i] == 'i' || stroke[i] == 'o')fout << stroke[i] << "la";
        else fout << stroke[i];
    fout.close();

}
void deshifr(string &st) {
    ifstream fin;
    fin.open(st);
    char ch;
    char newstroka[100];
    int dl = 0;
    while (fin.get(ch))
    {
        newstroka[dl] = ch;
        dl++;
    }
    newstroka[dl] = '\0';
    for (int i = 0; i < dl; i++)
    {
        if ((newstroka[i] == 'a' || newstroka[i] == 'e' || newstroka[i] == 'y' || newstroka[i] == 'u' || newstroka[i] == 'i' || newstroka[i] == 'o') && newstroka[i + 1] == 'l' && newstroka[i + 2] == 'a')cout << newstroka[i], i += 2;
        else cout << newstroka[i];
    }
    fin.close();
}
int main()
{
    string out = "output.txt";
    shifr(out);
    deshifr(out);
    return 0;
}
