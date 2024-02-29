package org.integratedmodelling.klab.hub.licenses.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.auth.KlabCertificate;
import org.integratedmodelling.klab.hub.licenses.dto.LicenseGenerator;
import org.integratedmodelling.klab.hub.licenses.dto.NodeAuthResponeFactory;
import org.integratedmodelling.klab.hub.licenses.services.LicenseConfigService;
import org.integratedmodelling.klab.hub.nodes.dtos.MongoNode;
import org.integratedmodelling.klab.hub.nodes.services.NodeService;
import org.integratedmodelling.klab.hub.repository.MongoGroupRepository;
import org.integratedmodelling.klab.rest.NodeAuthenticationRequest;
import org.integratedmodelling.klab.rest.NodeAuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NodeLicenseController{
	
	private NodeService nodeService;
    private LicenseGenerator licenseGenerator;
    private NodeAuthResponeFactory nodeAuthResponeFactory;
	
	@Autowired
	NodeLicenseController(NodeService nodeService,
			LicenseConfigService configService,
			MongoGroupRepository groupRepository) {
		this.nodeService = nodeService;
		this.licenseGenerator = new LicenseGenerator(configService);
		this.nodeAuthResponeFactory = new NodeAuthResponeFactory(nodeService,groupRepository,configService);
	}
	
	@GetMapping(value= API.HUB.NODE_BASE_ID, params = "certificate")
	@RolesAllowed({ "ROLE_SYSTEM" })
	public void generateCertFile(@PathVariable("id") String id, HttpServletResponse response) throws IOException {
		MongoNode node = nodeService.getByName(id);

		byte[] certFileContent = licenseGenerator.generate(node, null);

		String certFileString = String.format("attachment; filename=%s", KlabCertificate.DEFAULT_NODE_CERTIFICATE_FILENAME);

		response.setHeader("Content-disposition", certFileString);
		response.setContentType("text/plain;charset=utf-8");
		response.setContentLength(certFileContent.length);
		try {
			IOUtils.copy(new ByteArrayInputStream(certFileContent), response.getOutputStream());
			response.flushBuffer();
		} finally {
			response.getOutputStream().close();
		}
		//IOUtils.closeQuietly(response.getOutputStream());
	}
	
	@PostMapping(value= API.HUB.AUTHENTICATE_NODE)
	public ResponseEntity<NodeAuthenticationResponse> processCertificate(@RequestBody NodeAuthenticationRequest request,
			HttpServletRequest httpRequest) {
		String remoteAddr = "";
		if (httpRequest != null) {
			remoteAddr = httpRequest.getHeader("X-FORWARDED-FOR");
			if (remoteAddr == null || "".equals(remoteAddr)) {
				remoteAddr = httpRequest.getRemoteAddr();
			}
		}

		NodeAuthenticationResponse response = nodeAuthResponeFactory.getRespone(request, remoteAddr);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
