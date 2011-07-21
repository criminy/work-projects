
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
 *         &lt;element name="case_filing" type="{http://GAJEWS.gaaoc.us/types/}caseFiling"/>
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
    "caseFiling"
})
@XmlRootElement(name = "getCaseFilingResponse")
public class GetCaseFilingResponse {

    @XmlElement(name = "case_filing", required = true)
    protected CaseFiling caseFiling;

    /**
     * Gets the value of the caseFiling property.
     * 
     * @return
     *     possible object is
     *     {@link CaseFiling }
     *     
     */
    public CaseFiling getCaseFiling() {
        return caseFiling;
    }

    /**
     * Sets the value of the caseFiling property.
     * 
     * @param value
     *     allowed object is
     *     {@link CaseFiling }
     *     
     */
    public void setCaseFiling(CaseFiling value) {
        this.caseFiling = value;
    }

}
