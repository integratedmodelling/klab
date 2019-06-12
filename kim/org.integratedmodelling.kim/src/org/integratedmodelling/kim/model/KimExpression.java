package org.integratedmodelling.kim.model;

import org.integratedmodelling.kim.api.IKimExpression;

public class KimExpression implements IKimExpression {

	private String code;
	private String language;

	public KimExpression(String code, String language) {
		this.code = code.substring(1, code.length() - 1);
		this.language = language;
	}
	
	@Override
	public String getCode() {
		return this.code;
	}

	@Override
	public String getLanguage() {
		return this.language;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	
	@Override
	public String toString() {
		return code;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((language == null) ? 0 : language.hashCode());
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
		KimExpression other = (KimExpression) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (language == null) {
			if (other.language != null)
				return false;
		} else if (!language.equals(other.language))
			return false;
		return true;
	}
	
}
