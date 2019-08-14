package org.integratedmodelling.klab.engine.rest.controllers.base;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.Principal;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.Roles;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.components.geospace.visualization.Renderer;
import org.integratedmodelling.klab.components.time.extents.Time;
import org.integratedmodelling.klab.engine.Engine;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.rest.Capabilities;
import org.integratedmodelling.klab.rest.EngineStatus;
import org.integratedmodelling.klab.rest.KimCapabilities;
import org.integratedmodelling.klab.rest.ObservationReference;
import org.integratedmodelling.klab.rest.ObservationReference.GeometryType;
import org.integratedmodelling.klab.rest.PingResponse;
import org.integratedmodelling.klab.utils.IPUtils;
import org.integratedmodelling.klab.utils.NumberUtils;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * The root controller for the {@link API base API}. All endpoints are public
 * but the response depends on the privileges of the logged in user, remapped to
 * anonymous (or local anonymous) if accessed without authentication.
 * 
 * @author ferdinando.villa
 *
 */
@RestController
@CrossOrigin(origins = "*")
@Secured(Roles.PUBLIC)
public class KlabController {

    @RequestMapping(value = API.CAPABILITIES, method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Capabilities capabilities(Principal user, HttpServletRequest request) {
        Capabilities ret = Klab.INSTANCE.getCapabilities(Authentication.INSTANCE.getIdentity(user));
        if (IPUtils.isLocal(request.getRemoteAddr())) {
            for (IProject project : Resources.INSTANCE.getLocalWorkspace().getProjects()) {
                if (project.getRoot() != null) {
                    ret.getLocalWorkspaceProjects().add(Resources.INSTANCE.createProjectDescriptor(project));
                }
            }
        }
        return ret;
    }
    
    @RequestMapping(value = API.KIM, method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public KimCapabilities kimCapabilities(Principal user, HttpServletRequest request) {
        KimCapabilities ret = new KimCapabilities();
        // TODO this will have its own independent version
        ret.setBuild(Version.VERSION_BUILD);
        // TODO this will have its own independent version
        ret.setVersion(Version.CURRENT);
        ret.getKeywords().addAll(Kim.INSTANCE.getKimKeywords());
        return ret;
    }
    
	@RequestMapping(value = API.ENGINE.STATUS, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public EngineStatus engineStatus(Principal user, HttpServletRequest request) {
		boolean isAdmin = false; // TODO implement
		EngineStatus ret = new EngineStatus();
        Engine engine = Authentication.INSTANCE.getAuthenticatedIdentity(Engine.class);
        Runtime runtime = Runtime.getRuntime();
        if (engine != null) {
            ret.setTotalMemory(runtime.totalMemory() / 1048576);
            ret.setFreeMemory(runtime.freeMemory() / 1048576);
            ret.setBootTime(engine.getBootTime().getTime());
        }

        if (IPUtils.isLocal(request.getRemoteAddr()) || isAdmin) {
        	for (ISession session : Authentication.INSTANCE.getSessions()) {
        		// TODO filter the nonlocal ones if not admin
        		ret.getSessions().add(((Session)session).getSessionReference());
        	}
        }
		return ret;
	}

    @RequestMapping(value = API.SCHEMA, method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String resourceSchemata() {
        return Klab.INSTANCE.getResourceSchema();
    }

    @RequestMapping(
            value = API.SCHEMA,
            params = "resource",
            method = RequestMethod.GET,
            produces = "application/json")
    @ResponseBody
    public String resourceSchema(@RequestParam("resource") String what) {
        return Klab.INSTANCE.getResourceSchema(what);
    }

    @RequestMapping(
            value = API.SCHEMA,
            params = "list",
            method = RequestMethod.GET,
            produces = "application/json")
    @ResponseBody
    public String resourceSchema() {
        return Klab.INSTANCE.getResourceSchema("all");
    }

    @RequestMapping(
            value = API.PING,
            method = { RequestMethod.GET, RequestMethod.HEAD },
            produces = "application/json")
    @ResponseBody
    public PingResponse ping(Principal user, HttpServletRequest request) {

        PingResponse ret = new PingResponse();
        Engine engine = Authentication.INSTANCE.getAuthenticatedIdentity(Engine.class);
        Runtime runtime = Runtime.getRuntime();
        ret.setVersion(Version.CURRENT);
        ret.setOnline(engine != null);
        if (engine != null) {
            ret.setTotalMemory(runtime.totalMemory() / 1048576);
            ret.setFreeMemory(runtime.freeMemory() / 1048576);
            ret.setProcessorCount(runtime.availableProcessors());
            ret.setBootTime(engine.getBootTime().getTime());
            ret.setRequestTime(System.currentTimeMillis());
            ret.setUptime(System.currentTimeMillis() - engine.getBootTime().getTime());
            ret.setEngineId(engine.getId());
            if (IPUtils.isLocal(request.getRemoteAddr())) {
                ISession session = Authentication.INSTANCE.getDefaultSession();
                if (session != null) {
                    ret.setLocalSessionId(session.getId());
                }
            }
        }

        return ret;
    }

    /**
     * TEMPORARY! Delete when all demos are no longer demos. This is simply a duplicate of 
     * {@ink EngineViewController#getObservationData(Principal, String, String, String, GeometryType, HttpServletResponse)} 
     * not requiring authorization as it's complex to do within a report right now.
     * 
     * @param principal
     * @param observation
     * @param viewport
     * @param locator
     * @param format
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/engine/session/view/displaydata/{sessionId}/{observation}", method = RequestMethod.GET)
    public void getObservationData(@PathVariable String sessionId, @PathVariable String observation, @RequestParam(
            required = false) String viewport, @RequestParam(
                    required = false) String locator, @RequestParam ObservationReference.GeometryType format, HttpServletResponse response)
            throws Exception {

        ISession session = Authentication.INSTANCE.getIdentity(sessionId, ISession.class);
        IObservation obs = session.getObservation(observation);

        // TODO link to locator parameter
        ILocator timeLocator = Time.INITIALIZATION;

        if (obs instanceof IState) {

            if (format == GeometryType.RASTER) {
                BufferedImage image = Renderer.INSTANCE.render((IState) obs, timeLocator, NumberUtils
                        .intArrayFromString(viewport == null ? "800,800" : viewport));
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                ImageIO.write(image, "png", os);
                InputStream in = new ByteArrayInputStream(os.toByteArray());
                response.setContentType(MediaType.IMAGE_PNG_VALUE);
                IOUtils.copy(in, response.getOutputStream());
            } else if (format == GeometryType.COLORMAP) {
                // TODO get (and cache, or use cached) colormap for state values and produce an image in the
                // requested size
            } else if (format == GeometryType.SCALAR) {
                // TODO if distributed, use locator to get single value; otherwise ensure it's
                // actually scalar
            }
        }

    }

}
