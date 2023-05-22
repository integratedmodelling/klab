package org.integratedmodelling.klab.hub.nodes.controllers;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.hub.network.NodeNetworkManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import net.minidev.json.JSONObject;

@RestController
public class NodeCapabilitiesController {

    @GetMapping(value=API.HUB.NODE_ADAPTERS)
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_SYSTEM')")
    public ResponseEntity<?> getHubLogResponse() {
        JSONObject resp = new JSONObject();
        NodeNetworkManager.INSTANCE.getNodes().stream()
        .filter(node -> node.isOnline() && !node.getAdapters().isEmpty()).forEach(node -> {
            node.getAdapters().forEach(adapter -> {
                resp.appendField(adapter, node.getUrls());
            });
        });
        return new ResponseEntity<JSONObject>(resp, HttpStatus.OK);
    }

}
