package org.integratedmodelling.klab.node.controllers;

import java.security.Principal;

import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.runtime.ITicket;
import org.integratedmodelling.klab.data.encoding.Encoding.KlabData;
import org.integratedmodelling.klab.data.encoding.Encoding.KlabData.Builder;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;
import org.integratedmodelling.klab.node.auth.EngineAuthorization;
import org.integratedmodelling.klab.node.auth.Role;
import org.integratedmodelling.klab.node.resources.FileStorageService;
import org.integratedmodelling.klab.node.resources.ResourceManager;
import org.integratedmodelling.klab.rest.ResourceReference;
import org.integratedmodelling.klab.rest.ResourceSubmissionResponse;
import org.integratedmodelling.klab.rest.ResourceSubmissionResponse.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
	@GetMapping(value = API.NODE.RESOURCE.GET_URN, produces = "application/json")
	@ResponseBody
	public KlabData getUrnData(@PathVariable String urn, Principal principal) {

		IResource resource = resourceManager.getResource(urn, ((EngineAuthorization) principal).getGroups());
		// TODO check groups and send unauthorized if not authorized
		// (AccessDeniedException)
		if (resource == null) {
			throw new KlabResourceNotFoundException("resource " + urn + " not found on this node");
		}

		Builder builder = KlabData.newBuilder();

		// TODO!

		return builder.build();
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
	 * with all the contents (file name = temporary ID) and return whether it's
	 * accepted. After true is returned, the other endpoints can be used to check on
	 * the resource status.
	 * 
	 * @param file
	 * @param principal
	 * @return
	 */
	@PutMapping(API.NODE.RESOURCE.SUBMIT_FILES)
	@ResponseBody
	public ResourceSubmissionResponse submitResource(@RequestParam("file") MultipartFile file, Principal principal) {

		ResourceSubmissionResponse ret = new ResourceSubmissionResponse();
		
		String publishId = file.getName();
		String fileName = fileStorageService.storeFile(file);
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
				.path(fileName).toUriString();

		/*
		 * ensure we can handle this
		 */

		/*
		 * submit and create ticket
		 */
		
		
		// thread should unzip resource, load resource.json, establish adapter, call the
		// validator, build resource and import it
		// in public catalog
		
		ret.setTicket(publishId);
		ret.setStatus(Status.ACCEPTED);
		
		return ret;
	}

	/**
	 * Take charge of a resource submission consisting only of a resource.json
	 * contents and a temporary ID, and return whether it's accepted. After true is
	 * returned, the other endpoints can be used to check on the resource status.
	 * 
	 * @param resource
	 * @param principal
	 * @return
	 */
	@PostMapping(value = API.NODE.RESOURCE.SUBMIT_DESCRIPTOR, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResourceSubmissionResponse submitResource(@RequestBody ResourceReference resource, Principal principal) {

		ResourceSubmissionResponse ret = new ResourceSubmissionResponse();
		
		System.out.println("ZIO PAPA RESOURCE " + resource.getUrn() + " SUBMITTED FOR PUBLICATION");

		/*
		 * create ticket
		 */
//		
		ITicket ticket = resourceManager.publishResource(resource, null, (EngineAuthorization) principal,
				Klab.INSTANCE.getRootMonitor());

		ret.setTicket(ticket.getId());
		ret.setStatus(Status.ACCEPTED);
		
		return ret;
	}

}
