package ch.bfh.i4mi.client;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.transform.stream.StreamResult;

import oasis.names.tc.dsml._2._0.core.AttributeDescription;
import oasis.names.tc.dsml._2._0.core.BatchRequest;
import oasis.names.tc.dsml._2._0.core.BatchResponse;
import oasis.names.tc.dsml._2._0.core.Filter;
import oasis.names.tc.dsml._2._0.core.ObjectFactory;
import oasis.names.tc.dsml._2._0.core.SearchRequest;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

/**
 * The Class Application.
 */
public class Application {

	/**
	 * Instantiates a new application.
	 */
	private Application() {
		// This constructor is intentionally empty. Nothing special is needed here.
	}

	/**
	 * The main method builds the BatchRequest, sends the request to the web
	 * service and receives the response.
	 * 
	 *
	 * @param args
	 *            the arguments for the application.
	 *            
	 * @throws SOAPException the SOAPException
	 */
	public static void main(final String... args) throws SOAPException {

		ApplicationContext ctx = SpringApplication.run(
				ClientConfiguration.class, args);

		HPDClient hpdClient = ctx.getBean(HPDClient.class);

		AttributeDescription attrDesc = new AttributeDescription();
		attrDesc.setName("objectClass");

		Filter filter = new Filter();
		filter.setPresent(attrDesc);

		SearchRequest searchRequest = new ObjectFactory()
				.createSearchRequest();
		searchRequest.setDn("ou=HCProfessional,dc=HPD,o=ehealth-suisse,c=ch");
		searchRequest.setRequestID("01");
		searchRequest.setScope("wholeSubtree");
		searchRequest.setDerefAliases("neverDerefAliases");
		searchRequest.setSizeLimit(0L);
		searchRequest.setTimeLimit(0L);
		searchRequest.setTypesOnly(false);
		searchRequest.setFilter(filter);

		BatchRequest batchRequest = new BatchRequest();

		batchRequest.setRequestID("0001");
		batchRequest.setProcessing("sequential");
		batchRequest.setResponseOrder("sequential");
		batchRequest.setOnError("exit");
		batchRequest.getBatchRequests().add(searchRequest);
		
		Jaxb2Marshaller marshaller = ctx.getBean(Jaxb2Marshaller.class);

		BatchResponse batchResponse = hpdClient
				.getBatchResponse(batchRequest);

		if (batchResponse != null) {
			System.out.println("\n------------ Batch Response ------------");

			// Wrapping in JAXBElement is needed because of missing
			// XMLRootElement tags.
			marshaller.marshal(new JAXBElement<BatchResponse>(new QName(
					"urn:BatchResponse"), BatchResponse.class, batchResponse),
					new StreamResult(System.out));

		}

	}
}
