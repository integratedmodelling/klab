package org.integratedmodelling.klab.engine.rest.controllers.base;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.integratedmodelling.klab.engine.rest.api.EngineProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class EnvironmentController {

    @Autowired
    private EngineProperties engineProperties;

    private static final String APP_BASE_URL = "APP_BASE_URL";
    private static final String KEYCLOAK_URL = "KEYCLOAK_URL";

    @GetMapping(value = "/engine/environments")
    public void getEnvironmentVariables(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/javascript;utf-8");

        Map<String, String> kHubEnvironmentVariables = Map.ofEntries(Map.entry(APP_BASE_URL, engineProperties.env.getAppBaseUrl()),
                Map.entry(KEYCLOAK_URL, engineProperties.env.getKeycloakUrl()));

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonValue = objectMapper.writeValueAsString(kHubEnvironmentVariables);

        System.out.println(jsonValue);

        response.getWriter().append("var __ENV__= " + jsonValue + ";");
    }

}
