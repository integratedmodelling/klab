package org.integratedmodelling.klab.node.controllers;

import java.io.File;
import java.security.Principal;

import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.runtime.ITicket;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.common.monitoring.TicketManager;
import org.integratedmodelling.klab.data.encoding.Encoding.KlabData;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;
import org.integratedmodelling.klab.node.auth.EngineAuthorization;
import org.integratedmodelling.klab.node.auth.Role;
import org.integratedmodelling.klab.node.resources.FileStorageService;
import org.integratedmodelling.klab.node.resources.ResourceManager;
import org.integratedmodelling.klab.rest.ResourceDataRequest;
import org.integratedmodelling.klab.rest.ResourceReference;
import org.integratedmodelling.klab.rest.TicketResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Secured(Role.ENGINE)
public class ResourceController {

	@Autowired
	ResourceManager resourceManager;

	@Autowired
	FileStorageService fileStorageService;

	/*
	 * TODO this is probably the perfect place for a reactive controller, using a
	 * Mono<KlabData> instead of KlabData.
	 */
	@PostMapping(value = API.NODE.RESOURCE.CONTEXTUALIZE, /* produces = "application/x-protobuf", */ consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public KlabData getUrnData(@RequestBody ResourceDataRequest request, Principal principal) {
		IGeometry geometry = Geometry.create(request.getGeometry());
		return resourceManager.getResourceData(request.getUrn(), geometry,
				((EngineAuthorization) principal).getGroups());
	}

	@PostMapping(value = API.NODE.RESOURCE.RESOLVE_URN, produces = "application/json")
	@ResponseBody
	public ResourceReference resolveUrn(@PathVariable String urn, Principal principal) {

		IResource resource = resourceManager.getResource(urn, ((EngineAuthorization) principal).getGroups());
		// TODO check groups and send unauthorized if not authorized
		// (AccessDeniedException)
		if (resource == null) {
			throw new KlabResourceNotFoundException("resource " + urn + " not found on this node");
		}
		return ((Resource) resource).getReference();
	}

	/**
	 * Take charge of a resource submission consisting of a zip archive uploaded
	 * with all the contents (file name = temporary ID) and return the resulting
	 * ticket, from which the client can follow progress.
	 * 
	 * @param file
	 * @param principal
	 * @return
	 */
	@PutMapping(API.NODE.RESOURCE.SUBMIT_FILES)
	@ResponseBody
	public TicketResponse.Ticket submitResource(@RequestParam("file") MultipartFile file, Principal principal) {

		String fileName = fileStorageService.storeFile(file);
		ITicket ticket = resourceManager.publishResource(null, new File(fileName), (EngineAuthorization) principal,
				Klab.INSTANCE.getRootMonitor());

		return TicketManager.encode(ticket);
	}

	/**
	 * Take charge of a resource submission consisting only of a resource.json
	 * contents and a temporary ID, and return the resulting ticket, from which the
	 * client can follow progress.
	 * 
	 * @param resource
	 * @param principal
	 * @return
	 */
	@PostMapping(value = API.NODE.RESOURCE.SUBMIT_DESCRIPTOR, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public TicketResponse.Ticket submitResource(@RequestBody ResourceReference resource, Principal principal) {

		ITicket ticket = resourceManager.publishResource(resource, null, (EngineAuthorization) principal,
				Klab.INSTANCE.getRootMonitor());

		return TicketManager.encode(ticket);
	}

}
