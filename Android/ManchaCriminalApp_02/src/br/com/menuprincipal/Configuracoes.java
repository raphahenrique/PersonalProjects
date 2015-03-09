package br.com.menuprincipal;

import java.util.Calendar;

import br.com.manchacriminalapp.R;
import br.com.util.CrimeEnum;
import br.com.util.Util;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class Configuracoes extends Activity {

	private SeekBar sRaio;
	private EditText txtDataConfig;
	private TextView lblRaio;
	private Button btnSalvar;
	private CheckBox chkAs,chkFu,chkHo,chkEs,chkTr,chkOu,chkSe;
	private int a, m, d;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.configuracoes);		
		
		sRaio = (SeekBar)findViewById(R.id.seekBarRaio);
		txtDataConfig = (EditText)findViewById(R.id.txtDataConfig);
		lblRaio = (TextView)findViewById(R.id.lblRaio);
		btnSalvar = (Button)findViewById(R.id.btnSalvar);
		
		chkAs = (CheckBox)findViewById(R.id.chkAssalto);
		chkFu = (CheckBox)findViewById(R.id.chkFurto);
		chkHo = (CheckBox)findViewById(R.id.chkHomicidio);
		chkEs = (CheckBox)findViewById(R.id.chkEstupro);
		chkTr = (CheckBox)findViewById(R.id.chkTrafico);
		chkOu = (CheckBox)findViewById(R.id.chkOutro);
		chkSe = (CheckBox)findViewById(R.id.chkSequestro);
		
		iniciar();
		
		sRaio.setOnSeekBarChangeListener(new OnSeekBarChangeListener() 
		{			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) 
			{
				int raio = sRaio.getProgress()*100;
				
				if(raio == 0)
					raio = 1000;
				
				lblRaio.setText(raio + "m");	
			}
		});
		
		txtDataConfig.setOnClickListener(new OnClickListener() 
		{
			private DatePicker dt;
			
			@Override
			public void onClick(View v) 
			{
				AlertDialog.Builder build2 = new AlertDialog.Builder(Configuracoes.this);
				build2.setTitle("Data");
				
				LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				final View form = inflater.inflate(R.layout.item_data, null);
				
				dt = ((DatePicker)form.findViewById(R.id.datePicker1));
				dt.setMaxDate(System.currentTimeMillis());
				dt.setMinDate(0);				
				
				if(android.os.Build.VERSION.SDK_INT > 10){
					dt.setCalendarViewShown(false);
				}
				
				build2.setView(form);
				
				build2.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) 
					{
						a = dt.getYear();
						m = dt.getMonth();
						d = dt.getDayOfMonth();
						
				
						txtDataConfig.setText(d+"/"+(m+1)+"/"+a);								
					}
				});
				
				build2.create();
		        build2.show();
				
			}
		});
		
		
		btnSalvar.setOnClickListener(new OnClickListener() 
		{			
			@Override
			public void onClick(View v) 
			{				

				int tmpOcorrencia = 0;
				
				if(chkAs.isChecked())
					tmpOcorrencia += CrimeEnum.ASSALTO.getMask();
				
				if(chkFu.isChecked())
					tmpOcorrencia += CrimeEnum.FURTO.getMask();
				
				if(chkHo.isChecked())
					tmpOcorrencia += CrimeEnum.HOMICIDIO.getMask();
				
				if(chkTr.isChecked())
					tmpOcorrencia += CrimeEnum.TRAFICO.getMask();
				
				if(chkSe.isChecked())
					tmpOcorrencia += CrimeEnum.SEQUESTRO.getMask();
				
				if(chkEs.isChecked())
					tmpOcorrencia += CrimeEnum.ESTUPRO.getMask();
				
				if(chkOu.isChecked())
					tmpOcorrencia += CrimeEnum.OUTRO.getMask();
				
				int raio = sRaio.getProgress()*100;
				
				if(raio == 0)
					raio = 1000;
				
				Util.OCORRENCIAS = tmpOcorrencia;
				Util.RADIUS = raio;
				
				Calendar cal = Calendar.getInstance();										
				cal.set(a, m, d);
				
				if(cal.getTimeInMillis() < 0)
					Util.START_DATE = 0;
				else
					Util.START_DATE = cal.getTimeInMillis();
				
				Util.saveOcorrencias(Configuracoes.this);
								
				finish();
			}
		});
		
				
	}

	private void iniciar(){
		lblRaio.setText(String.valueOf(Util.RADIUS));		
		sRaio.setProgress(Util.RADIUS/100);
		
		if(Util.START_DATE > 0)
		{
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(Util.START_DATE);
			
			txtDataConfig.setText(cal.get(Calendar.DAY_OF_MONTH) + "/" + (cal.get(Calendar.MONTH)+1) + "/" + cal.get(Calendar.YEAR));
		}
		
		if(CrimeEnum.isMaskInList(CrimeEnum.ASSALTO.getMask()))
			chkAs.setChecked(true);
		
		if(CrimeEnum.isMaskInList(CrimeEnum.FURTO.getMask()))
			chkFu.setChecked(true);
		
		if(CrimeEnum.isMaskInList(CrimeEnum.HOMICIDIO.getMask()))
			chkHo.setChecked(true);
		
		if(CrimeEnum.isMaskInList(CrimeEnum.SEQUESTRO.getMask()))
			chkSe.setChecked(true);
		
		if(CrimeEnum.isMaskInList(CrimeEnum.ESTUPRO.getMask()))
			chkEs.setChecked(true);
		
		if(CrimeEnum.isMaskInList(CrimeEnum.TRAFICO.getMask()))
			chkTr.setChecked(true);
		
		if(CrimeEnum.isMaskInList(CrimeEnum.OUTRO.getMask()))
			chkOu.setChecked(true);
		
		
		
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		
		return true;
	}

}
