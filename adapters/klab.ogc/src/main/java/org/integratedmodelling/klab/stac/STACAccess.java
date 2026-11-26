package org.integratedmodelling.klab.stac;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Map;

import org.hortonmachine.gears.io.stac.auth.HMHttpAuthentication;
import org.hortonmachine.gears.io.stac.auth.HMS3Authentication;
import org.hortonmachine.gears.io.stac.auth.HMS3Location;
import org.hortonmachine.gears.io.stac.auth.HMStacAccess;
import org.hortonmachine.gears.io.stac.auth.HMStacResponse;
import org.hortonmachine.gears.io.stac.auth.IHMStacAuthentication;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.api.auth.IUserIdentity;
import org.integratedmodelling.klab.exceptions.KlabResourceAccessException;
import org.integratedmodelling.klab.rest.ExternalAuthenticationCredentials;
import org.integratedmodelling.klab.rest.Group;

import klab.commons.customProperties.auth.AuthType;
import klab.commons.customProperties.auth.AwsAuth;
import klab.commons.customProperties.auth.BasicAuth;
import klab.commons.customProperties.auth.BearerAuth;
import klab.commons.customProperties.auth.HeaderAuth;
import klab.commons.customProperties.auth.QueryParamAuth;
import klab.commons.customProperties.auth.ServiceAuthResolver;
import kong.unirest.HttpRequestWithBody;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;

/**
 * The access to the STAC catalogs and their assets, with the authentications of the groups of the user
 * (the <code>URL_AUTH_KEYS</code> custom property, see {@link ServiceAuthResolver}):
 *
 * <ul>
 * <li><code>aws</code>: signed S3 requests on the bucket of the key (<code>s3://bucket</code> or its https url),
 * or on the S3 compatible service of its <code>endpoint</code>; without access key, the region of the public
 * buckets;</li>
 * <li><code>basic</code>, <code>bearer</code>, <code>header</code>, <code>query</code>: the credentials sent to the
 * server of the key (http(s) only).</li>
 * </ul>
 *
 * The s3:// addresses no authentication applies to are read as objects of public buckets.
 */
public class STACAccess {

    /**
     * The region used for the legacy credentials of the S3 compatible services.
     */
    private static final String LEGACY_S3_REGION = "us-east-1";

    private STACAccess() {
    }

    /**
     * @return the groups of the user of the engine, empty if not authenticated.
     */
    public static Collection<Group> getUserGroups() {
        IUserIdentity user = Authentication.INSTANCE.getAuthenticatedIdentity(IUserIdentity.class);
        return user != null && user.getGroups() != null ? user.getGroups() : java.util.Collections.emptySet();
    }

    /**
     * @return the access with the authentications of the user of the engine.
     */
    public static HMStacAccess get() {
        return build(getUserGroups());
    }

    /**
     * The access of a resource: the authentications of the user and, after them, the legacy credentials of the
     * S3 compatible service of the <code>s3EndpointUrl</code> parameter.
     *
     * @param parameters the parameters of the resource.
     * @return the access.
     */
    public static HMStacAccess forResource(IParameters<String> parameters) {
        HMStacAccess access = get();
        String endpoint = parameters != null ? parameters.get("s3EndpointUrl", String.class) : null;
        if (endpoint != null && endpoint.startsWith("http")) {
            ExternalAuthenticationCredentials credentials = Authentication.INSTANCE.getCredentials(endpoint);
            if (credentials != null && credentials.getCredentials() != null && credentials.getCredentials().size() >= 2) {
                access.addAuthentication(new HMS3Authentication(credentials.getCredentials().get(0),
                        credentials.getCredentials().get(1), null, LEGACY_S3_REGION).setEndpoint(endpoint));
            }
        }
        return access;
    }

    /**
     * Build the access of some groups.
     *
     * @param groups the groups.
     * @return the access, with the authentications of the most specific keys first.
     */
    public static HMStacAccess build(Collection<? extends Group> groups) {
        HMStacAccess access = new HMStacAccess();
        for (Map.Entry<String, AuthType> entry : ServiceAuthResolver.entries(groups).entrySet()) {
            try {
                IHMStacAuthentication authentication = toAuthentication(entry.getKey(), entry.getValue(), access);
                if (authentication != null) {
                    access.addAuthentication(authentication);
                }
            } catch (Exception e) {
                // no secrets in the message
                Logging.INSTANCE.warn("The authentication for " + entry.getKey() + " cannot be used: " + e.getMessage());
            }
        }
        return access;
    }

