package org.integratedmodelling.klab.engine.rest.controllers.base;

import org.integratedmodelling.klab.API;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

/**
 * The controller implementing the {@link API.ENGINE.ADMIN engine admin API}. All endpoints are
 * preauthorized when accessed from the local IP.
 * 
 * @author ferdinando.villa
 *
 */
@RestController
@PreAuthorize("hasIpAddress('127.0.0.1')")
public class EngineAdminController {

}
