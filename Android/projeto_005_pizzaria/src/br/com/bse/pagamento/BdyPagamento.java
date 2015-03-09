package br.com.bse.pagamento;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class BdyPagamento {
	private int id_formaPag;
	public Spinner spFormaPag;
	public EditText txtTrocoPara;
	public TextView txtValorTotal;
	public TextView txtTroco;
	public Button btnFinalizarPedido;
	public Button btnCancelar;
	
	public int getId_formaPag() {
		return id_formaPag;
	}
	public void setId_formaPag(int id_formaPag) {
		this.id_formaPag = id_formaPag;
	}
}
