package br.com.bse.cardapio;

import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class BdyCardapio {
	public ListView lvCardapio;
	public Spinner spCategoria;
	public Button btnFazerPedido;
	public Button btnCancelar;
	public TextView txtValorTotal;
	public int id_categoria;
	
	private double valorTotal = 0;
	private int id;
	private int uf;
    private int cidade;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
	public int getId_categoria() {
		return id_categoria;
	}
	public void setId_categoria(int id_categoria) {
		this.id_categoria = id_categoria;
	}
	public double getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}
}
