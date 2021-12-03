package org.integratedmodelling.klab.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PreDestroy;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;


/**
 * A Cloud-driven k.LAB Engine.
 * 
 * @author steven.wohl
 *
 */
@EnableAutoConfiguration
@EnableDiscoveryClient
@ComponentScan
public class EngineApplication {
	
	private ConfigurableApplicationContext context;
	
	@Bean
	public RestTemplate restTemplate() {
		final RestTemplate restTemplate = new RestTemplate();
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
		messageConverters.add(converter);
		restTemplate.setMessageConverters(messageConverters);
		return restTemplate;
	}
	
	
	public void run(String[] args) {
		context = new SpringApplicationBuilder(EngineApplication.class)
				.listeners(new EngineRunner()).run(args);
	}
	
	
	@PreDestroy
	public void shutdown() {
	}
	
	
	
	public static void main(String args[]) {
		new EngineApplication().run(args);
	}


}
