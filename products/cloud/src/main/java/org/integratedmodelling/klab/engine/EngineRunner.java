package org.integratedmodelling.klab.engine;

import java.util.Arrays;
import java.util.stream.StreamSupport;

import javax.annotation.PreDestroy;

import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.api.auth.ICertificate;
import org.integratedmodelling.klab.auth.KlabCertificate;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.stereotype.Component;


//@ComponentScan(basePackages = { "org.integratedmodelling.klab.engine"})
@Component
public class EngineRunner implements ApplicationListener<ApplicationPreparedEvent>{


	@Bean
	public ProtobufHttpMessageConverter protobufHttpMessageConverter() {
		return new ProtobufHttpMessageConverter();
	}

	
	/*
	 * @Bean public RestTemplate restTemplate(ProtobufHttpMessageConverter hmc) {
	 * return new RestTemplate(Arrays.asList(hmc)); }
	 */
	
	@Bean
	public RemoteEngineService remoteEngineService() {
		RemoteEngineService service = new RemoteEngineService();
		service.setEngine(engine);
		return service;
	}

	
	private EngineStartupOptions options;
	
	
	public EngineRunner() {
	}
	
	
	public EngineRunner( String[] args ) {
	    if (args.length != 0) {
	        this.options = new EngineStartupOptions(args);
	    }
	}


    private static RemoteEngine engine;
	private static Environment environment;
	private ICertificate certificate;
	
	public static EngineRunner start(ApplicationPreparedEvent event) {
		environment = event.getApplicationContext().getEnvironment();
		return run(event.getArgs());
		
	}

	
	private static EngineRunner run(String[] args) {
		EngineRunner ret = new EngineRunner(args);
		if(!ret.boot()){
			throw new KlabException("Engine failed to start");
		}
		
		return ret;	
	}


	@PreDestroy
	public void shutdown() {
		engine.stop();
	}
	
	private boolean boot() {
		try {
		    String consul = environment.getProperty("spring.cloud.consul.enabled");
		    if(consul == "true") {
		        String certString = environment.getProperty("klab.certificate");
		        this.certificate = KlabCertificate.createFromString(certString);
		        setPropertiesFromEnvironment(environment);
		        engine = RemoteEngine.start(this.certificate, new EngineStartupOptions());
		    } else {
		        if(this.options != null) {
		            engine = RemoteEngine.start(null, this.options);
		        } else {
		            engine = RemoteEngine.start(null, new EngineStartupOptions());
		        }
		    }
		    
		    if (engine != null) {
		        engine.setName(environment.getProperty("ENGINE_SERVICE"));
		    }
		    
		} catch (Throwable e) {
			return false;
		}
		return true;
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

	
	@Override
	public void onApplicationEvent(ApplicationPreparedEvent event) {
		if (engine == null) {
			start(event);
		} else {
			return;
		}
	}

}
