package org.integratedmodelling.klab.hub;

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
import org.integratedmodelling.klab.api.hub.IHubStartupOptions;
import org.integratedmodelling.klab.api.services.IConfigurationService;
import org.integratedmodelling.klab.auth.KlabCertificate;
import org.integratedmodelling.klab.exceptions.KlabAuthorizationException;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.hub.authentication.HubAuthenticationManager;
import org.integratedmodelling.klab.hub.listeners.HubEventPublisher;
import org.integratedmodelling.klab.hub.listeners.HubReady;
import org.integratedmodelling.klab.rest.Group;
import org.integratedmodelling.klab.utils.FileCatalog;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;

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
	private String contextPath = "/hub";
	private IPartnerIdentity owner;
	private ICertificate certificate;
    FileCatalog<Group> defaultGroups;
    
    public Hub() {
    };
    
	public Hub(IHubStartupOptions options, ICertificate certificate) {
		this.certificate = certificate;
		this.owner = HubAuthenticationManager.INSTANCE.authenticate(options, certificate);
		// cert is prevalidated and we are the top consumers, so no further
		// authentication needed
	}

	public String getLocalAddress() {
		return "http://192.168.0.104:" + port + contextPath;
	}

	public void run(String[] args) {
		HubStartupOptions options = new HubStartupOptions();
		options.initialize(args);
	}

	public static Hub start() {
		return start(new HubStartupOptions());
	}

	public static Hub start(IHubStartupOptions options) {
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

			Hub ret = new Hub(options, certificate);

			if (!ret.boot(options)) {
				throw new KlabException("hub failed to start");
			}
			
			

			return ret;
		} else {
	
			Hub ret = new Hub();
			
			if(!ret.boot()){
				throw new KlabException("hub failed to start");
			};
	
			return ret;
		}
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
			
			System.out.println("\n" + Logo.HUB_BANNER);
			System.out.println(
					"\nStartup successful: " + "k.LAB hub server" + " v" + Version.CURRENT + " on " + new Date());
			
			applicationStarted();
			
		} catch (Throwable e) {
			Logging.INSTANCE.error(e);
			return false;
		}
		return true;
	}
	
	private boolean boot() {
		try {
			SpringApplication app = new SpringApplication(HubApplication.class);
			this.context = app.run();
			Environment environment = this.context.getEnvironment();
			String certString = environment.getProperty("klab.certificate");
			this.certificate = KlabCertificate.createFromString(certString);
			setPropertiesFromEnvironment(environment);
			this.owner = HubAuthenticationManager.INSTANCE.authenticate(new HubStartupOptions(), certificate);
			
			
			System.out.println("\n" + Logo.HUB_BANNER);
			System.out.println(
					"\nStartup successful: " + "k.LAB hub server" + " v" + Version.CURRENT + " on " + new Date());
			
			applicationStarted();
			
		} catch (Throwable e) {
			Logging.INSTANCE.error(e);
			return false;
		}
		return true;
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
	
	
	/*
	 * This is here because we can not catch the regular spring application events
	 * and the result is creating a custom event.  This will trigger beans that need to be
	 * run after the instances and spring app context have completed.  This order is slightly
	 * confused depending on the boot.  Cloud config expects 
	 */
	@SuppressWarnings("unchecked")
	private void applicationStarted() {
		HubReady event = new HubReady(new Object());
		HubEventPublisher<HubReady> eventAPublisher = (HubEventPublisher<HubReady> )context.getBean("hubEventPublisher");
		eventAPublisher.publish(event);
	}

	public void stop() {
		
		context.close();
	}

}
