package br.com.bse.pedido;

public class ItemPedido {
	private int id;
	private String descricao;
	private String detalhes;
	private int categoria;
	private String imagem;
	private double preco;
	private boolean selected;
	private boolean noItens = false;
	
	/*public ItensPedido(String descricao) {
	    this.descricao = descricao;
	    selected = false;
	  }*/

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getDetalhes() {
		return detalhes;
	}
	public void setDetalhes(String detalhes) {
		this.detalhes = detalhes;
	}
	public int getCategoria() {
		return categoria;
	}
	public String getImagem() {
		return imagem;
	}
	public void setImagem(String imagem) {
		this.imagem = imagem;
	}
	public void setCategoria(int categoria) {
		this.categoria = categoria;
	}
	public double getPreco() {
		return preco;
	}
	public void setPreco(double preco) {
		this.preco = preco;
	}
	public boolean isSelected() {
	    return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public boolean isNoItens() {
		return noItens;
	}
	public void setNoItens(boolean noItens) {
		this.noItens = noItens;
	}
	
}
