package org.integratedmodelling.klab.node.auth;

import java.lang.reflect.Constructor;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.api.auth.ICertificate;
import org.integratedmodelling.klab.api.auth.IPartnerIdentity;
import org.integratedmodelling.klab.api.node.INodeStartupOptions;
import org.integratedmodelling.klab.auth.KlabCertificate;
import org.integratedmodelling.klab.communication.client.Client;
import org.integratedmodelling.klab.exceptions.KlabAuthorizationException;
import org.integratedmodelling.klab.node.utils.DateTimeUtil;
import org.integratedmodelling.klab.rest.Group;
import org.integratedmodelling.klab.rest.NodeAuthenticationRequest;
import org.integratedmodelling.klab.rest.NodeAuthenticationResponse;
import org.jose4j.jwk.HttpsJwks;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.NumericDate;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.jwt.consumer.JwtContext;

public enum NodeAuth {

	INSTANCE;

	private static final String TOKEN_CLASS_PACKAGE = "org.integratedmodelling.node.resource.token";
	private static final int ALLOWED_CLOCK_SKEW_MS = 30000;
	private static final String DEFAULT_TOKEN_CLASS = AuthenticationToken.class.getSimpleName();
	private static final long JWKS_UPDATE_INTERVAL_MS = 10 * 60 * 1000; // every 10 minutes
	private static final String JWT_CLAIM_KEY_PERMISSIONS = "perms";
	private static final String JWT_CLAIM_TOKEN_TYPE = "cls";

	PublicKey publicKey;
	Set<Group> groups = new HashSet<>();

	IPartnerIdentity rootIdentity;
	// if this is set, use instead of whatever is in the certificate
	String authenticatingHub;
	Client client = Client.create();
	private Map<String, HttpsJwks> jwksUpdaters;
	private Map<String, JwtConsumer> jwksVerifiers;
	private JwtConsumer preValidationExtractor;
	private String nodeName;

	private NodeAuth() {

		preValidationExtractor = new JwtConsumerBuilder().setSkipAllValidators().setDisableRequireSignature()
				.setSkipSignatureVerification().build();

		jwksUpdaters = new HashMap<>();
		jwksVerifiers = new HashMap<>();
		// for (Entry<String, String> entry :
		// webSecurityConfig.getJwksEndpointsByIssuer().entrySet()) {
		// HttpsJwks jwks = buildJwksClient(entry.getValue());
		// HttpsJwksVerificationKeyResolver jwksKeyResolver = new
		// HttpsJwksVerificationKeyResolver(jwks);
		// JwtConsumer jwtVerifier = new
		// JwtConsumerBuilder().setSkipDefaultAudienceValidation() // audience == Client
		// ID, which will be checked against the policy later.
		// .setAllowedClockSkewInSeconds(ALLOWED_CLOCK_SKEW_MS / 1000)
		// .setVerificationKeyResolver(jwksKeyResolver).build();
		// jwksUpdaters.put(entry.getKey(), jwks);
		// jwksVerifiers.put(entry.getKey(), jwtVerifier);
		// }
	}

	public void setAuthenticatingHub(String url) {
		this.authenticatingHub = url;
	}

	public IPartnerIdentity getRootIdentity() {
		return rootIdentity;
	}

	public IPartnerIdentity authenticate(ICertificate certificate, INodeStartupOptions options) {

		String serverHub = authenticatingHub;
		if (serverHub == null) {
			serverHub = certificate.getProperty(KlabCertificate.KEY_SERVER);
		}

		if (serverHub == null) {
			throw new KlabAuthorizationException("a node cannot be started without a valid authenticating hub");
		}

		this.nodeName = options.getNodeName() == null ? certificate.getProperty(KlabCertificate.KEY_CERTIFICATE)
				: options.getNodeName();
		
		NodeAuthenticationRequest request = new NodeAuthenticationRequest();
		
		request.setCertificate(certificate.getProperty(KlabCertificate.KEY_CERTIFICATE));
		request.setNodeName(nodeName);
		request.setNodeKey(KlabCertificate.KEY_SIGNATURE);
		request.setLevel(certificate.getLevel());

		/*
		 * response contains the Base64-encoded public key for the JWT tokens.
		 */
		NodeAuthenticationResponse response = client.authenticateNode(serverHub, request);

		try {
			byte publicKeyData[] = Base64.getDecoder().decode(response.getPublicKey());
			X509EncodedKeySpec spec = new X509EncodedKeySpec(publicKeyData);
			KeyFactory kf = KeyFactory.getInstance("RSA");
			this.publicKey = kf.generatePublic(spec);
		} catch (Exception e) {
			throw new KlabAuthorizationException("invalid public key sent by hub");
		}

		this.groups.addAll(response.getGroups());

		return rootIdentity;
	}

