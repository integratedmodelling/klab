package org.integratedmodelling.klab.hub.stats.controllers;
//package org.integratedmodelling.klab.node.controllers;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.Network;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.api.auth.IUserIdentity;
import org.integratedmodelling.klab.auth.Node;
import org.integratedmodelling.klab.hub.network.NodeNetworkManager;
import org.integratedmodelling.klab.hub.stats.services.StatsNodeService;
import org.integratedmodelling.klab.rest.NodeCapabilities;
import org.integratedmodelling.klab.rest.NodeReference;
import org.integratedmodelling.klab.rest.StatsNodeRequest;
import org.springframework.beans.factory.annotation.Autowired;
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
    
    @GetMapping(value = API.STATS.STATS_OUTPUT, produces = {"application/json"})
    @PreAuthorize("hasRole('ROLE_SYSTEM') or hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_USER')")
    public ResponseEntity<?> getOutput(@RequestHeader("Authentication") String token, Principal principal,@Valid StatsNodeRequest request)
                                    throws JsonProcessingException {
            //throws UnsupportedEncodingException {
        
        RestTemplate restTemplate = new RestTemplate();
        Node statsNode = null;

        Collection<NodeReference> nodes = NodeNetworkManager.INSTANCE.getNodeReferences();
        for (NodeReference node : nodes) {
            Node authNode = new Node(node, token);
            NodeCapabilities nc = authNode.getClient().get(API.CAPABILITIES, NodeCapabilities.class);
            //if (nc.getResourceAdapters().contains("stats")) {
            if (authNode.getName().equals("im.stats")) {
            // TODO there should be just one, or we should be able to pick the one in our
            // federated hub. See what to do if the field isn't null.
                statsNode = authNode;
                break;
            }
        }
        if (statsNode == null) {
            return null;
        }
        String url = statsNode.getUrls().iterator().next();
        //INodeIdentity node = getStatisticsServer();
        
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
