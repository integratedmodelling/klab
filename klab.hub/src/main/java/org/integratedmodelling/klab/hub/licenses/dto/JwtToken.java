package org.integratedmodelling.klab.hub.licenses.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.auth.Hub;
import org.integratedmodelling.klab.auth.Role;
import org.integratedmodelling.klab.hub.groups.dto.MongoGroup;
import org.integratedmodelling.klab.hub.nodes.dtos.MongoNode;
import org.integratedmodelling.klab.hub.security.NetworkKeyManager;
import org.integratedmodelling.klab.hub.users.dto.ProfileResource;
import org.integratedmodelling.klab.rest.Group;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.lang.JoseException;


public class JwtToken {
	
	private static final String JWT_CLAIM_KEY_PERMISSIONS = "perms";
	private static final String ENGINE_AUDIENCE = "engine";
	private static final String SERVICE_AUDIENCE = "engine";
	private static final int EXPIRATION_DAYS = 10;
	private static final String JWT_CLAIM_KEY_ROLES = "roles";
	

	public String createEngineJwtToken(ProfileResource profile) {
		JwtClaims claims = new JwtClaims();
		Hub hub = Authentication.INSTANCE.getAuthenticatedIdentity(Hub.class);
		claims.setIssuer(hub.getName());
		claims.setSubject(profile.getUsername());
		claims.setAudience(ENGINE_AUDIENCE);
		claims.setIssuedAtToNow();
		claims.setExpirationTimeMinutesInTheFuture(60 * 24 * EXPIRATION_DAYS);
		claims.setGeneratedJwtId();
		
		List<String> roleStrings = new ArrayList<>();
		
		for (Role role : profile.getRoles()) {
			roleStrings.add(role.toString());
		}
		
		claims.setStringListClaim(JWT_CLAIM_KEY_ROLES, roleStrings);
		claims.setStringListClaim(JWT_CLAIM_KEY_PERMISSIONS, profile.getGroupsIds());
		JsonWebSignature jws = new JsonWebSignature();
		jws.setPayload(claims.toJson());
		jws.setKey(NetworkKeyManager.INSTANCE.getPrivateKey());
		jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);
		
		String token;
		try {
			token = jws.getCompactSerialization();
		} catch (JoseException e) {
			token = null;
			Logging.INSTANCE
					.error(String.format("Failed to generate JWT token string for user '%s': ", profile.getUsername()), e);
		}
		return token;
	}
	
	public String createServiceJwtToken(INodeIdentity service, Set<Group> groups) {
	    if (service == null || service.getType().equals(IIdentity.Type.NODE) || service.getType().equals(IIdentity.Type.LEGACY_NODE)) {
	        return null;
	    }
        JwtClaims claims = new JwtClaims();
        Hub hub = Authentication.INSTANCE.getAuthenticatedIdentity(Hub.class);
        claims.setIssuer(hub.getName());
        claims.setSubject(service.getId());
        claims.setAudience(SERVICE_AUDIENCE);
        claims.setIssuedAtToNow();
        claims.setExpirationTimeMinutesInTheFuture(60 * 24 * EXPIRATION_DAYS);
        claims.setGeneratedJwtId();
        
        claims.setStringListClaim(JWT_CLAIM_KEY_ROLES, Role.SERVICE);
        List<String> groupsIds = new ArrayList<>();
        for(Group grp : groups) {
            if (grp != null) {
                groupsIds.add(grp.getId());
            }
        }
        claims.setStringListClaim(JWT_CLAIM_KEY_PERMISSIONS, groupsIds);
        JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(claims.toJson());
        jws.setKey(NetworkKeyManager.INSTANCE.getPrivateKey());
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);
        
        String token;
        try {
            token = jws.getCompactSerialization();
        } catch (JoseException e) {
            token = null;
            Logging.INSTANCE
                    .error(String.format("Failed to generate JWT token string for service '%s': ", service.getId()), e);
        }
        return token;
    }
}
