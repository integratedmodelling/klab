package org.integratedmodelling.klab.hub.repository;

import java.util.Optional;

import org.integratedmodelling.klab.hub.api.MongoTag;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoTagRepository extends MongoRepository<MongoTag, String> {

    Optional<MongoTag> findByName(String name);

}
