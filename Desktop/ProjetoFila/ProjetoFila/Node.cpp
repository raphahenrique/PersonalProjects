#include "stdafx.h"
#include "Node.h"

Node::Node()
{


}
Node::Node(Carta x, Node *next)
{
	this->info = x;
	this->next = next;

}


Node::~Node()
{


}

