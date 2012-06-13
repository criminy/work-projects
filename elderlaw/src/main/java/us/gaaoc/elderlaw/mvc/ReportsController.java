package us.gaaoc.elderlaw.mvc;

import java.text.ParseException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import us.gaaoc.elderlaw.model.Reports;
import us.gaaoc.elderlaw.service.ReportsDAO;

@Controller
@RequestMapping(value = "/reports")
public class ReportsController
{
	@Autowired
	private ReportsDAO reportsDAO;
	
	public void setReportsDAO(ReportsDAO reportsDAO)
	{
		this.reportsDAO = reportsDAO;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String getReports(Model model, @RequestParam(value="startDate", required=false) Date startDate, @RequestParam(value="endDate", required=false) Date endDate, @RequestParam(value="agency", required=false) String agency) throws ParseException
	{	
		if((startDate != null) && endDate != null)
		{
			Reports report = reportsDAO.runReports(startDate, endDate, agency );
		
			model.addAttribute("report", report);
		}
		return "reports";
	}
}
