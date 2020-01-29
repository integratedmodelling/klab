package org.integratedmodelling.klab.hub.license;

import java.io.IOException;
import java.security.NoSuchProviderException;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.bouncycastle.openpgp.PGPException;
import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.auth.Hub;
import org.integratedmodelling.klab.auth.KlabCertificate;
import org.integratedmodelling.klab.auth.Node;
import org.integratedmodelling.klab.exceptions.KlabAuthorizationException;
import org.integratedmodelling.klab.hub.groups.MongoGroup;
import org.integratedmodelling.klab.hub.groups.MongoGroupAdapter;
import org.integratedmodelling.klab.hub.groups.commands.GetNodesGroups;
import org.integratedmodelling.klab.hub.nodes.MongoNode;
import org.integratedmodelling.klab.hub.nodes.commands.GetNodeAuthenticatedIdentity;
import org.integratedmodelling.klab.hub.nodes.services.NodeService;
import org.integratedmodelling.klab.hub.repository.MongoGroupRepository;
import org.integratedmodelling.klab.hub.security.NetworkKeyManager;
import org.integratedmodelling.klab.rest.AuthenticatedIdentity;
import org.integratedmodelling.klab.rest.Group;
import org.integratedmodelling.klab.rest.IdentityReference;
import org.integratedmodelling.klab.rest.NodeAuthenticationRequest;
import org.integratedmodelling.klab.rest.NodeAuthenticationResponse;
import org.integratedmodelling.klab.utils.IPUtils;
import org.joda.time.DateTime;

public class NodeAuthResponeFactory {
	
	public NodeAuthenticationResponse getRespone(
			NodeAuthenticationRequest request,
			String ip,
			LicenseConfiguration config, 
			NodeService nodeService, 
			MongoGroupRepository groupRepo) throws NoSuchProviderException, IOException, PGPException {
		
		switch (request.getLevel()) {
		case TEST:
			if (IPUtils.isLocal(ip)) {
				return localNode(request, groupRepo);
			} else {
				break;	
			}
		default:
//			if (IPUtils.isLocalhost(ip)) {
//				//You are running locally with a hub, so it is assumed that the hub is a development hub
//				return localNode(request, groupRepo);
//			} else {
				MongoNode node = nodeService.getNode(request.getNodeName());
				Set<Group> groups = new GetNodesGroups(node, groupRepo).execute();
				NodeAuthenticationResponse response = processNode(request.getCertificate(),node, groups, config);
				if(response.getUserData() != null) {
		    		node.setLastNodeConnection();
		    		nodeService.updateNode(node);
				}
				//networkManager.notifyAuthorizedNode(node, true);
				return response;
			}
//		}
		return null;		
	}
	
	private NodeAuthenticationResponse processNode(String cipher, MongoNode node, Set<Group> groups,
			LicenseConfiguration configuration) throws NoSuchProviderException, IOException, PGPException {
		Properties nodeProperties = PropertiesFactory.fromNode(node, configuration).getProperties();
		Properties cipherProperties =  new CipherProperties().getCipherProperties(configuration, cipher);
        nodeProperties.remove(KlabCertificate.KEY_EXPIRATION);
        cipherProperties.remove(KlabCertificate.KEY_EXPIRATION);

        if(nodeProperties.equals(cipherProperties)) {
        	
        	AuthenticatedIdentity authenticatedIdentity = 
        			new GetNodeAuthenticatedIdentity(node, groups).execute();
        	
    		NodeAuthenticationResponse response = new NodeAuthenticationResponse(
    				authenticatedIdentity,
    				Authentication.INSTANCE.getAuthenticatedIdentity(Hub.class).getId(),
    				groups,
    				NetworkKeyManager.INSTANCE.getEncodedPublicKey());    
    		return response;
        } else {
        	return new NodeAuthenticationResponse();
        }
	}
	
	private NodeAuthenticationResponse localNode(NodeAuthenticationRequest request, MongoGroupRepository groupRepository) {
		DateTime now = DateTime.now();
		DateTime tomorrow = now.plusDays(90);
		Hub hub = Authentication.INSTANCE.getAuthenticatedIdentity(Hub.class);
		
		List<MongoGroup> mongoGroups = groupRepository.findAll();
		Set<Group> groups = new HashSet<>();
		
		mongoGroups.forEach(
				mongoGroup -> groups.add(new MongoGroupAdapter(mongoGroup).convertGroup()));
		
		INodeIdentity node = authenticateLocalNodeCert(request.getNodeName());

		Logging.INSTANCE.info("authorized installed node " + node.getName());
		IdentityReference userIdentity = new IdentityReference(node.getName()
				,node.getParentIdentity().getEmailAddress(), now.toString());	
		AuthenticatedIdentity authenticatedIdentity = new AuthenticatedIdentity(userIdentity,
				groups, tomorrow.toString(), node.getId());
		NodeAuthenticationResponse response = new NodeAuthenticationResponse(authenticatedIdentity,
				hub.getId(), groups,
				NetworkKeyManager.INSTANCE.getEncodedPublicKey());
		//TODO
		//networkManager.notifyAuthorizedNode(node, true);
		return response;
	}
	
	private INodeIdentity authenticateLocalNodeCert(String nodeName) {
		Hub hub = Authentication.INSTANCE.getAuthenticatedIdentity(Hub.class);
		INodeIdentity node = new Node(hub.getName() + "." + nodeName, hub.getParentIdentity());
		try {
			node.getUrls().add("http://"+IPUtils.getLocalIp()+":8287/node");
		} catch (Exception e) {
			throw new KlabAuthorizationException(e.toString());
		}
		return node;
	}

}
