package us.gaaoc.elderlaw.model;

import java.util.ArrayList;
import java.util.List;

public class Reports
{
	private int totalOpen;
	private int totalClosed ;
	private int averageTime;
	
	private List<CountyReport> counties = new ArrayList<CountyReport>();

	public int getTotalOpen()
	{
		return totalOpen;
	}
	public void setTotalOpen(int totalOpen)
	{
		this.totalOpen = totalOpen;
	}
	public int getTotalClosed()
	{
		return totalClosed;
	}
	public void setTotalClosed(int totalClosed)
	{
		this.totalClosed = totalClosed;
	}
	public int getAverageTime()
	{
		return averageTime;
	}
	public void setAverageTime(int averageTime)
	{
		this.averageTime = averageTime;
	}
	public void setCounties(List<CountyReport> counties) 
	{
		this.counties = counties;
	}
	public List<CountyReport> getCounties() 
	{
		return counties;
	}
}
