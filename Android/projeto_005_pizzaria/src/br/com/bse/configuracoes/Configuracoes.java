package br.com.bse.configuracoes;

import br.com.bse.functions.Funcoes;
import br.com.bse.main.MenuPrincipal;
import br.com.bse.tags.Tag;

import com.example.projeto_005_pizzaria.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Configuracoes extends Activity{
	private BdyConfiguracoes bdy = new BdyConfiguracoes();
	private Funcoes f = new Funcoes();
	public SharedPreferences preferencias;
	public SharedPreferences.Editor editPref;
	public Boolean salvarPref;
	
	
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
			setContentView(R.layout.configuracoes);
			
			iniciarObjetos();
			
			
			preferencias = getSharedPreferences("preferencias", MODE_PRIVATE);
			editPref = preferencias.edit();
			
			salvarPref = preferencias.getBoolean("salvarPref", false);
			if(salvarPref == true){
				bdy.txtNome.setText(preferencias.getString("nome", ""));
	            bdy.txtTelefone.setText(preferencias.getString("telefone", ""));
	            bdy.txtEndereco.setText(preferencias.getString("endereco", ""));
	            bdy.txtNumEnd.setText(preferencias.getString("numEnd", ""));
	            bdy.txtBairro.setText(preferencias.getString("bairro", ""));
	            
				
			}
			
			
			bdy.btnSalvar.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(verificarCampos() == Tag.PREENCHIDOS) {
						editPref.putBoolean("salvarPref", true);
		                editPref.putString("nome", bdy.txtNome.getText().toString());
		                editPref.putString("telefone", bdy.txtTelefone.getText().toString());
		                editPref.putString("endereco", bdy.txtEndereco.getText().toString());
		                editPref.putString("numEnd", bdy.txtNumEnd.getText().toString());
		                editPref.putString("bairro", bdy.txtBairro.getText().toString());
		                editPref.commit();
		                
	                
		                
		                Toast.makeText(getApplicationContext(), "Salvo com Sucesso", Toast.LENGTH_SHORT).show();
		                
					}
					
				}
			});
			
			
			bdy.btnVoltar.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent i = new Intent(getApplicationContext(),MenuPrincipal.class);
					
					startActivity(i);
					finish();
					
				}
			});
			
			
			
	}
	


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	
	private void iniciarObjetos() {
		bdy.txtNome = (EditText) findViewById(R.id.config_txtNome);
		bdy.txtTelefone = (EditText) findViewById(R.id.config_txtTelefone);
		bdy.txtEndereco = (EditText) findViewById(R.id.config_txtEndereco);
		bdy.txtNumEnd = (EditText) findViewById(R.id.config_txtNumEnd);
		bdy.txtBairro = (EditText) findViewById(R.id.config_txtBairro);
		bdy.btnSalvar = (Button) findViewById(R.id.config_btnSalvar);
		bdy.btnVoltar = (Button) findViewById(R.id.config_btnVoltar);
	}
	
	private boolean verificarCampos() {
		if(!f.isValidString(bdy.txtNome.getText().toString())) {
			f.getMensagemOk("Configurações", "Nome inválido.", Configuracoes.this);
			bdy.txtNome.requestFocus();
			
			return false;
		}
		
		if(!f.isValidString(bdy.txtTelefone.getText().toString())){
			f.getMensagemOk("Configurações", "Telefone Inválido", Configuracoes.this);
			bdy.txtTelefone.requestFocus();
			
			return false;
			
		}
		
		if(!f.isValidString(bdy.txtEndereco.getText().toString())) {
			f.getMensagemOk("Configurações", "Endereço inválido.", Configuracoes.this);
			bdy.txtEndereco.requestFocus();
			
			return false;
		}
		
		if(!f.isValidInt(bdy.txtNumEnd.getText().toString())) {
			f.getMensagemOk("Configurações", "Número de endereço inválido.", Configuracoes.this);
			bdy.txtNumEnd.requestFocus();
			
			return false;
		}
		
		if(!f.isValidString(bdy.txtBairro.getText().toString())) {
			f.getMensagemOk("Configurações", "Bairro inválido.", Configuracoes.this);
			bdy.txtBairro.requestFocus();
			
			return false;
		}
		
		return true;
	}
	
	
}





