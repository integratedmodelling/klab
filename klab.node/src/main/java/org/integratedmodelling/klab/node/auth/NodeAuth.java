package org.integratedmodelling.klab.node.auth;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

import org.integratedmodelling.klab.api.auth.ICertificate;
import org.integratedmodelling.klab.api.auth.IPartnerIdentity;
import org.integratedmodelling.klab.auth.KlabCertificate;
import org.integratedmodelling.klab.communication.client.Client;
import org.integratedmodelling.klab.exceptions.KlabAuthorizationException;
import org.integratedmodelling.klab.rest.Group;
import org.integratedmodelling.klab.rest.NodeAuthenticationRequest;
import org.integratedmodelling.klab.rest.NodeAuthenticationResponse;

public enum NodeAuth {

	INSTANCE;

	PublicKey publicKey;
	Set<Group> groups = new HashSet<>();

	IPartnerIdentity rootIdentity;
	// if this is set, use instead of whatever is in the certificate
	String authenticatingHub;
	Client client = Client.create();
	private String publicKeyBase64;

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
		this.publicKeyBase64 =response.getPublicKey();

		try {
			byte publicKeyData[] = Base64.getDecoder().decode(response.getPublicKey());
			X509EncodedKeySpec spec = new X509EncodedKeySpec(publicKeyData);
			KeyFactory kf = KeyFactory.getInstance("RSA");
			this.publicKey = kf.generatePublic(spec);
		} catch (Exception e) {
			throw new KlabAuthorizationException("invalid public key sent by hub");
		}

		this.groups.addAll(response.getGroups());

		return rootIdentity;
	}

	public String getSecret() {
		return publicKeyBase64;
	}
	
	public PublicKey getPublicKey() {
		return publicKey;
	}
}
