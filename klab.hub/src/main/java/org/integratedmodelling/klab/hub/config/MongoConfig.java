package org.integratedmodelling.klab.hub.config;


import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;


@Profile("production")
@Configuration
@EnableMongoRepositories(basePackages = "org.integratedmodelling.klab.hub.repository")
@EnableMongoAuditing
public class MongoConfig extends AbstractMongoClientConfiguration {
	
    @Value("${mongo.hostname}")
    private String HOSTNAME;

    @Value("${mongo.port}")
    private int PORT;

	@Autowired
    private MappingMongoConverter mongoConverter;
    
    @Bean
    public MongoTemplate mongoTemplate(MongoClient mongoClient) {
    	this.mongoConverter.setMapKeyDotReplacement("#");
        return new MongoTemplate(mongoDbFactory(mongoClient), this.mongoConverter);
    }

    @Bean
    public MongoDatabaseFactory mongoDbFactory(MongoClient mongoClient) {
        return new SimpleMongoClientDatabaseFactory(mongoClient, getDatabaseName());
    }
    
    @Bean
    public ValidatingMongoEventListener validatingMongoEventListener() {
        return new ValidatingMongoEventListener(validator());
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }
    
    @Override
    protected Collection<String> getMappingBasePackages(){
        return Arrays.asList("org.integratedmodelling.klab.hub.api");
    }
    
	@Override
	protected String getDatabaseName() {
		return "hub";
	}

	@Override
	public MongoClient mongoClient() {
		String con = "mongodb://" + HOSTNAME + ":" + PORT;
		return MongoClients.create(con);
	}
}