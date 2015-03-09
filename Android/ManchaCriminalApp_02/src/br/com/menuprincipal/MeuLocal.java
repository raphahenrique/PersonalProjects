package br.com.menuprincipal;


import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;
import br.com.listeners.MapListener;
import br.com.listeners.MarkerListener;
import br.com.listeners.MyLocationListener;
import br.com.manchacriminalapp.R;
import br.com.util.Util;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.SupportMapFragment;


public class MeuLocal extends FragmentActivity 
{	
	private GoogleMap map;
	private ToggleButton btnAtiva;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.meu_local);
		
		FragmentManager fragmentManager = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment)fragmentManager.findFragmentById(R.id.map);

        btnAtiva = (ToggleButton)findViewById(R.id.btnAtiva);        
        
        map = mapFragment.getMap();
            
        map.setMyLocationEnabled(true);
        
        
        map.setOnMyLocationChangeListener(new OnMyLocationChangeListener() {
			
			@Override
			public void onMyLocationChange(Location arg0) {
				
				
			}
		});
        
        btnAtiva.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
			}
		});
        
        Util.readOccorencias(this);
        
    	LocationManager mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
	    mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new MyLocationListener(map, this));       

	    map.setOnMapClickListener(new MapListener(MeuLocal.this, map));
	    map.setOnInfoWindowClickListener(new MarkerListener(MeuLocal.this));
	   	    
	    btnAtiva.setChecked(true);
	    
	    btnAtiva.setOnCheckedChangeListener(new OnCheckedChangeListener() 
	    {	
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) 
			{
				Util.ENABLE_MARKER = isChecked;
				if(isChecked)
					Toast.makeText(MeuLocal.this, "Marcação ativada", Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(MeuLocal.this, "Marcação desativada", Toast.LENGTH_SHORT).show();
			}
		});
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_principal, menu);
		return true;
	}
	
	public void refreshGPS(MenuItem item)
	{
		MyLocationListener.STARTED = false;
		Toast.makeText(this, "Buscando localização...", Toast.LENGTH_SHORT).show();
	}
	
	public void deletaOcorrencias(MenuItem item)
	{
		deleteFile(Util.FILE);
		Toast.makeText(this, "Ocorrências Apagadas !!", Toast.LENGTH_SHORT).show();
	}
	
	public void mostraConfig(MenuItem item)
	{
		Intent i = new Intent(this, Configuracoes.class);
		startActivity(i);
				
		
	}	
}