package org.integratedmodelling.klab.hub.config;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "license")
public class LegacyLicenseConfig {
	
	private String userId;
	private String password;
	private String key;
	private String email;
	private String nodeName;
	private String hubId;
	private String name;
	private String hubUrl;

	private final SecRing secRing = new SecRing();
    private final PubRing pubRing = new PubRing();
	
	public static class SecRing {
		private String filename;
        private String digest;
        public String getFilename() {
			return filename;
		}
        public void setFilename(String filename) {
			this.filename = filename;
		}
		public byte[] getDigest() throws DecoderException {
			return Hex.decodeHex(digest.replaceAll(" ", "").toCharArray());
		}
		public void setDigest(String digest) {
			this.digest = digest;
		}
    }
    
    public static class PubRing {
		private String filename;
        private String digest;
        public String getFilename() {
			return filename;
		}
        public void setFilename(String filename) {
			this.filename = filename;
		}
		public byte[] getDigest() throws DecoderException {
			return Hex.decodeHex(digest.replaceAll(" ", "").toCharArray());
		}
		public void setDigest(String digest) {
			this.digest = digest;
		}
    }

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
    public SecRing getSecRing() {
		return secRing;
	}

	public PubRing getPubRing() {
		return pubRing;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getHubId() {
		return hubId;
	}

	public void setHubId(String hubId) {
		this.hubId = hubId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHubUrl() {
		return this.hubUrl;
	}
	
    public void setHubUrl(String hubUrl) {
		this.hubUrl = hubUrl;
	}
	
}
