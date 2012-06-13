package us.gaje.efiling.model.inbox;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import ognl.Ognl;
import ognl.OgnlContext;
import ognl.OgnlException;

import us.gaje.efiling.model.CaseRecord;
import us.gaje.efiling.model.Court;
import us.gaje.efiling.model.JpaIdEntity;
import us.gaje.efiling.model.Persistable;
import us.gaje.efiling.model.Person;
import us.gaje.efiling.model.enums.MessageType;

@Entity
@Table(name="inboxMessage")
@NamedQueries({
	@NamedQuery(name="byCourtAndReceivingPerson",query=
		"from Message where court = :court and receivingPerson = :receivingPerson"),	
})
//TODO: see if we can order the named query by sent date
public class Message extends JpaIdEntity implements Persistable<Message>, Serializable{

	private static final long serialVersionUID = 1L;
		
	
	public Class<Message> getPersistableClass() {
		return Message.class;
	}
	
	private String message;
	
	private Date sent;
	
	private boolean recentlySent;
	
	
	@Column(name="messageTypeId")
	private int _messageType;
	
	
	public int get_messageType() {
		return _messageType;
	}
	
	public void set_messageType(int _messageType) {
		this._messageType = _messageType;
	}
	
	public void setMessageType(MessageType t)
	{
		this._messageType = t.getValue();		
	}
	
	@Transient
	public MessageType getMessageType()
	{
		return MessageType.fromValue(this._messageType);
	}
	
	@ManyToOne
	@JoinColumn(name = "courtUuid")
	private Court court;
	
	@ManyToOne
	@JoinColumn(name="receivingPersonUuid")
	private Person receivingPerson;
	
	public void setReceivingPerson(Person receivingPerson) {
		this.receivingPerson = receivingPerson;
	}
	public Person getReceivingPerson() {
		return receivingPerson;
	}
	
	@ManyToOne
	@JoinColumn(name="caseRecordUuid")
	private CaseRecord caseRecord;
	
	public CaseRecord getCaseRecord() {
		return caseRecord;
	}
	
	public void setCaseRecord(CaseRecord caseRecord) {
		this.caseRecord = caseRecord;
	}
	
	@ManyToOne
	@JoinColumn(name="sendingPersonUuid")
	private Person sendingPerson;
	
	public Person getSendingPerson() {
		return sendingPerson;
	}
	
	public void setSendingPerson(Person sendingPerson) {
		this.sendingPerson = sendingPerson;
	}
	
	public String getMessage() {
		return message;
	}

	@Transient
	public String getMessageEvaluated() throws OgnlException
	{
		
		/*
		int beginReplace = this.message.indexOf("ognl(");
		int endExpr = this.message.indexOf(")ognl");
		
		int beginExpr = beginReplace + "ognl(".length();
		int endReplace = endExpr +  ")ognl".length();
		
		String expressionStr = this.message.substring(beginExpr, endExpr);
		
		OgnlContext ognl = new OgnlContext();
		Object expr = Ognl.parseExpression(expressionStr);
		
		String ret = this.message.substring(0,beginReplace) + (String) Ognl.getValue(expr,ognl,this) +
			this.message.substring(endReplace);
		
		return ret;
		*/
		
		
		OgnlContext ognl = new OgnlContext();
		Object expr = Ognl.parseExpression(this.message);
		return (String) Ognl.getValue(expr,ognl,this);		
	}
	
	public Date getSent() {
		return sent;
	}
	
	
	public boolean isRecentlySent() {
		return recentlySent;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setRecentlySent(boolean recentlySent) {
		this.recentlySent = recentlySent;
	}
	
	public void setSent(Date sent) {
		this.sent = sent;
	}
	
	public Court getCourt() {
		return court;
	}
	
	public void setCourt(Court court) {
		this.court = court;
	}
	
}
