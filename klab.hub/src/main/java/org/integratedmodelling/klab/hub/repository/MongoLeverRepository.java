package org.integratedmodelling.klab.hub.repository;

import java.util.List;
import java.util.Optional;

import org.integratedmodelling.klab.hub.licenses.dto.MongoLever;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoLeverRepository extends ResourceRepository<MongoLever, String>{
	Optional<MongoLever> findById(String id);
	Optional<MongoLever> findByNameIgnoreCase(String id);
	public List<MongoLever> findAll();
}
