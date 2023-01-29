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
    if (st == nullptr) return nullptr;
    else {
        Stack* temp = st;
        st = st->next;
        delete temp;
        return st;
    }
}
Stack* pop2(Stack* st, int& inf) {
    if (st == nullptr) return nullptr;
    else {
        Stack* temp = st;
        inf = st->info;
        st = st->next;
        delete temp;
        return st;
    }
}

void Info(Stack* st) {
    if (st == NULL)return;
    while (st != nullptr) {
        cout << st->info << " ";
        st = st->next;
    }
    cout << endl;
}
void del(Stack** st) {
    Stack* t;
    while (*st != NULL) {
        t = *st;
        *st = (*st)->next;
        delete t;
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
void change_top(Stack* st) {
    Stack* begin = st;
    Stack* temp = st;
    Stack* buff = NULL;
    int Temp;
    while (temp->next != NULL) {
        int info = temp->info;
        while (1)
        {
            st = st->next;
            if (st->next == buff)
            {
                Temp = st->info;
                st->info = temp->info;
                temp->info = Temp;
                buff = st;
                break;
            }
        }
        temp = temp->next;
        if (st == temp || temp->next == st)break;
        st = begin;
    }
    st = begin;
}
int find_max(Stack* st) {
    if (st != NULL) {
        int max = st->info;
        while (st != NULL) {
            if (st->info > max)max = st->info;
            st = st->next;
        }
        return max;
    }
    else return NULL;
}
Stack* task(Stack* st) {
    int max = find_max(st);
    Stack* task = nullptr;
    while (1) {
        task = push(task, st->info);
        if (st->info == max)break;
        st = st->next;
    }
    change_top(task);
    Info(task);
    return task;

}
int main() {
    setlocale(LC_ALL, "russian");
    Stack* st = nullptr;
    Stack* Task = nullptr;
    Stack* buff = st;
    string command;
    bool peek = true;
    bool main = true;
    int info;
    while (1) {
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
        else if (command == "pop2") {
            int info;
            st = pop2(st, info);
            cout << info << "\n";
        }
        else if (command == "info") {
            Info(st);
        }
        else if (command == "clear") {
            del(&st);
        }
        else if (command == "aswap") {
            Sort_ptr(&st);
        }
        else if (command == "iswap") {
            Sort_info(st);
        }
        else if (command == "task") {
            if (!(st == Task) || main == false) {
                if (Task != NULL)
                {
                    del(&Task); Task = NULL;   Task = task(st);
                }
                else Task = task(st);
            }
            else cout << "please,choose main stack!\n";
        }
        else if (command == "main") {
            if (peek)cout << "you are working with the main stack.\n";
            else {
                st = buff;
                peek = true;
                main = false;
            }
        }
        else if (command == "stack from task") {
            if (peek) {
                buff = st;
                st = Task;
                peek = false;
                main = true;
            }
            else cout << "please,choose main stack!\n";
        }
        else if (command == "exit") {
            del(&Task);
            del(&buff);
            break;
        }
    }
    return 0;
}

