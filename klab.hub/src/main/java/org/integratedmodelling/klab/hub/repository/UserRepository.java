package org.integratedmodelling.klab.hub.repository;

import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.integratedmodelling.klab.hub.api.User;
import org.integratedmodelling.klab.hub.api.User.AccountStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId>{	
	
	Optional<User> findById(String id);
	
	Optional<User> findByName(String username); // need exactly the username
	
	Optional<User> findByNameIgnoreCase(String username);
	
	Optional<User> findByEmailIgnoreCase(String email);
	
	Optional<User> findByNameIgnoreCaseOrEmailIgnoreCase(String username, String email);
	
    Boolean existsByNameIgnoreCase(String username);

    Boolean existsByEmailIgnoreCase(String email);
    
    @Query("{'groupEntries.group.$id' : ?0}")
	List<User> getUsersByGroupEntriesWithGroupId(ObjectId id);
    
    @Query("{'accountStatus' : ?0}")
	List<User> getUsersByAccountStatus(AccountStatus accountStatus);
}