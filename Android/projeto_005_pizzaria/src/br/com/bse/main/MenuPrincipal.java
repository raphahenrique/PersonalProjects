package br.com.bse.main;
import br.com.bse.configuracoes.Configuracoes;
import br.com.bse.localizacao.Localizacao;
import br.com.bse.pedidosrecentes.PedidosRecentes;

import com.example.projeto_005_pizzaria.R;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MenuPrincipal extends Activity {
	private BdyMenuPrincipal bdy = new BdyMenuPrincipal();
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		boolean isTablet = getResources().getBoolean(R.bool.isTablet);
		
		if(isTablet) {
			setContentView(R.layout.menu_principal_tablet);
		} else {
			setContentView(R.layout.menu_principal);
		}
		
		iniciarObjetos();
		
		bdy.btnVerCardapio.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i = new Intent(getApplicationContext(), Localizacao.class);
				startActivity(i);
				
				finish();
			}
		});
		
		bdy.btnPedidosRecentes.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),PedidosRecentes.class);
				
				startActivity(i);
				
			}
		});
	
		bdy.btnConfig.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),Configuracoes.class);
				
				startActivity(i);
				
			}
		});
		
		
		
	}
	
	private void iniciarObjetos() {
		bdy.btnVerCardapio = (Button) findViewById(R.id.home_btnCardapio);
		bdy.btnPedidosRecentes = (Button) findViewById(R.id.home_btnPedidosRecentes);
		bdy.btnConfig = (Button) findViewById(R.id.home_btnConfig);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		
		return super.onCreateOptionsMenu(menu);
	}
}
