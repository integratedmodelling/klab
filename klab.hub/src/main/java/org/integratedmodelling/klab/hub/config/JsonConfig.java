package org.integratedmodelling.klab.hub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class JsonConfig {

    @Bean
    public MappingJackson2HttpMessageConverter jacksonConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper());
        return converter;
    }

    @Bean
    public StringHttpMessageConverter stringConverter() {
        return new StringHttpMessageConverter();
    }
    
	@Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        objectMapper.configure(MapperFeature.AUTO_DETECT_GETTERS, false);
        objectMapper.configure(MapperFeature.AUTO_DETECT_IS_GETTERS, false);
        objectMapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        objectMapper.setSerializationInclusion(Include.NON_NULL);

        // all fields are serialized/deserialized, unless specifically annotated otherwise.
        objectMapper.setVisibility(new VisibilityChecker.Std(Visibility.ANY));

        return objectMapper;
    }
}
