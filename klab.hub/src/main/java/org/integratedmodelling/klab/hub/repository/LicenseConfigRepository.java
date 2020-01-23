package org.integratedmodelling.klab.hub.repository;

import org.bson.types.ObjectId;
import org.integratedmodelling.klab.hub.license.LicenseConfiguration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.google.common.base.Optional;

@Repository
public interface LicenseConfigRepository extends MongoRepository<LicenseConfiguration, ObjectId>{
	Optional<LicenseConfiguration> findById(String id);
}
