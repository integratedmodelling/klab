package org.integratedmodelling.klab.hub.repository;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.integratedmodelling.klab.hub.models.tokens.AuthenticationToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends MongoRepository<AuthenticationToken, ObjectId> {

    List<AuthenticationToken> findByUsername(String username);

    Optional<AuthenticationToken> findByTokenString(String tokenString);

}