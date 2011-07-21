
package us.gaaoc.gajews.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="caseUuid" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="docAck" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "caseUuid",
    "docAck"
})
@XmlRootElement(name = "docAckRequest")
public class DocAckRequest {

    @XmlElement(required = true)
    protected String caseUuid;
    @XmlElement(required = true)
    protected String docAck;

    /**
     * Gets the value of the caseUuid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCaseUuid() {
        return caseUuid;
    }

    /**
     * Sets the value of the caseUuid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCaseUuid(String value) {
        this.caseUuid = value;
    }

    /**
     * Gets the value of the docAck property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocAck() {
        return docAck;
    }

    /**
     * Sets the value of the docAck property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocAck(String value) {
        this.docAck = value;
    }

}
