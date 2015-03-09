package br.com.util;

public class DataTemplate
{
	private final double _latitude;
	private final double _longitude;
	private final long _data;
	private final String _desc;
	private final CrimeEnum _tipo;
	
	public DataTemplate(double latitude, double longitude, long data, String desc, CrimeEnum tipo)
	{
		_latitude = latitude;
		_longitude = longitude;
		_data = data;
		_desc = desc;
		_tipo = tipo;
	}
	
	public double getLatitude()
	{
		return _latitude;
	}
	
	public double getLongitude()
	{
		return _longitude;
	}
	
	public long getData()
	{
		return _data;
	}
	
	public String getDesc()
	{
		return _desc;
	}
	
	public CrimeEnum getTipo()
	{
		return _tipo;
	}
}