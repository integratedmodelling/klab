package org.integratedmodelling.klab.hub.controllers;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.integratedmodelling.klab.hub.api.HubProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class EnvironmentController {

    @Autowired
    private HubProperties hubProperties;

    private static final String APP_BASE_URL = "APP_BASE_URL";
    private static final String BASE_URL = "BASE_URL";
    private static final String ENVIRONMENT = "ENVIRONMENT";
    private static final String KEYCLOAK_URL = "KEYCLOAK_URL";
    private static final String STATIC_BASE_URL = "STATIC_BASE_URL";

    @GetMapping(value = "/ui/environments")
    public void getEnvironmentVariables(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/javascript;utf-8");

        Map<String, String> kHubEnvironmentVariables = Map.ofEntries(Map.entry(APP_BASE_URL, hubProperties.env.getAppBaseUrl()),
                Map.entry(BASE_URL, hubProperties.env.getBaseUrl()), Map.entry(ENVIRONMENT, hubProperties.env.getEnvironment()),
                Map.entry(KEYCLOAK_URL, hubProperties.env.getKeycloakUrl()),
                Map.entry(STATIC_BASE_URL, hubProperties.env.getStaticBaseUrl()));

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonValue = objectMapper.writeValueAsString(kHubEnvironmentVariables);

        response.getWriter().append("var __ENV__= " + jsonValue + ";");
    }

}
