#include "Carta.h"

Carta::Carta(){

}
Carta::Carta(int velMax, int cavalo, int torque,int numCarta)
{
	setVelMax(velMax);
	setCavalos(cavalo);
	setTorque(torque);
	setNumCarta(numCarta);
}
Carta::~Carta()
{
}

void Carta::setVelMax(int velMax){
	this->velMax = velMax;

}
int Carta::getVelMax(){

	return this->velMax;
}
void Carta::setCavalos(int cavalo){
	this->cavalo = cavalo;

}
int Carta::getCavalos(){
	return this->cavalo;

}
void Carta::setTorque(int torque){
	this->torque = torque;

}
int Carta::getTorque(){

	return this->torque;
}
void Carta::setNumCarta(int numCarta){
	this->numCarta = numCarta;

}
int Carta::getNumCarta(){

	return this->numCarta;
}
