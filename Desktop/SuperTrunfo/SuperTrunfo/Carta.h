#pragma once
class Carta
{
public:
	Carta();
	Carta(int velMax, int cavalo, int torque,int numCarta);
	~Carta();

	void setVelMax(int velMax);
	int getVelMax();

	void setCavalos(int cavalo);
	int getCavalos();

	void setTorque(int torque);
	int getTorque();

	void setNumCarta(int numCarta);
	int getNumCarta();

	//vel maxima , torque , cavalos/potencia, numCarta
private:
	int velMax;
	int cavalo;
	int torque;
	int numCarta;

};

