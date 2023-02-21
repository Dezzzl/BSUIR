#pragma once
#include <iostream>
using namespace std;
class queue
{
public:
	queue();
	~queue();
	void pop();
	void push(int data);
	void clear();
	bool empty();
private:
	class Node
	{
	public:
		Node* pNext;
		int data;
		Node(int data, Node* pNext = nullptr) {
			this->data = data;
			this->pNext = pNext;
		}
	};
	int Size;
	Node* begin;
	Node* end;
};
