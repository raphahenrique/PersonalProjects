package br.com.bse.pedidosrecentes;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import com.example.projeto_005_pizzaria.R;

public class PedidosRecentes extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.pedidos_recentes);
		
		
	
	
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	
	
}
