package org.integratedmodelling.klab.hub.models;

import java.util.List;
import javax.persistence.GeneratedValue;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Nodes")
public class KlabNode {
	
    @Id @GeneratedValue
    String id;

    @Indexed(unique = true)
    String node;
    
    @Indexed(unique = false)
    String email;
    
    DateTime registrationDate;
    
    DateTime lastNodeConnection;
    
    List<String> groups;

	public void setLastNodeConnection() {
		lastNodeConnection = DateTime.now();
	}

	public String getNode() {
		return this.node;
	}

	public String getEmail() {
		return this.email;
	}

	public List<String> getGroups() {
		return this.groups;
	}

	public void setGroups(List<String> groups) {
		this.groups = groups;
	}

}