package us.gaaoc.elderlaw.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import us.gaaoc.elderlaw.model.County;
import us.gaaoc.elderlaw.model.Lawyer;
import us.gaaoc.elderlaw.model.LegalArea;

@Transactional
@Service
public class DBUpdate
{
	@PersistenceContext
	EntityManager manager;
	
	public void setManager(EntityManager manager)
	{
		this.manager = manager;
	}
	
	/*
	@Scheduled(cron="1 * * * * *")
	public void testCrontab()
	{
		System.out.println("Cron test");
	}
	*/
	
	//scheduled to run every 24h, 15min after CID updates the csv files
	//@Scheduled(cron="0 15 7 * * *")
	//@Scheduled(cron="5 * * * * *")
	public void runUpdate() throws FileNotFoundException, IOException
	{
		String lawyerFile = "/home/colin/work/attorneyinfo.csv";
		String countyFile = "/home/colin/work/counties.csv";
		
		System.out.println("start county update");
			//addLawyer(lawyerFile);
			addCountyToLawyer(countyFile);
			System.out.println("finish county update");

	}
	/*
	@Scheduled(cron="5 * * * * *")
	public void populate() throws FileNotFoundException, IOException
	{
		String lawyerFile = "/home/colin/work/attorneyinfo.csv";
		String countyFile = "/home/colin/work/counties.csv";
		String countiesFile = "/home/colin/work/allcounties.csv";
		
		System.out.println("starting population");
			addLawyer(lawyerFile);
			addCounty(countiesFile);
			addCountyToLawyer(countyFile);
			fixAreas();
			System.out.println("finished population");

	}
	*/
	/*
	 * this reads in the csv file, separates the values based on commas, populates a new lawyer object, checks to see if the lawyer exists,
	 * if the lawyer exists check to see if any of the fields have changed; if they have, update them, if not, do nothing; also clears all existing
	 * county and area relationships so the addCountyToLawyer and addAreaToLawyer methods can remove removed relationships and add new ones
	 * if the lawyer doesn't exist add it straight to the database
	 */
	public void addLawyer(String fileName) throws IOException, FileNotFoundException
	{
		File file = new File(fileName);
		FileReader in = new FileReader(file);
		BufferedReader read = new BufferedReader(in);
		
		while(read.ready())
		{
			String temp;
			String[] tempString;
			boolean tempBool;
			Lawyer newLawyer = new Lawyer();
			//Lawyer testExistingLawyer;
			
			temp = read.readLine();
			tempString = temp.split(";");
			
			for(int x = 0; x < tempString.length; x++)
			{
				temp = tempString[x];
				temp = temp.replaceAll("\"", "");
				temp = temp.trim();
				tempString[x] = temp;
			}
			
			newLawyer.setId(tempString[0]);
			
			newLawyer.setBarNumber(tempString[1]);
			newLawyer.setName(tempString[2]);
	
			
			newLawyer.setAddress(tempString[3]);
			newLawyer.setEmail(tempString[4]);
			newLawyer.setPhone(tempString[5]);
			newLawyer.setStanding(tempString[6]);
			
			if(tempString[7].equalsIgnoreCase("Y"))
			{
				tempBool = true;
			}
			else
			{
				tempBool = false;
			}
			
			newLawyer.setInsurance(tempBool);
			
			if(tempString[8].equalsIgnoreCase("Y"))
			{
				tempBool = true;
			}
			else
			{
				tempBool = false;
			}
			
			newLawyer.setPresentation(tempBool);
			
		
			newLawyer.getCounties().clear();

			manager.merge(newLawyer);
		
		}
		read.close();
		in.close();
	}
	
