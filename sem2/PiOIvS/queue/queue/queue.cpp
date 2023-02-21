
#include"queue.h"
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
	cout << temp->data << " ";
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