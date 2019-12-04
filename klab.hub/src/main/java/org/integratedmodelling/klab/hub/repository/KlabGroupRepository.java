package org.integratedmodelling.klab.hub.repository;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.integratedmodelling.klab.hub.models.KlabGroup;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KlabGroupRepository extends MongoRepository<KlabGroup, ObjectId>{
	
	List<KlabGroup> findAll();
	
	Optional<KlabGroup> findById(String id);
	
	Optional<KlabGroup> findByGroupNameIgnoreCase(String groupName);
	
}
