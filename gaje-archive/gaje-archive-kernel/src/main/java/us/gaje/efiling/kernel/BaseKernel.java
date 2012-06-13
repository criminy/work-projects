package us.gaje.efiling.kernel;

import java.util.Date;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import org.springframework.transaction.annotation.Transactional;

import us.gaje.efiling.api.SendCase;
import us.gaje.efiling.api.UserEvent;
import us.gaje.efiling.api.business.CaseIsAtAgent;
import us.gaje.efiling.api.business.CaseIsAtAttorney;
import us.gaje.efiling.api.business.CaseIsAtClerk;
import us.gaje.efiling.api.business.CourtIs;
import us.gaje.efiling.api.business.UserIs;
import us.gaje.efiling.dao.DaoManager;
import us.gaje.efiling.model.Attorney;
import us.gaje.efiling.model.CaseRecord;
import us.gaje.efiling.model.Event;
import us.gaje.efiling.model.Person;
import us.gaje.efiling.model.documents.Document;
import us.gaje.efiling.model.enums.CaseStatus;
import us.gaje.efiling.model.enums.Counties;
import us.gaje.efiling.model.enums.DocumentStatus;
import us.gaje.efiling.model.enums.EventType;
import us.gaje.efiling.model.enums.Roles;

public abstract class BaseKernel extends RouteBuilder
{
	protected Predicate courtIs(final Counties county) {
		return new Predicate() {
			
			@Override
			public boolean matches(Exchange exchange) {
				return new CourtIs(getDaoManager(),county).courtIs(exchange.getIn().getBody(UserEvent.class));
			}
			
			@Override
			public String toString() {
				return new CourtIs(getDaoManager(),county).toString();
			}
		};
	}
	
	public abstract DaoManager getDaoManager();
	
	protected Predicate and(final Predicate...predicates)
	{
		return new Predicate() {
			
			@Override
			public boolean matches(Exchange arg0) {				
				for(Predicate pred : predicates)
				{
					if(!pred.matches(arg0)) return false;					
				}
				return true;
			}
			
			@Override
			public String toString() {
				StringBuilder buf = new StringBuilder();
				for(Predicate pred : predicates)
				{
					buf.append(pred.toString() + " && ");
				}
				
				String str = buf.toString();
				if(str.endsWith("&&")) str = str.substring(0,str.length()-2);
				return "[" + str + "]";							
			}
			
		};		
	}

	
	protected Processor updateNonImportedDocumentStatuses(final DocumentStatus dsc)
	{
		return new Processor() {
			
			@Override
			@Transactional
			public void process(Exchange exchange) throws Exception {
				UserEvent ue = exchange.getIn().getBody(UserEvent.class);
				CaseRecord cr = ue.getCaseRecord(getDaoManager());
				for(Document d : cr.getDocuments())
				{
					if(!d.getStatus().equals(DocumentStatus.imported))
					{
						d.setStatus(dsc);
						getDaoManager().get(Document.class).update(d);
					}					
				}				
			}
			
			@Override
			public String toString() {
				return "Set Non Imported Document Status codes to " + dsc.getDescription();
			}
		};
	}
	
		
	protected Predicate caseIsStatus(final CaseStatus... scx)
	{
		return new Predicate() {
			
			@Override
			public boolean matches(Exchange exchange) {
				UserEvent ue = exchange.getIn().getBody(SendCase.class);
				CaseStatus sc = ue.getCaseRecord(getDaoManager()).getStatus();
				
				for(CaseStatus x : scx)
				{
					if(sc == x)
					{
						return true;
					}
				}
				
				return false;
			}
			
			@Override
			public String toString() {
				StringBuilder str = new StringBuilder("Case Status: ");
				for(CaseStatus x : scx)
				{
					str.append(x.toString() + " ");
				}				
				return str.toString();
			}
			
		};
	}
	


}