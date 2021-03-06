
/*
 * 
 */

package us.gaaoc.gajews;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;

/**
 * This class was generated by Apache CXF 2.1.3
 * Fri Mar 13 10:57:37 EDT 2009
 * Generated source version: 2.1.3
 * 
 */


@WebServiceClient(name = "GAJE_ImportWSService", 
                  wsdlLocation = "classpath:GAJE_ImportWS.wsdl",
                  targetNamespace = "http://GAJEWS.gaaoc.us/") 
public class GAJEImportWSService extends Service {

    public final static URL WSDL_LOCATION;
    public final static QName SERVICE = new QName("http://GAJEWS.gaaoc.us/", "GAJE_ImportWSService");
    public final static QName GAJEImportWS = new QName("http://GAJEWS.gaaoc.us/", "GAJE_ImportWS");
    static {
        URL url = null;
        try {
            url = new URL("classpath:GAJE_ImportWS.wsdl");
        } catch (MalformedURLException e) {
            System.err.println("Can not initialize the default wsdl from classpath:GAJE_ImportWS.wsdl");
            // e.printStackTrace();
        }
        WSDL_LOCATION = url;
    }

    public GAJEImportWSService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public GAJEImportWSService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public GAJEImportWSService() {
        super(WSDL_LOCATION, SERVICE);
    }

    /**
     * 
     * @return
     *     returns QueuedFilings
     */
    @WebEndpoint(name = "GAJE_ImportWS")
    public QueuedFilings getGAJEImportWS() {
        return super.getPort(GAJEImportWS, QueuedFilings.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns QueuedFilings
     */
    @WebEndpoint(name = "GAJE_ImportWS")
    public QueuedFilings getGAJEImportWS(WebServiceFeature... features) {
        return super.getPort(GAJEImportWS, (Class<QueuedFilings>) QueuedFilings.class, features);
    }

}
