package org.integratedmodelling.klab.hub.repository;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.integratedmodelling.klab.hub.api.LicenseConfiguration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LicenseConfigRepository extends ResourceRepository<LicenseConfiguration, String>{
	Optional<LicenseConfiguration> findById(String id);
	Optional<LicenseConfiguration> findByKeyString(String keyString);
	Optional<LicenseConfiguration> findByNameIgnoreCase(String name);
	Optional<LicenseConfiguration> findByDefaultConfigIsTrue();
}
