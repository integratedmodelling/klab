package org.integratedmodelling.klab.test.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.integratedmodelling.klab.hub.Hub;
import org.junit.Test;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ServiceIntegration {

    @Test
    public void runTest() throws Exception {

    	RestTemplate restTemplate = new RestTemplate();
    	
    	SpringApplicationBuilder uws = new SpringApplicationBuilder(Modeler.class)
                .properties("server.port=8081",
                        "server.contextPath=/UserService",
                        "SOA.ControllerFactory.enforceProxyCreation=true");
        uws.run();
        SpringApplicationBuilder pws = new SpringApplicationBuilder(Hub.class)
                .properties("server.port=8082",
                        "server.contextPath=/ProjectService",
                        "SOA.ControllerFactory.enforceProxyCreation=true");
        pws.run();

        String url = "http://localhost:8081/UserService/users";
//        ResponseEntity<SimplePage<UserDTO>> response = restTemplate.exchange(
//                url,
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<SimplePage<UserDTO>>() {
//                });
//        assertNotNull(response);
//        List<UserDTO> users = response.getBody().getContent();
//        assertEquals(10, users.size());
//        assertNotNull(users.get(0).getProjects());
//        assertEquals(1, users.get(0).getProjects().size());

    }
	
}
