package org.integratedmodelling.klab.node;

import java.util.Arrays;

import javax.annotation.PreDestroy;
import javax.inject.Singleton;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Singleton
@EnableAutoConfiguration
@ComponentScan(basePackages = { "org.integratedmodelling.klab.engine.rest.controllers.base" })
public class NodeApplication implements ApplicationListener<ApplicationStartingEvent> {

	@PreDestroy
	public void shutdown() {
		// TODO engine shutdown if needed
	}

	@Bean
	public ProtobufHttpMessageConverter protobufHttpMessageConverter() {
		return new ProtobufHttpMessageConverter();
	}

	@Bean
	public RestTemplate restTemplate(ProtobufHttpMessageConverter hmc) {
		return new RestTemplate(Arrays.asList(hmc));
	}

	@Override
	public void onApplicationEvent(ApplicationStartingEvent arg0) {
	}

}
