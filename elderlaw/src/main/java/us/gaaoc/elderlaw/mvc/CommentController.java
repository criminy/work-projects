package us.gaaoc.elderlaw.mvc;

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
@RequestMapping(value= "/comment")
public class CommentController
{
	@Autowired
	private LawyerDAO lawyerDAO;
	
	public void setLawyerDAO(LawyerDAO lawyerDAO)
	{
		this.lawyerDAO = lawyerDAO;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String setComment(Model model, @RequestParam(value="lawyer") String lawyer, @RequestParam(value="comment") String comment)
	{
		//to get the name for the commenter, the session information is read, all of the stuff before and after the name is stripped out
		//using split and string arrays
		Date date = new Date();
		String user = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		String userName;
		
		String tempString[] = user.split("cn=");
		
		String tempName = tempString[1].toString();
		
		tempString = tempName.split(",");
		
		userName = tempString[0].toString();
		
		lawyerDAO.addComment(lawyer, userName, date, comment);
		return "redirect:/profile?lawyer=" + lawyer;
	}
}
