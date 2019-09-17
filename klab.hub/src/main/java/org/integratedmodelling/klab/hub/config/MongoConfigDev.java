package org.integratedmodelling.klab.hub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;

@Profile("development")
@Configuration
@EnableMongoRepositories(basePackages = "org.integratedmodelling.klab.hub.repository")
@EnableMongoAuditing
public class MongoConfigDev extends MongoConfig {

    @Bean
    public MongoTemplate mongoTemplate(MongoClient mongoClient) {
        return new MongoTemplate(mongoDbFactory(mongoClient));
    }

    @Bean
    public MongoDbFactory mongoDbFactory(MongoClient mongoClient) {
        return new SimpleMongoDbFactory(mongoClient, "collaborationTest");
    }
	
    @Bean(destroyMethod="shutdown")
    public MongoServer mongoServer() {
        MongoServer mongoServer = new MongoServer(new MemoryBackend());
        mongoServer.bind("localhost", 27017);
        return mongoServer;
    }
    
    @Bean(destroyMethod="close")
    public MongoClient mongoClient(MongoServer mongoServer) {
        return new MongoClient(new ServerAddress(mongoServer.getLocalAddress()));
    }
    
	@Override
	protected String getAuthDatabaseName() {
        return "authTest";
    }
	
	@Override
    protected String getMappingBasePackage() {
        return "org.integratedmodelling.klab.hub";
    }

	@Override
	protected String getDatabaseName() {
		return "collaborationTest";
	}
    
}