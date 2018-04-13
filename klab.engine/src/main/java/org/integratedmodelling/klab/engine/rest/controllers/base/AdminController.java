package org.integratedmodelling.klab.engine.rest.controllers.base;

import org.integratedmodelling.klab.API;
import org.integratedmodelling.klab.Klab;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * The controller implementing the {@link org.integratedmodelling.klab.API.ADMIN administration
 * API}. Secured to the ADMIN role but all endpoints are preauthorized when accessed from the local
 * IP.
 * 
 * @author ferdinando.villa
 *
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
// NOPE - the one below won't work (only for WEB expressions not for method expressions)
//@PreAuthorize("hasIpAddress('127.0.0.1') OR hasRole('ADMIN')")
public class AdminController {

  @RequestMapping(value = API.ADMIN.SHUTDOWN, method = RequestMethod.GET)
  public int shutdown() {

    int seconds = 2;

    new Thread() {

      @Override
      public void run() {

        int status = 0;
        if (seconds > 0) {
          try {
            sleep(seconds * 1000);
          } catch (InterruptedException e) {
            status = 255;
          }
        }

        System.exit(status);

      }
    }.start();
    
    return 0;
  }
}
