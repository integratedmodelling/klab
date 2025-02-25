package org.integratedmodelling.node.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.klab.node.NodeApplication;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(classes = NodeApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AuthenticationFilterTest {

	@Autowired
	private MockMvc mvc;

	private static RsaJsonWebKey rsaJsonWebKey;
	private static long oneHour = 60 * 60 * 1000;

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
	public void anonymousGetReturnsOk() throws Exception {
		mvc.perform(get("/")).andExpect(status().isOk());
	}

	@Test
	public void validTokenReturnsOk() throws Exception {
		long validTime = System.currentTimeMillis() + oneHour;
		mvc.perform(get("/").header("Authorization", getToken("valid.request", validTime))).andExpect(status().isOk());
	}

	@Test
	public void invalidTokenReturnsUnauthorized() throws Exception {
		long expiredTime = System.currentTimeMillis() - oneHour;
		mvc.perform(get("/").header("Authorization", getToken("expired.request", expiredTime)))
				.andExpect(status().isUnauthorized());
	}

	private String getToken(String subject, long expirationTimeInMillis) throws Exception {
		JwtClaims claims = new JwtClaims();

		claims.setIssuer("im");
		claims.setSubject(subject);
		claims.setAudience("engine");
		claims.setIssuedAtToNow();
		claims.setExpirationTime(NumericDate.fromMilliseconds(expirationTimeInMillis));
		claims.setGeneratedJwtId();
		claims.setStringListClaim("roles", "[]");
		claims.setStringListClaim("perms", "[]");

		JsonWebSignature jws = new JsonWebSignature();
		jws.setPayload(claims.toJson());
		jws.setKey(rsaJsonWebKey.getPrivateKey());
		jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);

		return jws.getCompactSerialization();
	}
}
