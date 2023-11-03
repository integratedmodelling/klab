package org.integratedmodelling.klab.hub.repository;

import java.util.List;
import java.util.Optional;

import org.integratedmodelling.klab.hub.api.MongoTag;
import org.integratedmodelling.klab.hub.api.TagNotification;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TagNotificationRepository extends ResourceRepository<TagNotification, String> {

    List<TagNotification> findAll();

    Optional<TagNotification> findByTag(MongoTag tag);

    @DeleteQuery
    void deleteByTag(MongoTag tag);
}
