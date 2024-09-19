package org.integratedmodelling.klab.hub.controllers;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class EnvironmentController {

    @Autowired
    private Environment env;

    private static final String HUB_ENV = "hub_env";
    private static final String KHUB_APP_BASE_URL = "KHUB_APP_BASE_URL";
    private static final String KHUB_BASE_URL = "KHUB_BASE_URL";
    private static final String KHUB_ENVIRONMENT = "KHUB_ENVIRONMENT";
    private static final String KHUB_KEYCLOAK_URL = "KHUB_KEYCLOAK_URL";
    private static final String KHUB_STATIC_BASE_URL = "KHUB_STATIC_BASE_URL";

    @GetMapping(value = "/ui/environments")
    public void getTestData(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/javascript;utf-8");

        Map<String, String> kHubEnvironmentVariables = Map.ofEntries(
                Map.entry(KHUB_APP_BASE_URL, env.getProperty(HUB_ENV + KHUB_APP_BASE_URL)),
                Map.entry(KHUB_BASE_URL, env.getProperty(HUB_ENV + KHUB_BASE_URL)),
                Map.entry(KHUB_ENVIRONMENT, env.getProperty(HUB_ENV + KHUB_ENVIRONMENT)),
                Map.entry(KHUB_KEYCLOAK_URL, env.getProperty(HUB_ENV + KHUB_KEYCLOAK_URL)),
                Map.entry(KHUB_STATIC_BASE_URL, env.getProperty(HUB_ENV + KHUB_STATIC_BASE_URL)));

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonValue = objectMapper.writeValueAsString(kHubEnvironmentVariables);

        response.getWriter().append("var __ENV__= " + jsonValue + ";");
    }
}
