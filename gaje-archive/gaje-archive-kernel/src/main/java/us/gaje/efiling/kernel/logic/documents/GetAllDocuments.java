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
public class GetAllDocuments {

	@PersistenceContext
	private EntityManager entityManager;

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Handler
	@Transactional
	@SuppressWarnings("unchecked")
	public List<String> getAllDocuments(@Body String courtUuid,
			@Header(value = "start") int start, @Header(value = "size") int size) {
		List<String> docs;

		if (courtUuid == null || courtUuid.equalsIgnoreCase("")) {
			docs = entityManager
					.createNativeQuery(
							"select d.uuid from documents d")
					.setFirstResult(start).setMaxResults(size).getResultList();
			System.out.println(String.format("range:[%d,%d] size: %d", start,
					start + size, docs.size()));
		} else {
			docs = entityManager
					.createNativeQuery(
							"select d.uuid from documents d,caseRecord cr cr.courtUuid = :court and cr.uuid = d.caseRecordUuid")
					.setParameter("court", courtUuid).setFirstResult(start)
					.setMaxResults(size).getResultList();
		}

		return docs;
	}

	@Handler
	@Transactional
	public int getAllDocumentsCount(@Body String courtUuid) {

		System.out.println("[getAllDocumentsCount] Counting...");
		if (courtUuid == null || courtUuid.equalsIgnoreCase("")) {
			int i = ((BigInteger) entityManager
					.createNativeQuery(
							"select count(d.uuid) from documents d")
					.getSingleResult()).intValue();
			System.out.println("[getAllDocumentsCount] finished..." + i);
			return i;
		} else {
			return ((BigInteger) entityManager
					.createNativeQuery(
							"select count(d.uuid) from documents d,caseRecord cr where cr.courtUuid = :court and cr.uuid = d.caseRecordUuid")
					.getSingleResult()).intValue();
		}

	}

	@Handler
	@Transactional
	public int getAllDocumentsCountByDate(
			@Header(ArchiveRouterConstants.COURT_ID_HEADER) String courtUuid,@Header(ArchiveRouterConstants.DATE_HEADER) String date) {
		if (courtUuid == null || courtUuid.equalsIgnoreCase("")) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

			System.out.println("[getAllDocumentsCountByDate] Counting...");
			int i = ((BigInteger) entityManager
					.createNativeQuery(
							"select count(d.uuid) from documents d where d.receivedDateTime < :date")
					.setParameter("date",date)
					.getSingleResult()).intValue();
			System.out.println("[getAllDocumentsCountByDate] finished..."
					+ i);
			return i;
		} else {
			return ((BigInteger) entityManager
					.createNativeQuery(
							"select count(d.uuid) from documents d,caseRecord cr where cr.courtUuid = :court and cr.uuid = d.caseRecordUuid and d.receivedDateTime < :date")
					.setParameter("date",date)
					.setParameter("court", courtUuid).getSingleResult()).intValue();
		}
	}

	@Handler
	@Transactional
	@SuppressWarnings("unchecked")
	public List<String> getAllDocumentsByDate(
			@Header(ArchiveRouterConstants.COURT_ID_HEADER) String courtUuid,
			@Header(ArchiveRouterConstants.DATE_HEADER) String date,
			@Header(value = "start") int start, @Header(value = "size") int size) {
		List<String> docs;

		if (courtUuid == null || courtUuid.equalsIgnoreCase("")) {
			System.out.println("[getAllDocumentsByDate] start");
			docs = entityManager
					.createNativeQuery(
							"select d.uuid from documents d where d.receivedDateTime < :date")
							.setParameter("date",date)
					.setFirstResult(start).setMaxResults(size).getResultList();
			System.out.println("[getAllDocumentsCountByDate] end..."
					+ docs.size());

		} else {
			docs = entityManager
					.createNativeQuery(
							"select d.uuid from documents d,caseRecord cr where cr.courtUuid = :court and cr.uuid = d.caseRecordUuid and d.receivedDateTime < :date")
					.setParameter("date", date)
					.setParameter("court", courtUuid).setFirstResult(start)
					.setMaxResults(size).getResultList();
		}

		return docs;
	}
}
