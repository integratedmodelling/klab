package org.integratedmodelling.klab.hub.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import org.integratedmodelling.klab.hub.models.KlabNode;

@Repository
public interface KlabNodeRepository extends CrudRepository<KlabNode, Long>{
	
	Optional<KlabNode> findById(String id);
	
	Optional<KlabNode> findByNodeIgnoreCase(String username);
	
	Optional<KlabNode> findByEmailIgnoreCase(String email);
	
	Optional<KlabNode> findByNodeIgnoreCaseOrEmailIgnoreCase(String username, String email);
	
    Boolean existsByNodeIgnoreCase(String username);

    Boolean existsByEmailIgnoreCase(String email);
    
	public List<KlabNode> findAll();
}
