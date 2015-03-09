#pragma once
#include "Carta.h"
#include "Node.h"
class Fila
{
public:
	Fila();
	~Fila();

	Node *PPrimeiro;
	Node *PUltimo;
	
	void DeleteNode(Node *PAux);

	bool Insere(Carta x);
	bool Vazia();
	bool Retira(Carta *x);


};

