package us.gaaoc.elderlaw.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import us.gaaoc.elderlaw.model.CaseStatus;
import us.gaaoc.elderlaw.model.CountyReport;
import us.gaaoc.elderlaw.model.Reports;

@Service
@Transactional
public class ReportsDAO
{
	@PersistenceContext
	EntityManager manager;
	
	public void setManager(EntityManager manager)
	{
		this.manager = manager;
	}
	
	//sorts through all the cases to get the ones that were started between the two chosen dates and adds them to the returned list
	public List<CaseStatus> getCasesByDate(Date startDate, Date endDate) throws ParseException
	{
		List<CaseStatus> cases = manager.createQuery("from CaseStatus").getResultList();
		List<CaseStatus> returnCases = new ArrayList<CaseStatus>();
		
		DateTime startDateTime = new DateTime(startDate);
		DateTime endDateTime = new DateTime(endDate);

		startDateTime = startDateTime.minusDays(1);
		endDateTime = endDateTime.plusDays(1);
		
		for(int x = 0; x < cases.size(); x++)
		{
			CaseStatus testCase = cases.get(x);
			Date testStartDate = testCase.getStartDateDate();
			
			if(testStartDate.after(startDateTime.toDate()) && testStartDate.before(endDateTime.toDate()))
			{
				returnCases.add(testCase);
			}
			else {}
		}
		
		return returnCases;
	}
	
	public List<CaseStatus> filterByAgency(List<CaseStatus> cases, String agency)
	{
		List<CaseStatus> filteredCases = new ArrayList<CaseStatus>();
		
		for(int x = 0; x < cases.size(); x++)
		{
			if(cases.get(x).getAgency().equalsIgnoreCase(agency))
			{
				filteredCases.add(cases.get(x));
			}
			else {}
		}
		
		return filteredCases;
	}
	
	public List<CountyReport> countyReports(List<CaseStatus> cases)
	{
		List<CountyReport> counties = new ArrayList<CountyReport>();
		
		for(int x = 0; x < cases.size(); x++)
		{
			boolean newCounty = true;
			int countyLocation = 0;
			
			
			for(int y = 0; y < counties.size(); y++)
			{
				if(cases.get(x).getCounty().getName().equalsIgnoreCase(counties.get(y).getName()))
				{
					newCounty = false;
					countyLocation = y;
					break;
				}
				else {}
			}
			
			if(newCounty == true)
			{
				CountyReport report = new CountyReport();
			
				report.setName(cases.get(x).getCounty().getName());
				report.setTotal(1);
			
				if(cases.get(x).getArea().getName().equalsIgnoreCase("probate"))
				{
					report.setProbate(1);
				}
				else if(cases.get(x).getArea().getName().equalsIgnoreCase("power of attorney"))
				{
					report.setPowerOfAttorney(1);
				}
				else if(cases.get(x).getArea().getName().equalsIgnoreCase("contract"))
				{
					report.setContract(1);
				}
				else if(cases.get(x).getArea().getName().equalsIgnoreCase("collections/garnishment"))
				{
					report.setCollectionsGarnishment(1);
				}
				else if(cases.get(x).getArea().getName().equalsIgnoreCase("other"))
				{
					report.setOther(1);
				}
				else {}
			
				counties.add(report);
			}
			else
			{
				int total = counties.get(countyLocation).getTotal();
				int probate = counties.get(countyLocation).getProbate();
				int powerOfAttorney = counties.get(countyLocation).getPowerOfAttorney();
				int contract = counties.get(countyLocation).getContract();
				int collectionsGarnishment = counties.get(countyLocation).getCollectionsGarnishment();
				int other = counties.get(countyLocation).getOther();
				
				counties.get(countyLocation).setTotal(total += 1);
				
				if(cases.get(x).getArea().getName().equalsIgnoreCase("probate"))
				{
					counties.get(countyLocation).setProbate(probate += 1);
				}
				else if(cases.get(x).getArea().getName().equalsIgnoreCase("power of attorney"))
				{
					counties.get(countyLocation).setPowerOfAttorney(powerOfAttorney += 1);
				}
				else if(cases.get(x).getArea().getName().equalsIgnoreCase("contract"))
				{
					counties.get(countyLocation).setContract(contract += 1);
				}
				else if(cases.get(x).getArea().getName().equalsIgnoreCase("collections/garnishment"))
				{
					counties.get(countyLocation).setCollectionsGarnishment(collectionsGarnishment += 1);
				}
				else if(cases.get(x).getArea().getName().equalsIgnoreCase("other"))
				{
					counties.get(countyLocation).setOther(other += 1);
				}
				else {}
			}
			
		} 
		
		return counties;
	}
	
	public Reports runReports(Date startDate, Date endDate, String agency) throws ParseException
	{
		Reports report = new Reports();
		int open = 0; 
		int closed = 0;
		int averageTime = 0;
		
		List<CaseStatus> cases = getCasesByDate(startDate, endDate);
		
		if(!agency.equalsIgnoreCase("SelectOne"))
		{
			cases = filterByAgency(cases, agency);
		}
		else {}
		
		for(int x = 0; x < cases.size(); x++)
		{
			CaseStatus testCase = cases.get(x);
			
			if(testCase.isStatus() == true)
			{
				open++;
			}
			else
			{
				averageTime += Days.daysBetween(new DateTime(testCase.getStartDateDate()), new DateTime(testCase.getEndDateDate())).getDays();
				closed++;
			}
		}
		
		if(closed > 0)
		{
			averageTime = averageTime / closed;
		}
		else
		{
			averageTime = 0;
		}
		
		report.setTotalOpen(open);
		report.setTotalClosed(closed);
		report.setAverageTime(averageTime);
		
		report.setCounties(countyReports(cases));
		
		return report;
	}
}
