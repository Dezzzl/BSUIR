#include<iostream>
using namespace std;
struct Stack {
    char info;
    Stack* next = NULL;
};
Stack* push(Stack* st, char inf) {
    Stack* t = new Stack;
    t->info = inf;
    t->next = st;
    return t;
}
Stack* pop(Stack* st, char* s) {
    Stack* temp = st;
    *s = st->info;
    st = st->next;
    delete temp;
    return st;
}
int Prior(char a) {
    switch (a) {
    case '*': 	case '/': 	return 3;
    case '-': 	case '+': 	return 2;
    case '(':   return 1;
    }
    return 0;
}
bool expressionCheck(const char* expression) {
    if (strlen(expression) == 1)return false;
    char bracketcheck;
    Stack* brackets = NULL;
    int i = 0;
    if (expression[i] == ')' || expression[i] == '*' || expression[i] == '+' || expression[i] == '-' || expression[i] == '/')return false;
    for (i = 0; i < strlen(expression) - 1; i++)
    {
        if (expression[i] == '(')
        {
            if (expression[i + 1] == '*' || expression[i + 1] == '+' || expression[i + 1] == '-' || expression[i + 1] == '/' || expression[i + 1] == ')') { return false; }
            else { brackets = push(brackets, expression[i]); continue; }
        }
        else  if (expression[i] == ')')
        {
            if (expression[i + 1] >= 'a' && expression[i + 1] <= 'z') {
                return false;
            }
            if (brackets == NULL)return false;
            else { brackets = pop(brackets, &bracketcheck); continue; }
        }
        else  if (expression[i] == '*' || expression[i] == '+' || expression[i] == '-' || expression[i] == '/') {
            if (expression[i + 1] == '*' || expression[i + 1] == '-' || expression[i + 1] == '+' || expression[i + 1] == '/' || expression[i + 1] == ')')return false;
            else continue;
        }
        else if (expression[i] >= 'a' && expression[i] <= 'z') {
            if ((expression[i + 1] >= 'a' && expression[i] <= 'z') || expression[i + 1] == '(')return false;
            else if (brackets == NULL && expression[i + 1] == ')')return false;
            else continue;
        }
        else return false;
    }
    if (expression[i] == '(' || expression[i] == '*' || expression[i] == '+' || expression[i] == '-' || expression[i] == '/')return false;
    if (expression[i] == ')')
    {
        if (brackets == NULL)return false;
        else
        {
            brackets = pop(brackets, &bracketcheck);
            if (brackets != NULL)return false;
            else return true;
        }
    }
    else return true;
}
void createOPZ(const char* expression, char* OPZ)
{
    Stack* forOPZ = NULL;
    Stack* t = NULL;
    int k = 0;
    char buff;
    for (int i = 0; i < strlen(expression); i++)

    {
        if (expression[i] >= 'a' && expression[i] <= 'z')OPZ[k++] = expression[i];
        else if (expression[i] == ')')
        {

            while ((forOPZ->info) != '(')
            {
                forOPZ = pop(forOPZ, &buff);
                if (!forOPZ) buff = '\0';
                OPZ[k++] = buff;
            }
            t = forOPZ;
            forOPZ = forOPZ->next;
            delete t;
        }
        else if (expression[i] == '(')forOPZ = push(forOPZ, expression[i]);
        else if (expression[i] == '+' || expression[i] == '*' || expression[i] == '-' || expression[i] == '/')
        {
            while (forOPZ != NULL && (Prior(forOPZ->info) >= Prior(expression[i])))
            {
                forOPZ = pop(forOPZ, &buff);
                OPZ[k++] = buff;
            }
            forOPZ = push(forOPZ, expression[i]);
        }
    }
    while (forOPZ != NULL)
    {
        forOPZ = pop(forOPZ, &buff);
        OPZ[k++] = buff;
    }
    OPZ[k] = '\0';
}
void createArr(float* arr, char* OPZ)
{
    bool letters[26];
    for (int i = 0; i < 26; i++)letters[i] = true;
    for (int i = 0; i < strlen(OPZ); i++)
    {
        if (OPZ[i] >= 'a' && OPZ[i] <= 'z') {

            if (letters[int(OPZ[i]) - 97] == true) {
                letters[int(OPZ[i]) - 97] = false;
                cout << "Введите " << OPZ[i] << ":";
                cin >> arr[int(OPZ[i])];
            }
        }
    }
}
float result(char* OPZ)
{
    char buff;
    float number1, number2;
    Stack* Letters = NULL;
    char middle = 'z' + 1;
    float* arr = new float[200];
    float result;
    createArr(arr, OPZ);
    for (int i = 0; i < strlen(OPZ); i++)
    {
        if (OPZ[i] >= 'a' && OPZ[i] <= 'z')Letters = push(Letters, OPZ[i]);
        else if (OPZ[i] == '*' || OPZ[i] == '+' || OPZ[i] == '/' || OPZ[i] == '-')
        {
            Letters = pop(Letters, &buff);
            number1 = arr[int(buff)];
            Letters = pop(Letters, &buff);
            number2 = arr[int(buff)];
            switch (OPZ[i])
            {
            case '+': result = number1 + number2; break;
            case '-': result = number2 - number1;   break;
            case '*': result = number1 * number2;  break;
            case '/': result = number2 / number1;   break;
            }
            arr[int(middle)] = result;
            Letters = push(Letters, middle);
            middle++;
        }

    }
    delete[]arr;
    return result;
}
int main() {
    setlocale(LC_ALL, "rus");
    char expression[100];
    char OPZ[100];
    while (1) {
        cout << "Введите выражение\n";
            gets_s(expression);
        bool check = expressionCheck(expression);
        if (check == false) { cout << "Выражение некорректно! Попробуйте ещё\n";  continue; }
        else { cout << "Выражение корректно!\n"; break; }
    }
    createOPZ(expression, OPZ);
    cout << "Полученная обратная польская запись: " << OPZ << endl;
    float res = result(OPZ);
    cout << "Результат вычисления: " << res;
}