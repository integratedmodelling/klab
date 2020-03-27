package org.integratedmodelling.klab.hub.license.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.NoSuchProviderException;
import java.util.Enumeration;
import java.util.Properties;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.bouncycastle.openpgp.PGPException;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.auth.KlabCertificate;
import org.integratedmodelling.klab.hub.api.BouncyLicense;
import org.integratedmodelling.klab.hub.api.EngineAuthResponeFactory;
import org.integratedmodelling.klab.hub.api.LicenseConfiguration;
import org.integratedmodelling.klab.hub.api.ProfileResource;
import org.integratedmodelling.klab.hub.api.PropertiesFactory;
import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.repository.LicenseConfigRepository;
import org.integratedmodelling.klab.hub.repository.MongoGroupRepository;
import org.integratedmodelling.klab.hub.users.services.UserProfileService;
import org.integratedmodelling.klab.rest.EngineAuthenticationRequest;
import org.integratedmodelling.klab.rest.EngineAuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EngineLicenseController extends LicenseController<EngineAuthenticationRequest>{
	
	private UserProfileService userProfileService;

	private LicenseConfigRepository licenseRepo;

	private MongoGroupRepository groupRepository;
	
	@Autowired
	EngineLicenseController(UserProfileService userProfileService,
			LicenseConfigRepository licenseRepo,
			MongoGroupRepository groupRepository) {
		this.userProfileService = userProfileService;
		this.licenseRepo = licenseRepo;
		this.groupRepository = groupRepository;
	}
	
	@GetMapping(value= API.HUB.USER_BASE_ID, params = "certificate")
	@PreAuthorize("authentication.getPrincipal() == #id")
	public void generateCertFile(@PathVariable("id") String id, HttpServletResponse response) throws IOException {
		ProfileResource profile = userProfileService.getCurrentUserProfile();
		LicenseConfiguration configuration = licenseRepo.findAll().get(0);

		Properties engineProperties = PropertiesFactory.fromProfile(profile, configuration).getProperties();


		byte[] certFileContent = new BouncyLicense().generate(engineProperties, configuration);;

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
		
	}
	
	@PostMapping(value= API.HUB.AUTHENTICATE_ENGINE)
	public ResponseEntity<EngineAuthenticationResponse> processCertificate(
			@RequestBody EngineAuthenticationRequest request,
			HttpServletRequest httpRequest) {
		String remoteAddr = "";

		if (httpRequest != null) {
			remoteAddr = httpRequest.getHeader("X-FORWARDED-FOR");
			if (remoteAddr == null || "".equals(remoteAddr)) {
				remoteAddr = httpRequest.getRemoteAddr();
			}
		}
		if(httpRequest.getHeader("test") != null) {
			remoteAddr = "128.0.0.1";
		}
		
		LicenseConfiguration config = licenseRepo.findByKeyString(request.getKey())
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