	public String getNodeName() {
		return nodeName;
	}
	
	public PublicKey getPublicKey() {
		return publicKey;
	}

	/**
	 * Given a JWT token that has previously been generated by a login event,
	 * validate its payload & signature. If it passes all checks and its payload can
	 * be extracted properly, then return an AuthenticationToken representing it.
	 */
	public AuthenticationToken validateJwt(String token) {
		AuthenticationToken result = new AuthenticationToken("UNKNOWN", "UNKNOWN");
		try {
			// first extract the partnerId so that we know which public key to use for
			// validating the signature
			JwtContext jwtContext = preValidationExtractor.process(token);
			String partnerId = jwtContext.getJwtClaims().getIssuer().trim();
			JwtConsumer jwtVerifier = jwksVerifiers.get(partnerId);

			if (jwtVerifier == null) {
				String msg = String.format("Couldn't find JWT verifier for partnerId %s. I only know about %s.",
						partnerId, jwksVerifiers.keySet().toString());
				// Exception e = new JwksNotFoundException(msg);
				// Logging.INSTANCE.error(msg, e);
				// throw e;
			}

			JwtClaims claims = jwtVerifier.processToClaims(token);
			String username = claims.getSubject();
			List<String> roleStrings = claims.getStringListClaimValue(JWT_CLAIM_KEY_PERMISSIONS);
			List<Role> roles = new ArrayList<>();
			for (String role : roleStrings) {
				roles.add(Role.valueOf(role));
			}

			// didn't throw an exception, so token is valid. Update the result and validate
			// claims.
			// TODO need to respect the 'cls' claim - AuthenticationToken by default, or map
			// to class
			String tokenClassName = claims.getStringClaimValue(JWT_CLAIM_TOKEN_TYPE);
			if (tokenClassName == null) {
				tokenClassName = DEFAULT_TOKEN_CLASS;
			}

			String fullClassName = String.format("%s.%s", TOKEN_CLASS_PACKAGE, tokenClassName);
			@SuppressWarnings("unchecked")
			Class<? extends AuthenticationToken> tokenClass = (Class<AuthenticationToken>) getClass().getClassLoader()
					.loadClass(fullClassName);
			if (tokenClass != null) {
				Constructor<? extends AuthenticationToken> constructor;
				// try {
				// constructor = tokenClass.getConstructor(String.class, String.class,
				// ROLE_COLLECTION_CLASS);
				// result = constructor.newInstance(coreApplicationConfig.getPartnerId(),
				// username, roles);
				// } catch (Exception e) {
				// try {
				// // probably a token type that doesn't accept roles in the constructor
				// (cronjob/engine/etc).
				// // re-try with just partner ID + username
				// constructor = tokenClass.getConstructor(String.class, String.class);
				// result = constructor.newInstance(coreApplicationConfig.getPartnerId(),
				// username);
				// } catch (Exception e2) {
				// // shouldn't ever get here, but if we do, wrap in a non-checked Exception
				// type
				// throw new TokenGenerationException("Unable to get token constructor method.",
				// e2);
				// }
				// }
			}

			// TODO should we filter by audience somehow? Maybe for "modeling" vs. "web"
			// operations?
			// Audience (aud) - The "aud" (audience) claim identifies the recipients that
			// the JWT is intended for.
			// Each principal intended to process the JWT must identify itself with a value
			// in the audience claim.
			// If the principal processing the claim does not identify itself with a value
			// in the aud claim
			// when this claim is present, then the JWT must be rejected.
			claims.getAudience();

			// Expiration time (exp) - The "exp" (expiration time) claim identifies the
			// expiration time
			// on or after which the JWT must not be accepted for processing.
			// The value should be in NumericDate[10][11] format.
			NumericDate expirationTime = claims.getExpirationTime();
			long now = System.currentTimeMillis();
			// if (expirationTime.isBefore(NumericDate.fromMilliseconds(now -
			// ALLOWED_CLOCK_SKEW_MS))) {
			// throw new JwtExpiredException(claims);
			// }

			long issuedAtUtcMs = claims.getIssuedAt().getValueInMillis();
			LocalDateTime issuedAt = DateTimeUtil.utcMsToUtcLocal(issuedAtUtcMs);
			result.setIssuedAt(issuedAt);

			// claims validate, so mark the result as authenticated
			result.setAuthenticated(true);

		} catch (MalformedClaimException e) {
			// TODO
			Logging.INSTANCE.error("WTF", e);
		} catch (InvalidJwtException e) {
			// TODO
			Logging.INSTANCE.error("WTF", e);
		} catch (Exception e) {
			// it was a JWT token, but some other exception happened.
			Logging.INSTANCE.error("WTF", e);
		}

		return result;
	}
}
