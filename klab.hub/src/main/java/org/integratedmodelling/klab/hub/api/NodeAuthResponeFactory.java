package org.integratedmodelling.klab.hub.api;

import java.io.IOException;
import java.net.SocketException;
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
import org.integratedmodelling.klab.hub.commands.GetINodeIdentity;
import org.integratedmodelling.klab.hub.commands.GetNodeAuthenticatedIdentity;
import org.integratedmodelling.klab.hub.commands.GetNodesGroups;
import org.integratedmodelling.klab.hub.exception.CertificateCipherExcepetion;
import org.integratedmodelling.klab.hub.licenses.services.LicenseConfigService;
import org.integratedmodelling.klab.hub.network.NodeNetworkManager;
import org.integratedmodelling.klab.hub.nodes.services.NodeService;
import org.integratedmodelling.klab.hub.repository.MongoGroupRepository;
import org.integratedmodelling.klab.hub.security.NetworkKeyManager;
import org.integratedmodelling.klab.hub.utils.IPUtils;
import org.integratedmodelling.klab.rest.AuthenticatedIdentity;
import org.integratedmodelling.klab.rest.Group;
import org.integratedmodelling.klab.rest.IdentityReference;
import org.integratedmodelling.klab.rest.NodeAuthenticationRequest;
import org.integratedmodelling.klab.rest.NodeAuthenticationResponse;
import org.joda.time.DateTime;

import com.fasterxml.jackson.databind.ObjectMapper;

public class NodeAuthResponeFactory {
    
    private NodeService nodeService;
    private MongoGroupRepository groupRepository;
    private LicenseConfigService configService;
    private ObjectMapper objectMapper = new ObjectMapper();

    public NodeAuthResponeFactory(NodeService nodeService,
            MongoGroupRepository groupRepository,
            LicenseConfigService configService) {
        this.nodeService = nodeService;
        this.groupRepository = groupRepository;
        this.configService = configService;
    }
	
	public NodeAuthenticationResponse getRespone(NodeAuthenticationRequest request,String ip) {
		
		switch (request.getLevel()) {
		case TEST:
			if (IPUtils.isLocal(ip)) {
				return local(request);
			} else {
				break;	
			}
		default:
			if (IPUtils.isLocalhost(ip)) {
				//You are running locally with a hub, so it is assumed that the hub is a development hub
				return local(request);
			} else {
				MongoNode node = nodeService.getByName(request.getName());
				Set<Group> groups = new GetNodesGroups(node).execute();
				LicenseConfiguration config = configService.getConfigByKey(request.getKey());
				NodeAuthenticationResponse response = process(request.getCertificate(),node, groups, config);
				if(response.getUserData() != null) {
		    		node.setLastConnection();
		    		nodeService.update(node);
				}
				//networkManager.notifyAuthorizedNode(node, true);
				return response;
			}
		}
		return null;
	}
	
	private NodeAuthenticationResponse process(String cipher, MongoNode node, Set<Group> groups,
			LicenseConfiguration configuration) {
		Properties nodeProperties = PropertiesFactory.fromNode(node, configuration).getProperties();
		Properties cipherProperties = null;
		try {
			cipherProperties = new CipherProperties().getCipherProperties(configuration, cipher);
		} catch (NoSuchProviderException | IOException | PGPException e) {
			throw new CertificateCipherExcepetion(node.getName());
		}
        nodeProperties.remove(KlabCertificate.KEY_EXPIRATION);
        cipherProperties.remove(KlabCertificate.KEY_EXPIRATION);

        if(nodeProperties.equals(cipherProperties)) {
        	Logging.INSTANCE.info("authorized installed node " + node.getName());
        	
        	INodeIdentity nodeIdentity = new GetINodeIdentity(node).execute();
        	Logging.INSTANCE.info(nodeIdentity.toString());
        	
        	AuthenticatedIdentity authenticatedIdentity = 
        			new GetNodeAuthenticatedIdentity(nodeIdentity, groups).execute();
        	
        	
    		NodeAuthenticationResponse response = new NodeAuthenticationResponse(
    				authenticatedIdentity,
    				Authentication.INSTANCE.getAuthenticatedIdentity(Hub.class).getName(),
    				groups,
    				NetworkKeyManager.INSTANCE.getEncodedPublicKey());
        	NodeNetworkManager.INSTANCE.notifyAuthorizedNode(nodeIdentity, true);
    		return response;
        } else {
        	return new NodeAuthenticationResponse();
        }
	}
	
	private NodeAuthenticationResponse local(NodeAuthenticationRequest request) {
		DateTime now = DateTime.now();
		DateTime tomorrow = now.plusDays(90);
		Hub hub = Authentication.INSTANCE.getAuthenticatedIdentity(Hub.class);
		
		List<MongoGroup> mongoGroups = groupRepository.findAll();
		Set<Group> groups = new HashSet<>();
		
		mongoGroups.forEach(
				mongoGroup -> groups.add(objectMapper.convertValue(mongoGroup, Group.class)));
		
		INodeIdentity node = authenticateLocal(request.getName());

		Logging.INSTANCE.info("authorized installed node " + node.getName());
		
		IdentityReference userIdentity = 
				new IdentityReference(
						node.getName(),
						node.getParentIdentity().getEmailAddress(), 
						now.toString());
		
		AuthenticatedIdentity authenticatedIdentity = 
				new AuthenticatedIdentity(
						userIdentity,
						groups,
						tomorrow.toString(),
						node.getId());
		
		NodeAuthenticationResponse response = 
				new NodeAuthenticationResponse(
						authenticatedIdentity,
						hub.getName(),
						groups,
						NetworkKeyManager.INSTANCE.getEncodedPublicKey());
		
		NodeNetworkManager.INSTANCE.notifyAuthorizedNode(node, true);
		return response;
	}
	
	private INodeIdentity authenticateLocal(String name) {
		Hub hub = Authentication.INSTANCE.getAuthenticatedIdentity(Hub.class);
		INodeIdentity node = new Node(hub.getName() + "." + name, hub.getParentIdentity());
		try {
			node.getUrls().add("http://"+IPUtils.getLocalIp()+":8287/node");
		} catch (SocketException e) {
			throw new KlabAuthorizationException(node.getName());
		}	
		return node;
	}

}
