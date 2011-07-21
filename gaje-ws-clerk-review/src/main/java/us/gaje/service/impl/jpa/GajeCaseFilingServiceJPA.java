package us.gaje.service.impl.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import us.gaaoc.framework.model.CaseRecord;
import us.gaje.efiling.ws.clerkreview.GajeCaseFiling;
import us.gaje.service.GajeCaseFilingService;
import us.gaje.service.GajeConstantsService;
import us.gaje.service.exceptions.ObjectNotFound;

@Component("gajeCaseFilingService")
public class GajeCaseFilingServiceJPA implements GajeCaseFilingService{

	@PersistenceContext	private EntityManager entityManager; 
	@Autowired private DomToWebserviceConverterJPA domToWebserviceConverter;	
	@Autowired private GajeConstantsService gajeConstantsService;
	
	public List<GajeCaseFiling> byCourtIdAndStatus(String courtId, int status) {
		String getCaseRecordsByStatus = 
			"from CaseRecord cr where cr.status.code = :statusCode and cr.court.id = :courtID and cr.deletedFilingFlag = 0 and cr.clearFilingFlag = 0";
				
		@SuppressWarnings("unchecked")
		List<CaseRecord> crList = entityManager.createQuery(getCaseRecordsByStatus)
			.setParameter("courtID",courtId)
			.setParameter("statusCode",status)
			.getResultList();
		
		List<GajeCaseFiling> cflist = new ArrayList<GajeCaseFiling>(crList.size()+1);
		
		for(CaseRecord cr : crList) {
			cflist.add(fromCaseRecord(cr));
		}
		return cflist;		
	}

	public GajeCaseFiling fromCaseRecord(CaseRecord cr)
	{
		// Manually wire in the beans to avoid needing load-time or compile-time weaving.
		GajeCaseFilingJPA cf = new GajeCaseFilingJPA();
		cf.setCaseRecord(cr);
		cf.setDomToWebserviceConverter(domToWebserviceConverter);
		cf.setGajeConstantsService(gajeConstantsService);
		cf.setEm(entityManager);
		return cf;
	}

	public GajeCaseFiling find(String id) {
		CaseRecord cr = entityManager.find(CaseRecord.class,id);
		if(cr == null)
		{
			throw new ObjectNotFound("Case Filing " + id + " not found");
		}
		return fromCaseRecord(cr);
	}
	
}
