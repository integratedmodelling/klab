package org.integratedmodelling.klab.hub.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.MongoClient;

@Profile("production")
@Configuration
@EnableMongoRepositories(basePackages = "org.integratedmodelling.klab.hub.repository")
@EnableMongoAuditing
public class MongoConfig extends AbstractMongoConfiguration {

    @Value("${mongo.hostname}")
    private String HOSTNAME;

    @Value("${mongo.port}")
    private int PORT;

    @Override
    protected String getDatabaseName() {
        return "collaboration";
    }

    protected String getAuthDatabaseName() {
        return "authProdTest";
    }
    
    @Override
    protected String getMappingBasePackage() {
        return "org.integratedmodelling.klab.hub";
    }

	@Override
	public MongoClient mongoClient() {
		System.out.println("In the mongo productions config");
		return new MongoClient(HOSTNAME, PORT);
	}

}