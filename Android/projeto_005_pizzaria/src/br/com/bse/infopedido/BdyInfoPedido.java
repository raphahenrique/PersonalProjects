package br.com.bse.infopedido;

import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class BdyInfoPedido {
	private int id;
	public TextView txtNumPed;
	public ListView lvItens;
	public TextView txtValorTotal;
	public TextView txtTroco;
	public Button btnVoltar;
	public Button btnCancelar;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
