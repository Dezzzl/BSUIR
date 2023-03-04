#pragma once
#include <iostream>
using namespace std;
class queue
{
public:
	queue();
	~queue();
	void pop();
	void pop(int &data);
	void push(int data);
	void clear();
	bool empty();
private:
	class Node
	{
	public:
		Node* pNext;
		int data;
		Node(int data, Node* pNext);
	};
	int Size;
	Node* begin;
	Node* end;
};
