package org.integratedmodelling.klab.hub.license.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.bouncycastle.openpgp.PGPException;
import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.licenses.services.LicenseService;
import org.integratedmodelling.klab.hub.nodes.MongoNode;
import org.integratedmodelling.klab.hub.nodes.services.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2")
public class LicenseController {
	
	@Autowired
	NodeService nodeService;
	
	@Autowired
	LicenseService licenseService;
	
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

}
