package org.integratedmodelling.klab.hub.repository;

import org.bson.types.ObjectId;
import org.integratedmodelling.klab.hub.api.MongoNode;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MongoNodeRepository extends ResourceRepository<MongoNode, String>{
	
	Optional<MongoNode> findById(String id);
	
	Optional<MongoNode> findByNameIgnoreCase(String name);
	
	Optional<MongoNode> findByEmailIgnoreCase(String email);
	
	Optional<MongoNode> findByNameIgnoreCaseOrEmailIgnoreCase(String username, String email);
	
    Boolean existsByNameIgnoreCase(String node);

    Boolean existsByEmailIgnoreCase(String email);
    
	public List<MongoNode> findAll();
}
