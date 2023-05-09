package vito.test;

import java.security.NoSuchAlgorithmException;
import java.util.Comparator;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;

/**
 * This class is an exploratory test made to understand how we can connect to the VITO endpoint via OpenID
 *
 */
public class OpenIDVitoImplementationTest {

    private static String ISSUER_URI ="https://aai.egi.eu/auth/realms/egi";
    private static String PROVIDER_CONFIGURATION = "/.well-known/openid-configuration";
    private static String OIDC_CODE = "https://aai.egi.eu/device?user_code=";
    private static String PROVIDER_TITLE = "EGI Check-in";
    private static String OIDC_CREDENTIALS_PATH = "/credentials/oidc";

    @Test
    /*
     * A test implementation of the steps needed to access to the OpenEO-VitoPlatform
     */
    public void openEODance() throws NoSuchAlgorithmException {
        String maxUrl = getEndpointOfMaxVersion();
        JSONObject provider = getOidcCredentialEndpoint(maxUrl);
        
        String providerURI = provider.getAsString("issuer");
        String clientId = provider.getAsString("default_clients.id"); // TODO fixme
        clientId = "vito-default-client";
        String scopes = provider.getAsString("scopes");; // TODO fixme
        scopes = "eduperson_entitlement+eduperson_scoped_affiliation+email+offline_access+openid";

        String deviceAuthEndpoint = getProviderCredentialsDeviceFlow(providerURI);
        postDeviceAuthEndpoint(deviceAuthEndpoint, clientId, scopes);
    }

    private String getEndpointOfMaxVersion() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity< ? > request = new HttpEntity<>(null, headers);
        ResponseEntity<String> result = restTemplate.exchange(VITO_BASE_URI + WELLKNOWN_OPENEO_PATH, HttpMethod.GET, request, String.class);

        if (result.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException();
        }

        JSONObject jsonObject = (JSONObject) JSONValue.parse(result.getBody());
        JSONArray versionsArray = (JSONArray) jsonObject.get("versions");

        Optional<JSONObject> maxVersionObject = versionsArray.stream()
                .map(obj -> (JSONObject) obj)
                .filter(o -> (boolean)o.get("production"))
                .max(Comparator.comparing(version -> (String) version.get("api_version")));

        if (maxVersionObject.isEmpty()) {
            throw new RuntimeException();
        }

        JSONObject maxVersion = maxVersionObject.get();
        return maxVersion.getAsString("url");
    }

    public JSONObject getOidcCredentialEndpoint(String urlEndpoint) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity< ? > request = new HttpEntity<>(null, headers);
        ResponseEntity<String> result = restTemplate.exchange(urlEndpoint + OIDC_CREDENTIALS_PATH, HttpMethod.GET, request, String.class);
        
        JSONObject jsonObject = (JSONObject) JSONValue.parse(result.getBody());
        JSONArray providersArray = (JSONArray) jsonObject.get("providers");

        JSONObject provider = providersArray.stream()
                .map(obj -> (JSONObject) obj)
                .filter(p -> p.get("title").equals(PROVIDER_TITLE))
                .findAny().orElse(null);

        return provider;
    }

    private String getProviderCredentialsDeviceFlow(String providerURI) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity< ? > request = new HttpEntity<>(null, headers);
        ResponseEntity<String> result = restTemplate.exchange(providerURI + WELLKNOWN_PROVIDER_ID, HttpMethod.GET, request, String.class);

        JSONObject jsonObject = (JSONObject) JSONValue.parse(result.getBody());
        String deviceAuthEndpoint =  jsonObject.getAsString("device_authorization_endpoint");

        return deviceAuthEndpoint;
    }

    private static String VITO_BASE_URI = "https://openeo.vito.be/";
    private static String WELLKNOWN_OPENEO_PATH = ".well-known/openeo";
    private static String WELLKNOWN_PROVIDER_ID = ".well-known/openid-configuration";

    private void postDeviceAuthEndpoint(String deviceAuthEndpoint, String clientId, String scopes) throws NoSuchAlgorithmException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        
        // Generate code challenge
        String codeVerifier = OAuthUtils.generateCodeVerifier();
        String codeChallenge = OAuthUtils.generateCodeChallengeS256(codeVerifier);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("client_id", clientId);
        requestBody.add("scope", scopes);
        requestBody.add("code_challenge", codeChallenge);
        requestBody.add("code_challenge_method", "S256");

        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity< ? > request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> result = restTemplate.exchange(deviceAuthEndpoint, HttpMethod.POST, request, String.class);

        JSONObject jsonObject = (JSONObject) JSONValue.parse(result.getBody());
        String verificationURIComplete = jsonObject.getAsString("verification_uri_complete");
        System.out.println(verificationURIComplete);
    }
}
