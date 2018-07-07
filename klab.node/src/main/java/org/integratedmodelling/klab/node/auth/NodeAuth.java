package org.integratedmodelling.klab.node.auth;

import org.integratedmodelling.klab.api.auth.ICertificate;
import org.integratedmodelling.klab.api.auth.IPartnerIdentity;
import org.integratedmodelling.klab.auth.KlabCertificate;
import org.integratedmodelling.klab.communication.client.Client;
import org.integratedmodelling.klab.exceptions.KlabAuthorizationException;
import org.integratedmodelling.klab.rest.NodeAuthenticationRequest;
import org.integratedmodelling.klab.rest.NodeAuthenticationResponse;

public enum NodeAuth {

	INSTANCE;
	
	IPartnerIdentity rootIdentity;
	// if this is set, use instead of whatever is in the certificate
	String authenticatingHub;
	Client client = Client.create();
	
	public void setAuthenticatingHub(String url) {
		this.authenticatingHub = url;
	}
	
	public IPartnerIdentity getRootIdentity() {
		return rootIdentity;
	}
	
	public IPartnerIdentity authenticate(ICertificate certificate) {
		
		String serverHub = authenticatingHub;
		if (serverHub == null) { 
			serverHub = certificate.getProperty(KlabCertificate.KEY_SERVER);
		}
		
		if (serverHub == null) {
			throw new KlabAuthorizationException("a node cannot be started without a valid authenticating hub");
		}
		
		NodeAuthenticationRequest request = new NodeAuthenticationRequest();
		
		request.setCertificate(certificate.getProperty(KlabCertificate.KEY_CERTIFICATE));
		request.setUsername(certificate.getProperty(KlabCertificate.KEY_PARTNER_NAME));
		request.setUserKey(KlabCertificate.KEY_SIGNATURE);
		request.setLevel(certificate.getLevel());
		
		NodeAuthenticationResponse response = client.authenticateNode(serverHub, request);
		
		
		
		return rootIdentity;
	}
}
