package org.integratedmodelling.klab.hub.api;

import javax.persistence.GeneratedValue;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.validation.annotation.Validated;

/*
 * Generic model so that all extended concrete classes have 
 * a generic structure
 * 
 *  Steve
 */
@Validated
public abstract class GenericModel {
	@Id @GeneratedValue
    String id;

	@NotNull(message = "Name field cannot be null or blank")
	@Indexed(unique = true)
    String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
