package br.com.bse.pedido;

public class Pedido {
	private int id;
	private String nome;
	private String telefone;
	private double valorTotal;
	private int id_formaPag;
	private double troco;
	private String endereco;
	private int numEnd;
	private String bairro;
	private int cidade;
	private int uf;
	private int status;
	private String dataPed;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public double getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}
	public int getId_formaPag() {
		return id_formaPag;
	}
	public void setId_formaPag(int id_formaPag) {
		this.id_formaPag = id_formaPag;
	}
	public double getTroco() {
		return troco;
	}
	public void setTroco(double troco) {
		this.troco = troco;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public int getNumEnd() {
		return numEnd;
	}
	public void setNumEnd(int numEnd) {
		this.numEnd = numEnd;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public int getCidade() {
		return cidade;
	}
	public void setCidade(int cidade) {
		this.cidade = cidade;
	}
	public int getUf() {
		return uf;
	}
	public void setUf(int uf) {
		this.uf = uf;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getDataPed(){
		return dataPed;
	}
	public void setDataPed(String dataPed){
		this.dataPed = dataPed;
	}
	
}
