package org.integratedmodelling.klab.hub.controllers.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URISyntaxException;

import org.integratedmodelling.klab.hub.api.AgreementTemplate;
import org.integratedmodelling.klab.hub.enums.AgreementLevel;
import org.integratedmodelling.klab.hub.enums.AgreementType;
import org.integratedmodelling.klab.hub.repository.AgreementTemplateRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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
public class AgreementTemplateControllerTest {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    AgreementTemplateRepository agreementTemplateRepository;

    private RestTemplate restTemplate;
    private String url;
    private String token;
    private HttpHeaders headers;

    private AgreementTemplate createAgreementTemplate(AgreementType type, AgreementLevel level) {
        AgreementTemplate agreementTemplate = new AgreementTemplate();
        agreementTemplate.setAgreementType(type);
        agreementTemplate.setAgreementLevel(level);
        return agreementTemplate;
    }

    @BeforeAll
    public void beforeAll() throws URISyntaxException {
        token = AcceptanceTestUtils.getSessionTokenForDefaultAdministrator(randomServerPort);

        agreementTemplateRepository.save(createAgreementTemplate(AgreementType.USER, AgreementLevel.NON_PROFIT));
        agreementTemplateRepository.save(createAgreementTemplate(AgreementType.USER, AgreementLevel.PROFIT));
        agreementTemplateRepository.save(createAgreementTemplate(AgreementType.INSTITUTION, AgreementLevel.NON_PROFIT));
        agreementTemplateRepository.save(createAgreementTemplate(AgreementType.INSTITUTION, AgreementLevel.PROFIT));
    }

    @BeforeEach
    void beforeEach() {
        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        headers.add("Authentication", token);
    }

    @ParameterizedTest
    @ValueSource(strings = {"USER", "INSTITUTION"})
    @DisplayName("Get an agreement template with an existing agreement type")
    public void getAgreementTemplate_isOKWithExisitingAgreementType(String agreementType) {
        url = "http://localhost:" + randomServerPort + "/hub/api/v2/agreement-templates/type-level?agreementType=" + agreementType + "&agreementLevel=NON_PROFIT";
        HttpEntity<String> httpEntity = new HttpEntity<>("", headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @ParameterizedTest
    @ValueSource(strings = {"NON_PROFIT", "PROFIT"})
    @DisplayName("Get an agreement template with an existing agreement level")
    public void getAgreementTemplate_isOKWithExisitingAgreementLevel(String agreementLevel) {
        url = "http://localhost:" + randomServerPort + "/hub/api/v2/agreement-templates/type-level?agreementType=USER&agreementLevel=" + agreementLevel;
        HttpEntity<String> httpEntity = new HttpEntity<>("", headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @ParameterizedTest
    @ValueSource(strings = {"IMAGINARYTYPE", ""})
    @DisplayName("Fail getting an agreement template with non exisitng agreement type")
    public void getAgreementTemplate_failsWithNonExisitingAgreementType(String agreementType) {
        url = "http://localhost:" + randomServerPort + "/hub/api/v2/agreement-templates/type-level?agreementType=" + agreementType + "&agreementLevel=NON_PROFIT";
        HttpEntity<String> httpEntity = new HttpEntity<>("", headers);

        Assertions.assertThrows(HttpServerErrorException.InternalServerError.class, () -> {
            restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"IMAGINARYLEVEL", ""})
    @DisplayName("Get an agreement template with an existing agreement level")
    public void getAgreementTemplate_failsWithNonExisitingAgreementLevel(String agreementLevel) {
        url = "http://localhost:" + randomServerPort + "/hub/api/v2/agreement-templates/type-level?agreementType=USER&agreementLevel=" + agreementLevel;
        HttpEntity<String> httpEntity = new HttpEntity<>("", headers);

        Assertions.assertThrows(HttpServerErrorException.InternalServerError.class, () -> {
            restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
        });
    }

    @AfterAll
    void afterAll() {
        agreementTemplateRepository.deleteAll();
    }
}
