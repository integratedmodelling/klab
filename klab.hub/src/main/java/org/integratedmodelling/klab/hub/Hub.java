package org.integratedmodelling.klab.hub;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

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
import org.integratedmodelling.klab.hub.listeners.LicenseStartupPublisher;
import org.integratedmodelling.klab.hub.listeners.LicenseStartupReady;
import org.integratedmodelling.klab.rest.Group;
import org.integratedmodelling.klab.utils.FileCatalog;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

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
		if(!cloudEnabled()) {
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
			this.certificate = getCertFromEnv(environment);
			this.owner = HubAuthenticationManager.INSTANCE.authenticate(new HubStartupOptions(), certificate);
			LicenseStartupPublisher eventAPublisher = (LicenseStartupPublisher)context.getBean("licenseStartupPublisher");
			eventAPublisher.publish(new LicenseStartupReady(new Object()));
			
			System.out.println("\n" + Logo.HUB_BANNER);
			System.out.println(
					"\nStartup successful: " + "k.LAB hub server" + " v" + Version.CURRENT + " on " + new Date());
			
		} catch (Throwable e) {
			Logging.INSTANCE.error(e);
			return false;
		}
		return true;
	}
	
	private static boolean cloudEnabled( ) {
		try {
			Yaml yaml = new Yaml();
			Object loadedYaml = yaml.load(Hub.class.getClassLoader().getResourceAsStream("bootstrap.yml"));
			return cloundConfingEnabledd(loadedYaml);
		} catch (YAMLException e) {
			Logging.INSTANCE.info("Cloud configration not enabled");
			return false;
		}	
	}
	
	private static boolean cloundConfingEnabledd(Object yml) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(yml,LinkedHashMap.class);
		JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);
		jsonObject = new Gson().fromJson(jsonObject.get("spring"), JsonObject.class);
		jsonObject = new Gson().fromJson(jsonObject.get("cloud"), JsonObject.class);
		jsonObject = new Gson().fromJson(jsonObject.get("consul"), JsonObject.class);
		jsonObject = new Gson().fromJson(jsonObject.get("config"), JsonObject.class);
		if (jsonObject.get("enabled").getAsBoolean()) {
			Logging.INSTANCE.info("Cloud configration enabled");
			return true;
		} else {
			return false;
		}
	}
	
	private KlabCertificate getCertFromEnv(Environment env) {
		Properties props = new Properties();
		props.setProperty(KlabCertificate.KEY_CERTIFICATE_TYPE,
				env.getProperty(KlabCertificate.KEY_CERTIFICATE_TYPE));
		props.setProperty(KlabCertificate.KEY_CERTIFICATE_LEVEL, 
				env.getProperty(KlabCertificate.KEY_CERTIFICATE_LEVEL));
		props.setProperty(KlabCertificate.KEY_HUBNAME, 
				env.getProperty(KlabCertificate.KEY_HUBNAME));
		props.setProperty(KlabCertificate.KEY_PARTNER_NAME, 
				env.getProperty(KlabCertificate.KEY_PARTNER_NAME));
		props.setProperty(KlabCertificate.KEY_PARTNER_EMAIL, 
				env.getProperty(KlabCertificate.KEY_PARTNER_EMAIL));
		props.setProperty(KlabCertificate.KEY_URL, 
				env.getProperty(KlabCertificate.KEY_URL));
		props.setProperty(KlabCertificate.KEY_SIGNATURE, 
				env.getProperty(KlabCertificate.KEY_SIGNATURE));
		props.setProperty(KlabCertificate.KEY_CERTIFICATE, 
				env.getProperty("klab.pgpKey"));
		return KlabCertificate.createFromProperties(props);
	}

	public void stop() {
		
		context.close();
	}

}
