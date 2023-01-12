package org.integratedmodelling.klab.node.auth;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.api.auth.ICertificate;
import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.api.auth.IPartnerIdentity;
import org.integratedmodelling.klab.api.node.INodeStartupOptions;
import org.integratedmodelling.klab.auth.KlabCertificate;
import org.integratedmodelling.klab.auth.KlabUser;
import org.integratedmodelling.klab.auth.Node;
import org.integratedmodelling.klab.auth.Partner;
import org.integratedmodelling.klab.auth.Role;
import org.integratedmodelling.klab.auth.UserIdentity;
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

/**
 * Authentication manager. Not a Spring component as it must exist before the
 * application starts.
 * 
 * @author Luke
 * @author Ferd
 */
public enum NodeAuthenticationManager {

	INSTANCE;

	private static final String TOKEN_CLASS_PACKAGE = "org.integratedmodelling.node.resource.token";
	private static final int ALLOWED_CLOCK_SKEW_MS = 30000;
	private static final String DEFAULT_TOKEN_CLASS = EngineAuthorization.class.getSimpleName();
	private static final long JWKS_UPDATE_INTERVAL_MS = 10 * 60 * 1000; // every 10 minutes
	private static final String JWT_CLAIM_KEY_PERMISSIONS = "perms";
	private static final String JWT_CLAIM_TOKEN_TYPE = "cls";
	private static final String ENGINE_AUDIENCE = "engine";
	private static final String JWT_CLAIM_KEY_ROLES = "roles";

	Map<String, Group> groups = new HashMap<>();
	IPartnerIdentity rootIdentity;
	// if this is set, use instead of whatever is in the certificate
	String authenticatingHub;
	Client client = Client.create();
	private Map<String, JwtConsumer> jwksVerifiers = new HashMap<>();
	private JwtConsumer preValidationExtractor;
	private String nodeName;
	private String hubName;
	private INodeIdentity node;
	long wtfErrors = 0;

	NodeAuthenticationManager() {
		Authentication.INSTANCE.setPrincipalTranslator((principal) -> {
			EngineAuthorization u = (EngineAuthorization) principal;

			UserIdentity ret = new KlabUser(u.getName(), node);
			for (Role role : u.getRoles()) {
				ret.getRoles().add(role.name());
			}
			for (Group group : u.getGroups()) {
				ret.getGroups().add(new Group(group.getId()));
			}
			return ret;
		});
	}

	protected HttpsJwks buildJwksClient(String url) {
		return new HttpsJwks(url);
	}

	public void setAuthenticatingHub(String url) {
		this.authenticatingHub = url;
	}

	public IPartnerIdentity getRootIdentity() {
		return rootIdentity;
	}

	/**
	 * Meant to be re-entrant eventually - TODO store the cert and the options so we
	 * can call again
	 * 
	 * @param certificate
	 * @param options
	 * @return the partner identity that owns this node.
	 */
	public IPartnerIdentity authenticate(ICertificate certificate, INodeStartupOptions options) {

		String serverHub = authenticatingHub;
		if (serverHub == null) {
			serverHub = certificate.getProperty(KlabCertificate.KEY_PARTNER_HUB);
		}

		if (serverHub == null) {
			throw new KlabAuthorizationException("a node cannot be started without a valid authenticating hub");
		}

		this.authenticatingHub = serverHub;
		this.nodeName = options.getNodeName() == null ? certificate.getProperty(KlabCertificate.KEY_NODENAME)
				: options.getNodeName();

		NodeAuthenticationRequest request = new NodeAuthenticationRequest();

		request.setCertificate(certificate.getProperty(KlabCertificate.KEY_CERTIFICATE));
		request.setName(nodeName);
		request.setKey(certificate.getProperty(KlabCertificate.KEY_SIGNATURE));
		request.setLevel(certificate.getLevel());
		request.setEmail(certificate.getProperty(KlabCertificate.KEY_PARTNER_EMAIL));

		/*
		 * response contains the groupset for validation and the Base64-encoded public
		 * key for the JWT tokens. We throw away the public key after building it; if
		 * the first token decryption fails and we have authenticated some time before,
		 * we can try re-authenticating once to update it before refusing authorization.
		 */
		PublicKey publicKey = null;
		NodeAuthenticationResponse response = client.authenticateNode(serverHub, request);
		this.hubName = response.getAuthenticatingHub();

		try {
			byte publicKeyData[] = Base64.getDecoder().decode(response.getPublicKey());
			X509EncodedKeySpec spec = new X509EncodedKeySpec(publicKeyData);
			KeyFactory kf = KeyFactory.getInstance("RSA");
			publicKey = kf.generatePublic(spec);
		} catch (Exception e) {
			throw new KlabAuthorizationException("invalid public key sent by hub");
		}

		for (Group group : response.getGroups()) {
			this.groups.put(group.getId(), group);
		}

		/*
		 * build a verifier for the token coming from any engine that has validated with
		 * the authenticating hub.
		 */
		preValidationExtractor = new JwtConsumerBuilder().setSkipAllValidators().setDisableRequireSignature()
				.setSkipSignatureVerification().build();

		JwtConsumer jwtVerifier = new JwtConsumerBuilder().setSkipDefaultAudienceValidation()
				.setAllowedClockSkewInSeconds(ALLOWED_CLOCK_SKEW_MS / 1000).setVerificationKey(publicKey).build();

		jwksVerifiers.put(response.getAuthenticatingHub(), jwtVerifier);

		/*
		 * setup the various identities: partner->node, we add the engine later.
		 */
		rootIdentity = new Partner(response.getUserData().getIdentity().getId());
		Authentication.INSTANCE.registerIdentity(rootIdentity);
		Node node = new Node(certificate.getProperty(ICertificate.KEY_NODENAME), rootIdentity);
		node.setOnline(true);
		Authentication.INSTANCE.registerIdentity(node);

		return rootIdentity;
	}

