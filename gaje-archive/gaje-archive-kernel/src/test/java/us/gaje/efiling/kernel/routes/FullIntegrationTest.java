package us.gaje.efiling.kernel.routes;

import java.io.FileInputStream;
import java.sql.Connection;
import java.util.Date;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import junit.framework.Assert;

import org.apache.camel.CamelContext;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import us.gaje.efiling.kernel.routes.efiling.SendCaseFilingRoute;
import us.gaje.efiling.model.CaseRecord;
import us.gaje.efiling.model.Court;
import us.gaje.efiling.repositories.CaseRepository;

/**
 * In this test, we:
 *  1) Agent:
 * 		a) Upload a case into the EFiLing system
 *  	b) Add new documents into the case
 *  	c) Sign the required case documents
 *      d) set the court date
 *      e) Send
 *  2) Attorney:
 *      a) Read incoming cases list, grab the first one
 *      b) Reject the case
 *  3) Agent:
 *      a) Check rejected list, grab first one
 *      b) Modify the case
 *      c) send the case
 *  4) Attorney: 
 *      a) Read incoming cases list, grab the first one
 *      b) Sign the docuemnts
 *      c) Send the case
 *  5) Clerk:
 *       
 *  	
 * @author artripa
 *
 */
@ContextConfiguration(locations="classpath:/full-integration-test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration
public class FullIntegrationTest  {

	@Resource private CamelContext camelContext;	
	@Resource private CaseRepository caseRepository;
	@PersistenceContext private EntityManager entityManager;
	
	@org.junit.Before
	public void startup() throws Exception
	{
		camelContext.start();
	}
	
	@Test
	public void testFullIntegration() throws Exception 
	{	
		CaseRecord cr = caseRepository.findAll().get(0);
		camelContext.createProducerTemplate().sendBody(SendCaseFilingRoute.DIRECT(SendCaseFilingRoute.ENDPOINT_SEND),cr.getUuid());
	}
	
}
