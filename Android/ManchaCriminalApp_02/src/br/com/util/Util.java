package br.com.util;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

public class Util
{
	/**
	 * General Configs
	 */
	public static int RADIUS = 1000;
	public static long START_DATE = 0;
	public static int OCORRENCIAS = 127;
	public static boolean ENABLE_MARKER = true;
	
	public static String FILE = "DATA";
	private static List<DataTemplate> entries = new ArrayList<DataTemplate>(); 
		
	public static DataTemplate[] getEntries()
	{
		return entries.toArray(new DataTemplate[0]);
	}
	
	public static void addOcorrencia(Context ctx, DataTemplate dt)
	{
		entries.add(dt);
		saveOcorrencias(ctx);
	}	
	
	public static void saveOcorrencias(Context ctx)
	{		
		StringBuilder conteudo = new StringBuilder();
		
		conteudo.append(RADIUS);
		conteudo.append(";");
		conteudo.append(OCORRENCIAS);
		conteudo.append(";");
		conteudo.append(START_DATE);
		conteudo.append("\r\n");
		
		for(DataTemplate dt : entries)
		{
			conteudo.append(dt.getLatitude());
			conteudo.append(";");
			conteudo.append(dt.getLongitude());
			conteudo.append(";");
			conteudo.append(dt.getData());
			conteudo.append(";");
			conteudo.append(dt.getDesc());
			conteudo.append(";");
			conteudo.append(dt.getTipo().toString());
			conteudo.append("\r\n");
		}
		
		saveFile(FILE, ctx, conteudo.toString());
	}
	
	private static boolean saveFile(String file, Context ctx, String content)
	{
		FileOutputStream outputStream;
		try 
		{
			ctx.deleteFile(file);
			outputStream = ctx.openFileOutput(FILE, Context.MODE_PRIVATE);
			outputStream.write(content.getBytes());
			outputStream.close();
		  
		  return true;
		} 
		catch (Exception e) 
		{}
		
		return false;
	}
	
	private static List<String> readFile(String file, Context ctx)
	{
		List<String> tmp = new ArrayList<String>();
		try
		{
	        InputStream inputStream = ctx.openFileInput(FILE);

	        if ( inputStream != null ) 
	        {
	            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
	            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
	            String receiveString = "";

	            while ((receiveString = bufferedReader.readLine()) != null) 
	            	tmp.add(receiveString);

	            inputStream.close();
	        }
	    }
	    catch (Exception e) 
	    {}
		
		return tmp;
	}
	
	public static void readOccorencias(Context ctx)
	{
		entries.clear();
		
		boolean config = false;
		
		for(String s : readFile(FILE, ctx))
		{
			StringTokenizer st = new StringTokenizer(s, ";");
			
			if (!config)
			{
				try{ 
					RADIUS = Integer.parseInt(st.nextToken());
					OCORRENCIAS = Integer.parseInt(st.nextToken());
					START_DATE = Long.parseLong(st.nextToken());

				}catch(NumberFormatException ne){
				          
				}
				
				
				config = true;
			}
			
			if(st.countTokens() != 5)
				continue;
	            	
			entries.add(new DataTemplate(Double.parseDouble(st.nextToken()), Double.parseDouble(st.nextToken()), Long.parseLong(st.nextToken()), st.nextToken(), CrimeEnum.valueOf(st.nextToken())));
		}   
	}
	
	// metros
	public static float getDistanceBetweenPoints(LatLng p1, LatLng p2)
	{
		float[] tmp = new float[1];
		Location.distanceBetween(p1.latitude, p1.longitude, p2.latitude, p2.longitude, tmp);
		
		return tmp[0];
	}
	
	public static List<DataTemplate> getEntriesInRadius(Context ctx, DataTemplate[] src, LatLng center, int radius)
	{
		List<DataTemplate> tmp = new ArrayList<DataTemplate>();
		
		for(DataTemplate dt : src)
		{
			if(!entryFilter(dt))
				continue;

			LatLng tmpLL = new LatLng(dt.getLatitude(), dt.getLongitude());
			
			if(getDistanceBetweenPoints(center, tmpLL) <= radius)
				tmp.add(dt);
		}
		
		return tmp;
	}
	
	private static boolean entryFilter(DataTemplate dt)
	{
		if(dt.getData() < START_DATE)
			return false;
		
		if(!CrimeEnum.isMaskInList(dt.getTipo().getMask()))
			return false;
		
		return true;
	}
	
	public static CircleOptions newCircle(LatLng posicao, int radius, int stroke, int color)
	{
		CircleOptions circleOptions = new CircleOptions();
		circleOptions.center(posicao).radius(radius).strokeWidth(stroke).strokeColor(color); //Fill/stroke >cor de dentro/fora
	
		return circleOptions;	
	}
}