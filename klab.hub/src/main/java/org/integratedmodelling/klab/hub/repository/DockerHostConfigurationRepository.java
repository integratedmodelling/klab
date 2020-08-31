package org.integratedmodelling.klab.hub.repository;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.integratedmodelling.klab.hub.network.DockerHostConfig;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DockerHostConfigurationRepository extends MongoRepository<DockerHostConfig, ObjectId>{
	Optional<DockerHostConfig> findById(String id);
}
