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
	temp->right=temp->left = NULL;
	return temp;
}
Tree* push(Tree *root, int key, string name) {
	Tree* prev; Tree* temp;
	temp = root;
	bool find = true;
	while (find && temp) {
		prev = temp;
		if (key == temp->ID) {
			find = false;
			cout << "Ключ не уникален";
		}
		else if(key > temp->ID)temp = temp->right;
		else temp = temp->left;
	}
	if (find) {
		temp = create(key, name);
		if (key > prev->ID)prev->right = temp;
		else prev->left = temp;
	}
}
void printInfo(Tree*root) {
	if (root) {
		printInfo(root-> right);
		cout << root->ID << " " << root->Name << endl;
		printInfo(root->left);
}
}
void printInfoReverse(Tree* root) {
	if (root) {
		printInfoReverse(root->left);
		cout << root->ID << " " << root->Name << endl;
		printInfoReverse(root->right);
	}
}
void printByKey(Tree* root, int key) {
	Tree* temp = root;
	while (key != temp->ID && temp) {
		if (key > temp->ID)temp = temp->right;
		else if (key < temp->ID)temp = temp->left;
		else cout << "ID: " << temp->ID << " ФИО: " << temp->Name << endl;

	}
	if (!temp)cout << "Информации не обнаружено";
}
Tree* delNode(Tree* root, int key) {
	Tree* left=NULL, * right=NULL, *del, *R, *prevDel, *prev_R;
	del= root;
	prevDel = nullptr;
	while (del!=NULL&&del->ID!=key) {
		prevDel = del;
		if (del->ID > key)del = del->left;
		else del = del->right;
	}
	if (!del) { cout << "Информации не обнаружено"; return root; }
	if (del->right == NULL)R = del->left;
	else if (del->left == NULL)R = del->right;
	else {
		prev_R = del;
		R = del->left;
		while (R->right != NULL) {
			prev_R = R;
			R = R->right;
		}
	}
	if (prev_R == del)R->right = del->right;
	else {
		R->right = del->right;
		prev_R->right = R->left;
		R->left = prev_R;
	}
	if (del == root)root = R;
	else if (del->ID > prev_R->ID)
		prev_R->right = R;
	else prev_R->left = R;
	delete del;
	return root;
}
void Del(Tree *root) {
	if (root != NULL) {
		Del(root->left);
		Del(root->right);
		delete root;
	}
}
void treeToVector(vector<person>ps, Tree*root) {
	if (root) {
		treeToVector(ps,root->left);
		person Person;
		Person.ID = root->ID;
		Person.Name = root->Name;
		ps.push_back(Person);
		treeToVector(ps,root->right);
	}
}
void makeBalanced(vector<person>ps, Tree** root, int first, int last) {
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
		std::cout << str << root->ID << std::endl;
		printTree(root->left, level + 1);
	}
}
void task(Tree *root, int &count) {
	if (root) {
		task(root->left, count);
		count += root->Name.length();
		task(root->right, count);
}
}
int main() {
	
}
