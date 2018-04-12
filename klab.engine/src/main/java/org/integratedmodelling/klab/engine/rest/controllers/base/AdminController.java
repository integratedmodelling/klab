package org.integratedmodelling.klab.engine.rest.controllers.base;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

/**
 * The controller implementing the {@link org.integratedmodelling.klab.API.ADMIN administration API}. Secured to the ADMIN role
 * but all endpoints are preauthorized when accessed from the local IP.
 * 
 * @author ferdinando.villa
 *
 */
@RestController
// TODO secure
@PreAuthorize("hasIpAddress('127.0.0.1')")
public class AdminController {

}
