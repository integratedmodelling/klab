package org.integratedmodelling.klab.hub.licenses.dto;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class PgpKey {
	

	private String secret;
	private String digest;
	private String password;
	private String id;

	public String getSecret() {
		return secret;
	}
	
	public void setSecret(String secret) {
		this.secret = secret;
	}
	
	public byte[] getDigest() throws DecoderException {
		return Hex.decodeHex(digest.replaceAll(" ", "").toCharArray());
	}
	
	public void setDigest(String digest) {
		this.digest = digest;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	
}
