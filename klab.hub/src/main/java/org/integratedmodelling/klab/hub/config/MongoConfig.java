package org.integratedmodelling.klab.hub.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.mongodb.MongoClient;

@Profile("production")
@Configuration
@EnableMongoRepositories(basePackages = "org.integratedmodelling.klab.hub.repository")
@EnableMongoAuditing
public class MongoConfig {
	@Autowired
	private MongoDbFactory mongoFactory;

	@Autowired
	private MongoMappingContext mongoMappingContext;

	@Bean
	public MappingMongoConverter mongoConverter() throws Exception {
	  DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoFactory);
	  MappingMongoConverter mongoConverter = new MappingMongoConverter(dbRefResolver, mongoMappingContext);
	  //this is my customization
	  mongoConverter.setMapKeyDotReplacement("#");
	  return mongoConverter;
	}
    

}