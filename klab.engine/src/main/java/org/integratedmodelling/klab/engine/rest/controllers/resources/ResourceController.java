package org.integratedmodelling.klab.engine.rest.controllers.resources;

import java.security.Principal;

import javax.servlet.http.HttpServletResponse;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.Roles;
import org.integratedmodelling.klab.api.documentation.IReport.Encoding;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.engine.rest.controllers.engine.EngineSessionController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * The controller implementing the {@link org.integratedmodelling.klab.api.API.NODE.RESOURCE resource API}.
 * 
 * @author ferdinando.villa
 * @author enrico.girotto
 *
 */
@RestController
public class ResourceController {

	@CrossOrigin(origins = "*")
	@Secured(Roles.SESSION)
	@RequestMapping(value = API.NODE.RESOURCE.UPLOAD_URN, method = RequestMethod.POST)
	public ResponseEntity<HttpStatus> uploadResource(
			Principal principal,
			@RequestParam String refId,
			@RequestParam MultipartFile file)
			throws Exception {
		if (file == null) {
			return new ResponseEntity<HttpStatus>(HttpStatus.UNPROCESSABLE_ENTITY); // 422
		}
		System.out.println(String.format("RefId: %s", refId != null ? refId : "Not send"));
		System.out.println(String.format("File name %s", file.getName()));
	    System.out.println(String.format("File original name %s", file.getOriginalFilename()));
	    System.out.println(String.format("File size %s", file.getSize()));

	    // TODO: file.getInputStream();
		// TODO: check size, type, etc. is needed? k.EXPLORER take care to checking it
		// TODO: do something with file based on type and referenced id 
		
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
}
