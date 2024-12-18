package org.integratedmodelling.klab.engine.rest.controllers.base;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
    private static final String ACTIVE_PROFILE = "ACTIVE_PROFILE";

    private static final String ENGINE_REMOTE = "engine.remote";
    private static final String ENGINE_LOCAL = "engine.local";

    @GetMapping(value = "/engine/environments")
    public void getEnvironmentVariables(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/javascript;utf-8");

        List<String> activeProfiles = Pattern.compile(",").splitAsStream(System.getProperty("spring.profiles.active", "unknown"))
                .collect(Collectors.toList());

        String activeProfile = activeProfiles.contains(ENGINE_REMOTE) ? ENGINE_REMOTE : ENGINE_LOCAL;

        /* 
         * Get engine properties
         */
        Map<String, String> kHubEnvironmentVariables = new HashMap<>();

        if (activeProfile.equals(ENGINE_REMOTE)) {
            kHubEnvironmentVariables = Map.ofEntries(Map.entry(APP_BASE_URL, engineProperties.env.getAppBaseUrl()),
                    Map.entry(KEYCLOAK_URL, engineProperties.env.getKeycloakUrl()), Map.entry(ACTIVE_PROFILE, activeProfile));
        } else {
            kHubEnvironmentVariables = Map.ofEntries(Map.entry(ACTIVE_PROFILE, activeProfile));
        }

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonValue = objectMapper.writeValueAsString(kHubEnvironmentVariables);

        System.out.println(jsonValue);

        response.getWriter().append("var __ENV__= " + jsonValue + ";");
    }

}
