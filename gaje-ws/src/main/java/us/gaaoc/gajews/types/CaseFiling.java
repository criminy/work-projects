
package us.gaaoc.gajews.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for caseFiling complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="caseFiling">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="docketID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="divID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="category" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="filingType" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="caption" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="date" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dispositionDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="prosecutionAttorneys" type="{http://GAJEWS.gaaoc.us/types/}caseOfficialList"/>
 *         &lt;element name="defenseAttorneys" type="{http://GAJEWS.gaaoc.us/types/}caseOfficialList"/>
 *         &lt;element name="initiatingParties" type="{http://GAJEWS.gaaoc.us/types/}casePartyList"/>
 *         &lt;element name="caseInitiatingPartyOrganizations" type="{http://GAJEWS.gaaoc.us/types/}casePartyList"/>
 *         &lt;element name="caseDefendantParties" type="{http://GAJEWS.gaaoc.us/types/}casePartyList"/>
 *         &lt;element name="witnesses" type="{http://GAJEWS.gaaoc.us/types/}casePartyList"/>
 *         &lt;element name="events" type="{http://GAJEWS.gaaoc.us/types/}caseEventList"/>
 *         &lt;element name="courtID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "caseFiling", propOrder = {
    "docketID",
    "divID",
    "type",
    "category",
    "filingType",
    "caption",
    "date",
    "dispositionDate",
    "status",
    "prosecutionAttorneys",
    "defenseAttorneys",
    "initiatingParties",
    "caseInitiatingPartyOrganizations",
    "caseDefendantParties",
    "witnesses",
    "events",
    "courtID"
})
public class CaseFiling {

    @XmlElement(required = true, nillable = true)
    protected String docketID;
    protected int divID;
    protected int type;
    protected int category;
    protected int filingType;
    @XmlElement(required = true)
    protected String caption;
    @XmlElement(required = true)
    protected String date;
    @XmlElement(required = true)
    protected String dispositionDate;
    @XmlElement(required = true)
    protected String status;
    @XmlElement(required = true)
    protected CaseOfficialList prosecutionAttorneys;
    @XmlElement(required = true)
    protected CaseOfficialList defenseAttorneys;
    @XmlElement(required = true)
    protected CasePartyList initiatingParties;
    @XmlElement(required = true)
    protected CasePartyList caseInitiatingPartyOrganizations;
    @XmlElement(required = true)
    protected CasePartyList caseDefendantParties;
    @XmlElement(required = true)
    protected CasePartyList witnesses;
    @XmlElement(required = true)
    protected CaseEventList events;
    @XmlElement(required = true)
    protected String courtID;

    /**
     * Gets the value of the docketID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocketID() {
        return docketID;
    }

    /**
     * Sets the value of the docketID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocketID(String value) {
        this.docketID = value;
    }

    /**
     * Gets the value of the divID property.
     * 
     */
    public int getDivID() {
        return divID;
    }

    /**
     * Sets the value of the divID property.
     * 
     */
    public void setDivID(int value) {
        this.divID = value;
    }

    /**
     * Gets the value of the type property.
     * 
     */
    public int getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     */
    public void setType(int value) {
        this.type = value;
    }

    /**
     * Gets the value of the category property.
     * 
     */
    public int getCategory() {
        return category;
    }

    /**
     * Sets the value of the category property.
     * 
     */
    public void setCategory(int value) {
        this.category = value;
    }

    /**
     * Gets the value of the filingType property.
     * 
     */
    public int getFilingType() {
        return filingType;
    }

    /**
     * Sets the value of the filingType property.
     * 
     */
    public void setFilingType(int value) {
        this.filingType = value;
    }

