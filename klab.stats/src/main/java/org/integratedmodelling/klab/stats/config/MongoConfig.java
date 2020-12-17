//package org.integratedmodelling.klab.stats.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.mongodb.MongoDbFactory;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
//import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
//import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
//import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
//import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
//
//import com.mongodb.MongoClient;
//import com.mongodb.ServerAddress;
//
//import de.bwaldvogel.mongo.MongoServer;
//import de.bwaldvogel.mongo.ServerVersion;
//import de.bwaldvogel.mongo.backend.memory.MemoryBackend;
//
//@Configuration
//@EnableMongoRepositories(basePackages = "org.integratedmodelling.klab.stats.repository")
//public class MongoConfig {
//	@Autowired
//    private MappingMongoConverter mongoConverter;
//    
//    @Bean
//    public MongoTemplate mongoTemplate(MongoClient mongoClient) {
//    	this.mongoConverter.setMapKeyDotReplacement("#");
//        return new MongoTemplate(mongoDbFactory(mongoClient), this.mongoConverter);
//    }
//
//    @Bean
//    public MongoDbFactory mongoDbFactory(MongoClient mongoClient) {
//        return new SimpleMongoDbFactory(mongoClient, getDatabaseName());
//    }
//    
//    @Bean
//    public ValidatingMongoEventListener validatingMongoEventListener() {
//        return new ValidatingMongoEventListener(validator());
//    }
//
//    @Bean
//    public LocalValidatorFactoryBean validator() {
//        return new LocalValidatorFactoryBean();
//    }
//	
//    @Bean(destroyMethod="shutdown")
//    public MongoServer mongoServer() {
//    	MemoryBackend backend = (MemoryBackend) new MemoryBackend().version(ServerVersion.MONGO_3_6);
//        MongoServer mongoServer = new MongoServer(backend);
//        mongoServer.bind("localhost", 27017);
//        return mongoServer;
//    }
//    
//    @Bean(destroyMethod="close")
//    public MongoClient mongoClient(MongoServer mongoServer) {
//        return new MongoClient(new ServerAddress(mongoServer.getLocalAddress()));
//    }
//	
//    protected String getMappingBasePackage() {
//        return "org.integratedmodelling.klab.stats";
//    }
//
//	protected String getDatabaseName() {
//		return "stats";
//	}
//
//	public MongoClient mongoClient() {
//		return new MongoClient("localhost", 27017);
//	}
//}
