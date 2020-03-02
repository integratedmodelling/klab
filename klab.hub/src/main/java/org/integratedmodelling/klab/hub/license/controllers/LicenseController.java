package org.integratedmodelling.klab.hub.license.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.NoSuchProviderException;
import java.util.Properties;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.bouncycastle.openpgp.PGPException;
import org.integratedmodelling.klab.auth.KlabCertificate;
import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.license.BouncyLicense;
import org.integratedmodelling.klab.hub.license.EngineAuthResponeFactory;
import org.integratedmodelling.klab.hub.license.LicenseConfiguration;
import org.integratedmodelling.klab.hub.license.NodeAuthResponeFactory;
import org.integratedmodelling.klab.hub.license.PropertiesFactory;
import org.integratedmodelling.klab.hub.manager.EmailManager;
import org.integratedmodelling.klab.hub.nodes.MongoNode;
import org.integratedmodelling.klab.hub.nodes.services.NodeService;
import org.integratedmodelling.klab.hub.repository.LicenseConfigRepository;
import org.integratedmodelling.klab.hub.repository.MongoGroupRepository;
import org.integratedmodelling.klab.hub.tokens.services.RegistrationTokenService;
import org.integratedmodelling.klab.hub.users.ProfileResource;
import org.integratedmodelling.klab.hub.users.services.UserProfileService;
import org.integratedmodelling.klab.hub.users.services.UserRegistrationService;
import org.integratedmodelling.klab.rest.EngineAuthenticationRequest;
import org.integratedmodelling.klab.rest.EngineAuthenticationResponse;
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

	private NodeService nodeService;
	
	private UserProfileService userProfileService;

	private LicenseConfigRepository licenseRepo;

	private MongoGroupRepository groupRepository;
	
	@Autowired
	LicenseController(NodeService nodeService,
			UserProfileService userProfileService,
			LicenseConfigRepository licenseRepo,
			MongoGroupRepository groupRepository) {
		this.nodeService = nodeService;
		this.userProfileService = userProfileService;
		this.licenseRepo = licenseRepo;
		this.groupRepository = groupRepository;
	}

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

		LicenseConfiguration config = licenseRepo.findByKeyString(request.getNodeKey())
				.orElseGet(() -> new LicenseConfiguration());

		NodeAuthenticationResponse response;

		try {
			response = new NodeAuthResponeFactory().getRespone(request, remoteAddr, config, nodeService, groupRepository);
		} catch (NoSuchProviderException | IOException | PGPException e) {
			throw new BadRequestException("Make a more useful message to help fiqure out what happened.");
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping(value= "engines/{username}", params = "certificate")
	@RolesAllowed({ "authentication.getPrincipal() == #username" })
	public void generateEngineCertFile(@PathVariable("username") String username, HttpServletResponse response) throws IOException {
		ProfileResource profile = userProfileService.getCurrentUserProfile();
		LicenseConfiguration configuration = licenseRepo.findAll().get(0);

		Properties engineProperties = PropertiesFactory.fromProfile(profile, configuration).getProperties();


		byte[] certFileContent = new BouncyLicense().generate(engineProperties, configuration);;

		String certFileString = String.format("attachment; filename=%s", KlabCertificate.DEFAULT_NODE_CERTIFICATE_FILENAME);

		response.setHeader("Content-disposition", certFileString);
		response.setContentType("text/plain;charset=utf-8");
		response.setContentLength(certFileContent.length);
		IOUtils.copy(new ByteArrayInputStream(certFileContent), response.getOutputStream());
		response.flushBuffer();
		IOUtils.closeQuietly(response.getOutputStream());
	}
	
	@PostMapping(value= "engines/auth-cert")
	public ResponseEntity<EngineAuthenticationResponse> processEngineCertificate(
			@RequestBody EngineAuthenticationRequest request,
			HttpServletRequest httpRequest) {
		String remoteAddr = "";

		if (httpRequest != null) {
			remoteAddr = httpRequest.getHeader("X-FORWARDED-FOR");
			if (remoteAddr == null || "".equals(remoteAddr)) {
				remoteAddr = httpRequest.getRemoteAddr();
			}
		}

		LicenseConfiguration config = licenseRepo.findByKeyString(request.getUserKey())
				.orElseGet(() -> new LicenseConfiguration());

		EngineAuthenticationResponse response;
		
		try {
			response = new EngineAuthResponeFactory()
				.getRespone(request, remoteAddr, config, userProfileService, groupRepository);
		} catch (NoSuchProviderException | IOException | PGPException e) {
			throw new BadRequestException("Make a more useful message to help fiqure out what happened.");
		}
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
