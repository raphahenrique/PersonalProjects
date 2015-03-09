package br.com.bse.confirmacao;
import java.util.ArrayList;
import java.util.List;
import com.example.projeto_005_pizzaria.R;
import br.com.bse.cardapio.Cardapio;
import br.com.bse.dadosentrega.DadosEntrega;
import br.com.bse.functions.Funcoes;
import br.com.bse.main.MenuPrincipal;
import br.com.bse.pedido.ListaPedido;
import br.com.bse.tags.Tag;
import android.os.Bundle;
import android.os.Parcelable;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class ListaConfirmacao extends Activity {
	private List<ListaPedido> listaPedido;
	private Funcoes funcoes = new Funcoes();
	private BdyListaConfirmacao bdy = new BdyListaConfirmacao();
	private boolean isTablet;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		isTablet = getResources().getBoolean(R.bool.isTablet);
		
		if(isTablet) {
			setContentView(R.layout.confirmacao_itens_tablet);
		} else {
			setContentView(R.layout.confirmacao_itens);
		}
		
		iniciarObjetos();
		
		Intent i = getIntent();
		listaPedido = i.getParcelableArrayListExtra("listaPedido");
		
		bdy.setValorTotal(i.getExtras().getDouble("valorTotal"));
		bdy.setUf(i.getExtras().getInt("uf"));
		bdy.setCidade(i.getExtras().getInt("cidade"));
		
		
		ArrayAdapter<ListaPedido> itens = new CarregarLista(ListaConfirmacao.this, listaPedido);
		bdy.lv.setAdapter(itens);
		
		bdy.txtValorTotal.setText(funcoes.formatMoney_Real(bdy.getValorTotal()));
		
		bdy.btnAdicionar.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ListaConfirmacao.this, Cardapio.class);
			    
			    intent.putParcelableArrayListExtra("listaPedido", (ArrayList<? extends Parcelable>) listaPedido);
			    intent.putExtra("valorTotal", bdy.getValorTotal());
			    intent.putExtra("uf", bdy.getUf());
			    intent.putExtra("cidade", bdy.getCidade());
			    startActivity(intent);
	            
	            finish();
			}
		});
		
		bdy.btnConfirmar.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				if(listaPedido.isEmpty()) {
					funcoes.getMensagemOk("Pedido", "Selecione produtos para serem adicionados ao carrinho.", ListaConfirmacao.this);
				} else {
					Intent intent = new Intent(ListaConfirmacao.this, DadosEntrega.class);
					intent.putParcelableArrayListExtra("listaPedido", (ArrayList<? extends Parcelable>) listaPedido);
					intent.putExtra("uf", bdy.getUf());
				    intent.putExtra("cidade", bdy.getCidade());
				    intent.putExtra("valorTotal", bdy.getValorTotal());
				    startActivity(intent);
		            
		            finish();
				}
			}
		});
		
		bdy.btnCancelar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dialogCancelar();
			}
		});
	}
	
	private void iniciarObjetos() {
		bdy.lv = (ListView) findViewById(R.id.confirmacao_listItens);
		bdy.btnAdicionar = (Button) findViewById(R.id.confirmacao_btnAdicionar);
		bdy.txtValorTotal = (TextView) findViewById(R.id.confirmacao_txtValorTotal);
		bdy.btnConfirmar = (Button) findViewById(R.id.confirmacao_btnConfirmar);
		bdy.btnCancelar = (Button) findViewById(R.id.confirmacao_btnCancelar);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	DialogInterface.OnClickListener sairApp = new DialogInterface.OnClickListener() {
	    @Override
	    public void onClick(DialogInterface dialog, int escolha) {
	        switch (escolha) {
	        case DialogInterface.BUTTON_POSITIVE:
	            finish();
	            break;
	        case DialogInterface.BUTTON_NEGATIVE:
	            break;
	        }
	    }
	};
	
	public class CarregarLista extends ArrayAdapter<ListaPedido> {
		private List<ListaPedido> list;
		private Activity context;

		public CarregarLista(Activity context, List<ListaPedido> list) {
			super(context, R.layout.lista_confirmacao, list);
			this.context = context;
			this.list = list;
		}

		class ViewHolder {
			protected EditText txtQuantidade;
			protected TextView lblDescricaoProd;
			protected TextView lblDescricaoItem;
			protected TextView lblPrecoProd;
			protected Button btnExcluir;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = null;
			final int p = position;
			final byte isItemAdicional = list.get(position).isItemAdicional();
			
			LayoutInflater inflator = context.getLayoutInflater();
			
			if(isTablet) {
				view = inflator.inflate(R.layout.lista_confirmacao_tablet, null);
			} else {
				view = inflator.inflate(R.layout.lista_confirmacao, null);
			}
			
			final ViewHolder viewHolder = new ViewHolder();
			
			view.setClickable(true);
			
			viewHolder.txtQuantidade = (EditText) view.findViewById(R.id.listaConf_txtQuantidade);
			viewHolder.lblDescricaoProd = (TextView) view.findViewById(R.id.listaConf_lblDescricaoProd);
			viewHolder.lblPrecoProd = (TextView) view.findViewById(R.id.listaConf_lblPrecoProd);
			viewHolder.lblDescricaoItem = (TextView) view.findViewById(R.id.listaConf_lblDescricaoItem);
			viewHolder.btnExcluir = (Button) view.findViewById(R.id.listaConf_btnExcluir);
			
			viewHolder.btnExcluir.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					AlertDialog.Builder alert = new AlertDialog.Builder(ListaConfirmacao.this);
					alert.setTitle(list.get(p).getDescricao_produto());
			        alert.setMessage("Deseja remover o item?");

			        alert.setPositiveButton(Tag.SIM, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
							if(isItemAdicional == Tag.TRUE) {
								bdy.setValorTotal(bdy.getValorTotal() - (list.get(p).getQuantidade() * (list.get(p).getPreco_produto() + list.get(p).getPreco_ItemAdicional())));
							} else {
								bdy.setValorTotal(bdy.getValorTotal() - (list.get(p).getQuantidade() * list.get(p).getPreco_produto()));
							}
							
							bdy.txtValorTotal.setText(funcoes.formatMoney_Real(bdy.getValorTotal()));
							
							list.remove(p);
							bdy.lv.invalidateViews();
			            }
			        });
			        
			        alert.setNegativeButton(Tag.NAO, new DialogInterface.OnClickListener() {
			            public void onClick(DialogInterface dialog, int whichButton) {
			            	
			            }
			        });
			        
			        alert.show();
				}
			});
			
			viewHolder.txtQuantidade.addTextChangedListener(new TextWatcher() {
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
				}
				
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				}
				
				@Override
				public void afterTextChanged(Editable s) {
					if(!s.toString().equals("")) {
						int qtdAtual = Integer.valueOf(s.toString());
						
						if(qtdAtual > 0) {
							if(isItemAdicional == Tag.TRUE) {
								bdy.setValorTotal(bdy.getValorTotal() + (qtdAtual - list.get(p).getQuantidade()) * (list.get(p).getPreco_produto() + list.get(p).getPreco_ItemAdicional()));									
							} else {
								bdy.setValorTotal(bdy.getValorTotal() + (qtdAtual - list.get(p).getQuantidade()) * list.get(p).getPreco_produto());				
							}
							
							bdy.txtValorTotal.setText(funcoes.formatMoney_Real(bdy.getValorTotal()));
							list.get(p).setQuantidade(qtdAtual);
						}
					}
				}
			});
			
			view.setTag(viewHolder);
			
			ViewHolder holder = (ViewHolder) view.getTag();
			
			holder.txtQuantidade.setText(String.valueOf(list.get(position).getQuantidade()));
			holder.lblDescricaoProd.setText(list.get(position).getDescricao_produto() + " - " + funcoes.formatMoney_Real(list.get(position).getPreco_produto()));
			holder.lblPrecoProd.setText(funcoes.formatMoney_Real(list.get(position).getPreco_produto() + list.get(position).getPreco_ItemAdicional()));
			holder.lblDescricaoItem.setText(list.get(position).getDescricao_ItemAdicional());
			
			return view;
		}
	}
	
	private void dialogCancelar() {
		AlertDialog.Builder alert = new AlertDialog.Builder(ListaConfirmacao.this);
        
		alert.setTitle("Cancelar");
        alert.setMessage("Deseja cancelar o pedido?");
        
        alert.setPositiveButton(Tag.SIM, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				Intent intent = new Intent(ListaConfirmacao.this, MenuPrincipal.class);
	            startActivity(intent);
	            
	            finish();
            }
        });
        
        alert.setNegativeButton(Tag.NAO, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            	
            }
        });
        
        alert.show();
	}
}