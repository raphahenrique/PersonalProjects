package br.com.bse.dadosentrega;
import java.util.ArrayList;
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
import br.com.bse.main.MenuPrincipal;
import br.com.bse.pagamento.Pagamento;
import br.com.bse.pedido.ListaPedido;
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
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;




public class DadosEntrega extends Activity {
	private BdyDadosEntrega bdy = new BdyDadosEntrega();
	private Funcoes f = new Funcoes();
	private int uf;
	private boolean change = false;
	private boolean isTablet;
	private int cidade;
	private double valorTotal;
	private List<ListaPedido> listaPedido;
	
	private JSONParser jParser = new JSONParser();
	private JSONArray jsonArray_uf = null;
	private JSONArray jsonArray_cidade = null;
	private int id_uf_atendimento;
	private String[] uf_disponiveis;
	private String[] cidades_disponiveis;
	private HashMap<Integer, Integer> mapUF = new HashMap<Integer, Integer>(); //<linha, id_uf>
	private HashMap<Integer, Integer> mapCidade;
	
	public SharedPreferences preferencias;
	public SharedPreferences.Editor editPref;
	public Boolean salvarPref;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		isTablet = getResources().getBoolean(R.bool.isTablet);
		
		if(isTablet) {
			setContentView(R.layout.dados_entrega_tablet);
		} else {
			setContentView(R.layout.dados_entrega);
		}
		
		iniciarObjetos();
		
