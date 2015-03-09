package br.com.bse.localizacao;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.projeto_005_pizzaria.R;
import br.com.bse.cardapio.Cardapio;
import br.com.bse.connection.JSONParser;
import br.com.bse.functions.Funcoes;
import br.com.bse.main.MenuPrincipal;
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
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class Localizacao extends Activity {
	private BdyLocalizacao bdy = new BdyLocalizacao();
	private Funcoes f = new Funcoes();
	private String[] uf_disponiveis;
	private String[] cidades_disponiveis;
	private HashMap<Integer, Integer> mapUF = new HashMap<Integer, Integer>(); //<linha, id_uf>
	private HashMap<Integer, Integer> mapCidade;
	
	
	private JSONParser jParser = new JSONParser();
	private JSONArray jsonArray_uf = null;
	private JSONArray jsonArray_cidade = null;
	private int id_uf_atendimento;
	private boolean isTablet;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		isTablet = getResources().getBoolean(R.bool.isTablet);
		
		if(isTablet) {
			setContentView(R.layout.localizacao_tablet);
		} else {
			setContentView(R.layout.localizacao);
		}
		
		iniciarObjetos();
		
		if (android.os.Build.VERSION.SDK_INT > 8) {
			// Permitindo a conexao com a internet para as threads
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		
		new CarregarUF().execute();
		
		bdy.btnContinuar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(verificarCampos() == Tag.PREENCHIDOS) {
					Intent i = new Intent(getApplicationContext(), Cardapio.class);
					
					//i.putExtra("uf", mapUF.get(bdy.spEstadoDisp.getSelectedItemPosition()));
					//i.putExtra("cidade", mapCidade.get(bdy.spCidadeDisp.getSelectedItemPosition()));
					i.putExtra("uf", bdy.spEstadoDisp.getSelectedItemPosition());
					i.putExtra("cidade", bdy.spCidadeDisp.getSelectedItemPosition());
					startActivity(i);
					
					finish();
				}
			}
		});
	}
	
	private void iniciarObjetos() {
		bdy.spEstadoDisp = (Spinner) findViewById(R.id.loc_spEstadoDisp);
		bdy.spCidadeDisp = (Spinner) findViewById(R.id.loc_spCidadeDisp);
		bdy.btnContinuar = (Button) findViewById(R.id.loc_btnContinuar);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	class CarregarUF extends AsyncTask<String, String, Void> {
		private ProgressDialog pdCarregar = new ProgressDialog(Localizacao.this);
		private JSONObject conexao;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pdCarregar = new ProgressDialog(Localizacao.this);
			pdCarregar.setMessage("Carregando...");
			pdCarregar.setIndeterminate(false);
			pdCarregar.setCancelable(false);
			pdCarregar.show();
		}

		@Override
		protected Void doInBackground(String... params) {
			List<NameValuePair> args = new ArrayList<NameValuePair>();
			
			try {
				conexao = jParser.makeHttpRequest(Tag.URL_UF_DISPONIVEIS, Tag.GET, args);
				
				if(conexao != null) {
					int success = conexao.getInt(Tag.SUCCESS);

					if (success == 1) {
						jsonArray_uf = conexao.getJSONArray(Tag.UF_ATENDIMENTO);
						uf_disponiveis = new String[jsonArray_uf.length() + 1];
						uf_disponiveis[0] = " ";
						
						for (int i = 0; i <= (jsonArray_uf.length() - 1); i++) {
							JSONObject json_obj = jsonArray_uf.getJSONObject(i);

							mapUF.put(i, json_obj.getInt(Tag.ID_UF));
							uf_disponiveis[i+1] = json_obj.getString(Tag.SIGLA_UF);
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
									R.layout.spinner_localizacao_tablet, uf_disponiveis);
						} else {
							uf_adapter = new ArrayAdapter<String>(getApplicationContext(), 
									R.layout.spinner_localizacao, uf_disponiveis);
						}
						
						uf_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						bdy.spEstadoDisp.setAdapter(uf_adapter);
						
						bdy.spEstadoDisp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
				            @Override
				            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
				            	if(bdy.spEstadoDisp.getSelectedItemPosition() == 0) {
				            		String[] vazia = new String[1];
				            		vazia[0] = "";
				            		
				            		ArrayAdapter<String> cidade_adapter = new ArrayAdapter<String>(getApplicationContext(), 
											android.R.layout.simple_spinner_item, vazia);
									cidade_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
									bdy.spCidadeDisp.setAdapter(cidade_adapter);
				            	} else {
				            		id_uf_atendimento = mapUF.get(position - 1);
				            		mapCidade = new HashMap<Integer, Integer>();
					            	new CarregarCidades().execute();
				            	}
				            	
				            }

							@Override
							public void onNothingSelected(AdapterView<?> arg0) {
							}
				        });
					} else {
						AlertDialog.Builder alert = new AlertDialog.Builder(Localizacao.this);
				        
						alert.setTitle("Localização");
				        alert.setMessage("Erro de conexão com a internet. Deseja tentar novamente?");
				        
				        alert.setPositiveButton(Tag.SIM, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								new CarregarUF().execute();
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
		private ProgressDialog pdCarregar = new ProgressDialog(Localizacao.this);
		private JSONObject conexao;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pdCarregar = new ProgressDialog(Localizacao.this);
			pdCarregar.setMessage("Carregando cidades disponíveis...");
			pdCarregar.setIndeterminate(false);
			pdCarregar.setCancelable(true);
			pdCarregar.show();
		}
		
		@Override
		protected String doInBackground(String... params) {
			runOnUiThread(new Runnable() {
				public void run() {
					int success;
					
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
									R.layout.spinner_localizacao_tablet, cidades_disponiveis);
							cidade_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						} else {
							cidade_adapter = new ArrayAdapter<String>(getApplicationContext(), 
									R.layout.spinner_localizacao, cidades_disponiveis);
							cidade_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						}
						bdy.spCidadeDisp.setAdapter(cidade_adapter);
					} else {
						AlertDialog.Builder alert = new AlertDialog.Builder(Localizacao.this);
				        
						alert.setTitle("Localização");
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
	
	private boolean verificarCampos() {
		if(bdy.spEstadoDisp.getSelectedItemPosition() == 0) {
			f.getMensagemOk("Localização", "Selecione uma localização válida.", Localizacao.this);
			return false;
		}
		
		return true;
	}
}
