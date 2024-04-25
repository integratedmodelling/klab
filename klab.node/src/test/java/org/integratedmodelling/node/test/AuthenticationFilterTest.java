package org.integratedmodelling.node.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.klab.node.auth.EngineAuthorization;
import org.integratedmodelling.klab.node.auth.NodeAuthenticationManager;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.NumericDate;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AuthenticationFilterTest {

	private static RsaJsonWebKey rsaJsonWebKey;

	@BeforeAll
	static void setUp() throws Exception {
		rsaJsonWebKey = RsaJwkGenerator.generateJwk(2048);
		rsaJsonWebKey.setKeyId("k1");

		Map<String, JwtConsumer> jwksVerifiers = new HashMap<>();
		JwtConsumer jwtVerifier = new JwtConsumerBuilder().setSkipDefaultAudienceValidation()
				.setAllowedClockSkewInSeconds(30).setVerificationKey(rsaJsonWebKey.getPublicKey()).build();
		jwksVerifiers.put("im", jwtVerifier);

		JwtConsumer validator = new JwtConsumerBuilder().setSkipAllValidators().setDisableRequireSignature()
				.setSkipSignatureVerification().build();

		NodeAuthenticationManager.INSTANCE.setJwksVerifiers(jwksVerifiers);
		NodeAuthenticationManager.INSTANCE.setPreValidationExtractor(validator);
	}

	@Test
	public void expiredTokenMustReturnNullAsAuthorization() throws Exception {

		JwtClaims claims = new JwtClaims();

		claims.setIssuer("im");
		claims.setSubject("test.user");
		claims.setAudience("engine");
		claims.setIssuedAtToNow();
		claims.setExpirationTime(NumericDate.fromMilliseconds(System.currentTimeMillis() - (40 * 1000)));
		claims.setGeneratedJwtId();
		claims.setStringListClaim("roles", "[]");
		claims.setStringListClaim("perms", "[]");

		JsonWebSignature jws = new JsonWebSignature();
		jws.setPayload(claims.toJson());
		jws.setKey(rsaJsonWebKey.getPrivateKey());
		jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);

		String jwt = jws.getCompactSerialization();

		EngineAuthorization token = NodeAuthenticationManager.INSTANCE.validateJwt(jwt);

		assertEquals(null, token);

	}
}
