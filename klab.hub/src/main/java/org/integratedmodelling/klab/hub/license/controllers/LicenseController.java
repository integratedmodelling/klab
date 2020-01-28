package org.integratedmodelling.klab.hub.license.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.NoSuchProviderException;
import java.util.Properties;
import java.util.Set;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.bouncycastle.openpgp.PGPException;
import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.auth.Hub;
import org.integratedmodelling.klab.auth.KlabCertificate;
import org.integratedmodelling.klab.hub.groups.commands.GetNodesGroups;
import org.integratedmodelling.klab.hub.license.BouncyLicense;
import org.integratedmodelling.klab.hub.license.CipherProperties;
import org.integratedmodelling.klab.hub.license.LicenseConfiguration;
import org.integratedmodelling.klab.hub.license.PropertiesFactory;
import org.integratedmodelling.klab.hub.nodes.MongoNode;
import org.integratedmodelling.klab.hub.nodes.commands.GetNodeAuthenticatedIdentity;
import org.integratedmodelling.klab.hub.nodes.services.NodeService;
import org.integratedmodelling.klab.hub.repository.LicenseConfigRepository;
import org.integratedmodelling.klab.hub.repository.MongoGroupRepository;
import org.integratedmodelling.klab.hub.security.NetworkKeyManager;
import org.integratedmodelling.klab.rest.AuthenticatedIdentity;
import org.integratedmodelling.klab.rest.Group;
import org.integratedmodelling.klab.rest.NodeAuthenticationRequest;
import org.integratedmodelling.klab.rest.NodeAuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/")
public class LicenseController {
	
	@Autowired
	NodeService nodeService;
	
	@Autowired
	LicenseConfigRepository licenseRepo;
	
	@Autowired
	MongoGroupRepository groupRepository;
	
	@GetMapping(value= "nodes/{id}", params = "certificate")
	@RolesAllowed({ "ROLE_SYSTEM" })
	public void generateNodeCertFile(@PathVariable("id") String id, HttpServletResponse response) throws IOException {
		MongoNode node = nodeService.getNode(id);
		LicenseConfiguration configuration = licenseRepo.findAll().get(0);
		
		Properties nodeProperties = PropertiesFactory.fromNode(node, configuration).getProperties();

		
		byte[] certFileContent = new BouncyLicense().generate(nodeProperties, configuration);;
		
		String certFileString = String.format("attachment; filename=%s", KlabCertificate.DEFAULT_NODE_CERTIFICATE_FILENAME);
		
		response.setHeader("Content-disposition", certFileString);
		response.setContentType("text/plain;charset=utf-8");
		response.setContentLength(certFileContent.length);
		IOUtils.copy(new ByteArrayInputStream(certFileContent), response.getOutputStream());
		response.flushBuffer();
		IOUtils.closeQuietly(response.getOutputStream());
	}
	
	@PostMapping(value= "nodes/auth-cert")
	public ResponseEntity<NodeAuthenticationResponse> processNodeCertificate(
			@RequestBody NodeAuthenticationRequest request, 
			HttpServletRequest httpRequest) {
		
		String remoteAddr = "";

        if (httpRequest != null) {
            remoteAddr = httpRequest.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = httpRequest.getRemoteAddr();
            }
        }
        
        LicenseConfiguration configuration = licenseRepo.findByKeyString(request.getNodeKey()).get();
        
        MongoNode node = nodeService.getNode(request.getNodeName());
        
        Properties nodeProperties = PropertiesFactory.fromNode(node, configuration).getProperties();
        
        Properties cipherProperties = null;
        
		try {
			cipherProperties = new CipherProperties().getCipherProperties(configuration, request.getCertificate());
		} catch (NoSuchProviderException | IOException | PGPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        nodeProperties.remove(KlabCertificate.KEY_EXPIRATION);
        cipherProperties.remove(KlabCertificate.KEY_EXPIRATION);

        if(nodeProperties.equals(cipherProperties)) {
        	
        	Set<Group> groups = new GetNodesGroups(node, groupRepository).execute();
        	AuthenticatedIdentity authenticatedIdentity = 
        			new GetNodeAuthenticatedIdentity(node, groups).execute();
        	
    		NodeAuthenticationResponse response = new NodeAuthenticationResponse(
    				authenticatedIdentity,
    				Authentication.INSTANCE.getAuthenticatedIdentity(Hub.class).getId(),
    				groups,
    				NetworkKeyManager.INSTANCE.getEncodedPublicKey());
    		
    		node.setLastNodeConnection();
    		nodeService.updateNode(node);
    		
    		return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
        	NodeAuthenticationResponse response = new NodeAuthenticationResponse();
        	return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }

	}

}