    /**
     * Gets the value of the caption property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCaption() {
        return caption;
    }

    /**
     * Sets the value of the caption property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCaption(String value) {
        this.caption = value;
    }

    /**
     * Gets the value of the date property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the value of the date property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDate(String value) {
        this.date = value;
    }

    /**
     * Gets the value of the dispositionDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDispositionDate() {
        return dispositionDate;
    }

    /**
     * Sets the value of the dispositionDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDispositionDate(String value) {
        this.dispositionDate = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the prosecutionAttorneys property.
     * 
     * @return
     *     possible object is
     *     {@link CaseOfficialList }
     *     
     */
    public CaseOfficialList getProsecutionAttorneys() {
        return prosecutionAttorneys;
    }

    /**
     * Sets the value of the prosecutionAttorneys property.
     * 
     * @param value
     *     allowed object is
     *     {@link CaseOfficialList }
     *     
     */
    public void setProsecutionAttorneys(CaseOfficialList value) {
        this.prosecutionAttorneys = value;
    }

    /**
     * Gets the value of the defenseAttorneys property.
     * 
     * @return
     *     possible object is
     *     {@link CaseOfficialList }
     *     
     */
    public CaseOfficialList getDefenseAttorneys() {
        return defenseAttorneys;
    }

    /**
     * Sets the value of the defenseAttorneys property.
     * 
     * @param value
     *     allowed object is
     *     {@link CaseOfficialList }
     *     
     */
    public void setDefenseAttorneys(CaseOfficialList value) {
        this.defenseAttorneys = value;
    }

    /**
     * Gets the value of the initiatingParties property.
     * 
     * @return
     *     possible object is
     *     {@link CasePartyList }
     *     
     */
    public CasePartyList getInitiatingParties() {
        return initiatingParties;
    }

    /**
     * Sets the value of the initiatingParties property.
     * 
     * @param value
     *     allowed object is
     *     {@link CasePartyList }
     *     
     */
    public void setInitiatingParties(CasePartyList value) {
        this.initiatingParties = value;
    }

    /**
     * Gets the value of the caseInitiatingPartyOrganizations property.
     * 
     * @return
     *     possible object is
     *     {@link CasePartyList }
     *     
     */
    public CasePartyList getCaseInitiatingPartyOrganizations() {
        return caseInitiatingPartyOrganizations;
    }

    /**
     * Sets the value of the caseInitiatingPartyOrganizations property.
     * 
     * @param value
     *     allowed object is
     *     {@link CasePartyList }
     *     
     */
    public void setCaseInitiatingPartyOrganizations(CasePartyList value) {
        this.caseInitiatingPartyOrganizations = value;
    }

    /**
     * Gets the value of the caseDefendantParties property.
     * 
     * @return
     *     possible object is
     *     {@link CasePartyList }
     *     
     */
    public CasePartyList getCaseDefendantParties() {
        return caseDefendantParties;
    }

    /**
     * Sets the value of the caseDefendantParties property.
     * 
     * @param value
     *     allowed object is
     *     {@link CasePartyList }
     *     
     */
    public void setCaseDefendantParties(CasePartyList value) {
        this.caseDefendantParties = value;
    }

    /**
     * Gets the value of the witnesses property.
     * 
     * @return
     *     possible object is
     *     {@link CasePartyList }
     *     
     */
    public CasePartyList getWitnesses() {
        return witnesses;
    }

    /**
     * Sets the value of the witnesses property.
     * 
     * @param value
     *     allowed object is
     *     {@link CasePartyList }
     *     
     */
    public void setWitnesses(CasePartyList value) {
        this.witnesses = value;
    }

    /**
     * Gets the value of the events property.
     * 
     * @return
     *     possible object is
     *     {@link CaseEventList }
     *     
     */
    public CaseEventList getEvents() {
        return events;
    }

    /**
     * Sets the value of the events property.
     * 
     * @param value
     *     allowed object is
     *     {@link CaseEventList }
     *     
     */
    public void setEvents(CaseEventList value) {
        this.events = value;
    }

    /**
     * Gets the value of the courtID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCourtID() {
        return courtID;
    }

    /**
     * Sets the value of the courtID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCourtID(String value) {
        this.courtID = value;
    }

}
