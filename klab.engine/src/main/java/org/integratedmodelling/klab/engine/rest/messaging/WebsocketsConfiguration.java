package org.integratedmodelling.klab.engine.rest.messaging;

import java.util.List;

import org.integratedmodelling.klab.api.API;
import org.springframework.beans.factory.annotation.Autowired;
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
		registry.addEndpoint(API.MESSAGE).withSockJS();
	}

	@Override
	public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
		registration
			.setSendTimeLimit(15 * 1000)
			.setMessageSizeLimit(256 * 1024)
			.setSendBufferSizeLimit(2048 * 1024);
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
