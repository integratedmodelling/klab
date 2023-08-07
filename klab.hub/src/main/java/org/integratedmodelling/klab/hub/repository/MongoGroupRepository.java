package org.integratedmodelling.klab.hub.repository;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.integratedmodelling.klab.hub.api.MongoGroup;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoGroupRepository extends MongoRepository<MongoGroup, ObjectId>{
	
	List<MongoGroup> findAll();
	Optional<MongoGroup> findById(String id);
	Optional<MongoGroup> findByNameIgnoreCase(String groupName);
	boolean existsByNameIgnoreCase(String groupName);
	
	@Query("{'complimentary' : true }")
	List<MongoGroup> findComplimentaryGroups(); 
	
    List<MongoGroup> findByOptInIsTrue();
    List<MongoGroup> findByComplimentaryIsTrue(); 

    List<MongoGroup> findByNameIn(List<String> names);
	
}
