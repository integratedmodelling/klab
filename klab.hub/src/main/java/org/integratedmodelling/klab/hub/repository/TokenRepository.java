package org.integratedmodelling.klab.hub.repository;

import java.util.List;
import java.util.Optional;

import org.integratedmodelling.klab.hub.tokens.dto.TokenAuthentication;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends ResourceRepository<TokenAuthentication, String> {

    List<TokenAuthentication> findByUsername(String username);
    Optional<TokenAuthentication> findByTokenString(String tokenString);
	
    @Query("{'username' : ?0 , '_class' : ?1}")
    Optional<TokenAuthentication> findByUsernameAndClass(String username,  String _class);
	List<TokenAuthentication> deleteAllByUsername(String username);

}