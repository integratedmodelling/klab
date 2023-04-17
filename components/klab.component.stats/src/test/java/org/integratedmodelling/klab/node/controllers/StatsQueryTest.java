package org.integratedmodelling.klab.node.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;

import java.net.URISyntaxException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
//import org.springframework.boot.test.web.server.LocalServerPort;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@ContextConfiguration
@ActiveProfiles(profiles = "development")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class StatsQueryTest {
    
    @LocalServerPort
    int randomServerPort;
    
    private RestTemplate restTemplate;
    private String url;
    private HttpHeaders headers;

    @BeforeEach
    void setUp() throws URISyntaxException {
        restTemplate = new RestTemplate();
    }
    
    @ParameterizedTest
    @WithAnonymousUser
    @ValueSource(strings = {"query_type=outcome_aggregate", "top=1232", "output=Error", "from=2023-03-12"})
    @DisplayName("Get different parameter combinations")   
    public void statsQueryTest(String parameter) {
            url = "http://localhost:" + randomServerPort + "/node/api/v2/stats/output?" + parameter;
            HttpEntity<String> httpEntity = new HttpEntity<>("", headers);
            
            ResponseEntity<?> output = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
            
            assertEquals(HttpStatus.OK ,output.getStatusCode());
    }
    
    @ParameterizedTest
    @ValueSource(strings = {"query_type=outcome_aggregate", "top=1232", "output=Error", "from=2023-03-12"})
    @DisplayName("Get different parameter combinations")   
    public void statsQueryMockTest(String parameter) {
            url = "http://localhost:" + randomServerPort + "/node/api/v2/stats/output?" + parameter;
            HttpEntity<String> httpEntity = new HttpEntity<>("", headers);
            
            ResponseEntity<?> output = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
            
            assertEquals(HttpStatus.OK ,output.getStatusCode());
    }

}
