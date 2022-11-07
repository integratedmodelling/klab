package org.integratedmodelling.klab.semantic;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.StreamSupport;

import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.auth.ICertificate;
import org.integratedmodelling.klab.api.auth.IPartnerIdentity;
import org.integratedmodelling.klab.api.semanticserver.ISemanticServerStartupOptions;
import org.integratedmodelling.klab.api.services.IConfigurationService;
import org.integratedmodelling.klab.auth.KlabCertificate;
import org.integratedmodelling.klab.engine.Engine;
import org.integratedmodelling.klab.exceptions.KlabAuthorizationException;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;

public class SemanticServer {

	int port = IConfigurationService.DEFAULT_SEMANTIC_SERVER_PORT;
	private ConfigurableApplicationContext context;
	private String contextPath = "/";
	private IPartnerIdentity owner;
	private ICertificate certificate;
	private Engine engine;
	
	/**
	 * 
	 * This needs to be rearagned so that the authentication happens after the spring boot
	 * so that we can use the injection of the properties to register the service with the hub.
	 * @param certificate
	 */
	
    public SemanticServer() {
    };
    
	public SemanticServer(ISemanticServerStartupOptions options, ICertificate certificate) {
		this.certificate = certificate;
//		this.owner = NodeAuthenticationManager.INSTANCE.authenticate(certificate, options);
	}

	public String getLocalAddress() {
		return "http://127.0.0.1:" + port + contextPath;
	}

	public void run(String[] args) {
		SemanticServerStartupOptions options = new SemanticServerStartupOptions();
		options.initialize(args);
	}

	public static SemanticServer start() {
		return start(new SemanticServerStartupOptions());
	}

	public static SemanticServer start(ISemanticServerStartupOptions options) {
		if(!options.isCloudConfig()) {
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
			SemanticServer ret = new SemanticServer(options, certificate);
	
			if (!ret.boot(options)) {
				throw new KlabException("node failed to start");
			}

			return ret;
		} else {
			SemanticServer ret = new SemanticServer();
			
			if(!ret.boot()){
				throw new KlabException("hub failed to start");
			};
	
			return ret;
		}
	}

	private boolean boot(ISemanticServerStartupOptions options) {
		try {
			SpringApplication app = new SpringApplication(SemanticServerApplication.class);
			this.context = app.run(options.getArguments());
			this.engine = Engine.start(this.certificate);
			this.port = options.getPort();
			Map<String, Object> props = new HashMap<>();
			props.put("server.port", "" + options.getPort());
			props.put("spring.main.banner-mode", "off");
			props.put("server.servlet.contextPath", contextPath);
			
			app.setDefaultProperties(props);
			System.out.println("\n" + ISemanticServerStartupOptions.BANNER);
			System.out.println(
					"\nStartup successful: " + "k.LAB semantic server" + " v" + Version.CURRENT + " on " + new Date());
		} catch (Throwable e) {
			Logging.INSTANCE.error(e);
			return false;
		}
		return true;
	}
	
	private boolean boot() {
		try {
			SpringApplication app = new SpringApplication(SemanticServerApplication.class);
			this.context = app.run();
			Environment environment = this.context.getEnvironment();
			String certString = environment.getProperty("klab.certificate");
			this.certificate = KlabCertificate.createFromString(certString);
			setPropertiesFromEnvironment(environment);
//			this.owner = NodeAuthenticationManager.INSTANCE.authenticate(certificate, new NodeStartupOptions());
			this.engine = Engine.start(this.certificate);
			System.out.println("\n" + ISemanticServerStartupOptions.BANNER);
			System.out.println(
					"\nStartup successful: " + "k.LAB semantic server" + " v" + Version.CURRENT + " on " + new Date());

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
		        .map(ps -> ((EnumerablePropertySource<?>) ps).getPropertyNames())
		        .flatMap(Arrays::<String>stream)
		        .forEach(propName -> Configuration.INSTANCE.getProperties().setProperty(propName, environment.getProperty(propName)));
		Configuration.INSTANCE.save();
		return;
	}
}
