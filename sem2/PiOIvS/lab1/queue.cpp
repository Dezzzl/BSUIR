
#include"queue.h"
queue::Node::Node(int data, Node* pNext = nullptr) {
	this->data = data;
	this->pNext = pNext;
}
queue::queue()
{
	Size = 0;
	begin = nullptr;
	end = nullptr;
}
queue::~queue()
{
	clear();
}
void queue::pop()
{
	if (Size == 0)return;
	Node* temp = begin;
	begin = begin->pNext;
	delete temp;
	Size--;
}
void queue::pop(int &data)
{
	if (Size == 0)return;
	Node* temp = begin;
	begin = begin->pNext;
	data = temp->data;
	delete temp;
	Size--;
}
void queue::push(int data)
{
	if (begin == nullptr)
	{
		begin = new Node(data);
		end = begin;
	}
	else
	{
		Node* current = this->end;
		current->pNext = new Node(data);
		this->end = current->pNext;
	}
	Size++;
}
void queue::clear()
{
	while (Size)
	{
		pop();
	}
}
bool queue::empty() {
	return Size ? false : true;
}