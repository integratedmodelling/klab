package org.integratedmodelling.klab.hub.stats.services;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.klab.Network;
import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.integratedmodelling.klab.rest.StatsNodeRequest;

import com.fasterxml.jackson.core.JsonProcessingException;


@Service
public class StatsNodeService {

    public static final String STATS_SERVICE_ADAPTER_ID = "stats";
    
    public ResponseEntity<?> getURL(StatsNodeRequest request) throws JsonProcessingException{
        
        RestTemplate restTemplate = new RestTemplate();
        INodeIdentity statsNode;

        Collection<INodeIdentity> nodes = Network.INSTANCE.getNodesWithAdapter(STATS_SERVICE_ADAPTER_ID);
        for (INodeIdentity node : Network.INSTANCE.getNodesWithAdapter(STATS_SERVICE_ADAPTER_ID)) {
            // TODO there should be just one, or we should be able to pick the one in our
            // federated hub. See what to do if the field isn't null.
            statsNode = node;
            break;
        }
        if (!nodes.isEmpty()) {
            statsNode = nodes.iterator().next();
        } else {
            return null;
        }
        String url = statsNode.getUrls().iterator().next();
        //INodeIdentity node = getStatisticsServer();

        
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("query_type", "{query_type}")
                .queryParam("top", "{top}")
                .queryParam("outcome", "{outcome}")
                .queryParam("from", "{from}")
                .queryParam("to", "{to}")
                .encode()
                .toUriString();
        
        Map<String, Object> params = new HashMap<>();
        params.put("query_type", request.getQueryType());
        params.put("top", request.getTop());
        params.put("outcome", request.getOutcome());
        params.put("from", request.getFrom());
        params.put("to", request.getTo());
        
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
