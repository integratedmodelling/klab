package org.integratedmodelling.klab.hub.config;


import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "org.integratedmodelling.klab.hub.repository")
@EnableMongoAuditing
@Profile("development")
@Import(EmbeddedMongoAutoConfiguration.class)

public class MongoConfigDev {

}