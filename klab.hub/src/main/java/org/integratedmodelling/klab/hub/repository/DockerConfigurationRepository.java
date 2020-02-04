package org.integratedmodelling.klab.hub.repository;

import org.bson.types.ObjectId;
import org.integratedmodelling.klab.hub.network.DockerConfiguration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DockerConfigurationRepository extends MongoRepository<DockerConfiguration, ObjectId>{

}
