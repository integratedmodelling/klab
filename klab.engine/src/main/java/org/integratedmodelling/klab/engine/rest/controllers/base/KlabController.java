package org.integratedmodelling.klab.engine.rest.controllers.base;

import org.integratedmodelling.klab.API;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.data.rest.resources.responses.Capabilities;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * The root controller for the {@link API base API}.
 * 
 * @author ferdinando.villa
 *
 */
@RestController
@PreAuthorize("hasRole('ROLE_ANONYMOUS') OR hasRole('ROLE_USER')")
public class KlabController {

  @RequestMapping(value = API.CAPABILITIES, method = RequestMethod.GET, produces = "application/json")
  @ResponseBody public Capabilities capabilities() {
    return Klab.INSTANCE.getCapabilities();
  }

}
