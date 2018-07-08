package org.integratedmodelling.klab.node;

import java.util.Arrays;

import javax.annotation.PreDestroy;
import javax.inject.Singleton;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Singleton
@EnableAutoConfiguration
@ComponentScan(basePackages = { 
		"org.integratedmodelling.klab.node.security",
		"org.integratedmodelling.klab.node.controllers"
})
public class NodeApplication {

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
	
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
