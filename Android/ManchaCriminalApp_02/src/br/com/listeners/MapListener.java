package br.com.listeners;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import br.com.manchacriminalapp.R;
import br.com.util.CrimeEnum;
import br.com.util.DataTemplate;
import br.com.util.Util;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapListener implements OnMapClickListener
{
	private GoogleMap map;
	private Context ctx;
	private ArrayAdapter<String> adapter;
	private EditText txtData; 
	private DatePicker dt;
	private int d,m,a;
	private EditText txtHora;
	private TimePicker tm;
	private int hora,min;
	
	public MapListener(Context ctx, GoogleMap map)
	{
		this.ctx = ctx;
		this.map = map;
		
		List<String> list = new ArrayList<String>();
		
		for(CrimeEnum e : CrimeEnum.values())
			list.add(e.toString());
		
		adapter = new ArrayAdapter<String>(ctx, android.R.layout.simple_spinner_item, list);
	}
	
	@Override
	public void onMapClick(final LatLng posicao) 
	{
		if(!Util.ENABLE_MARKER)
			return;
		map.clear();
		
		final List<DataTemplate> region = Util.getEntriesInRadius(ctx, Util.getEntries(), posicao, Util.RADIUS);	
		addMarkersInRadius(region, posicao, map, ctx);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle("Ocorrência");
        builder.setMessage("O que deseja fazer?");
        
        builder.setPositiveButton("Nova ocorrencia", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				AlertDialog.Builder build = new AlertDialog.Builder(ctx);
		        build.setTitle("Nova ocorrência");
		        
				LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				final View form = inflater.inflate(R.layout.item_formulario, null);
		
				((Spinner) form.findViewById(R.id.cmbTipo)).setAdapter(adapter);
				
				txtData = (EditText)form.findViewById(R.id.txtData);
				txtData.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						AlertDialog.Builder build2 = new AlertDialog.Builder(ctx);
						build2.setTitle("Data ocorrência");
						
						LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
						final View form = inflater.inflate(R.layout.item_data, null);
						
						dt = ((DatePicker)form.findViewById(R.id.datePicker1));
						
						if(android.os.Build.VERSION.SDK_INT > 10){
							dt.setCalendarViewShown(false);
							dt.setMaxDate(System.currentTimeMillis());
							dt.setMinDate(0);
						}
						
						build2.setView(form);
						
						build2.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) //formulario
							{
								a = dt.getYear();
								m = dt.getMonth();
								d = dt.getDayOfMonth();
								
								Calendar cal = Calendar.getInstance();
								cal.set(a, m, d);
								
								txtData.setText(d+"/"+(m+1)+"/"+a);								
							}
						});
						
						build2.create();
				        build2.show();
						
					}
				});
				
				txtHora = (EditText)form.findViewById(R.id.txtHora);
				txtHora.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						AlertDialog.Builder build3 = new AlertDialog.Builder(ctx);
						build3.setTitle("Hora ocorrência");
						
						LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
						final View form = inflater.inflate(R.layout.item_hora, null);
						
						tm = ((TimePicker)form.findViewById(R.id.timePicker));
	
						tm.setIs24HourView(true);
				
						build3.setView(form);
						
						build3.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) //formulario
							{
								hora = tm.getCurrentHour();
								min = tm.getCurrentMinute();
						
								txtHora.setText(hora+":"+min);								
							}
						});
							
						build3.create();
				        build3.show();
						
					}
				});
				build.setView(form);
				
				build.setPositiveButton("Criar", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) //formulario
					{
						Calendar cal = Calendar.getInstance();
						
						String desc = ((EditText) form.findViewById(R.id.txtDesc)).getText().toString();
												
						cal.set(a, m, d, hora, min);
																		
						DataTemplate tmp = new DataTemplate(posicao.latitude, posicao.longitude, cal.getTimeInMillis(), desc, CrimeEnum.valueOf(((Spinner) form.findViewById(R.id.cmbTipo)).getSelectedItem().toString()));
						
						
						MarkerOptions mo = newMarker(posicao, tmp.getTipo().toString(), "Data: " + d + "/" + (m+1) + "/" +a + "\r\nHora: " + hora +  ":" + min, BitmapDescriptorFactory.fromAsset(tmp.getTipo().toString().toLowerCase() + ".png"), ctx);
						
						map.addMarker(mo);
							
						Util.addOcorrencia(ctx, tmp);
					}
				});
				
				build.create();
		        build.show();
			}
		});
        
        builder.setNegativeButton("Visualizar área", new DialogInterface.OnClickListener() {
        	public void onClick(DialogInterface arg0, int arg1) 
        	{  
        		MarkerOptions mo = newMarker(posicao, "Centro", "Crimes na região: " + region.size(), BitmapDescriptorFactory.fromAsset("markerVerde.png"), ctx);
        		map.addMarker(mo);	        
        	} 
        }); 
        
        map.addCircle(Util.newCircle(posicao, Util.RADIUS, 5, Color.RED));	
        
        builder.create();
        builder.show();
	}
	
	public static void addMarkersInRadius(List<DataTemplate> region, LatLng posicao, GoogleMap map, Context ctx)
	{
		for(DataTemplate dt : region)
		{
			LatLng pos = new LatLng(dt.getLatitude(), dt.getLongitude());
			
			Calendar cal = Calendar.getInstance();
			
			cal.setTimeInMillis(dt.getData());
						
			map.addMarker(newMarker(pos, dt.getTipo().toString(), "Data: " + cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH) + "/" + cal.get(Calendar.YEAR) + " " + cal.get(Calendar.HOUR_OF_DAY) +  ":" + cal.get(Calendar.MINUTE), BitmapDescriptorFactory.fromAsset(dt.getTipo().toString().toLowerCase() + ".png"), ctx));
		}
	}
	
	private static MarkerOptions newMarker(LatLng pos, String title, String snipper, BitmapDescriptor icon, Context ctx)
	{
		MarkerOptions mo = new MarkerOptions().position(pos);
		
		mo.title(title);
		mo.snippet(snipper);
		mo.icon(icon);
		
		return mo;
	}
}