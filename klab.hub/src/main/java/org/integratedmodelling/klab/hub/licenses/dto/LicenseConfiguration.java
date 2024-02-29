package org.integratedmodelling.klab.hub.licenses.dto;

import org.integratedmodelling.klab.hub.api.GenericModel;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "LicenseConfigurations")
public class LicenseConfiguration extends GenericModel{
	
	
	private String email;
	
	private String hubId;
	
	private String keyString;
	
	private String hubUrl;
	
	private String passphrase;
	
	private ArmoredKeyPair keys;

	private byte[] digest;
	
	private Boolean defaultConfig = false;
	
	public String getId() {
		return super.getId();
	}

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
		return super.getName();
	}

	public void setName(String name) {
		super.setName(name);
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

	public void setDigest(byte[] sha256hex) {
		this.digest = sha256hex;		
	}

	public byte[] getDigest() {
		return this.digest;
	}

	public String getHubUrl() {
		return hubUrl;
	}

	public void setHubUrl(String hubUrl) {
		this.hubUrl = hubUrl;
	}


	public Boolean getDefaultConfig() {
		return defaultConfig;
	}

	public void setDefaultConfig(Boolean defaultConfig) {
		this.defaultConfig = defaultConfig;
	}
	
}
