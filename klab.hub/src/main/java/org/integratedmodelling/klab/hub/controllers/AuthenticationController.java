package org.integratedmodelling.klab.hub.controllers;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.DecoderException;
import org.bouncycastle.openpgp.PGPException;
import org.integratedmodelling.klab.hub.manager.EngineAuthManager;
import org.integratedmodelling.klab.hub.manager.NodeAuthManager;
import org.integratedmodelling.klab.hub.manager.TokenManager;
import org.integratedmodelling.klab.rest.EngineAuthenticationRequest;
import org.integratedmodelling.klab.rest.EngineAuthenticationResponse;
import org.integratedmodelling.klab.rest.NodeAuthenticationRequest;
import org.integratedmodelling.klab.rest.NodeAuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth-cert")
public class AuthenticationController {
	
	@Autowired
	EngineAuthManager engineAuthManager;
	
	@Autowired
	NodeAuthManager nodeAuthManager;
	
	@Autowired
	TokenManager tokenManager;
	
	@PostMapping(value="/engine", produces = "application/json")
	public ResponseEntity<?> authenticateEngine(@RequestBody EngineAuthenticationRequest request,
			HttpServletRequest httpRequest) throws IOException, PGPException, DecoderException {
        String remoteAddr = "";

        if (httpRequest != null) {
            remoteAddr = httpRequest.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = httpRequest.getRemoteAddr();
            }
        }
		EngineAuthenticationResponse response = engineAuthManager.processEngineCert(request, remoteAddr);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping(value="/node", produces = "application/json")
	public ResponseEntity<?> authenticateNode(@RequestBody NodeAuthenticationRequest request,
			HttpServletRequest httpRequest) throws Exception {
        String remoteAddr = "";

        if (httpRequest != null) {
            remoteAddr = httpRequest.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = httpRequest.getRemoteAddr();
            }
        }
		NodeAuthenticationResponse response = nodeAuthManager.processNodeCert(request, remoteAddr);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
