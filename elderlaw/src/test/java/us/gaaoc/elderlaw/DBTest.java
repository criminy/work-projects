/*package us.gaaoc.elderlaw;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import us.gaaoc.elderlaw.model.Lawyer;
import us.gaaoc.elderlaw.model.LegalArea;
import us.gaaoc.elderlaw.service.DBUpdate;

@RunWith(value=SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:elderLaw-db.xml")
@TransactionConfiguration(transactionManager="txManager", defaultRollback=false)
@Transactional
public class DBTest
{
	
	@PersistenceContext
	EntityManager manager;
	
	public void setManager(EntityManager manager)
	{
		this.manager = manager;
	}
	
	@Test
	public void doNothing()
	{
		
	}
	
	Had to run this once to change all the areas to homoginized values
	@Test
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
	
	
}
*/