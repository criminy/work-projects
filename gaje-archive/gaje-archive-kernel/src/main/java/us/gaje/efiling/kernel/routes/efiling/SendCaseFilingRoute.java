package us.gaje.efiling.kernel.routes.efiling;

import java.io.IOException;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Header;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.springframework.stereotype.Component;

@Component
public class SendCaseFilingRoute extends RouteBuilder {

	public static final String ENDPOINT_SEND_FROM_AGENT = "gaje.sendFromAgent";
	public static final String ENDPOINT_SEND_FROM_ATTORNEY = "gaje.sendFromAttorney";
	public static final String ENDPOINT_SEND_FROM_CLERK = "gaje.sendFromClerk";
	public static final String ENDPOINT_SEND_FROM_JUDGE = "gaje.sendFromJudge";
	public static final String ENDPOINT_SEND = "gaje.sendCaseFiling";
	public static final String ENDPOINT_ERROR = "gaje.error";
	
	private static final String HEADER_ERROR = "gajeError";
	
	public static String LOG(String endpoint) {
		return "log." + endpoint;
	}

	public static String DIRECT(String endpoint) {
		return "direct:" + endpoint;
	}

	public static String JMS(String endpoint) {
		 return "activemq:" + endpoint;
		//return MOCK(endpoint);
	}
	
	
	public static String MOCK(String endpoint) {
		return "mock:" + endpoint;
	}

	private static Predicate caseIsAtAgent() {
		return new Predicate() {

			@Override
			public boolean matches(Exchange exchange) {
				return true;
			}
		};
	}

	private static Predicate caseIsAtAttorney() {
		return new Predicate() {

			@Override
			public boolean matches(Exchange exchange) {
				return false;
			}
		};
	}

	private static Predicate caseIsAtClerk() {
		return new Predicate() {

			@Override
			public boolean matches(Exchange exchange) {
				return false;
			}
		};
	}

	private static Predicate caseIsAtJudge() {
		return new Predicate() {

			@Override
			public boolean matches(Exchange exchange) {
				return false;
			}
		};
	}
	
	

	@Override
	public void configure() throws Exception {
		
		from(DIRECT(LOG(ENDPOINT_ERROR)))			
			.setHeader("file",body())
			.bean(new Logfilewriter());
		
		from(DIRECT(LOG(ENDPOINT_SEND)))
			.setHeader("file",body())
			.bean(new Logfilewriter());

		
		from(DIRECT(ENDPOINT_SEND_FROM_AGENT))
			.log(LoggingLevel.INFO,"us.gaje.log","Sending ${body} from agent");
		
		from(JMS(ENDPOINT_SEND))
			.to(DIRECT(ENDPOINT_SEND));
		
		from(DIRECT(ENDPOINT_SEND))
			.choice()
				.when(caseIsAtAgent())
					.to(DIRECT(ENDPOINT_SEND_FROM_AGENT))
				.when(caseIsAtAttorney())
					.to(DIRECT(ENDPOINT_SEND_FROM_ATTORNEY))
				.when(caseIsAtClerk())
					.to(DIRECT(ENDPOINT_SEND_FROM_CLERK))
				.when(caseIsAtJudge())
					.to(DIRECT(ENDPOINT_SEND_FROM_JUDGE))
				.otherwise()
					.setHeader(HEADER_ERROR,constant("Case can't be sent to."))
					.wireTap(DIRECT(LOG(ENDPOINT_ERROR)));
	}
}
