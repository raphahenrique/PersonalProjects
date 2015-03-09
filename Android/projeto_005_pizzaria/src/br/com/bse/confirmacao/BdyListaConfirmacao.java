package br.com.bse.confirmacao;

import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class BdyListaConfirmacao {
	public ListView lv;
	public TextView txtValorTotal;
	public Button btnAdicionar;
	public Button btnConfirmar;
	public Button btnCancelar;
	
	private int uf;
	private int cidade;
	private double valorTotal;
	
	public int getUf() {
		return uf;
	}
	public void setUf(int uf) {
		this.uf = uf;
	}
	public int getCidade() {
		return cidade;
	}
	public void setCidade(int cidade) {
		this.cidade = cidade;
	}
	public double getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}
}
