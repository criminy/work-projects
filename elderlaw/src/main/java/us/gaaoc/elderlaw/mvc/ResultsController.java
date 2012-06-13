package us.gaaoc.elderlaw.mvc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import us.gaaoc.elderlaw.model.Lawyer;
import us.gaaoc.elderlaw.service.LawyerDAO;

@Controller
@RequestMapping(value = "/results")
public class ResultsController
{
	@Autowired
	private LawyerDAO lawyerDAO;
	
	public void setLawyerDAO(LawyerDAO lawyerDAO)
	{
		this.lawyerDAO = lawyerDAO;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String getResultsForm(Model model, @RequestParam String lastName, @RequestParam String county/*, @RequestParam String area*/)
	{
		List<Lawyer> listResult = lawyerDAO.findLawyers(lastName, county/*, area*/);
		System.out.println(listResult.size());
		
		model.addAttribute("results", listResult);
		return "results";
	}
}