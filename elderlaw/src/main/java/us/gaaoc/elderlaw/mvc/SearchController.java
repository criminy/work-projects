package us.gaaoc.elderlaw.mvc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import us.gaaoc.elderlaw.model.Search;

@Controller
@RequestMapping(value= "/search")
public class SearchController
{
	@RequestMapping(method=RequestMethod.GET)
	public String getSearchForm(Model model) throws IOException, FileNotFoundException
	{
		model.addAttribute(new Search());
		model.addAllAttributes(dropDowns());

		return "search";
	}
	
	protected Map dropDowns()
	{
		Map dropDowns = new HashMap();
		
		Map<String,String> county = new LinkedHashMap<String,String>();
		//county.put("value", "displayName");
		county.put("", "");
		county.put("appling", "Appling County");
		county.put("athens-clarke", "Athens-Clarke County");
		county.put("atkinson", "Atkinson County");
		county.put("augusta-richmond", "Augusta-Richmond County");
		county.put("bacon", "Bacon County");
		county.put("baker", "Baker County");
		county.put("baldwin", "Baldwin County");
		county.put("banks", "Banks County");
		county.put("barrow", "Barrow County");
		county.put("bartow", "Bartow County");
		county.put("benHill", "Ben Hill County");
		county.put("berrien", "Berrien County");
		county.put("bibb", "Bibb County");
		county.put("bleckley", "Bleckley County");
		county.put("brantley", "Brantley County");
		county.put("brooks", "Brooks County");
		county.put("bryan", "Bryan County");
		county.put("bulloch", "Bulloch County");
		county.put("burke", "Burke County");
		county.put("butts", "Butts County");
		county.put("calhoun", "Calhoun County");
		county.put("camden", "Camden County");
		county.put("candler", "Candler County");
		county.put("carroll", "Carroll County");
		county.put("catoosa", "Catoosa County");
		county.put("charlton", "Charlton County");
		county.put("chatham", "Chatham County");
		county.put("chattahoochee", "Chattahoochee County");
		county.put("chattooga", "Chattooga County");
		county.put("cherokee", "Cherokee County");
		county.put("clarke", "Clarke County");
		county.put("clay", "Clay County");
		county.put("clayton", "Clayton County");
		county.put("clinch", "Clinch County");
		county.put("cobb", "Cobb County");
		county.put("coffee", "Coffee County");
		county.put("colquitt", "Colquitt County");
		county.put("columbia", "Columbia County");
		county.put("columbus-muscogee", "Columbus-Muscogee County");
		county.put("cook", "Cook County");
		county.put("coweta", "Coweta County");
		county.put("crawford", "Crawford County");
		county.put("crisp", "Crisp County");
		county.put("dade", "Dade County");
		county.put("dawson", "Dawson County");
		county.put("dekalb", "DeKalb County");
		county.put("decatur", "Decatur County");
		county.put("dodge", "Dodge County");
		county.put("dooly", "Dooly County");
		county.put("dougherty", "Dougherty County");
		county.put("douglas", "Douglas County");
		county.put("early", "Early County");
		county.put("echols", "Echols County");
		county.put("effingham", "Effingham County");
		county.put("elbert", "Elbert County");
		county.put("emanuel", "Emanuel County");
		county.put("evans", "Evans County");
		county.put("fannin", "Fannin County");
		county.put("fayette", "Fayette County");
		county.put("floyd", "Floyd County");
		county.put("forsyth", "Forsyth County");
		county.put("franklin", "Franklin County");
		county.put("fulton", "Fulton County");
		county.put("gilmer", "Gilmer County");
		county.put("glascock", "Glascock County");
		county.put("glynn", "Glynn County");
		county.put("gordon", "Gordon County");
		county.put("grady", "Grady County");
		county.put("greene", "Greene County");
		county.put("gwinnett", "Gwinnett");
		county.put("habersham", "Habersham County");
		county.put("hall", "Hall County");
		county.put("hancock", "Hancock County");
		county.put("haralson", "Haralson County");
		county.put("harris", "Harris County");
		county.put("hart", "Hart County");
		county.put("heard", "Heard County");
		county.put("henry", "Henry County");
		county.put("houston", "Houston County");
		county.put("irwin", "Irwin County");
		county.put("jackson", "Jackson County");
		county.put("jasper", "Jasper County");
		county.put("jeffDavis", "Jeff Davis County");
		county.put("jefferson", "Jefferson County");
		county.put("jenkins", "Jenkins County");
		county.put("johnson", "Johnson County");
		county.put("jones", "Jones County");
		county.put("lamar", "Lamar County");
		county.put("lanier", "Lanier County");
		county.put("laurens", "Laurens County");
		county.put("lee", "Lee County");
		county.put("liberty", "Liberty County");
		county.put("lincoln", "Lincoln County");
		county.put("long", "Long County");
		county.put("lowndes", "Lowndes County");
		county.put("lumpkin", "Lumpkin County");
		county.put("macon", "Macon County");
		county.put("madison", "Madison County");
		county.put("marion", "Marion County");
		county.put("mcduffie", "McDuffie County");
		county.put("mcintosh", "McIntosh County");
		county.put("meriwether", "Meriwether County");
		county.put("miller", "Miller County");
		county.put("mitchell", "Mitchell County");
		county.put("monroe", "Monroe County");
		county.put("montgomery", "Montgomery County");
		county.put("morgan", "Morgan County");
		county.put("murray", "Murray County");
		county.put("muscogee", "Muscogee County");
		county.put("newton", "Newton County");
		county.put("oconee", "Oconee County");
		county.put("oglethorpe", "Oglethorpe County");
		county.put("paulding", "Palding County");
		county.put("peach", "Peach County");
		county.put("pickens", "Pickens County");
		county.put("pierce", "Pierce County");
		county.put("pike", "Pike County");
		county.put("polk", "Polk County");
		county.put("pulaski", "Pulaski County");
		county.put("putnam", "Putnam County");
		county.put("quitman", "Quitman County");
		county.put("rabun", "Rabun County");
		county.put("randolph", "Randolph County");
		county.put("richmond", "Richmond County");
		county.put("rockdale", "Rockdale County");
		county.put("schley", "Schley County");
		county.put("screven", "Screven County");
		county.put("seminole", "Seminole County");
		county.put("spalding", "Spalding County");
		county.put("stephens", "Stephens County");
		county.put("stewart", "Stewart County");
		county.put("sumter", "Sumter County");
		county.put("talbot", "Talbot County");
		county.put("taliaferro", "Taliaferro County");
		county.put("tattnall", "Tattnall County");
		county.put("taylor", "Taylor County");
		county.put("telfair", "Telfair County");
		county.put("terrell", "Terrell County");
		county.put("thomas", "Thomas County");
		county.put("tift", "Tift County");
		county.put("toombs", "Toombs County");
		county.put("towns", "Towns County");
		county.put("treutien", "Treutien County");
		county.put("troup", "Troup County");
		county.put("turner", "Turner County");
		county.put("twiggs", "Twiggs County");
		county.put("union", "Union County");
		county.put("upson", "Upson County");
		county.put("walker", "Walker County");
		county.put("walton", "Walton County");
		county.put("ware", "Ware County");
		county.put("warren", "Warren County");
		county.put("washington", "Washington County");
		county.put("wayne", "Wayne County");
		county.put("webster", "Webster County");
		county.put("wheeler", "Wheeler County");
		county.put("white", "White County");
		county.put("whitfield", "Whitfield County");
		county.put("wilcox", "Wilcox County");
		county.put("wilkes", "Wilkes County");
		county.put("wilkinson", "Wilkinson County");
		county.put("worth", "Worth County");
		
		dropDowns.put("counties", county);
		
		Map<String,String> area = new LinkedHashMap<String,String>();
		//area.put("value", "displayName");
		area.put("", "");
		area.put("advancedDirectives", "Advanced Directives");
		area.put("bankruptcy", "Bankruptcy");
		area.put("collections", "Collections");
		area.put("contracts", "Contracts");
		area.put("consumer", "Consumer");
		area.put("familyLaw", "Family Law");
		area.put("guardianship", "Guardianship");
		area.put("loans", "Loans");
		area.put("powersOfAttorney", "Powers of Attorney");
		area.put("probate", "Probate");
		area.put("trustsEstates", "Trusts and Estates");
		area.put("wills", "Wills");

		dropDowns.put("areas", area);
		
		return dropDowns;
	}
}
