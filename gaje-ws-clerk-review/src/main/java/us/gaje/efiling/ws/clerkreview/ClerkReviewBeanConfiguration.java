/**
 * 
 */
package us.gaje.efiling.ws.clerkreview;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import us.gaaoc.gajews.ClerkReview;
import us.gaje.efiling.ws.clerkreview.util.ProxyBuilder;
import us.gaje.model.service.CaseRecordService;
import us.gaje.service.CourtAccessDefinitions;
import us.gaje.service.GajeCaseFilingService;
import us.gaje.service.GajeConstantsService;
import us.gaje.service.web.SessionService;

/**
 * 
 * This is the workflow definition of our webservices, using 
 * Springs JavaConfig support.
 * 
 * @author artripa
 *
 */

// This was actually a huge hassle and, 
// in the future, bean configuration like this should
// be done in the XML file.
//		-- Shawn Artrip
//
// Well now that I'm having to add more in the stack, it's a lot more simpler. Still,
// with a layered proxy approach the way the code is designed makes it very unclear as
// as to which beans are called where in the stack. Something more literal would be better.

// a better, more literal design for this?
//
//	ProxyFactory factory = new ProxyFactory();
//	factory.build().
//		.proxy(log())
//		.proxy(handleError())
//		.proxy(handleSecurity())
//		.proxy(checkCaseStatuses())
//		.impl(clerkReviewImpl());

// implemented proxyBuilder as explained above. Seems nice.
// 	- Shawn Artrip

@Configuration
public class ClerkReviewBeanConfiguration {
	
	@Autowired GajeConstantsService constantsService;
	@Autowired GajeCaseFilingService caseFilingService;
	@Autowired SessionService sessionService;
	@Autowired CourtAccessDefinitions courtAccessDefinitions;
	
	@Bean(name="clerkReviewService")
	public ClerkReview clerkReviewService()
	{		
		
		// we build a chain by taking the clerkreview interface
		// and adding a bunch of proxies around it. The first proxy is the first
		// in the stack and they go in their described order.
		ProxyBuilder proxyBuilder = new ProxyBuilder();
		return proxyBuilder.build(ClerkReview.class)
			.proxy(log())
			.proxy(handleError())
			.proxy(handleSecurity())
			.proxy(checkCaseStatuses())
			.impl(clerkReviewImpl());
	}
	
	@Bean
	public ClerkReview checkCaseStatuses()
	{
		ClerkReviewCaseStatusProxy proxy = new ClerkReviewCaseStatusProxy();
		proxy.setGajeCaseFilingService(this.caseFilingService);
		return proxy;
	}
	
	@Bean
	public ClerkReview clerkReviewImpl()
	{
		ClerkReviewImpl i = new ClerkReviewImpl();
		i.setGajeCaseFilingService(caseFilingService);
		i.setGajeConstantsService(constantsService);			
		return i;
	}
	
	@Bean
	public ClerkReview handleSecurity()
	{
		ClerkReviewSecurityProxy proxy = new ClerkReviewSecurityProxy();		
		proxy.setGajeCaseFilingService(this.caseFilingService);
		proxy.setCourtAccessDefinitions(courtAccessDefinitions);
		return proxy;
	}

	@Bean
	public ClerkReview handleError()
	{
		ClerkReviewErrorHandlingProxy proxy = new ClerkReviewErrorHandlingProxy();		
		proxy.setSessionService(sessionService);
		return proxy;
	}

	@Bean
	public ClerkReview log()
	{
		ClerkReviewLoggingProxy proxy = new ClerkReviewLoggingProxy();
		proxy.setGajeCaseFilingService(caseFilingService);	
		proxy.setLevel("nolevel");
		return proxy;
	}
	
}
