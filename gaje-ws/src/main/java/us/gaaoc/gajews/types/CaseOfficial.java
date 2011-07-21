
package us.gaaoc.gajews.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for case_official complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="case_official">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="person" type="{http://GAJEWS.gaaoc.us/types/}person_type"/>
 *         &lt;element name="barID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cmsCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "case_official", propOrder = {
    "person",
    "barID",
    "cmsCode"
})
public class CaseOfficial {

    @XmlElement(required = true)
    protected PersonType person;
    @XmlElement(required = true)
    protected String barID;
    @XmlElement(required = true)
    protected String cmsCode;

    /**
     * Gets the value of the person property.
     * 
     * @return
     *     possible object is
     *     {@link PersonType }
     *     
     */
    public PersonType getPerson() {
        return person;
    }

    /**
     * Sets the value of the person property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonType }
     *     
     */
    public void setPerson(PersonType value) {
        this.person = value;
    }

    /**
     * Gets the value of the barID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBarID() {
        return barID;
    }

    /**
     * Sets the value of the barID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBarID(String value) {
        this.barID = value;
    }

    /**
     * Gets the value of the cmsCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCmsCode() {
        return cmsCode;
    }

    /**
     * Sets the value of the cmsCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCmsCode(String value) {
        this.cmsCode = value;
    }

}
