package org.integratedmodelling.klab.hub.license.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.hub.repository.LicenseConfigRepository;
import org.integratedmodelling.klab.hub.repository.MongoLeverRepository;
import org.integratedmodelling.klab.rest.LeverAuthenticationRequest;
import org.integratedmodelling.klab.rest.LeverAuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LeverLicenseController extends LicenseController<LeverAuthenticationRequest>{
	private LicenseConfigRepository licenseRepo;
	private MongoLeverRepository leverRepo;
	
	@Autowired
	public LeverLicenseController(LicenseConfigRepository licenseRepo,
			MongoLeverRepository leverRepo)  {
		this.licenseRepo = licenseRepo;
		this.leverRepo = leverRepo;
	}

	@Override
	@GetMapping(value= API.HUB.LEVER_BASE_ID, params = "certificate")
	void generateCertFile(String id, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@GetMapping(value= API.HUB.AUTHENTICATE_LEVER)
	ResponseEntity<?> processCertificate(LeverAuthenticationRequest request, HttpServletRequest httpRequest) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
