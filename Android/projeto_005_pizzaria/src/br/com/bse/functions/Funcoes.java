package br.com.bse.functions;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import br.com.bse.tags.Tag;

import android.app.AlertDialog;
import android.content.Context;

public class Funcoes {
	public void getMensagemOk(String titulo, String msg, Context classe){
		AlertDialog.Builder message = new AlertDialog.Builder(classe);
		message.setTitle(titulo);
		message.setNeutralButton("OK", null);
		message.setMessage(msg);
		message.show();
	}
	
	public String formatMoney_Real(double valor) {
		DecimalFormat formatacao = new DecimalFormat("##,###,###,##0.00", new DecimalFormatSymbols (new Locale("pt", "BR")));  
		formatacao.setMinimumFractionDigits(2);   
		formatacao.setParseBigDecimal (true); 
		
		return "R$ " + formatacao.format(valor);
	}
	
	public boolean isValidString(String texto) {
		if(texto.equals(null) || texto.trim().equals("")) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean isValidInt(String valor) {
		try {
			Integer.parseInt(valor);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public String gerarCamposSql(String[] valores) {
		String campos = "";
		
		for(int i = 0; i < valores.length; i++){
			if(!campos.equals("")) {
				campos += ", ";
			}
			
			if(valores[i].equals(Tag.NULL)) {
				campos += Tag.NULL;
			} else {
				campos += "'" + valores[i] + "'";
			}
		}
		
		return "(" + campos + ")";
	}
	
	public String formatarNumero(int valor) {
		NumberFormat f = new DecimalFormat("000000"); 
		return f.format(valor);
	}
}
