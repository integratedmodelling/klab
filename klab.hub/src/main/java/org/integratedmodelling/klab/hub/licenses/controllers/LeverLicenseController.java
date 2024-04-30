package org.integratedmodelling.klab.hub.licenses.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.hub.licenses.dto.LeverAuthResponseFactory;
import org.integratedmodelling.klab.hub.licenses.dto.LicenseConfiguration;
import org.integratedmodelling.klab.hub.licenses.services.LeverService;
import org.integratedmodelling.klab.hub.licenses.services.LicenseConfigService;
import org.integratedmodelling.klab.hub.tokens.services.LeverAuthTokenService;
import org.integratedmodelling.klab.rest.LeverAuthenticationRequest;
import org.integratedmodelling.klab.rest.LeverAuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LeverLicenseController extends LicenseController<LeverAuthenticationRequest>{
	
	private LeverService leverService;
	private LicenseConfigService configService;
	private LeverAuthTokenService tokenService;
	
	@Autowired
	public LeverLicenseController(LeverService leverService,
			LicenseConfigService configService,
			LeverAuthTokenService tokenService)  {
		this.leverService = leverService;
		this.configService = configService;
		this.tokenService = tokenService;
	}

	@Override
	@GetMapping(value= API.HUB.LEVER_BASE_ID, params = "certificate")
	void generateCertFile(String id, String agreementId, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@PostMapping(value= API.HUB.AUTHENTICATE_LEVER)
	ResponseEntity<?> processCertificate(@RequestBody LeverAuthenticationRequest request, HttpServletRequest httpRequest) {
		String remoteAddr = "";

		if (httpRequest != null) {
			remoteAddr = httpRequest.getHeader("X-FORWARDED-FOR");
			if (remoteAddr == null || "".equals(remoteAddr)) {
				remoteAddr = httpRequest.getRemoteAddr();
			}
		}
		
		request.setIp(remoteAddr);
		/*
		 * This is so we can do production style tests and ignore local certificate processing
		 */
		if(httpRequest.getHeader("test") != null) {
			request.setCycle("production");
		}
		
		LicenseConfiguration config = configService.getConfigByKey(request.getKey());
		
		LeverAuthenticationResponse response;
		
		response = new LeverAuthResponseFactory().getResponse(request, config, leverService, tokenService);
		return new ResponseEntity<>(response, HttpStatus.OK);
		
	}
	
	

}
