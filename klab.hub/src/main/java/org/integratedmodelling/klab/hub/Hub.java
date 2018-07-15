package org.integratedmodelling.klab.hub;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Logo;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.auth.ICertificate;
import org.integratedmodelling.klab.api.hub.IHubStartupOptions;
import org.integratedmodelling.klab.api.services.IConfigurationService;
import org.integratedmodelling.klab.auth.KlabCertificate;
import org.integratedmodelling.klab.exceptions.KlabAuthorizationException;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.hub.authentication.HubAuthenticationManager;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * This will start a hub at http://localhost:8284/klab with the default security
 * config.
 * 
 * @author ferdinando.villa
 * 
 */
public class Hub {

	int port = IConfigurationService.DEFAULT_HUB_PORT;
	private ConfigurableApplicationContext context;
	private String contextPath = "/klab";
	private HubAuthenticationManager authManager;
	private ICertificate certificate;

	public Hub(IHubStartupOptions options, ICertificate certificate) {
		this.certificate = certificate;
		// cert is prevalidated and we are the top consumers, so no further
		// authentication needed
	}

	public String getLocalAddress() {
		return "http://127.0.0.1:" + port + contextPath;
	}

	public void run(String[] args) {
		HubStartupOptions options = new HubStartupOptions();
		options.initialize(args);
	}

	public static Hub start() {
		return start(new HubStartupOptions());
	}

	public static Hub start(IHubStartupOptions options) {

		ICertificate certificate = null;

		if (options.getCertificateResource() != null) {
			certificate = KlabCertificate.createFromClasspath(options.getCertificateResource());
		} else {
			File certFile = options.getCertificateFile();
			certificate = certFile.exists() ? KlabCertificate.createFromFile(certFile)
					: KlabCertificate.createDefault();
		}

		if (!certificate.isValid()) {
			throw new KlabAuthorizationException("certificate is invalid: " + certificate.getInvalidityCause());
		}

		Hub ret = new Hub(options, certificate);

		if (!ret.boot(options)) {
			throw new KlabException("node failed to start");
		}

		return ret;
	}

	private boolean boot(IHubStartupOptions options) {
		try {
			this.port = options.getPort();
			Map<String, Object> props = new HashMap<>();
			props.put("server.port", "" + options.getPort());
			props.put("spring.main.banner-mode", "off");
			props.put("server.servlet.contextPath", contextPath);
			SpringApplication app = new SpringApplication(HubApplication.class);
			app.setDefaultProperties(props);
			this.context = app.run(options.getArguments());
			this.authManager = this.context.getBean(HubAuthenticationManager.class);
			this.authManager.authenticate(options, this.certificate);
			System.out.println("\n" + Logo.HUB_BANNER);
			System.out.println(
					"\nStartup successful: " + "k.LAB hub server" + " v" + Version.CURRENT + " on " + new Date());
		} catch (Throwable e) {
			Logging.INSTANCE.error(e);
			return false;
		}
		return true;
	}

	public void stop() {

		// // shutdown all components
		// if (this.sessionClosingTask != null) {
		// this.sessionClosingTask.cancel(true);
		// }
		//
		// // shutdown the task executor
		// if (taskExecutor != null) {
		// taskExecutor.shutdown();
		// try {
		// if (!taskExecutor.awaitTermination(800, TimeUnit.MILLISECONDS)) {
		// taskExecutor.shutdownNow();
		// }
		// } catch (InterruptedException e) {
		// taskExecutor.shutdownNow();
		// }
		// }
		//
		// // shutdown the script executor
		// if (scriptExecutor != null) {
		// scriptExecutor.shutdown();
		// try {
		// if (!scriptExecutor.awaitTermination(800, TimeUnit.MILLISECONDS)) {
		// scriptExecutor.shutdownNow();
		// }
		// } catch (InterruptedException e) {
		// scriptExecutor.shutdownNow();
		// }
		// }
		//
		// // and the session scheduler
		// if (scheduler != null) {
		// scheduler.shutdown();
		// try {
		// if (!scheduler.awaitTermination(800, TimeUnit.MILLISECONDS)) {
		// scheduler.shutdownNow();
		// }
		// } catch (InterruptedException e) {
		// scheduler.shutdownNow();
		// }
		// }
		//
		// // shutdown the runtime
		// Klab.INSTANCE.getRuntimeProvider().shutdown();

		context.close();
	}

}
