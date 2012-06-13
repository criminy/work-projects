package us.gaaoc.elderlaw.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import us.gaaoc.elderlaw.model.Lawyer;
import us.gaaoc.elderlaw.service.LawyerDAO;

@Controller
@RequestMapping(value = "/profile")
public class ProfileController
{
	@Autowired
	private LawyerDAO lawyerDAO;
	
	public void setLawyerDAO(LawyerDAO lawyerDAO)
	{
		this.lawyerDAO = lawyerDAO;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String getLawyerProfile(Model model, @RequestParam String lawyer)
	{
		Lawyer lawyerProfile = lawyerDAO.giveLawyer(lawyer);
		
		model.addAttribute("lawyerProfile", lawyerProfile);
		
		return "profile";
	}
}