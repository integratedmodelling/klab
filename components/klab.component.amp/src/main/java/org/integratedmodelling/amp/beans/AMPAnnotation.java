package org.integratedmodelling.amp.beans;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="Annotations")
@TypeAlias(value = "AMPAnnotation")
public class AMPAnnotation {

	private String id;
	private String observable;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getObservable() {
		return observable;
	}

	public void setObservable(String observable) {
		this.observable = observable;
	}

}
