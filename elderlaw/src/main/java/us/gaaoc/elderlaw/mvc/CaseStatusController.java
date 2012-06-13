package us.gaaoc.elderlaw.mvc;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import us.gaaoc.elderlaw.service.LawyerDAO;

@Controller
@RequestMapping(value= "/case")
public class CaseStatusController
{
	@Autowired
	private LawyerDAO lawyerDAO;
	
	public void setLawyerDAO(LawyerDAO lawyerDAO)
	{
		this.lawyerDAO = lawyerDAO;
	}
	
	@RequestMapping(value="open", method=RequestMethod.POST)
	public String OpenCaseStatus(Model model, @RequestParam(value="lawyer") String lawyer, @RequestParam(value="firstName") String firstName,@RequestParam(value="lastName") String lastName,@RequestParam(value="county") String county,@RequestParam(value="area") String area,@RequestParam(value="date") String date) throws ParseException
	{
		Date dateChoice = new Date();
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		
		if(!date.equals("Date"))
		{
			dateChoice = df.parse(date);
		}
		else
		{
			
		}
		
		//to get the name for the commenter, the session information is read, all of the stuff before and after the name is stripped out
		//using split and string arrays
		String userSession = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		String agency;
		
		String tempString[] = userSession.split("cn=");
		
		String tempName = tempString[1].toString();
		
		tempString = tempName.split(",");
		
		agency = tempString[0].toString();
		
		lawyerDAO.addCase(lawyer, firstName, lastName, agency, county, area, dateChoice);
		return "redirect:../profile?lawyer=" + lawyer;
	}
	
	@RequestMapping(value="close", method=RequestMethod.POST)
	public String CloseCaseStatus(Model model, @RequestParam(value="lawyer") String lawyer, @RequestParam(value="id") String id, @RequestParam(value="story") String story, @RequestParam(value="date") String date) throws ParseException
	{	
		Date dateChoice = new Date();
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		
		if(!date.equals("Date"))
		{
			dateChoice = df.parse(date);
		}
		else
		{
			
		}
		lawyerDAO.closeCase(id, story, dateChoice);
		return "redirect:../profile?lawyer=" + lawyer;
	}
	
	@RequestMapping(value="delete", method=RequestMethod.POST)
	public String CloseCaseStatus(Model model, @RequestParam(value="lawyer") String lawyer, @RequestParam(value="id") String id) throws ParseException
	{	
		
		lawyerDAO.deleteCase(lawyer, id);
		return "redirect:../profile?lawyer=" + lawyer;
	}
}
