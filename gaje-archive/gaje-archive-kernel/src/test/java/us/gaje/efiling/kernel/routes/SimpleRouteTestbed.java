package us.gaje.efiling.kernel.routes;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.apache.camel.Body;
import org.apache.camel.CamelContext;
import org.apache.camel.Handler;
import org.apache.camel.Header;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.direct.DirectComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.junit.Test;

import us.gaje.efiling.kernel.logic.documents.IdentitySplitter;
import us.gaje.efiling.model.documents.Document;






class Route1 extends RouteBuilder
{

	
	
	@Override
	public void configure() throws Exception {
		
		from("direct:test")
			.inOnly()
			.setBody(simple("null"))
			.setHeader("documentSize",constant(60000))
			.setHeader("documentStepsize",constant(3000))
			.loop().groovy("(int) Math.ceil(request.headers.documentSize / request.headers.documentStepsize)")
				.setHeader("rangeStart").groovy("request.headers.documentStepsize * exchange.properties.CamelLoopIndex")			
				.setHeader("rangeEnd").groovy("request.headers.documentStepsize * exchange.properties.CamelLoopIndex + request.headers.documentStepsize")
				.log(LoggingLevel.WARN,"property.CamelLoopIndex")
				.log(LoggingLevel.WARN,"headers.rangeStart")
				.log(LoggingLevel.WARN,"headers.rangeEnd")
				.transform().method(new SimpleRouteTestbed.GenerateList())
				.split().method(new IdentitySplitter<Object>())
					.to("direct:test2")					
				.end()
				.bean(new SimpleRouteTestbed.B1(),"run")
			.end();
		
		from("direct:test2")
			.transform(simple("B"))
			.bean(new SimpleRouteTestbed.B1(),"runI")
			.to("direct:test3");
		
		
		from("direct:test3")
				.transform().method(new SimpleRouteTestbed.GenerateList2())
				.split()
					.method(new IdentitySplitter<Object>())
						.transform(simple("A"))
						.bean(new SimpleRouteTestbed.B1(),"runII");
		
				
	}
	
}

public class SimpleRouteTestbed {

	public static class GenerateList
	{
		@Handler
		public List<String> getList(@Header("rangeStart") int start,@Header("documentStepsize") int size)
		{
			List<String> lx = new ArrayList<String>(size);
			for(int i = start; i!=size+start;i++)
			{
				lx.add(Integer.toString(i));
			}
			return lx;
		}
	}
	

	public static class GenerateList2
	{
		@Handler
		public List<String> getList()
		{
			List<String> lx = new ArrayList<String>(10);
			for(int i = 0; i!=10;i++)
			{
				lx.add(Integer.toString(i));
			}
			return lx;
		}
	}
	
	
	public static class B1
	{

		static int count = 0;
		static int icount = 0;
		static int iicount = 0;
		
		@Handler
		public void run(@Header("rangeStart") int rangeStart,@Header("rangeEnd") int rangeEnd)
		{
			count++;
		}
		
		@Handler
		public void runI(@Body String val,@Header("rangeStart") int rangeStart,@Header("rangeEnd") int rangeEnd)
		{
			//System.out.println(val);
			icount++;
		}
		
		@Handler
		public void runII()
		{
			iicount++;
		}
		
	}
	
	CamelContext ctx;
	
	@Test
	public void test1() throws Exception
	{
		/*
		ctx = new DefaultCamelContext();

		ctx.addComponent("direct",new DirectComponent());		
		ctx.addRoutes(new Route1());
		
		ctx.start();
		
		

		B1.count = 0;
		ctx.createProducerTemplate().sendBody("direct:test","test"); 
		Assert.assertEquals(20,B1.count);
		
		Assert.assertEquals(60000,B1.icount);
		Assert.assertEquals(60000*10,B1.iicount);
		*/
	}
	
}
