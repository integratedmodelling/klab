package org.integratedmodelling.klab.hub.repository;

import java.util.List;
import java.util.Optional;

import org.integratedmodelling.klab.hub.tags.dto.MongoTag;
import org.integratedmodelling.klab.hub.tags.dto.TagNotification;
import org.springframework.data.mongodb.repository.DeleteQuery;

public interface TagNotificationRepository extends ResourceRepository<TagNotification, String> {

    List<TagNotification> findAll();

    Optional<TagNotification> findByTag(MongoTag tag);

    @DeleteQuery
    void deleteByTag(MongoTag tag);
}
