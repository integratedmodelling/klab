package org.integratedmodelling.klab.rest;

import org.integratedmodelling.klab.api.auth.ICertificate;

public class NodeAuthenticationRequest {
    
    private String nodeName;
    private String nodeKey;
    private String certificate;
	private ICertificate.Level level = ICertificate.Level.INSTITUTIONAL;
	
    public NodeAuthenticationRequest() {}
    
    public String getNodeName() {
        return nodeName;
    }
    
    public void setNodeName(String username) {
        this.nodeName = username;
    }
    
    public String getNodeKey() {
        return nodeKey;
    }
    
    public void setNodeKey(String userKey) {
        this.nodeKey = userKey;
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

}
