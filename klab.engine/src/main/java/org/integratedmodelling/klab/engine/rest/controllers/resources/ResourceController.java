package org.integratedmodelling.klab.engine.rest.controllers.resources;

import java.io.File;
import java.security.Principal;

import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.Roles;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension.Type;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.components.runtime.observations.ObservationGroup;
import org.integratedmodelling.klab.engine.rest.controllers.engine.EngineSessionController;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.monitoring.Message;
import org.integratedmodelling.klab.utils.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
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
    public ResponseEntity<HttpStatus> uploadResource(Principal principal, @RequestParam String refId, @RequestParam MultipartFile file)
            throws Exception {
        
        if (file == null) {
            return new ResponseEntity<HttpStatus>(HttpStatus.UNPROCESSABLE_ENTITY); // 422
        }

        /*
         * try importing as a resource.
         */
        ISession session = EngineSessionController.getSession(principal);
        File destination = new File(Configuration.INSTANCE.getDataPath("uploads") + File.separator
                + file.getOriginalFilename());
        file.transferTo(destination);
        destination.deleteOnExit();
        IResource resource = Resources.INSTANCE.importResource(destination.toURI().toURL(), Resources.INSTANCE
                .getServiceWorkspace().getServiceProject(session.getMonitor()));

        if (resource == null) {
            throw new IllegalStateException("uploaded file " + file.getOriginalFilename()
                    + " could not be imported as a resource");
        }

        if (resource.hasErrors()) {
            throw new IllegalStateException("uploaded file " + file.getOriginalFilename()
                    + " was imported with errors");
        }

        /*
         * decide what to do. If we have a non-scalar irregular space and there is no refId, we can create a context.
         */
        if (refId == null && resource.getGeometry().getDimension(Type.SPACE) != null) {

            // ISubject context = null;

            Pair<IArtifact, IArtifact> data = Resources.INSTANCE
                    .resolveResourceToArtifact(resource.getUrn(), session.getMonitor());

            ISubject ret = data.getSecond() instanceof ObservationGroup
                    && ((ObservationGroup) data.getSecond()).groupSize() > 0
                            ? (ISubject) ((ObservationGroup) data.getSecond()).iterator().next()
                            : (ISubject) data.getFirst();

            /*
            * notify context
            */
            session.getMonitor()
                    .send(Message.create(session
                            .getId(), IMessage.MessageClass.ObservationLifecycle, IMessage.Type.NewObservation, Observations.INSTANCE
                                    .createArtifactDescriptor(ret, null, ITime.INITIALIZATION, -1, false, true)));

            // TODO must finish this task and start another, otherwise no context gets registered.

            // /*
            // * notify result
            // */
            // IObservation notifiable = (IObservation) (data.getSecond() instanceof ObservationGroup
            // && data.getSecond().groupSize() > 0 ? data.getSecond().iterator().next()
            // : data.getSecond());
            //
            // session.getMonitor().send(Message.create(session
            // .getId(), IMessage.MessageClass.ObservationLifecycle, IMessage.Type.NewObservation,
            // Observations.INSTANCE
            // .createArtifactDescriptor(notifiable, context, ITime.INITIALIZATION, -1, false, true)
            // .withTaskId(token)));

            /*
             * Register the observation context with the session. It will be disposed of
             * and/or persisted by the session itself.
             */
            ((Session) session).registerObservationContext(((Observation) ret).getRuntimeContext());

        }

        /**
         * TODO should send back notification to add resource to local tree. Could be a separate tree...
         */

        System.out.println(String.format("RefId: %s", refId != null ? refId : "Not sent"));
        System.out.println(String.format("File name %s", file.getName()));
        System.out.println(String.format("File original name %s", file.getOriginalFilename()));
        System.out.println(String.format("File size %s", file.getSize()));

        // TODO: file.getInputStream();
        // TODO: check size, type, etc. is needed? k.EXPLORER take care to checking it
        // TODO: do something with file based on type and referenced id

        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }

}
