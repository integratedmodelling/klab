package org.integratedmodelling.klab.node.controllers;

import java.io.File;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.KlabPermissions;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.runtime.ITicket;
import org.integratedmodelling.klab.api.services.IIndexingService.Match;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.common.monitoring.TicketManager;
import org.integratedmodelling.klab.data.encoding.Encoding.KlabData;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;
import org.integratedmodelling.klab.node.auth.EngineAuthorization;
import org.integratedmodelling.klab.node.auth.Role;
import org.integratedmodelling.klab.node.resources.FileStorageService;
import org.integratedmodelling.klab.node.resources.ResourceManager;
import org.integratedmodelling.klab.rest.Group;
import org.integratedmodelling.klab.rest.ResourceDataRequest;
import org.integratedmodelling.klab.rest.ResourceReference;
import org.integratedmodelling.klab.rest.TicketResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

	/**
	 * Controller for the main operation of retrieving resource data. Unique in
	 * k.LAB for returning a Protobuf object instead of JSON, although so far only
	 * the JSON encoding for protobuf has worked.
	 * <p>
	 * TODO: another similar one should be provided that accepts a KlabData object,
	 * encoding everything but with the option of providing data for the
	 * contextualization scope. This is needed to allow computations that take
	 * inputs or parameters. The KlabData should have an option to transfer only
	 * diffs vs. current situation to avoid unneeded transfer.
	 * <p>
	 * TODO: As the volume of data can be large, this is probably the perfect place
	 * for a reactive controller, using a Mono<KlabData> instead of KlabData.
	 */
	@PostMapping(value = API.NODE.RESOURCE.CONTEXTUALIZE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public KlabData getUrnData(@RequestBody ResourceDataRequest request, Principal principal) {
		IGeometry geometry = Geometry.create(request.getGeometry());
		if (!resourceManager.canAccess(request.getUrn(), (EngineAuthorization) principal)) {
			throw new SecurityException(request.getUrn());
		}
		// TODO also check that the principal can access the adapter

		// TODO log and record
		Logging.INSTANCE.info("authorized access to " + request.getUrn() + " by "
				+ ((EngineAuthorization) principal).getFullyQualifiedUsername());

		return resourceManager.getResourceData(request.getUrn(), geometry);
	}

	/**
	 * Send back the descriptor of the resource identified by the URN in the path.
	 * 
	 * @param urn
	 * @param principal
	 * @return
	 */
	@GetMapping(value = API.NODE.RESOURCE.RESOLVE_URN, produces = "application/json")
	@ResponseBody
	public ResourceReference resolveUrn(@PathVariable String urn, Principal principal) {

		IResource resource = resourceManager.getResource(urn, ((EngineAuthorization) principal).getGroups());
		if (!resourceManager.canAccess(urn, (EngineAuthorization) principal)) {
			throw new SecurityException(urn);
		}
		if (resource == null) {
			throw new KlabResourceNotFoundException("resource " + urn + " not found on this node");
		}
		return ((Resource) resource).getReference();
	}

	/**
	 * List all the resources accessible to the principal.
	 * 
	 * @param principal
	 * @return
	 */
	@GetMapping(value = API.NODE.RESOURCE.LIST, produces = "application/json")
	@ResponseBody
	public List<ResourceReference> listResources(Principal principal, @RequestParam(required = false) String query) {
		List<ResourceReference> ret = new ArrayList<>();
		if (query != null) {
			for (Match match : resourceManager.queryResources(query)) {
				if (resourceManager.canAccess(match.getId(), (EngineAuthorization) principal)) {
					IResource resource = resourceManager.getResource(match.getId(),
							((EngineAuthorization) principal).getGroups());
					if (resource != null) {
						ResourceReference ref = ((Resource) resource).getReference();
						ref.getMetadata().put(IMetadata.IM_SEARCH_SCORE, match.getScore()+"");
						ret.add(ref);
					}
				}
			}
		} else {
			for (String urn : resourceManager.getOnlineResources()) {
				if (resourceManager.canAccess(urn, (EngineAuthorization) principal)) {
					IResource resource = resourceManager.getResource(urn,
							((EngineAuthorization) principal).getGroups());
					if (resource != null) {
						ret.add(((Resource) resource).getReference());
					}
				}
			}
		}
		return ret;
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
	@PostMapping(API.NODE.RESOURCE.SUBMIT_FILES)
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
	
	
	public static boolean approveSubmission(EngineAuthorization user, long fileSize) {
		String submitting = Configuration.INSTANCE.getProperty("klab.node.submitting", "NONE");
		if ("*".equals(submitting)) {
			return checkUploadLimit(user, fileSize);
		} else if ("NONE".equals(submitting)) {
			return false;
		} else {
			KlabPermissions perms = KlabPermissions.create(submitting);
			Collection<String> groups = new ArrayList<String>();
			user.getGroups().forEach(g -> groups.add(g.getId()));
			if(perms.isAuthorized(user.getUsername(), groups)) {
				return checkUploadLimit(user, fileSize);
			} else {
				return false;
			}
		}
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
	@DeleteMapping(value = API.NODE.RESOURCE.DELETE_URN)
	public boolean deleteResource(@PathVariable String urn, Principal principal) {
		return resourceManager.deleteResource(urn, (EngineAuthorization) principal, Klab.INSTANCE.getRootMonitor());
	}

	/**
	 * Update a resource based on the content of the passed object. This will
	 * replace the JSON descriptor with the passed one, increment the major version
	 * number for the resource, and leave the previous version in the history of the
	 * new resource.
	 * 
	 * @param urn       the CURRENT URN of the resource (even if the modification
	 *                  changes it)
	 * @param resource  the new body of the resource descriptor. No changes are
	 *                  allowed besides modifying the resource descriptor; if new
	 *                  content is needed, delete the resource and reimport.
	 * @param principal the user
	 * @return a ticket for
	 */
	@PutMapping(value = API.NODE.RESOURCE.UPDATE_URN, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateResource(@PathVariable String urn, @RequestBody ResourceReference resource, Principal principal) {
		resourceManager.updateResource(urn, resource, (EngineAuthorization) principal, Klab.INSTANCE.getRootMonitor());
	}

}
