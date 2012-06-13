package us.gaaoc.elderlaw.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import us.gaaoc.elderlaw.model.CaseStatus;
import us.gaaoc.elderlaw.model.Comment;
import us.gaaoc.elderlaw.model.County;
import us.gaaoc.elderlaw.model.Lawyer;
import us.gaaoc.elderlaw.model.LegalArea;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LawyerDAO
{
	@PersistenceContext
	EntityManager manager;
	
	public void setManager(EntityManager manager)
	{
		this.manager = manager;
	}
	
	@Transactional
	public List<Lawyer> findLawyers(String name, String county/*, String area*/)
	{
		if(name != null && !name.equals(""))
		{
			//search by name if entered
			return manager.createQuery("from Lawyer where name like :name").setParameter("name", "%" + name + "%").getResultList();
		}
		else
		{
			/*if((county != null && !county.equals("")) && (area != null && !area.equals("")))
			{
				//search by county and area of both are entered
				return manager.createQuery("select l from Lawyer l JOIN l.counties as c JOIN l.areas as a where c.name like :county AND a.name like :area").setParameter("county", county).setParameter("area", area).getResultList();
			}
			else*/ if(county !=null && !county.equals(""))
			{
				//search by county if entered
				return manager.createQuery("select l from Lawyer l JOIN l.counties as c where c.name like :county").setParameter("county", county).getResultList();
			}
			/*else if(area != null && !area.equals(""))
			{
				//search by area if entered
				return manager.createQuery("select l from Lawyer l JOIN l.areas as a where a.name like :area").setParameter("area", area).getResultList();
			}*/
			else
			{
				//if none of the search parameters are set it returns everyone
				return manager.createQuery("from Lawyer").getResultList();
			}		
		}
	}
	
	//this is what's called to get the lawyer object for the profile
	public Lawyer giveLawyer(String lawyer)
	{
		return manager.find(Lawyer.class, lawyer);
	}
	
	//creates a new comment, sets the fields based on the input from the form, adds it to the list of comment objects on a lawyer, and merges it back to the db
	public void addComment(String lawyerId, String user, Date date, String comment)
	{
		Comment newComment = new Comment();
		
		newComment.setUser(user);
		newComment.setDate(date);
		newComment.setComment(comment);
		
		Lawyer lawyerComment = manager.find(Lawyer.class, lawyerId);
		
		lawyerComment.getComments().add(newComment);
		
		manager.merge(lawyerComment);
	}
	
	//same as above but for cases instead of comments
	public void addCase(String lawyerId, String firstName, String lastName, String agency, String county, String area, Date date)
	{
		CaseStatus newCase = new CaseStatus();
		County newCounty = (County) manager.createQuery("FROM County WHERE name like :county").setParameter("county", county).getSingleResult();
		LegalArea newArea = (LegalArea) manager.createQuery("FROM LegalArea WHERE name like :area").setParameter("area", area).getSingleResult();
		
		
		newCase.setFirstName(firstName);
		newCase.setLastName(lastName);
		newCase.setAgency(agency);
		newCase.setStartDate(date);
		newCase.setCounty(newCounty);
		newCase.setArea(newArea);
		newCase.setStatus(true);
		
		Lawyer lawyerNewCase = manager.find(Lawyer.class, lawyerId);

		lawyerNewCase.getStatuses().add(newCase);
		
		manager.merge(lawyerNewCase);
	}
	
	//finds the case and changes the status flag and gives it an end date
	public void closeCase(String id, String story, Date date)
	{
		CaseStatus closeCase = manager.find(CaseStatus.class, id);
		boolean storyBool;
		
		if(story.equalsIgnoreCase("yes"))
		{
			storyBool = true;
		}
		else
		{
			storyBool = false;
		}
		
		closeCase.setEndDate(date);
		closeCase.setStatus(false);
		closeCase.setStory(storyBool);
		
		
		
		manager.merge(closeCase);
	}
	
	public void deleteCase(String lawyerId, String caseId)
	{
		Lawyer lawyerDeleteCase = manager.find(Lawyer.class, lawyerId);
		CaseStatus deleteCaseStatus = manager.find(CaseStatus.class, caseId);
		lawyerDeleteCase.getStatuses().remove(deleteCaseStatus);
		manager.merge(lawyerDeleteCase);
	}
}
