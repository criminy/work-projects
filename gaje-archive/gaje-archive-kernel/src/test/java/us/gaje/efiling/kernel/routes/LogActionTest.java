package us.gaje.efiling.kernel.routes;

import org.apache.camel.CamelContext;
import org.apache.camel.component.direct.DirectComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.junit.Test;

import us.gaje.efiling.kernel.routes.efiling.SendCaseFilingRoute;

public class LogActionTest {

	@Test
	public void testLogAction() throws Exception 
	{
		CamelContext context = new DefaultCamelContext();
		context.addRoutes(new SendCaseFilingRoute());		
		context.addComponent("direct",new DirectComponent());
		context.addComponent("activemq",new DirectComponent());
		context.start();
		
		context.createProducerTemplate().sendBody(SendCaseFilingRoute.DIRECT(SendCaseFilingRoute.ENDPOINT_SEND),"uuid1");
	}
	
}
