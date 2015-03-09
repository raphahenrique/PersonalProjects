#pragma once
#include "Carta.h"
class Node
{
public:
	Node();
	Node(Carta info, Node *next);
	~Node();

	Carta info;

	Node *next;


};

