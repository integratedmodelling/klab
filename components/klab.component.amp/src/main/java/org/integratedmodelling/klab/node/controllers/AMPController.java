package org.integratedmodelling.klab.node.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.integratedmodelling.amp.API.AMP;
import org.integratedmodelling.amp.annotation.AnnotationManager;
import org.integratedmodelling.klab.api.auth.Roles;
import org.integratedmodelling.klab.rest.TicketResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.jsonldjava.utils.JsonUtils;

@RestController
@CrossOrigin(origins = "*")
@Secured(Roles.ENGINE)
public class AMPController implements AMP {

//	@Autowired
//	AnnotationManager annotationManager;

	@PostMapping(value = SUBMIT_ANNOTATION, consumes = "application/ld+json", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public TicketResponse.Ticket submitAnnotation(HttpServletRequest request) throws IOException {
		Object jsonObject = JsonUtils.fromInputStream(request.getInputStream());
//		return annotationManager.process(jsonObject);
		return null;
	}

}
