package org.integratedmodelling.klab.node.controllers;

import java.security.Principal;
import java.time.LocalDate;

import javax.validation.Valid;

import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.auth.IUserIdentity;
//import org.integratedmodelling.klab.auth.Role;
import org.integratedmodelling.klab.node.services.StatsQueryService;
import org.integratedmodelling.stats.api.StatsQueryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 *  
 * @author aris
 *
 */
@RestController
//@Secured(Role.USER)
public class StatsQueryController {

    
    private StatsQueryService outputServiceHandler;
    
    @Autowired
    StatsQueryController(StatsQueryService outputServiceHandler) {
        this.outputServiceHandler = outputServiceHandler;
    }
    
    @GetMapping(value = API.STATS.STATS_OUTPUT, produces = {"application/json"})
    @PreAuthorize("hasRole('ROLE_SYSTEM') or hasRole('ROLE_ADMINISTRATOR') or hasRole('ROLE_USER')")
    public ResponseEntity<?> getOutput(Principal principal,@Valid StatsQueryRequest request)
                                    throws JsonProcessingException {
            //throws UnsupportedEncodingException {
        
        //IUserIdentity user = Authentication.INSTANCE.getUserIdentity(principal);
       // boolean adminOrAuditor = Authentication.INSTANCE.hasRole(principal, Role.ROLE_ADMINISTRATOR);
        
        if(request == null) {
            request = new StatsQueryRequest();
        }
        
        ResponseEntity<?> output = outputServiceHandler.getQuery(request);
        return output;                
 
    }
}
