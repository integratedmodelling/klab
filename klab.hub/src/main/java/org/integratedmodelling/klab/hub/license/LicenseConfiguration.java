package org.integratedmodelling.klab.hub.license;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "LicenseConfigurations")
public class LicenseConfiguration {
	
	@Id
	private String id;
	
	private String email;
	
	private String hubId;
	
	private String keyString;
	
	private String name;
	
	private String passphrase;
	
	private ArmoredKeyPair keys;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHubId() {
		return hubId;
	}

	public void setHubId(String hubId) {
		this.hubId = hubId;
	}

	public String getKeyString() {
		return keyString;
	}

	public void setKeyString(String keyString) {
		this.keyString = keyString;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArmoredKeyPair getKeys() {
		return keys;
	}

	public void setKeys(ArmoredKeyPair keys) {
		this.keys = keys;
	}

	public String getPassphrase() {
		return passphrase;
	}

	public void setPassphrase(String passphrase) {
		this.passphrase = passphrase;
	}
	
}
