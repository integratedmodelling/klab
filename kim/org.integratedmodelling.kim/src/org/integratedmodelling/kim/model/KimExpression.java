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

}
