// ProjetoFila.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include "Fila.h"
#include <iostream>
#include <stdlib.h>

using namespace std;

void Matcher(Fila &, Fila &,int Atributo);

int _tmain(int argc, _TCHAR* argv[])
{
	//Cria 2 filas
	Fila deck1;
	Fila deck2;

	Carta sptrunfo(320, 750, 44);
	Carta gol(200, 240, 21);
	Carta tiguan(230, 400, 40);
	Carta cartabosta(3, 4, 4);
	Carta carta20(20,20,20);
	Carta carta30(30, 30, 30);


	if (deck1.Vazia()){
		cout << "deu vazia ";
	}
	else{
		cout << "deu ruim"<<endl;
	}


	if (deck1.Insere(cartabosta)){
		deck1.Insere(cartabosta);
		deck1.Insere(cartabosta);
		deck1.Insere(cartabosta);
		deck1.Insere(cartabosta);
		cout << "deu 1";
		if (deck2.Insere(gol)){
			deck2.Insere(tiguan);
			deck2.Insere(tiguan);
			deck2.Insere(carta30);
			deck2.Insere(carta20);
			deck2.Insere(gol);
			cout << "deu 2 " << endl;
		}
		else{
			cout << "deu ruim";
		}
	}

	cout << "\n\nCOMPARANDO..." << endl;



	while ((!deck1.Vazia()) && (!deck2.Vazia())) // 1-Cavalos/Potencia
	{
		Matcher(deck1, deck2, 1);
	}

	if (deck1.Vazia()){
		cout << "\n\nDECK 2 GANHOU !!";
	}else{
		cout << "\n\nDECK 1 GANHOU !!";
	}



	getchar();
	return 0;

}


//Atributo --->  0-Velocidade Máxima  1-Cavalos/Potência  2-Torque    , Carta &Vencedor
void Matcher(Fila &Deck1, Fila &Deck2, int Atributo){

	Carta *Carta1,*Carta2;
	Carta1 = new Carta;
	Carta2 = new Carta;

	switch (Atributo)
	{
	case 0: //Velocidade Máxima
		if ((Deck1.PPrimeiro->info.getVelMax()) >= (Deck2.PPrimeiro->info.getVelMax())){ //arrumar o igual 
			Deck1.Retira(Carta1);
			Deck2.Retira(Carta2);
			Deck1.Insere(*Carta1);
			Deck1.Insere(*Carta2);

		}else{
			Deck1.Retira(Carta1);
			Deck2.Retira(Carta2);
			Deck2.Insere(*Carta1);
			Deck2.Insere(*Carta2);

		}

		break;
	case 1: //Cavalos/Potencia
		if ((Deck1.PPrimeiro->info.getCavalos()) >= (Deck2.PPrimeiro->info.getCavalos())){ //arrumar o igual 
			Deck1.Retira(Carta1);
			Deck2.Retira(Carta2);
			Deck1.Insere(*Carta1);
			Deck1.Insere(*Carta2);

		}
		else{
			Deck1.Retira(Carta1);
			Deck2.Retira(Carta2);
			Deck2.Insere(*Carta1);
			Deck2.Insere(*Carta2);

		}

		break;
	case 2: // Torque
		if ((Deck1.PPrimeiro->info.getTorque()) >= (Deck2.PPrimeiro->info.getTorque())){ //arrumar o igual 
			Deck1.Retira(Carta1);
			Deck2.Retira(Carta2);
			Deck1.Insere(*Carta1);
			Deck1.Insere(*Carta2);

		}
		else{
			Deck1.Retira(Carta1);
			Deck2.Retira(Carta2);
			Deck2.Insere(*Carta1);
			Deck2.Insere(*Carta2);

		}

		break;
	default:
		break;
	}



}