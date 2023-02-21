package org.integratedmodelling.klab.hub.repository;

import java.util.List;
import java.util.Optional;

import org.integratedmodelling.klab.hub.api.MongoTag;
import org.integratedmodelling.klab.rest.HubNotificationMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoTagRepository extends MongoRepository<MongoTag, String> {

    List<MongoTag> findAll();

    Optional<MongoTag> findByName(String name);

    @Query("{'type' : ?0}")
    List<MongoTag> getMongoTagsByType(HubNotificationMessage.Type type);

}
