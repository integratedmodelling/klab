package org.integratedmodelling.klab.hub.repository;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.integratedmodelling.klab.hub.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId>{	
	
	Optional<User> findById(String id);
	
	Optional<User> findByUsername(String username); // need exactly the username
	
	Optional<User> findByUsernameIgnoreCase(String username);
	
	Optional<User> findByEmailIgnoreCase(String email);
	
	Optional<User> findByUsernameIgnoreCaseOrEmailIgnoreCase(String username, String email);
	
    Boolean existsByUsernameIgnoreCase(String username);

    Boolean existsByEmailIgnoreCase(String email);
}