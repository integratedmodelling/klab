package org.integratedmodelling.klab.engine.rest.controllers.engine;

import org.integratedmodelling.klab.api.auth.Roles;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RestController;

/**
 * The controller implementing the
 * {@link org.integratedmodelling.klab.API.ENGINE engine API}. These use the
 * engine to create sessions and are available to all users of the engine.
 * 
 * @author ferdinando.villa
 *
 */
@RestController
@Secured(Roles.ENGINE_USER)
public class EngineController {

}
