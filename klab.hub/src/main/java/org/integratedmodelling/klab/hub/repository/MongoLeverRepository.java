package org.integratedmodelling.klab.hub.repository;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.integratedmodelling.klab.hub.api.MongoLever;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoLeverRepository extends MongoRepository<MongoLever, ObjectId>{
	Optional<MongoLever> findById(String id);
	Optional<MongoLever> findByNameIgnoreCase(String id);
	public List<MongoLever> findAll();
}
