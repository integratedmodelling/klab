package org.integratedmodelling.klab.hub.api;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Levers")
public class MongoLever extends GenericModel{
	
    @Indexed(unique = false)
    String email;
    
    String baseUrl;
    
    DateTime registrationDate;
    
    DateTime lastConnection;

	public void setLlastConnection() {
		lastConnection = DateTime.now();
	}

	public String getNode() {
		return this.name;
	}

	public String getEmail() {
		return this.email;
	}

	public String getUrl() {
		return this.baseUrl;
	}

}