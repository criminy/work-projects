package us.gaje.efiling.kernel.logic.documents;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.camel.Body;
import org.apache.camel.Handler;
import org.apache.camel.Header;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import us.gaje.efiling.kernel.routes.documents.archival.ArchiveRouterConstants;

@Component
public class GetImportedDocuments {

	@PersistenceContext
	private EntityManager entityManager;

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Handler
	@Transactional
	@SuppressWarnings("unchecked")
	public List<String> getImportedDocuments(@Body String courtUuid,
			@Header(value = "start") int start, @Header(value = "size") int size) {
		List<String> docs;

		if (courtUuid == null || courtUuid.equalsIgnoreCase("")) {
			docs = entityManager
					.createNativeQuery(
							"select d.uuid from documents d where d.statusCode = 2")
					.setFirstResult(start).setMaxResults(size).getResultList();
			System.out.println(String.format("range:[%d,%d] size: %d", start,
					start + size, docs.size()));
		} else {
			docs = entityManager
					.createNativeQuery(
							"select d.uuid from documents d,caseRecord cr where d.statusCode = 2 and cr.courtUuid = :court and cr.uuid = d.caseRecordUuid")
					.setParameter("court", courtUuid).setFirstResult(start)
					.setMaxResults(size).getResultList();
		}

		return docs;
	}

	@Handler
	@Transactional
	public int getImportedDocumentCount(@Body String courtUuid) {

		System.out.println("[getImportedDocumentCount] Counting...");
		if (courtUuid == null || courtUuid.equalsIgnoreCase("")) {
			int i = ((BigInteger) entityManager
					.createNativeQuery(
							"select count(d.uuid) from documents d where d.statusCode = 2")
					.getSingleResult()).intValue();
			System.out.println("[getImportedDocumentCount] finished..." + i);
			return i;
		} else {
			return ((BigInteger) entityManager
					.createNativeQuery(
							"select count(d.uuid) from documents d,caseRecord cr where d.statusCode = 2 and cr.courtUuid = :court and cr.uuid = d.caseRecordUuid")
					.getSingleResult()).intValue();
		}

	}

	@Handler
	@Transactional
	public int getImportedDocumentsByDateCount(
			@Header(ArchiveRouterConstants.COURT_ID_HEADER) String courtUuid,@Header(ArchiveRouterConstants.DATE_HEADER) String date) {
		if (courtUuid == null || courtUuid.equalsIgnoreCase("")) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

			System.out.println("[getImportedDocumentsByDateCount] Counting...");
			int i = ((BigInteger) entityManager
					.createNativeQuery(
							"select count(d.uuid) from documents d where d.statusCode = 2 and d.receivedDateTime < :date")
					.setParameter("date",date)
					.getSingleResult()).intValue();
			System.out.println("[getImportedDocumentsByDateCount] finished..."
					+ i);
			return i;
		} else {
			return ((BigInteger) entityManager
					.createNativeQuery(
							"select count(d.uuid) from documents d,caseRecord cr where d.statusCode = 2 and cr.courtUuid = :court and cr.uuid = d.caseRecordUuid and d.receivedDateTime < :date")
					.setParameter("date",date)
					.setParameter("court", courtUuid).getSingleResult()).intValue();
		}
	}

	@Handler
	@Transactional
	@SuppressWarnings("unchecked")
	public List<String> getImportedDocumentsByDate(
			@Header(ArchiveRouterConstants.COURT_ID_HEADER) String courtUuid,
			@Header(ArchiveRouterConstants.DATE_HEADER) String date,
			@Header(value = "start") int start, @Header(value = "size") int size) {
		List<String> docs;

		if (courtUuid == null || courtUuid.equalsIgnoreCase("")) {
			System.out.println("[getImportedDocumentsByDate] start");
			docs = entityManager
					.createNativeQuery(
							"select d.uuid from documents d where d.statusCode = 2 and d.receivedDateTime < :date")
							.setParameter("date",date)
					.setFirstResult(start).setMaxResults(size).getResultList();
			System.out.println("[getImportedDocumentsByDate] end..."
					+ docs.size());

		} else {
			docs = entityManager
					.createNativeQuery(
							"select d.uuid from documents d,caseRecord cr where d.statusCode = 2 and cr.courtUuid = :court and cr.uuid = d.caseRecordUuid and d.receivedDateTime < :date")
					.setParameter("date", date)
					.setParameter("court", courtUuid).setFirstResult(start)
					.setMaxResults(size).getResultList();
		}

		return docs;
	}
}
