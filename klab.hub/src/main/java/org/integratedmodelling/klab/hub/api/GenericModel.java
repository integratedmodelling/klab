package org.integratedmodelling.klab.hub.api;

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
	@Id
    String id;

	@NotNull(message = "Name field cannot be null or blank")
	@Indexed(unique=true)
    String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GenericModel other = (GenericModel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
