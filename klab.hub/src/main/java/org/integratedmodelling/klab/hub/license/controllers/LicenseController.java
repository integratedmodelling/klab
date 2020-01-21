package org.integratedmodelling.klab.hub.license.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Set;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.bouncycastle.openpgp.PGPException;
import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.auth.Hub;
import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.groups.commands.GetNodesGroups;
import org.integratedmodelling.klab.hub.license.commands.EvaluateNodeLicenseProperties;
import org.integratedmodelling.klab.hub.licenses.services.LicenseService;
import org.integratedmodelling.klab.hub.nodes.MongoNode;
import org.integratedmodelling.klab.hub.nodes.commands.GetNodeAuthenticatedIdentity;
import org.integratedmodelling.klab.hub.nodes.services.NodeService;
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
@RequestMapping("/api/v2")
public class LicenseController {
	
	@Autowired
	NodeService nodeService;
	
	@Autowired
	LicenseService licenseService;
	
	@Autowired
	MongoGroupRepository groupRepository;
	
	@GetMapping(value= "/nodes/{id}", params = "certificate")
	@RolesAllowed({ "ROLE_SYSTEM" })
	public void generateNodeCertFile(@PathVariable("id") String id, HttpServletResponse response) throws IOException {
		MongoNode node = nodeService.getNode(id);
		byte[] certFileContent = null;
		try {
			certFileContent = licenseService.generateCertFile(node);
		} catch (GeneralSecurityException | PGPException e) {
			throw new BadRequestException("Error Creating node certificate byte String");
		}
		String certFileString = String.format("attachment; filename=%s", licenseService.get_NODE_CERT_FILE_NAME());
		response.setHeader("Content-disposition", certFileString);
		response.setContentType("text/plain;charset=utf-8");
		response.setContentLength(certFileContent.length);
		IOUtils.copy(new ByteArrayInputStream(certFileContent), response.getOutputStream());
		response.flushBuffer();
		IOUtils.closeQuietly(response.getOutputStream());
	}
	
	@PostMapping(value= "/nodes/auth-cert")
	@RolesAllowed({ "ROLE_SYSTEM" })
	public ResponseEntity<NodeAuthenticationResponse> processNodeCertificate(@RequestBody NodeAuthenticationRequest request, HttpServletRequest httpRequest) {
        String remoteAddr = "";

        if (httpRequest != null) {
            remoteAddr = httpRequest.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = httpRequest.getRemoteAddr();
            }
        }
        
        MongoNode node = nodeService.getNode(request.getNodeName());
        
        boolean compare = 
        		new EvaluateNodeLicenseProperties(
        				node, 
        				licenseService, 
        				request.getCertificate()).execute();

        if(compare) {
        	Set<Group> groups = new GetNodesGroups(node, groupRepository).execute();
        	AuthenticatedIdentity authenticatedIdentity = 
        			new GetNodeAuthenticatedIdentity(node, groups).execute();
        	
    		NodeAuthenticationResponse response = new NodeAuthenticationResponse(
    				authenticatedIdentity,
    				Authentication.INSTANCE.getAuthenticatedIdentity(Hub.class).getId(),
    				groups,
    				NetworkKeyManager.INSTANCE.getEncodedPublicKey());
    		
    		return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
        	NodeAuthenticationResponse response = new NodeAuthenticationResponse();
        	return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }

	}

}
