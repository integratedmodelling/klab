package org.integratedmodelling.klab.hub.repository;

import java.util.List;
import java.util.Optional;

import org.integratedmodelling.klab.hub.tags.dto.MongoTag;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoTagRepository extends ResourceRepository<MongoTag, String> {

    List<MongoTag> findAll();

    Optional<MongoTag> findByName(String name);

    List<MongoTag> findAllByUsernameOrUsernameIsNull(String username);

}
