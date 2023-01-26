#include<iostream>
#include<string>
using namespace std;
struct Stack {
    int info;
    Stack* next;
};
Stack* push(Stack* st, int inf) {
    Stack* t = new Stack;
    t->info = inf;
    t->next = st;
    return t;
}
Stack* pop1(Stack* st) {
    if (st->next == nullptr) return nullptr;
    else {
        Stack* temp = st;
        st = st->next;
        delete temp;
        return st;
    }
}
Stack* pop2(Stack* st, int& inf) {
    if (st->next == nullptr) return nullptr;
    else {
        Stack* temp = st;
        inf = st->info;
        st = st->next;
        delete temp;
        return st;
    }
}

void peek(Stack* st) {
    while (st != nullptr) {
        cout << st->info << " ";
        st = st->next;
    }
}
void create(Stack* st, int inf) {
    if (st != nullptr) { cout << "Очистите память"; return; }
    else st->info = inf;
}
void del(Stack* st) {
    Stack* temp;
    while (st != nullptr) {
        temp = st;
        st = st->next;
        delete temp;
    }
}
void Sort_ptr(Stack** p) {
    Stack* t = NULL, * t1, * r;
    if ((*p)->next->next == NULL) return;
    do {
        for (t1 = *p; t1->next->next != t; t1 = t1->next)
            if (t1->next->info > t1->next->next->info) {
                r = t1->next->next;
                t1->next->next = r->next;
                r->next = t1->next;
                t1->next = r;
            }
        t = t1->next;
    } while ((*p)->next->next != t);
}
void Sort_info(Stack* p) {
    Stack* t = NULL, * t1;
    int r;
    do {
        for (t1 = p; t1->next != t; t1 = t1->next)
            if (t1->info > t1->next->info) {
                r = t1->info;
                t1->info = t1->next->info;
                t1->next->info = r;
            }
        t = t1;
    } while (p->next != t);
}
void change(Stack* st) {
    Stack* buff = st;
    while (buff != NULL) {
        buff = st->next;
        st->next->next = st;
    }
}
int find_max(Stack* st) {
    int max = st->info;
    while (st != NULL) {
        if (st->info > max)max = st->info;
        st = st->next;
    }
    return max;
}
void task(Stack* st) {
    int max = find_max(st);
    Stack* task = nullptr;
    int counter = 0;
    while (1) {
        task = push(task, st->info);
        if (st->info == max)break;
        st = st->next;
        counter++;
    }
}
int main() {
    setlocale(LC_ALL, "russian");
    Stack* st = nullptr;
    string command;
    int info;
    while (command != "quit") {
        cout << "> ";
        getline(cin, command);
        if (command == "push") {
            cout << "Введите информацию для ввода: ";
            cin >> info; cin.ignore();
            st = push(st, info);
        }
        else if (command == "pop1") {
            st = pop1(st);
        }
        else if (command == "peek") {
            peek(st);
            cout << endl;
        }
        else if (command == "clear") {
            del(st);
        }
        else if (command == "aswap") {
            Sort_ptr(&st);
        }
        else if (command == "iswap") {
            Sort_info(st);
        }
        else if (command == "task") {
            task(st);
        }
    }
    return 0;
}

//todo: поменять вершину