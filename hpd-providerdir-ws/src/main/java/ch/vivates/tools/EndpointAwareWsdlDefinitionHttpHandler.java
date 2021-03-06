package ch.vivates.tools;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;
import org.springframework.ws.transport.http.HttpTransportConstants;
import org.springframework.ws.wsdl.WsdlDefinition;
import org.springframework.xml.transform.TransformerObjectSupport;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * The Class EndpointAwareWsdlDefinitionHttpHandler replaces the SERVICE_ENDPOINT_PLACEHOLDER
 * in the WSDL-File on a GET-Request and sends it as response.
 * 
 * @author Federico Marmory, Post CH, major development
 * @author Kevin Tippenhauer, Berner Fachhochschule, xsd folder placeholder, javadoc
 */
@SuppressWarnings("restriction")
public class EndpointAwareWsdlDefinitionHttpHandler extends TransformerObjectSupport implements HttpHandler, InitializingBean {

    /** The Constant CONTENT_TYPE. */
    private static final String CONTENT_TYPE = "text/xml";

    /** The WSDL definition. */
    private WsdlDefinition definition;
    
    /** The service endpoint. */
    private String serviceEndpoint;
    
    /** The xsdSchemaFolder. */
    private String xsdFolder;

    /**
     * Instantiates a new endpoint aware wsdl definition http handler.
     */
    public EndpointAwareWsdlDefinitionHttpHandler() {
    }

    /**
     * Instantiates a new endpoint aware wsdl definition http handler.
     *
     * @param definition the definition
     */
    public EndpointAwareWsdlDefinitionHttpHandler(WsdlDefinition definition) {
        this.definition = definition;
    }

    /**
     * Sets the definition.
     *
     * @param definition the new definition
     */
    public void setDefinition(WsdlDefinition definition) {
        this.definition = definition;
    }

	/**
	 * Sets the service endpoint.
	 *
	 * @param serviceEndpoint the new service endpoint
	 */
	public void setServiceEndpoint(String serviceEndpoint) {
		this.serviceEndpoint = serviceEndpoint;
	}
	
	/**
	 * Sets the location for the XSD Folder.
	 *
	 * @param serviceEndpoint the new location for the XSD Folder
	 */
	public void setXsdFolder(String xsdFolder) {
		this.xsdFolder = xsdFolder;
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
        Assert.notNull(definition, "'definition' is required");
    }

    /* (non-Javadoc)
     * @see com.sun.net.httpserver.HttpHandler#handle(com.sun.net.httpserver.HttpExchange)
     */
    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            if (HttpTransportConstants.METHOD_GET.equals(httpExchange.getRequestMethod())) {
                Headers headers = httpExchange.getResponseHeaders();
                headers.set(HttpTransportConstants.HEADER_CONTENT_TYPE, CONTENT_TYPE);
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                transform(definition.getSource(), new StreamResult(os));
                String oss = new String(os.toByteArray());
                oss = oss.replace("[[SERVICE_ENDPOINT_PLACEHOLDER]]", serviceEndpoint);
                byte[] buf = oss.replace("[[XSD_FOLDER_PLACEHOLDER]]", xsdFolder).getBytes();
                httpExchange.sendResponseHeaders(HttpTransportConstants.STATUS_OK, buf.length);
                FileCopyUtils.copy(buf, httpExchange.getResponseBody());
            }
            else {
                httpExchange.sendResponseHeaders(HttpTransportConstants.STATUS_METHOD_NOT_ALLOWED, -1);
            }
        }
        catch (TransformerException ex) {
            logger.error(ex, ex);
        }
        finally {
            httpExchange.close();
        }
    }
}