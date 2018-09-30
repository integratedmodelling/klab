package org.integratedmodelling.klab.engine.rest.controllers.base;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * The controller implementing the {@link org.integratedmodelling.klab.api.API.ADMIN
 * administration API}. Secured to the ADMIN role but all endpoints are
 * preauthorized when accessed from the local IP.
 * 
 * @author ferdinando.villa
 *
 */
@RestController
@Secured(Roles.ADMIN)
public class AdminController {

	@Autowired
	ApplicationContext applicationContext;

	public void shutDown(ExitCodeGenerator exitCodeGenerator) {
		SpringApplication.exit(applicationContext, exitCodeGenerator);
		System.exit(0);
	}

	@RequestMapping(value = API.ADMIN.SHUTDOWN, method = RequestMethod.GET)
	public int shutdown() {

		int seconds = 2;
		new Thread() {

			int status = 0;

			@Override
			public void run() {

				if (seconds > 0) {
					try {
						sleep(seconds * 1000);
					} catch (InterruptedException e) {
						status = -1;
					}
				}
				shutDown(new ExitCodeGenerator() {
					@Override
					public int getExitCode() {
						return status;
					}});
			}
		}.start();

		return 0;
	}
}
