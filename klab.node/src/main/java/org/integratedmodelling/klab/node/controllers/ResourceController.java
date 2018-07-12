package org.integratedmodelling.klab.node.controllers;

import java.security.Principal;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;
import org.integratedmodelling.klab.node.auth.Role;
import org.integratedmodelling.klab.node.resources.FileStorageService;
import org.integratedmodelling.klab.node.resources.ResourceManager;
import org.integratedmodelling.klab.rest.ResourceReference;
import org.integratedmodelling.klab.rest.UploadFileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	
	@RequestMapping(value = API.NODE.RESOURCE.RESOLVE_URN, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResourceReference resolveUrn(@PathVariable String urn, Principal principal) {
		IResource resource = resourceManager.getCatalog().get(urn);
		// TODO check groups and send unauthorized if not authorized (AccessDeniedException)
		if (resource == null) {
			throw new KlabResourceNotFoundException("resource " + urn + " not found on this node");
		}
		return ((Resource)resource).getReference();
	}
	
    @PutMapping(API.NODE.RESOURCE.SUBMIT)
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file, Principal principal) {
    	
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }
	
}
