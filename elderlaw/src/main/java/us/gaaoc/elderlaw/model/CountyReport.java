package us.gaaoc.elderlaw.model;

public class CountyReport
{
	String name;
	int total, probate, powerOfAttorney, contract, collectionsGarnishment, other;
	
	public int getTotal() 
	{
		return total;
	}
	public void setTotal(int total) 
	{
		this.total = total;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public int getProbate()
	{
		return probate;
	}
	public void setProbate(int probate)
	{
		this.probate = probate;
	}
	public int getPowerOfAttorney()
	{
		return powerOfAttorney;
	}
	public void setPowerOfAttorney(int powerOfAttorney) 
	{
		this.powerOfAttorney = powerOfAttorney;
	}
	public int getContract() 
	{
		return contract;
	}
	public void setContract(int contract) 
	{
		this.contract = contract;
	}
	public int getCollectionsGarnishment() 
	{
		return collectionsGarnishment;
	}
	public void setCollectionsGarnishment(int collectionsGarnishment) 
	{
		this.collectionsGarnishment = collectionsGarnishment;
	}
	public int getOther() 
	{
		return other;
	}
	public void setOther(int other) 
	{
		this.other = other;
	}
	

}
