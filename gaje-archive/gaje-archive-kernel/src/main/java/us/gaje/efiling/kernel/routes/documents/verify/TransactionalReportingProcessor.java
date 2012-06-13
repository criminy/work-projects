package us.gaje.efiling.kernel.routes.documents.verify;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import us.gaje.efiling.dao.DaoManager;
import us.gaje.efiling.kernel.logic.documents.DocumentVerifyingCounter;
import us.gaje.efiling.repositories.CaseRepository;
import us.gaje.efiling.repositories.CourtRepository;

@Component
@Transactional
public class TransactionalReportingProcessor implements Processor{

	@Autowired private DaoManager daoManager;
	@Autowired private CaseRepository caseRepository;
	@Autowired private CourtRepository courtRepository;

	
	@Transactional
	@Override
	public void process(Exchange exchange) throws Exception {
		DocumentVerifyingCounter counter = exchange.getIn().getHeader("counter",DocumentVerifyingCounter.class);
		counter.printReport(daoManager,courtRepository,caseRepository);
	}
}
