package org.integratedmodelling.stats.controller;

import java.security.Principal;

import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.node.auth.Role;
import org.integratedmodelling.klab.rest.ObservationResultStatistics;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Secured(Role.ENGINE)
public class StatsController {

    /*
     * Only needed endpoint is PUT to add activities. All the rest is handled through the adapter or
     * the operations.
     */
    @PutMapping(API.STATS.STATS_ADD)
    void addActivity(ObservationResultStatistics activity, Principal principal) {

    }
}
