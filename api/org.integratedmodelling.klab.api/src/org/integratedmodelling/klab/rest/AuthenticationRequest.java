package org.integratedmodelling.klab.rest;

import org.integratedmodelling.klab.api.auth.ICertificate;

/**
 * Simple abstract class for all authentication requests
 * 
 * @author steven.wohl
 *
 */
public abstract class AuthenticationRequest {
	
	protected String name;
	protected String email;
	protected String certificate;
	protected String key;
	protected ICertificate.Level level;
	protected String idAgreement;
	
	public AuthenticationRequest() {
	    this.setLevel(ICertificate.Level.INSTITUTIONAL);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCertificate() {
		return certificate;
	}
	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public ICertificate.Level getLevel() {
		return level;
	}
	public void setLevel(ICertificate.Level level) {
		this.level = level;
	}
    public String getIdAgreement() {
        return idAgreement;
    }
    public void setIdAgreement(String idAgreement) {
        this.idAgreement = idAgreement;
    }
	
	

}