		if (android.os.Build.VERSION.SDK_INT > 8) {
			// Permitindo a conexao com a internet para as threads
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		
	    Intent i = getIntent();
		uf = i.getExtras().getInt("uf") - 1;
		cidade = i.getExtras().getInt("cidade");
		valorTotal = i.getExtras().getDouble("valorTotal");
		listaPedido = i.getParcelableArrayListExtra("listaPedido");
		
		preferencias = getSharedPreferences("preferencias", MODE_PRIVATE);
		editPref = preferencias.edit();
		
		salvarPref = preferencias.getBoolean("salvarPref", false);
		if(salvarPref == true){
			bdy.txtNome.setText(preferencias.getString("nome", ""));
            bdy.txtTelefone.setText(preferencias.getString("telefone", ""));
            bdy.txtEndereco.setText(preferencias.getString("endereco", ""));
            bdy.txtNumEnd.setText(preferencias.getString("numEnd", ""));
            bdy.txtBairro.setText(preferencias.getString("bairro", ""));
            
            bdy.chkSalvarPref.setChecked(true);
        
			
		}
		
		
		
		new CarregarUF().execute();
		
	    bdy.btnConfirmar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(verificarCampos() == Tag.PREENCHIDOS) {
					
		            if (bdy.chkSalvarPref.isChecked()) {
		                editPref.putBoolean("salvarPref", true);
		                editPref.putString("nome", bdy.txtNome.getText().toString());
		                editPref.putString("telefone", bdy.txtTelefone.getText().toString());
		                editPref.putString("endereco", bdy.txtEndereco.getText().toString());
		                editPref.putString("numEnd", bdy.txtNumEnd.getText().toString());
		                editPref.putString("bairro", bdy.txtBairro.getText().toString());
		                editPref.commit();
		            } else {
		                editPref.clear();
		                editPref.commit();
		            }

					
					
					Intent i = new Intent(getApplicationContext(), Pagamento.class);
					
					i.putExtra("nome", bdy.txtNome.getText().toString());
					i.putExtra("telefone", bdy.txtTelefone.getText().toString());
					i.putExtra("valorTotal", valorTotal);
					i.putExtra("endereco", bdy.txtEndereco.getText().toString());
					i.putExtra("numEnd", Integer.parseInt(bdy.txtNumEnd.getText().toString()));
					i.putExtra("bairro", bdy.txtBairro.getText().toString());
					i.putExtra("cidade", mapCidade.get(bdy.spCidade.getSelectedItemPosition()));
					i.putExtra("uf", mapUF.get(bdy.spUf.getSelectedItemPosition()));
					i.putParcelableArrayListExtra("listaPedido", (ArrayList<? extends Parcelable>) listaPedido);
					
					startActivity(i);
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

	private boolean verificarCampos() {
		if(!f.isValidString(bdy.txtNome.getText().toString())) {
			f.getMensagemOk("Pedido", "Nome inválido.", DadosEntrega.this);
			bdy.txtNome.requestFocus();
			
			return false;
		}
		
		if(!f.isValidString(bdy.txtEndereco.getText().toString())) {
			f.getMensagemOk("Pedido", "Endereço inválido.", DadosEntrega.this);
			bdy.txtEndereco.requestFocus();
			
			return false;
		}
		
		if(!f.isValidInt(bdy.txtNumEnd.getText().toString())) {
			f.getMensagemOk("Pedido", "Número de endereço inválido.", DadosEntrega.this);
			bdy.txtNumEnd.requestFocus();
			
			return false;
		}
		
		if(!f.isValidString(bdy.txtBairro.getText().toString())) {
			f.getMensagemOk("Pedido", "Bairro inválido.", DadosEntrega.this);
			bdy.txtBairro.requestFocus();
			
			return false;
		}
		
		return true;
	}
	
	
	private void iniciarObjetos() {
		bdy.txtNome = (EditText) findViewById(R.id.pedido_txtNome);
		bdy.txtTelefone = (EditText) findViewById(R.id.pedido_txtTelefone);
		bdy.txtEndereco = (EditText) findViewById(R.id.pedido_txtEndereco);
		bdy.txtNumEnd = (EditText) findViewById(R.id.pedido_txtNumEnd);
		bdy.txtBairro = (EditText) findViewById(R.id.pedido_txtBairro);
		bdy.spUf = (Spinner) findViewById(R.id.pedido_spUf);
		bdy.spCidade = (Spinner) findViewById(R.id.pedido_spCidade);
		bdy.chkSalvarPref = (CheckBox) findViewById(R.id.pedido_chkSalvarPref);
		bdy.btnConfirmar = (Button) findViewById(R.id.pedido_btnConfirmar);
		bdy.btnCancelar = (Button) findViewById(R.id.pedido_btnCancelar);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	class CarregarUF extends AsyncTask<String, String, Void> {
		private JSONObject conexao;
		private ProgressDialog pdCarregar = new ProgressDialog(DadosEntrega.this);

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pdCarregar = new ProgressDialog(DadosEntrega.this);
			pdCarregar.setMessage("Carregando...");
			pdCarregar.setIndeterminate(false);
			pdCarregar.setCancelable(false);
			pdCarregar.show();
		}

		@Override
		protected Void doInBackground(String... params) {
			try {
				List<NameValuePair> args = new ArrayList<NameValuePair>();
				conexao = jParser.makeHttpRequest(Tag.URL_UF_DISPONIVEIS, Tag.GET, args);
				
				if(conexao != null) {
					int success = conexao.getInt(Tag.SUCCESS);

					if (success == 1) {
						jsonArray_uf = conexao.getJSONArray(Tag.UF_ATENDIMENTO);
						uf_disponiveis = new String[jsonArray_uf.length()];
						
						for (int i = 0; i <= (jsonArray_uf.length() - 1); i++) {
							JSONObject json_obj = jsonArray_uf.getJSONObject(i);

							mapUF.put(i, json_obj.getInt(Tag.ID_UF));
							uf_disponiveis[i] = json_obj.getString(Tag.SIGLA_UF);
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
						ArrayAdapter<String> uf_adapter;
						
						if(isTablet) {
							uf_adapter = new ArrayAdapter<String>(getApplicationContext(), 
									R.layout.spinner_row_tablet, uf_disponiveis);
						} else {
							uf_adapter = new ArrayAdapter<String>(getApplicationContext(), 
									R.layout.spinner_row, uf_disponiveis);
						}
						
						uf_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						bdy.spUf.setAdapter(uf_adapter);
						bdy.spUf.setSelection(uf);
						
						bdy.spUf.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
				            @Override
				            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
				            	if(change) {
				            		cidade = 0;
				            	}
				            	id_uf_atendimento = mapUF.get(position);
				            	mapCidade = new HashMap<Integer, Integer>();
				            	new CarregarCidades().execute();
				            }

							@Override
							public void onNothingSelected(AdapterView<?> arg0) {
							}
				        });
					} else {
						AlertDialog.Builder alert = new AlertDialog.Builder(DadosEntrega.this);
				        
						alert.setTitle("Dados para Entrega");
				        alert.setMessage("Erro de conexão com a internet. Deseja tentar novamente?");
				        
				        alert.setPositiveButton(Tag.SIM, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								new CarregarUF();
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
	
	class CarregarCidades extends AsyncTask<String, String, String> {
		private ProgressDialog pdCarregar = new ProgressDialog(DadosEntrega.this);
		private int success;
		private JSONObject conexao;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pdCarregar = new ProgressDialog(DadosEntrega.this);
			pdCarregar.setMessage("Carregando cidades disponíveis...");
			pdCarregar.setIndeterminate(false);
			pdCarregar.setCancelable(true);
			pdCarregar.show();
		}
		
		@Override
		protected String doInBackground(String... params) {
			runOnUiThread(new Runnable() {
				public void run() {
					
					
					try {
						jParser = new JSONParser();
						List<NameValuePair> params = new ArrayList<NameValuePair>();
						params.add(new BasicNameValuePair(Tag.ID_UF_ATENDIMENTO, String.valueOf(id_uf_atendimento)));
						conexao = jParser.makeHttpRequest(Tag.URL_CIDADES_DISPONIVEIS, Tag.GET, params);

						if(conexao != null) {
							success = conexao.getInt(Tag.SUCCESS);
							
							if (success == 1) {
								jsonArray_cidade = conexao.getJSONArray(Tag.CIDADE_ATENDIMENTO);
								cidades_disponiveis = new String[jsonArray_cidade.length()];

								for (int i = 0; i <= (jsonArray_cidade.length() - 1); i++) {
									JSONObject json_obj = jsonArray_cidade.getJSONObject(i);
									
									mapCidade.put(i, json_obj.getInt(Tag.ID_CIDADE));
									cidades_disponiveis[i] = json_obj.getString(Tag.DESCRICAO_CIDADE);
								}
								
								pdCarregar.dismiss();
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
			
			//Aproveitando o uso da Thread para atualizar o spinner
			runOnUiThread(new Runnable() {
				public void run() {
					if(conexao != null) {
						ArrayAdapter<String> cidade_adapter;
						
						if(isTablet) {
							cidade_adapter = new ArrayAdapter<String>(getApplicationContext(), 
									R.layout.spinner_row_tablet, cidades_disponiveis);
						} else {
							cidade_adapter = new ArrayAdapter<String>(getApplicationContext(), 
									R.layout.spinner_row, cidades_disponiveis);
						}
						
						cidade_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						bdy.spCidade.setAdapter(cidade_adapter);
						bdy.spCidade.setSelection(cidade);
						change = true;
					} else {
						AlertDialog.Builder alert = new AlertDialog.Builder(DadosEntrega.this);
				        
						alert.setTitle("Dados para Entrega");
				        alert.setMessage("Erro de conexão com a internet. Deseja tentar novamente?");
				        
				        alert.setPositiveButton(Tag.SIM, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								new CarregarCidades().execute();
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
	
	private void dialogCancelar() {
		AlertDialog.Builder alert = new AlertDialog.Builder(DadosEntrega.this);
        
		alert.setTitle("Cancelar");
        alert.setMessage("Deseja cancelar o pedido?");
        
        alert.setPositiveButton(Tag.SIM, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				Intent intent = new Intent(DadosEntrega.this, MenuPrincipal.class);
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
