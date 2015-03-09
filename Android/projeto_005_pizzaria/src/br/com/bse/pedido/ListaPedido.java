package br.com.bse.pedido;
import android.os.Parcel;
import android.os.Parcelable;

public class ListaPedido implements Parcelable {
	private int id_produto;
	private int id_itemAdicional; 
	private int quantidade; 
	private String descricao_produto; 
	private String descricao_ItemAdicional;
	private String detalhe_produto;
	private double preco_ItemAdicional;
	private double preco_produto; 
	private byte isItemAdicional;
	
	public ListaPedido(Parcel source) {
        id_produto = source.readInt();
        id_itemAdicional = source.readInt();
        quantidade = source.readInt();
        descricao_produto = source.readString();
        descricao_ItemAdicional = source.readString();
        preco_produto = source.readDouble();
        preco_ItemAdicional = source.readDouble();
        isItemAdicional = source.readByte();
    }
	
	public ListaPedido(int id_produto, int id_itemAdicional, int quantidade, String descricao_produto, String descricao_ItemAdicional, 
			double preco_produto, double preco_ItemAdicional, byte isItemAdicional) { 
        this.id_produto = id_produto;
        this.id_itemAdicional = id_itemAdicional;
        this.quantidade = quantidade;
        this.descricao_produto = descricao_produto;
        this.descricao_ItemAdicional = descricao_ItemAdicional;
        this.preco_produto = preco_produto;
        this.preco_ItemAdicional = preco_ItemAdicional;
        this.isItemAdicional = isItemAdicional;
    }
	
	public ListaPedido() {
		
	}
	public int getId_produto() {
		return id_produto;
	}
	public void setId_produto(int id_produto) {
		this.id_produto = id_produto;
	}
	public int getId_itemAdicional() {
		return id_itemAdicional;
	}
	public void setId_itemAdicional(int id_itemAdicional) {
		this.id_itemAdicional = id_itemAdicional;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	public String getDescricao_produto() {
		return descricao_produto;
	}
	public void setDescricao_produto(String descricao_produto) {
		this.descricao_produto = descricao_produto;
	}
	public String getDescricao_ItemAdicional() {
		return descricao_ItemAdicional;
	}
	public void setDescricao_ItemAdicional(String descricao_ItemAdicional) {
		this.descricao_ItemAdicional = descricao_ItemAdicional;
	}
	public double getPreco_ItemAdicional() {
		return preco_ItemAdicional;
	}
	public void setPreco_ItemAdicional(double preco_ItemAdicional) {
		this.preco_ItemAdicional = preco_ItemAdicional;
	}
	public double getPreco_produto() {
		return preco_produto;
	}
	public void setPreco_produto(double preco_produto) {
		this.preco_produto = preco_produto;
	}
	public byte isItemAdicional() {
		return isItemAdicional;
	}
	public void setItemAdicional(byte itemAdicional) {
		this.isItemAdicional = itemAdicional;
	}
	public String getDetalhe_produto() {
		return detalhe_produto;
	}

	public void setDetalhe_produto(String detalhe_produto) {
		this.detalhe_produto = detalhe_produto;
	}
	
	@Override
	public int describeContents() {
		return this.hashCode();
	}
	
	@Override
	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeInt(id_produto);
		dest.writeInt(id_itemAdicional);
		dest.writeInt(quantidade);
		dest.writeString(descricao_produto);
		dest.writeString(descricao_ItemAdicional);
		dest.writeDouble(preco_produto);
		dest.writeDouble(preco_ItemAdicional);
		dest.writeByte(isItemAdicional);
	}

	public static final Parcelable.Creator<ListaPedido> CREATOR = new Parcelable.Creator<ListaPedido>() {
        @Override
        public ListaPedido createFromParcel(Parcel source) {
            return new ListaPedido(source);
        }
 
        @Override
        public ListaPedido[] newArray(int size) {
            return new ListaPedido[size];
        }
 
    };
}
