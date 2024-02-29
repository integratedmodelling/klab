package org.integratedmodelling.klab.hub.licenses.controllers;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.integratedmodelling.klab.rest.AuthenticationRequest;
import org.springframework.http.ResponseEntity;

/**
* This is an abstract license controller for all derived license controllers.
* 
* Its a small refactoring for now, with the possibility of creating a more
* cohesive authentication process.  Each concrete class will use its respective
* AuthenticationRequest as a element of the class.
*/
public abstract class LicenseController<R extends AuthenticationRequest>{
	
	/**
	* Creates a license for the specified id.
	* The response is used to stream back the new certificate as a download.
	* 
	*/
	abstract void generateCertFile(String id, String agreementId, HttpServletResponse response) throws IOException;
	/**
	* Creates a license for the specified id.
	* The response is used to stream back the new certificate in download form
	 * @throws MessagingException 
	* 
	*/
	abstract ResponseEntity<?> processCertificate(R request,
			HttpServletRequest httpRequest) throws MessagingException;

}
