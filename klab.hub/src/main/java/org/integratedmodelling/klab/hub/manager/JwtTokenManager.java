package org.integratedmodelling.klab.hub.manager;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.hub.authentication.HubAuthenticationManager;
import org.integratedmodelling.klab.hub.models.ProfileResource;
import org.integratedmodelling.klab.hub.security.NetworkKeyManager;
import org.integratedmodelling.klab.rest.Group;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenManager {
	
	private static final String JWT_CLAIM_KEY_PERMISSIONS = "perms";
	private static final String ENGINE_AUDIENCE = "engine";
	private static final int EXPIRATION_DAYS = 10;
	
	@Autowired
	private HubAuthenticationManager hubAuthenticationManager;
	
	@Autowired
	private KlabUserManager klabUserManager;

	public String createEngineJwtToken(String username) {
		ProfileResource profile = klabUserManager.getUserProfile(username);
		JwtClaims claims = new JwtClaims();
		claims.setIssuer(hubAuthenticationManager.getHubReference().getId());
		claims.setSubject(profile.getUsername());
		claims.setAudience(ENGINE_AUDIENCE);
		claims.setIssuedAtToNow();
		claims.setExpirationTimeMinutesInTheFuture(60 * 24 * EXPIRATION_DAYS);
		claims.setGeneratedJwtId();
		
		List<String> roleStrings = new ArrayList<>();
		for (Group role : profile.getGroupsList()) {
			roleStrings.add(role.getId());
		}
		
		JsonWebSignature jws = new JsonWebSignature();
		jws.setPayload(claims.toJson());
		jws.setKey(NetworkKeyManager.INSTANCE.getPrivateKey());
		jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);
		claims.setStringListClaim(JWT_CLAIM_KEY_PERMISSIONS, roleStrings);
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
