package org.integratedmodelling.klab.rest;

import org.integratedmodelling.klab.api.auth.ICertificate;

public class EngineAuthenticationRequest extends AuthenticationRequest {

	private String userType;

	public EngineAuthenticationRequest(String name, String key, String userType, String certificate,
			ICertificate.Level level, String idAgreement) {
		super();
		this.name = name;
		this.key = key;
		this.userType = userType;
		this.certificate = certificate;
		this.level = level;
		this.idAgreement = idAgreement;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public EngineAuthenticationRequest() {
	    this.setLevel(ICertificate.Level.USER);
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	public ICertificate.Level getLevel() {
		return level;
	}

	public void setLevel(ICertificate.Level level) {
		this.level = level;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((certificate == null) ? 0 : certificate.hashCode());
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((userType == null) ? 0 : userType.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((idAgreement == null) ? 0 : idAgreement.hashCode());
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
		EngineAuthenticationRequest other = (EngineAuthenticationRequest) obj;
		if (certificate == null) {
			if (other.certificate != null)
				return false;
		} else if (!certificate.equals(other.certificate))
			return false;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (userType == null) {
			if (other.userType != null)
				return false;
		} else if (!userType.equals(other.userType))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (idAgreement == null) {
            if (other.idAgreement!= null)
                return false;
        } else if (!idAgreement.equals(other.idAgreement))
            return false;
		return true;
	}
}
