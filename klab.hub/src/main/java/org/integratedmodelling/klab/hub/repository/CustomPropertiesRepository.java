package org.integratedmodelling.klab.hub.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.integratedmodelling.klab.hub.api.CustomProperties;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.google.common.base.Optional;

@Repository
public interface CustomPropertiesRepository extends ResourceRepository<CustomProperties, String>{

        List<CustomProperties> findByIsForUserIsTrue();
        List<CustomProperties> findByIsForGroupIsTrue();
        Optional<CustomProperties> findByName(String name);

}
