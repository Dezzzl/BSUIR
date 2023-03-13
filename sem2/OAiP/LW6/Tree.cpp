#include<iostream>
#include <string>
#include<vector>
using namespace std;
struct Tree {
	int ID;
	string Name;
	Tree* left, * right;
};
struct person {
	int ID;
	string Name;
};
Tree* create(int inf, string name) {
	Tree* temp = new Tree;
	temp->Name = name;
	temp->ID = inf;
	temp->right = temp->left = NULL;
	return temp;
}
Tree* push(Tree* root, int key, string name) {
	if (root == NULL) {
		root = create(key, name);
		return root;
	}
	else {
		Tree* prev = NULL; Tree* temp;
		temp = root;
		bool find = true;
		while (find && temp) {
			prev = temp;
			if (key == temp->ID) {
				find = false;
				cout << "Ключ не уникален\n";
			}
			else if (key > temp->ID)temp = temp->right;
			else temp = temp->left;
		}
		if (find) {
			temp = create(key, name);
			if (key > prev->ID)prev->right = temp;
			else prev->left = temp;
		}
		return root;
	}
}
void printInfo(Tree* root) {
	if (root) {
		printInfo(root->right);
		cout << "ID: " << root->ID << " ФИО: " << root->Name << endl;
		printInfo(root->left);
	}
}
void printInfoReverse(Tree* root) {
	if (root) {
		printInfoReverse(root->left);
		cout << "ID: " << root->ID << " ФИО: " << root->Name << endl;
		printInfoReverse(root->right);
	}
}
void printByKey(Tree* root, int key) {
	Tree* temp = root;
	while (key != temp->ID && temp) {
		if (key > temp->ID)temp = temp->right;
		else if (key < temp->ID)temp = temp->left;
	}
	if (!temp)cout << "Информации не обнаружено\n";
	else  cout << "ID: " << temp->ID << " ФИО: " << temp->Name << endl;
}
Tree* delNode(Tree* root, int key) {
	Tree* left = NULL, * del, * R, * prevDel = NULL, * prev_R = NULL;
	del = root;
	prevDel = nullptr;
	while (del != NULL && del->ID != key) {
		prevDel = del;
		if (del->ID > key)del = del->left;
		else del = del->right;
	}
	if (!del) { cout << "Информации не обнаружено\n"; return root; }
	if (del->right == NULL)R = del->left;
	else if (del->left == NULL)R = del->right;
	else {
		prev_R = del;
		R = del->left;
		while (R->right != NULL) {
			prev_R = R;
			R = R->right;
		}

		if (prev_R == del)R->right = del->right;
		else {
			R->right = del->right;
			prev_R->right = R->left;
			R->left = prev_R;
		}
	}
	if (del == root)root = R;
	else if (del->ID > prevDel->ID)
		prevDel->right = R;
	else prevDel->left = R;
	delete del;
	return root;
}
void Del(Tree* root) {
	if (root != NULL) {
		Del(root->left);
		Del(root->right);
		delete root;
	}
}
void treeToVector(vector<person>& ps, Tree* root) {
	if (root) {
		treeToVector(ps, root->left);
		person Person;
		Person.ID = root->ID;
		Person.Name = root->Name;
		ps.push_back(Person);
		treeToVector(ps, root->right);
	}
}
void makeBalanced(vector<person>& ps, Tree** root, int first, int last) {
	if (last == first) {
		*root = nullptr;
		return;
	}
	else {
		int middle = (first + last) / 2;
		*root = new Tree;
		(*root)->ID = ps[middle].ID;
		(*root)->Name = ps[middle].Name;
		makeBalanced(ps, &(*root)->left, first, middle);
		makeBalanced(ps, &(*root)->right, middle + 1, last);
	}
}
void printTree(Tree* root, int level) {
	string str;
	if (root) {
		printTree(root->right, level + 1);
		for (int i = 0; i < level; i++)
			str += "  ";
		cout << "ID: " << root->ID << " ФИО: " << root->Name << endl;
		printTree(root->left, level + 1);
	}
}
void task(Tree* root, int& count) {
	if (root) {
		task(root->left, count);
		count += root->Name.length();
		task(root->right, count);
	}
}
void menu() {
	cout << "Меню:" << endl << "1-Добaвить элемент" << endl << "2-Вывести информацию в порядке убывания ключа" << endl << "3--Вывести информацию в порядке возрастания ключа" << endl <<
		"4-Вывести информацию по ключу" << endl << "5-Удалить элемент" << endl << "6-Удалить дерево" << endl << "7-Сбалансировать дерево" << endl << "8-Индивидуальне задание" << endl << "9-Меню" << endl <<
		"0-Выход" << endl;
}
int main() {
	setlocale(LC_ALL, "rus");
	Tree* root = NULL;
	Tree* root1 = NULL;
	vector<person>ps;
	int choice;
	bool exit = true;
	menu();
	while (exit) {
		cin >> choice;
		switch (choice) {
		case 1: {
			int ID;
			string name;
			cout << "Введите имя\n";
			cin >> name;
			cout << "Введите ID\n";
			cin >> ID;
			root = push(root, ID, name);
			break;
		}
		case 2: {printInfo(root); break; }
		case 3: {printInfoReverse(root); break; }
		case 4: {cout << "Введите ID\n";
			int ID;
			cin >> ID;
			printByKey(root, ID);
			break;
		}
		case 5: {cout << "Введите ID\n";
			int ID;
			cin >> ID;
			delNode(root, ID);
			break;
		}
		case 6: {Del(root); root = NULL; break; }
		case 7: {treeToVector(ps, root);
			makeBalanced(ps, &root1, 0, ps.size());
			Del(root);
			root = root1;
			ps.clear();
			break;
		}
		case 8: {
			int count = 0;
			task(root, count);
			cout << "Количество букв: " << count << endl;
			break;
		}
		case 9: {menu(); break; }
		case 0: {exit = false; break; }
		}
	}
	return 0;
}
