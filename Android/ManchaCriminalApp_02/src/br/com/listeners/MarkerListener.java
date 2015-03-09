package br.com.listeners;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import br.com.util.DataTemplate;
import br.com.util.Util;

import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class MarkerListener implements OnInfoWindowClickListener
{
	private Context ctx;
	
	public MarkerListener(Context ctx)
	{
		this.ctx = ctx;
	}

	@Override
	public void onInfoWindowClick(Marker marker) {
		AlertDialog.Builder build = new AlertDialog.Builder(ctx);
        build.setTitle("Descrição");
		
        LatLng pos = marker.getPosition();
        DataTemplate data = null;
        
        for(DataTemplate dt : Util.getEntries())
        {
        	if(dt.getLatitude() == pos.latitude && dt.getLongitude() == pos.longitude)
        		data = dt;
        }
        
        if(data == null)
        	return;
        
        build.setMessage(data.getDesc());
        
        build.setPositiveButton("OK", new DialogInterface.OnClickListener() {
        	public void onClick(DialogInterface arg0, int arg1) 
        		{  
        			        
        		} 
        	});
        
        build.create();
        build.show();


	
        
	}
}