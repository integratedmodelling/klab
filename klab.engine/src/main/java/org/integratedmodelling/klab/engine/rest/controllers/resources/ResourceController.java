package org.integratedmodelling.klab.engine.rest.controllers.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.Roles;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension.Type;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IResourceAdapter;
import org.integratedmodelling.klab.api.observations.IObservationGroup;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.common.Urns;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.data.resources.Codelist;
import org.integratedmodelling.klab.engine.resources.Worldview;
import org.integratedmodelling.klab.engine.rest.controllers.engine.EngineSessionController;
import org.integratedmodelling.klab.engine.runtime.ObserveContextTask;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.exceptions.KlabIllegalArgumentException;
import org.integratedmodelling.klab.exceptions.KlabIllegalStateException;
import org.integratedmodelling.klab.model.Acknowledgement;
import org.integratedmodelling.klab.rest.CodelistReference;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.JsonUtils;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Parameters;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

/**
 * The controller implementing the
 * {@link org.integratedmodelling.klab.api.API.NODE.RESOURCE resource API}.
 * 
 * @author ferdinando.villa
 * @author enrico.girotto
 *
 */
@RestController
public class ResourceController {

	@CrossOrigin(origins = "*")
	// @Secured(Roles.SESSION)
	@RequestMapping(value = API.NODE.RESOURCE.UPLOAD_URN, method = RequestMethod.POST)
	public ResponseEntity<HttpStatus> uploadResource(Principal principal, @RequestParam(required = false) String refId,
			@RequestParam("files[]") MultipartFile[] files) throws Exception {

		if (files == null || files.length == 0) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "No files");
		}

		ISession session = EngineSessionController.getSession(principal);

		/*
		 * try importing as a resource.
		 */
		File primary = null;
		String filename = null;
		for (MultipartFile f : files) {

			File destination = new File(
					Configuration.INSTANCE.getDataPath("uploads") + File.separator + f.getOriginalFilename());
			f.transferTo(destination);
			destination.deleteOnExit();

			if (primary == null) {
				List<IResourceAdapter> adapters = Resources.INSTANCE.getResourceAdapter(destination,
						new Parameters<String>());
				if (adapters.size() > 0) {
					primary = destination;
					filename = f.getOriginalFilename();
				}
			}

		}
		if (primary == null) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"No adapter found to handle uploaded file(s)");
		}
		IResource resource = Resources.INSTANCE.importResource(primary.toURI().toURL(),
				Resources.INSTANCE.getServiceWorkspace().getServiceProject(session.getMonitor()));

		if (resource == null) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Uploaded file " + filename + " could not be imported as a resource");
		}

		if (resource.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Uploaded file " + filename + " was imported with errors");
		}

		/*
		 * decide what to do. If we have a non-scalar irregular space and there is no
		 * refId, we can create a context.
		 */
		if (refId == null && resource.getGeometry().getDimension(Type.SPACE) != null) {

			Pair<IArtifact, IArtifact> data = Resources.INSTANCE.resolveResourceToArtifact(resource.getUrn(),
					session.getMonitor(), true, Worldview.getGeoregionConcept(), Worldview.getGeoregionConcept());

			ISubject ret = data.getSecond() instanceof IObservationGroup
					&& ((IObservationGroup) data.getSecond()).groupSize() > 0
							? (ISubject) ((IObservationGroup) data.getSecond()).iterator().next()
							: (ISubject) data.getFirst();

			IScale scale = Scale.create(session.getState().getGeometry());
			Acknowledgement observer = Observations.INSTANCE.makeROIObserver((Shape) ret.getScale().getSpace().getShape(),
					scale.getTime(), null, session.getState().getRegionOfInterestName(), session.getMonitor());
			try {
				new ObserveContextTask((Session) session, observer, new ArrayList<>()).get();
			} catch (InterruptedException | ExecutionException e) {
				session.getMonitor().error(e);
			}
		}

		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}

	@RequestMapping(value = API.ENGINE.RESOURCE.UPDATE_CODELIST, method = RequestMethod.POST)
	public void updateCodelist(Principal principal, @PathVariable String urn,
			@RequestParam("file") MultipartFile[] files) throws Exception {

		if (!Urns.INSTANCE.isLocal(urn)) {
			throw new KlabIllegalStateException("Updating of codelists is only allowed on local engines and resources");
		}
		
		IResource resource = Resources.INSTANCE.resolveResource(urn);
		if (resource == null) {
			throw new KlabIllegalArgumentException("Update codelist: resource '" + urn + "' not found in catalog");
		}
		
		for (MultipartFile f : files) {
			File destination = new File(
					Configuration.INSTANCE.getDataPath("uploads") + File.separator + f.getOriginalFilename());
			f.transferTo(destination);
			destination.deleteOnExit();
			CodelistReference codelist = JsonUtils.load(destination, CodelistReference.class);
			Resources.INSTANCE.updateCodelist(resource, codelist, Klab.INSTANCE.getRootMonitor());
		}
		
	}
	
	@RequestMapping(value = API.ENGINE.RESOURCE.GET_CODELIST, method = RequestMethod.GET)
	public CodelistReference getCodelist(Principal principal, @PathVariable String urn, @PathVariable String codelist) throws Exception {

		IResource resource = Resources.INSTANCE.resolveResource(urn);
		if (resource == null) {
			throw new KlabIllegalArgumentException("Update codelist: resource '" + urn + "' not found in catalog");
		}
		
		return ((Codelist)Resources.INSTANCE.getCodelist(resource, codelist, Klab.INSTANCE.getRootMonitor())).getReference();
	}
	
	@RequestMapping(value = API.ENGINE.RESOURCE.CREATE_CODELIST, method = RequestMethod.GET)
	public CodelistReference createCodelist(Principal principal, @RequestParam String urn, @PathVariable String codelist) throws Exception {

		IResource resource = Resources.INSTANCE.resolveResource(urn);
		if (resource == null) {
			throw new KlabIllegalArgumentException("Update codelist: resource '" + urn + "' not found in catalog");
		}
		
		return ((Codelist)Resources.INSTANCE.createCodelist(resource, codelist, Klab.INSTANCE.getRootMonitor())).getReference();
	}

	@RequestMapping(value = API.NODE.RESOURCE.EXPORT_URN, method = RequestMethod.GET)
	public void getObservationData(Principal principal, @PathVariable String urn, @RequestParam String outputFormat,
			HttpServletResponse response) throws Exception {

		File output = File.createTempFile("klab", ".resource");
		IResource resource = Resources.INSTANCE.resolveResource(urn);

		if (resource == null) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Resource " + urn + " does not exist");
		}

		if (resource.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Resource " + urn + " has errors and cannot be exported");
		}

		IResourceAdapter adapter = Resources.INSTANCE.getResourceAdapter(resource.getAdapterType());

		if (adapter == null) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Resource " + urn + " cannot be exported: adapter type not found");
		}

		/*
		 * go for it
		 */
		if (adapter.getImporter().exportResource(output, resource, outputFormat)) {

			response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
			try (InputStream in = new FileInputStream(output)) {
				IOUtils.copy(in, response.getOutputStream());
			}
		} else {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Export of resource " + urn + " failed.");
		}

	}

}
