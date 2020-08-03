package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.api.knowledge.IAuthority;

public class AuthorityReference implements IAuthority.Capabilities {

	private String name;
	private String worldview;
	private boolean searchable;
	private boolean fuzzy;
	private List<String> documentationFormats = new ArrayList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AuthorityReference() {
	}

	public AuthorityReference(String name) {
		super();
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		AuthorityReference other = (AuthorityReference) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ComponentReference [name=" + name + "]";
	}

	public boolean isSearchable() {
		return searchable;
	}

	public void setSearchable(boolean searchable) {
		this.searchable = searchable;
	}

	@Override
	public List<String> getDocumentationFormats() {
		return documentationFormats;
	}

	public void setDocumentationFormats(List<String> documentationFormats) {
		this.documentationFormats = documentationFormats;
	}

	@Override
	public boolean isFuzzy() {
		return fuzzy;
	}

	public void setFuzzy(boolean fuzzy) {
		this.fuzzy = fuzzy;
	}

	public String getWorldview() {
		return worldview;
	}

	public void setWorldview(String worldview) {
		this.worldview = worldview;
	}
	
}