    /**
     * Convert an entry of the custom properties to an authentication.
     *
     * @return the authentication or null if the entry only configures the access (as the region of the public
     *         buckets) or doesn't apply to STAC.
     */
    static IHMStacAuthentication toAuthentication(String key, AuthType auth, HMStacAccess access) {
        boolean http = key.startsWith("http://") || key.startsWith("https://");
        if (auth instanceof AwsAuth) {
            return toS3Authentication(key, (AwsAuth) auth, access);
        }
        if (!http) {
            Logging.INSTANCE.warn("The authentication of type " + auth.getType() + " for " + key
                    + " is ignored: it applies only to http(s) addresses.");
            return null;
        }
        if (auth instanceof BasicAuth) {
            BasicAuth basic = (BasicAuth) auth;
            return HMHttpAuthentication.basic(key, basic.getUsername(), basic.getPasswordSecret());
        }
        if (auth instanceof BearerAuth) {
            return HMHttpAuthentication.bearer(key, ((BearerAuth) auth).getToken());
        }
        if (auth instanceof HeaderAuth) {
            HeaderAuth header = (HeaderAuth) auth;
            return HMHttpAuthentication.header(key, header.getHeaderName(), header.getValueSecret());
        }
        if (auth instanceof QueryParamAuth) {
            QueryParamAuth query = (QueryParamAuth) auth;
            return HMHttpAuthentication.queryParameter(key, query.getParameterName(), query.getValueSecret());
        }
        return null;
    }

    private static IHMStacAuthentication toS3Authentication(String key, AwsAuth aws, HMStacAccess access) {
        String region = blankToNull(aws.getRegion());
        String endpoint = blankToNull(aws.getEndpoint());
        String bucket = null;
        if (key.startsWith("s3://")) {
            String path = key.substring(5);
            int slash = path.indexOf('/');
            bucket = slash >= 0 ? path.substring(0, slash) : path;
        } else {
            HMS3Location location = HMS3Location.parse(key.endsWith("/") ? key : key + "/");
            if (location != null) {
                // an AWS url of the bucket
                bucket = location.getBucket();
                if (region == null) {
                    region = location.getRegion();
                }
            } else if (key.startsWith("http://") || key.startsWith("https://")) {
                // the url of an S3 compatible service, with or without the bucket in its path
                URI uri = URI.create(key);
                if (endpoint == null) {
                    endpoint = uri.getScheme() + "://" + uri.getRawAuthority();
                }
                String path = uri.getPath() == null ? "" : uri.getPath().replaceAll("^/+", "");
                if (!path.isEmpty()) {
                    bucket = path.split("/")[0];
                }
            }
        }
        if (aws.isAnonymous()) {
            // a public bucket: only its region is of use
            if (region != null && access.getDefaultS3Region() == null) {
                access.setDefaultS3Region(region);
            }
            return null;
        }
        HMS3Authentication authentication = new HMS3Authentication(aws.getAccessKeyId(), aws.getSecretAccessKeySecret(),
                blankToNull(aws.getSessionTokenSecret()), region).setEndpoint(endpoint);
        if (bucket != null && !bucket.isEmpty()) {
            authentication.forBuckets(bucket);
        }
        return authentication;
    }

    private static String blankToNull(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }

    /**
     * @param url an address, also s3://.
     * @return the address usable by http clients, as the https one of an s3 object.
     */
    public static String toHttpUrl(String url) {
        try {
            return get().toHttpUrl(url);
        } catch (IllegalArgumentException e) {
            throw new KlabResourceAccessException("Cannot access " + url + ": " + e.getMessage());
        }
    }

    /**
     * Read a document, with authentication if needed.
     *
     * @param access the access.
     * @param url the address, also s3://.
     * @return the content.
     */
    public static String readString(HMStacAccess access, String url) {
        try (HMStacResponse response = access.get(url)) {
            return new String(response.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException | RuntimeException e) {
            throw new KlabResourceAccessException("Cannot read " + url + ": " + e.getMessage());
        }
    }

    /**
     * Read a json document, with authentication if needed.
     *
     * @param url the address, also s3://.
     * @return the json object.
     */
    public static JSONObject getJson(String url) {
        return new JSONObject(readString(get(), url));
    }

    /**
     * POST a json document, as the STAC searches, with the authentication of the address if needed.
     *
     * @param url the address.
     * @param payload the json to send.
     * @return the response.
     */
    public static HttpResponse<JsonNode> postJson(String url, JSONObject payload) {
        HMStacAccess access = get();
        IHMStacAuthentication authentication = access.getAuthentication(url);
        String httpUrl = access.toHttpUrl(url);
        HttpRequestWithBody request = Unirest.post(httpUrl).header("Content-Type", "application/json");
        if (authentication != null) {
            for (Map.Entry<String, String> header : authentication.getHeaders(url).entrySet()) {
                request = request.header(header.getKey(), header.getValue());
            }
        }
        return request.body(payload).asJson();
    }
}
