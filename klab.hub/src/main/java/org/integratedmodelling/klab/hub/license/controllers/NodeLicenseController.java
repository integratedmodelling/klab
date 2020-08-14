package org.integratedmodelling.klab.hub.license.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.auth.KlabCertificate;
import org.integratedmodelling.klab.hub.api.BouncyLicense;
import org.integratedmodelling.klab.hub.api.LicenseConfiguration;
import org.integratedmodelling.klab.hub.api.MongoNode;
import org.integratedmodelling.klab.hub.api.NodeAuthResponeFactory;
import org.integratedmodelling.klab.hub.api.PropertiesFactory;
import org.integratedmodelling.klab.hub.licenses.services.LicenseConfigService;
import org.integratedmodelling.klab.hub.nodes.services.NodeService;
import org.integratedmodelling.klab.hub.repository.LicenseConfigRepository;
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
public class NodeLicenseController extends LicenseController<NodeAuthenticationRequest>{
	
	private NodeService nodeService;
	private LicenseConfigService configService;
	private MongoGroupRepository groupRepository;
	
	@Autowired
	NodeLicenseController(NodeService nodeService,
			LicenseConfigService configService,
			MongoGroupRepository groupRepository) {
		this.nodeService = nodeService;
		this.configService = configService;
		this.groupRepository = groupRepository;
	}
	
	@GetMapping(value= API.HUB.NODE_BASE_ID, params = "certificate")
	@RolesAllowed({ "ROLE_SYSTEM" })
	public void generateCertFile(@PathVariable("id") String id, HttpServletResponse response) throws IOException {
		MongoNode node = nodeService.getByName(id);
		LicenseConfiguration configuration = configService.getDefaultConfig();

		Properties nodeProperties = PropertiesFactory.fromNode(node, configuration).getProperties();


		byte[] certFileContent = new BouncyLicense().generate(nodeProperties, configuration);;

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

		LicenseConfiguration config = configService.getConfigByKey(request.getKey());

		NodeAuthenticationResponse response;

		response = new NodeAuthResponeFactory().getRespone(request, remoteAddr, config, nodeService, groupRepository);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
