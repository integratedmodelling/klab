package org.integratedmodelling.stats.controller;

import java.security.Principal;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.node.auth.Role;
import org.integratedmodelling.klab.rest.ObservationResultStatistics;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Stats ingestion and reporting endpoints.
 * 
 * @author Ferd
 *
 */
@RestController
@Secured(Role.ENGINE)
public class StatsController {

    /*
     * Only needed endpoint is PUT to add activities. All the rest is handled through the adapter or
     * the operations.
     */
    @PutMapping(API.STATS.STATS_ADD)
    public void addActivity(ObservationResultStatistics activity, Principal principal) {

    }
    
    /**
     * Report generation endpoint. All data are also available through k.LAB resources.
     * 
     * @param principal
     * @return
     */
    @GetMapping(value=API.STATS.STATS_REPORT, produces = {"text/html" /*, PDF and text (markdown) */})
    public String addActivity(Principal principal) {
        return "<p>Unimplemented</p>";
    }

}
