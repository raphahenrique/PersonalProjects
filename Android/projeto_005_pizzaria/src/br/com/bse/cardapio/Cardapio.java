package br.com.bse.cardapio;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import br.com.bse.confirmacao.ListaConfirmacao;
import br.com.bse.connection.JSONParser;
import br.com.bse.functions.Funcoes;
import br.com.bse.main.MenuPrincipal;
import br.com.bse.pedido.ItemPedido;
import br.com.bse.pedido.ListaPedido;
import br.com.bse.tags.Tag;
import com.example.projeto_005_pizzaria.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class Cardapio extends Activity {
	private JSONParser jParser = new JSONParser();
	private Funcoes funcoes = new Funcoes();
	private BdyCardapio bdy = new BdyCardapio();
	
	private JSONArray jsonArray_categoria = null;
	private JSONArray produtos = null;
	private JSONArray jsonArray_itens_adicionais = null;
	
	private String[] categorias;
	private String[] itens_adicionais;
	private boolean isTablet;
	
	private ArrayList<ItemPedido> itensPedido;
	private ArrayList<ListaPedido> listaPedido;
	
	private ListaPedido lista;
    private ProgressDialog pDialog;
    
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		isTablet = getResources().getBoolean(R.bool.isTablet);
		
		if(isTablet) {
			setContentView(R.layout.cardapio_tablet);
		} else {
			setContentView(R.layout.cardapio);
		}
		
		iniciarObjetos();
		
		Intent i = getIntent();
		listaPedido = i.getParcelableArrayListExtra("listaPedido");
		
		if(listaPedido == null) {
			listaPedido = new ArrayList<ListaPedido>();
		} else {
			bdy.setValorTotal(i.getExtras().getDouble("valorTotal"));
			bdy.txtValorTotal.setText(funcoes.formatMoney_Real(bdy.getValorTotal()));
			//valorTotal = i.getExtras().getDouble("valorTotal");
			//bdy.txtValorTotal.setText(funcoes.formatMoney_Real(valorTotal));
		}
		
		bdy.setUf(i.getExtras().getInt("uf"));
		bdy.setCidade(i.getExtras().getInt("cidade"));
		
		if (android.os.Build.VERSION.SDK_INT > 8) {
			// Permitindo a conexao com a internet para as threads
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		
		new CarregarCategoria().execute();
		
		bdy.btnFazerPedido.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(listaPedido.isEmpty()) {
					funcoes.getMensagemOk("Pedido", "Selecione produtos para serem adicionados ao carrinho.", Cardapio.this);
				} else {
					Intent intent = new Intent(Cardapio.this, ListaConfirmacao.class);
				    
				    intent.putParcelableArrayListExtra("listaPedido", listaPedido);
				    //intent.putExtra("valorTotal", valorTotal);
				    intent.putExtra("valorTotal", bdy.getValorTotal());
				    intent.putExtra("uf", bdy.getUf());
				    intent.putExtra("cidade", bdy.getCidade());
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
		bdy.btnFazerPedido = (Button) findViewById(R.id.cardapio_btnFazerPedido);
		bdy.btnCancelar = (Button) findViewById(R.id.cardapio_btnCancelar);
		bdy.txtValorTotal = (TextView) findViewById(R.id.cardapio_txtValorTotal);
		bdy.spCategoria = (Spinner) findViewById(R.id.cardapio_spCategoria);
		bdy.lvCardapio = (ListView) findViewById(R.id.cardapio_listCardapio);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	class CarregarCategoria extends AsyncTask<String, String, Void> {
		private JSONObject conexao;
		private ProgressDialog pdCarregar = new ProgressDialog(Cardapio.this);
		private HashMap<Integer, Integer> mapCategorias = new HashMap<Integer, Integer>(); //<linha, id_categoria>

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pdCarregar = new ProgressDialog(Cardapio.this);
			pdCarregar.setMessage("Carregando...");
			pdCarregar.setIndeterminate(false);
			pdCarregar.setCancelable(false);
			pdCarregar.show();
		}

		@Override
		protected Void doInBackground(String... params) {
			try {
				List<NameValuePair> args = new ArrayList<NameValuePair>();
				conexao = jParser.makeHttpRequest(Tag.URL_CATEGORIAS, Tag.GET, args);
				
				if(conexao != null) {
					int success = conexao.getInt(Tag.SUCCESS);

					if (success == 1) {
						jsonArray_categoria = conexao.getJSONArray(Tag.CATEGORIA_PRODUTO);
						categorias = new String[jsonArray_categoria.length()];
						
						for (int i = 0; i <= (jsonArray_categoria.length() - 1); i++) {
							JSONObject json_obj = jsonArray_categoria.getJSONObject(i);

							mapCategorias.put(i, json_obj.getInt(Tag.ID_CATEGORIA));
							categorias[i] = json_obj.getString(Tag.DESCRICAO_CATEGORIA);
							
						}
						
						pdCarregar.dismiss();
					}
				}
			} catch (JSONException e) {
				pdCarregar.dismiss();
				e.printStackTrace();
			}
			
			return null;
		}

		protected void onPostExecute(Void v) {
			pdCarregar.dismiss();
			
			//Aproveitando o uso da Thread para atualizar o spinner
			runOnUiThread(new Runnable() {
				public void run() {
					if(conexao != null) {
						ArrayAdapter<String> cardapio_adapter;
						
						if(isTablet) {
							cardapio_adapter = new ArrayAdapter<String>(getApplicationContext(), 
										R.layout.spinner_row_tablet, categorias);
						} else {
							cardapio_adapter = new ArrayAdapter<String>(getApplicationContext(), 
										R.layout.spinner_row, categorias);
						}
						
						cardapio_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						bdy.spCategoria.setAdapter(cardapio_adapter);
						
						bdy.spCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
				            @Override
				            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
				            	//id_categoria = mapCategorias.get(position);
				            	bdy.setId_categoria(mapCategorias.get(position));
				            	new ListaProdutos().execute();
				            }

							@Override
							public void onNothingSelected(AdapterView<?> arg0) {
							}
				        });
					} else {
						AlertDialog.Builder alert = new AlertDialog.Builder(Cardapio.this);
				        
						alert.setTitle("Localizaçãoo");
				        alert.setMessage("Erro de conexão com a internet. Deseja tentar novamente?");
				        
				        alert.setPositiveButton(Tag.SIM, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								new CarregarCategoria().execute();
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
			});
		}
	}
	
	class ListaProdutos extends AsyncTask<String, String, String> {
		private JSONObject conexao;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Cardapio.this);
			pDialog.setMessage("Carregando produtos...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}
		
		protected String doInBackground(String... args) {
			try {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair(Tag.CATEGORIA_PRODUTO, String.valueOf(bdy.getId_categoria())));
				conexao = jParser.makeHttpRequest(Tag.URL_PRODUTOS_CATEGORIA, Tag.GET, params);
				
				if(conexao != null) {
					int existeProdutos = conexao.getInt(Tag.SUCCESS);
					ItemPedido item;
					
					if (existeProdutos == 1) {
						produtos = conexao.getJSONArray(Tag.PRODUTO);
				    	itensPedido = new ArrayList<ItemPedido>();

						for (int i = 0; i < produtos.length(); i++) {
							JSONObject json_obj = produtos.getJSONObject(i);
							
							item = new ItemPedido();
							item.setId(json_obj.getInt(Tag.ID_PRODUTO));
							item.setDescricao(json_obj.getString(Tag.DESCRICAO_PRODUTO));
							item.setDetalhes(json_obj.getString(Tag.DETALHES_PRODUTO));
							item.setPreco(json_obj.getDouble(Tag.PRECO_PRODUTO));
							item.setImagem(json_obj.getString(Tag.IMAGEM_PRODUTO));
							
							itensPedido.add(item);
						}
					} else {
						itensPedido = new ArrayList<ItemPedido>();
						item = new ItemPedido();
						
						item.setNoItens(true);
						item.setDescricao("Não há itens");
						itensPedido.add(item);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			pDialog.dismiss();

			return null;
		}

		
		protected void onPostExecute(String file_url) {
			pDialog.dismiss();
			
			runOnUiThread(new Runnable() {
				public void run() {
					if(conexao != null) {
						ArrayAdapter<ItemPedido> itens = new CarregarLista(Cardapio.this, itensPedido);
						bdy.lvCardapio.setAdapter(itens);
					} else {
						AlertDialog.Builder alert = new AlertDialog.Builder(Cardapio.this);
				        
						alert.setTitle("Cardápio");
				        alert.setMessage("Erro de conexão com a internet. Deseja tentar novamente?");
				        
				        alert.setPositiveButton(Tag.SIM, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								new ListaProdutos().execute();
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
			});
		}
	}
	
	public class CarregarLista extends ArrayAdapter<ItemPedido> {
		private List<ItemPedido> list;
		private Activity context;

		public CarregarLista(Activity context, List<ItemPedido> list) {
			super(context, R.layout.lista_cardapio, list);
			this.context = context;
			this.list = list;
		}

		class ViewHolder {
			protected TextView txtValorTotal;
			protected TextView txtDescricao;
			protected TextView txtDetalhes;
			protected TextView txtNoItems;
			protected Button btnAdicionar;
			protected TextView txtPreco;
			protected ImageView img;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = null;
			final int p = position;
			final boolean noItens = list.get(position).isNoItens();
			
			if (convertView == null) {
				LayoutInflater inflator = context.getLayoutInflater();
				if(isTablet) {
					view = inflator.inflate(R.layout.lista_cardapio_tablet, null);
				} else {
					view = inflator.inflate(R.layout.lista_cardapio, null);
				}
				final ViewHolder viewHolder = new ViewHolder();
				
				view.setClickable(true);
				
				viewHolder.txtDescricao = (TextView) view.findViewById(R.id.listaCardapio_descricao);
				viewHolder.txtDetalhes = (TextView) view.findViewById(R.id.listaCardapio_detalhes);
				viewHolder.btnAdicionar = (Button) view.findViewById(R.id.listaCardapio_btnAdicionar);
				viewHolder.txtPreco = (TextView) view.findViewById(R.id.listaCardapio_lblPreco);
				viewHolder.img = (ImageView) view.findViewById(R.id.listaCardapio_imgCardapio);
				viewHolder.txtNoItems = (TextView) view.findViewById(R.id.listaCardapio_noItems);
				
				final int id = list.get(position).getId();
				bdy.setId(list.get(position).getId());
				
				if(!noItens) {
					/*viewHolder.btnDetalhes.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							funcoes.getMensagemOk(titulo, detalhes, getContext());
						}
					});*/
					
					viewHolder.btnAdicionar.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							lista = new ListaPedido();
							
							lista.setId_produto(id);
							lista.setDescricao_produto(list.get(p).getDescricao());
							lista.setPreco_produto(list.get(p).getPreco());
							
							new CarregarItemAdicional(id).execute();
						}
					});
				}
				
				view.setTag(viewHolder);
			} else {
				view = convertView;
			}
			
			ViewHolder holder = (ViewHolder) view.getTag();
			
			if(noItens) {
				holder.txtNoItems.setVisibility(View.VISIBLE);
				holder.txtDescricao.setVisibility(View.GONE);
				holder.txtDetalhes.setVisibility(View.GONE);
				holder.txtPreco.setVisibility(View.GONE);
				holder.btnAdicionar.setVisibility(View.GONE);
				
				holder.txtNoItems.setTextSize(16);
				holder.txtNoItems.setText(list.get(position).getDescricao());
			} else {
				holder.txtNoItems.setVisibility(View.GONE);
				
				holder.txtDescricao.setText(list.get(position).getDescricao());
				holder.txtDetalhes.setText(list.get(position).getDetalhes());
				holder.txtPreco.setText(funcoes.formatMoney_Real(list.get(position).getPreco()));
				
				String urlImagem = list.get(position).getImagem();
				URL url = null;
				
				try {
					url = new URL(urlImagem);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				
				Bitmap bmp = null; 
				
				try {
					bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
				} catch (IOException e) {
					e.printStackTrace();
				}
						    
				holder.img.setImageBitmap(bmp);
			}
			
			return view;
		}
	}
	
	class CarregarItemAdicional extends AsyncTask<String, String, String> {
		private JSONObject conexao;
		private ProgressDialog pdCarregar = new ProgressDialog(Cardapio.this);
		private HashMap<Integer, Integer> mapItensAdicionais = new HashMap<Integer, Integer>(); //<linha, id_uf>
		private HashMap<Integer, Double> mapPreco = new HashMap<Integer, Double>(); //<linha, id_uf>
		private int id_produto;
		private int success;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pdCarregar = new ProgressDialog(Cardapio.this);
			pdCarregar.setMessage("Carregando...");
			pdCarregar.setIndeterminate(false);
			pdCarregar.setCancelable(true);
			pdCarregar.show();
		}
		
		public CarregarItemAdicional(int id_prod) {
			id_produto = id_prod;
		}
		
		@Override
		protected String doInBackground(String... params) {
			runOnUiThread(new Runnable() {
				public void run() {
					try {
						jParser = new JSONParser();
						List<NameValuePair> params = new ArrayList<NameValuePair>();
						params.add(new BasicNameValuePair(Tag.ID_PRODUTO, String.valueOf(id_produto)));
						conexao = jParser.makeHttpRequest(Tag.URL_ITENS_ADICIONAIS, Tag.GET, params);
						
						if(conexao != null) {
							success = conexao.getInt(Tag.SUCCESS);
							
							if (success == 1) {
								jsonArray_itens_adicionais = conexao.getJSONArray(Tag.ITEM_ADICIONAL);
								itens_adicionais = new String[jsonArray_itens_adicionais.length() + 1];

								itens_adicionais[0] = "Nenhum - R$ 0,00";
								mapPreco.put(0, 0.00);
								mapItensAdicionais.put(0, 0);
								
								for (int i = 0; i <= (jsonArray_itens_adicionais.length() - 1); i++) {
									JSONObject json_obj = jsonArray_itens_adicionais.getJSONObject(i);
									
									mapItensAdicionais.put(i + 1, json_obj.getInt(Tag.ID_ITEM_ADICIONAL));
									mapPreco.put(i + 1, json_obj.getDouble(Tag.PRECO_ITEM_ADICIONAL));
									
									itens_adicionais[i + 1] = json_obj.getString(Tag.DESCRICAO_ITEM_ADICIONAL) +
											" - " + funcoes.formatMoney_Real(json_obj.getDouble(Tag.PRECO_ITEM_ADICIONAL));
								}
							} 
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});
            
			return null;
		}
		
		protected void onPostExecute(String file_url) {
			pdCarregar.dismiss();
			
			runOnUiThread(new Runnable() {
				public void run() {
					if(conexao != null) {
						if(success == 1) {
							AlertDialog.Builder builder = new AlertDialog.Builder(Cardapio.this);
				            
					    	builder.setTitle("Itens adicionais");
					    	builder.setSingleChoiceItems(itens_adicionais, 0, null);
					    	
				            builder.setSingleChoiceItems(itens_adicionais, -1, new DialogInterface.OnClickListener() {
				            	public void onClick(DialogInterface dialog, int item) {
				            		if(mapItensAdicionais.get(item) != 0) {
				            			lista.setId_itemAdicional(mapItensAdicionais.get(item));
					            		lista.setDescricao_ItemAdicional("* " + itens_adicionais[item]);
					            		lista.setPreco_ItemAdicional(mapPreco.get(item));
					             		lista.setItemAdicional(Tag.TRUE);
				            		} else {
				            			lista.setItemAdicional(Tag.FALSE);
				            		}
				                }
				              });
				            
				            builder.setPositiveButton("Confirmar", dialog_itens_adicionais)
						    .setNegativeButton("Cancelar", dialog_itens_adicionais).show();
						} else {
							lista.setItemAdicional(Tag.FALSE);
							dialogQuantidade(false);
						}
					} else {
						AlertDialog.Builder alert = new AlertDialog.Builder(Cardapio.this);
				        
						alert.setTitle("CardÃ¡pio");
				        alert.setMessage("Erro de conexÃ£o com a internet. Deseja tentar novamente?");
				        
				        alert.setPositiveButton(Tag.SIM, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								new CarregarItemAdicional(id_produto).execute();
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
			});
		}
		
		DialogInterface.OnClickListener dialog_itens_adicionais = new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int escolha) {
		        switch (escolha) {
		        case DialogInterface.BUTTON_POSITIVE:
		        	dialogQuantidade(true);
		        	
		            break;
		        case DialogInterface.BUTTON_NEGATIVE:
		            break;
		        }
		    }
		};
	}
	
	private void dialogQuantidade(final boolean isItensAdicionais) {
		AlertDialog.Builder alert = new AlertDialog.Builder(Cardapio.this);
        final EditText input = new EditText(Cardapio.this);
        
        alert.setMessage("Quantidade:");
        
        input.setInputType(Tag.TIPO_NUMERO);
        input.setText("1");
        
        alert.setView(input);
        
        alert.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
            	lista.setQuantidade(Integer.parseInt(input.getText().toString()));
            	
            	listaPedido.add(lista);
			    
            	/*if(isItensAdicionais) {
            		valorTotal += (lista.getPreco_produto() + lista.getPreco_ItemAdicional()) * lista.getQuantidade();
            	} else {
            		valorTotal += lista.getPreco_produto() * lista.getQuantidade();
            	}*/
            	
            	//bdy.txtValorTotal.setText(funcoes.formatMoney_Real(valorTotal));
            	
            	if(isItensAdicionais) {
            		bdy.setValorTotal(bdy.getValorTotal() + (lista.getPreco_produto() + lista.getPreco_ItemAdicional()) * lista.getQuantidade());
            	} else {
            		bdy.setValorTotal(bdy.getValorTotal() + lista.getPreco_produto() * lista.getQuantidade());
            	}
            	
            	bdy.txtValorTotal.setText(funcoes.formatMoney_Real(bdy.getValorTotal()));
            }
        });
        
        alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            	
            }
        });
        
        alert.show();
	}
	
	private void dialogCancelar() {
		AlertDialog.Builder alert = new AlertDialog.Builder(Cardapio.this);
        
		alert.setTitle("Cancelar");
        alert.setMessage("Deseja cancelar o pedido?");
        
        alert.setPositiveButton(Tag.SIM, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				Intent intent = new Intent(Cardapio.this, MenuPrincipal.class);
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
