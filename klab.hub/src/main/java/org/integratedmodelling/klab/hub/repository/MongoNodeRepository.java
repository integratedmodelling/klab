package org.integratedmodelling.klab.hub.repository;

import org.bson.types.ObjectId;
import org.integratedmodelling.klab.hub.nodes.MongoNode;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MongoNodeRepository extends MongoRepository<MongoNode, ObjectId>{
	
	Optional<MongoNode> findById(String id);
	
	Optional<MongoNode> findByNodeIgnoreCase(String node);
	
	Optional<MongoNode> findByEmailIgnoreCase(String email);
	
	Optional<MongoNode> findByNodeIgnoreCaseOrEmailIgnoreCase(String username, String email);
	
    Boolean existsByNodeIgnoreCase(String node);

    Boolean existsByEmailIgnoreCase(String email);
    
	public List<MongoNode> findAll();
}
