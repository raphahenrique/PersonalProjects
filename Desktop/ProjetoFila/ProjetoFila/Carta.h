#pragma once
class Carta
{
public:
	Carta();
	Carta(int velMax,int cavalo,int torque);
	~Carta();

	void setVelMax(int velMax);
	int getVelMax();

	void setCavalos(int cavalo);
	int getCavalos();

	void setTorque(int torque);
	int getTorque();

	//vel maxima , torque , cavalos/potencia
private:
	int velMax;
	int cavalo;
	int torque;


};

