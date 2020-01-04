package org.integratedmodelling.klab.node;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Logo;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.auth.ICertificate;
import org.integratedmodelling.klab.api.auth.IPartnerIdentity;
import org.integratedmodelling.klab.api.node.INodeStartupOptions;
import org.integratedmodelling.klab.api.services.IConfigurationService;
import org.integratedmodelling.klab.auth.KlabCertificate;
import org.integratedmodelling.klab.exceptions.KlabAuthorizationException;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.node.auth.NodeAuthenticationManager;
import org.integratedmodelling.klab.utils.URLUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * This will start a node at http://localhost:8287/node with the default
 * security config.
 * 
 * @author ferdinando.villa
 * 
 */
public class Node {

	int port = IConfigurationService.DEFAULT_NODE_PORT;
	private ConfigurableApplicationContext context;
	private String contextPath = "/node";
	private IPartnerIdentity owner;
	private ICertificate certificate;
	
	public Node(INodeStartupOptions options, ICertificate certificate) {
		this.certificate = certificate;
		this.owner = NodeAuthenticationManager.INSTANCE.authenticate(certificate, options);
		// in engine: setRootIdentity(this.owner);
	}

	public String getLocalAddress() {
		return "http://127.0.0.1:" + port + contextPath;
	}

	public void run(String[] args) {
		NodeStartupOptions options = new NodeStartupOptions();
		options.initialize(args);
	}

	public static Node start() {
		return start(new NodeStartupOptions());
	}

	public static Node start(INodeStartupOptions options) {

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

		/*
		 * This authenticates with the hub
		 */
		Node ret = new Node(options, certificate);

		if (!ret.boot(options)) {
			throw new KlabException("node failed to start");
		}

		return ret;
	}

	private boolean boot(INodeStartupOptions options) {
		try {
			this.port = options.getPort();
			Map<String, Object> props = new HashMap<>();
			props.put("server.port", "" + options.getPort());
			props.put("spring.main.banner-mode", "off");
			props.put("server.servlet.contextPath", contextPath);
			SpringApplication app = new SpringApplication(NodeApplication.class);
			app.setDefaultProperties(props);
			this.context = app.run(options.getArguments());
			System.out.println("\n" + Logo.NODE_BANNER);
			System.out.println(
					"\nStartup successful: " + "k.LAB node server" + " v" + Version.CURRENT + " on " + new Date());
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
