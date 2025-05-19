package org.integratedmodelling.klab.hub.repository;

import java.util.List;
import java.util.Optional;

import org.integratedmodelling.klab.hub.groups.dto.MongoGroup;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoGroupRepository extends ResourceRepository<MongoGroup, String>{
	
	List<MongoGroup> findAll();
	Optional<MongoGroup> findById(String id);
	Optional<MongoGroup> findByNameIgnoreCase(String groupName);
	boolean existsByNameIgnoreCase(String groupName);
	
	@Query("{'complimentary' : true }")
	List<MongoGroup> findComplimentaryGroups(); 
	
    List<MongoGroup> findByOptInIsTrue();
    List<MongoGroup> findByComplimentaryIsTrue(); 

    List<MongoGroup> findByNameIn(List<String> names);
    List<MongoGroup> findByCustomProperties_KeyAndCustomProperties_Value(String key, String value);
	
}
