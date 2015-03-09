#include <Windows.h>
#include "TelaJogo.h"

using namespace std;
using namespace System;
using namespace ProjectSiri2;

int WINAPI WinMain(HINSTANCE hInstance, HINSTANCE hPrevInstance, LPSTR lpCmdLine, int nShowCmd){
	Application::EnableVisualStyles();
	Application::Run(gcnew TelaJogo());

}
