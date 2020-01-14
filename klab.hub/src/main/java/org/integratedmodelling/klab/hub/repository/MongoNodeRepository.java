package org.integratedmodelling.klab.hub.repository;

import org.integratedmodelling.klab.hub.nodes.MongoNode;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MongoNodeRepository extends MongoRepository<MongoNode, Long>{
	
	Optional<MongoNode> findById(String id);
	
	Optional<MongoNode> findByNodeIgnoreCase(String username);
	
	Optional<MongoNode> findByEmailIgnoreCase(String email);
	
	Optional<MongoNode> findByNodeIgnoreCaseOrEmailIgnoreCase(String username, String email);
	
    Boolean existsByNodeIgnoreCase(String username);

    Boolean existsByEmailIgnoreCase(String email);
    
	public List<MongoNode> findAll();
}
