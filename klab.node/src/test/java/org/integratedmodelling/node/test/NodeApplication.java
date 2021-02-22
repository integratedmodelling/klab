package org.integratedmodelling.node.test;

import java.util.Arrays;

import javax.annotation.PreDestroy;
import javax.inject.Singleton;

import org.integratedmodelling.klab.node.NodeStartupOptions;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.http.converter.protobuf.ProtobufJsonFormatHttpMessageConverter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Singleton
@EnableAutoConfiguration
@ComponentScan(basePackages = {"org.integratedmodelling.klab.node.test.security", "org.integratedmodelling.klab.node.resources",
        "org.integratedmodelling.klab.node.controllers"})
public class NodeApplication {

    // property specifying the resource online checking interval in seconds
    public static final String RESOURCE_CHECKING_INTERVAL_SECONDS = "klab.node.resources.checkinterval";

    private static Node node;

    public void run(String[] args) {
        NodeStartupOptions options = new NodeStartupOptions();
        options.initialize(args);
        node = Node.start(options);
    }

    @PreDestroy
    public void shutdown() {
        // TODO engine shutdown if needed
    }

    @Bean
    public ProtobufJsonFormatHttpMessageConverter ProtobufJsonFormatHttpMessageConverter() {
        return new ProtobufJsonFormatHttpMessageConverter();
    }

    @Bean
    public RestTemplate restTemplate(ProtobufHttpMessageConverter hmc) {
        return new RestTemplate(Arrays.asList(hmc));
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static void main(String args[]) {
        new NodeApplication().run(args);
    }

}
