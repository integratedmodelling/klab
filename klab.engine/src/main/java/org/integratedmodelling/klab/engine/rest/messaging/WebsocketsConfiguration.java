package org.integratedmodelling.klab.engine.rest.messaging;

import java.util.List;

import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.services.IConfigurationService;
import org.integratedmodelling.klab.monitoring.Message;
import org.integratedmodelling.klab.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.DefaultContentTypeResolver;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.handler.invocation.HandlerMethodReturnValueHandler;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * Usage: from JS:
 * 
 * <pre>
    var socket = new SockJS('/modeler/message');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        stompClient.subscribe('/message/' + sessionId, function(command){
            var cmd = JSON.parse(command.body);
            // process(cmd);
        });
    });
  
    Sending:
    
    stompClient.send("/klab/message", {}, JSON.stringify({ 'name': name}
 * 
 * </pre>
 * 
 * @author ferdinando.villa
 *
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebsocketsConfiguration implements WebSocketMessageBrokerConfigurer {

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
    	Message.setPayloadMapTranslator((map, cls) -> { 
    		if (IConfigurationService.REST_RESOURCES_PACKAGE_ID.equals(cls.getPackage().getName())) {
    			return JsonUtils.convertMap(map, cls);
    		}
    		return map;
    	});
        registry.addEndpoint(API.MESSAGE).setAllowedOriginPatterns("*").withSockJS();
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        registration.setSendTimeLimit(15 * 1000).setMessageSizeLimit(1024 * 1024).setSendBufferSizeLimit(1024 * 1024);
    }

    @Bean
    public ServletServerContainerFactoryBean createServletServerContainerFactoryBean() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(32768);
        container.setMaxBinaryMessageBufferSize(32768);
        return container;
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
    }

    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        registration.taskExecutor().corePoolSize(4).maxPoolSize(10);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        // TODO: ??
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        // TODO: ??
    }

    @Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
        // Workaround for issue 2445:
        // https://github.com/spring-projects/spring-boot/issues/2445
        DefaultContentTypeResolver resolver = new DefaultContentTypeResolver();
        resolver.setDefaultMimeType(MimeTypeUtils.APPLICATION_JSON);
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setObjectMapper(objectMapper);
        converter.setContentTypeResolver(resolver);
        messageConverters.add(converter);
        return false;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry configurer) {
        // Prefix for messages FROM server TO client
        configurer.enableSimpleBroker(API.MESSAGE);
        // Prefix for messages FROM client TO server, sent to /klab/message: :
        configurer.setApplicationDestinationPrefixes("/klab");
    }

}
