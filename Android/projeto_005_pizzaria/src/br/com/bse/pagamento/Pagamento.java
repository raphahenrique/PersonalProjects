package br.com.bse.pagamento;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.projeto_005_pizzaria.R;
import br.com.bse.connection.JSONParser;
import br.com.bse.functions.Funcoes;
import br.com.bse.infopedido.InfoPedido;
import br.com.bse.main.MenuPrincipal;
import br.com.bse.pedido.ListaPedido;
import br.com.bse.pedido.Pedido;
import br.com.bse.tags.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class Pagamento extends Activity {
	private BdyPagamento bdy = new BdyPagamento();
	private Pedido p = new Pedido();
	private Funcoes f = new Funcoes();
	private JSONParser jParser = new JSONParser();
	private JSONArray jsonArray_formaPag = null;
	private HashMap<Integer, Integer> mapFormaPag = new HashMap<Integer, Integer>(); //<linha, id_forma_pag>
	private String[] formas_pag;
	private List<ListaPedido> listaPedido;
	private String sql = "";
	private ProgressDialog pdCarregar;
	private boolean isTablet;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		isTablet = getResources().getBoolean(R.bool.isTablet);
		
		if(isTablet) {
			setContentView(R.layout.pagamento_tablet);
		} else {
			setContentView(R.layout.pagamento);
		}
		
		iniciarObjetos();
		setarValores();
		
		if (android.os.Build.VERSION.SDK_INT > 8) {
			// Permitindo a conexao com a internet para as threads
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		
		new CarregarFormaPag().execute();
		
		bdy.txtTrocoPara.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				if(!s.toString().equals("")) {
					if(Double.parseDouble(s.toString()) >= p.getValorTotal()) {
						p.setTroco(Double.parseDouble(s.toString()) - p.getValorTotal());
						bdy.txtTroco.setText(f.formatMoney_Real(p.getTroco()));
					} else {
						p.setTroco(0);
						bdy.txtTroco.setText(f.formatMoney_Real(0));
					}
				} else {
					p.setTroco(0);
					bdy.txtTroco.setText(f.formatMoney_Real(0));
				}
			}
		});
		
		bdy.btnFinalizarPedido.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				pdCarregar = new ProgressDialog(Pagamento.this);
				pdCarregar.setMessage("Carregando...");
				pdCarregar.setIndeterminate(false);
				pdCarregar.setCancelable(false);
				pdCarregar.show();
				
				p.setId_formaPag(mapFormaPag.get(bdy.spFormaPag.getSelectedItemPosition()));
				new GerarIdPedido().execute();
				new AdicionarPedido().execute();
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
		bdy.spFormaPag = (Spinner) findViewById(R.id.pag_spFormaPag);
		bdy.txtTrocoPara = (EditText) findViewById(R.id.pag_txtTrocoPara);
		bdy.txtValorTotal = (TextView) findViewById(R.id.pag_txtValorTotal);
		bdy.txtTroco = (TextView) findViewById(R.id.pag_txtTroco);
		bdy.btnFinalizarPedido = (Button) findViewById(R.id.pag_btnFinalizarPedido);
		bdy.btnCancelar = (Button) findViewById(R.id.pag_btnCancelar);
	}
	
	private void setarValores() {
		
		// "yyyy-MM-dd hh:mm:ss"
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date dataPed = new Date();
		
		Intent i = getIntent();
		p.setNome(i.getExtras().getString("nome"));
		p.setTelefone(i.getExtras().getString("telefone"));
		p.setValorTotal(i.getExtras().getDouble("valorTotal"));
		p.setEndereco(i.getExtras().getString("endereco"));
		p.setNumEnd(i.getExtras().getInt("numEnd"));
		p.setBairro(i.getExtras().getString("bairro"));
		p.setCidade(i.getExtras().getInt("cidade"));
		p.setUf(i.getExtras().getInt("uf"));
		p.setStatus(Tag.PEDIDO_FINALIZADO);
		p.setDataPed(sdf.format(dataPed));
		listaPedido = i.getParcelableArrayListExtra("listaPedido");
		bdy.txtValorTotal.setText(f.formatMoney_Real(p.getValorTotal()));
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		
		return super.onCreateOptionsMenu(menu);
	}

	class CarregarFormaPag extends AsyncTask<String, String, Void> {
		private JSONObject conexao;
		private ProgressDialog pdCarregar = new ProgressDialog(Pagamento.this);

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pdCarregar = new ProgressDialog(Pagamento.this);
			pdCarregar.setMessage("Carregando...");
			pdCarregar.setIndeterminate(false);
			pdCarregar.setCancelable(false);
			pdCarregar.show();
		}

		@Override
		protected Void doInBackground(String... params) {
			try {
				List<NameValuePair> args = new ArrayList<NameValuePair>();
				conexao = jParser.makeHttpRequest(Tag.URL_FORMA_PAG, Tag.GET, args);
				
				if(conexao != null) {
					int success = conexao.getInt(Tag.SUCCESS);

					if (success == 1) {
						jsonArray_formaPag = conexao.getJSONArray(Tag.FORMA_PAG);
						formas_pag = new String[jsonArray_formaPag.length()];
						
						for (int i = 0; i <= (jsonArray_formaPag.length() - 1); i++) {
							JSONObject json_obj = jsonArray_formaPag.getJSONObject(i);

							mapFormaPag.put(i, json_obj.getInt(Tag.ID_FORMA_PAG));
							formas_pag[i] = json_obj.getString(Tag.DESC_FORMA_PAG);
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
						ArrayAdapter<String> formaPag_adapter;
						
						if(isTablet) {
							formaPag_adapter = new ArrayAdapter<String>(getApplicationContext(), 
									R.layout.spinner_row_tablet, formas_pag);
						} else {
							formaPag_adapter = new ArrayAdapter<String>(getApplicationContext(), 
									R.layout.spinner_row, formas_pag);
						}
						formaPag_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						bdy.spFormaPag.setAdapter(formaPag_adapter);
						
						bdy.spFormaPag.setSelection(0);
					} else {
						AlertDialog.Builder alert = new AlertDialog.Builder(Pagamento.this);
				        
						alert.setTitle("Pagamento");
				        alert.setMessage("Erro de conexão com a internet. Deseja tentar novamente?");
				        
				        alert.setPositiveButton(Tag.SIM, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								new CarregarFormaPag().execute();
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
	
	class GerarIdPedido extends AsyncTask<String, String, Void> {
		private JSONObject conexao;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(String... params) {
			try {
				List<NameValuePair> args = new ArrayList<NameValuePair>();
				conexao = jParser.makeHttpRequest(Tag.URL_MAX_ID_PEDIDO, Tag.GET, args);
				
				if(conexao != null) {
					int success = conexao.getInt(Tag.SUCCESS);

					if (success == 1) {
						jsonArray_formaPag = conexao.getJSONArray(Tag.TB_PED);
						p.setId(1);
						
						for (int i = 0; i <= (jsonArray_formaPag.length() - 1); i++) {
							JSONObject json_obj = jsonArray_formaPag.getJSONObject(i);

							p.setId(p.getId() + json_obj.getInt(Tag.ID_PED));
						}
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			return null;
		}

		protected void onPostExecute(Void v) {
			runOnUiThread(new Runnable() {
				public void run() {
					if(conexao == null) {
						AlertDialog.Builder alert = new AlertDialog.Builder(Pagamento.this);
				        
						alert.setTitle("Pagamento");
				        alert.setMessage("Erro de conexão com a internet. Deseja tentar novamente?");
				        
				        alert.setPositiveButton(Tag.SIM, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								new GerarIdPedido().execute();
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
	
	class AdicionarPedido extends AsyncTask<String, String, String> {
		private JSONObject conexao;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
		}
		
		protected String doInBackground(String... args) {
			try {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				
				params.add(new BasicNameValuePair(Tag.ID_PED, String.valueOf(p.getId())));
				params.add(new BasicNameValuePair(Tag.NOME_CLI_PED, p.getNome()));
				params.add(new BasicNameValuePair(Tag.TEL_PED, p.getTelefone()));
				params.add(new BasicNameValuePair(Tag.VALOR_TOTAL_PED, String.valueOf(p.getValorTotal())));
				params.add(new BasicNameValuePair(Tag.FORMA_PAG_PED, String.valueOf(p.getId_formaPag())));
				params.add(new BasicNameValuePair(Tag.TROCO_PED, String.valueOf(p.getTroco())));
				params.add(new BasicNameValuePair(Tag.ENDERECO_PED, p.getEndereco()));
				params.add(new BasicNameValuePair(Tag.NUM_END_PED, String.valueOf(p.getNumEnd())));
				params.add(new BasicNameValuePair(Tag.BAIRRO_PED, p.getBairro()));
				params.add(new BasicNameValuePair(Tag.CIDADE_PED, String.valueOf(p.getCidade())));
				params.add(new BasicNameValuePair(Tag.UF_PED, String.valueOf(p.getUf())));
				params.add(new BasicNameValuePair(Tag.STATUS_PED, String.valueOf(p.getStatus())));
				params.add(new BasicNameValuePair(Tag.DATA_PED, String.valueOf(p.getDataPed())));
				
				conexao = jParser.makeHttpRequest(Tag.URL_ADD_PEDIDO, Tag.POST, params);
				
				if(conexao != null) {
					if (conexao.getInt(Tag.SUCCESS) == 1) {
						gerarSqlItens();
					} 
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		protected void onPostExecute(String file_url) {
			if(conexao == null) {
				AlertDialog.Builder alert = new AlertDialog.Builder(Pagamento.this);
		        
				alert.setTitle("Pagamento");
		        alert.setMessage("Erro de conexãoo com a internet. Deseja tentar novamente?");
		        
		        alert.setPositiveButton(Tag.SIM, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						new AdicionarPedido().execute();
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
	
	private void gerarSqlItens() {
		for (final ListaPedido lista : listaPedido) {
			if(!sql.equals("")) {
				sql += ", ";
			}
			String[] valores = new String[Tag.QTD_CAMPOS_ITENS];
			
			valores[0] = Tag.NULL;
			valores[1] = String.valueOf(p.getId());
			valores[2] = String.valueOf(lista.getId_produto());
			valores[3] = String.valueOf(lista.getId_itemAdicional());
			valores[4] = String.valueOf(lista.getPreco_ItemAdicional());
			valores[5] = String.valueOf(lista.getPreco_produto());
			valores[6] = String.valueOf(lista.getQuantidade());
			valores[7] = Tag.NULL;
			
			sql += f.gerarCamposSql(valores);
		}
		
		new AdicionarItensPedido().execute();
	}
	
	class AdicionarItensPedido extends AsyncTask<String, String, String> {
		private int success;
		private JSONObject conexao;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
		
		protected String doInBackground(String... args) {
			try {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				
				String querySql = "INSERT INTO " + Tag.TB_ITEM_PEDIDO + " VALUES " + sql;
				params.add(new BasicNameValuePair(Tag.VALOR_SQL, querySql));
				
				conexao = jParser.makeHttpRequest(Tag.URL_ADD_ITENS_PED, Tag.POST, params);
				
				if(conexao != null) {
					if (conexao.getInt(Tag.SUCCESS) == 1) {
						success = conexao.getInt(Tag.SUCCESS);
					} 
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		protected void onPostExecute(String file_url) {
			if(conexao != null) {
				if(success == 1) {
					pdCarregar.dismiss();
					Intent i = new Intent(getApplicationContext(), InfoPedido.class);
					i.putExtra("numPed", p.getId());
					i.putExtra("valorTotal", p.getValorTotal());
					i.putExtra("troco", p.getTroco());
					i.putParcelableArrayListExtra("listaPedido", (ArrayList<? extends Parcelable>) listaPedido);
					startActivity(i);
					
					finish();
				}
			} else {
				AlertDialog.Builder alert = new AlertDialog.Builder(Pagamento.this);
		        
				alert.setTitle("Pagamento");
		        alert.setMessage("Erro de conexão com a internet. Deseja tentar novamente?");
		        
		        alert.setPositiveButton(Tag.SIM, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						new AdicionarItensPedido().execute();
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
	
	private void dialogCancelar() {
		AlertDialog.Builder alert = new AlertDialog.Builder(Pagamento.this);
        
		alert.setTitle("Cancelar");
        alert.setMessage("Deseja cancelar o pedido?");
        
        alert.setPositiveButton(Tag.SIM, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				Intent intent = new Intent(Pagamento.this, MenuPrincipal.class);
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
