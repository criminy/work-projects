
package us.gaaoc.gajews.types;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the us.gaaoc.gajews.types package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _UuidList_QNAME = new QName("http://GAJEWS.gaaoc.us/types/", "uuid_list");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: us.gaaoc.gajews.types
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DocAckRequest }
     * 
     */
    public DocAckRequest createDocAckRequest() {
        return new DocAckRequest();
    }

    /**
     * Create an instance of {@link PutDocument }
     * 
     */
    public PutDocument createPutDocument() {
        return new PutDocument();
    }

    /**
     * Create an instance of {@link CaseOfficial }
     * 
     */
    public CaseOfficial createCaseOfficial() {
        return new CaseOfficial();
    }

    /**
     * Create an instance of {@link ResetRequest }
     * 
     */
    public ResetRequest createResetRequest() {
        return new ResetRequest();
    }

    /**
     * Create an instance of {@link CaseEventList }
     * 
     */
    public CaseEventList createCaseEventList() {
        return new CaseEventList();
    }

    /**
     * Create an instance of {@link AddressList }
     * 
     */
    public AddressList createAddressList() {
        return new AddressList();
    }

    /**
     * Create an instance of {@link AddressType }
     * 
     */
    public AddressType createAddressType() {
        return new AddressType();
    }

    /**
     * Create an instance of {@link GetCaseFilingRequest }
     * 
     */
    public GetCaseFilingRequest createGetCaseFilingRequest() {
        return new GetCaseFilingRequest();
    }

    /**
     * Create an instance of {@link ImportAckRequest }
     * 
     */
    public ImportAckRequest createImportAckRequest() {
        return new ImportAckRequest();
    }

    /**
     * Create an instance of {@link CasePartyList }
     * 
     */
    public CasePartyList createCasePartyList() {
        return new CasePartyList();
    }

    /**
     * Create an instance of {@link ImportAckType }
     * 
     */
    public ImportAckType createImportAckType() {
        return new ImportAckType();
    }

    /**
     * Create an instance of {@link DocAckResponse }
     * 
     */
    public DocAckResponse createDocAckResponse() {
        return new DocAckResponse();
    }

    /**
     * Create an instance of {@link OrganizationType }
     * 
     */
    public OrganizationType createOrganizationType() {
        return new OrganizationType();
    }

    /**
     * Create an instance of {@link CaseFiling }
     * 
     */
    public CaseFiling createCaseFiling() {
        return new CaseFiling();
    }

    /**
     * Create an instance of {@link GetCaseFilingResponse }
     * 
     */
    public GetCaseFilingResponse createGetCaseFilingResponse() {
        return new GetCaseFilingResponse();
    }

    /**
     * Create an instance of {@link ImportAckResponse }
     * 
     */
    public ImportAckResponse createImportAckResponse() {
        return new ImportAckResponse();
    }

    /**
     * Create an instance of {@link GetQueuedFilingsRequest }
     * 
     */
    public GetQueuedFilingsRequest createGetQueuedFilingsRequest() {
        return new GetQueuedFilingsRequest();
    }

    /**
     * Create an instance of {@link DocumentType }
     * 
     */
    public DocumentType createDocumentType() {
        return new DocumentType();
    }

    /**
     * Create an instance of {@link GetDocumentsRequest }
     * 
     */
    public GetDocumentsRequest createGetDocumentsRequest() {
        return new GetDocumentsRequest();
    }

    /**
     * Create an instance of {@link GetDocumentsResponse }
     * 
     */
    public GetDocumentsResponse createGetDocumentsResponse() {
        return new GetDocumentsResponse();
    }

    /**
     * Create an instance of {@link DocumentList }
     * 
     */
    public DocumentList createDocumentList() {
        return new DocumentList();
    }

    /**
     * Create an instance of {@link PutDocumentResponse }
     * 
     */
    public PutDocumentResponse createPutDocumentResponse() {
        return new PutDocumentResponse();
    }

    /**
     * Create an instance of {@link PersonType }
     * 
     */
    public PersonType createPersonType() {
        return new PersonType();
    }

    /**
     * Create an instance of {@link GetQueuedFilingsResponse }
     * 
     */
    public GetQueuedFilingsResponse createGetQueuedFilingsResponse() {
        return new GetQueuedFilingsResponse();
    }

    /**
     * Create an instance of {@link ResetResponse }
     * 
     */
    public ResetResponse createResetResponse() {
        return new ResetResponse();
    }

    /**
     * Create an instance of {@link CaseEventType }
     * 
     */
    public CaseEventType createCaseEventType() {
        return new CaseEventType();
    }

    /**
     * Create an instance of {@link CasePartyType }
     * 
     */
    public CasePartyType createCasePartyType() {
        return new CasePartyType();
    }

    /**
     * Create an instance of {@link CaseOfficialList }
     * 
     */
    public CaseOfficialList createCaseOfficialList() {
        return new CaseOfficialList();
    }

    /**
     * Create an instance of {@link UuidList }
     * 
     */
    public UuidList createUuidList() {
        return new UuidList();
    }

    /**
     * Create an instance of {@link OrganizationList }
     * 
     */
    public OrganizationList createOrganizationList() {
        return new OrganizationList();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UuidList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://GAJEWS.gaaoc.us/types/", name = "uuid_list")
    public JAXBElement<UuidList> createUuidList(UuidList value) {
        return new JAXBElement<UuidList>(_UuidList_QNAME, UuidList.class, null, value);
    }

}
