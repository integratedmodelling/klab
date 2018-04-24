package org.integratedmodelling.klab.engine.rest.controllers.engine;

import org.integratedmodelling.klab.api.auth.Roles;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RestController;

/**
 * The controller implementing the {@link org.integratedmodelling.klab.api.API.ENGINE.SESSION session API}.
 * 
 * @author ferdinando.villa
 *
 */
@RestController
@Secured(Roles.SESSION)
public class EngineSessionController {

}
