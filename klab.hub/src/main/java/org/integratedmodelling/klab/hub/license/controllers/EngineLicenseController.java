package org.integratedmodelling.klab.hub.license.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.NoSuchProviderException;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.bouncycastle.openpgp.PGPException;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.ICertificate;
import org.integratedmodelling.klab.auth.KlabCertificate;
import org.integratedmodelling.klab.hub.api.EngineAuthResponeFactory;
import org.integratedmodelling.klab.hub.api.LicenseGenerator;
import org.integratedmodelling.klab.hub.api.ProfileResource;
import org.integratedmodelling.klab.hub.emails.services.EmailManager;
import org.integratedmodelling.klab.hub.exception.LicenseExpiredException;
import org.integratedmodelling.klab.hub.exception.LicenseGenerationError;
import org.integratedmodelling.klab.hub.licenses.services.LicenseConfigService;
import org.integratedmodelling.klab.hub.repository.MongoGroupRepository;
//import org.integratedmodelling.klab.hub.tokens.services.UserAuthTokenService;
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

import com.google.gson.Gson;
import com.google.gson.JsonObject;

@RestController
public class EngineLicenseController extends LicenseController<EngineAuthenticationRequest>{
	
	private EmailManager emailManager;
	
	private EngineAuthResponeFactory authFactory;

    private UserProfileService userProfileService;

    private LicenseGenerator licenseGenerator;
	
	@Autowired
	EngineLicenseController(UserProfileService userProfileService,
			LicenseConfigService configService,
			MongoGroupRepository groupRepository,
		    EmailManager emailManager/*,
		    UserAuthTokenService authTokenService*/) {
	    this.authFactory = new EngineAuthResponeFactory(userProfileService, groupRepository, configService/*, authTokenService*/);
	    this.licenseGenerator = new LicenseGenerator(configService);
		this.userProfileService = userProfileService;
		this.emailManager = emailManager;
	}
	
	@GetMapping(value= API.HUB.USER_BASE_ID, params = "certificate")
	@PreAuthorize("authentication.getPrincipal() == #id")
	public void generateCertFile(@PathVariable("id") String id, HttpServletResponse response) throws IOException {
	    
		ProfileResource profile = userProfileService.getCurrentUserProfile();
		byte[] certFileContent = licenseGenerator.generate(profile, null);
		String certFileString = String.format("attachment; filename=%s", KlabCertificate.DEFAULT_ENGINE_CERTIFICATE_FILENAME);
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
			HttpServletRequest httpRequest) throws MessagingException {
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
		
		EngineAuthenticationResponse response = null;
		
		try {
			response = authFactory
				.getRespone(request, remoteAddr);
		} catch (NoSuchProviderException | IOException | PGPException e) {
			throw new LicenseGenerationError("Issue in authenticating certificate.");
		} catch (LicenseExpiredException e) {
			emailManager.expiredLicenseEmail(request.getEmail());
		}
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping(value= API.HUB.LEGACY_AUTHENTICATE_ENGINE)
	public ResponseEntity<EngineAuthenticationResponse> processLegacyEndpoint(HttpServletRequest request) throws IOException, MessagingException {
	    final String str = IOUtils.toString(request.getInputStream(), Charset.defaultCharset());
	    JsonObject translate = new Gson().fromJson(str, JsonObject.class);
	    EngineAuthenticationRequest newRequest = new EngineAuthenticationRequest();
	    newRequest.setCertificate(translate.get("certificate").getAsString());
	    newRequest.setName(translate.get("username").getAsString());
	    newRequest.setKey(translate.get("userKey").getAsString());
	    newRequest.setUserType(translate.get("userType").getAsString());
	    newRequest.setLevel(ICertificate.Level.USER);
	    return processCertificate(newRequest, request);
	}

}
