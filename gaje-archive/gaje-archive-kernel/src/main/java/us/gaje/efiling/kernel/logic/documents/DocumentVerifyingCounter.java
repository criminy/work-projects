package us.gaje.efiling.kernel.logic.documents;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import us.gaje.efiling.dao.DaoManager;
import us.gaje.efiling.model.CaseRecord;
import us.gaje.efiling.model.Court;
import us.gaje.efiling.model.documents.Document;
import us.gaje.efiling.model.documents.DocumentVersion;
import us.gaje.efiling.repositories.CaseRepository;
import us.gaje.efiling.repositories.CourtRepository;


public class DocumentVerifyingCounter {
	public int valid = 0;
	public int invalid = 0;	
	public Map<String, Map<String,Set<String>>> badDocuments = new HashMap<String, Map<String,Set<String>>>();
	
	public void addBadDocument(Document d)
	{
		String courtUuid = d.getCaseRecord().getCourt().getUuid();
		if(!badDocuments.containsKey(courtUuid))
			badDocuments.put(courtUuid,new HashMap<String, Set<String>>());
		
		String caseId = d.getCaseRecord().getUuid();
		if(!badDocuments.get(courtUuid).containsKey(caseId))
			badDocuments.get(courtUuid).put(caseId,new HashSet<String>());
				
		badDocuments.get(courtUuid).get(caseId).add(d.getUuid());
	}
		
	public void printReport(DaoManager daoManager,CourtRepository courtRepository,CaseRepository caseRepository)
	{
//		System.out.println("----------------------");
//		System.out.println("Valid: " + valid);
//		System.out.println("Invalid: " + invalid);
//		System.out.println("Invalid case count " + badDocuments.size());
//		System.out.println("----------------------");
		
		System.out.println("DOCUMENT CVNUMBER OCSS_NUMBER DOCSTATUS");
		for(String strCourt : this.badDocuments.keySet())
		{
			for(String caseId : this.badDocuments.get(strCourt).keySet())
			{
				for(String documentId : this.badDocuments.get(strCourt).get(caseId))
				{
					Document d = daoManager.get(Document.class).read(documentId);
					
					List<DocumentVersion> dv = d.getDocumentVersions();
					if(dv.size() == 0)
					{
						System.out.println("NO VERSIONS : " );
					}else{
						CaseRecord cr = d.getCaseRecord();
						String caseTrackingId = (cr.getCaseTrackingId() == null || cr.getCaseTrackingId().equalsIgnoreCase("") ? "NONE" : cr.getCaseTrackingId() );
						System.out.println(
								String.format("%s_V%s %s %s %s",
										d.getUuid(),
										Integer.toString(dv.iterator().next().getVersion()),
										caseTrackingId,
										d.getCaseRecord().getOcssNumber(),
										d.getStatus().getDescription().toUpperCase()));
					}															
				}
			}
		}
		
		/*
		for(String strcourt : this.badDocuments.keySet())
		{
			Court c = courtRepository.findOne(strcourt);
			
			System.out.println(c.getName());
			for(String caseId : this.badDocuments.get(strcourt).keySet())
			{
				CaseRecord cr = caseRepository.findOne(caseId);
				System.out.println("\t " + cr.getOcssNumber() );
				for(String docId : this.badDocuments.get(strcourt).get(caseId))
				{
					Document d = daoManager.get(Document.class).read(docId);
					System.out.println("\t\t " + d.getTitle());
				}				
			}
		}
		*/
		
	}
	
}
