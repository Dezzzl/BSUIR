#include"Set.h"
#include<iostream>
#include<vector>
#include<string>
using namespace std;
void test1() {
	cout << "Test-1\n";
	Set set1;
	set1.add("1");
	set1.add("2");
	set1.add("3");
	set1.add("4");
	set1.add("5");
	Set set2;
	set2.add("3");
	set2.add("4");
	set2.add("5");
	set2.add("6");
	set2.add("7");
	Set set3;
	set3.add("5");
	set3.add("6");
	set3.add("7");
	set3.add("8");
	set3.add("9");
	cout << "A = ";
	set1.printSet();
	cout << "B = ";
	set2.printSet();
	cout << "C = ";
	set3.printSet();
	vector <Set> sets = { set1, set2, set3 };
	Set res = set1.symmetricDifference(sets);
	cout << "Res = ";
	res.printSet();
	cout << endl;
}
void test2() {
	cout << "Test-2\n";
	Set set1;
	set1.add("1");
	set1.add("2");
	set1.add("3");
	set1.add("4");
	set1.add("5");
	Set set2;
	set2.add("3");
	set2.add("4");
	set2.add("5");
	set2.add("6");
	set2.add("7");
	cout << "A = ";
	set1.printSet();
	cout << "B = ";
	set2.printSet();
	Set res = set1.symmetricDifference(set2);
	cout << "Res = ";
	res.printSet();
	cout << endl;
}
void test3() {
	cout << "Test-3\n";
	Set set1;
	set1.add("1");
	set1.add("2");
	set1.add("3");
	set1.add("4");
	set1.add("5");
	cout << "A = ";
	set1.printSet();
	cout << "Res = ";
	Set res = set1.symmetricDifference(set1);
	res.printSet();
	cout << endl;
}
void test4() {
	cout << "Test-4\n";
	Set set1;
	set1.add("1");
	set1.add("2");
	set1.add("3");
	set1.add("4");
	set1.add("5");
	Set set2;
	set2.add("3");
	set2.add("4");
	set2.add("5");
	set2.add("6");
	set2.add("7");
	Set set3;
	set3.add("5");
	set3.add("6");
	set3.add("7");
	set3.add("8");
	set3.add("9");
	Set set4;
	set4.add("7");
	set4.add("8");
	set4.add("9");
	set4.add("10");
	set4.add("11");
	cout << "A = ";
	set1.printSet();
	cout << "B = ";
	set2.printSet();
	cout << "C = ";
	set3.printSet();
	cout << "D = ";
	set4.printSet();
	vector <Set> sets = { set1, set2, set3, set4 };
	Set res = set1.symmetricDifference(sets);
	cout << "Res = ";
	res.printSet();
	cout << endl;
}
void test5() {
	cout << "Test-5\n";
	Set set1;
	set1.add("1");
	set1.add("2");
	set1.add("3");
	set1.add("4");
	set1.add("5");
	Set set2;
	set2.add("1");
	set2.add("2");
	set2.add("3");
	set2.add("4");
	set2.add("5");
	cout << "A = ";
	set1.printSet();
	cout << "B = ";
	set2.printSet();
	cout << "Res = ";
	Set res = set1.symmetricDifference(set2);
	res.printSet();
	cout << endl;
}
void test6() {
	cout << "Test-6\n";
	Set set1;
	set1.add("A");
	set1.add("B");
	set1.add("C");
	set1.add("D");
	set1.add("E");
	Set set2;
	set2.add("C");
	set2.add("A");
	set2.add("D");
	set2.add("F");
	set2.add("G");
	cout << "A = ";
	set1.printSet();
	cout << "B = ";
	set2.printSet();
	cout << "Res = ";
	Set res = set1.symmetricDifference(set2);
	res.printSet();
	cout << endl;
}
void test7() {
	cout << "Test-7\n";
	Set set1;
	set1.add("111");
	set1.add("1");
	set1.add("3");
	set1.add("4");
	set1.add("5");
	Set set2;
	set2.add("10");
	set2.add("1");
	set2.add("C");
	set2.add("2");
	set2.add("E");
	Set set3;
	set3.add("12");
	set3.add("1");
	set3.add("A");
	set3.add("2");
	set3.add("C");
	Set set4;
	set4.add("B");
	set4.add("D");
	set4.add("C");
	set4.add("H");
	set4.add("2");
	cout << "A = ";
	set1.printSet();
	cout << "B = ";
	set2.printSet();
	cout << "C = ";
	set3.printSet();
	cout << "D = ";
	set4.printSet();
	vector <Set> sets = { set1, set2, set3, set4 };
	Set res = set1.symmetricDifference(sets);
	cout << "Res = ";
	res.printSet();
	cout << endl;
}
void test8() {
	cout << "Test-8\n";
	Set set1;
	Set set2;
	set2.add("C");
	set2.add("A");
	set2.add("D");
	set2.add("F");
	set2.add("G");
	cout << "A = ";
	set1.printSet();
	cout << "B = ";
	set2.printSet();
	cout << "Res = ";
	Set res = set1.symmetricDifference(set2);
	res.printSet();
	cout << endl;
}

void tests() {
	test1();
	test2();
	test3();
	test4();
	test5();
	test6();
	test7();
	test8();
}
int main() {
	tests();
}