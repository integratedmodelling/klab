package org.integratedmodelling.random.adapters;

import org.integratedmodelling.klab.exceptions.KlabException;

public class RecreationIDB {

	private boolean isOnline = false;
	RecreationIDBRuntimeEnvironment ribd;
	RecreationIDBOutputDeserializer deserializer;
	private String serviceURL = "";

	public RecreationIDB() {
		this("https://ridb.recreation.gov/api/v1");
	}

	public RecreationIDB(String serviceUrl) {
		this.serviceURL = serviceUrl;
		ribd = new RecreationIDBRuntimeEnvironment(serviceURL);
		isOnline = ribd.isOnline();
		deserializer = new RecreationIDBOutputDeserializer();

	}

	public boolean isOnline() {
		return isOnline;
	}

	public String getServiceURL() {
		return serviceURL;
	}

	public RecreationIDBOutputDeserializer.RecreationAreas recreationAreas(String input, String apiKey)
			throws KlabException {
		String response = ribd.recreationIDBSendRequest(input,
				RecreationIDBRuntimeEnvironment.RecreationIDBRequestType.RecAreas, apiKey);
		deserializer.setJson(response);
		return deserializer.deserializeRecAreasData();
	}

}
