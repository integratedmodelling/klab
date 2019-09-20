package org.integratedmodelling.klab.hub.manager;

import java.util.List;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.hub.authentication.HubAuthenticationManager;
import org.integratedmodelling.klab.hub.network.NetworkManager;
import org.integratedmodelling.klab.hub.security.NetworkKeyManager;
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
				processLocalNode(request);
			} else {
				break;	
			}
		default:
			if (IPUtils.isLocal(ip)) {
				//You are running locally with a hub, so it is assumed that the hub is a development hub
				processLocalNode(request);
			} else {
				processNode(request);
			}
			break;
		}
		return null;
	}

	private void processNode(NodeAuthenticationRequest request) {
		// TODO Auto-generated method stub
		
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
			node.getUrls().add("http://"+IPUtils.getLocalIp()+"/node");
		} catch (Exception e) {
			throw new KlabAuthorizationException(e.toString());
		}
		return node;
	}


}
