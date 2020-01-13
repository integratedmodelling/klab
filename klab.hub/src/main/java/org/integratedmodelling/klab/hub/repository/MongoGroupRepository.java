package org.integratedmodelling.klab.hub.repository;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.integratedmodelling.klab.hub.groups.MongoGroup;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoGroupRepository extends MongoRepository<MongoGroup, ObjectId>{
	
	List<MongoGroup> findAll();
	Optional<MongoGroup> findById(String id);
	Optional<MongoGroup> findByGroupNameIgnoreCase(String groupName);
	boolean existsByGroupNameIgnoreCase(String groupName);
	
}
