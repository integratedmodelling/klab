package org.integratedmodelling.klab.hub.stats.controllers;
//package org.integratedmodelling.klab.node.controllers;

import java.security.Principal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.auth.Node;
import org.integratedmodelling.klab.hub.network.NodeNetworkManager;
import org.integratedmodelling.klab.rest.NodeReference;
import org.integratedmodelling.klab.rest.StatsNodeRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;


@RestController
@CrossOrigin(origins = "*")
//@Secured(Role.USER)
public class StatsNodeController {
    
    public static final String STATS_SERVICE_ADAPTER_ID = "stats";
    
    private boolean nodeIsOnlineAndHasAdapter(INodeIdentity node, String adapterName) {
        return node.isOnline() && !node.getAdapters().isEmpty() && node.getAdapters().contains(adapterName);
    }

    
    @GetMapping(value = API.STATS.STATS_OUTPUT, produces = {"application/json"})
    @PreAuthorize("hasRole('ROLE_SYSTEM') or hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_USER')")
    public ResponseEntity<?> getOutput(@RequestHeader("Authentication") String token, Principal principal,@Valid StatsNodeRequest request)
                                    throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        Optional<INodeIdentity> statsNode  = NodeNetworkManager.INSTANCE.getNodes().stream()
                .filter(node -> nodeIsOnlineAndHasAdapter(node, STATS_SERVICE_ADAPTER_ID))
                .findFirst();

        // TODO there should be just one, or we should be able to pick the one in our
        // federated hub. See what to do if the field isn't null.

        if (statsNode.isEmpty()) {
            return null;
        }
        String url = ((Node)statsNode.get()).getUrls().iterator().next();
        
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.set("Authentication", token);
        HttpEntity<?> entity = new HttpEntity<>(headers);
       
        
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(url+"/"+API.STATS.STATS_OUTPUT)
                .queryParam("queryType", "{queryType}")
                .queryParam("top", "{top}")
                .queryParam("outcome", "{outcome}")
                .queryParam("from", "{from}")
                .queryParam("to", "{to}")
                .queryParam("resolutionTimeMin", "{resolutionTimeMin}")
                .queryParam("resolutionTimeMax", "{resolutionTimeMax}")
                .encode()
                .toUriString();
        
        Map<String, Object> params = new HashMap<>();
        params.put("queryType", request.getQueryType());
        params.put("top", request.getTop());
        params.put("outcome", request.getOutcome());
        params.put("from", request.getFrom());
        params.put("to", request.getTo());
        params.put("resolutionTimeMin", request.getResolutionTimeMin());
        params.put("resolutionTimeMax", request.getResolutionTimeMax());

        
        ResponseEntity<?> response = restTemplate.exchange(
                urlTemplate,
                HttpMethod.GET,
                entity,
                String.class,
                params
        );
        
        return response;
    }
}