	public void fixAreas()
	{
		List<Lawyer> lawyersList = manager.createQuery("FROM Lawyer").getResultList();
		Lawyer lawyerFix;
		LegalArea areaOne = manager.find(LegalArea.class, "1");
		LegalArea areaTwo= manager.find(LegalArea.class, "2");
		LegalArea areaThree= manager.find(LegalArea.class, "3");
		LegalArea areaFour= manager.find(LegalArea.class, "4");
		LegalArea areaFive= manager.find(LegalArea.class, "5");
		
		for(int i = 0; i < lawyersList.size(); i++)
		{
			lawyerFix = lawyersList.get(i);
			lawyerFix.getAreas().clear();
			lawyerFix.getAreas().add(areaOne);
			lawyerFix.getAreas().add(areaTwo);
			lawyerFix.getAreas().add(areaThree);
			lawyerFix.getAreas().add(areaFour);
			lawyerFix.getAreas().add(areaFive);
			
			manager.merge(lawyerFix);
		}
	}
	
	//this is a one-off method which adds all the counties to the database, a one off for areas wasn't added because they use custom-IDs
	public void addCounty(String counties) throws IOException, FileNotFoundException
	{
		File file = new File(counties);
		FileReader in = new FileReader(file);
		BufferedReader read = new BufferedReader(in);
		
		while(read.ready())
		{
			String temp;
			String[] tempString;
			County newCounty = new County();
			
			temp = read.readLine();
			tempString = temp.split(";");
			
			for(int x = 0; x < tempString.length; x++)
			{
				temp = tempString[x];
				temp = temp.replaceAll("\"", "");
				temp = temp.trim();
				tempString[x] = temp;
			}
			
			newCounty.setId(tempString[0]);
			newCounty.setName(tempString[1]);
			
			manager.merge(newCounty);
		}
		read.close();
		in.close();
	}

	//this adds all area relationships to the lawyer, converting the short-text IDs for numerical IDs
	//THIS HAS BEEN DISABLED BECAUSE AREAS HAVE BEEN HOMOGENIZED, REENABLE IF AREA IS PUT BACK IN
	public void addAreaToLawyer(String fileName) throws IOException, FileNotFoundException
	{
		File file = new File(fileName);
		FileReader in = new FileReader(file);
		BufferedReader read = new BufferedReader(in);
		
		LegalArea newArea;
		
		while(read.ready())
		{
			String temp;
			String[] tempString;
			String areaId;
			
			temp = read.readLine();
			tempString = temp.split(";");
			
			for(int x = 0; x < tempString.length; x++)
			{
				temp = tempString[x];
				temp = temp.replaceAll("\"", "");
				temp = temp.trim();
				tempString[x] = temp;
			}
			
			Lawyer addLawyerArea = manager.find(Lawyer.class, tempString[0]);
			if(addLawyerArea != null)
			{
				if(tempString[1].equalsIgnoreCase("ADVDIR"))
				{
					areaId = "1";
				}
				else
				{
					areaId = "6";
				}
				newArea = manager.find(LegalArea.class, areaId);
				addLawyerArea.getAreas().add(newArea);
			
				manager.merge(addLawyerArea);
			}
			else {}
		}
		read.close();
		in.close();
	}
	
	//this adds all county relationships to the lawyer
	public void addCountyToLawyer(String fileName) throws IOException, FileNotFoundException
	{
		File file = new File(fileName);
		FileReader in = new FileReader(file);
		BufferedReader read = new BufferedReader(in);
		
		County newCounty;
		
		while(read.ready())
		{
			String temp;
			String[] tempString;
			
			temp = read.readLine();
			tempString = temp.split(";");
			
			for(int x = 0; x < tempString.length; x++)
			{
				temp = tempString[x];
				temp = temp.replaceAll("\"", "");
				temp = temp.trim();
				tempString[x] = temp;
			}
			
			Lawyer addLawyerCounty = manager.find(Lawyer.class, tempString[0]);
			if (addLawyerCounty != null)
			{
				newCounty = manager.find(County.class, tempString[1]);
				
				if(!addLawyerCounty.getCounties().contains(newCounty))
				{
					addLawyerCounty.getCounties().add(newCounty);
				}
				else {}
				
			
				manager.merge(addLawyerCounty);
			}
			else {}
		}
		read.close();
		in.close();
	}
}
