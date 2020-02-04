package org.integratedmodelling.klab.hub.repository;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.integratedmodelling.klab.hub.network.DockerConfiguration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DockerConfigurationRepository extends MongoRepository<DockerConfiguration, ObjectId>{
	Optional<DockerConfiguration> findById(String id);
}
