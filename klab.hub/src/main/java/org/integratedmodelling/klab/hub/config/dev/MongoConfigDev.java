package org.integratedmodelling.klab.hub.config.dev;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.ServerVersion;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;

@Profile("development")
@Configuration
@EnableMongoRepositories(basePackages = "org.integratedmodelling.klab.hub.repository")
@EnableMongoAuditing
public class MongoConfigDev extends AbstractMongoClientConfiguration {
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
	
    @Bean(destroyMethod="shutdown")
    public MongoServer mongoServer() {
    	MemoryBackend backend = (MemoryBackend) new MemoryBackend().version(ServerVersion.MONGO_3_6);
        MongoServer mongoServer = new MongoServer(backend);
        mongoServer.bind("localhost", 27018);
        return mongoServer;
    }
    
    @Bean(destroyMethod="close")
    public MongoClient mongoClient(MongoServer mongoServer) {
    	String con = "mongodb://" + new ServerAddress(mongoServer.getLocalAddress());
        return MongoClients.create(con);
    }
	
	@Override
    protected String getMappingBasePackage() {
        return "org.integratedmodelling.klab.hub";
    }

	@Override
	protected String getDatabaseName() {
		return "hubTest";
	}

	@Override
	public MongoClient mongoClient() {
		String con = "mongodb://" + "localhost" + ":" + 27018;
		return MongoClients.create(con);
	}
}