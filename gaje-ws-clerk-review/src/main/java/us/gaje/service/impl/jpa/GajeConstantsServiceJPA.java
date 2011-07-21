package us.gaje.service.impl.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import us.gaaoc.framework.model.CaseRecordEventType;
import us.gaaoc.framework.model.CaseRecordStatus;
import us.gaaoc.framework.model.Court;
import us.gaaoc.framework.model.CourtEventType;
import us.gaaoc.framework.model.DocumentInstance;
import us.gaaoc.framework.model.DocumentStatus;
import us.gaje.service.GajeConstantsService;

@Component("gajeConstantsService")
@Transactional
public class GajeConstantsServiceJPA implements GajeConstantsService{
	
	@PersistenceContext	private EntityManager em;


	@Transactional
	public CaseRecordStatus getCaseRecordStatus(int id) {
		return em.find(CaseRecordStatus.class,id);
	}

	@Transactional
	public DocumentStatus getDocumentStatus(int id)
	{
		return em.find(DocumentStatus.class,id);
	}
	
	@Transactional
	public CourtEventType getCourtEventType(int id)
	{
		return em.find(CourtEventType.class,id);
	}
	
	@Transactional
	public CaseRecordEventType getCaseRecordEventType(int id)
	{
		return em.find(CaseRecordEventType.class,id);
	}

	@Transactional
	public DocumentInstance getDocumentInstanceByLocalCode(String localCode,
			Court court) {
		//TODO: this assumes that every document instance has a UNIQUE docLocalCode PER court
		//TODO: catch multiple result exceptions, log them
		return (DocumentInstance) em.createQuery("from DocumentInstance di where di.docLocalCode.code = :type and di.court = :court")
			.setParameter("type",localCode)
			.setParameter("court",court)
			.getSingleResult();
	}
	
	
	
	
	
	
	public void setEm(EntityManager em) {
		this.em = em;
	}
}
