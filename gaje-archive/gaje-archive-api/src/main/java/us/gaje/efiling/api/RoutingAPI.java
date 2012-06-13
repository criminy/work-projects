package us.gaje.efiling.api;

public class RoutingAPI {

	public static final String ENDPOINT_SEND_FROM_AGENT = "gaje.sendFromAgent";
	public static final String ENDPOINT_SEND_FROM_ATTORNEY = "gaje.sendFromAttorney";
	public static final String ENDPOINT_SEND_FROM_CLERK = "gaje.sendFromClerk";
	public static final String ENDPOINT_SEND_FROM_JUDGE = "gaje.sendFromJudge";
	public static final String ENDPOINT_SEND = "gaje.sendCaseFiling";
	
	public static final String ENDPOINT_REJECT = "gaje.rejectCaseFiling";
	
	public static final String ENDPOINT_ERROR = "gaje.error";
	
	
	
	public static final String HEADER_ERROR = "gajeError";
	
	public static String LOG(String endpoint) {
		return "log." + endpoint;
	}

	public static String DIRECT(String endpoint) {
		return "direct:" + endpoint;
	}

	public static String JMS(String endpoint) {
		 return "activemq:" + endpoint;
	}
	
	public static String MOCK(String endpoint) {
		return "mock:" + endpoint;
	}

	
}
