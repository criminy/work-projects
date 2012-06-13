package us.gaje.efiling.api;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import us.gaje.efiling.model.CaseRecord;

import static us.gaje.efiling.api.RoutingAPI.*;

@Component
public class EFilingFacadeImpl implements EFilingFacade {
	
	private ProducerTemplate producer;
	
	@Autowired
	public EFilingFacadeImpl(CamelContext camelContext) {
		producer = camelContext.createProducerTemplate();
	}
	
	@Override
	public void send(CaseRecord cr,String comment)
	{
		producer.sendBody(DIRECT(ENDPOINT_SEND),cr.getUuid());
	}
	
	@Override
	public void reject(CaseRecord cr,String comment)
	{
		producer.sendBody(DIRECT(ENDPOINT_REJECT),cr.getUuid());
	}
	
	
	
}