	/**
	 * Single node name as written in certificate, without the hub name prepended.
	 * 
	 * @return
	 */
	public String getNodeId() {
		return nodeName;
	}

	/**
	 * Fully qualified node path with hub name prepended.
	 * 
	 * @return
	 */
	public String getNodeName() {
		return hubName + "." + nodeName;
	}

	/**
	 * Given a JWT token that has previously been generated by a login event,
	 * validate its payload & signature. If it passes all checks and its payload can
	 * be extracted properly, then return an EngineAuthorization representing it.
	 */
	public EngineAuthorization validateJwt(String token) {

		EngineAuthorization result = null;

		try {
			// first extract the partnerId so that we know which public key to use for
			// validating the signature
			JwtContext jwtContext = preValidationExtractor.process(token);
			String hubId = jwtContext.getJwtClaims().getIssuer().trim();
			JwtConsumer jwtVerifier = jwksVerifiers.get(hubId);

			if (jwtVerifier == null) {
				String msg = String.format("Couldn't find JWT verifier for partnerId %s. I only know about %s.", hubId,
						jwksVerifiers.keySet().toString());
				// Exception e = new JwksNotFoundException(msg);
				// Logging.INSTANCE.error(msg, e);
				// throw e;
			}

			JwtClaims claims = jwtVerifier.processToClaims(token);
			String username = claims.getSubject();
			List<String> groupStrings = claims.getStringListClaimValue(JWT_CLAIM_KEY_PERMISSIONS);
			List<String> roleStrings = claims.getStringListClaimValue(JWT_CLAIM_KEY_ROLES);

			// didn't throw an exception, so token is valid. Update the result and validate
			// claims. This is an engine-only entry point so the role is obvious.
			result = new EngineAuthorization(hubId, username, Collections.unmodifiableList(filterRoles(roleStrings)));

			/*
			 * Audience (aud) - The "aud" (audience) claim identifies the recipients that
			 * the JWT is intended for. Each principal intended to process the JWT must
			 * identify itself with a value in the audience claim. If the principal
			 * processing the claim does not identify itself with a value in the aud claim
			 * // when this claim is present, then the JWT must be rejected.
			 */
			if (!claims.getAudience().contains(ENGINE_AUDIENCE)) {

			}

			/*
			 * Expiration time (exp) - The "exp" (expiration time) claim identifies the
			 * expiration time on or after which the JWT must not be accepted for
			 * processing. The value should be in NumericDate[10][11] format.
			 */
			NumericDate expirationTime = claims.getExpirationTime();
			long now = System.currentTimeMillis();
			if (expirationTime.isBefore(NumericDate.fromMilliseconds(now - ALLOWED_CLOCK_SKEW_MS))) {
				throw new KlabAuthorizationException("user " + username + " is using an expired authorization");
			}

			long issuedAtUtcMs = claims.getIssuedAt().getValueInMillis();
			LocalDateTime issuedAt = DateTimeUtil.utcMsToUtcLocal(issuedAtUtcMs);
			result.setIssuedAt(issuedAt);
			result.getGroups().addAll(filterGroups(groupStrings));
			result.setAuthenticated(true);

		} catch (MalformedClaimException | InvalidJwtException e) {
			// TODO see if we should reauthenticate and if so, try that before throwing an
			// authorization exception
			if ((wtfErrors % 100) == 0) {
				Logging.INSTANCE.error("WTF (" + wtfErrors + " errors)", e);
			}
			wtfErrors++;
		} catch (Exception e) {
			// it was a JWT token, but some other exception happened.
			if ((wtfErrors % 100) == 0) {
				Logging.INSTANCE.error("WTF (" + wtfErrors + " errors)", e);
			}
			wtfErrors++;
		}
		
		return result;
	}

	// As of now the node and hub have different roles. It maybe best to unify this.
	// I add the Role.ROLE_ENGINE because they hub does not give this to users. In
	// the future
	// we may need to create engines with no user, and as of now the hub would not
	// know how to
	// do that...
	private List<Role> filterRoles(List<String> roleStrings) {
		List<Role> ret = new ArrayList<>();
		for (String roleString : roleStrings) {
			for (Role r : Role.values()) {
				if (r.getAuthority().equals(roleString)) {
					ret.add(r);
				}
			}
		}
		ret.add(Role.ROLE_ENGINE);
		return ret;
	}

	private Set<Group> filterGroups(List<String> groupStrings) {
		Set<Group> ret = new HashSet<>();
		List<String> authenticated = new ArrayList<>();
		for (String groupId : groupStrings) {
			Group group = groups.get(groupId);
			if (group != null) {
				authenticated.add(groupId);
				ret.add(group);
			}
		}
		
		Logging.INSTANCE.info("Received groups " + groupStrings + "; authenticated " + authenticated);
		
		return ret;
	}
}
