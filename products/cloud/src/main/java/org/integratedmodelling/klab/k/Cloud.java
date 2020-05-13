package org.integratedmodelling.klab.k;

import java.util.Arrays;
import java.util.stream.StreamSupport;

import javax.annotation.PreDestroy;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.api.auth.ICertificate;
import org.integratedmodelling.klab.auth.KlabCertificate;
import org.integratedmodelling.klab.engine.Engine;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@ComponentScan(basePackages = { "org.integratedmodelling.klab.engine.rest.security",
		"org.integratedmodelling.klab.engine.rest.controllers.base",
		"org.integratedmodelling.klab.engine.rest.controllers.engine",
		"org.integratedmodelling.klab.engine.rest.controllers.network",
		"org.integratedmodelling.klab.engine.rest.messaging",
		"org.integratedmodelling.klab.engine.rest.controllers.resources" })
public class Cloud implements ApplicationListener<ApplicationPreparedEvent>{


	@Bean
	public ProtobufHttpMessageConverter protobufHttpMessageConverter() {
		return new ProtobufHttpMessageConverter();
	}

	@Bean
	public RestTemplate restTemplate(ProtobufHttpMessageConverter hmc) {
		return new RestTemplate(Arrays.asList(hmc));
	}
	
	public Cloud() {
	}
	
	private static Engine engine;
	private static Environment environment;
	private ICertificate certificate;


	public static Cloud start(ApplicationPreparedEvent event) {
		environment = event.getApplicationContext().getEnvironment();
		return run();
		
	}

	private static Cloud run() {
		Cloud ret = new Cloud();
		if(!ret.boot()){
			throw new KlabException("hub failed to start");
		};
		return ret;	
	}



	@PreDestroy
	public void shutdown() {
		engine.stop();
	}
	
	private boolean boot() {
		try {
			Environment env = environment;
			String certString = env.getProperty("klab.certificate");
			this.certificate = KlabCertificate.createFromString(certString);
			setPropertiesFromEnvironment(env);
			engine = Engine.start(this.certificate);
		} catch (Throwable e) {
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

	@Override
	public void onApplicationEvent(ApplicationPreparedEvent event) {
		if (engine == null ) {
			start(event);
		} else {
			return;
		}
	}

}
