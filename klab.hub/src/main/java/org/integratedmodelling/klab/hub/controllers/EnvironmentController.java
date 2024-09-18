package org.integratedmodelling.klab.hub.controllers;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class EnvironmentController {

    private static final String ENV_PREFIX = "KHUB_";

    @GetMapping(value = "/ui/environments")
    public void getTestData(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/javascript;utf-8");

        Map<String, String> environmentVariables = System.getenv();

        Map<String, String> kHubEnvironmentVariables = environmentVariables.entrySet().stream()
                .filter((v) -> v.getKey().startsWith(ENV_PREFIX))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonValue = objectMapper.writeValueAsString(kHubEnvironmentVariables);

        response.getWriter().append("var __ENV__= " + jsonValue + ";");
    }
}
