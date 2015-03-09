#include "stdafx.h"
#include "Fila.h"
#include "Node.h"

Fila::Fila()
{
	this->PPrimeiro = NULL;
	this->PUltimo = NULL;

}


Fila::~Fila()
{



}

bool Fila::Insere(Carta x){

	Node *NoNovo;
	
	NoNovo = new Node;
	
	NoNovo->info = x;

	if (Vazia()){
		PPrimeiro = NoNovo;
		PUltimo = NoNovo;
		NoNovo->next = NoNovo;
		
		return true;
	}
	else if(PPrimeiro->next==PPrimeiro) {
		PPrimeiro->next = NoNovo;
		NoNovo->next = PPrimeiro;
		PUltimo = NoNovo;

		return true;
	}
	else{
		PUltimo->next = NoNovo;
		NoNovo->next = PPrimeiro;
		PUltimo = NoNovo;

		return true;
	}

	return false;

}

bool Fila::Vazia(){
	if (PPrimeiro == NULL)
		return true;
	else
		return false;

}

bool Fila::Retira(Carta *X){

	Node *PAux;

	if (!Vazia()){//Um elemento
		if (PPrimeiro->next == PPrimeiro){
			PAux = PPrimeiro;
			*X = PAux->info;
			PPrimeiro = NULL;
			PUltimo = NULL;
			DeleteNode(PAux);

			return true;
		}else{
			PAux = PPrimeiro;
			*X = PAux->info;
			PPrimeiro = PPrimeiro->next;
			PUltimo->next = PPrimeiro;
			DeleteNode(PAux);

			return true;
		}

	}
	else{

		return false;
	}
	
	return false;
}

void Fila::DeleteNode(Node *PAux){

	delete PAux;
}



