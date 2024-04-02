package org.integratedmodelling.klab.hub.repository;

import java.util.List;
import java.util.Optional;

import org.integratedmodelling.klab.hub.nodes.dtos.MongoNode;
import org.springframework.stereotype.Repository;

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
