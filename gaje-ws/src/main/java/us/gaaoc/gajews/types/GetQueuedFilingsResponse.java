
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
 *         &lt;element name="cases" type="{http://GAJEWS.gaaoc.us/types/}uuidList"/>
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
    "cases"
})
@XmlRootElement(name = "getQueuedFilingsResponse")
public class GetQueuedFilingsResponse {

    @XmlElement(required = true)
    protected UuidList cases;

    /**
     * Gets the value of the cases property.
     * 
     * @return
     *     possible object is
     *     {@link UuidList }
     *     
     */
    public UuidList getCases() {
        return cases;
    }

    /**
     * Sets the value of the cases property.
     * 
     * @param value
     *     allowed object is
     *     {@link UuidList }
     *     
     */
    public void setCases(UuidList value) {
        this.cases = value;
    }

}
