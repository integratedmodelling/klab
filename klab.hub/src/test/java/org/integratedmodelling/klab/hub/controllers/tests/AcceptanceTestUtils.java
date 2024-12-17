package org.integratedmodelling.klab.hub.controllers.tests;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.rest.UserAuthenticationRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import net.minidev.json.JSONObject;

public class AcceptanceTestUtils {

    public static String getSessionTokenForDefaultAdministrator(int port) throws URISyntaxException {
        final String baseUrl = "http://localhost:"+ port + "/hub" + API.HUB.AUTHENTICATE_USER;
        URI uri = new URI(baseUrl);
        HttpHeaders headers = new HttpHeaders();
        UserAuthenticationRequest auth= new UserAuthenticationRequest();
        auth.setPassword("password");
        auth.setUsername("system");
        HttpEntity<?> request = new HttpEntity<>(auth, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<JSONObject> result = restTemplate.postForEntity(uri, request, JSONObject.class);
        JSONObject body = result.getBody();
        return ((Map<?,?>) body.get("Authentication")).get("tokenString").toString();
    }

}
