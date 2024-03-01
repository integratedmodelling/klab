package org.integratedmodelling.klab.hub.controllers.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URISyntaxException;

import org.integratedmodelling.klab.hub.api.Agreement;
import org.integratedmodelling.klab.hub.repository.AgreementRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@ActiveProfiles(profiles = "production")
@TestInstance(Lifecycle.PER_CLASS)
public class AgreementControllerTest {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    AgreementRepository agreementRepository;

    private RestTemplate restTemplate;
    private String url;
    private String token;
    private HttpHeaders headers;

    private String agreementId = "mockId";

    @BeforeAll
    public void beforeAll() throws URISyntaxException {
        token = AcceptanceTestUtils.getSessionTokenForDefaultAdministrator(randomServerPort);
        
        Agreement agreement = new Agreement();
        agreement.setId(agreementId);

        agreementRepository.save(agreement);
    }

    @BeforeEach
    void beforeEach() {
        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        headers.add("Authentication", token);
    }

    @Test
    @DisplayName("Get an agreement by Id")
    public void getAllAgreements_isOK() {
        url = "http://localhost:" + randomServerPort + "/hub/api/v2/agreements?id=" + agreementId;
        HttpEntity<String> httpEntity = new HttpEntity<>("", headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    
    @Test
    @DisplayName("Fail getting agreements without providing an id parameter")
    public void getAllAgreements_failNoIdParameter() {
        url = "http://localhost:" + randomServerPort + "/hub/api/v2/agreements";
        HttpEntity<String> httpEntity = new HttpEntity<>("", headers);

        Assertions.assertThrows(HttpServerErrorException.InternalServerError.class, () -> {
            restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
        });
    }

}
