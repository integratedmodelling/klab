package org.integratedmodelling.node.test;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.StreamSupport;

import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Logo;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.auth.ICertificate;
import org.integratedmodelling.klab.api.auth.IPartnerIdentity;
import org.integratedmodelling.klab.api.node.INodeStartupOptions;
import org.integratedmodelling.klab.api.services.IConfigurationService;
import org.integratedmodelling.klab.auth.KlabCertificate;
import org.integratedmodelling.klab.engine.Engine;
import org.integratedmodelling.klab.exceptions.KlabAuthorizationException;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.node.NodeStartupOptions;
import org.integratedmodelling.klab.node.auth.NodeAuthenticationManager;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;

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
	private Engine engine;
	
    public Node() {
    };
    
	public Node(INodeStartupOptions options, ICertificate certificate) {
		this.certificate = certificate;
		this.owner = NodeAuthenticationManager.INSTANCE.authenticate(certificate, options);
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
//		if(!options.isCloudConfig()) {
//			ICertificate certificate = null;
//	
//			if (options.getCertificateResource() != null) {
//				certificate = KlabCertificate.createFromClasspath(options.getCertificateResource());
//			} else {
//				File certFile = options.getCertificateFile();
//				certificate = certFile.exists() ? KlabCertificate.createFromFile(certFile)
//						: KlabCertificate.createDefault();
//			}
//	
//			if (!certificate.isValid()) {
//				throw new KlabAuthorizationException("certificate is invalid: " + certificate.getInvalidityCause());
//			}
//	
//			/*
//			 * This authenticates with the hub
//			 */
//			Node ret = new Node(options, certificate);
//	
//			if (!ret.boot(options)) {
//				throw new KlabException("node failed to start");
//			}
//
//			return ret;
//		} else {
			Node ret = new Node();
			
			if(!ret.boot(options)){
				throw new KlabException("node failed to start");
			};
	
			return ret;
//		}
	}

	private boolean boot(INodeStartupOptions options) {
		try {
			SpringApplication app = new SpringApplication(NodeApplication.class);
			this.context = app.run(options.getArguments());
			this.engine = Engine.start(this.certificate);
			this.port = options.getPort();
			Map<String, Object> props = new HashMap<>();
			props.put("server.port", "" + options.getPort());
			props.put("spring.main.banner-mode", "off");
			props.put("server.servlet.contextPath", contextPath);
			
			app.setDefaultProperties(props);
			System.out.println("\n" + Logo.NODE_BANNER);
			System.out.println(
					"\nStartup successful: " + "k.LAB node server" + " v" + Version.CURRENT + " on " + new Date());
		} catch (Throwable e) {
			Logging.INSTANCE.error(e);
			return false;
		}
		return true;
	}
	
	private boolean boot() {
		try {
			SpringApplication app = new SpringApplication(NodeApplication.class);
			this.context = app.run();
			Environment environment = this.context.getEnvironment();
			String certString = environment.getProperty("klab.certificate");
			this.certificate = KlabCertificate.createFromString(certString);
			setPropertiesFromEnvironment(environment);
			this.owner = NodeAuthenticationManager.INSTANCE.authenticate(certificate, new NodeStartupOptions());
			this.engine = Engine.start(this.certificate);
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
		context.close();
	}

	public IPartnerIdentity getOwner() {
		return owner;
	}

	public ICertificate getCertificate() {
		return certificate;
	}

	public Engine getEngine() {
		return engine;
	}
	
	
	private static void setPropertiesFromEnvironment(Environment environment) {
		MutablePropertySources propSrcs =  ((ConfigurableEnvironment) environment).getPropertySources();
		StreamSupport.stream(propSrcs.spliterator(), false)
		        .filter(ps -> ps instanceof EnumerablePropertySource)
		        .map(ps -> ((EnumerablePropertySource) ps).getPropertyNames())
		        .flatMap(Arrays::<String>stream)
		        .forEach(propName -> Configuration.INSTANCE.getProperties().setProperty(propName, environment.getProperty(propName)));
		Configuration.INSTANCE.save();
		return;
	}
	

}
