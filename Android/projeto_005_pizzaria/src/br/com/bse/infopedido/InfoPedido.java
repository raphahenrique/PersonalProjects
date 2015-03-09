package br.com.bse.infopedido;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.projeto_005_pizzaria.R;
import br.com.bse.connection.JSONParser;
import br.com.bse.functions.Funcoes;
import br.com.bse.main.MenuPrincipal;
import br.com.bse.pedido.ListaPedido;
import br.com.bse.tags.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class InfoPedido extends Activity {
	private BdyInfoPedido bdy = new BdyInfoPedido();
	private Funcoes f = new Funcoes();
	private JSONParser jParser = new JSONParser();
	private List<ListaPedido> listaPedido;
	private boolean isTablet;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		isTablet = getResources().getBoolean(R.bool.isTablet);
		
		if(isTablet) {
			setContentView(R.layout.info_pedido_tablet);
		} else {
			setContentView(R.layout.info_pedido);
		}
		
		iniciarObjetos();
		setarValores();
		
		if (android.os.Build.VERSION.SDK_INT > 8) {
			// Permitindo a conexao com a internet para as threads
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
	    
	    ArrayAdapter<ListaPedido> itens = new CarregarLista(InfoPedido.this, listaPedido);
		bdy.lvItens.setAdapter(itens);
		
		bdy.btnVoltar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(InfoPedido.this, MenuPrincipal.class);
	            startActivity(intent);
	            
	            finish();
			}
		});
		
		bdy.btnCancelar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				AlertDialog.Builder message = new AlertDialog.Builder(InfoPedido.this);
				message.setTitle("Sair");
				message.setMessage("Deseja realmente cancelar o pedido?").setPositiveButton(Tag.SIM, cancelarPedido)
			    .setNegativeButton(Tag.NAO, cancelarPedido).show();
			}
		});
	}
	
	private void iniciarObjetos() {
		bdy.txtNumPed = (TextView) findViewById(R.id.infoped_txtNumPed);
		bdy.lvItens = (ListView) findViewById(R.id.infoped_lvItens);
		bdy.txtValorTotal = (TextView) findViewById(R.id.infoped_txtValorTotal);
		bdy.txtTroco = (TextView) findViewById(R.id.infoped_txtTroco);
		bdy.btnVoltar = (Button) findViewById(R.id.infoped_btnVoltarMenu);
		bdy.btnCancelar = (Button) findViewById(R.id.infoped_btnCancelar);
	}

	private void setarValores() {
		Intent i = getIntent();
		
		bdy.setId(i.getExtras().getInt("numPed"));
		bdy.txtNumPed.setText(getString(R.string.infoped_numpedido) + " " + f.formatarNumero(bdy.getId()));
	    bdy.txtValorTotal.setText(f.formatMoney_Real(i.getExtras().getDouble("valorTotal")));
	    bdy.txtTroco.setText(f.formatMoney_Real(i.getExtras().getDouble("troco")));
	    listaPedido = i.getParcelableArrayListExtra("listaPedido");
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	public class CarregarLista extends ArrayAdapter<ListaPedido> {
		private List<ListaPedido> list;
		private Activity context;

		public CarregarLista(Activity context, List<ListaPedido> list) {
			super(context, R.layout.lista_infoped, list);
			this.context = context;
			this.list = list;
		}

		class ViewHolder {
			protected TextView txtQuantidade;
			protected TextView lblDescricaoProd;
			protected TextView lblDescricaoItem;
			protected TextView lblPrecoFinal;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = null;
			
			if (convertView == null) {
				LayoutInflater inflator = context.getLayoutInflater();
				
				if(isTablet) {
					view = inflator.inflate(R.layout.lista_infoped_tablet, null);
				} else {
					view = inflator.inflate(R.layout.lista_infoped, null);
				}
				
				final ViewHolder viewHolder = new ViewHolder();
				
				view.setClickable(true);
				
				viewHolder.txtQuantidade = (TextView) view.findViewById(R.id.listaInfo_lblQuantidade);
				viewHolder.lblDescricaoProd = (TextView) view.findViewById(R.id.listaInfo_lblDescricaoProd);
				viewHolder.lblDescricaoItem = (TextView) view.findViewById(R.id.listaInfo_lblDescricaoItem);
				viewHolder.lblPrecoFinal = (TextView) view.findViewById(R.id.listaInfo_txtPrecoFinal);
				
				view.setTag(viewHolder);
			} else {
				view = convertView;
			}
			
			ViewHolder holder = (ViewHolder) view.getTag();
			
			double valorFinal = (list.get(position).getPreco_produto() + list.get(position).getPreco_ItemAdicional()) * list.get(position).getQuantidade();
			
			holder.txtQuantidade.setText(String.valueOf(list.get(position).getQuantidade()));
			holder.lblDescricaoProd.setText(list.get(position).getDescricao_produto() + " - " + f.formatMoney_Real(list.get(position).getPreco_produto()));
			holder.lblDescricaoItem.setText(list.get(position).getDescricao_ItemAdicional());
			
			
			holder.lblPrecoFinal.setText(f.formatMoney_Real(valorFinal));
			
			return view;
		}
	}
	
	class CancelarPedido extends AsyncTask<String, String, String> {
		private ProgressDialog pdCarregar = new ProgressDialog(InfoPedido.this);
		private JSONObject conexao;
		private int success;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pdCarregar = new ProgressDialog(InfoPedido.this);
			pdCarregar.setMessage("Cancelando...");
			pdCarregar.setIndeterminate(false);
			pdCarregar.setCancelable(false);
			pdCarregar.show();
		}

		protected String doInBackground(String... args) {
			try {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				
				params.add(new BasicNameValuePair(Tag.ID_PED, String.valueOf(bdy.getId())));
				params.add(new BasicNameValuePair(Tag.STATUS_PED, String.valueOf(0)));

				conexao = jParser.makeHttpRequest(Tag.URL_CANC_PED, Tag.GET, params);
				
				if(conexao != null) {
					success = conexao.getInt(Tag.SUCCESS);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}
		
		protected void onPostExecute(String file_url) {
			pdCarregar.dismiss();
			
			if(conexao != null) {
				if(success == 1) {
					dialogCancelamento();
				} 
				
				if(success == 2) {
					f.getMensagemOk("Pedido", "Pedido ja esta em andamento. Favor entre em contato pelo telefone para o cancelamento.", InfoPedido.this);
				}
			} else {
				AlertDialog.Builder alert = new AlertDialog.Builder(InfoPedido.this);
		        
				alert.setTitle("Pedido");
		        alert.setMessage("Erro de conexão com a internet. Deseja tentar novamente?");
		        
		        alert.setPositiveButton(Tag.SIM, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						new CancelarPedido().execute();
		            }
		        });
		        
		        alert.setNegativeButton(Tag.NAO, new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int whichButton) {
		            	Intent i = new Intent(getApplicationContext(), MenuPrincipal.class);
						startActivity(i);
						
						finish();
		            }
		        });
		        
		        alert.show();
			}
			
		}
	}
	
	DialogInterface.OnClickListener cancelarPedido = new DialogInterface.OnClickListener() {
	    @Override
	    public void onClick(DialogInterface dialog, int escolha) {
	        switch (escolha) {
	        case DialogInterface.BUTTON_POSITIVE:
	            new CancelarPedido().execute();
	            break;
	        case DialogInterface.BUTTON_NEGATIVE:
	            break;
	        }
	    }
	};
	
	private void dialogCancelamento() {
		AlertDialog.Builder alert = new AlertDialog.Builder(InfoPedido.this);
        
		alert.setTitle("Cancelamento");
        alert.setMessage("Pedido cancelado com sucesso.");
        
        alert.setNeutralButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent i = new Intent(getApplicationContext(), MenuPrincipal.class);
				startActivity(i);
				
				finish();
			}
		});
        
        alert.show();
	}
}
