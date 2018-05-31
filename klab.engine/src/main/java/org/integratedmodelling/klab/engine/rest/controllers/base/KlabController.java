package org.integratedmodelling.klab.engine.rest.controllers.base;

import java.security.Principal;

import org.integratedmodelling.klab.Auth;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.Roles;
import org.integratedmodelling.klab.engine.Engine;
import org.integratedmodelling.klab.rest.Capabilities;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(origins = "http://localhost:8080")
@Secured(Roles.PUBLIC)
public class KlabController {

    
    @RequestMapping(value = API.CAPABILITIES, method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Capabilities capabilities(Principal user) {
        return Klab.INSTANCE.getCapabilities(Auth.INSTANCE.getIdentity(user));
    }

    @RequestMapping(value = API.SCHEMA, method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String resourceSchemata() {
        return Klab.INSTANCE.getResourceSchema();
    }
    
    @RequestMapping(value = API.SCHEMA, params = "resource", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String resourceSchema(@RequestParam("resource") String what) {
        return Klab.INSTANCE.getResourceSchema(what);
    }
    
    @RequestMapping(value = API.SCHEMA, params = "list", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String resourceSchema() {
        return Klab.INSTANCE.getResourceSchema("all");
    }

    @RequestMapping(
            value = API.PING,
            method = { RequestMethod.GET, RequestMethod.HEAD })
    @ResponseBody
    public String ping() {
        Engine engine = Klab.INSTANCE.getRootMonitor().getIdentity().getParentIdentity(Engine.class);
        if (engine == null) {
            return "0";
        }
        return "" + (System.currentTimeMillis() - engine.getBootTime().getTime());
    }

}
