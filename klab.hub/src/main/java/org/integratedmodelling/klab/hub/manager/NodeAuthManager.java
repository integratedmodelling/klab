package org.integratedmodelling.klab.hub.manager;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.commons.codec.DecoderException;
import org.bouncycastle.openpgp.PGPException;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.hub.authentication.HubAuthenticationManager;
import org.integratedmodelling.klab.hub.exception.AuthenticationFailedException;
import org.integratedmodelling.klab.hub.models.KlabNode;
import org.integratedmodelling.klab.hub.network.NetworkManager;
import org.integratedmodelling.klab.hub.security.NetworkKeyManager;
import org.integratedmodelling.klab.auth.KlabCertificate;
import org.integratedmodelling.klab.auth.Node;
import org.integratedmodelling.klab.exceptions.KlabAuthorizationException;
import org.integratedmodelling.klab.rest.AuthenticatedIdentity;
import org.integratedmodelling.klab.rest.Group;
import org.integratedmodelling.klab.rest.IdentityReference;
import org.integratedmodelling.klab.rest.NodeAuthenticationRequest;
import org.integratedmodelling.klab.rest.NodeAuthenticationResponse;
import org.integratedmodelling.klab.utils.IPUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NodeAuthManager {
	
	@Autowired
	HubAuthenticationManager hubAuthenticationManager;
	
	@Autowired
	LicenseManager licenseManager;
	
	@Autowired
	KlabNodeManager klabNodeManager;
	
	@Autowired
	NetworkManager networkManager;
	
	public NodeAuthenticationResponse processNodeCert(NodeAuthenticationRequest request, String ip) {
		switch (request.getLevel()) {
		case TEST:
			if (IPUtils.isLocal(ip)) {
				return processLocalNode(request);
			} else {
				break;	
			}
		default:
			if (IPUtils.isLocalhost(ip)) {
				//You are running locally with a hub, so it is assumed that the hub is a development hub
				return processLocalNode(request);
			} else {
				return processNode(request,ip);
			}
		}
		return null;
	}

	private NodeAuthenticationResponse processNode(NodeAuthenticationRequest request, String ip) {
		Logging.INSTANCE.info(request.toString());
		DateTime now = DateTime.now();
		DateTime tomorrow = now.plusDays(90);
		INodeIdentity node = authenticateNodeCert(request.getCertificate());
		Logging.INSTANCE.info(node.getName());
		List<Group> Groups = klabNodeManager.getNodeGroups(request.getNodeName());
		node.getUrls().add(klabNodeManager.getNode(request.getNodeName()).getUrl());
		Logging.INSTANCE.info("authorized node " + node.getName());
		IdentityReference userIdentity = new IdentityReference(node.getName()
				,node.getParentIdentity().getEmailAddress(), now.toString());		
		AuthenticatedIdentity authenticatedIdentity = new AuthenticatedIdentity(userIdentity,
				Groups, tomorrow.toString(), node.getId());
		NodeAuthenticationResponse response = new NodeAuthenticationResponse(authenticatedIdentity,
				hubAuthenticationManager.getHubReference().getId(), Groups,
				NetworkKeyManager.INSTANCE.getEncodedPublicKey());
		networkManager.notifyAuthorizedNode(node, hubAuthenticationManager.getHubReference(), true);
		return response;
	}

	private INodeIdentity authenticateNodeCert(String certificate) {
		try {
			Properties certificateProperties = licenseManager.readCertFileContent(certificate);
			String nodename = certificateProperties.getProperty(KlabCertificate.KEY_NODENAME);
			String expiryString = certificateProperties.getProperty(KlabCertificate.KEY_EXPIRATION);
			DateTime expiry = DateTime.parse(expiryString);
			if (expiry.isBeforeNow()) {
				String msg = String.format("The cert file submitted for node %s is expired.", nodename);
				throw new AuthenticationFailedException(msg);
			}
			KlabNode node = klabNodeManager.getNode(nodename);
			Properties properties = licenseManager.getPropertiesString(node);
			certificateProperties.remove(KlabCertificate.KEY_EXPIRATION);
	        properties.remove(KlabCertificate.KEY_EXPIRATION);
	        if (certificateProperties.equals(properties)) {
	        	INodeIdentity ret = null;
	    		ret = new Node(hubAuthenticationManager.getHubName() + "." + nodename, hubAuthenticationManager.getPartner());
	    		return ret;
	        }
	        return null;
		} catch (IOException | PGPException | DecoderException e) {
			throw new AuthenticationFailedException(e.toString());
		}
	}

	private NodeAuthenticationResponse processLocalNode(NodeAuthenticationRequest request) {
		DateTime now = DateTime.now();
		DateTime tomorrow = now.plusDays(90);
		INodeIdentity node = authenticateLocalNodeCert(request.getNodeName());
		List<Group> Groups = klabNodeManager.getGroups();
		Logging.INSTANCE.info("authorized installed node " + node.getName());
		IdentityReference userIdentity = new IdentityReference(node.getName()
				,node.getParentIdentity().getEmailAddress(), now.toString());	
		AuthenticatedIdentity authenticatedIdentity = new AuthenticatedIdentity(userIdentity,
				Groups, tomorrow.toString(), node.getId());
		NodeAuthenticationResponse response = new NodeAuthenticationResponse(authenticatedIdentity,
				hubAuthenticationManager.getHubReference().getId(), Groups,
				NetworkKeyManager.INSTANCE.getEncodedPublicKey());
		networkManager.notifyAuthorizedNode(node, hubAuthenticationManager.getHubReference(), true);
		return response;
		}

	private INodeIdentity authenticateLocalNodeCert(String nodeName) {
		INodeIdentity node = new Node(hubAuthenticationManager.getHubName() + "." + nodeName, hubAuthenticationManager.getPartner());
		try {
			node.getUrls().add("http://"+IPUtils.getLocalIp()+":8287/node");
		} catch (Exception e) {
			throw new KlabAuthorizationException(e.toString());
		}
		return node;
	}


}
