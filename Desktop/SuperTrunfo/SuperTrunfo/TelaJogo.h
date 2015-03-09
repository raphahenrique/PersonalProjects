#include "Fila.h"
#include <time.h>
#include <Windows.h>
#include <string>
#pragma once

namespace SuperTrunfo {

	using namespace System;
	using namespace System::ComponentModel;
	using namespace System::Collections;
	using namespace System::Windows::Forms;
	using namespace System::Data;
	using namespace System::Drawing;
	using namespace System::Resources;

	

	static Fila deck1;
	static Fila deck2;

	static int ultimoGanhador = 0;


	/// <summary>
	/// Summary for TelaJogo
	/// </summary>
	public ref class TelaJogo : public System::Windows::Forms::Form
	{
	public:

		System::ComponentModel::ComponentResourceManager^  res = (gcnew System::ComponentModel::ComponentResourceManager(TelaJogo::typeid));



		//Atributo --->  0-Velocidade Máxima  1-Cavalos/Potência  2-Torque   
		void Matcher(Fila &Deck1, Fila &Deck2, int Atributo, int &ultimoGanhador){

			Carta *Carta1, *Carta2;
			Carta1 = new Carta;
			Carta2 = new Carta;

			//carta 25 = super trunfo

			switch (Atributo)
			{
			case 0: //Velocidade Máxima
				if (Deck1.PPrimeiro->info.getNumCarta() == 25){
					if ((Deck2.PPrimeiro->info.getNumCarta() == 11) || (Deck2.PPrimeiro->info.getNumCarta() == 21) || (Deck2.PPrimeiro->info.getNumCarta() == 31) || (Deck2.PPrimeiro->info.getNumCarta() == 41)){
						Deck1.Retira(Carta1);
						Deck2.Retira(Carta2);
						Deck2.Insere(*Carta1);
						Deck2.Insere(*Carta2);
						ultimoGanhador = 2;

					}else{
						Deck1.Retira(Carta1);
						Deck2.Retira(Carta2);
						Deck1.Insere(*Carta1);
						Deck1.Insere(*Carta2);
						ultimoGanhador = 1;
					}
						
				}else if ((Deck1.PPrimeiro->info.getVelMax()) >= (Deck2.PPrimeiro->info.getVelMax())){ 
						Deck1.Retira(Carta1);
						Deck2.Retira(Carta2);
						Deck1.Insere(*Carta1);
						Deck1.Insere(*Carta2);
						ultimoGanhador = 1;
					}else{
						Deck1.Retira(Carta1);
						Deck2.Retira(Carta2);
						Deck2.Insere(*Carta1);
						Deck2.Insere(*Carta2);
						ultimoGanhador = 2;
					}
				
				break;
			case 1: //Cavalos/Potencia
				if (Deck1.PPrimeiro->info.getNumCarta() == 25){
					if ((Deck2.PPrimeiro->info.getNumCarta() == 11) || (Deck2.PPrimeiro->info.getNumCarta() == 21) || (Deck2.PPrimeiro->info.getNumCarta() == 31) || (Deck2.PPrimeiro->info.getNumCarta() == 41)){
						Deck1.Retira(Carta1);
						Deck2.Retira(Carta2);
						Deck2.Insere(*Carta1);
						Deck2.Insere(*Carta2);
						ultimoGanhador = 2;

					}
					else{
						Deck1.Retira(Carta1);
						Deck2.Retira(Carta2);
						Deck1.Insere(*Carta1);
						Deck1.Insere(*Carta2);
						ultimoGanhador = 1;
					}

				}else if((Deck1.PPrimeiro->info.getCavalos()) >= (Deck2.PPrimeiro->info.getCavalos())){
					Deck1.Retira(Carta1);
					Deck2.Retira(Carta2);
					Deck1.Insere(*Carta1);
					Deck1.Insere(*Carta2);
					ultimoGanhador = 1;

				}
				else{
					Deck1.Retira(Carta1);
					Deck2.Retira(Carta2);
					Deck2.Insere(*Carta1);
					Deck2.Insere(*Carta2);
					ultimoGanhador = 2;

				}

				break;
			case 2: // Torque
				if (Deck1.PPrimeiro->info.getNumCarta() == 25){
					if ((Deck2.PPrimeiro->info.getNumCarta() == 11) || (Deck2.PPrimeiro->info.getNumCarta() == 21) || (Deck2.PPrimeiro->info.getNumCarta() == 31) || (Deck2.PPrimeiro->info.getNumCarta() == 41)){
						Deck1.Retira(Carta1);
						Deck2.Retira(Carta2);
						Deck2.Insere(*Carta1);
						Deck2.Insere(*Carta2);
						ultimoGanhador = 2;

					}
					else{
						Deck1.Retira(Carta1);
						Deck2.Retira(Carta2);
						Deck1.Insere(*Carta1);
						Deck1.Insere(*Carta2);
						ultimoGanhador = 1;
					}

				}
				else if ((Deck1.PPrimeiro->info.getTorque()) >= (Deck2.PPrimeiro->info.getTorque())){
					Deck1.Retira(Carta1);
					Deck2.Retira(Carta2);
					Deck1.Insere(*Carta1);
					Deck1.Insere(*Carta2);
					ultimoGanhador = 1;

				}
				else{
					Deck1.Retira(Carta1);
					Deck2.Retira(Carta2);
					Deck2.Insere(*Carta1);
					Deck2.Insere(*Carta2);
					ultimoGanhador = 2;


				}

				break;
			default:
				break;
			}

		}

		//Escolhe o caminho da imagem
		void escolheImg(Fila Decks){

			switch (Decks.PPrimeiro->info.getNumCarta())
			{
			case 11:
				pictureBox2->Image = (cli::safe_cast<System::Drawing::Image^>(res->GetObject(L"11")));
				break;
			case 12:
				pictureBox2->Image = (cli::safe_cast<System::Drawing::Image^>(res->GetObject(L"12")));

				break;

			case 13:
				pictureBox2->Image = (cli::safe_cast<System::Drawing::Image^>(res->GetObject(L"13")));

				break;

			case 14:
				pictureBox2->Image = (cli::safe_cast<System::Drawing::Image^>(res->GetObject(L"14")));
				break;

			case 15:
				pictureBox2->Image = (cli::safe_cast<System::Drawing::Image^>(res->GetObject(L"15")));
				break;

			case 21:
				pictureBox2->Image = (cli::safe_cast<System::Drawing::Image^>(res->GetObject(L"21")));
				break;

			case 22:
				pictureBox2->Image = (cli::safe_cast<System::Drawing::Image^>(res->GetObject(L"22")));
				break;

			case 23:
				pictureBox2->Image = (cli::safe_cast<System::Drawing::Image^>(res->GetObject(L"23")));
				break;

			case 24:
				pictureBox2->Image = (cli::safe_cast<System::Drawing::Image^>(res->GetObject(L"24")));
				break;

			case 25:
				pictureBox2->Image = (cli::safe_cast<System::Drawing::Image^>(res->GetObject(L"25")));
				break;

			case 31:
				pictureBox2->Image = (cli::safe_cast<System::Drawing::Image^>(res->GetObject(L"31")));
				break;

			case 32:
				pictureBox2->Image = (cli::safe_cast<System::Drawing::Image^>(res->GetObject(L"32")));
				break;

			case 33:
				pictureBox2->Image = (cli::safe_cast<System::Drawing::Image^>(res->GetObject(L"33")));
				break;

			case 34:
				pictureBox2->Image = (cli::safe_cast<System::Drawing::Image^>(res->GetObject(L"34")));
				break;

			case 35:
				pictureBox2->Image = (cli::safe_cast<System::Drawing::Image^>(res->GetObject(L"35")));
				break;

			case 41:
				pictureBox2->Image = (cli::safe_cast<System::Drawing::Image^>(res->GetObject(L"41")));
				break;

			case 42:
				pictureBox2->Image = (cli::safe_cast<System::Drawing::Image^>(res->GetObject(L"42")));
				break;

			case 43:
				pictureBox2->Image = (cli::safe_cast<System::Drawing::Image^>(res->GetObject(L"43")));
				break;

			case 44:
				pictureBox2->Image = (cli::safe_cast<System::Drawing::Image^>(res->GetObject(L"44")));
				break;

			case 45:
				pictureBox2->Image = (cli::safe_cast<System::Drawing::Image^>(res->GetObject(L"45")));
				break;


			default:
				pictureBox2->Image = (cli::safe_cast<System::Drawing::Image^>(res->GetObject(L"PictureBox1.Image")));
				break;
			}



		}

		//Escolhe o caminho da imagem - picture box 1
		void escolheImg1(Fila Decks){

			switch (Decks.PPrimeiro->info.getNumCarta())
			{
			case 11:
				pictureBox1->Image = (cli::safe_cast<System::Drawing::Image^>(res->GetObject(L"11")));
				break;
			case 12:
				pictureBox1->Image = (cli::safe_cast<System::Drawing::Image^>(res->GetObject(L"12")));

				break;

			case 13:
				pictureBox1->Image = (cli::safe_cast<System::Drawing::Image^>(res->GetObject(L"13")));

				break;

			case 14:
				pictureBox1->Image = (cli::safe_cast<System::Drawing::Image^>(res->GetObject(L"14")));
				break;

			case 15:
				pictureBox1->Image = (cli::safe_cast<System::Drawing::Image^>(res->GetObject(L"15")));
				break;

			case 21:
				pictureBox1->Image = (cli::safe_cast<System::Drawing::Image^>(res->GetObject(L"21")));
				break;

			case 22:
				pictureBox1->Image = (cli::safe_cast<System::Drawing::Image^>(res->GetObject(L"22")));
				break;

			case 23:
				pictureBox1->Image = (cli::safe_cast<System::Drawing::Image^>(res->GetObject(L"23")));
				break;

			case 24:
				pictureBox1->Image = (cli::safe_cast<System::Drawing::Image^>(res->GetObject(L"24")));
				break;

			case 25:
				pictureBox1->Image = (cli::safe_cast<System::Drawing::Image^>(res->GetObject(L"25")));
				break;

			case 31:
				pictureBox1->Image = (cli::safe_cast<System::Drawing::Image^>(res->GetObject(L"31")));
				break;

			case 32:
				pictureBox1->Image = (cli::safe_cast<System::Drawing::Image^>(res->GetObject(L"32")));
				break;

			case 33:
				pictureBox1->Image = (cli::safe_cast<System::Drawing::Image^>(res->GetObject(L"33")));
				break;

			case 34:
				pictureBox1->Image = (cli::safe_cast<System::Drawing::Image^>(res->GetObject(L"34")));
				break;

			case 35:
				pictureBox1->Image = (cli::safe_cast<System::Drawing::Image^>(res->GetObject(L"35")));
				break;

			case 41:
				pictureBox1->Image = (cli::safe_cast<System::Drawing::Image^>(res->GetObject(L"41")));
				break;

			case 42:
				pictureBox1->Image = (cli::safe_cast<System::Drawing::Image^>(res->GetObject(L"42")));
				break;

			case 43:
				pictureBox1->Image = (cli::safe_cast<System::Drawing::Image^>(res->GetObject(L"43")));
				break;

			case 44:
				pictureBox1->Image = (cli::safe_cast<System::Drawing::Image^>(res->GetObject(L"44")));
				break;

			case 45:
				pictureBox1->Image = (cli::safe_cast<System::Drawing::Image^>(res->GetObject(L"45")));
				break;


			default:
				pictureBox1->Image = (cli::safe_cast<System::Drawing::Image^>(res->GetObject(L"PictureBox1.Image")));
				break;
			}



		}


		//Se for a vez da CPU, pega o maior atributo da CPU
		void pegaMaior(int &at){
			if (ultimoGanhador == 2){
				//ver o melhor e passar para at
				btnComparar->Enabled = false;

				int velMaxCPU = deck2.PPrimeiro->info.getVelMax();
				int cavalosCPU = deck2.PPrimeiro->info.getCavalos();
				int torqueCPU = deck2.PPrimeiro->info.getTorque();

				if ((velMaxCPU > cavalosCPU) && (velMaxCPU > torqueCPU)){
					at = 0;
				}
				else if ((cavalosCPU>velMaxCPU) && (cavalosCPU>torqueCPU)){
					at = 1;
				}
				else if ((torqueCPU>velMaxCPU) && (torqueCPU>cavalosCPU)){
					at = 2;
				}
				else{
					at = rand() % 3 ;
				}

			}
			else{
				btnComparar->Enabled = true;
				
			}
		
		}

		void shuffle(int valor[], int size) {
			
			srand(time(NULL));

			for (int i = 0; i < (size * 20); i++) {
				int randvalue1 = (rand() % size) + 0;
				
				int randvalue2 = (rand() % size) + 0;

				int temp = valor[randvalue1];
				valor[randvalue1] = valor[randvalue2];
				valor[randvalue2] = temp;
			}
		}



		TelaJogo(void)
		{
			InitializeComponent();

			
			//
			//TODO: Add the constructor code here
			//
		}

	protected:
		/// <summary>
		/// Clean up any resources being used.
		/// </summary>
		~TelaJogo()
		{
			if (components)
			{
				delete components;
			}
		}

	private: System::Windows::Forms::PictureBox^  pictureBox1;
	protected:
	private: System::Windows::Forms::PictureBox^  pictureBox2;
	private: System::Windows::Forms::Label^  label1;
	private: System::Windows::Forms::Label^  label2;
	private: System::Windows::Forms::Button^  btnComparar;



	private: System::Windows::Forms::RadioButton^  rdVelMax;
	private: System::Windows::Forms::RadioButton^  rdCavalo;
	private: System::Windows::Forms::RadioButton^  rdTorque;
	private: System::Windows::Forms::Label^  txtCartaD1;

	private: System::Windows::Forms::Label^  txt2;
private: System::Windows::Forms::PictureBox^  pictureBox3;
private: System::Windows::Forms::Button^  button1;
private: System::Windows::Forms::Label^  lblNumJoga;
private: System::Windows::Forms::Label^  lblNumCPU;
private: System::Windows::Forms::Button^  button2;


	private:

		/// <summary>
		/// Required designer variable.
		/// </summary>
		System::ComponentModel::Container ^components;

#pragma region Windows Form Designer generated code
		/// <summary>
		/// Required method for Designer support - do not modify
		/// the contents of this method with the code editor.
		/// </summary>
		void InitializeComponent(void)
		{
			System::ComponentModel::ComponentResourceManager^  resources = (gcnew System::ComponentModel::ComponentResourceManager(TelaJogo::typeid));
			this->pictureBox1 = (gcnew System::Windows::Forms::PictureBox());
			this->pictureBox2 = (gcnew System::Windows::Forms::PictureBox());
			this->label1 = (gcnew System::Windows::Forms::Label());
			this->label2 = (gcnew System::Windows::Forms::Label());
			this->btnComparar = (gcnew System::Windows::Forms::Button());
			this->rdVelMax = (gcnew System::Windows::Forms::RadioButton());
			this->rdCavalo = (gcnew System::Windows::Forms::RadioButton());
			this->rdTorque = (gcnew System::Windows::Forms::RadioButton());
			this->txtCartaD1 = (gcnew System::Windows::Forms::Label());
			this->txt2 = (gcnew System::Windows::Forms::Label());
			this->pictureBox3 = (gcnew System::Windows::Forms::PictureBox());
			this->button1 = (gcnew System::Windows::Forms::Button());
			this->lblNumJoga = (gcnew System::Windows::Forms::Label());
			this->lblNumCPU = (gcnew System::Windows::Forms::Label());
			this->button2 = (gcnew System::Windows::Forms::Button());
			(cli::safe_cast<System::ComponentModel::ISupportInitialize^>(this->pictureBox1))->BeginInit();
			(cli::safe_cast<System::ComponentModel::ISupportInitialize^>(this->pictureBox2))->BeginInit();
			(cli::safe_cast<System::ComponentModel::ISupportInitialize^>(this->pictureBox3))->BeginInit();
			this->SuspendLayout();
			// 
			// pictureBox1
			// 
			this->pictureBox1->BorderStyle = System::Windows::Forms::BorderStyle::FixedSingle;
			this->pictureBox1->Image = (cli::safe_cast<System::Drawing::Image^>(resources->GetObject(L"pictureBox1.Image")));
			this->pictureBox1->Location = System::Drawing::Point(711, 49);
			this->pictureBox1->Name = L"pictureBox1";
			this->pictureBox1->Size = System::Drawing::Size(333, 465);
			this->pictureBox1->TabIndex = 0;
			this->pictureBox1->TabStop = false;
			// 
			// pictureBox2
			// 
			this->pictureBox2->BorderStyle = System::Windows::Forms::BorderStyle::FixedSingle;
			this->pictureBox2->Location = System::Drawing::Point(114, 49);
			this->pictureBox2->Name = L"pictureBox2";
			this->pictureBox2->Size = System::Drawing::Size(333, 465);
			this->pictureBox2->TabIndex = 0;
			this->pictureBox2->TabStop = false;
			// 
			// label1
			// 
			this->label1->AutoSize = true;
			this->label1->Font = (gcnew System::Drawing::Font(L"Corbel", 15.75F, System::Drawing::FontStyle::Regular, System::Drawing::GraphicsUnit::Point,
				static_cast<System::Byte>(0)));
			this->label1->Location = System::Drawing::Point(150, 9);
			this->label1->Name = L"label1";
			this->label1->Size = System::Drawing::Size(103, 26);
			this->label1->TabIndex = 1;
			this->label1->Text = L"JOGADOR";
			// 
			// label2
			// 
			this->label2->AutoSize = true;
			this->label2->Font = (gcnew System::Drawing::Font(L"Corbel", 15.75F, System::Drawing::FontStyle::Regular, System::Drawing::GraphicsUnit::Point,
				static_cast<System::Byte>(0)));
			this->label2->Location = System::Drawing::Point(742, 9);
			this->label2->Name = L"label2";
			this->label2->Size = System::Drawing::Size(133, 26);
			this->label2->TabIndex = 1;
			this->label2->Text = L"ADVERSÁRIO";
			// 
			// btnComparar
			// 
			this->btnComparar->Font = (gcnew System::Drawing::Font(L"Microsoft Sans Serif", 11.25F, System::Drawing::FontStyle::Bold, System::Drawing::GraphicsUnit::Point,
				static_cast<System::Byte>(0)));
			this->btnComparar->Location = System::Drawing::Point(516, 539);
			this->btnComparar->Name = L"btnComparar";
			this->btnComparar->Size = System::Drawing::Size(119, 51);
			this->btnComparar->TabIndex = 3;
			this->btnComparar->Text = L"COMPARAR";
			this->btnComparar->UseVisualStyleBackColor = true;
			this->btnComparar->Click += gcnew System::EventHandler(this, &TelaJogo::btnComparar_Click);
			// 
			// rdVelMax
			// 
			this->rdVelMax->AutoSize = true;
			this->rdVelMax->Font = (gcnew System::Drawing::Font(L"Microsoft Sans Serif", 12, System::Drawing::FontStyle::Regular, System::Drawing::GraphicsUnit::Point,
				static_cast<System::Byte>(0)));
			this->rdVelMax->Location = System::Drawing::Point(89, 539);
			this->rdVelMax->Name = L"rdVelMax";
			this->rdVelMax->Size = System::Drawing::Size(164, 24);
			this->rdVelMax->TabIndex = 4;
			this->rdVelMax->TabStop = true;
			this->rdVelMax->Text = L"Velocidade Máxima";
			this->rdVelMax->UseVisualStyleBackColor = true;
			// 
			// rdCavalo
			// 
			this->rdCavalo->AutoSize = true;
			this->rdCavalo->Font = (gcnew System::Drawing::Font(L"Microsoft Sans Serif", 12, System::Drawing::FontStyle::Regular, System::Drawing::GraphicsUnit::Point,
				static_cast<System::Byte>(0)));
			this->rdCavalo->Location = System::Drawing::Point(285, 539);
			this->rdCavalo->Name = L"rdCavalo";
			this->rdCavalo->Size = System::Drawing::Size(89, 24);
			this->rdCavalo->TabIndex = 5;
			this->rdCavalo->TabStop = true;
			this->rdCavalo->Text = L"Potência";
			this->rdCavalo->UseVisualStyleBackColor = true;
			// 
			// rdTorque
			// 
			this->rdTorque->AutoSize = true;
			this->rdTorque->Font = (gcnew System::Drawing::Font(L"Microsoft Sans Serif", 12, System::Drawing::FontStyle::Regular, System::Drawing::GraphicsUnit::Point,
				static_cast<System::Byte>(0)));
			this->rdTorque->Location = System::Drawing::Point(406, 539);
			this->rdTorque->Name = L"rdTorque";
			this->rdTorque->Size = System::Drawing::Size(77, 24);
			this->rdTorque->TabIndex = 6;
			this->rdTorque->TabStop = true;
			this->rdTorque->Text = L"Torque";
			this->rdTorque->UseVisualStyleBackColor = true;
			// 
			// txtCartaD1
			// 
			this->txtCartaD1->AutoSize = true;
			this->txtCartaD1->Font = (gcnew System::Drawing::Font(L"Consolas", 18, System::Drawing::FontStyle::Regular, System::Drawing::GraphicsUnit::Point,
				static_cast<System::Byte>(0)));
			this->txtCartaD1->Location = System::Drawing::Point(475, 390);
			this->txtCartaD1->Name = L"txtCartaD1";
			this->txtCartaD1->Size = System::Drawing::Size(0, 28);
			this->txtCartaD1->TabIndex = 7;
			// 
			// txt2
			// 
			this->txt2->AutoSize = true;
			this->txt2->Location = System::Drawing::Point(800, 539);
			this->txt2->Name = L"txt2";
			this->txt2->Size = System::Drawing::Size(40, 13);
			this->txt2->TabIndex = 9;
			this->txt2->Text = L"";
			// 
			// pictureBox3
			// 
			this->pictureBox3->Dock = System::Windows::Forms::DockStyle::Fill;
			this->pictureBox3->Image = (cli::safe_cast<System::Drawing::Image^>(resources->GetObject(L"pictureBox3.Image")));
			this->pictureBox3->Location = System::Drawing::Point(0, 0);
			this->pictureBox3->Name = L"pictureBox3";
			this->pictureBox3->Size = System::Drawing::Size(1171, 602);
			this->pictureBox3->SizeMode = System::Windows::Forms::PictureBoxSizeMode::StretchImage;
			this->pictureBox3->TabIndex = 10;
			this->pictureBox3->TabStop = false;
			// 
			// button1
			// 
			this->button1->Font = (gcnew System::Drawing::Font(L"Microsoft Sans Serif", 27.75F, System::Drawing::FontStyle::Regular, System::Drawing::GraphicsUnit::Point,
				static_cast<System::Byte>(0)));
			this->button1->Location = System::Drawing::Point(98, 455);
			this->button1->Name = L"button1";
			this->button1->Size = System::Drawing::Size(197, 75);
			this->button1->TabIndex = 11;
			this->button1->Text = L"INICIAR";
			this->button1->UseVisualStyleBackColor = true;
			this->button1->Click += gcnew System::EventHandler(this, &TelaJogo::button1_Click);
			// 
			// lblNumJoga
			// 
			this->lblNumJoga->AutoSize = true;
			this->lblNumJoga->Font = (gcnew System::Drawing::Font(L"Corbel", 15.75F, System::Drawing::FontStyle::Regular, System::Drawing::GraphicsUnit::Point,
				static_cast<System::Byte>(0)));
			this->lblNumJoga->Location = System::Drawing::Point(326, 9);
			this->lblNumJoga->Name = L"lblNumJoga";
			this->lblNumJoga->Size = System::Drawing::Size(93, 26);
			this->lblNumJoga->TabIndex = 12;
			this->lblNumJoga->Text = L"Nº Cartas";
			// 
			// lblNumCPU
			// 
			this->lblNumCPU->AutoSize = true;
			this->lblNumCPU->Font = (gcnew System::Drawing::Font(L"Corbel", 15.75F, System::Drawing::FontStyle::Regular, System::Drawing::GraphicsUnit::Point,
				static_cast<System::Byte>(0)));
			this->lblNumCPU->Location = System::Drawing::Point(915, 9);
			this->lblNumCPU->Name = L"lblNumCPU";
			this->lblNumCPU->Size = System::Drawing::Size(93, 26);
			this->lblNumCPU->TabIndex = 13;
			this->lblNumCPU->Text = L"Nº Cartas";
			// 
			// button2
			// 
			this->button2->Font = (gcnew System::Drawing::Font(L"Microsoft Sans Serif", 14.25F, System::Drawing::FontStyle::Regular, System::Drawing::GraphicsUnit::Point,
				static_cast<System::Byte>(0)));
			this->button2->Location = System::Drawing::Point(503, 455);
			this->button2->Name = L"button2";
			this->button2->Size = System::Drawing::Size(146, 59);
			this->button2->TabIndex = 14;
			this->button2->Text = L"REINICIAR";
			this->button2->UseVisualStyleBackColor = true;
			this->button2->Click += gcnew System::EventHandler(this, &TelaJogo::button2_Click);
			// 
			// TelaJogo
			// 
			this->AutoScaleDimensions = System::Drawing::SizeF(6, 13);
			this->AutoScaleMode = System::Windows::Forms::AutoScaleMode::Font;
			this->BackColor = System::Drawing::SystemColors::ButtonHighlight;
			this->ClientSize = System::Drawing::Size(1171, 602);
			this->Controls->Add(this->button1);
			this->Controls->Add(this->pictureBox3);
			this->Controls->Add(this->button2);
			this->Controls->Add(this->lblNumCPU);
			this->Controls->Add(this->lblNumJoga);
			this->Controls->Add(this->txt2);
			this->Controls->Add(this->rdTorque);
			this->Controls->Add(this->rdCavalo);
			this->Controls->Add(this->rdVelMax);
			this->Controls->Add(this->btnComparar);
			this->Controls->Add(this->label2);
			this->Controls->Add(this->label1);
			this->Controls->Add(this->pictureBox2);
			this->Controls->Add(this->pictureBox1);
			this->Controls->Add(this->txtCartaD1);
			this->FormBorderStyle = System::Windows::Forms::FormBorderStyle::FixedSingle;
			this->Icon = (cli::safe_cast<System::Drawing::Icon^>(resources->GetObject(L"$this.Icon")));
			this->MaximizeBox = false;
			this->Name = L"TelaJogo";
			this->StartPosition = System::Windows::Forms::FormStartPosition::CenterScreen;
			this->Text = L"SUPER TRUNFO";
			this->Load += gcnew System::EventHandler(this, &TelaJogo::TelaJogo_Load);
			(cli::safe_cast<System::ComponentModel::ISupportInitialize^>(this->pictureBox1))->EndInit();
			(cli::safe_cast<System::ComponentModel::ISupportInitialize^>(this->pictureBox2))->EndInit();
			(cli::safe_cast<System::ComponentModel::ISupportInitialize^>(this->pictureBox3))->EndInit();
			this->ResumeLayout(false);
			this->PerformLayout();

		}

#pragma endregion
	private: System::Void TelaJogo_Load(System::Object^  sender, System::EventArgs^  e) {
				
				 Carta lista[20];

				 Carta a1(345, 740, 690,11);
				 Carta a2(370, 750, 720,12);
				 Carta a3(330, 647, 818,13);
				 Carta a4(283, 325, 370,14);
				 Carta a5(415, 1202, 1500, 15);
				 Carta b1(367, 963, 905,21);
				 Carta b2(355, 710, 689,22);
				 Carta b3(300, 477, 600,23);
				 Carta b4(317, 550, 540,24);
				 Carta b5(205, 103, 153,25); //SUPER TRUNFO
				 Carta c1(332, 577, 539,31);
				 Carta c2(347, 570, 598,32);
				 Carta c3(230, 210, 390,33);
				 Carta c4(285, 662, 856, 34);
				 Carta c5(290, 580, 754, 35);
				 Carta d1(343, 662, 657, 41);
				 Carta d2(357, 913, 980, 42);
				 Carta d3(314, 500, 460, 43);
				 Carta d4(430, 1200, 1570, 44);
				 Carta d5(377, 730, 1000, 45);
				 

				 lista[0] = a1;
				 lista[1] = a2;
				 lista[2] = a3;
				 lista[3] = a4;
				 lista[4] = a5;
				 lista[5] = b1;
				 lista[6] = b2;
				 lista[7] = b3;
				 lista[8] = b4;
				 lista[9] = b5;
				 lista[10] = c1;
				 lista[11] = c2;
				 lista[12] = c3;
				 lista[13] = c4;
				 lista[14] = c5;
				 lista[15] = d1;
				 lista[16] = d2;
				 lista[17] = d3;
				 lista[18] = d4;
				 lista[19] = d5;
				 
				 int x = 0, numero1, numero2;

			
				 
				 int *valor = new int[19];

				 for (int i = 0; i <= 19; i++) {
					 valor[i] =  i;
				 }

				 shuffle(valor, 19 + 1);

			
				 for (int i = 0; i <= 19; i++) {
					 if (deck1.ContaCarta() < 10){
						 deck1.Insere(lista[valor[i]]);
					 }else{
						 deck2.Insere(lista[valor[i]]);
					 }
				 }

				
				 
				 rdVelMax->Checked = true;

				 /*txt2->Text = "Vel Max.: "+
					 "\nCavalos: "+	 
					 "\nTorque: " ;*/
				 pictureBox1->Image = (cli::safe_cast<System::Drawing::Image^>(res->GetObject(L"carta")));
				 escolheImg(deck1);
				
				 lblNumJoga->Text = "Nº Cartas: " + deck1.ContaCarta();
				 lblNumCPU->Text = "Nº Cartas: " + deck2.ContaCarta();

	}

	private: System::Void btnComparar_Click(System::Object^  sender, System::EventArgs^  e){

				
				 int at;

				 if (!deck1.Vazia() && (!deck2.Vazia())){

					 if (rdVelMax->Checked == true){
						 at = 0;
					 }
					 else if (rdCavalo->Checked == true){
						 at = 1;
					 }else{
						 at = 2;//torque
					 }


					 //ultimoGanhador int: 1-usuario  2-cpu
					 Matcher(deck1, deck2, at, ultimoGanhador);
					 
					 if ((!deck1.Vazia()) && (!deck2.Vazia())){

						 if (ultimoGanhador == 2){
							/* txt2->Text = "Vel Max.: " + Convert::ToString(deck2.PPrimeiro->info.getVelMax()) +
								 "\nCavalos: " + Convert::ToString(deck2.PPrimeiro->info.getCavalos()) +
								 "\nTorque: " + Convert::ToString(deck2.PPrimeiro->info.getTorque());*/
							 escolheImg1(deck2);
						 }
						 escolheImg(deck1);
						 lblNumJoga->Text = "Nº Cartas: " + deck1.ContaCarta();
						 lblNumCPU->Text = "Nº Cartas: " + deck2.ContaCarta();

						 pegaMaior(at);


						 while (ultimoGanhador == 2){
							 
							 if ((!deck1.Vazia()) && (!deck2.Vazia())){
								 MessageBox::Show("CPU Pensando ...","CPU");
								 Sleep(1500);
							 }
							 else{
								 break;
							 }
							
							 if ((!deck1.Vazia()) && (!deck2.Vazia())){
								 /*txt2->Text = "Vel Max.: " + Convert::ToString(deck2.PPrimeiro->info.getVelMax()) +
									 "\nCavalos: " + Convert::ToString(deck2.PPrimeiro->info.getCavalos()) +
									 "\nTorque: " + Convert::ToString(deck2.PPrimeiro->info.getTorque());*/
								 escolheImg1(deck2);
								 escolheImg(deck1);
								 lblNumJoga->Text = "Nº Cartas: " + deck1.ContaCarta();
								 lblNumCPU->Text = "Nº Cartas: " + deck2.ContaCarta();
								 Matcher(deck1, deck2, at, ultimoGanhador);
								 pegaMaior(at);
								 if ((!deck1.Vazia()) && (!deck2.Vazia())){
									 /*txt2->Text = "Vel Max.: " + Convert::ToString(deck2.PPrimeiro->info.getVelMax()) +
										 "\nCavalos: " + Convert::ToString(deck2.PPrimeiro->info.getCavalos()) +
										 "\nTorque: " + Convert::ToString(deck2.PPrimeiro->info.getTorque());*/
									 escolheImg1(deck2);
									 escolheImg(deck1);
									 lblNumJoga->Text = "Nº Cartas: " + deck1.ContaCarta();
									 lblNumCPU->Text = "Nº Cartas: " + deck2.ContaCarta();
								 }
							 }else{
								 break;
							 }

						 }

						 pegaMaior(at);

						 if ((!deck1.Vazia()) && (!deck2.Vazia())){
							/* txt2->Text = "Vel Max.: " +
								 "\nCavalos: " +
								 "\nTorque: ";
*/
							 pictureBox1->Image = (cli::safe_cast<System::Drawing::Image^>(res->GetObject(L"carta")));
							 escolheImg(deck1);
							 lblNumJoga->Text = "Nº Cartas: " + deck1.ContaCarta();
							 lblNumCPU->Text = "Nº Cartas: " + deck2.ContaCarta();
						 }
						 else{
							 if (deck1.Vazia()){
								 txtCartaD1->Text = "DECK 2 GANHOU !!";
								 btnComparar->Enabled = false;
								 button2->Show();

							 }
							 else{
								 txtCartaD1->Text = "DECK 1 GANHOU !!";
								 btnComparar->Enabled = false;
								 button2->Show();
							 }
						 }

					 }
					 else{
						 if (deck1.Vazia()){
							 txtCartaD1->Text = "DECK 2 GANHOU !!";
							 btnComparar->Enabled = false;
							 button2->Show();
						 }
						 else{
							 txtCartaD1->Text = "DECK 1 GANHOU !!";
							 btnComparar->Enabled = false;
							 button2->Show();
						 }
					 }
				 } else{
					 if (deck1.Vazia()){
						 txtCartaD1->Text = "DECK 2 GANHOU !!";
						 btnComparar->Enabled = false;
						 button2->Show();
					 }
					 else{
						 txtCartaD1->Text = "DECK 1 GANHOU !!";
						 btnComparar->Enabled = false;
						 button2->Show();
					 }
				 }


	}
private: System::Void button1_Click(System::Object^  sender, System::EventArgs^  e) {
			 pictureBox3->Hide();
			 button2->Hide();
			 button1->Hide();

}
private: System::Void button2_Click(System::Object^  sender, System::EventArgs^  e) {
			 Application::Restart();
}
};
}
