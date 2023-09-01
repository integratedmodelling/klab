package org.integratedmodelling.klab.hub.repository;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.integratedmodelling.klab.hub.api.DeletedUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeletedUserRepository extends ResourceRepository<DeletedUser, String>{	
	
	Optional<DeletedUser> findById(String id);
	
	Optional<DeletedUser> findByUsernameIgnoreCase(String username);
	
	Optional<DeletedUser> findByEmailIgnoreCase(String email);
	
	Optional<DeletedUser> findByUsernameIgnoreCaseOrEmailIgnoreCase(String username, String email);
	
}