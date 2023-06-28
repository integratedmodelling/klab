package org.integratedmodelling.klab.hub.api;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.auth.Hub;
import org.integratedmodelling.klab.auth.Role;
import org.integratedmodelling.klab.hub.security.NetworkKeyManager;
import org.integratedmodelling.klab.rest.Group;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.lang.JoseException;


public class JwtToken {
	
	private static final String JWT_CLAIM_KEY_PERMISSIONS = "perms";
	private static final String ENGINE_AUDIENCE = "engine";
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
}
