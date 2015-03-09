package br.com.util;

public enum CrimeEnum
{
	ASSALTO(1),
	FURTO(2),
	ESTUPRO(4),
	SEQUESTRO(8),
	HOMICIDIO(16),
	TRAFICO(32),
	OUTRO(64);	
	
	private final int _mask;
	
	private CrimeEnum(int mask)
	{
		_mask = mask;
	}
	
	public int getMask()
	{
		return _mask;
	}
	
	public static boolean isMaskInList(int mask)
	{
		return ((Util.OCORRENCIAS & mask) == mask);
	}
}