package org.integratedmodelling.klab.hub.repository;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.integratedmodelling.klab.hub.tokens.AuthenticationToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends MongoRepository<AuthenticationToken, ObjectId> {

    List<AuthenticationToken> findByUsername(String username);
    Optional<AuthenticationToken> findByTokenString(String tokenString);
	
    @Query("{'username' : ?0 , '_class' : ?1}")
    Optional<AuthenticationToken> findByUsernameAndClass(String username,  String _class);

}