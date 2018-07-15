package org.integratedmodelling.klab.node.controllers;

import java.security.Principal;

import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.data.encoding.Encoding.KlabData;
import org.integratedmodelling.klab.data.encoding.Encoding.KlabData.Builder;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;
import org.integratedmodelling.klab.node.auth.EngineAuthorization;
import org.integratedmodelling.klab.node.auth.Role;
import org.integratedmodelling.klab.node.resources.FileStorageService;
import org.integratedmodelling.klab.node.resources.ResourceManager;
import org.integratedmodelling.klab.rest.PublishResourceResponse;
import org.integratedmodelling.klab.rest.ResourceReference;
import org.integratedmodelling.klab.rest.UploadFileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
     * TODO this is probably the perfect place for a reactive controller, using a Mono<KlabData> instead of
     * KlabData.
     */
    @GetMapping(value = API.NODE.RESOURCE.GET_URN, produces = "application/json")
    @ResponseBody
    public KlabData getUrnData(@PathVariable String urn, Principal principal) {

        IResource resource = resourceManager.getResource(urn, ((EngineAuthorization) principal).getGroups());
        // TODO check groups and send unauthorized if not authorized (AccessDeniedException)
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
        // TODO check groups and send unauthorized if not authorized (AccessDeniedException)
        if (resource == null) {
            throw new KlabResourceNotFoundException("resource " + urn + " not found on this node");
        }
        return ((Resource) resource).getReference();
    }

    @PutMapping(API.NODE.RESOURCE.SUBMIT)
    public PublishResourceResponse uploadFile(@RequestParam("file") MultipartFile file, Principal principal) {

        String fileName = fileStorageService.storeFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
                .path(fileName).toUriString();

        // TODO spawn publish thread, return 201 with response
        PublishResourceResponse ret = new PublishResourceResponse();
        
        // thread should unzip resource, load resource.json, establish adapter, call the validator, build resource and import it
        // in public catalog
        
        
//        return new UploadFileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
        
        return ret;
    }

    @PostMapping(API.NODE.RESOURCE.SUBMIT)
    @ResponseBody
    public PublishResourceResponse uploadFile(ResourceReference resource, Principal principal) {
        
        PublishResourceResponse ret = new PublishResourceResponse();

        IResource res = resourceManager.publishResource(resource, null, (EngineAuthorization) principal, Klab.INSTANCE.getRootMonitor());
        ret.setUrn(res.getUrn());
        
        return ret;
    }

}
